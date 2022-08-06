package com.auth.security;

import com.auth.models.User;
import com.auth.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApplicationUserDetailService implements UserDetailsService {

    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(String.format("Provided user name %s not found", username));
        Set<SimpleGrantedAuthority> authorities = userService.getUserAuthorities(user.getId()).stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toSet());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getHashedPassword())
                .authorities(authorities)
                .accountLocked(user.isLocked())
                .build();
    }
}
