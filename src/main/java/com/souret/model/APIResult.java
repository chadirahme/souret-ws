package com.souret.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
public class APIResult {
    String message;
    int userId;
    Object user;

    public APIResult(){

    }

    public APIResult(String message)
    {
        this.message = message;
    }
}
