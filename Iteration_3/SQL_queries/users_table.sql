USE CRS

CREATE TABLE users(
  UserID NVARCHAR(50) PRIMARY KEY NOT NULL,
  Password NVARCHAR(50) NOT NULL
)

INSERT INTO User VALUES('150121031','abc123')
INSERT INTO users VALUES('150121032','password2')
INSERT INTO users VALUES('150121033','password3')
INSERT INTO users VALUES('150121034','password4')
INSERT INTO users VALUES('150121035','password5')
INSERT INTO users VALUES('150122036','password6')
INSERT INTO users VALUES('150122037','password7')
INSERT INTO users VALUES('150122038','password8')
INSERT INTO users VALUES('150122039','password9')
INSERT INTO users VALUES('150122040','password10')
INSERT INTO users VALUES('150122041','password11')
INSERT INTO users VALUES('150122042','password12')


CREATE TABLE Student (
    studentID NVARCHAR(50) PRIMARY KEY,
    name NVARCHAR(50),
    surname NVARCHAR(50),
    gender CHAR,
    birthDate DATE,
    advisorID NVARCHAR(50),
	foreign key (advisorID) references Advisor(advisorID)
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
    FOREIGN KEY (ssn) REFERENCES Lecturer(ssn)
);


