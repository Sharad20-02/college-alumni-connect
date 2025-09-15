package com.infy.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alumni")
public class Alumni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String secretKey;
    private Boolean verified = false;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus; 

    @ManyToOne
    private College college;

    @OneToOne(mappedBy = "alumni", cascade = CascadeType.ALL)
    @JsonManagedReference
    private SkillSet skillSet;

    @OneToOne(mappedBy = "alumni", cascade = CascadeType.ALL)
    private Job currentJob;
}