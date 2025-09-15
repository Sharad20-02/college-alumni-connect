package com.infy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillSetRequest {
    private String skills;
    private Integer experienceYears;
    private Double gpa;
}
