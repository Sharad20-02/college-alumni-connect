CREATE TABLE college (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    contact_mail VARCHAR(255)
);

CREATE TABLE alumni (
    id BIGINT PRIMARY KEY,
    full_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    secret_key VARCHAR(255),
    verified BOOLEAN,
    employment_status VARCHAR(20),
    college_id BIGINT,
    FOREIGN KEY (college_id) REFERENCES college(id)
);

CREATE TABLE skill_set (
    id BIGINT PRIMARY KEY,
    skills VARCHAR(255),
    gpa DOUBLE,
    experience_years INT,
    score DOUBLE,
    alumni_id BIGINT,
    FOREIGN KEY (alumni_id) REFERENCES alumni(id)
);

CREATE TABLE job (
    id BIGINT PRIMARY KEY,
    role VARCHAR(255),
    company VARCHAR(255),
    salary BIGINT,
    location VARCHAR(255),
    working_hours VARCHAR(50),
    working_days VARCHAR(50),
    alumni_id BIGINT,
    FOREIGN KEY (alumni_id) REFERENCES alumni(id)
);

CREATE TABLE new_job (
    id BIGINT PRIMARY KEY,
    role VARCHAR(255),
    company VARCHAR(255),
    salary BIGINT,
    working_hours VARCHAR(50),
    working_days VARCHAR(50),
    required_skills VARCHAR(255),
    posted_by_type VARCHAR(20),
    contact_email VARCHAR(255),
    posted_by_alumni_id BIGINT,
    posted_by_college_id BIGINT,
    application_url VARCHAR(255)
);


-- ------------------------------
-- Colleges
-- ------------------------------

INSERT INTO college (id, name, address, contact_mail) VALUES
(1, 'ABC Engineering College', '123 Main Street', 'placement@abc.edu'),
(2, 'XYZ Institute of Technology', '456 Elm Street', 'placement@xyz.edu');

-- ------------------------------
-- Alumni (some employed, some unemployed)
-- ------------------------------
-- Passwords are bcrypt encoded version of "password123"
INSERT INTO alumni (id, full_name, email, password, secret_key, verified, employment_status, college_id) VALUES
(1, 'John Doe', 'unemployed1@college.com', '$2a$10$hhGhrLRzcmjvs2f6O5AUt.Yfxr1ZBZgGtg3a0.jrURLARphf7rPMa', 'abc123', true, 'UNEMPLOYED', 1),
(2, 'Jane Smith', 'employed1@college.com', '$2a$10$hhGhrLRzcmjvs2f6O5AUt.Yfxr1ZBZgGtg3a0.jrURLARphf7rPMa', 'abc123', true, 'EMPLOYED', 1),
(3, 'Alice Johnson', 'unemployed2@college.com', '$2a$10$hhGhrLRzcmjvs2f6O5AUt.Yfxr1ZBZgGtg3a0.jrURLARphf7rPMa', 'xyz123', true, 'UNEMPLOYED', 2),
(4, 'Bob Williams', 'employed2@college.com', '$2a$10$hhGhrLRzcmjvs2f6O5AUt.Yfxr1ZBZgGtg3a0.jrURLARphf7rPMa', 'xyz123', true, 'EMPLOYED', 2);

-- ------------------------------
-- SkillSets for unemployed alumni
-- ------------------------------
INSERT INTO skill_set (id, skills, gpa, experience_years, score, alumni_id) VALUES
(1, 'Java,SpringBoot,SQL', 8.5, 2, NULL, 1),
(2, 'Python,Django,ML', 9.0, 3, NULL, 3);

-- ------------------------------
-- Jobs posted by employed alumni
-- ------------------------------
INSERT INTO job (id, role, company, salary, location, working_hours, working_days, alumni_id) VALUES
(1, 'Software Engineer', 'Infosys', 850000, 'Bangalore', '9AM-6PM', 'Mon-Fri', 2),
(2, 'Data Analyst', 'TCS', 700000, 'Mumbai', '9AM-5PM', 'Mon-Fri', 4);

-- ------------------------------
-- NewJob (for alumni referrals) example
-- ------------------------------
INSERT INTO new_job (id, role, company, salary, working_hours, working_days, required_skills, posted_by_type, contact_email, posted_by_alumni_id, posted_by_college_id, application_url) VALUES
(1, 'Backend Developer', 'Capgemini', 800000, '10AM-7PM', 'Mon-Fri', 'Java,Spring', 'ALUMNI', 'employed1@college.com', 2, NULL, 'https://example.com/apply');
