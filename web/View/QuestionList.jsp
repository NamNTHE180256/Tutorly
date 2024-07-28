<%-- 
    Document   : QuestionList
    Created on : 21 thg 7, 2024, 23:21:45
    Author     : asus
--%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.Question"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Question List</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    </head>
    <body>

        <h1 style="background: #0E3C6E; color: white; width: fit-content; padding: 2px; border-radius: 5px;">Uploaded Questions</h1>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">Number</th>
                    <th scope="col">Question</th>
                    <th scope="col">Answer A</th>
                    <th scope="col">Answer B</th>
                    <th scope="col">Answer C</th>
                    <th scope="col">Answer D</th>
                    <th scope="col">Correct Answer</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Question> questions = (List<Question>) request.getAttribute("questions");
                if (questions != null && !questions.isEmpty()) {
                    for (Question question : questions) {
                %>
                <tr>
                    <td scope="row"><%= question.getNumber() %></td>
                    <td><%= question.getQuestionText() %></td>
                    <td><%= question.getAnswerA() %></td>
                    <td><%= question.getAnswerB() %></td>
                    <td><%= question.getAnswerC() %></td>
                    <td><%= question.getAnswerD() %></td>
                    <td><%= question.getCorrectAnswer() %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7" >No questions uploaded.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>




        <h2>ClassId: <%= request.getAttribute("lessonId") %></h2>

        <h2>Lesson ID: <%= request.getAttribute("lessonId") %></h2>

        <form action="SetQuizTimeServlet" method="get">
            <input type="hidden" name="lessonId" value="<%= request.getAttribute("lessonId") %>">
            <input type="hidden" name="classId" value="<%= request.getAttribute("classId") %>">

            <div class="dropdown">
                <button class="btn btn-default dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Please choose time of the quiz
                </button>
                <ul class="dropdown-menu" role="menu" id="quizTimeMenu">
                    <li><a class="dropdown-item" value="15" href="#" onclick="selectTime(this)">15 seconds</a></li>
                    <li><a class="dropdown-item" value="900" href="#" onclick="selectTime(this)">15 minutes</a></li>
                    <li><a class="dropdown-item" value="1800" href="#" onclick="selectTime(this)">30 minutes</a></li>
                    <li><a class="dropdown-item" value="2700" href="#" onclick="selectTime(this)">45 minutes</a></li>
                    <li><a class="dropdown-item" value="3600" href="#" onclick="selectTime(this)">60 minutes</a></li>
                    <li><a class="dropdown-item" value="5400" href="#" onclick="selectTime(this)">90 minutes</a></li>
                </ul>
            </div>
            <input type="hidden" id="selectedQuizTime" name="quizTime" value="">

            <script>
                function selectTime(element) {
                    var selectedTime = element.getAttribute('value');
                    document.getElementById('selectedQuizTime').value = selectedTime;
                    // Optionally, update the button text to show the selected time
                    document.querySelector('.dropdown-toggle').innerText = "Selected: " + selectedTime + " seconds";
                }
            </script>


            <button type="submit" class="btn btn-default">Set Time</button>

        </form>
    </body>
</html>
