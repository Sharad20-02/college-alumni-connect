package com.infy.app.service;

import com.infy.app.dto.AlumniJobPostRequest;
import com.infy.app.dto.JobListingResponse;
import com.infy.app.model.*;
import com.infy.app.repository.AlumniRepository;
import com.infy.app.repository.CollegeRepository;
import com.infy.app.repository.JobRepository;
import com.infy.app.repository.NewJobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final NewJobRepository newJobRepo;
    private final AlumniRepository alumniRepo;
    private final CollegeRepository collegeRepo;
    private final JobRepository jobRepo;
    private final EmailService emailService;

    public JobService(NewJobRepository newJobRepo, AlumniRepository alumniRepo, CollegeRepository collegeRepo,
                    EmailService emailService, JobRepository jobRepo) {
        this.newJobRepo = newJobRepo;
        this.alumniRepo = alumniRepo;
        this.collegeRepo = collegeRepo;
        this.emailService = emailService;
        this.jobRepo = jobRepo;
    }
    // -----------------------------
    // Employed alumni posts a job
    // -----------------------------
    @Transactional
    public void postJobByAlumni(Long alumniId, AlumniJobPostRequest req) {
        Alumni alumni = alumniRepo.findById(alumniId)
                .orElseThrow(() -> new RuntimeException("Alumni not found"));

        if (alumni.getEmploymentStatus() != EmploymentStatus.EMPLOYED) {
            throw new RuntimeException("Only employed alumni can post jobs");
        }

        NewJob job = new NewJob();
        job.setRole(req.getRole());
        job.setCompany(req.getCompany());
        job.setSalary(req.getSalary());
        job.setWorkingHours(req.getWorkingHours());
        job.setWorkingDays(req.getWorkingDays());
        job.setRequiredSkills(req.getRequiredSkills());
        job.setApplicationUrl(req.getApplicationUrl());
        job.setContactEmail(alumni.getEmail());
        job.setPostedByType(PosterType.ALUMNI);
        job.setPostedByAlumni(alumni);
        job.setPostedByCollege(alumni.getCollege()); // optional, for filtering by college

        newJobRepo.save(job);
    }

    // -----------------------------
    // Unemployed alumni browsing jobs
    // -----------------------------
     public List<JobListingResponse> browseJobs(Long alumniId) {
        Alumni alumni = alumniRepo.findById(alumniId)
                .orElseThrow(() -> new RuntimeException("Alumni not found"));

        // Only unemployed alumni can browse
        if (alumni.getEmploymentStatus() != EmploymentStatus.UNEMPLOYED) {
            throw new RuntimeException("Only unemployed alumni can browse jobs");
        }

        // Fetch employed alumni from the same college
        List<Alumni> employedAlumniInCollege = alumniRepo.findByCollegeAndEmploymentStatus(
                alumni.getCollege(),
                EmploymentStatus.EMPLOYED
        );

        // Fetch jobs posted by those alumni
        List<Job> jobs = jobRepo.findByAlumniIn(employedAlumniInCollege);

        // Map to response DTO
        return jobs.stream()
                .map(job -> new JobListingResponse(
                        job.getId(),
                        job.getRole(),
                        job.getCompany(),
                        job.getSalary(),
                        job.getLocation(),
                        null, // requiredSkills not used here
                        null, // postedByType not used here
                        job.getAlumni().getEmail()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void notifyCandidate(Long employedAlumniId, Long candidateAlumniId) {
        Alumni employed = alumniRepo.findById(employedAlumniId)
                .orElseThrow(() -> new RuntimeException("Alumni not found"));

        Alumni candidate = alumniRepo.findById(candidateAlumniId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        if (!employed.getCollege().equals(candidate.getCollege())) {
            throw new RuntimeException("Candidate is not from the same college");
        }

        // Send email
        String to = candidate.getEmail();
        String subject = "Referral Opportunity from Employed Alumni";
        String body = String.format(
            "Alumni %s (%s) is referring you to a job opportunity at %s",
            employed.getFullName(), employed.getEmail(), employed.getCurrentJob().getCompany()
        );

        emailService.sendEmail(to, subject, body);
    }

    @Transactional(readOnly = true)
    public List<NewJob> getJobsForUnemployedAlumni(Long alumniId) {
        Alumni alumni = alumniRepo.findById(alumniId)
                .orElseThrow(() -> new RuntimeException("Alumni not found"));
        College college = alumni.getCollege();

        // Only jobs posted by this college or employed alumni of this college
        return newJobRepo.findByPostedByCollegeOrPostedByAlumni_College(college, college);
    }

    @Transactional(readOnly = true)
    public List<Alumni> browseCandidates(Long employedAlumniId) {
        Alumni employed = alumniRepo.findById(employedAlumniId)
                .orElseThrow(() -> new RuntimeException("Alumni not found"));

        if (employed.getEmploymentStatus() != EmploymentStatus.EMPLOYED) {
            throw new RuntimeException("Only employed alumni can browse candidates");
        }

        College college = employed.getCollege();

        // Return all unemployed alumni of the same college
        return alumniRepo.findByCollegeAndEmploymentStatus(college, EmploymentStatus.UNEMPLOYED);
    }

    @Transactional
    public void notifyEmployedAlumni(Long unemployedAlumniId, Long jobId) {
        Alumni unemployed = alumniRepo.findById(unemployedAlumniId)
                .orElseThrow(() -> new RuntimeException("Alumni not found"));

        NewJob job = newJobRepo.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!unemployed.getCollege().equals(job.getPostedByCollege())) {
            throw new RuntimeException("Job not visible to this alumni");
        }

        // Send email to job poster
        String to = job.getPostedByAlumni().getEmail();
        String subject = "Candidate Interested in Your Job";
        String body = String.format(
            "Alumni %s (%s) is interested in your job posting: %s at %s",
            unemployed.getFullName(), unemployed.getEmail(), job.getRole(), job.getCompany()
        );
        emailService.sendEmail(to, subject, body);
    }
}


