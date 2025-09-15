package com.infy.app;

import com.infy.app.model.Alumni;
import com.infy.app.model.EmploymentStatus;
import com.infy.app.model.College;
import com.infy.app.repository.AlumniRepository;
import com.infy.app.repository.CollegeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    CommandLineRunner preloadData(CollegeRepository collegeRepo, AlumniRepository alumniRepo, PasswordEncoder passwordEncoder) {
        return args -> {

            // ---------- Sample College ----------
            College college = new College();
            college.setName("ABC Engineering College");
            college.setAddress("123 Main Street");
            college.setContactMail("placement@abc.edu");
            collegeRepo.save(college);

            // ---------- Sample Alumni 1 (unemployed) ----------
            Alumni alumni1 = new Alumni();
            alumni1.setFullName("John Doe");
            alumni1.setEmail("john@example.com");
            alumni1.setPassword(passwordEncoder.encode("password123"));
            alumni1.setSecretKey("abc123");   // used for registration verification
            alumni1.setEmploymentStatus(EmploymentStatus.UNEMPLOYED);
            alumni1.setVerified(false);
            alumni1.setCollege(college);
            alumniRepo.save(alumni1);

            // ---------- Sample Alumni 2 (employed) ----------
            Alumni alumni2 = new Alumni();
            alumni2.setFullName("Jane Smith");
            alumni2.setEmail("jane@example.com");
            alumni2.setPassword(passwordEncoder.encode("password123"));
            alumni2.setSecretKey("def456");
            alumni2.setEmploymentStatus(EmploymentStatus.EMPLOYED);
            alumni2.setVerified(false);
            alumni2.setCollege(college);
            alumniRepo.save(alumni2);

            System.out.println("Preloaded dummy college and alumni!");
        };
    }
}
