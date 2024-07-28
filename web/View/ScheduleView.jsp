<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

            .details-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .details-header p {
                margin: 0;
            }

            .modal-header .close {
                padding: 1rem;
                margin: -1rem -1rem -1rem auto;
            }
        </style>
        <title>Schedule</title>
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
                                                                        url: '../Tutorly/ScheduleController?service=viewLessonDetail&classid=${v.getAClass().id}&lessonid=${v.getId()}',
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
                                            <a href="${requestScope.lesson.getAClass().getTutor().getLinkmeet()}" class="nav-link">A link</a>
                                        </li>
                                    </ul>
                                </nav>
                                <hr />
                                <div class="details-header">
                                    <p><strong>Material:</strong></p>
                                    <!-- Button to Open the Modal -->
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#material" id="viewMaterialButton">
                                        View Material
                                    </button>
                                </div>
                                <div class="modal" id="material">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <!-- Modal Header -->
                                            <div class="modal-header">
                                                <h4 class="modal-title" style="color: #0E3C6E">Materials:</h4>
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            </div>
                                            <!-- Modal body -->
                                            <div class="modal-body">
                                                <div class="accordion" id="accordionExample">
                                                    <!-- Documents Accordion Item -->
                                                    <div class="accordion-item">
                                                        <h2 class="accordion-header" id="headingOne">
                                                            <button class="accordion-button" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                                                Documents
                                                            </button>
                                                        </h2>
                                                        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">
                                                            <div id="documentsContent" class="accordion-body"></div>
                                                        </div>
                                                    </div>
                                                    <!-- Slides Accordion Item -->
                                                    <div class="accordion-item">
                                                        <h2 class="accordion-header" id="headingTwo">
                                                            <button class="accordion-button collapsed" type="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo" id="materialAccordionButton">
                                                                Slides
                                                            </button>
                                                        </h2>
                                                        <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-parent="#accordionExample">
                                                            <div id="materialContent" class="accordion-body"></div>
                                                        </div>
                                                    </div>
                                                    <!-- Videos Accordion Item -->
                                                    <div class="accordion-item">
                                                        <h2 class="accordion-header" id="headingFour">
                                                            <button class="accordion-button collapsed" type="button" data-toggle="collapse" data-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour" id="videoAccordionButton">
                                                                Records/Videos
                                                            </button>
                                                        </h2>
                                                        <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour" data-parent="#accordionExample">
                                                            <div id="videoContent" class="accordion-body"></div>
                                                        </div>
                                                    </div>
                                                </div>  
                                            </div>
                                            <!-- Modal footer -->
                                            <div class="modal-footer">
                                                <button type="button" class="btn" style="background-color: #0E3C6E; color: white;" data-dismiss="modal">Close</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <hr />
                                <div class="details-header">
                                    <p><strong>Quiz:</strong></p>
                                    <!-- Button to Open the Modal -->
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#assigment">
                                        View Quiz
                                    </button>
                                </div>
                                <div class="modal" id="assigment">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <!-- Modal Header -->
                                            <div class="modal-header">
                                                <h4 class="modal-title" style="color: #0E3C6E">Quizs:</h4>
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            </div>
                                            <!-- Modal body -->
                                            <div class="modal-body">
                                                <nav class="navbar navbar-expand-sm">
                                                    <c:choose>
                                                        <c:when test="${not empty Quizoflesson}">
                                                            <c:forEach items="${Quizoflesson}" var="a">
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
                                                                        <button class="btn" style="background-color: #0E3C6E; color: white;" type="submit">Do Quiz</button>
                                                                    </li>
                                                                </ul>
                                                                <hr/>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <p>No Quiz for this lesson</p>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </nav>
                                            </div>
                                            <!-- Modal footer -->
                                            <div class="modal-footer">
                                                <button type="button" class="btn" style="background-color: #0E3C6E; color: white;" data-dismiss="modal">Close</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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
        <script>
                                                                            $(document).ready(function () {
                                                                                $('#materialAccordionButton').on('click', function () {
                                                                                    if (!$(this).data('loaded')) {
                                                                                        $('#materialContent').html(`
            <c:forEach var="x" items="${listmaterial}">
                                <li class="list-group-item list-group-item-action">
                                    <a href="Material?action=download&slotid=${slotid}&id=${x.id}&classid=${classid}">
                ${x.fileName}
                                    </a>
                                </li>
            </c:forEach>
                        `);
                                                                                        $(this).data('loaded', true);
                                                                                    }
                                                                                });

                                                                                $('#videoAccordionButton').on('click', function () {
                                                                                    if (!$(this).data('loaded')) {
                                                                                        $('#videoContent').html(`
            <c:forEach var="x" items="${listvideo}">
                                <li class="list-group-item list-group-item-action">
                                    <a href="Material?action=downloadVideo&slotid=${slotid}&id=${x.id}&classid=${classid}">
                ${x.fileName}
                                    </a>
                                </li>
            </c:forEach>
                        `);
                                                                                        $(this).data('loaded', true);
                                                                                    }
                                                                                });

                                                                                $('#viewMaterialButton').on('click', function () {
                                                                                    // Load documents content
                                                                                    if (!$('#documentsContent').data('loaded')) {
                                                                                        $('#documentsContent').html(`
            <c:forEach items="${document}" var="d">
                                <div class="accordion-body">
                                    <strong>${d.fileName} :</strong>
                                    <a href="#">${d.filePath}</a>
                                    <p style="margin: 0; color: #A2A2A2">${d.uploadedAt}</p>
                                </div>
                                <hr/>
            </c:forEach>
                        `);
                                                                                        $('#documentsContent').data('loaded', true);
                                                                                    }
                                                                                });
                                                                            });
        </script>

    </body>
</html>
