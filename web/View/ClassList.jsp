<%-- 
    Document   : ClassList
    Created on : Jun 17, 2024, 10:54:46 PM
    Author     : Tung Duong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>
            .head {
                margin: 0;
                padding: 0;
            }
            .navheader {
                margin: 0 20px;
                padding: 0;
            }
            .logo_img {
                height: 40px;
            }
            .head_link a {
                color: #0E3C6E;
            }
            .head_icon {
                margin-top: 15px;
                margin-right: 5px;
                font-size: 20px;
                color: #0E3C6E;
            }
            .student_profile_image {
                margin-top: 15px;
                height: 50px;
            }
            .learnername {
                margin-top: 15px;
                color: #0E3C6E;
            }
            .navbarmenu {
                margin: 0 20px 10px;
            }
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
        </style>
        <title>List of Classes</title>
    </head>
    <body>
        <%@ include file = "SearchTutorHeader.jsp" %>
        <div class="container">
            <h2>List of classes</h2>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Name Class</th>
                        <th>Time</th>
                        <th>Date</th>
                        <th>Total Sessions</th>
                        <th>In Progress</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="c" items="${classes}">
                        <tr>
                            <td>${c.learner.name} - ${c.subject.name}</td>
                            <c:set var="session" value="${sessionData[c.id]}" />
                            <td>${fn:substring(session.startTime, 0, 5)} - ${fn:substring(session.endTime, 0, 5)}</td>
                            <td><fmt:formatDate value="${c.startDate}" pattern="dd/MM/yyyy"/></td>
                            <td>${c.totalSession}</td>
                            <td>
                                <c:set var="progress" value="${progressMap[c.id]}" />
                                <c:choose>
                                    <c:when test="${progress != null}">
                                        <div class="progress">
                                            <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" style="width: ${progress}%;" aria-valuenow="${progress}" aria-valuemin="0" aria-valuemax="100">Finished</div>
                                            <div role="progressbar" style="width: ${100 - progress}%;" aria-valuenow="${100 - progress}" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                        ${progress}%
                                    </c:when>
                                    <c:otherwise>
                                        <div class="progress">
                                            <div class="progress-bar" role="progressbar" style="width: 0%;" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                        0%
                                    </c:otherwise>
                                </c:choose>
                            </td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <%@ include file = "tutor-footer.jsp" %>
    </body>
</html>
