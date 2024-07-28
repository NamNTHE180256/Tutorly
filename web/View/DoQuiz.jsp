<%-- 
    Document   : DoQuiz
    Created on : 21 thg 7, 2024, 23:20:13
    Author     : asus
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.Question"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Do Quiz</title>
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
            #alertBox {
                text-align: center;
                border: 1px solid black;
                padding: 20px;
                width: 50%;
                margin: 20px auto;
            }
            #quizContent {
                display: none;
            }
            .question {
                margin-bottom: 20px;
            }
            .question p {
                margin: 0;
            }
            .form-check {
                margin-bottom: 10px;
            }
            #navigation {
                position: fixed;
                right: 20px;
                top: 100px;
                border: 1px solid black;
                padding: 10px;
            }
            #timer {
                font-weight: bold;
            }
            .warning {
                color: red;
                display: none;
            }
        </style>
        <script type="text/javascript">
            var timeLeft = <%= session.getAttribute("timeLeft") != null ? (Integer) session.getAttribute("timeLeft") : (Integer) session.getAttribute("quizTime") %>;
            var timerId;

            function formatTime(seconds) {
                var hours = Math.floor(seconds / 3600);
                var minutes = Math.floor((seconds % 3600) / 60);
                var seconds = seconds % 60;
                return (hours < 10 ? "0" : "") + hours + ":" +
                        (minutes < 10 ? "0" : "") + minutes + ":" +
                        (seconds < 10 ? "0" : "") + seconds;
            }

            function updateTimer() {
                document.getElementById("timer").innerHTML = formatTime(timeLeft);
                if (timeLeft <= 0) {
                    clearInterval(timerId);
                    document.getElementById("quizForm").submit();
                }
                timeLeft--;
                updateTimeLeftInSession();
            }

            function updateTimeLeftInSession() {
                fetch('UpdateTimeServlet', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'timeLeft=' + timeLeft
                });
            }

            function startQuiz() {
                timerId = setInterval(updateTimer, 1000);
                document.getElementById("alertBox").style.display = "none";
                document.getElementById("quizContent").style.display = "block";
            <% session.setAttribute("startTime", System.currentTimeMillis()); %>
            }

            function cancelQuiz() {
                window.location.href = '/Tutorly/View/HomePage.jsp'; // Redirect to homepage or another page
            }

            function markQuestionCompleted(questionNumber) {
                document.getElementById('nav-question-' + questionNumber).classList.add('completed');
            }

            function validateForm() {
                var questions = document.querySelectorAll('.question');
                var allAnswered = true;
                questions.forEach(function (question) {
                    var answered = question.querySelector('input[type="radio"]:checked');
                    if (!answered) {
                        allAnswered = false;
                    }
                });
                if (!allAnswered) {
                    document.getElementById('warning').style.display = 'block';
                    setTimeout(function () {
                        document.getElementById('warning').style.display = 'none';
                    }, 3000);
                    return false;
                }
                return true;
            }

            function stopTimer() {
                clearInterval(timerId);
            }
        </script>
    </head>
    <body class="container">
        
        <div id="alertBox" class="border p-4">
            <h1>Take Quiz</h1>
            <% List<Question> questions = (List<Question>) session.getAttribute("questions"); %>
            <p>Number of Questions: <%= questions != null ? questions.size() : "N/A" %> </p>
            <p>Grading method: Highest grade</p>
            <p>Time of Quiz: <%= (Integer) session.getAttribute("quizTime") / 60 %> minutes</p>
            <p>NOTE: Time will count down from the moment you start your attempt and you must submit before it expires. Are you sure that you wish to start now?</p>
            <button class="btn btn-primary" onclick="startQuiz()">Start</button>
            <button class="btn btn-secondary" onclick="cancelQuiz()">Cancel</button>
        </div>

        <div id="quizContent" class="mt-2">
            <h1>Take Quiz</h1>
            <form id="quizForm" action="SubmitQuizServlet" method="post" onsubmit="stopTimer(); return validateForm();">
                
                <% if (questions != null && !questions.isEmpty()) {
                        int questionNumber = 1;
                        for (Question question : questions) {
                %>
                <div class="question p-3 border mb-4 col-md-9" id="question-<%= questionNumber %>">
                    <p style="font-weight: bold ">Question <%= questionNumber %>. <%= question.getQuestionText() %></p>
                    <div class="form-check">
                        <input type="radio" class="form-check-input" name="answer<%= questionNumber %>" value="A" onclick="markQuestionCompleted(<%= questionNumber %>)" <%= "A".equals(session.getAttribute("answer" + questionNumber)) ? "checked" : "" %>> 
                        <label class="form-check-label"><%= question.getAnswerA() %></label>
                    </div>
                    <div class="form-check">
                        <input type="radio" class="form-check-input" name="answer<%= questionNumber %>" value="B" onclick="markQuestionCompleted(<%= questionNumber %>)" <%= "B".equals(session.getAttribute("answer" + questionNumber)) ? "checked" : "" %>> 
                        <label class="form-check-label"><%= question.getAnswerB() %></label>
                    </div>
                    <div class="form-check">
                        <input type="radio" class="form-check-input" name="answer<%= questionNumber %>" value="C" onclick="markQuestionCompleted(<%= questionNumber %>)" <%= "C".equals(session.getAttribute("answer" + questionNumber)) ? "checked" : "" %>> 
                        <label class="form-check-label"><%= question.getAnswerC() %></label>
                    </div>
                    <div class="form-check">
                        <input type="radio" class="form-check-input" name="answer<%= questionNumber %>" value="D" onclick="markQuestionCompleted(<%= questionNumber %>)" <%= "D".equals(session.getAttribute("answer" + questionNumber)) ? "checked" : "" %>> 
                        <label class="form-check-label"><%= question.getAnswerD() %></label>
                    </div>
                </div>
                <% questionNumber++;
                        }
                    } else {
                %>
                <p>No questions available.</p>
                <% } %>
                <button type="submit" class="btn btn-success">Submit</button>
            </form>
            <div id="navigation" class="border p-3 mt-4 col-md-3">
                <div id="warning" class="alert alert-danger" style="display: none;">Please answer all questions before submitting.</div>
                <h5>Quiz Navigation</h5>
                <ul class="question-list list-unstyled">
                    <% if (questions != null && !questions.isEmpty()) {
                            int questionNumber = 1;
                            for (Question question : questions) {
                    %>
                    <li id="nav-question-<%= questionNumber %>" onclick="document.getElementById('question-<%= questionNumber %>').scrollIntoView();" class="p-2 border"><%= questionNumber %></li>
                        <% questionNumber++;
                                }
                            }
                        %>
                </ul>
                <p>Time left: <span id="timer"></span></p>
                <button type="submit" form="quizForm" class="btn btn-primary">Submit</button>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        
    </body>
</html>
