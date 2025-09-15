package com.infy.app.repository;

import com.infy.app.model.Alumni;
import com.infy.app.model.College;
import com.infy.app.model.EmploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlumniRepository extends JpaRepository<Alumni, Long> {

    Optional<Alumni> findByEmail(String email);

    // <---- Add this method for employed alumni browsing candidates
    List<Alumni> findByCollegeAndEmploymentStatus(College college, EmploymentStatus status);
}
