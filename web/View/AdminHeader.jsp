<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpSession" %>

<%
    // Get the current page name from the request URL
    String currentPage = request.getRequestURI() + "?" + request.getQueryString();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <title>Admin Dashboard</title>
</head>
<body>
    <nav class="navbar navbar-expand-sm navbar-dark" style="background-color: #0E3C6E;">
        <a class="navbar-brand" href="#">
            <img src="image/LOGO_TUTORLY (1).png" style="height: 50px" alt="Logo">
        </a>

        <!-- Centered Links -->
        <div class="collapse navbar-collapse justify-content-center">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link <%= currentPage.contains("dashboard") ? "active" : "" %>" href="${pageContext.request.contextPath}/AdminController?action=dashboard">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= currentPage.contains("learner") ? "active" : "" %>" href="${pageContext.request.contextPath}/AdminController?action=learner">Learner</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= currentPage.contains("tutor") ? "active" : "" %>" href="${pageContext.request.contextPath}/AdminController?action=tutor">Tutor</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= currentPage.contains("viewManager") ? "active" : "" %>" href="${pageContext.request.contextPath}/AdminController?action=viewManager">Manager</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= currentPage.contains("subject") ? "active" : "" %>" href="${pageContext.request.contextPath}/AdminController?action=viewSubject">Subject</a>
                </li>
            </ul>
        </div>

        <!-- Right Aligned Links -->
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="logout">Logout</a>
            </li>
        </ul>
    </nav>
</body>
</html>