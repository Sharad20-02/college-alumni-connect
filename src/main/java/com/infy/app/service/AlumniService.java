package com.infy.app.service;

import com.infy.app.dto.*;
import com.infy.app.model.*;
import com.infy.app.repository.AlumniRepository;
import com.infy.app.repository.SkillSetRepository;
import com.infy.app.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlumniService {

    private final AlumniRepository alumniRepo;
    private final SkillSetRepository skillRepo;
    private final JobRepository jobRepo;

    public AlumniService(AlumniRepository alumniRepo, SkillSetRepository skillRepo, JobRepository jobRepo) {
        this.alumniRepo = alumniRepo;
        this.skillRepo = skillRepo;
        this.jobRepo = jobRepo;
    }

    @Transactional
    public void completeProfileEmployed(Long alumniId, EmployedProfileRequest req) {
        Alumni alumni = alumniRepo.findById(alumniId)
                .orElseThrow(() -> new RuntimeException("Alumni not found"));

        // Update common info if needed
        alumni.setEmploymentStatus(EmploymentStatus.EMPLOYED);

        // Create Job entity
        Job job = new Job();
        job.setAlumni(alumni);
        job.setCompany(req.getCompany());
        job.setRole(req.getRole());
        job.setSalary(req.getSalary());
        job.setLocation(req.getLocation());
        job.setWorkingHours(req.getWorkingHours());
        job.setWorkingDays(req.getWorkingDays());

        alumni.setCurrentJob(job);
        alumniRepo.save(alumni); // cascades Job save
    }

    @Transactional
    public void completeProfileUnemployed(Long alumniId, UnemployedProfileRequest req) {
        Alumni alumni = alumniRepo.findById(alumniId)
                .orElseThrow(() -> new RuntimeException("Alumni not found"));

        alumni.setEmploymentStatus(EmploymentStatus.UNEMPLOYED);

        // Create SkillSet entity
        SkillSet skillSet = new SkillSet();
        skillSet.setAlumni(alumni);
        skillSet.setSkills(req.getSkillSet().getSkills());
        skillSet.setExperienceYears(req.getSkillSet().getExperienceYears());
        skillSet.setGpa(req.getSkillSet().getGpa());

        alumni.setSkillSet(skillSet);
        alumniRepo.save(alumni); // cascades SkillSet save
    }
}
