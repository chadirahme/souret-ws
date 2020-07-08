package com.souret.service;

import com.souret.config.GenerateFileName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Service
public class FileService extends GenerateFileName {

    @Value("${upload.path}")
    private String path;

    private final Path root = Paths.get("/Users/chadirahme/IdeaProjects/souret-ws/ProfilePicture");

    //private final Path rootLocation = Paths.get(path);

    public String store(MultipartFile file) {
        String filePath="";

        try {
            //String ext= getFileExtension(file.getOriginalFilename());
            String  uniqueFileName=getUniqueFileName(path,"" ,file.getOriginalFilename());
            Path rootLocation = Paths.get(path);
            System.out.println(file.getOriginalFilename());
            System.out.println(rootLocation.toUri());
            //filePath=rootLocation.resolve(file.getOriginalFilename()).toString();
            filePath=rootLocation.resolve(uniqueFileName).toString();

            Files.copy(file.getInputStream(), rootLocation.resolve(uniqueFileName),
                    StandardCopyOption.REPLACE_EXISTING);


            return filePath;
        } catch (Exception e) {
            //throw new RuntimeException("FAIL!");
            return "-1";
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path rootLocation = Paths.get(path);
            Path file = rootLocation.resolve(filename);

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }


    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public void sound() {

    }
}
