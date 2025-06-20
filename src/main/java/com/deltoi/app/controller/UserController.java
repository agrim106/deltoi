package com.deltoi.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deltoi.app.dto.UserDto;
import com.deltoi.app.dto.UserLoginRequest;
import com.deltoi.app.dto.UserSignupRequest;
import com.deltoi.app.entity.User;
import com.deltoi.app.repository.UserRepository;
import com.deltoi.app.service.UserService;

/**
 * Controller for handling user authentication and profile operations.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Handles user signup with a new account.
     * @param request User signup details
     * @return ResponseEntity with JWT token and 201 Created status
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequest request) {
        ResponseEntity<String> response = userService.signup(request);
        return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
    }

    /**
     * Handles user login with credentials.
     * @param request User login details
     * @return ResponseEntity with JWT token and 200 OK status
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        ResponseEntity<String> response = userService.login(request);
        return response;
    }

    /**
     * Handles user logout (client-side token invalidation).
     * @param auth Current authentication context
     * @return ResponseEntity with 200 OK status
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication auth) {
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves the authenticated user's profile.
     * @param auth Current authentication context
     * @return ResponseEntity with UserDto and 200 OK status
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(Authentication auth) {
        String email = auth.getName(); // Updated to use email
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            User u = user.get();
            UserDto userDto = new UserDto(u.getId(), u.getUsername(), u.getEmail(), u.getCreatedAt());
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }
}