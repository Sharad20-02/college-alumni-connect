package com.infy.app.controller;

import com.infy.app.dto.AlumniJobPostRequest;
import com.infy.app.dto.JobListingResponse;
import com.infy.app.model.Alumni;
import com.infy.app.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // -----------------------------
    // Employed alumni posts a job
    // -----------------------------
    @PostMapping("/post")
    public ResponseEntity<String> postJob(@RequestBody AlumniJobPostRequest request,
                                          Authentication authentication) {
        Long alumniId = Long.parseLong(authentication.getName());
        jobService.postJobByAlumni(alumniId, request);
        return ResponseEntity.ok("Job posted successfully!");
    }

    // -----------------------------
    // Unemployed alumni browsing jobs
    // -----------------------------
    @GetMapping("/browse")
    public ResponseEntity<List<JobListingResponse>> browseJobs(Authentication authentication) {
        Long alumniId = Long.parseLong(authentication.getName());
        List<JobListingResponse> jobs = jobService.browseJobs(alumniId);
        return ResponseEntity.ok(jobs);
    }

    // Unemployed alumni clicks "Interested"
    @PostMapping("/jobs/{jobId}/interested")
    public ResponseEntity<String> interestedInJob(@PathVariable Long jobId, Authentication auth) {
        Long alumniId = Long.parseLong(auth.getName());
        jobService.notifyEmployedAlumni(alumniId, jobId);
        return ResponseEntity.ok("Notification sent to job poster!");
    }

    // Employed alumni browses candidates
    @GetMapping("/candidates")
    public ResponseEntity<List<Alumni>> browseCandidates(Authentication auth) {
        Long alumniId = Long.parseLong(auth.getName());
        return ResponseEntity.ok(jobService.browseCandidates(alumniId));
    }

    // Employed alumni sends referral
    @PostMapping("/candidates/{candidateId}/notify")
    public ResponseEntity<String> notifyCandidate(@PathVariable Long candidateId, Authentication auth) {
        Long alumniId = Long.parseLong(auth.getName());
        jobService.notifyCandidate(alumniId, candidateId);
        return ResponseEntity.ok("Candidate notified successfully!");
    }

}
