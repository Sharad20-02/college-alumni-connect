package com.infy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumniJobPostRequest {
    private Long alumniId;   // who is posting
    private String role;
    private String company;
    private Double salary;
    private String location;
    private String workingHours;
    private String workingDays;
    private String requiredSkills;
    private String applicationUrl;  // optional
}
