package com.infy.app.controller;

import com.infy.app.dto.*;
import com.infy.app.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // -----------------------------
    // Register (verify secret key)
    // -----------------------------
    @PostMapping("/register")
    public ResponseEntity<AlumniRegistrationResponse> register(
            @RequestBody AlumniRegistrationRequest request) {
        AlumniRegistrationResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    // -----------------------------
    // Login (returns JWT)
    // -----------------------------
    @PostMapping("/login")
    public ResponseEntity<AlumniLoginResponse> login(
            @RequestBody AlumniLoginRequest request) {
        AlumniLoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
