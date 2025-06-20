package com.deltoi.app;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.userdetails.User;

import com.deltoi.app.security.JwtUtil;
import com.deltoi.app.service.UserService;

@SpringBootTest
class DeltoiApplicationTests {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @MockBean
    private JwtUtil mockJwtUtil;

    @MockBean
    private Authentication mockAuthentication; // Now resolves correctly

    @MockBean
    private UserService mockUserService;

    @Test
    void contextLoads() {
        when(mockJwtUtil.generateToken(any(String.class))).thenReturn("mocked-token");
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        when(mockUserService.loadUserByUsername(any(String.class))).thenReturn(User.withUsername("test").password("password").roles("USER").build());
    }
}