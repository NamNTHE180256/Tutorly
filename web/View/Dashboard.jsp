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
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src='js/newjavascript.js'></script>
        <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/core@5.10.1/main.min.js'></script>
        <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@fullcalendar/core@5.10.1/main.min.css' />

        <style>
            html, body {
                overflow: hidden;
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
                flex-grow: 1;
                display: flex;
                flex-direction: column;
                padding: 20px;
                box-sizing: border-box;
                overflow: auto;

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

        <%@ include file = "StudentHeader.jsp" %>
        <main style="background-color: #D9D9D9; flex-grow: 1; display: flex; flex-direction: column;">

            <!--Content-->
            <div class="container-fluid content" style="padding-bottom: 20px">
                <div class="row" style="flex-grow: 1; display: flex;">
                    <div class="col-sm-3 d-flex flex-column">
                        <div class="todo">
                            <p class="todohead">To do</p>
                            <!--Number of Quiz which have status : Not completed-->
                            <p class="todonumber">${todoQuiz}</p>
                            <p class="foot">Quiz(s) not completed</p>
                        </div>

                        <img style="width: 310px" src="image/Dashboard3.png">
                    </div>
                    <!-- Session occur in 5 hours -->
                    <div class="col-sm-9 d-flex flex-column" >
                        <div class="upcommingclass flex-grow-1">
                            <!--HEAD-->
                            <nav class="navbar navbar-expand-sm">
                                <!-- Left -->
                                <ul class="navbar-nav mr-auto">
                                    <li class="nav-item">
                                        <h1 class="upcommingclasstitle">Upcoming...</h1>
                                    </li>
                                </ul>
                            </nav>

                            <!--Content-->
                            <div class="upcommingclassdedtails" style="height: 450px">
                                <script>
                                    document.addEventListener('DOMContentLoaded', function() {
                                    var calendarEl = document.getElementById('calendar');
                                    var calendar = new FullCalendar.Calendar(calendarEl, {
                                    headerToolbar: {
                                    left: 'prev,next today',
                                            center: 'title',
                                            right: 'listDay,listWeek'
                                    },
                                            views: {
                                            listDay: { buttonText: 'list day' },
                                                    listWeek: { buttonText: 'list week' }
                                            },
                                            initialView: 'listWeek',
                                            initialDate: new Date().toISOString().split('T')[0],
                                            navLinks: true,
                                            editable: true,
                                            dayMaxEvents: true,
                                            events: [
                                    <c:forEach items="${lesson_vector}" var="v">
                                            {
                                            title: '${v.getAClass().getTutor().getSubject().getName()} - ${v.getAClass().getTutor().getName()}',
                                                                start: '${v.getDate()}T${v.getSession().getStartTime()}',
                                                                                    url: 'http://google.com/',
                                                                                    end: '${v.getDate()}T${v.getSession().getEndTime()}',
                                                                                                        className: 'custom-event'
                                                                                                }<c:if test="${v != lesson_vector[lesson_vector.size() - 1]}">,</c:if>
                                    </c:forEach>
                                                                                                ]
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
