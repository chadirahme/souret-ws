package com.souret.controller;


import com.souret.model.APIResult;
import com.souret.model.User;
import com.souret.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list() {
        return service.listAll();
    }

    @GetMapping("/peoples/{id}")
    public List<User> findUsersToFollow(@PathVariable Integer id) {
        return service.findUsersToFollow(id);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        try {
            User user = service.get(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<APIResult> add(@RequestBody User user, UriComponentsBuilder builder) {
        APIResult apiResult=new APIResult();
        String message = "";

        boolean flag = service.addUser(user);
        if (flag == false) {
            message = "Email already registered !!!";
            apiResult.setMessage(message);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResult);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        message = "Email is registered !!!";
        apiResult.setMessage(message);
        apiResult.setUser(service.get(user.getId()));
        return ResponseEntity.ok()
                 .headers(headers)
                .body(apiResult);
       // return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //curl -X POST -H "Content-Type: application/json" -d "{\"email\":\"eng.chadi\",\"password\":123}" http://localhost:5000/users/login
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        try {
            User loginUser = service.loginUser(user);
            if(loginUser!=null)
            return new ResponseEntity<User>(loginUser, HttpStatus.OK);
            else
                return new ResponseEntity<User>( new User(), HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Integer id) {
        try {
            User existUser = service.get(id);
            service.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //curl -X DELETE http://localhost:5000/users/10
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
