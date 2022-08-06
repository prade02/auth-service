package com.auth.models;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
@Getter
public class UserRole {

    @Id
    private int id;
    private int userId;
    private String authority;
}
