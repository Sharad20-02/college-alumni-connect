package com.infy.app.dto;

import com.infy.app.model.PosterType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobListingResponse {
    private Long jobId;
    private String role;
    private String company;
    private Double salary;
    private String location;
    private String requiredSkills;
    private PosterType postedByType; // COLLEGE / ALUMNI
    private String contactEmail;
}
