package com.souret.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "userfollowers")
public class UserFollower {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="userid")
    private int userid;
    @Column(name="followerid")
    private int followerid;
    @Column(name = "followdate")
    private LocalDateTime followdate;
}
