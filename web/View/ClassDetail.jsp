<%-- 
    Document   : ClassDetail
    Created on : 15 thg 6, 2024, 05:03:04
    Author     : asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*, Model.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="DAO.*" %>
<%
    // Get class ID from request
//    int classId = Integer.parseInt(request.getParameter("classId"));
    int classId = 1;
    // Fetch class details using the provided function
   AClassDAO classDAO = new AClassDAO();
    AClass aClass = classDAO.getClassById(classId);

    // Get current date or date from request parameters
    // Format dates
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tutorName = aClass.getTutor().getName();

    // Get class details
    String studentName = aClass.getLearner().getName();
    int totalSlots = aClass.getTotalSession();
    Date startDate = aClass.getStartDate();
    Date endDate = aClass.getEndDate();
    String subjectName = aClass.getTutor().getSubject().getName();
    int attendance = 10; // This should be fetched dynamically based on attended sessions
    int progress = (attendance * 100) / totalSlots;

%>
<!DOCTYPE html>
<html lang="en">
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
                /*                overflow: hidden;  don't do scrollbars */
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

            .todo, .classofstudent, .upcommingclass {
                margin-bottom: 20px;
                flex-shrink: 0;
            }

            .todo {
                background-color: white;
                border-radius: 20px;
                padding-top: 20px;
                height: 100%;
            }

            .todo .todohead {
                background-color: #F7B500;
                width: fit-content;
                border-radius: 5px;
                padding: 5px;
                margin-left: 30px;
                color: white;
                box-shadow: 6px 6px 10px 0px rgba(0, 0, 0, 0.4);
            }

            .todo .todonumber {
                text-align: center;
                font-size: 80px;
            }

            .todo .foot {
                text-align: center;
                color: #A2A2A2;
                padding-bottom: 10px;
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

            .upcommingclassnote {
                color: #A2A2A2;
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
            .todo p{
                font-size: 20px;
                margin: 20px;
            }

            #calendar {
                flex-grow: 1;
            }

            .leftupcoming h4 {
                color: #0E3C6E;
            }

            .leftupcoming p {
                color: #0E3C6E;
            }

            .rightupcoming h4 {
                color: #0E3C6E;
            }
        </style>
        <title>Dashboard</title>
    </head>
    <body>
        <header>
            <%@ include file = "StudentHeader.jsp" %>
        </header>

        <main style="background-color: #D9D9D9; flex-grow: 1; display: flex; flex-direction: column;">

            <!--Content-->
            <div class="container-fluid content">
                <div class="row" style="flex-grow: 1; display: flex;">
                    <div class="col-sm-4 d-flex flex-column">
                        <div class="todo">
                            <p class="todohead" style="font-size: 20px;font-weight: bold">                    
                                <c:choose>
                                    <c:when test="${sessionScope.user.role == 'tutor'}">
                                    <p class="todohead" style="font-size: 20px; font-weight: bold">Class : <%= studentName %></p>
                                </c:when>
                                <c:otherwise>
                                    <p class="todohead" style="font-size: 20px; font-weight: bold">Class : MR <%= tutorName %></p>
                                </c:otherwise>
                            </c:choose> </p>
                            <div class="d-flex justify-content-between">
                                <p>Total Slots:</p>
                                <p><%= totalSlots %></p>
                            </div>
                            <div class="d-flex justify-content-between">
                                <p>Start Date:</p>
                                <p><%= sdf.format(startDate) %></p>
                            </div>                            
                            <div class="d-flex justify-content-between">
                                <p>End Date:</p>
                                <p><%= sdf.format(endDate) %></p>
                            </div>                            
                            <div class="d-flex justify-content-between">
                                <p>Subject:</p>
                                <p><%= subjectName %></p>
                            </div>                            
                            <div class="d-flex justify-content-between">
                                <p>Attendance:</p>
                                <p><%= attendance %>/<%= totalSlots %></p>
                            </div>
                        </div>


                    </div>
                    <!-- Session occur in 5 hours -->
                    <div class="col-sm-8 d-flex flex-column">
                        <div class="upcommingclass flex-grow-1">
                            <!--HEAD-->
                            <nav class="navbar navbar-expand-sm">
                                <!-- Left -->
                                <ul class="navbar-nav mr-auto">
                                    <li class="nav-item">
                                        <h1 class="upcommingclasstitle">Calendar</h1>
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
                                                                        url: 'http://google.com/',
                                                                        end: '${v.getDate()}T${v.getSession().getEndTime()}',
                                                                                        className: 'custom-event'
                                                                                }${not empty v and v != vector[vector.size() - 1] ? ',' : ''}
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
                </div>
            </div>
        </main>

    </body>
</html>