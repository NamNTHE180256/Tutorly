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
        <meta charset="UTF-8">
        <title>Question List</title>
    </head>
    <body>

        <h1>Uploaded Questions</h1>
        <table border="1">
            <tr>
                <th>Number</th>
                <th>Question</th>
                <th>Answer A</th>
                <th>Answer B</th>
                <th>Answer C</th>
                <th>Answer D</th>
                <th>Correct Answer</th>
            </tr>
            <%
                List<Question> questions = (List<Question>) request.getAttribute("questions");
                if (questions != null && !questions.isEmpty()) {
                    for (Question question : questions) {
            %>
            <tr>
                <td><%= question.getNumber() %></td>
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
                <td colspan="7">No questions uploaded.</td>
            </tr>
            <%
                }
            %>
        </table>

        <h2>ClassId: <%= request.getAttribute("lessonId") %></h2>

        <h2>Lesson ID: <%= request.getAttribute("lessonId") %></h2>

        <form action="SetQuizTimeServlet" method="post">
            <input type="hidden" name="lessonId" value="<%= request.getAttribute("lessonId") %>">
            <input type="hidden" name="classId" value="<%= request.getAttribute("classId") %>">

            <label for="quizTime">Please choose time of the quiz:</label>
            <select name="quizTime" id="quizTime">
                <option value="15">15 seconds</option>
                <option value="900">15 minutes</option>
                <option value="1800">30 minutes</option>
                <option value="2700">45 minutes</option>
                <option value="3600">60 minutes</option>
                <option value="5400">90 minutes</option>
            </select>
            <button type="submit">Set Time</button>
        </form>
    </body>
</html>
