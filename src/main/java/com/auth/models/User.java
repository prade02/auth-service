package com.auth.models;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_details")
@Getter
public class User {

    @Id
    private int id;
    private String userName;
    private String email;
    private String hashedPassword;
    private boolean locked;
}
