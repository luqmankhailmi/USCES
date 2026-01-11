-- If want to use sequence:
INSERT INTO FACULTY
(faculty_id, faculty_name)
VALUES
(NEXT VALUE FOR FACULTY_SEQ, 'Computer Science');
-- Use NEXT VALUE FOR x instead of NEXT_VAL (Java DB Derby doesnt support NEXT_VAL)
