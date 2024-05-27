--CREATE DATABASE Tutorly
--use Tutorly
--drop database Tutorly
CREATE TABLE [User] (
    id INT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
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
    [cert] VARCHAR(255),
    price FLOAT,
    bank VARCHAR(255),
    [status] VARCHAR(50),
	FOREIGN KEY (subjectId) REFERENCES [Subject](id)
);


CREATE TABLE Rating (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tutorId INT,
    learnerId INT,
    rating INT,
    review TEXT,
    createdAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (tutorId) REFERENCES Tutor(id),
    FOREIGN KEY (learnerId) REFERENCES Learner(id)
);

CREATE TABLE Class (
    id INT PRIMARY KEY,
    learnerId INT,
    tutorId INT,
    meetUrl VARCHAR(255),
    totalSlots INT,
    startDate DATE,
    endDate DATE,
    [status] VARCHAR(50),
    FOREIGN KEY (learnerId) REFERENCES Learner(id),
    FOREIGN KEY (tutorId) REFERENCES Tutor(id)
);

CREATE TABLE Slot (
    id INT IDENTITY(1,1) PRIMARY KEY,
    startTime TIME,
    endTime TIME,
    [dayOfWeek] VARCHAR(50)
);

CREATE TABLE Class_Slot (
    id INT PRIMARY KEY,
    classId INT,
    slotId INT,
    [date] DATE,
    [status] VARCHAR(50),
    FOREIGN KEY (classId) REFERENCES Class(id),
    FOREIGN KEY (slotId) REFERENCES Slot(id)
);

CREATE TABLE TutorAvailability (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tutorId INT,
    slotId INT,
	[status] VARCHAR(50),
    FOREIGN KEY (tutorId) REFERENCES Tutor(id),
    FOREIGN KEY (slotId) REFERENCES Slot(id)
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
    [fileName] NVARCHAR(255),
    filePath VARCHAR(255),
    createdAt DATETIME DEFAULT GETDATE(),
    class_slotId INT,
    FOREIGN KEY (class_slotId) REFERENCES Class_Slot(id)
);

CREATE TABLE Material (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    [fileName] NVARCHAR(255),
    filePath VARCHAR(255),
    fileType VARCHAR(50),
    uploadedAt DATETIME DEFAULT GETDATE(),
    class_slotId INT,
    FOREIGN KEY (class_slotId) REFERENCES Class_Slot(id)
);


-- Insert into User table
INSERT INTO [User] (id, email, [password], [role], createdAt)
VALUES 
(1, 'learner1@example.com', '1', 'learner', GETDATE()),
(2, 'learner2@example.com', '1', 'learner', GETDATE()),
(3, 'tutor1@example.com', '2', 'tutor', GETDATE()),
(4, 'tutor2@example.com', '2', 'tutor', GETDATE()),
(5, 'admin1@example.com', '3', 'admin', GETDATE());

-- Insert into Learner table
INSERT INTO Learner (id, [name], [image])
VALUES 
(1, N'Nam Nguyễn', 'image1.jpg'),
(2, N'Trang Trần', 'image2.jpg');

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
INSERT INTO Tutor (id, subjectId, [name], gender, [image], bio, edu, [cert], price, bank, [status])
VALUES 
(3, 13, N'HiểnDA', 1, 'tutor1.jpg', 'Experienced Math Tutor', 'PhD in Mathematics', 'Math Cert', 300.0, 'Bank1', 'Active'),
(4, 4, N'Trương Gia Bình', 1, 'tutor2.jpg', 'Physics Specialist', 'MSc in Physics', 'Physics Cert', 250.0, 'Bank2', 'Active');

-- Insert into Rating table
INSERT INTO Rating (tutorId, learnerId, rating, review, createdAt)
VALUES 
(3, 1, 5, 'Thầy dạy hay, vui tính', GETDATE()),
(4, 2, 4, 'Thầy dạy vui tính, hay', GETDATE());

-- Insert into Class table
INSERT INTO Class (id, learnerId, tutorId, meetUrl, totalSlots, startDate, endDate, [status])
VALUES 
(1, 1, 3, 'https://meet.url/1', 10, '2024-05-01', '2024-05-30', 'Ongoing'),
(2, 2, 4, 'https://meet.url/2', 8, '2024-06-01', '2024-06-30', 'Pending');

-- Insert into Slot table
INSERT INTO Slot (startTime, endTime, [dayOfWeek])
VALUES 
('08:00:00', '09:30:00', 'Monday'),
('10:00:00', '11:30:00', 'Monday'),
('14:00:00', '15:30:00', 'Monday'),
('16:00:00', '17:30:00', 'Monday'),
('19:00:00', '20:30:00', 'Monday'),
('08:00:00', '09:30:00', 'Tuesday'),
('10:00:00', '11:30:00', 'Tuesday'),
('14:00:00', '15:30:00', 'Tuesday'),
('16:00:00', '17:30:00', 'Tuesday'),
('19:00:00', '20:30:00', 'Tuesday'),
('08:00:00', '09:30:00', 'Wednesday'),
('10:00:00', '11:30:00', 'Wednesday'),
('14:00:00', '15:30:00', 'Wednesday'),
('16:00:00', '17:30:00', 'Wednesday'),
('19:00:00', '20:30:00', 'Wednesday'),
('08:00:00', '09:30:00', 'Thursday'),
('10:00:00', '11:30:00', 'Thursday'),
('14:00:00', '15:30:00', 'Thursday'),
('16:00:00', '17:30:00', 'Thursday'),
('19:00:00', '20:30:00', 'Thursday'),
('08:00:00', '09:30:00', 'Friday'),
('10:00:00', '11:30:00', 'Friday'),
('14:00:00', '15:30:00', 'Friday'),
('16:00:00', '17:30:00', 'Friday'),
('19:00:00', '20:30:00', 'Friday'),
('08:00:00', '09:30:00', 'Saturday'),
('10:00:00', '11:30:00', 'Saturday'),
('14:00:00', '15:30:00', 'Saturday'),
('16:00:00', '17:30:00', 'Saturday'),
('19:00:00', '20:30:00', 'Saturday'),
('08:00:00', '09:30:00', 'Sunday'),
('10:00:00', '11:30:00', 'Sunday'),
('14:00:00', '15:30:00', 'Sunday'),
('16:00:00', '17:30:00', 'Sunday'),
('19:00:00', '20:30:00', 'Sunday')
-- Insert into Class_Slot table
INSERT INTO Class_Slot (id, classId, slotId, [date], [status])
VALUES 
(1, 1, 1, '2024-05-06', 'Scheduled'),
(2, 2, 2, '2024-06-10', 'Scheduled');

-- Insert into TutorAvailability table
INSERT INTO TutorAvailability (tutorId, slotId, [status])
VALUES 
(4, 1, 'Available'),
(4, 3, 'Available'),
(4, 5, 'Available'),
(4, 7, 'Available'),
(4, 9, 'Available'),
(4, 14, 'Available'),
(4, 19, 'Available'),
(4, 20, 'Available'),
(4, 24, 'Available'),
(3, 2, 'Available'),
(3, 5, 'Available'),
(3, 7, 'Available'),
(3, 13, 'Available'),
(3, 17, 'Available'),
(3, 22, 'Available'),
(3, 28, 'Available'),
(3, 35, 'Available')

-- Insert into Payment table
INSERT INTO Payment (classId, amount, [date])
VALUES 
(1, 3000.0, '2024-05-02'),
(2, 2000.0, '2024-06-02');

-- Insert into Assignment table
INSERT INTO Assignment ([fileName], filePath, createdAt, class_slotId)
VALUES 
(N'Assignment1.docx', 'path/to/assignment1.docx', GETDATE(), 1),
(N'Assignment2.docx', 'path/to/assignment2.docx', GETDATE(), 2);

-- Insert into Material table
INSERT INTO Material ([fileName], filePath, fileType, uploadedAt, class_slotId)
VALUES 
(N'Material1.pdf', 'path/to/material1.pdf', 'PDF', GETDATE(), 1),
(N'Material2.pptx', 'path/to/material2.pptx', 'PPTX', GETDATE(), 2);
