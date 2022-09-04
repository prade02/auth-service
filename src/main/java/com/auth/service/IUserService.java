package com.auth.service;

import com.auth.models.User;

import java.util.Collection;

public interface IUserService {
    User findByUsername(String userName);

    Collection<String> getUserAuthorities(int userId);
}
