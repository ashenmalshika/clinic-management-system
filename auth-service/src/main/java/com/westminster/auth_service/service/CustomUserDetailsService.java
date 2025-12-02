package com.westminster.auth_service.service;

import com.westminster.auth_service.model.AppUser;
import com.westminster.auth_service.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        var auths = Arrays.stream(u.getRoles().split(","))
                .map(String::trim).map(SimpleGrantedAuthority::new).toList();
        return new User(u.getUsername(), u.getPassword(), auths);
    }
}