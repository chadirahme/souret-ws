package com.souret.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("public")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> test() {
        try {
            return new ResponseEntity<String>("Server UP...", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }
}


