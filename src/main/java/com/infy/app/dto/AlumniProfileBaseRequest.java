package com.infy.app.dto;

import com.infy.app.model.EmploymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumniProfileBaseRequest {
    private Long alumniId;
    private String phoneNumber;
    private String address;
    private EmploymentStatus employmentStatus;
}
