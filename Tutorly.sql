--CREATE DATABASE Tutorly
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
    bio NTEXT,
    edu NVARCHAR(255),
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
    [status] VARCHAR(50), --ongoing, finished, trial
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
	score FLOAT,
	[status] VARCHAR(20),
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
(1, N'Nam Nguyễn', 'learner1.jpg'),
(2, N'Trang Trần', 'learner2.jpg'),
(3, N'Đức Anh', 'learner3.jpg'),
(4, N'Lê Dũng', 'learner4.jpg'),
(5, N'Tiến Dũng', 'learner5.jpg'),
(6, N'Tùng Dương', 'learner6.jpg')

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

(7, 2, N'Đặng Anh Hiển', 1, 'tutor1.jpg', 
    N'Với hơn 15 năm kinh nghiệm giảng dạy Toán học tại các trường đại học hàng đầu, tôi, Đặng Anh Hiển, luôn tự hào về khả năng giúp học sinh nắm vững kiến thức và đạt điểm cao trong các kỳ thi. Phương pháp giảng dạy của tôi không chỉ tập trung vào việc giải thích các khái niệm khó một cách dễ hiểu mà còn khơi gợi sự hứng thú và đam mê học tập ở mỗi học sinh. Tôi tin rằng mỗi học sinh đều có tiềm năng và nhiệm vụ của tôi là giúp các em phát huy tối đa khả năng của mình.', 
    N'Đại học FPT', 300.0, 'Bank1', 'Active'),
(8, 4, N'Trương Gia Bình', 1, 'tutor2.jpg', 
    N'Tôi là Trương Gia Bình, một chuyên gia Vật lý với hơn 20 năm kinh nghiệm giảng dạy và nghiên cứu. Với niềm đam mê và sự kiên trì, tôi luôn nỗ lực truyền đạt những kiến thức vật lý phức tạp một cách đơn giản và thú vị. Tôi đã có nhiều công trình nghiên cứu được công bố trên các tạp chí khoa học uy tín, và tôi tin rằng, sự hiểu biết sâu rộng cùng phương pháp giảng dạy sáng tạo của mình sẽ giúp học sinh không chỉ học tốt mà còn yêu thích môn Vật lý.', 
    N'Đại học Bách Khoa Hà Nội', 250.0, 'Bank2', 'Active'),
(10, 15, N'Nguyễn Xuân', 0, 'tutor4.jpg', 
    N'Với phương châm "Từ zero đến hero", tôi, Nguyễn Xuân, đã giúp đỡ nhiều học sinh từ mất gốc trở thành xuất sắc trong môn học. Phong cách giảng dạy của tôi linh hoạt, phù hợp với từng đối tượng học sinh, và tôi luôn sẵn sàng hỗ trợ các em vượt qua mọi khó khăn trong học tập. Tôi tin rằng sự nỗ lực và kiên trì sẽ đem lại kết quả tốt đẹp cho mọi học sinh.', 
    N'Đại học Kinh tế Quốc dân', 350.0, 'Bank2', 'Active'),
(11, 10, N'Phạm Quang Huy', 1, 'tutor5.jpg', 
    N'Tôi là Phạm Quang Huy, giáo viên dạy Tiếng Anh với hơn 12 năm kinh nghiệm. Tôi đã giúp nhiều học sinh đạt được chứng chỉ tiếng Anh quốc tế như IELTS, TOEFL với điểm số cao. Với phương pháp giảng dạy sáng tạo và sử dụng nhiều công cụ hiện đại, tôi luôn cố gắng mang đến cho học viên những bài học thú vị và hiệu quả. Tôi tin rằng với sự hướng dẫn của mình, các em sẽ tự tin hơn trong việc sử dụng tiếng Anh và đạt được mục tiêu của mình.', 
    N'Đại học Ngoại thương', 300.0, 'Bank3', 'Pending'),
(13, 5, N'Trần Hữu Dũng', 1, 'tutor6.jpg', 
    N'Tôi là Trần Hữu Dũng, giảng viên Vật lý ứng dụng với hơn 18 năm kinh nghiệm. Tôi tin rằng việc kết hợp lý thuyết với thực hành là cách tốt nhất để học sinh hiểu và yêu thích môn Vật lý. Tôi luôn tìm cách áp dụng các hiện tượng vật lý vào thực tiễn, giúp học sinh không chỉ học tốt mà còn thấy được sự kỳ diệu và ứng dụng của Vật lý trong cuộc sống hàng ngày.', 
    N'Đại học Sư phạm Hà Nội', 320.0, 'Bank3', 'Pending'),
(9, 13, N'Nguyễn Phương', 0, 'tutor3.jpg', 
    N'Tôi là Nguyễn Phương, một giáo viên dạy văn với 10 năm kinh nghiệm. Tôi luôn tin rằng văn học không chỉ là môn học mà còn là cách để học sinh hiểu và trân trọng cuộc sống. Với phương pháp giảng dạy nhiệt huyết và tận tâm, tôi đã giúp nhiều học sinh đạt giải cao trong các kỳ thi học sinh giỏi văn cấp quốc gia. Tôi luôn cố gắng tạo ra một môi trường học tập đầy cảm hứng để các em có thể phát triển toàn diện kỹ năng ngôn ngữ và tư duy.', 
    N'Đại học Kinh tế Quốc dân', 400.0, 'Bank2', 'Pending');

<<<<<<< HEAD
DELETE FROM Tutor WHERE id = 																			
=======
																			
>>>>>>> 2668d73a852ea550eafa9ca536a81000ad149629
-- Insert into Rating table
INSERT INTO Rating (learnerId, tutorId, rating, review, createdAt)
VALUES 
(1, 7, 5, N'Thầy dạy hay, vui tính', GETDATE()),
(2, 8, 4, N'Thầy dạy vui tính, hay', GETDATE());

-- Insert into Class table
INSERT INTO Class (learnerId, tutorId, totalSession, startDate, endDate, [status])
VALUES 
(1, 7, 10, '2024-05-01', '2024-05-30', 'ongoing'),
(2, 8, 5, '2024-06-01', '2024-06-30', 'finished');

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
(1, 'M1', '2024-05-06', 'Finished'),
(1, 'M1', '2024-05-13', 'Finished'),
(1, 'M1', '2024-05-20', 'Finished'),
(1, 'M1', '2024-05-27', 'Finished'),
(1, 'M1', '2024-06-03', 'Finished'),
(1, 'M1', '2024-06-10', 'Scheduled'),
(1, 'M1', '2024-06-17', 'Scheduled'),
(1, 'M1', '2024-06-24', 'Scheduled'),
(1, 'M1', '2024-07-01', 'Scheduled'),
(2, 'SA1', '2024-05-11', 'Finished'),
(2, 'SA1', '2024-05-18', 'Finished'),
(2, 'SA1', '2024-05-25', 'Finished'),
(2, 'SA1', '2024-06-01', 'Finished'),
(2, 'SA1', '2024-06-08', 'Scheduled'),
(2, 'SA1', '2024-06-15', 'Scheduled'),
(2, 'SA1', '2024-06-22', 'Scheduled');


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
INSERT INTO Assignment ([fileName], filePath, score, [status], createdAt, lessionId)
VALUES 
(N'Assignment1', 'path/to/assignment1.docx', 9.5, 'done', GETDATE(), 1),
(N'Assignment2', 'path/to/assignment2.docx', 8.5, 'done', GETDATE(), 2),
(N'Assignment3', 'path/to/assignment3.docx', 7.5, 'done', GETDATE(), 3),
(N'Assignment4', 'path/to/assignment4.docx', 6.5, 'done', GETDATE(), 4),
(N'Assignment5', 'path/to/assignment5.docx', 5.5, 'done', GETDATE(), 5),
(N'Assignment6', 'path/to/assignment6.docx', null, 'todo', GETDATE(), 6),
(N'Assignment7', 'path/to/assignment7.docx', null, 'todo', GETDATE(), 7),
(N'Assignment8', 'path/to/assignment8.docx', null, 'todo', GETDATE(), 8),
(N'Assignment9', 'path/to/assignment9.docx', null, 'todo', GETDATE(), 9),
(N'Assignment10', 'path/to/assignment10.docx', null, 'done', GETDATE(), 10),
(N'Assignment11', 'path/to/assignment11.docx', null, 'done', GETDATE(), 11),
(N'Assignment12', 'path/to/assignment12.docx', null, 'done', GETDATE(), 12),
(N'Assignment13', 'path/to/assignment13.docx', null, 'done', GETDATE(), 13),
(N'Assignment14', 'path/to/assignment14.docx', null, 'todo', GETDATE(), 14),
(N'Assignment15', 'path/to/assignment15.docx', null, 'todo', GETDATE(), 15),
(N'Assignment16', 'path/to/assignment16.docx', null, 'todo', GETDATE(), 16);

-- Insert into Material table
INSERT INTO Material ([fileName], filePath, fileType, uploadedAt, lessionId)
VALUES 
(N'Material1.pdf', 'path/to/material1.pdf', 'PDF', GETDATE(), 1),
(N'Material2.pptx', 'path/to/material2.pptx', 'PPTX', GETDATE(), 2);