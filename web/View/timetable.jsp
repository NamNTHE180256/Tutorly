<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Timetable</title>
    </head>
    <body>
        <h1>Weekly Timetable</h1>
        <a href="?week=${weekOffset - 1}">Previous Week</a> | 
        <a href="?week=${weekOffset + 1}">Next Week</a>
        <table border="1">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="session" items="${sessions}">
                    <tr>
                        <td>${session.title}</td>
                        <td>${session.description}</td>
                        <td>${session.date}</td>
                        <td>${session.startTime}</td>
                        <td>${session.endTime}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>