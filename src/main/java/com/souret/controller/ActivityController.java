package com.souret.controller;

import com.souret.config.GenerateFileName;
import com.souret.model.APIResult;
import com.souret.model.Activity;
import com.souret.model.FileInfo;
import com.souret.model.User;
import com.souret.service.ActivityService;
import com.souret.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("activity")
public class ActivityController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService service;

    @Autowired
    FileService fileservice;

    @GetMapping(value = "/allActivity/{id}")
    public List<Activity> list(@PathVariable Integer id) {
        if(id==0)
        return service.listAll()
                .stream().sorted(Comparator.comparing(Activity::getCreationdate).reversed())
                .collect(Collectors.toList());
        else
            return service.listUserActivity(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> get(@PathVariable Integer id) {
        try {
            Activity activity = service.get(id);
            return new ResponseEntity<Activity>(activity, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Activity>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping//(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<APIResult>  add( @RequestParam("description") String description,
                                           @RequestParam("userId") int userId,
                                          @RequestParam MultipartFile file) {
        APIResult apiResult=new APIResult();
        String message = "";
        try {
            logger.info(String.format("File name '%s' uploaded successfully.", file.getOriginalFilename()));

            String filePath = fileservice.store(file);

            Activity activity=new Activity();
            activity.setUser(new User(userId));
            activity.setDescription(description);
            activity.setFilename( GenerateFileName.getNewFileName());//file.getOriginalFilename());
            activity.setFilepath(filePath);

            service.addActivity(activity);

            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            apiResult.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(apiResult);
            //return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            message = "Fail to upload Profile Picture" + file.getOriginalFilename() + "!";
            apiResult.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(apiResult);
        }

    }

    @GetMapping(value = "/files/{filename:.+}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileservice.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    //@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> add1(@RequestBody Activity activity, UriComponentsBuilder builder,
                                    @RequestParam MultipartFile file) {
        logger.info(String.format("File name '%s' uploaded successfully.", file.getOriginalFilename()));

        boolean flag = service.addActivity(activity);
        if (flag == false) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/activity/{id}").buildAndExpand(activity.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = fileservice.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ActivityController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }


}
