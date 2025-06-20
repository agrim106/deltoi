package com.deltoi.app.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deltoi.app.dto.UserLoginRequest;
import com.deltoi.app.dto.UserSignupRequest;
import com.deltoi.app.security.JwtUtil;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    private Map<String, org.springframework.security.core.userdetails.User> userStore = new HashMap<>();

    public String signup(UserSignupRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserDetails user = User.withUsername(request.getUsername())
                .password(encodedPassword)
                .roles("USER")
                .build();
        userStore.put(request.getUsername(), (org.springframework.security.core.userdetails.User) user);
        return jwtUtil.generateToken(request.getUsername());
    }

    public String login(UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(request.getUsername());
        }
        throw new RuntimeException("Invalid credentials");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userStore.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }
}