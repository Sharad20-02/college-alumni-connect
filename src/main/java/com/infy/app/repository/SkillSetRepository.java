package com.infy.app.repository;

import com.infy.app.model.SkillSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillSetRepository extends JpaRepository<SkillSet, Long> {

    // Example: find all alumni who have "Java" in their skills
    List<SkillSet> findBySkillsContainingIgnoreCase(String skill);

    // You could add more filters later, like GPA or years of experience
}
