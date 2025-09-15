package com.infy.app.repository;

import com.infy.app.model.College;
import com.infy.app.model.NewJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewJobRepository extends JpaRepository<NewJob, Long> {

    // Find jobs posted by college or by employed alumni from this college
    List<NewJob> findByPostedByCollegeOrPostedByAlumni_College(College college1, College college2);
}
