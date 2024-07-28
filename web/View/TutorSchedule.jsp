<%-- 
    Document   : TutorSchedule
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
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
       
        <title>Tutor Schedule</title>
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
            .table thead th {
                vertical-align: bottom;
                border-bottom: 2px solid #dee2e6;
            }
            .table-bordered td, .table-bordered th {
                border: 1px solid #dee2e6;
            }
            .table-responsive {
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
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
            .navbar-expand-sm .navbar-nav .nav-link {
                padding-right: 0.5rem;
                padding-left: 0.5rem;
            }
            .navbar-nav .nav-link {
                padding: 0.5rem;
            }
            .btn {
                padding: 0.375rem 0.75rem;
                font-size: 0.875rem;
            }
        </style>
    </head>
    <body>
        <%@ include file="TutorHeader.jsp" %>
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-md-10 bg-white rounded p-4 shadow">
                    <nav class="navbar navbar-expand-sm mb-3">
                        <span class="navbar-brand title">Schedule</span>
                    </nav>
                    <form class="row mb-3 justify-content-left" method="get" action="TutorScheduleController">
                        <div class="col-auto">
                            <div class="input-group">
                                <label class="input-group-text" for="selectedWeek">Select Week</label>
                                <select class="form-control" id="selectedWeek" name="selectedWeek" style="width: 250px;">
                                    <c:forEach var="week" items="${weeks}">
                                        <option value="${week}" ${week == selectedWeek ? 'selected' : ''}>${week}</option>
                                    </c:forEach>
                                </select>
                                <button type="submit" class="btn btn-primary" style="width: 80px;">Filter</button>
                            </div>
                        </div>
                    </form>

                    <div class="table-responsive">
                        <table class="table table-bordered text-center">
                            <thead>
                                <tr class="bg-light-gray">
                                    <th class="text-uppercase">Time</th>
                                    <th class="text-uppercase">Monday</th>
                                    <th class="text-uppercase">Tuesday</th>
                                    <th class="text-uppercase">Wednesday</th>
                                    <th class="text-uppercase">Thursday</th>
                                    <th class="text-uppercase">Friday</th>
                                    <th class="text-uppercase">Saturday</th>
                                    <th class="text-uppercase">Sunday</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="timeSlot" items="${'08:00,10:00,14:00,16:00,19:00'.split(',')}">
                                    <tr>
                                        <td class="align-middle">${timeSlot}</td>
                                        <c:forEach var="day" items="${'Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday'.split(',')}">
                                            <td>
                                                <c:forEach var="lesson" items="${lessons}">
                                                    <c:if test="${lesson.session.dayOfWeek eq day && fn:substring(lesson.session.startTime, 0, 5) eq timeSlot}">
                                                        <div class="lesson-box">
                                                            <span class="${lesson.status.toLowerCase()}">${lesson.status}</span>
                                                            <br>
                                                            <span>${lesson.getSession().id}</span> - <span>${lesson.getAClass().getLearner().name}</span>
                                                            <br>
                                                            <span>${lesson.getAClass().subject.name}</span>
                                                            <br>
                                                            <span><fmt:formatDate value="${lesson.date}" pattern="dd/MM/yyyy"/></span>
                                                        </div>
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                        </c:forEach>
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
        
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
