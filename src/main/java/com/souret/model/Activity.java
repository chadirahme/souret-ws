package com.souret.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(name="userid")
//    private int userid;

    @Column(name="description")
    private String description;

    @Column(name = "creationdate")
    private LocalDateTime creationdate;

    @OneToOne
    @JoinColumn(name="userid")
    private User user;

    @Column(name="filename")
    private String filename;

    @Column(name="filepath")
    private String filepath;
}
