package com.infy.app.repository;

import com.infy.app.model.Job;
import com.infy.app.model.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByAlumniIn(List<Alumni> alumniList);
}
