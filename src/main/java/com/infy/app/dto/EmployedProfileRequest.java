package com.infy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployedProfileRequest {
    private Long alumniId;
    private String company;
    private String role;
    private Double salary;
    private String location;
    private String workingHours;
    private String workingDays;
}
