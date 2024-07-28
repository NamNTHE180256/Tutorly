<%-- 
    Document   : ReviewQuiz
    Created on : 21 thg 7, 2024, 23:22:57
    Author     : asus
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.Question"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Review Quiz</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .question-list {
                list-style-type: none;
                padding: 0;
            }
            .question-list li {
                display: inline-block;
                margin: 5px;
                border: 1px solid black;
                padding: 10px;
                cursor: pointer;
            }
            .question-list .completed {
                background-color: blue;
                color: white;
            }
            .question {
                margin-bottom: 20px;
            }
            .question p {
                margin: 0;
            }
            .question input {
                margin-right: 10px;
            }
            .status-true {
                color: green;
            }
            .status-false {
                color: red;
            }
            #navigation {
                position: fixed;
                right: 20px;
                top: 100px;
                border: 1px solid black;
                padding: 10px;
            }
            #reviewInfo {
                border: 1px solid black;
                padding: 10px;
                margin-bottom: 20px;
            }
            #reviewInfo p {
                margin: 5px 0;
            }
            .form-check {
                display: flex;
                align-items: center;
            }
            .form-check input {
                margin-right: 10px;
            }
        </style>
    </head>
    <body class="container">
        
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Review Quiz</h1>
            <div id="navigation" class="border p-3 col-md-3">
                <h5>Quiz Navigation</h5>
                <ul class="question-list list-unstyled">
                    <%
                        List<Question> questions = (List<Question>) session.getAttribute("questions");
                        if (questions != null && !questions.isEmpty()) {
                            for (Question question : questions) {
                    %>
                    <li id="nav-question-<%= question.getNumber() %>" onclick="document.getElementById('question-<%= question.getNumber() %>').scrollIntoView();" class="p-2 border"><%= question.getNumber() %></li>
                        <%
                                }
                            }
                        %>
                </ul>
                <button class="btn btn-primary mt-2" onclick="window.location.href = '/Tutorly/QuizController'">Finish Review</button>
            </div>
        </div>

        <div id="reviewInfo" class="border p-3 mb-4 col-md-9">
            <p><strong>Started on:</strong> <%= session.getAttribute("startedOn") != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) session.getAttribute("startedOn")) : "N/A" %></p>
            <p><strong>Status:</strong> Done</p>
            <p><strong>Completed on:</strong> <%= session.getAttribute("completedOn") != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) session.getAttribute("completedOn")) : "N/A" %></p>
            <p><strong>Time taken:</strong> <%= session.getAttribute("timeTaken") != null ? session.getAttribute("timeTaken") : "N/A" %></p>
            <p><strong>Marks:</strong> <%= session.getAttribute("score") %> ( <%= session.getAttribute("correctAnswers") %> / <%= questions != null ? questions.size() : "N/A" %> )</p>
        </div>

        <div>
            <%
                if (questions != null && !questions.isEmpty()) {
                    for (Question question : questions) {
                        String userAnswer = (String) session.getAttribute("answer" + question.getNumber()); // Get the chosen answer from the session
                        boolean isCorrect = userAnswer != null && userAnswer.equals(question.getCorrectAnswer());
            %>
            <div class="question p-3 border mb-4 col-md-9" id="question-<%= question.getNumber() %>">
                <p style="font-weight: bold">Question <%= question.getNumber() %>. <%= question.getQuestionText() %></p>
                <div class="form-check">
                    <input type="radio" name="answer<%= question.getNumber() %>" value="A" <%= question.getAnswerA().equals(userAnswer) ? "checked" : "" %> disabled>
                    <label class="form-check-label"><%= question.getAnswerA() %></label>
                </div>
                <div class="form-check">
                    <input type="radio" name="answer<%= question.getNumber() %>" value="B" <%= question.getAnswerB().equals(userAnswer) ? "checked" : "" %> disabled>
                    <label class="form-check-label"><%= question.getAnswerB() %></label>
                </div>
                <div class="form-check">
                    <input type="radio" name="answer<%= question.getNumber() %>" value="C" <%= question.getAnswerC().equals(userAnswer) ? "checked" : "" %> disabled>
                    <label class="form-check-label"><%= question.getAnswerC() %></label>
                </div>
                <div class="form-check">
                    <input type="radio" name="answer<%= question.getNumber() %>" value="D" <%= question.getAnswerD().equals(userAnswer) ? "checked" : "" %> disabled>
                    <label class="form-check-label"><%= question.getAnswerD() %></label>
                </div>
                <p style="color: #7d5a29; background-color: #fcefdc; border-color: #fbe8cd; "> The correct answer is: <%= question.getCorrectAnswer() %> <span style="font-weight: bold" class="status-<%= isCorrect ? "true" : "false" %>">(<%= isCorrect ? "Correct" : "False" %>)</span></p>
            </div>
            <%
                    }
                } else {
            %>
            <p>No questions available.</p>
            <%
                }
            %>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
