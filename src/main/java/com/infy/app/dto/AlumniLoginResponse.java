package com.infy.app.dto;

import com.infy.app.model.EmploymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumniLoginResponse {
    private Long alumniId;
    private String token;
    private String fullName;
    private EmploymentStatus employmentStatus;
}