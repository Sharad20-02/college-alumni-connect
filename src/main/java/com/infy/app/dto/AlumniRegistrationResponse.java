package com.infy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumniRegistrationResponse {
    private Long alumniId;
    private String fullName;
    private String email;
    private boolean verified;
}