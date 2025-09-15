package com.infy.app.controller;

import com.infy.app.dto.EmployedProfileRequest;
import com.infy.app.dto.UnemployedProfileRequest;
import com.infy.app.service.AlumniService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alumni")
public class AlumniController {

    private final AlumniService alumniService;

    public AlumniController(AlumniService alumniService) {
        this.alumniService = alumniService;
    }

    // -----------------------------
    // Complete profile for EMPLOYED alumni
    // -----------------------------
    @PostMapping("/profile/employed")
    public ResponseEntity<String> completeEmployedProfile(
            @RequestBody EmployedProfileRequest request,
            Authentication authentication) {

        // alumniId is stored as principal in JwtFilter
        Long alumniId = Long.parseLong(authentication.getName());

        alumniService.completeProfileEmployed(alumniId, request);
        return ResponseEntity.ok("Employed profile completed successfully!");
    }

    // -----------------------------
    // Complete profile for UNEMPLOYED alumni
    // -----------------------------
    @PostMapping("/profile/unemployed")
    public ResponseEntity<String> completeUnemployedProfile(
            @RequestBody UnemployedProfileRequest request,
            Authentication authentication) {

        Long alumniId = Long.parseLong(authentication.getName());

        alumniService.completeProfileUnemployed(alumniId, request);
        return ResponseEntity.ok("Unemployed profile completed successfully!");
    }
}
