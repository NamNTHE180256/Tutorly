<%-- 
    Document   : ViewClassnew
    Created on : Jul 26, 2024, 12:49:44 PM
    Author     : TRANG
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="DAO.*" %>
<%
      AClassDAO classDAO = new AClassDAO();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" href="../style/assignment.css">
        <title>View Class</title>

    </head>
    <body>
        <header>
            <c:choose>
                <c:when test="${sessionScope.user.role eq 'tutor'}">
                    <jsp:include page="TutorHeader.jsp"/>
                </c:when>
                <c:otherwise>
                    <jsp:include page="SearchTutorHeader.jsp"/>
                </c:otherwise>
            </c:choose>
        </header>
        <span style="margin: 20px;"></span>
        <button style="margin: 15px; color: white; background: #0E3C6E" class="btn btn-default dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
            Status of class
        </button>
        <ul class="dropdown-menu" role="menu">
            <li><a class="dropdown-item" href="ViewClassnew">None (All classess) </a></li>
            <li>
                <a class="dropdown-item" href="ViewClassnew?status=finished">Finish</a>
            </li>
            <li><a class="dropdown-item" href="ViewClassnew?status=ongoing">Ongoing </a></li>
            <li><a class="dropdown-item" href="ViewClassnew?status=trial">Trial</a></li>
        </ul>
        <div class="container">
            <div class="row row-cols-4">
                <c:forEach var="c" items="${classes}">
                    <div class="col-4">
                        <div class="card">
                            <div class="card-body">
                                <c:choose>
                                    <c:when test="${sessionScope.user.role eq 'learner'}">
                                        <h5 class="card-title" style="background-color: #0E3C6E; padding: 5px;
                                            color: white; border-radius: 3px; text-align: center;">
                                            ${c.tutor.name} - ${c.tutor.subject.name}
                                        </h5>
                                    </c:when>
                                    <c:when test="${sessionScope.user.role eq 'tutor'}">
                                        <h5 class="card-title" style="background-color: #0E3C6E; padding: 5px;
                                            color: white; border-radius: 3px; text-align: center;">
                                            ${c.learner.name} - ${c.tutor.subject.name}
                                        </h5>
                                    </c:when>
                                    <c:otherwise>
                                        <h5 class="card-title" style="background-color: #0E3C6E; padding: 5px;
                                            color: white; border-radius: 3px; text-align: center;">
                                            Role not recognized
                                        </h5>
                                    </c:otherwise>
                                </c:choose>
                                <c:set var="session" value="${sessionData[c.id]}" />
                                <p class="card-text">
                                    Session: ${fn:substring(sessionData[c.id].startTime, 0, 5)} - ${fn:substring(sessionData[c.id].endTime, 0, 5)}
                                </p>
                                <p class="card-text">
                                    Start date : <fmt:formatDate value="${c.startDate}" pattern="dd/MM/yyyy"/>
                                </p>
                                <p class="card-text">
                                    End date: <fmt:formatDate value="${c.endDate}" pattern="dd/MM/yyyy"/>
                                </p>
                                <p class="card-text">
                                    Total lesson : ${c.totalSession}
                                </p>
                                <c:set var="att" value="${attendance[c.id]}" />
                                <p class="card-text">
                                    Attendance : ${att} /  ${c.totalSession}
                                </p>
                                <c:set var="progress" value="${progressMap[c.id]}" />
                                <p class="card-text">
                                    Progress :  ${progress}% </p>

                                <div class="progress" style="height: 30px">
                                    <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" style="width: ${progress}%;" aria-valuenow="${progress}" aria-valuemin="0" aria-valuemax="100">Finished</div>
                                    <div role="progressbar" style="width: ${100 - progress}%;" aria-valuenow="${100 - progress}" aria-valuemin="0" aria-valuemax="100"></div>
                                </div> 


                                <p class="card-text ${c.status eq 'finished' ? 'text-success' : 'text-warning'}">
                                    Status : ${c.status}
                                </p>
                                <span style="display: inline-flex;">
                                    <a href="history?classId=${c.id}" class="btn btn-outline-primary" style="margin-right: 15px;">
                                        View details
                                    </a>
                                    <!--<button type="button" class="btn btn-outline-danger">Cancel class</button>-->
                                    <c:choose>
                                        <c:when test="${sessionScope.user.role eq 'learner' && c.status eq 'finished'}">
                                            <a href="RatingTutorServlet?classId=${c.id}" type="button" class="btn btn-outline-warning">Rate tutor</a>
                                        </c:when>
                                        <c:when test="${sessionScope.user.role eq 'learner' && c.status ne 'finished'}">
                                            <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#exampleModal-${c.id}">
                                                Cancel class
                                            </button>
                                            <div class="modal fade" id="exampleModal-${c.id}" tabindex="-1" aria-labelledby="exampleModalLabel-${c.id}" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <form method="get" action="CancelClassController">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="exampleModalLabel-${c.id}">Are you sure to cancel this class</h5>
                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                ${c.tutor.name} - ${c.tutor.subject.name} -  <fmt:formatDate value="${c.startDate}" pattern="dd/MM/yyyy"/>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <input type="hidden" name="class_id" value="${c.id}">
                                                                <button class="btn btn-primary" type="submit">Confirm</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <!-- Show nothing -->
                                        </c:otherwise>
                                    </c:choose>

                                </span>
                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </body>
</html>
