CREATE DATABASE OTUZBIR

USE OTUZBIR

CREATE TABLE Course(
   courseID NVARCHAR(50) PRIMARY KEY NOT NULL,
   name NVARCHAR(50) NOT NULL,
   credit INT,
   prerequisiteID NVARCHAR(50),
   courseType NVARCHAR(3)
)
INSERT INTO Course VALUES ('CSE1200', 'Computer Programming I',6,NULL,'m')
INSERT INTO Course VALUES ('MATH1001', 'Calculus I',6,NULL,'m')
INSERT INTO Course VALUES ('CSE1241', 'Introduction to Computer Engineering',4,NULL,'m')
INSERT INTO Course VALUES ('PHYS1101', 'Physics I',4,NULL,'m')
INSERT INTO Course VALUES ('ATA121', 'Atatürks Prin. & History I' ,2,NULL,'m')
INSERT INTO Course VALUES ('MBG1201', 'Introduction to Modern Biology',2,NULL,'m')
INSERT INTO Course VALUES ('TRD121', 'Turkish Language I',2,NULL,'m')
INSERT INTO Course VALUES ('MATH1002', 'Calculus II',6,NULL,'m')
INSERT INTO Course VALUES ('CSE1242', 'Computer Programming II',6,'CSE1241','m')
INSERT INTO Course VALUES ('PYHS1102', 'Physics II',4,NULL,'m')
INSERT INTO Course VALUES ('ATA122', 'Atatürks Prin. & History',2,NULL,'m')
INSERT INTO Course VALUES ('MATH2256', 'Linear Algebra for Comp. Eng.',5,NULL,'m')

CREATE TABLE Mandatory(
	courseID NVARCHAR(50) PRIMARY KEY,
	year INT,
	FOREIGN KEY (CourseID) REFERENCES Course(CourseID) ON DELETE CASCADE
)

INSERT INTO Mandatory VALUES ('MATH1001',1)
INSERT INTO Mandatory VALUES ('CSE1200',1)
INSERT INTO Mandatory VALUES ('CSE1241',1)
INSERT INTO Mandatory VALUES ('PHYS1101',1)
INSERT INTO Mandatory VALUES ('ATA121',1)
INSERT INTO Mandatory VALUES ('TRD121',1)
INSERT INTO Mandatory VALUES ('MBG1201',1)

CREATE TABLE NTE(
	courseID NVARCHAR(50),
	FOREIGN KEY (courseID) REFERENCES Course(courseID) ON DELETE CASCADE
)
CREATE TABLE TE(
	courseID NVARCHAR(50),
	year INT,
	FOREIGN KEY (courseID) REFERENCES Course(courseID) ON DELETE CASCADE
)

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
    FOREIGN KEY (ssn) REFERENCES Lecturer(ssn)
);

CREATE TABLE Student (
    studentID NVARCHAR(50) PRIMARY KEY,
    name NVARCHAR(50),
    surname NVARCHAR(50),
    gender CHAR,
    birthDate DATE,
    advisorID NVARCHAR(50),
	foreign key (advisorID) references Advisor(advisorID)
);

CREATE TABLE CourseSection (
    sectionID NVARCHAR(50) PRIMARY KEY,
	capacity INT,
	courseID NVARCHAR(50),
	lecturerSSN NVARCHAR(50),
	FOREIGN KEY (courseID) REFERENCES Course(courseID),
	FOREIGN KEY (lecturerSSN) REFERENCES Lecturer(ssn)
)
CREATE TABLE TimeSlot(
	id INT PRIMARY KEY,
	timeInterval NVARCHAR(50),
	day NVARCHAR(50),
	classroom NVARCHAR(50),
	sectionID NVARCHAR(50),
	FOREIGN KEY (sectionID) REFERENCES CourseSection(sectionID)
)
CREATE TABLE Transcript(
    studentID NVARCHAR(50) PRIMARY KEY,
	completedCourses NVARCHAR(50),
	currentCourses NVARCHAR(50),
	waitedCourses NVARCHAR(50),
	currentCoursesSection NVARCHAR(50),
	waitedCoursesSection NVARCHAR(50),
	FOREIGN KEY (studentID) REFERENCES Student(studentID)
)

