package com.example.registration.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Alex on 20.08.2017.
 */
@Data
@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "matching_password")
    private String matchingPassword;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "enabled")
    private boolean enabled;
}
