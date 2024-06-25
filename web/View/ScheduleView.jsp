<%-- 
    Document   : Dashboard
    Created on : May 29, 2024, 3:11:59 AM
    Author     : TRANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src='js/newjavascript.js'></script>
        <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/core@5.10.1/main.min.js'></script>
        <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@fullcalendar/core@5.10.1/main.min.css' />

        <style>
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

            .upcommingclass {
                background-color: white;
                border-radius: 20px;
                padding-top: 10px;
                padding-bottom: 20px;
                padding: 40px;
                display: flex;
                flex-direction: column;
                height: 100%;
                overflow: hidden;
                flex-grow: 1;
            }

            .upcommingclasstitle {
                color: #0E3C6E;
                font-weight: bold;
            }

            .upcommingclassdedtails {
                background-color: #E6E6E6;
                border-radius: 20px;
                flex: 1;
                overflow: hidden;
                display: flex;
                flex-direction: column;
                padding: 20px;
                box-sizing: border-box;
            }

            #calendar-container {
                height: 100%;
                width: 100%;
                box-sizing: border-box;
                display: flex;
                flex-direction: column;
                flex-grow: 1;
                background-color: white;
                padding: 20px;
                overflow: hidden;
                box-shadow: 0px 5px 15px 0px rgba(0, 0, 0, 0.35);
            }

            #calendar {
                flex-grow: 1;
            }
        </style>
        <title>Dashboard</title>
    </head>
    <body>

        <%@ include file="StudentHeader.jsp" %>
        <main style="background-color: #D9D9D9; flex-grow: 1; display: flex; flex-direction: column;">

            <!--Content-->
            <div class="container-fluid content" style="padding-bottom: 20px">
                <div class="row" style="flex-grow: 1; display: flex;">

                    <!-- Session occur in 5 hours -->
                    <div class="col-sm-9 d-flex flex-column">
                        <div class="upcommingclass flex-grow-1">
                            <!--HEAD-->
                            <nav class="navbar navbar-expand-sm">
                                <!-- Left -->
                                <ul class="navbar-nav mr-auto">
                                    <li class="nav-item">
                                        <h1 class="upcommingclasstitle">Schedule</h1>
                                    </li>
                                </ul>
                            </nav>

                            <!--Content-->
                            <div class="upcommingclassdedtails">
                                <script>
                                    document.addEventListener('DOMContentLoaded', function () {
                                        var calendarEl = document.getElementById('calendar');

                                        var events = [
                                            <c:forEach items="${lesson_vector}" var="v">
                                                {
                                                    title: '${v.getAClass().getTutor().getSubject().getName()}-${v.getAClass().getTutor().getName()}',
                                                    start: '${v.getDate()}T${v.getSession().getStartTime()}',
                                                    url: '../Tutorly/ScheduleController?service=viewLessonDetail&lessonid=${v.getId()}',
                                                    end: '${v.getDate()}T${v.getSession().getEndTime()}',
                                                    className: 'custom-event'
                                                }<c:if test="${not empty v and v != lesson_vector[lesson_vector.size() - 1]}">,</c:if>
                                            </c:forEach>
                                        ];

                                        var calendar = new FullCalendar.Calendar(calendarEl, {
                                            height: '100%',
                                            expandRows: true,
                                            slotMinTime: '08:00',
                                            slotMaxTime: '21:00',
                                            headerToolbar: {
                                                left: 'prev,next today',
                                                center: 'title',
                                                right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
                                            },
                                            initialView: 'dayGridMonth',
                                            initialDate: new Date().toISOString().split('T')[0], // Current date
                                            navLinks: true, // can click day/week names to navigate views
                                            editable: false,
                                            selectable: false,
                                            nowIndicator: true,
                                            dayMaxEvents: true, // allow "more" link when too many events
                                            events: events
                                        });

                                        calendar.render();
                                    });
                                </script>
                                <div id='calendar-container'>
                                    <div id='calendar'></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Conditionally display the right column -->
                    <c:if test="${not empty service}">
                        <div class="col-sm-3 d-flex flex-column" style="background-color: white; border-radius: 20px;
                             height: fit-content; padding: 10px; padding-left: 10px; padding-right: 10px;">
                            <div>
                                <p style="font-size: 30px; color: #0E3C6E; font-weight: bolder; padding-left: 20px"><strong>Lesson details:</strong></p>
                                <hr />
                                <nav class="navbar navbar-expand-sm">
                                    <!-- Left -->
                                    <ul class="navbar-nav mr-auto">
                                        <li class="nav-item">
                                            <p class="nav-link" style="padding: 0"><strong>Join lesson:</strong></p>
                                        </li>
                                    </ul>
                                    <!-- Right -->
                                    <ul class="navbar-nav ml-auto">
                                        <li class="nav-item">
                                            <a href="#" class="nav-link">A link</a>
                                        </li>
                                    </ul>
                                </nav>
                                <hr />
                                <nav class="navbar navbar-expand-sm">
                                    <!-- Left -->
                                    <ul class="navbar-nav mr-auto">
                                        <li class="nav-item">
                                            <p><strong>Material:</strong></p>
                                        </li>
                                    </ul>
                                    <!-- Right -->
                                    <ul class="navbar-nav ml-auto">
                                        <li class="nav-item">
                                            <!-- Button to Open the Modal -->
                                            <button type="button" class="btn" data-toggle="modal"
                                                    data-target="#material" style="background-color: #0E3C6E; color: white;">
                                                View Material
                                            </button>

                                            <!-- The Modal -->
                                            <div class="modal" id="material">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <!-- Modal Header -->
                                                        <div class="modal-header">
                                                            <h4 class="modal-title" style="color: #0E3C6E">Materials:</h4>
                                                            <button type="button" class="close"
                                                                    data-dismiss="modal">&times;</button>
                                                        </div>
                                                        <!-- Modal body -->
                                                        <div class="modal-body">
                                                            Modal body..
                                                        </div>
                                                        <!-- Modal footer -->
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn" style="background-color: #0E3C6E; color: white;"
                                                                    data-dismiss="modal">Close</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </nav>
                                <hr />
                                <nav class="navbar navbar-expand-sm">
                                    <!-- Left -->
                                    <ul class="navbar-nav mr-auto">
                                        <li class="nav-item">
                                            <p><strong>Assignment:</strong></p>
                                        </li>
                                    </ul>
                                    <!-- Right -->
                                    <ul class="navbar-nav ml-auto">
                                        <li class="nav-item">
                                            <!-- Button to Open the Modal -->
                                            <button type="button" class="btn" data-toggle="modal"
                                                    data-target="#assigment" style="background-color: #0E3C6E; color: white;">
                                                View Assignment
                                            </button>

                                            <!-- The Modal -->
                                            <div class="modal" id="assigment">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <!-- Modal Header -->
                                                        <div class="modal-header">
                                                            <h4 class="modal-title" style="color: #0E3C6E">Assignments:</h4>
                                                            <button type="button" class="close"
                                                                    data-dismiss="modal">&times;</button>
                                                        </div>
                                                        <!-- Modal body -->
                                                        <div class="modal-body">
                                                            <nav class="navbar navbar-expand-sm">
                                                                <c:choose>
                                                                    <c:when test="${not empty assignmentoflesson}">
                                                                        <c:forEach items="${assignmentoflesson}" var="a">
                                                                            <!-- Left -->
                                                                            <ul class="navbar-nav mr-auto">
                                                                                <li class="nav-item">
                                                                                    <a class="nav-link" style="font-size: 16px"><strong>${a.getFileName()}</strong></a>
                                                                                    <p style="color: #A2A2A2;">${a.getCreatedAt()}</p>
                                                                                </li>
                                                                            </ul>
                                                                            <!-- Right -->
                                                                            <ul class="navbar-nav ml-auto" >
                                                                                <li class="nav-item">
                                                                                    <button class="btn" style="background-color: #0E3C6E; color: white; " type="submit">Do assignment</button>
                                                                                </li>
                                                                            </ul>
                                                                            <hr/>
                                                                        </c:forEach>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <p>No assignment for this lesson</p>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </nav>
                                                        </div>
                                                        <!-- Modal footer -->
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn" style="background-color: #0E3C6E; color: white;"
                                                                    data-dismiss="modal">Close</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </nav>
                                <hr />
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </main>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </body>
</html>
