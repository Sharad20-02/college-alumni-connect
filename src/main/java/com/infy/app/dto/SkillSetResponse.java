package com.infy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillSetResponse {
    private Long id;
    private String skills;
    private Integer experienceYears;
    private Double gpa;
    private Integer score;  // calculated in backend
}
