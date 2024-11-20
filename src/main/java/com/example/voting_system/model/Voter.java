package com.example.voting_system.model;

import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;

@Entity
@Data
public class Voter {


    @Id
    private String phoneNumber;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String confirmPassword;

    @Column(nullable = false)
    private String aadharNumber;

    @Column(nullable = false)
    private String avatar = "default.png";

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private boolean isVoted = false;

}


