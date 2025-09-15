package com.infy.app.service;

import com.infy.app.dto.*;
import com.infy.app.model.Alumni;
import com.infy.app.repository.AlumniRepository;
import com.infy.app.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AlumniRepository alumniRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AlumniRepository alumniRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.alumniRepo = alumniRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AlumniRegistrationResponse register(AlumniRegistrationRequest req) {
        Alumni alumni = alumniRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("No alumni invited with this email"));

        if (!alumni.getSecretKey().equals(req.getSecretKey())) {
            throw new RuntimeException("Invalid secret key");
        }

        alumni.setFullName(req.getFullName());
        alumni.setPassword(passwordEncoder.encode(req.getPassword()));
        alumni.setVerified(true);

        alumniRepo.save(alumni);

        return new AlumniRegistrationResponse(
                alumni.getId(),
                alumni.getFullName(),
                alumni.getEmail(),
                alumni.getVerified()
        );
    }

    public AlumniLoginResponse login(AlumniLoginRequest req) {
        Alumni alumni = alumniRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Alumni not found"));

        if (!passwordEncoder.matches(req.getPassword(), alumni.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(
                alumni.getId(),
                alumni.getEmail(),
                alumni.getEmploymentStatus().name()
        );

        return new AlumniLoginResponse(
                alumni.getId(),
                token,
                alumni.getFullName(),
                alumni.getEmploymentStatus()
        );
    }
}
