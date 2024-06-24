<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <title>Tutor Schedule</title>
        <style>
            body {
                background-color: #A2A2A2;

            }
            .leftconttent {
                padding: 10px;
                height: 100%;
            }
            .leftconttent .nav-item {
                width: 100px;
            }
            .rating {
                display: inline;
                align-items: center;
                text-align: center;
            }
            .fee {
                display: inline-flex;
            }
            .buttons {
                flex-direction: column;
                align-items: flex-end;
            }
            .btn-trial, .btn-register {
                background-color: #0E3C6E;
                color: white;
                padding: 10px 20px;
                border-radius: 5px;
                margin-top: 10px;
                text-align: center;
            }
            .btn-register {
                background-color: #6c757d;
            }
            .tutorimg {
                width: 200px;
                height: 200px;
                margin-right: 20px;
            }
            .tutor-name {
                color: #0E3C6E;
                font-size: 24px;
                font-weight: bold;
            }
            .subject, .students {
                display: flex;
                align-items: center;
            }
            .subject i, .students i {
                font-size: 20px;
            }
            .subject h3, .students h3 {
                font-size: 20px;
                margin: 0;
            }
            .bg-light-gray {
                background-color: #f7f7f7;
            }
            .table-bordered thead td, .table-bordered thead th {
                border-bottom-width: 2px;
            }
            .table thead th {
                vertical-align: bottom;
                border-bottom: 2px solid #dee2e6;
            }
            .table-bordered td, .table-bordered th {
                border: 1px solid #dee2e6;
            }
            .bg-sky.box-shadow {
                box-shadow: 0px 5px 0px 0px #0E3C6E;
            }
            .bg-sky {
                background-color: #0E3C6E;
            }
            .padding-15px-lr {
                padding-left: 15px;
                padding-right: 15px;
            }
            .padding-5px-tb {
                padding-top: 5px;
                padding-bottom: 5px;
            }
            .margin-10px-bottom {
                margin-bottom: 10px;
            }
            .border-radius-5 {
                border-radius: 5px;
            }
            .margin-10px-top {
                margin-top: 10px;
            }
            .font-size14 {
                font-size: 14px;
            }
            .text-light-gray {
                color: #d6d5d5;
            }
            .font-size13 {
                font-size: 13px;
            }
            .table-bordered td, .table-bordered th {
                border: 1px solid #dee2e6;
            }
            .table td, .table th {
                padding: .75rem;
                vertical-align: top;
                border-top: 1px solid #dee2e6;
            }
            body {
                margin-top: 20px;
                background: #eee;
            }
            .finished {
                color: white;
                background-color: #0E3C6E;
                padding: 5px;
                border-radius: 5px;
            }

            .scheduled {
                color: black;
                background-color: #FFC107;
                padding: 5px;
                border-radius: 5px;
            }
        </style>
    </head>

    <body>
        <c:choose>
            <c:when test="${tutor != null}">
                <%@ include file="TutorHeader.jsp" %>
            </c:when>
            <c:when test="${learner != null}">
                <%@ include file="StudentHeader.jsp" %>
            </c:when>
        </c:choose>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-8" style="background-color: white; margin: 20px; margin-left: 40px; border-radius: 20px; padding: 20px; box-shadow: 0px 5px 15px 0px rgba(0, 0, 0, 0.35);">
                    <nav class="navbar navbar-expand-sm">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <p class="title text-uppercase">Lesson</p>
                            </li>
                        </ul>
                    </nav>

                    <div class="table-responsive">
                        <table class="table table-bordered text-center">
                            <thead>
                                <tr class="bg-light-gray">
                                    <th class="text-uppercase">Session</th>
                                    <th class="text-uppercase">Subject</th>
                                    <th class="text-uppercase">Time</th>
                                    <th class="text-uppercase">Date</th>
                                    <th class="text-uppercase">Status</th>
                                    <th class="text-uppercase">Action</th>

                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="l" items="${lessons}">
                                    <tr>
                                         
                                        <td>${l.session.id}</td>
                                        <td>${l.getAClass().tutor.subject.name}</td>
                                        <td>${fn:substring(l.session.startTime, 0, 5)}</td>
                                        <td><fmt:formatDate value="${l.date}" pattern="dd/MM/yyyy"/></td>
                                        <td style="color: ${l.status eq 'Finished' ? 'green' : 'orange'}">${l.status}</td>
                                        <td>
                                            <a href="#">View Materials </a>
                                        </td> 
                                    </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                        <c:if test="${empty lessons}">
                            <p class="text-center">No lessons scheduled.</p>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <%@ include file = "tutor-footer.jsp" %>
</html>