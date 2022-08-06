package com.auth.service;

import com.auth.models.User;
import com.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public Collection<String> getUserAuthorities(int userId) {
        return userRepository.getUserAuthorities(userId);
    }
}
