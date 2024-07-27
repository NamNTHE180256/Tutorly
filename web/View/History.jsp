<%-- 
    Document   : History
    Created on : Jun 3, 2024, 2:42:13 PM
    Author     : Tung Duong
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <title>History Lesson</title>
        <style>
            body {
                background-color: #A2A2A2;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
                margin: 0;
            }
            .container-fluid {
                flex: 1;
                padding: 20px;
            }
            .footer {
                background-color: #0E3C6E;
                color: white;
                padding: 20px 0;
                text-align: center;
            }
            .bg-light-gray {
                background-color: #f7f7f7;
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
            .title {
                color: #0E3C6E;
                font-size: 26px;
                font-weight: 700;
            }
        </style>
    </head>
    <body>
        <c:choose>
            <c:when test="${tutor != null}">
                <%@ include file="TutorHeader.jsp" %>
            </c:when>
            <c:when test="${learner != null}">
                <%@ include file="SearchTutorHeader.jsp" %>
            </c:when>
        </c:choose>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-8 mx-auto" style="background-color: white; border-radius: 20px; padding: 20px; box-shadow: 0px 5px 15px 0px rgba(0, 0, 0, 0.35);">
                    <nav class="navbar navbar-expand-sm ">
                        <ul class="navbar-nav col-md-8">
                            <li class="nav-item">
                                <p class="title">Class Details</p>
                            </li>
                        </ul>
                        <ul class="navbar-nav col-md-4">
                            <form action="history" method="GET">
                                <select class="form-control" name="class" onchange="this.form.submit()" >
                                    <option value="" >Select class</option>
                                    <c:forEach items="${list}" var="l">
                                        <option value="${l.id}" ${classParam == l.id ? 'selected': ''}>${l.tutor.subject.name} - ${l.learner.name}</option>
                                    </c:forEach>
                                </select>
                            </form>
                        </ul>
                    </nav>
                    <div class="table-responsive">
                        <table class="table table-bordered text-center table-hover table-striped">
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
                                        <td class="${l.status eq 'Finished' ? 'text-success' : 'text-warning'}">${l.status}</td>
                                        <td><a href="lessonDetailControllers?classid=${l.getAClass().id}&lessonId=${l.getId()}" class="btn btn-primary">View lesson details</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <c:if test="${empty lessons}">
                            <p class="text-center">No lessons scheduled.</p>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <footer class="footer">
            <div class="container">
                <p style="margin: 0; font-size: 16px;">
                    Mọi góp ý, thắc mắc xin liên hệ Công ty cung cấp dịch vụ gia sư | Email: <a href="mailto:Tutory@gmail.com" style="color: #FFC107;">Tutory@gmail.com</a> | Điện thoại: <a href="tel:0123456789" style="color: #FFC107;">0123456789</a>
                </p>
                <p style="margin: 0; font-size: 16px;">© 2024 Power by TUTORLY</p>
            </div>
        </footer>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
