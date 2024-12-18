CREATE DATABASE OTUZBIR


	
CREATE TABLE Course(
   courseID NVARCHAR(50) PRIMARY KEY NOT NULL,
   name NVARCHAR(50) NOT NULL,
   credit INT,
   prerequisiteID NVARCHAR(50),
   courseType NVARCHAR(3)
);
-- Mandatory Courses
INSERT INTO Course VALUES ('MATH1001', 'Calculus I', 6, NULL, 'm');
INSERT INTO Course VALUES ('CSE1200', 'Introduction to Computer Engineering', 4, NULL, 'm');
INSERT INTO Course VALUES ('CSE1241', 'Computer Programming I', 6, NULL, 'm');
INSERT INTO Course VALUES ('PHYS1101', 'Physics I', 4, NULL, 'm');
INSERT INTO Course VALUES ('ATA121', 'Atatürk s Prin. & History I', 2, NULL, 'm');
INSERT INTO Course VALUES ('MBG1201', 'Introduction to Modern Biology', 2, NULL, 'm');
INSERT INTO Course VALUES ('TRD121', 'Turkish Language I', 2, NULL, 'm');
INSERT INTO Course VALUES ('MATH1002', 'Calculus II', 6, NULL, 'm');
INSERT INTO Course VALUES ('CSE1242', 'Computer Programming II', 6, 'CSE1241', 'm');
INSERT INTO Course VALUES ('PHYS1102', 'Physics II', 4, NULL, 'm');
INSERT INTO Course VALUES ('ATA122', 'Atatürk s Prin. & History II', 2, NULL, 'm');
INSERT INTO Course VALUES ('MATH2256', 'Linear Algebra for Comp. Eng.', 5, NULL, 'm');
INSERT INTO Course VALUES ('TRD122', 'Turkish Language II', 2, NULL, 'm');
INSERT INTO Course VALUES ('MATH2059', 'Numerical Methods', 4, 'MATH1001', 'm');
INSERT INTO Course VALUES ('CSE2225', 'Data Structures', 7, 'CSE1242', 'm');
INSERT INTO Course VALUES ('CSE2023', 'Discrete Comp. Structures', 6, NULL, 'm');
INSERT INTO Course VALUES ('EE2031', 'Electric Circuits', 5, 'PHYS1102', 'm');
INSERT INTO Course VALUES ('ECON2004', 'Engineering Economy', 4, NULL, 'm');
INSERT INTO Course VALUES ('MATH2055', 'Differential Equations', 4, NULL, 'm');
INSERT INTO Course VALUES ('CSE2246', 'Analysis of Algorithms', 6, 'CSE2225', 'm');
INSERT INTO Course VALUES ('CSE2260', 'Principles of Prog. Languages', 6, 'CSE1242', 'm');
INSERT INTO Course VALUES ('EE2032', 'Electronics', 5, 'EE2031', 'm');
INSERT INTO Course VALUES ('CSE2138', 'Systems Programming', 7, NULL, 'm');
INSERT INTO Course VALUES ('STAT2253', 'Prob. and Stat. for Comp. Eng.', 5, NULL, 'm');
INSERT INTO Course VALUES ('CSE3055', 'Database Systems', 7, 'CSE2225', 'm');
INSERT INTO Course VALUES ('CSE3033', 'Operating Systems', 7, 'CSE2225', 'm');
INSERT INTO Course VALUES ('CSE3063', 'Object-Oriented Software Desg.', 5, 'CSE1242', 'm');
INSERT INTO Course VALUES ('CSE3215', 'Digital Logic Design', 6, NULL, 'm');
INSERT INTO Course VALUES ('IE3081', 'Modeling and Disc. Simulation', 4, 'STAT2253', 'm');
INSERT INTO Course VALUES ('COM2202', 'Tech. Comm. & Entrepreneurship', 2, NULL, 'm');
INSERT INTO Course VALUES ('CSE3044', 'Software Engineering', 7, 'CSE3055', 'm');
INSERT INTO Course VALUES ('CSE3264', 'Formal Lang. & Auto. Theory', 5, 'CSE2023', 'm');
INSERT INTO Course VALUES ('CSE3038', 'Computer Organization', 7, 'CSE2138', 'm');
INSERT INTO Course VALUES ('CSE3048', 'Int. to Signals and Systems', 5, 'MATH2055', 'm');
INSERT INTO Course VALUES ('IE3235', 'Operations Research', 4, 'MATH2256', 'm');
INSERT INTO Course VALUES ('CSE4297', 'Engineering Project I', 5, NULL, 'm');
INSERT INTO Course VALUES ('CSE4074', 'Computer Networks', 5, NULL, 'm');
INSERT INTO Course VALUES ('CSE4219', 'Prin. of Embed. System Design', 6, 'CSE3038', 'm');
INSERT INTO Course VALUES ('ISG121', 'Work Safety I', 2, NULL, 'm');
INSERT INTO Course VALUES ('CSE4288', 'Int. to Machine Learning', 5, 'STAT2253', 'm');
INSERT INTO Course VALUES ('CSE4298', 'Engineering Project II', 5, 'CSE4297', 'm');
INSERT INTO Course VALUES ('ISG122', 'Work Safety II', 2, NULL, 'm');

-- Technical Elective Courses
INSERT INTO Course VALUES ('CSE4075', 'Wireless & Mobile Networks', 5, 'CSE4074', 'te');
INSERT INTO Course VALUES ('CSE4034', 'Advanced Unix Programming', 5, 'CSE3033', 'te');
INSERT INTO Course VALUES ('CSE4061', 'Compiler Design', 5, 'CSE3264', 'te');
INSERT INTO Course VALUES ('CSE4217', 'Microprocessors', 5, 'CSE3038', 'te');

-- Non-Technical Elective Courses
INSERT INTO Course VALUES ('NTE1003', 'The Culture of Radiation Safety', 3, NULL, 'nte');
INSERT INTO Course VALUES ('YDA1001', 'German for Beginners', 3, NULL, 'nte');
INSERT INTO Course VALUES ('HSS3002', 'Ethics in Engineering and Science', 3, NULL, 'nte');
INSERT INTO Course VALUES ('MGT1021', 'Design, Innovation and Entrepreneurship', 3, NULL, 'nte');


CREATE TABLE Mandatory(
	courseID NVARCHAR(50) PRIMARY KEY,
	year INT,
	FOREIGN KEY (CourseID) REFERENCES Course(CourseID) ON DELETE CASCADE
);

CREATE TABLE NTE(
	courseID NVARCHAR(50),
	FOREIGN KEY (courseID) REFERENCES Course(courseID) ON DELETE CASCADE
);
CREATE TABLE TE(
	courseID NVARCHAR(50),
	year INT,
	FOREIGN KEY (courseID) REFERENCES Course(courseID) ON DELETE CASCADE
);

CREATE TABLE Lecturer (
    ssn NVARCHAR(50) PRIMARY KEY,
    name NVARCHAR(50),
    surname NVARCHAR(50),
    birthdate DATE,
    gender CHAR(1)
);

CREATE TABLE Advisor (
    advisorID NVARCHAR(50) PRIMARY KEY, 
    ssn NVARCHAR(50),            
    FOREIGN KEY (ssn) REFERENCES Lecturer(ssn) on delete cascade
);
INSERT INTO Advisor VALUES ('123456', NULL);
INSERT INTO Advisor VALUES ('654321', NULL);

CREATE TABLE Student (
    studentID NVARCHAR(50) PRIMARY KEY,
    name NVARCHAR(50),
    surname NVARCHAR(50),
    gender CHAR,
    birthDate DATE,
    advisorID NVARCHAR(50),
	foreign key (advisorID) references Advisor(advisorID) on delete set null
);
INSERT INTO Student VALUES ('150121031', 'Kenan', 'Yildiz', 'M', '2000-01-01', '123456');
INSERT INTO Student VALUES ('150121032', 'Enis', 'Destan', 'M', '1999-02-14', '123456');
INSERT INTO Student VALUES ('150121033', 'Oguz', 'Aydin', 'M', '2001-03-12', '123456');
INSERT INTO Student VALUES ('150121034', 'Aslı', 'Cakir', 'F', '2001-05-20', '123456');
INSERT INTO Student VALUES ('150121035', 'Necip', 'Uysal', 'M', '2002-06-30', '123456');
INSERT INTO Student VALUES ('150122036', 'Berkan', 'Kutlu', 'M', '2002-08-15', '654321');
INSERT INTO Student VALUES ('150122037', 'Ferdi', 'Kadioglu', 'M', '2001-10-22', '654321');
INSERT INTO Student VALUES ('150122038', 'Ceyda', 'Santos', 'F', '1998-12-05', '654321');
INSERT INTO Student VALUES ('150122039', 'Elif', 'Yildirim', 'F', '1999-04-17', '654321');
INSERT INTO Student VALUES ('150122040', 'Kerem', 'Akturkoglu', 'M', '2005-06-09', '654321');
INSERT INTO Student VALUES ('150122041', 'Baris', 'Alper', 'M', '2006-01-09', '654321');
INSERT INTO Student VALUES ('150122042', 'Baki', 'Mercimek', 'M', '2005-11-09', '654321');

create table StudentsOfAdvisor(
	studentID nvarchar(50) primary key,
	advisorID nvarchar(50),
	foreign key (studentID) references Student(studentID) on delete cascade,
	foreign key (advisorID) references Advisor(advisorID) on delete cascade
);
INSERT INTO StudentsOfAdvisor VALUES ('150121031', '123456');
INSERT INTO StudentsOfAdvisor VALUES ('150121032', '123456');
INSERT INTO StudentsOfAdvisor VALUES ('150121033', '123456');
INSERT INTO StudentsOfAdvisor VALUES ('150121034', '123456');
INSERT INTO StudentsOfAdvisor VALUES ('150121035', '123456');
INSERT INTO StudentsOfAdvisor VALUES ('150122036', '654321');
INSERT INTO StudentsOfAdvisor VALUES ('150122037', '654321');
INSERT INTO StudentsOfAdvisor VALUES ('150122038', '654321');
INSERT INTO StudentsOfAdvisor VALUES ('150122039', '654321');
INSERT INTO StudentsOfAdvisor VALUES ('150122040', '654321');
INSERT INTO StudentsOfAdvisor VALUES ('150122041', '654321');
INSERT INTO StudentsOfAdvisor VALUES ('150122042', '654321');

CREATE TABLE CourseSection (
    	sectionID NVARCHAR(50),
	capacity INT,
	courseID NVARCHAR(50),
	lecturerSSN NVARCHAR(50),
	FOREIGN KEY (courseID) REFERENCES Course(courseID) on delete cascade,
	FOREIGN KEY (lecturerSSN) REFERENCES Lecturer(ssn) on delete set null
);
INSERT INTO CourseSection VALUES (1, 2, 'MATH1001', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'MATH1001', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE1200', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE1241', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE1241', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'PHYS1101', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'PHYS1101', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'ATA121', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'MBG1201', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'TRD121', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'MATH1002', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'MATH1002', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE1242', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE1242', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'PHYS1102', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'PHYS1102', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'ATA122', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'TRD122', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE2246', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE2246', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE2225', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE2225', NULL);
INSERT INTO CourseSection VALUES (3, 2, 'CSE2225', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'MATH2256', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'MATH2059', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE2023', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'EE2031', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'ECON2004', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'MATH2055', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE2260', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'EE2032', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE2138', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE2138', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'STAT2253', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE3055', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE3055', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE3033', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE3033', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE3063', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE3063', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE3215', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE3215', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'IE3081', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'IE3081', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'COM2202', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE3044', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE3044', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE3264', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE3264', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE3038', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE3038', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE3048', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'IE3235', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE4074', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE4074', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE4219', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE4219', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE4288', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE4288', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'ISG121', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'ISG122', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE4075', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE4075', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE4034', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE4034', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE4061', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE4061', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'CSE4217', NULL);
INSERT INTO CourseSection VALUES (2, 2, 'CSE4217', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'NTE1003', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'YDA1001', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'HSS3002', NULL);
INSERT INTO CourseSection VALUES (1, 2, 'MGT1021', NULL);


CREATE TABLE TimeSlot(
	id INT PRIMARY KEY,
	timeInterval NVARCHAR(50),
	day NVARCHAR(50),
	classroom NVARCHAR(50),
	sectionID NVARCHAR(50),
	FOREIGN KEY (sectionID) REFERENCES CourseSection(sectionID) on delete set null
);
CREATE TABLE Transcript(
    studentID NVARCHAR(50) PRIMARY KEY,
	completedCourses NVARCHAR(50),
	currentCourses NVARCHAR(50),
	waitedCourses NVARCHAR(50),
	currentCoursesSection NVARCHAR(50),
	waitedCoursesSection NVARCHAR(50),
	FOREIGN KEY (studentID) REFERENCES Student(studentID) on delete set null
);
