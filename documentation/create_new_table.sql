-- Create Sequences for Auto-increment
CREATE SEQUENCE faculty_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE admin_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE student_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE manifesto_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE election_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE candidate_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE vote_seq START WITH 1 INCREMENT BY 1;

-- 1. FACULTY Table
CREATE TABLE FACULTY (
    faculty_id INTEGER PRIMARY KEY,
    faculty_name VARCHAR(255) NOT NULL
);

-- 2. ADMIN Table
CREATE TABLE ADMIN (
    admin_id INTEGER PRIMARY KEY,
    admin_name VARCHAR(255) NOT NULL,
    staff_number VARCHAR(12) NOT NULL,
    faculty_id INTEGER NOT NULL,
    admin_email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT fk_admin_faculty FOREIGN KEY (faculty_id) REFERENCES FACULTY(faculty_id)
);

-- 3. STUDENT Table
CREATE TABLE STUDENT (
    student_id INTEGER PRIMARY KEY,
    student_name VARCHAR(255) NOT NULL,
    student_number VARCHAR(12) NOT NULL,
    faculty_id INTEGER NOT NULL,
    student_email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT fk_student_faculty FOREIGN KEY (faculty_id) REFERENCES FACULTY(faculty_id)
);

-- 4. MANIFESTO Table
CREATE TABLE MANIFESTO (
    manifesto_id INTEGER PRIMARY KEY,
    manifesto_content VARCHAR(2000) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. ELECTION Table
CREATE TABLE ELECTION (
    election_id INTEGER PRIMARY KEY,
    election_name VARCHAR(255) NOT NULL,
    faculty_id INTEGER NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    CONSTRAINT fk_election_faculty FOREIGN KEY (faculty_id) REFERENCES FACULTY(faculty_id)
);

-- 6. CANDIDATE Table
CREATE TABLE CANDIDATE (
    candidate_id INTEGER PRIMARY KEY,
    student_id INTEGER NOT NULL,
    election_id INTEGER NOT NULL,
    manifesto_id INTEGER NOT NULL,
    CONSTRAINT fk_candidate_student FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    CONSTRAINT fk_candidate_election FOREIGN KEY (election_id) REFERENCES ELECTION(election_id),
    CONSTRAINT fk_candidate_manifesto FOREIGN KEY (manifesto_id) REFERENCES MANIFESTO(manifesto_id)
);

-- 7. VOTE Table
CREATE TABLE VOTE (
    vote_id INTEGER PRIMARY KEY,
    student_id INTEGER NOT NULL,
    election_id INTEGER NOT NULL,
    candidate_id INTEGER NOT NULL,
    voted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_vote_student FOREIGN KEY (student_id) REFERENCES STUDENT(student_id),
    CONSTRAINT fk_vote_election FOREIGN KEY (election_id) REFERENCES ELECTION(election_id),
    CONSTRAINT fk_vote_candidate FOREIGN KEY (candidate_id) REFERENCES CANDIDATE(candidate_id)
);
