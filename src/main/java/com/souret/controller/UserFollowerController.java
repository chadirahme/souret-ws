package com.souret.controller;


import com.souret.model.APIResult;
import com.souret.model.User;
import com.souret.model.UserFollower;
import com.souret.service.UserFollowerService;
import com.souret.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("userfollower")
public class UserFollowerController {

    private static final Logger logger = LoggerFactory.getLogger(UserFollowerController.class);


    @GetMapping
    public List<UserFollower> list() {
        return service.listAll();
    }

    @Autowired
    private UserFollowerService service;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<APIResult> add(@RequestBody UserFollower user, UriComponentsBuilder builder) {
        service.addFollow(user);

        User user1= userService.get(user.getUserid());
        user1.setFollowingcount(user1.getFollowingcount()+1);
        userService.save(user1);

        User user2= userService.get(user.getFollowerid());
        user2.setFollowercount(user2.getFollowercount()+1);
        userService.save(user2);


        APIResult apiResult=new APIResult();
        apiResult.setMessage("UserFollower added..");
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }
}
