<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                margin-top: 0;
                margin-bottom: 0;
                margin-left: 20px;
                margin-right: 20px;
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
            .tutor_profile_image {
                margin-top: 15px;
                height: 50px;
            }
            .learnername {
                margin-top: 15px;
                color: #0E3C6E;
            }
            .navbarmenu {
                margin-top: 0;
                margin-bottom: 10px;
                margin-left: 20px;
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
        </style>
        <title>Tutor</title>
    </head>
    <body>
        <header class="head">
            <nav class="navbar navbar-expand-sm navheader">
                <a href="View/HomePage.jsp">
                    <img src="${pageContext.request.contextPath}/image/LOGO_TUTORLY.png" class="logo_img">
                </a>
                <!-- Right -->
                <ul class="navbar-nav ml-auto">
                    <!-- Notification from system or tutor -->
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <i class="fa-solid fa-bell head_icon"></i>
                        </a>
                    </li>
                    <!-- Message from tutor -->
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <i class="fa-regular fa-message head_icon"></i>
                        </a>
                    </li>
                    <!-- Schedule of student -->
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <i class="fa-regular fa-calendar head_icon"></i>
                        </a>
                    </li>
                    <!-- List of favorite tutors -->
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <i class="fa-regular fa-heart head_icon"></i>
                        </a>
                    </li>
                    <!-- Tutor name -->
                    <li class="nav-item">
                        <a href="TutorProfileController"><p class="nav-link learnername">${tutor.name}</p></a>
                    </li>
                    <!-- Tutor profile image -->
                    <li class="nav-item">
                        <a class="nav-link" href="TutorProfileController">
                            <img class="tutor_profile_image" src="${pageContext.request.contextPath}/image/${tutor.image}">
                        </a>
                    </li>
                </ul>
            </nav>
            <hr />
        </header>
        <!-- Menu -->
        <nav class="navbar navbar-expand-sm navbarmenu">
            <!-- Links -->
            <ul class="navbar-nav">
                <li class="nav-item navmenuitem">
                    <!-- Dashboard -->
                    <a class="nav-link" href="${pageContext.request.contextPath}/tutor-dashboard">Dashboard</a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Schedule -->
                    <a class="nav-link" href="TutorScheduleController">Schedule</a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Material -->
                    <a class="nav-link" href="#">Material</a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Assignment -->
                    <a class="nav-link" href="ManageAssignmentController">Assignment</a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Student Request -->
                    <a class="nav-link" href="TutorHistoryController">Student Request</a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Trial Request -->
                    <a class="nav-link" href="TrialRequestController">Trial Request</a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- History -->
                    <a class="nav-link" href="history">History</a>
                </li>
                <li class="nav-item navmenuitem">
                    <a class="nav-link" id="income-link" href="#" onclick="redirectToIncome()">Income</a>
                </li>
            </ul>

        </nav>
    </body>
    <script>
        function redirectToIncome() {
            var currentDate = new Date();
            var sixDaysAgoDate = new Date();
            sixDaysAgoDate.setDate(currentDate.getDate() - 6);

            var currentDateString = currentDate.toISOString().split('T')[0];
            var sixDaysAgoDateString = sixDaysAgoDate.toISOString().split('T')[0];

            var url = 'IncomeController?start=' + sixDaysAgoDateString + '&end=' + currentDateString + '&view=week' + '&tutorID=' + ${sessionScope.tutor.getId()};
            window.location.href = url;
        }
    </script>
</html>
