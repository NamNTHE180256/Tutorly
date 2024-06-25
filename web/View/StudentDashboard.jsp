<%-- 
    Document   : StudentDashboard
    Created on : Jun 19, 2024, 10:46:10 AM
    Author     : Tung Duong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    // Get the current page name from the request URL
    String currentPage = request.getRequestURI() + "?" + request.getQueryString();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            .navmenuitem {
                margin-right: 25px;
                background-color: #0E3C6E;
                border-radius: 10px;
                box-shadow: 6px 6px 10px 0px rgba(0, 0, 0, 0.4);
            }
            .navmenuitem a {
                color: aliceblue;
            }
            .content {
                padding-top: 20px;
                background-color: #E6E6E6;
            }
            .progress-bar {
                background-color: #d3d3d3;
            }
            .progress {
                background-color: #d3d3d3;
            }
            html, body {
                overflow: hidden; /* don't do scrollbars */
                font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
                font-size: 14px;
                height: 100%;
                margin: 0;
                display: flex;
                flex-direction: column;
            }

            .container-fluid {
                display: flex;
                flex-grow: 1;
                overflow: hidden;
                padding: 0;
                margin: 0;
            }

            .content {
                display: flex;
                flex-grow: 1;
                overflow: hidden;
            }

            .classofstudent, .upcommingclass {
                margin-bottom: 20px;
                flex-shrink: 0;
            }

            .classofstudent {
                background-color: white;
                border-radius: 20px;
                padding-top: 20px;
            }

            .classofstudent .classofstudenthead {
                background-color: #F7B500;
                width: fit-content;
                border-radius: 5px;
                padding: 5px;
                margin-left: 30px;
                color: white;
                box-shadow: 6px 6px 10px 0px rgba(0, 0, 0, 0.4);
            }

            .classofstudent ul {
                display: inline-flex;
                text-decoration: none;
            }

            .classofstudent li {
                text-decoration: none;
            }

            .classofstudent .classongoing {
                text-align: center;
                font-size: 80px;
            }

            .classofstudent .classongoingfoot {
                text-align: center;
                color: #A2A2A2;
                padding-bottom: 10px;
            }

            .classofstudent .classcomplete {
                text-align: center;
                font-size: 80px;
            }

            .classofstudent .classcompletefoot {
                text-align: center;
                color: #A2A2A2;
                padding-bottom: 10px;
            }

            .ongoingclass {
                margin-left: 40px;
            }

            .completedclass {
                margin-right: 40px;
            }

            .upcommingclass {
                background-color: white;
                border-radius: 20px;
                padding: 20px;
                box-shadow: 6px 6px 10px 0px rgba(0, 0, 0, 0.4);
            }

            .upcommingclass .upcommingclasstitle {
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
            }

            .upcommingclass .upcommingclassdetails {
                font-size: 16px;
            }

            .upcommingclass .table th,
            .upcommingclass .table td {
                font-size: 16px;
            }
        </style>
        <title>Dashboard</title>
    </head>
    <body>
        <%@ include file="StudentHeader.jsp" %>
        <main style="background-color: #D9D9D9; flex-grow: 1; display: flex; flex-direction: column;">
            <div class="container-fluid content">
                <div class="row" style="flex-grow: 1; display: flex;">
                    <div class="col-sm-3 d-flex flex-column">
                        <div class="classofstudent">
                            <p class="classofstudenthead">Class</p>
                            <nav class="navbar navbar-expand-sm">
                                <ul class="navbar-nav mr-auto ongoingclass">
                                    <li class="nav-item">
                                        <p class="classongoing">${og}</p>
                                        <p class="classongoingfoot">ongoing</p>
                                    </li>
                                </ul>
                                <ul class="navbar-nav ml-auto completedclass">
                                    <li class="nav-item">
                                        <p class="classcomplete">${fn}</p>
                                        <p class="classcompletefoot">completed</p>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    <div class="col-sm-9 d-flex flex-column">
                        <div class="upcommingclass flex-grow-1">
                            <nav class="navbar navbar-expand-sm">
                                <ul class="navbar-nav mr-auto">
                                    <li class="nav-item">
                                        <h1 class="upcommingclasstitle">List of classes</h1>
                                    </li>
                                </ul>
                            </nav>
                            <div class="upcommingclassdetails">
                                <div class="container">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Name Class</th>
                                                <th>Date</th>
                                                <th>Time</th>
                                                <th>Total Sessions</th>
                                                <th>In Progress</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="c" items="${classes}">
                                                <tr>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/ClassDetail?classId=${c.id}">${c.tutor.name} - ${c.tutor.subject.name}</a>
                                                    </td>
                                                    <c:set var="session" value="${sessionData[c.id]}" />
                                                    <td><fmt:formatDate value="${c.startDate}" pattern="dd/MM/yyyy"/></td>
                                                    <td>${fn:substring(sessionData[c.id].startTime, 0, 5)}</td>
                                                    <td>${c.totalSession}</td>
                                                    <td>
                                                        <c:set var="progress" value="${progressMap[c.id]}" />
                                                        <div class="progress">
                                                            <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" style="width: ${progress}%;" aria-valuenow="${progress}" aria-valuemin="0" aria-valuemax="100">Finished</div>
                                                            <div role="progressbar" style="width: ${100 - progress}%;" aria-valuenow="${100 - progress}" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                        ${progress}%
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@ include file="tutor-footer.jsp" %>
    </body>
</html>
