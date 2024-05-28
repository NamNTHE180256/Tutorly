CREATE DATABASE Tutorly
use Tutorly
--drop database Tutorly
CREATE TABLE [User] (
    id INT IDENTITY(1,1) PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    [password] VARCHAR(255) NOT NULL,
    [role] VARCHAR(50) NOT NULL,
    createdAt DATETIME DEFAULT GETDATE()
);

CREATE TABLE Learner (
    id INT PRIMARY KEY,
    [name] NVARCHAR(255) NOT NULL,
    image VARCHAR(255)
);

CREATE TABLE [Subject] (
    id INT IDENTITY(1,1) PRIMARY KEY,
    [name] NVARCHAR(255) NOT NULL
);

CREATE TABLE Tutor (
    id INT PRIMARY KEY,
    subjectId INT,
    [name] NVARCHAR(255) NOT NULL,
    gender BIT,
    [image] VARCHAR(255),
    bio VARCHAR(255),
    edu VARCHAR(255),
    price FLOAT,
    bank VARCHAR(255),
    [status] VARCHAR(50),
	FOREIGN KEY (subjectId) REFERENCES [Subject](id)
);

CREATE TABLE [Certificate] (
	id INT IDENTITY(1,1) PRIMARY KEY,
	tutorId INT,
	[image] VARCHAR(255),
	FOREIGN KEY (tutorId) REFERENCES Tutor(id)
);

CREATE TABLE Rating (
    id INT IDENTITY(1,1) PRIMARY KEY,
	learnerId INT,
    tutorId INT,
    rating INT,
    review TEXT,
    createdAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (tutorId) REFERENCES Tutor(id),
    FOREIGN KEY (learnerId) REFERENCES Learner(id)
);

CREATE TABLE SavedTutor (
	id INT IDENTITY(1,1) PRIMARY KEY,
	learnerId INT,
    tutorId INT,
	FOREIGN KEY (tutorId) REFERENCES Tutor(id),
    FOREIGN KEY (learnerId) REFERENCES Learner(id)
);

CREATE TABLE Class (
    id INT IDENTITY(1,1) PRIMARY KEY,
    learnerId INT,
    tutorId INT,
    totalSession INT,
    startDate DATE,
    endDate DATE,
    [status] VARCHAR(50),
    FOREIGN KEY (learnerId) REFERENCES Learner(id),
    FOREIGN KEY (tutorId) REFERENCES Tutor(id)
);

CREATE TABLE [Session] (
    id VARCHAR(10) PRIMARY KEY,
    startTime TIME,
    endTime TIME,
    [dayOfWeek] VARCHAR(50)
);

CREATE TABLE Lession (
    id INT IDENTITY(1,1) PRIMARY KEY,
    classId INT,
    sessionId VARCHAR(10),
    [date] DATE,
    [status] VARCHAR(50),
    FOREIGN KEY (classId) REFERENCES Class(id),
    FOREIGN KEY (sessionId) REFERENCES [Session](id)
);

CREATE TABLE TutorAvailability (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tutorId INT,
    sessionId VARCHAR(10),
	[status] VARCHAR(50),
    FOREIGN KEY (tutorId) REFERENCES Tutor(id),
    FOREIGN KEY (sessionId) REFERENCES [Session](id)
);

CREATE TABLE Payment (
    id INT IDENTITY(1,1) PRIMARY KEY,
    classId INT,
    amount REAL,
    [date] date,
    FOREIGN KEY (classId) REFERENCES Class(id)
);

CREATE TABLE Assignment (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
	lessionId INT,
    [fileName] NVARCHAR(255),
    filePath VARCHAR(255),
    createdAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (lessionId) REFERENCES Lession(id)
);

CREATE TABLE Material (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
	lessionId INT,
    [fileName] NVARCHAR(255),
    filePath VARCHAR(255),
    fileType VARCHAR(50),
    uploadedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (lessionId) REFERENCES Lession(id)
);


-- Insert into User table
INSERT INTO [User] (email, [password], [role], createdAt)
VALUES 
('learner1@gmail.com', '1', 'learner', GETDATE()),
('learner2@gmail.com', '1', 'learner', GETDATE()),
('learner3@gmail.com', '1', 'learner', GETDATE()),
('learner4@gmail.com', '1', 'learner', GETDATE()),
('learner5@gmail.com', '1', 'learner', GETDATE()),
('learner6@gmail.com', '1', 'learner', GETDATE()),
('tutor1@example.com', '2', 'tutor', GETDATE()),
('tutor2@example.com', '2', 'tutor', GETDATE()),
('tutor3@example.com', '2', 'tutor', GETDATE()),
('tutor4@example.com', '2', 'tutor', GETDATE()),
('tutor5@example.com', '2', 'tutor', GETDATE()),
('tutor6@example.com', '2', 'tutor', GETDATE()),
('admin1@example.com', '3', 'admin', GETDATE());

-- Insert into Learner table
INSERT INTO Learner (id, [name], [image])
VALUES 
(1, N'Nam Nguyễn', 'image1.jpg'),
(2, N'Trang Trần', 'image2.jpg'),
(3, N'Đức Anh', 'image1.jpg'),
(4, N'Lê Dũng', 'image2.jpg'),
(5, N'Tiến Dũng', 'image1.jpg'),
(6, N'Tùng Dương', 'image2.jpg')

-- Insert into Subject table
INSERT INTO Subject ([name])
VALUES 
(N'Maths 10'),
(N'Maths 11'),
(N'Maths 12'),
(N'Physics 10'),
(N'Physics 11'),
(N'Physics 12'),
(N'Chemistry 10'),
(N'Chemistry 11'),
(N'Chemistry 12'),
(N'English 10'),
(N'English 11'),
(N'English 12'),
(N'Literature 10'),
(N'Literature 11'),
(N'Literature 12')
-- Insert into Tutor table
INSERT INTO Tutor (id, subjectId, [name], gender, [image], bio, edu, price, bank, [status])
VALUES 
(7, 13, N'Đặng Anh Hiển', 1, 'tutor1.jpg', 'Experienced Math Tutor', 'FPT University', 300.0, 'Bank1', 'Active'),
(8, 4, N'Trương Gia Bình', 1, 'tutor2.jpg', 'Physics Specialist', 'HUST', 250.0, 'Bank2', 'Active'),
(9, 15, N'Nguyễn Phương', 0, 'tutor3.jpg', 'Là một giáo viên dạy văn có kinh nghiệm 10 năm', 'NEU', 400.0, 'Bank2', 'Active'),
(10, 15, N'Nguyễn Xuân', 0, 'tutor4.jpg', 'I can grow you from zero to hero', 'NEU', 350.0, 'Bank2', 'Active')
-- Insert into Rating table
INSERT INTO Rating (learnerId, tutorId, rating, review, createdAt)
VALUES 
(1, 7, 5, 'Thầy dạy hay, vui tính', GETDATE()),
(2, 8, 4, 'Thầy dạy vui tính, hay', GETDATE());

-- Insert into Class table
INSERT INTO Class (learnerId, tutorId, totalSession, startDate, endDate, [status])
VALUES 
(1, 7, 10, '2024-05-01', '2024-05-30', 'Ongoing'),
(2, 8, 5, '2024-06-01', '2024-06-30', 'Pending');

-- Insert into Slot table
INSERT INTO [Session] (id, startTime, endTime, [dayOfWeek])
VALUES 
('M1', '08:00:00', '09:30:00', 'Monday'),
('M2', '10:00:00', '11:30:00', 'Monday'),
('M3', '14:00:00', '15:30:00', 'Monday'),
('M4', '16:00:00', '17:30:00', 'Monday'),
('M5', '19:00:00', '20:30:00', 'Monday'),
('T1', '08:00:00', '09:30:00', 'Tuesday'),
('T2', '10:00:00', '11:30:00', 'Tuesday'),
('T3', '14:00:00', '15:30:00', 'Tuesday'),
('T4', '16:00:00', '17:30:00', 'Tuesday'),
('T5', '19:00:00', '20:30:00', 'Tuesday'),
('W1', '08:00:00', '09:30:00', 'Wednesday'),
('W2', '10:00:00', '11:30:00', 'Wednesday'),
('W3', '14:00:00', '15:30:00', 'Wednesday'),
('W4', '16:00:00', '17:30:00', 'Wednesday'),
('W5', '19:00:00', '20:30:00', 'Wednesday'),
('R1', '08:00:00', '09:30:00', 'Thursday'),
('R2', '10:00:00', '11:30:00', 'Thursday'),
('R3', '14:00:00', '15:30:00', 'Thursday'),
('R4', '16:00:00', '17:30:00', 'Thursday'),
('R5', '19:00:00', '20:30:00', 'Thursday'),
('F1', '08:00:00', '09:30:00', 'Friday'),
('F2', '10:00:00', '11:30:00', 'Friday'),
('F3', '14:00:00', '15:30:00', 'Friday'),
('F4', '16:00:00', '17:30:00', 'Friday'),
('F5', '19:00:00', '20:30:00', 'Friday'),
('SA1', '08:00:00', '09:30:00', 'Saturday'),
('SA2', '10:00:00', '11:30:00', 'Saturday'),
('SA3', '14:00:00', '15:30:00', 'Saturday'),
('SA4', '16:00:00', '17:30:00', 'Saturday'),
('SA5', '19:00:00', '20:30:00', 'Saturday'),
('SU1', '08:00:00', '09:30:00', 'Sunday'),
('SU2', '10:00:00', '11:30:00', 'Sunday'),
('SU3', '14:00:00', '15:30:00', 'Sunday'),
('SU4', '16:00:00', '17:30:00', 'Sunday'),
('SU5', '19:00:00', '20:30:00', 'Sunday');

-- Insert into Class_Slot table
INSERT INTO Lession (classId, sessionId, [date], [status])
VALUES 
(1, 'M1', '2024-05-06', 'Scheduled'),
(2, 'SA1', '2024-06-10', 'Scheduled');

-- Insert into TutorAvailability table
INSERT INTO TutorAvailability (tutorId, sessionId, [status])
VALUES 
(7, 'M1', 'Available'),
(7, 'M4', 'Available'),
(7, 'T2', 'Available'),
(7, 'SA1', 'Available'),
(8, 'SU3', 'Available'),
(8, 'F5', 'Available'),
(8, 'R3', 'Available'),
(8, 'M4', 'Available'),
(9, 'T3', 'Available'),
(9, 'W1', 'Available'),
(9, 'SA5', 'Available'),
(9, 'F5', 'Available'),
(10, 'T2', 'Available'),
(10, 'F4', 'Available'),
(10, 'SU5', 'Available'),
(10, 'SA5', 'Available'),
(10, 'M5', 'Available');

-- Insert into Payment table
INSERT INTO Payment (classId, amount, [date])
VALUES 
(1, 3000.0, '2024-05-02'),
(2, 2000.0, '2024-06-02');

-- Insert into Assignment table
INSERT INTO Assignment ([fileName], filePath, createdAt, lessionId)
VALUES 
(N'Assignment1.docx', 'path/to/assignment1.docx', GETDATE(), 1),
(N'Assignment2.docx', 'path/to/assignment2.docx', GETDATE(), 2);

-- Insert into Material table
INSERT INTO Material ([fileName], filePath, fileType, uploadedAt, lessionId)
VALUES 
(N'Material1.pdf', 'path/to/material1.pdf', 'PDF', GETDATE(), 1),
(N'Material2.pptx', 'path/to/material2.pptx', 'PPTX', GETDATE(), 2);
