<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Quiz"%>
<%@ page import="DAO.QuizDAO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Upload Quiz</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .upload-assignment {
                margin-top: 50px;
                border: 1px dashed #000;
                padding: 20px;
                max-width: 600px;
                margin: 50px auto;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }
            .upload-assignment h1 {
                text-align: center;
                margin-bottom: 30px;
            }
            .upload-assignment input[type="file"] {
                width: 100%;
                margin-top: 20px;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            .upload-assignment button {
                display: block;
                width: 100%;
                margin-top: 20px;
                padding: 10px;
                border: none;
                background-color: #007bff;
                color: #fff;
                border-radius: 4px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }
            .upload-assignment .note {
                margin-top: 20px;
                color: red;
            }
        </style>
    </head>
    <body>

        <%
            String lessonIdStr = request.getParameter("lessonId");
            String classIdStr = request.getParameter("classId");
            if (lessonIdStr == null || lessonIdStr.isEmpty()) {
                out.println("Lesson ID is missing.");
                return;
            }

            int lessonId = Integer.parseInt(lessonIdStr);
            int classId = Integer.parseInt(classIdStr);
            QuizDAO quizDAO = new QuizDAO();
            Quiz existingQuiz = quizDAO.getQuizByLessonId(lessonId);
            if (existingQuiz != null) {
        %>
        <script type="text/javascript">
            if (confirm("A quiz already exists for this lesson. Do you want to create a new one?")) {
                window.location.href = "QuizServlet?action=createNew&lessonId=<%= lessonIdStr %>";
            } else {
                window.location.href = "Tutorly/lessonDetailControllers?classid=${v.getAClass().id}&lessonId=${v.getId()}";
            }
        </script>
        <%
            } else {
        %>
        <div class="container upload-assignment">
            <h1>Upload Quiz</h1>
            <form action="/Tutorly/QuizServlet" method="post" enctype="multipart/form-data">
                <input type="hidden" name="role" value="tutor">
                <input type="hidden" name="lessonId" value="<%= lessonIdStr %>">
                <input type="hidden" name="classId" value="<%= classIdStr %>">
                <input type="file" name="file" required>
                <button type="submit">Upload</button>
            </form>
            <div class="note">
                <p>NOTE: The format of each question is: Question; Answer A; Answer B; Answer C; Answer D; Correct Answer. Each downline is another question.</p>
            </div>
        </div>
        <%
            }
        %>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
