package com.infy.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "new_job")
public class NewJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    private String company;
    private Double salary;
    private String workingHours;
    private String workingDays;
    private String requiredSkills;

    @Enumerated(EnumType.STRING)
    private PosterType postedByType; // COLLEGE / ALUMNI

    private String contactEmail;
    private String applicationUrl;

    @ManyToOne
    private Alumni postedByAlumni;

    @ManyToOne
    private College postedByCollege;
}
