CREATE TABLE User(
  UserID NVARCHAR(50) PRIMARY KEY NOT NULL,
  Password NVARCHAR(50) NOT NULL,
  userType CHAR(50)
);

INSERT INTO User VALUES('150121031', 'abc123', 'S');
INSERT INTO User VALUES('150121032', 'password2', 'S');
INSERT INTO User VALUES('150121033', 'password3', 'S');
INSERT INTO User VALUES('150121034', 'password4', 'S');
INSERT INTO User VALUES('150121035', 'password5', 'S');
INSERT INTO User VALUES('150122036', 'password6', 'S');
INSERT INTO User VALUES('150122037', 'password7', 'S');
INSERT INTO User VALUES('150122038', 'password8', 'S');
INSERT INTO User VALUES('150122039', 'password9', 'S');
INSERT INTO User VALUES('150122040', 'password10', 'S');
INSERT INTO User VALUES('150122041', 'password11', 'S');
INSERT INTO User VALUES('150122042', 'password12', 'S');
INSERT INTO User VALUES('123456', 'ganiz123', 'A');
INSERT INTO User VALUES('654321', 'sanem123', 'A');
INSERT INTO User VALUES('96321', 'arda123', 'H');
INSERT INTO User VALUES('85213', 'esra123', 'H');
INSERT INTO User VALUES('1453', 'volkan123', 'D');
INSERT INTO User VALUES('1071','anastasya123','D');




CREATE TABLE Course(
   courseID NVARCHAR(50) PRIMARY KEY NOT NULL,
   name NVARCHAR(50) NOT NULL,
   credit INT,
   prerequisiteID NVARCHAR(50),
   courseType NVARCHAR(3),
   semester int
);
-- Mandatory Courses
INSERT INTO Course VALUES ('MATH1001', 'Calculus I', 6, NULL, 'm',1);
INSERT INTO Course VALUES ('CSE1200', 'Introduction to Computer Engineering', 4, NULL, 'm',1);
INSERT INTO Course VALUES ('CSE1241', 'Computer Programming I', 6, NULL, 'm',1);
INSERT INTO Course VALUES ('PHYS1101', 'Physics I', 4, NULL, 'm',1);
INSERT INTO Course VALUES ('ATA121', 'Atatürk s Prin. & History I', 2, NULL, 'm',1);
INSERT INTO Course VALUES ('MBG1201', 'Introduction to Modern Biology', 2, NULL, 'm',1);
INSERT INTO Course VALUES ('TRD121', 'Turkish Language I', 2, NULL, 'm',1);
INSERT INTO Course VALUES ('MATH1002', 'Calculus II', 6, NULL, 'm',1);
INSERT INTO Course VALUES ('CSE1242', 'Computer Programming II', 6, 'CSE1241', 'm',1);
INSERT INTO Course VALUES ('PHYS1102', 'Physics II', 4, NULL, 'm',1);
INSERT INTO Course VALUES ('ATA122', 'Atatürk s Prin. & History II', 2, NULL, 'm',1);
INSERT INTO Course VALUES ('MATH2256', 'Linear Algebra for Comp. Eng.', 5, NULL, 'm',2);
INSERT INTO Course VALUES ('TRD122', 'Turkish Language II', 2, NULL, 'm',1);
INSERT INTO Course VALUES ('MATH2059', 'Numerical Methods', 4, 'MATH1001', 'm',2);
INSERT INTO Course VALUES ('CSE2225', 'Data Structures', 7, 'CSE1242', 'm',2);
INSERT INTO Course VALUES ('CSE2023', 'Discrete Comp. Structures', 6, NULL, 'm',2);
INSERT INTO Course VALUES ('EE2031', 'Electric Circuits', 5, 'PHYS1102', 'm',2);
INSERT INTO Course VALUES ('ECON2004', 'Engineering Economy', 4, NULL, 'm',2);
INSERT INTO Course VALUES ('MATH2055', 'Differential Equations', 4, NULL, 'm',2);
INSERT INTO Course VALUES ('CSE2246', 'Analysis of Algorithms', 6, 'CSE2225', 'm',2);
INSERT INTO Course VALUES ('CSE2260', 'Principles of Prog. Languages', 6, 'CSE1242', 'm',2);
INSERT INTO Course VALUES ('EE2032', 'Electronics', 5, 'EE2031', 'm',2);
INSERT INTO Course VALUES ('CSE2138', 'Systems Programming', 7, NULL, 'm',2);
INSERT INTO Course VALUES ('STAT2253', 'Prob. and Stat. for Comp. Eng.', 5, NULL, 'm',2);
INSERT INTO Course VALUES ('CSE3055', 'Database Systems', 7, 'CSE2225', 'm',3);
INSERT INTO Course VALUES ('CSE3033', 'Operating Systems', 7, 'CSE2225', 'm',3);
INSERT INTO Course VALUES ('CSE3063', 'Object-Oriented Software Desg.', 5, 'CSE1242', 'm',3);
INSERT INTO Course VALUES ('CSE3215', 'Digital Logic Design', 6, NULL, 'm',3);
INSERT INTO Course VALUES ('IE3081', 'Modeling and Disc. Simulation', 4, 'STAT2253', 'm',3);
INSERT INTO Course VALUES ('COM2202', 'Tech. Comm. & Entrepreneurship', 2, NULL, 'm',3);
INSERT INTO Course VALUES ('CSE3044', 'Software Engineering', 7, 'CSE3055', 'm',3);
INSERT INTO Course VALUES ('CSE3264', 'Formal Lang. & Auto. Theory', 5, 'CSE2023', 'm',3);
INSERT INTO Course VALUES ('CSE3038', 'Computer Organization', 7, 'CSE2138', 'm',3);
INSERT INTO Course VALUES ('CSE3048', 'Int. to Signals and Systems', 5, 'MATH2055', 'm',3);
INSERT INTO Course VALUES ('IE3235', 'Operations Research', 4, 'MATH2256', 'm',3);
INSERT INTO Course VALUES ('CSE4297', 'Engineering Project I', 5, NULL, 'm',4);
INSERT INTO Course VALUES ('CSE4074', 'Computer Networks', 5, NULL, 'm',4);
INSERT INTO Course VALUES ('CSE4219', 'Prin. of Embed. System Design', 6, 'CSE3038', 'm',4);
INSERT INTO Course VALUES ('ISG121', 'Work Safety I', 2, NULL, 'm',4);
INSERT INTO Course VALUES ('CSE4288', 'Int. to Machine Learning', 5, 'STAT2253', 'm',4);
INSERT INTO Course VALUES ('CSE4298', 'Engineering Project II', 5, 'CSE4297', 'm',4);
INSERT INTO Course VALUES ('ISG122', 'Work Safety II', 2, NULL, 'm',4);

-- Technical Elective Courses
INSERT INTO Course VALUES ('CSE4075', 'Wireless & Mobile Networks', 5, 'CSE4074', 'te',4);
INSERT INTO Course VALUES ('CSE4034', 'Advanced Unix Programming', 5, 'CSE3033', 'te',4);
INSERT INTO Course VALUES ('CSE4061', 'Compiler Design', 5, 'CSE3264', 'te',4);
INSERT INTO Course VALUES ('CSE4217', 'Microprocessors', 5, 'CSE3038', 'te',4);

-- Non-Technical Elective Courses
INSERT INTO Course VALUES ('NTE1003', 'The Culture of Radiation Safety', 3, NULL, 'nte',0);
INSERT INTO Course VALUES ('YDA1001', 'German for Beginners', 3, NULL, 'nte',0);
INSERT INTO Course VALUES ('HSS3002', 'Ethics in Engineering and Science', 3, NULL, 'nte',0);
INSERT INTO Course VALUES ('MGT1021', 'Design, Innovation and Entrepreneurship', 3, NULL, 'nte',0);


CREATE TABLE Lecturer (
    ssn NVARCHAR(50) PRIMARY KEY,
    name NVARCHAR(50),
    surname NVARCHAR(50),
    birthdate DATE,
    gender CHAR(1)
);
INSERT INTO Lecturer VALUES ('123456', 'Murat Can', 'Ganiz', '1970-01-01', 'M');
INSERT INTO Lecturer VALUES ('654321', 'Sanem', 'Yilmaz', '1975-01-01', 'F');
INSERT INTO Lecturer VALUES ('789012', 'Ahmet', 'Kaya', '1980-02-15', 'M');
INSERT INTO Lecturer VALUES ('890123', 'Mehmet', 'Demir', '1985-06-20', 'M');
INSERT INTO Lecturer VALUES ('901234', 'Ali', 'Celik', '1978-11-05', 'M');
INSERT INTO Lecturer VALUES ('012345', 'Mustafa', 'Akin', '1990-03-10', 'M');
INSERT INTO Lecturer VALUES ('567890', 'Ayse', 'Aydin', '1982-07-22', 'F');
INSERT INTO Lecturer VALUES ('678901', 'Fatma', 'Sen', '1988-09-12', 'F');
INSERT INTO Lecturer VALUES ('789013', 'Elif', 'Dogan', '1992-01-30', 'F');
INSERT INTO Lecturer VALUES ('890124', 'Zeynep', 'Korkmaz', '1979-04-18', 'F');
INSERT INTO Lecturer VALUES ('96321', 'Arda', 'Ülkü', '1987-04-21', 'M');
INSERT INTO Lecturer VALUES ('85213', 'Esra', 'Bilir', '1991-08-10', 'F');
INSERT INTO Lecturer VALUES ('1453', 'Volkan', 'Bakırel', '1985-02-14', 'M');
INSERT INTO Lecturer VALUES ('1071', 'Anastasya', 'Aras', '1992-11-07', 'F');


CREATE TABLE Student (
    studentID NVARCHAR(50) PRIMARY KEY,
    name NVARCHAR(50),
    surname NVARCHAR(50),
    gender CHAR,
    birthDate DATE,
    advisorID NVARCHAR(50),
	semester int,
	foreign key (advisorID) references Lecturer(SSN) on delete set null
);
INSERT INTO Student VALUES ('150121031', 'Hasan', 'Erzincanli', 'M', '2002-09-18', '123456',3);
INSERT INTO Student VALUES ('150121032', 'Enis', 'Destan', 'M', '1999-02-14', '123456',3);
INSERT INTO Student VALUES ('150121033', 'Oguz', 'Aydin', 'M', '2001-03-12', '123456',3);
INSERT INTO Student VALUES ('150121034', 'Aslı', 'Cakir', 'F', '2001-05-20', '123456',2);
INSERT INTO Student VALUES ('150121035', 'Necip', 'Uysal', 'M', '2002-06-30', '123456',2);
INSERT INTO Student VALUES ('150122036', 'Berkan', 'Kutlu', 'M', '2002-08-15', '654321',2);
INSERT INTO Student VALUES ('150122037', 'Ferdi', 'Kadioglu', 'M', '2001-10-22', '654321',4);
INSERT INTO Student VALUES ('150122038', 'Ceyda', 'Santos', 'F', '1998-12-05', '654321',4);
INSERT INTO Student VALUES ('150122039', 'Elif', 'Yildirim', 'F', '1999-04-17', '654321',4);
INSERT INTO Student VALUES ('150122040', 'Kerem', 'Akturkoglu', 'M', '2005-06-09', '654321',1);
INSERT INTO Student VALUES ('150122041', 'Baris', 'Alper', 'M', '2006-01-09', '654321',1);
INSERT INTO Student VALUES ('150122042', 'Baki', 'Mercimek', 'M', '2005-11-09', '654321',1);

create table StudentsOfAdvisor(
	studentID nvarchar(50) primary key,
	advisorID nvarchar(50),
	foreign key (studentID) references Student(studentID) on delete cascade,
	foreign key (advisorID) references Lecturer(SSN) on delete cascade
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
    sectionID NVARCHAR(50) PRIMARY KEY,
	capacity INT,
	courseID NVARCHAR(50),
	lecturerSSN NVARCHAR(50),
	FOREIGN KEY (courseID) REFERENCES Course(courseID) on delete cascade,
	FOREIGN KEY (lecturerSSN) REFERENCES Lecturer(ssn) on delete set null
);
INSERT INTO CourseSection VALUES ('MATH1001.1', 2, 'MATH1001', '123456');
INSERT INTO CourseSection VALUES ('MATH1001.2', 2, 'MATH1001', '654321');
INSERT INTO CourseSection VALUES ('CSE1200.1', 2, 'CSE1200', '789012');
INSERT INTO CourseSection VALUES ('CSE1241.1', 2, 'CSE1241', '890123');
INSERT INTO CourseSection VALUES ('CSE1241.2', 2, 'CSE1241', '901234');
INSERT INTO CourseSection VALUES ('PHYS1101.1', 2, 'PHYS1101', '012345');
INSERT INTO CourseSection VALUES ('PHYS1101.2', 2, 'PHYS1101', '567890');
INSERT INTO CourseSection VALUES ('ATA121.1', 2, 'ATA121', '678901');
INSERT INTO CourseSection VALUES ('MBG1201.1', 2, 'MBG1201', '789013');
INSERT INTO CourseSection VALUES ('TRD121.1', 2, 'TRD121', '890124');
INSERT INTO CourseSection VALUES ('MATH1002.1', 2, 'MATH1002', '123456');
INSERT INTO CourseSection VALUES ('MATH1002.2', 2, 'MATH1002', '654321');
INSERT INTO CourseSection VALUES ('CSE1242.1', 2, 'CSE1242', '789012');
INSERT INTO CourseSection VALUES ('CSE1242.2', 2, 'CSE1242', '890123');
INSERT INTO CourseSection VALUES ('PHYS1102.1', 2, 'PHYS1102', '901234');
INSERT INTO CourseSection VALUES ('PHYS1102.2', 2, 'PHYS1102', '012345');
INSERT INTO CourseSection VALUES ('ATA122.1', 2, 'ATA122', '567890');
INSERT INTO CourseSection VALUES ('TRD122.1', 2, 'TRD122', '678901');
INSERT INTO CourseSection VALUES ('CSE2246.1', 2, 'CSE2246', '789013');
INSERT INTO CourseSection VALUES ('CSE2246.2', 2, 'CSE2246', '890124');
INSERT INTO CourseSection VALUES ('CSE2225.1', 2, 'CSE2225', '123456');
INSERT INTO CourseSection VALUES ('CSE2225.2', 2, 'CSE2225', '654321');
INSERT INTO CourseSection VALUES ('CSE2225.3', 2, 'CSE2225', '789012');
INSERT INTO CourseSection VALUES ('MATH2256.1', 2, 'MATH2256', '890123');
INSERT INTO CourseSection VALUES ('MATH2059.1', 2, 'MATH2059', '901234');
INSERT INTO CourseSection VALUES ('CSE2023.1', 2, 'CSE2023', '012345');
INSERT INTO CourseSection VALUES ('EE2031.1', 2, 'EE2031', '567890');
INSERT INTO CourseSection VALUES ('ECON2004.1', 2, 'ECON2004', '678901');
INSERT INTO CourseSection VALUES ('MATH2055.1', 2, 'MATH2055', '789013');
INSERT INTO CourseSection VALUES ('CSE2260.1', 2, 'CSE2260', '890124');
INSERT INTO CourseSection VALUES ('EE2032.1', 2, 'EE2032', '123456');
INSERT INTO CourseSection VALUES ('CSE2138.1', 2, 'CSE2138', '654321');
INSERT INTO CourseSection VALUES ('CSE2138.2', 2, 'CSE2138', '789012');
INSERT INTO CourseSection VALUES ('STAT2253.1', 2, 'STAT2253', '890123');
INSERT INTO CourseSection VALUES ('CSE3055.1', 2, 'CSE3055', '901234');
INSERT INTO CourseSection VALUES ('CSE3055.2', 2, 'CSE3055', '012345');
INSERT INTO CourseSection VALUES ('CSE3033.1', 2, 'CSE3033', '567890');
INSERT INTO CourseSection VALUES ('CSE3033.2', 2, 'CSE3033', '678901');
INSERT INTO CourseSection VALUES ('CSE3063.1', 2, 'CSE3063', '789013');
INSERT INTO CourseSection VALUES ('CSE3063.2', 2, 'CSE3063', '890124');
INSERT INTO CourseSection VALUES ('CSE3215.1', 2, 'CSE3215', '123456');
INSERT INTO CourseSection VALUES ('CSE3215.2', 2, 'CSE3215', '654321');
INSERT INTO CourseSection VALUES ('IE3081.1', 2, 'IE3081', '789012');
INSERT INTO CourseSection VALUES ('IE3081.2', 2, 'IE3081', '890123');
INSERT INTO CourseSection VALUES ('COM2202.1', 2, 'COM2202', '901234');
INSERT INTO CourseSection VALUES ('CSE3044.1', 2, 'CSE3044', '012345');
INSERT INTO CourseSection VALUES ('CSE3044.2', 2, 'CSE3044', '567890');
INSERT INTO CourseSection VALUES ('CSE3264.1', 2, 'CSE3264', '678901');
INSERT INTO CourseSection VALUES ('CSE3264.2', 2, 'CSE3264', '789013');
INSERT INTO CourseSection VALUES ('CSE3038.1', 2, 'CSE3038', '890124');
INSERT INTO CourseSection VALUES ('CSE3038.2', 2, 'CSE3038', '123456');
INSERT INTO CourseSection VALUES ('CSE3048.1', 2, 'CSE3048', '654321');
INSERT INTO CourseSection VALUES ('IE3235.1', 2, 'IE3235', '789012');
INSERT INTO CourseSection VALUES ('CSE4074.1', 2, 'CSE4074', '890123');
INSERT INTO CourseSection VALUES ('CSE4074.2', 2, 'CSE4074', '901234');
INSERT INTO CourseSection VALUES ('CSE4219.1', 2, 'CSE4219', '012345');
INSERT INTO CourseSection VALUES ('CSE4219.2', 2, 'CSE4219', '567890');
INSERT INTO CourseSection VALUES ('CSE4288.1', 2, 'CSE4288', '678901');
INSERT INTO CourseSection VALUES ('CSE4288.2', 2, 'CSE4288', '789013');
INSERT INTO CourseSection VALUES ('CSE4075.1', 2, 'CSE4075', '890124');
INSERT INTO CourseSection VALUES ('CSE4075.2', 2, 'CSE4075', '123456');
INSERT INTO CourseSection VALUES ('ISG121.1', 2, 'ISG121', '654321');
INSERT INTO CourseSection VALUES ('ISG122.1', 2, 'ISG122', '789012');
INSERT INTO CourseSection VALUES ('CSE4034.1', 2, 'CSE4034', '890123');
INSERT INTO CourseSection VALUES ('CSE4034.2', 2, 'CSE4034', '901234');
INSERT INTO CourseSection VALUES ('CSE4061.1', 2, 'CSE4061', '012345');
INSERT INTO CourseSection VALUES ('CSE4061.2', 2, 'CSE4061', '567890');
INSERT INTO CourseSection VALUES ('CSE4217.1', 2, 'CSE4217', '678901');
INSERT INTO CourseSection VALUES ('CSE4217.2', 2, 'CSE4217', '789013');
INSERT INTO CourseSection VALUES ('NTE1003.1', 2, 'NTE1003', '890124');
INSERT INTO CourseSection VALUES ('YDA1001.1', 2, 'YDA1001', '123456');
INSERT INTO CourseSection VALUES ('HSS3002.1', 2, 'HSS3002', '654321');
INSERT INTO CourseSection VALUES ('MGT1021.1', 2, 'MGT1021', '789012');

CREATE TABLE TimeSlot(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    timeInterval NVARCHAR(50),
    day NVARCHAR(50),
    classroom NVARCHAR(50),
    sectionID NVARCHAR(50),
    FOREIGN KEY (sectionID) REFERENCES CourseSection(sectionID) ON DELETE SET NULL
);

INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Monday', 'M2Z10', 'MATH1001.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('11:30-12:20', 'Monday', 'M2Z10', 'CSE1200.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('12:30-13:20', 'Tuesday', 'M2Z10', 'CSE1241.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('13:30-14:20', 'Tuesday', 'M2Z10', 'CSE1241.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('14:30-15:20', 'Monday', 'M2Z10', 'PHYS1101.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('15:30-16:20', 'Monday', 'M2Z10', 'PHYS1101.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Tuesday', 'M2Z10', 'ATA121.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Tuesday', 'M2Z10', 'MBG1201.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('8:30-9:20', 'Wednesday', 'M2Z10', 'TRD121.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Thursday', 'M2Z10', 'MATH1002.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Thursday', 'M2Z10', 'MATH1002.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('14:30-15:20', 'Tuesday', 'M2Z10', 'CSE1242.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('15:30-16:20', 'Tuesday', 'M2Z10', 'CSE1242.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('14:30-15:20', 'Friday', 'M2Z10', 'PHYS1102.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('15:30-16:20', 'Friday', 'M2Z10', 'PHYS1102.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('11:30-12:20', 'Thursday', 'M2Z10', 'ATA122.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('12:30-13:20', 'Thursday', 'M2Z10', 'TRD122.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('8:30-9:20', 'Monday', 'M2Z09', 'CSE2246.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Monday', 'M2Z09', 'CSE2246.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Monday', 'M2Z09', 'CSE2225.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('11:30-12:20', 'Monday', 'M2Z09', 'CSE2225.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('12:30-13:20', 'Monday', 'M2Z09', 'CSE2225.3');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('14:30-15:20', 'Monday', 'M2Z09', 'MATH2256.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Tuesday', 'M2Z09', 'MATH2059.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Tuesday', 'M2Z09', 'CSE2023.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('11:30-12:20', 'Tuesday', 'M2Z09', 'EE2031.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('13:30-14:20', 'Tuesday', 'M2Z09', 'ECON2004.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('8:30-9:20', 'Wednesday', 'M2Z09', 'MATH2055.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Wednesday', 'M2Z09', 'CSE2260.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Wednesday', 'M2Z09', 'EE2032.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('11:30-12:20', 'Thursday', 'M2Z09', 'CSE2138.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('12:30-13:20', 'Thursday', 'M2Z09', 'CSE2138.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('13:30-14:20', 'Friday', 'M2Z09', 'STAT2253.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Monday', 'M2Z11', 'CSE3055.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Monday', 'M2Z11', 'CSE3055.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Friday', 'M2Z11', 'CSE3033.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Friday', 'M2Z11', 'CSE3033.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Wednesday', 'M2Z11', 'CSE3063.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Wednesday', 'M2Z11', 'CSE3063.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Thursday', 'M2Z11', 'CSE3215.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Thursday', 'M2Z11', 'CSE3215.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('11:30-12:20', 'Thursday', 'M2Z11', 'IE3081.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Tuesday', 'M2Z11', 'IE3081.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('13:30-14:20', 'Friday', 'M2Z11', 'COM2202.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('14:30-15:20', 'Friday', 'M2Z11', 'CSE3044.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('15:30-16:20', 'Friday', 'M2Z11', 'CSE3044.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('11:30-12:20', 'Friday', 'M2Z11', 'CSE3264.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Wednesday', 'M2Z11', 'CSE3264.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('11:30-12:20', 'Wednesday', 'M2Z11', 'CSE3038.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('12:30-13:20', 'Wednesday', 'M2Z11', 'CSE3038.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('13:30-12:20', 'Wednesday', 'M2Z11', 'CSE3048.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('12:30-13:20', 'Thursday', 'M2Z11', 'IE3235.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('8:30-9:20', 'Monday', 'M2Z12', 'CSE4074.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Monday', 'M2Z12', 'CSE4074.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Friday', 'M2Z12', 'CSE4219.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Friday', 'M2Z12', 'CSE4219.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Tuesday', 'M2Z12', 'CSE4288.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Tuesday', 'M2Z12', 'CSE4288.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('11:30-12:20', 'Tuesday', 'M2Z12', 'CSE4075.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('12:30-13:20', 'Tuesday', 'M2Z12', 'CSE4075.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Thursday', 'M2Z12', 'ISG121.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Thursday', 'M2Z12', 'ISG122.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('8:30-9:20', 'Wednesday', 'M2Z12', 'CSE4034.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Wednesday', 'M2Z12', 'CSE4034.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('10:30-11:20', 'Wednesday', 'M2Z12', 'CSE4061.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('11:30-12:20', 'Wednesday', 'M2Z12', 'CSE4061.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('12:30-13:20', 'Wednesday', 'M2Z12', 'CSE4217.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('13:30-12:20', 'Wednesday', 'M2Z12', 'CSE4217.2');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('9:30-10:20', 'Thursday', 'M2Z04', 'NTE1003.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('15:30-16:20', 'Friday', 'M2Z04', 'YDA1001.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('12:30-13:20', 'Tuesday', 'M2Z04', 'HSS3002.1');
INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID) VALUES ('11:30-12:20', 'Monday', 'M2Z04', 'MGT1021.1');



CREATE TABLE CompletedCourse(
	studentID NVARCHAR(50),
	courseID NVARCHAR(50),
	grade NVARCHAR(50),
	foreign key (studentID) references Student(studentID) on delete cascade,
	foreign key (courseID) references Course(courseID) on delete cascade
);
INSERT INTO CompletedCourse VALUES ('150121031', 'MATH1001', 'BB');
INSERT INTO CompletedCourse VALUES ('150121031', 'CSE1200', 'CC');
INSERT INTO CompletedCourse VALUES ('150121031', 'CSE1241', 'BB');
INSERT INTO CompletedCourse VALUES ('150121031', 'PHYS1101', 'AA');
INSERT INTO CompletedCourse VALUES ('150121031', 'ATA121', 'AA');
INSERT INTO CompletedCourse VALUES ('150121031', 'MBG1201', 'BA');
INSERT INTO CompletedCourse VALUES ('150121031', 'TRD121', 'DC');
INSERT INTO CompletedCourse VALUES ('150121031', 'MATH1002', 'BA');
INSERT INTO CompletedCourse VALUES ('150121031', 'CSE1242', 'CB');
INSERT INTO CompletedCourse VALUES ('150121031', 'PHYS1102', 'BA');
INSERT INTO CompletedCourse VALUES ('150121031', 'ATA122', 'AA');
INSERT INTO CompletedCourse VALUES ('150121031', 'MATH2256', 'BB');
INSERT INTO CompletedCourse VALUES ('150121031', 'TRD122', 'AA');
INSERT INTO CompletedCourse VALUES ('150121031', 'MATH2059', 'BB');
INSERT INTO CompletedCourse VALUES ('150121031', 'CSE2023', 'CB');
INSERT INTO CompletedCourse VALUES ('150121031', 'EE2031', 'CB');
INSERT INTO CompletedCourse VALUES ('150121031', 'ECON2004', 'CC');
INSERT INTO CompletedCourse VALUES ('150121031', 'MATH2055', 'BA');
INSERT INTO CompletedCourse VALUES ('150121031', 'CSE2260', 'CB');
INSERT INTO CompletedCourse VALUES ('150121031', 'EE2032', 'BA');
INSERT INTO CompletedCourse VALUES ('150121031', 'CSE2138', 'CB');
INSERT INTO CompletedCourse VALUES ('150121031', 'STAT2253', 'BB');

INSERT INTO CompletedCourse VALUES ('150121032', 'MATH1001', 'CC');
INSERT INTO CompletedCourse VALUES ('150121032', 'CSE1200', 'CC');
INSERT INTO CompletedCourse VALUES ('150121032', 'CSE1241', 'BB');
INSERT INTO CompletedCourse VALUES ('150121032', 'PHYS1101', 'BA');
INSERT INTO CompletedCourse VALUES ('150121032', 'ATA121', 'AA');
INSERT INTO CompletedCourse VALUES ('150121032', 'MBG1201', 'BA');
INSERT INTO CompletedCourse VALUES ('150121032', 'TRD121', 'DC');
INSERT INTO CompletedCourse VALUES ('150121032', 'MATH1002', 'CB');
INSERT INTO CompletedCourse VALUES ('150121032', 'CSE1242', 'CC');
INSERT INTO CompletedCourse VALUES ('150121032', 'PHYS1102', 'BA');
INSERT INTO CompletedCourse VALUES ('150121032', 'ATA122', 'AA');
INSERT INTO CompletedCourse VALUES ('150121032', 'MATH2256', 'BB');
INSERT INTO CompletedCourse VALUES ('150121032', 'TRD122', 'AA');
INSERT INTO CompletedCourse VALUES ('150121032', 'MATH2059', 'CB');
INSERT INTO CompletedCourse VALUES ('150121032', 'CSE2023', 'CB');
INSERT INTO CompletedCourse VALUES ('150121032', 'EE2031', 'CC');
INSERT INTO CompletedCourse VALUES ('150121032', 'CSE2225', 'BB');
INSERT INTO CompletedCourse VALUES ('150121032', 'ECON2004', 'CC');
INSERT INTO CompletedCourse VALUES ('150121032', 'MATH2055', 'DC');
INSERT INTO CompletedCourse VALUES ('150121032', 'CSE2260', 'CC');
INSERT INTO CompletedCourse VALUES ('150121032', 'EE2032', 'BA');
INSERT INTO CompletedCourse VALUES ('150121032', 'CSE2138', 'CC');
INSERT INTO CompletedCourse VALUES ('150121032', 'STAT2253', 'BB');

INSERT INTO CompletedCourse VALUES ('150121033', 'MATH1001', 'CC');
INSERT INTO CompletedCourse VALUES ('150121033', 'CSE1200', 'CC');
INSERT INTO CompletedCourse VALUES ('150121033', 'CSE1241', 'BB');
INSERT INTO CompletedCourse VALUES ('150121033', 'PHYS1101', 'BA');
INSERT INTO CompletedCourse VALUES ('150121033', 'ATA121', 'AA');
INSERT INTO CompletedCourse VALUES ('150121033', 'MBG1201', 'BA');
INSERT INTO CompletedCourse VALUES ('150121033', 'TRD121', 'DC');
INSERT INTO CompletedCourse VALUES ('150121033', 'MATH1002', 'CB');
INSERT INTO CompletedCourse VALUES ('150121033', 'CSE1242', 'CC');
INSERT INTO CompletedCourse VALUES ('150121033', 'PHYS1102', 'BA');
INSERT INTO CompletedCourse VALUES ('150121033', 'ATA122', 'AA');
INSERT INTO CompletedCourse VALUES ('150121033', 'MATH2256', 'BB');
INSERT INTO CompletedCourse VALUES ('150121033', 'TRD122', 'AA');
INSERT INTO CompletedCourse VALUES ('150121033', 'MATH2059', 'CC');
INSERT INTO CompletedCourse VALUES ('150121033', 'CSE2023', 'CC');
INSERT INTO CompletedCourse VALUES ('150121033', 'EE2031', 'CB');
INSERT INTO CompletedCourse VALUES ('150121033', 'CSE2225', 'CB');
INSERT INTO CompletedCourse VALUES ('150121033', 'ECON2004', 'CB');
INSERT INTO CompletedCourse VALUES ('150121033', 'MATH2055', 'CC');
INSERT INTO CompletedCourse VALUES ('150121033', 'CSE2260', 'CB');
INSERT INTO CompletedCourse VALUES ('150121033', 'EE2032', 'BB');
INSERT INTO CompletedCourse VALUES ('150121033', 'CSE2138', 'CB');
INSERT INTO CompletedCourse VALUES ('150121033', 'CSE2246', 'DC');
INSERT INTO CompletedCourse VALUES ('150121033', 'STAT2253', 'BA');

INSERT INTO CompletedCourse VALUES ('150121034', 'MATH1001', 'BA');
INSERT INTO CompletedCourse VALUES ('150121034', 'CSE1200', 'CB');
INSERT INTO CompletedCourse VALUES ('150121034', 'CSE1241', 'CC');
INSERT INTO CompletedCourse VALUES ('150121034', 'PHYS1101', 'AA');
INSERT INTO CompletedCourse VALUES ('150121034', 'ATA121', 'AA');
INSERT INTO CompletedCourse VALUES ('150121034', 'MBG1201', 'BB');
INSERT INTO CompletedCourse VALUES ('150121034', 'TRD121', 'CC');
INSERT INTO CompletedCourse VALUES ('150121034', 'MATH1002', 'BA');
INSERT INTO CompletedCourse VALUES ('150121034', 'CSE1242', 'CB');
INSERT INTO CompletedCourse VALUES ('150121034', 'ATA122', 'AA');
INSERT INTO CompletedCourse VALUES ('150121034', 'MATH2256', 'BB');
INSERT INTO CompletedCourse VALUES ('150121034', 'TRD122', 'AA');
INSERT INTO CompletedCourse VALUES ('150121034', 'MATH2059', 'BB');
INSERT INTO CompletedCourse VALUES ('150121034', 'CSE2225', 'CB');

INSERT INTO CompletedCourse VALUES ('150121035', 'MATH1001', 'BA');
INSERT INTO CompletedCourse VALUES ('150121035', 'CSE1200', 'CB');
INSERT INTO CompletedCourse VALUES ('150121035', 'CSE1241', 'CC');
INSERT INTO CompletedCourse VALUES ('150121035', 'PHYS1101', 'AA');
INSERT INTO CompletedCourse VALUES ('150121035', 'ATA121', 'AA');
INSERT INTO CompletedCourse VALUES ('150121035', 'MBG1201', 'BB');
INSERT INTO CompletedCourse VALUES ('150121035', 'TRD121', 'CB');
INSERT INTO CompletedCourse VALUES ('150121035', 'MATH1002', 'CC');
INSERT INTO CompletedCourse VALUES ('150121035', 'CSE1242', 'BB');
INSERT INTO CompletedCourse VALUES ('150121035', 'ATA122', 'AA');
INSERT INTO CompletedCourse VALUES ('150121035', 'PHYS1102', 'AA');
INSERT INTO CompletedCourse VALUES ('150121035', 'MATH2256', 'CB');
INSERT INTO CompletedCourse VALUES ('150121035', 'TRD122', 'BB');

INSERT INTO CompletedCourse VALUES ('150122036', 'MATH1001', 'BB');
INSERT INTO CompletedCourse VALUES ('150122036', 'CSE1200', 'CC');
INSERT INTO CompletedCourse VALUES ('150122036', 'CSE1241', 'CB');
INSERT INTO CompletedCourse VALUES ('150122036', 'PHYS1101', 'BB');
INSERT INTO CompletedCourse VALUES ('150122036', 'ATA121', 'BB');
INSERT INTO CompletedCourse VALUES ('150122036', 'MBG1201', 'BB');
INSERT INTO CompletedCourse VALUES ('150122036', 'TRD121', 'DC');
INSERT INTO CompletedCourse VALUES ('150122036', 'MATH1002', 'BB');
INSERT INTO CompletedCourse VALUES ('150122036', 'CSE1242', 'CC');
INSERT INTO CompletedCourse VALUES ('150122036', 'PHYS1102', 'CB');
INSERT INTO CompletedCourse VALUES ('150122036', 'ATA122', 'BA');
INSERT INTO CompletedCourse VALUES ('150122036', 'TRD122', 'BB');

INSERT INTO CompletedCourse VALUES ('150122037', 'MATH1001', 'BB');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE1200', 'CC');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE1241', 'CC');
INSERT INTO CompletedCourse VALUES ('150122037', 'PHYS1101', 'AA');
INSERT INTO CompletedCourse VALUES ('150122037', 'ATA121', 'BB');
INSERT INTO CompletedCourse VALUES ('150122037', 'MBG1201', 'AA');
INSERT INTO CompletedCourse VALUES ('150122037', 'TRD121', 'BA');
INSERT INTO CompletedCourse VALUES ('150122037', 'MATH1002', 'CB');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE1242', 'BB');
INSERT INTO CompletedCourse VALUES ('150122037', 'PHYS1102', 'CC');
INSERT INTO CompletedCourse VALUES ('150122037', 'ATA122', 'AA');
INSERT INTO CompletedCourse VALUES ('150122037', 'MATH2256', 'BA');
INSERT INTO CompletedCourse VALUES ('150122037', 'TRD122', 'DC');
INSERT INTO CompletedCourse VALUES ('150122037', 'MATH2059', 'BA');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE2225', 'DC');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE2023', 'DD');
INSERT INTO CompletedCourse VALUES ('150122037', 'EE2031', 'BA');
INSERT INTO CompletedCourse VALUES ('150122037', 'ECON2004', 'AA');
INSERT INTO CompletedCourse VALUES ('150122037', 'MATH2055', 'CC');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE2246', 'BB');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE2260', 'AA');
INSERT INTO CompletedCourse VALUES ('150122037', 'EE2032', 'CB');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE2138', 'CC');
INSERT INTO CompletedCourse VALUES ('150122037', 'STAT2253', 'BA');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE3055', 'CC');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE3033', 'BB');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE3063', 'BA');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE3215', 'AA');
INSERT INTO CompletedCourse VALUES ('150122037', 'IE3081', 'BA');
INSERT INTO CompletedCourse VALUES ('150122037', 'COM2202', 'DC');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE3044', 'BB');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE3264', 'CC');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE3038', 'CB');
INSERT INTO CompletedCourse VALUES ('150122037', 'CSE3048', 'BA');

INSERT INTO CompletedCourse VALUES ('150122038', 'MATH1001', 'BB');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE1200', 'CC');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE1241', 'CC');
INSERT INTO CompletedCourse VALUES ('150122038', 'PHYS1101', 'AA');
INSERT INTO CompletedCourse VALUES ('150122038', 'ATA121', 'BB');
INSERT INTO CompletedCourse VALUES ('150122038', 'MBG1201', 'AA');
INSERT INTO CompletedCourse VALUES ('150122038', 'TRD121', 'BA');
INSERT INTO CompletedCourse VALUES ('150122038', 'MATH1002', 'CB');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE1242', 'BB');
INSERT INTO CompletedCourse VALUES ('150122038', 'PHYS1102', 'CC');
INSERT INTO CompletedCourse VALUES ('150122038', 'ATA122', 'AA');
INSERT INTO CompletedCourse VALUES ('150122038', 'MATH2256', 'BA');
INSERT INTO CompletedCourse VALUES ('150122038', 'TRD122', 'DC');
INSERT INTO CompletedCourse VALUES ('150122038', 'MATH2059', 'BA');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE2225', 'DC');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE2023', 'DD');
INSERT INTO CompletedCourse VALUES ('150122038', 'EE2031', 'BA');
INSERT INTO CompletedCourse VALUES ('150122038', 'ECON2004', 'AA');
INSERT INTO CompletedCourse VALUES ('150122038', 'MATH2055', 'CC');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE2246', 'BB');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE2260', 'AA');
INSERT INTO CompletedCourse VALUES ('150122038', 'EE2032', 'CB');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE2138', 'CC');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE3055', 'CC');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE3033', 'BB');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE3063', 'BA');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE3215', 'AA');
INSERT INTO CompletedCourse VALUES ('150122038', 'COM2202', 'DC');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE3044', 'BB');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE3264', 'CC');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE3038', 'CB');
INSERT INTO CompletedCourse VALUES ('150122038', 'CSE3048', 'BA');

INSERT INTO CompletedCourse VALUES ('150122039', 'MATH1001', 'BB');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE1200', 'CC');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE1241', 'CC');
INSERT INTO CompletedCourse VALUES ('150122039', 'PHYS1101', 'AA');
INSERT INTO CompletedCourse VALUES ('150122039', 'ATA121', 'BB');
INSERT INTO CompletedCourse VALUES ('150122039', 'MBG1201', 'AA');
INSERT INTO CompletedCourse VALUES ('150122039', 'TRD121', 'BA');
INSERT INTO CompletedCourse VALUES ('150122039', 'MATH1002', 'CB');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE1242', 'BB');
INSERT INTO CompletedCourse VALUES ('150122039', 'PHYS1102', 'CC');
INSERT INTO CompletedCourse VALUES ('150122039', 'ATA122', 'AA');
INSERT INTO CompletedCourse VALUES ('150122039', 'MATH2256', 'BA');
INSERT INTO CompletedCourse VALUES ('150122039', 'TRD122', 'AA');
INSERT INTO CompletedCourse VALUES ('150122039', 'MATH2059', 'BB');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE2225', 'DC');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE2023', 'CC');
INSERT INTO CompletedCourse VALUES ('150122039', 'EE2031', 'AA');
INSERT INTO CompletedCourse VALUES ('150122039', 'ECON2004', 'BA');
INSERT INTO CompletedCourse VALUES ('150122039', 'MATH2055', 'CC');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE2246', 'CB');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE2260', 'AA');
INSERT INTO CompletedCourse VALUES ('150122039', 'EE2032', 'CB');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE2138', 'CC');
INSERT INTO CompletedCourse VALUES ('150122039', 'STAT2253', 'BA');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE3055', 'CC');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE3033', 'BB');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE3063', 'BA');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE3215', 'AA');
INSERT INTO CompletedCourse VALUES ('150122039', 'IE3081', 'BA');
INSERT INTO CompletedCourse VALUES ('150122039', 'IE3235', 'DD');
INSERT INTO CompletedCourse VALUES ('150122039', 'COM2202', 'DC');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE3044', 'BB');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE3264', 'CC');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE3038', 'CB');
INSERT INTO CompletedCourse VALUES ('150122039', 'CSE3048', 'BA');

INSERT INTO CompletedCourse VALUES ('150122041', 'MATH1001', 'BB');

create table CurrentCourse(
	studentID NVARCHAR(50) ,
	courseID NVARCHAR(50),
	foreign key (studentID) references Student(studentID) on delete cascade,
	foreign key (courseID) references Course(courseID) on delete cascade
);

INSERT INTO CurrentCourse VALUES ('150121031', 'CSE2225');
INSERT INTO CurrentCourse VALUES ('150121031', 'CSE3215');

INSERT INTO CurrentCourse VALUES ('150121032', 'CSE2246');
INSERT INTO CurrentCourse VALUES ('150121032', 'CSE3215');

INSERT INTO CurrentCourse VALUES ('150121033', 'CSE2246');
INSERT INTO CurrentCourse VALUES ('150121033', 'CSE3063');

INSERT INTO CurrentCourse VALUES ('150121034', 'CSE2023');
INSERT INTO CurrentCourse VALUES ('150121034', 'PHYS1102');

INSERT INTO CurrentCourse VALUES ('150121035', 'CSE2023');
INSERT INTO CurrentCourse VALUES ('150121035', 'ECON2004');

INSERT INTO CurrentCourse VALUES ('150122037', 'IE3235');
INSERT INTO CurrentCourse VALUES ('150122037', 'ISG121');

INSERT INTO CurrentCourse VALUES ('150122038', 'CSE4074');

INSERT INTO CurrentCourse VALUES ('150122040', 'MATH1001');
INSERT INTO CurrentCourse VALUES ('150122040', 'CSE1200');

INSERT INTO CurrentCourse VALUES ('150122041', 'CSE1241');
INSERT INTO CurrentCourse VALUES ('150122041', 'PHYS1101');

CREATE TABLE WaitedCourse(
	studentID nvarchar(50) ,
	courseID nvarchar(50),
	foreign key (studentID) references Student(studentID) on delete cascade,
	foreign key (courseID) references Course(courseID) on delete set null
);

CREATE TABLE CurrentSection(
	studentID nvarchar(50) ,
	courseID nvarchar(50),
	courseSectionID NVARCHAR(50),
	foreign key (studentID) references Student(studentID) on delete cascade,
	foreign key (courseSectionID) references CourseSection(sectionID) on delete set null,
	foreign key (courseID) references Course(courseID) on delete set null
);

INSERT INTO CurrentSection VALUES ('150121031', 'CSE2225', 'CSE2225.1');
INSERT INTO CurrentSection VALUES ('150121031', 'CSE3215', 'CSE3215.1');

INSERT INTO CurrentSection VALUES ('150121032', 'CSE2246', 'CSE2246');
INSERT INTO CurrentSection VALUES ('150121032', 'CSE3215', 'CSE3215.1');

INSERT INTO CurrentSection VALUES ('150121033', 'CSE2246', 'CSE2246.1');
INSERT INTO CurrentSection VALUES ('150121033', 'CSE3063', 'CSE3063.2');

INSERT INTO CurrentSection VALUES ('150121034', 'CSE2023', 'CSE2023.1');
INSERT INTO CurrentSection VALUES ('150121034', 'PHYS1102', 'PHYS1102.2');

INSERT INTO CurrentSection VALUES ('150121035', 'CSE2023', 'CSE2023.1');
INSERT INTO CurrentSection VALUES ('150121035', 'ECON2004', 'ECON2004.1');

INSERT INTO CurrentSection VALUES ('150122037', 'IE3235', 'IE3235.1');
INSERT INTO CurrentSection VALUES ('150122037', 'ISG121', 'ISG121.1');

INSERT INTO CurrentSection VALUES ('150122038', 'CSE4074', 'CSE4074.2');

INSERT INTO CurrentSection VALUES ('150122040', 'MATH1001', 'MATH1001.1');
INSERT INTO CurrentSection VALUES ('150122040', 'CSE1200', 'CSE1200.1');

INSERT INTO CurrentSection VALUES ('150122041', 'CSE1241', 'CSE1241.2');
INSERT INTO CurrentSection VALUES ('150122041', 'PHYS1101', 'PHYS1101.1');



CREATE TABLE WaitedSection(
	studentID NVARCHAR(50) ,
	courseID NVARCHAR(50),
	courseSectionID NVARCHAR(50),
	foreign key (studentID) references Student(studentID) on delete cascade,
	foreign key (courseSectionID) references CourseSection(sectionID) on delete set null,
	foreign key (courseID) references Course(courseID) on delete set null
);

CREATE TABLE Notification(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	receiverID NVARCHAR(50),
	senderID NVARCHAR(50),
	notificationMessage NVARCHAR(100)
);


