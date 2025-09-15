package com.infy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumniJobPostResponse {
    private Long jobId;
    private String role;
    private String company;
    private String contactEmail; // referrer’s email auto-filled
}
