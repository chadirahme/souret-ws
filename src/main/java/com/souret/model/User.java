package com.souret.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    public User(){

    }
    public User(int id){
        this.id=id;
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name = "creationdate")
    private LocalDateTime creationdate;

    @Column(name="usertype")
    private int usertype;

    @Column(name="followercount")
    private int followercount;

    @Column(name="followingcount")
    private int followingcount;

}
