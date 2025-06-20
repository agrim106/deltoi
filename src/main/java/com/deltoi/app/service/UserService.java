package com.deltoi.app.service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deltoi.app.dto.UserLoginRequest;
import com.deltoi.app.dto.UserSignupRequest;
import com.deltoi.app.entity.User;
import com.deltoi.app.repository.UserRepository;
import com.deltoi.app.security.JwtUtil;

/**
 * Service for managing user authentication and details.
 */
@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    /**
     * Signs up a new user with a hashed password.
     * @param request User signup details
     * @return ResponseEntity with JWT token and 201 Created status
     * @throws IllegalArgumentException if password is empty
     */
    public ResponseEntity<String> signup(UserSignupRequest request) {
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            logger.warn("Signup attempt with empty password for username: {}", request.getUsername());
            throw new IllegalArgumentException("Password cannot be empty");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        logger.info("Signing up user: {}, encoded password length: {}", request.getUsername(), encodedPassword.length());
        User user = new User();
        user.setUsername(request.getUsername()); // Keep username for name column
        user.setPassword(encodedPassword);
        user.setEmail(request.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok(jwtUtil.generateToken(request.getEmail())); // Use email for token
    }

    /**
     * Authenticates a user and returns a JWT token.
     * @param request User login details
     * @return ResponseEntity with JWT token and 200 OK status
     * @throws RuntimeException if authentication fails
     */
    public ResponseEntity<String> login(UserLoginRequest request) {
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            logger.warn("Login attempt with empty password for email: {}", request.getEmail());
            throw new IllegalArgumentException("Password cannot be empty");
        }
        logger.info("Login attempt for email: {}, password provided: {}", request.getEmail(), request.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            if (authentication.isAuthenticated()) {
                logger.info("Login successful for email: {}", request.getEmail());
                return ResponseEntity.ok(jwtUtil.generateToken(request.getEmail()));
            }
        } catch (Exception e) {
            logger.error("Authentication failed for email: {}, error: {}", request.getEmail(), e.getMessage());
            throw new RuntimeException("Invalid credentials: " + e.getMessage());
        }
        logger.warn("Login failed for email: {}, invalid credentials", request.getEmail());
        throw new RuntimeException("Invalid credentials");
    }

    /**
     * Loads user details by email for authentication.
     * @param email The user's email (used as username)
     * @return UserDetails object
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), new ArrayList<>()); // Use email as username
    }
}