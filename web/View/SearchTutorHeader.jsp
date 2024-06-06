<%-- 
    Document   : StudentHeader
    Created on : Jun 2, 2024, 5:43:03 PM
    Author     : TRANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        </style>
        <title>JSP Page</title>
    </head>
    <body>
        <header class="head">
            <nav class="navbar navbar-expand-sm navheader">
                <a href="../Tutorly/TutorController"><img src="image/LOGO_TUTORLY.png" class="logo_img"></a>
                <!-- Left -->

                <!-- Right -->
                <ul class="navbar-nav ml-auto">
                    <!-- Notification from system or tutor -->
                    <li class="nav-item">
                        <a class="nav-link" href="#"><i class="fa-solid fa-bell head_icon"></i></a>
                    </li>
                    <!-- Message from tutor -->
                    <li class="nav-item">
                        <a class="nav-link" href="#"><i class="fa-regular fa-message head_icon"></i></a>
                    </li>
                    <!-- Schedule of student -->
                    <li class="nav-item">
                        <a class="nav-link" href="../Tutorly/TutorController"><i class="fa-regular fa-calendar head_icon"></i></a>
                    </li>
                    <!-- List of favor tutor -->
                    <li class="nav-item">
                        <a class="nav-link" href="#"><i class="fa-regular fa-heart head_icon"></i></a>
                    </li>
                    <!-- Student name -->
                    <li class="nav-item">
                        <a href="../Tutorly/StudentProfileController"><p class="nav-link learnername"> Nam Nguyễn</p></a>
                    </li>
                    <!-- Student profile image -->
                    <li class="nav-item">
                        <a class="nav-link" href="#"><img class="student_profile_image" src="image/image1.jpg"></a>
                    </li>
                </ul>
            </nav>
            <hr />
        </header>
        <!--menu-->
        <nav class="navbar navbar-expand-sm navbarmenu">
            <!-- Links -->
            <ul class="navbar-nav">
                <li class="nav-item navmenuitem">
                    <!-- Dashboard -->
                    <a class="nav-link" href="../Tutorly/DashboardController">Dashboard</a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Schedule -->
                    <a class="nav-link" href="#">Schedule</a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Material -->
                    <a class="nav-link" href="#">Material</a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Assignment -->
                    <a class="nav-link" href="../Tutorly/AssignmentController">Assignment</a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Save list -->
                    <a class="nav-link" href="#">Save list tutors</a>
                </li>
            </ul>
        </nav>
    </body>
</html>
