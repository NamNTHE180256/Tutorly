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
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
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

            .student_profile_image {
                margin-top: 15px;
                height: 50px;
            }

            .learnername {
                margin-top: 15px;
                color: #0E3C6E;
            }

            /*            .navbarmenu {
                            margin-top: 0;
                            margin-bottom: 10px;
                            margin-left: 20px;
                            
                        }
            
                        .navmenuitem {
                            margin-right: 25px;
                            background-color: #0E3C6E;
                            border-radius: 10px;
                            width: 120px;
                            
                             text-align: center;
                        }
                        
                        .navmenuitem a {
                            color: aliceblue;
                             justify-content: center;
                        }*/

            .content {
                padding-top: 20px;
                background-color: #E6E6E6;
            }
        </style>
        <title>JSP Page</title>
    </head>
    <body >

        <nav class="navbar navbar-expand-lg">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                        data-bs-target="#navbarExample" aria-controls="navbarExample" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <a class="navbar-brand" href="#"><img src="image/LOGO_TUTORLY.png" class="logo_img"></a>
                <div class="collapse navbar-collapse" id="navbarExample">
                    <ul class="navbar-nav me-auto mb-0">
                        <!-- Find tutor -->
                    </ul>
                    <div class="d-flex align-items-center flex-column flex-lg-row">
                        <ul class="navbar-nav ml-auto">
                            <!-- Notification from system or tutor -->
                            
                            <!-- Message from tutor -->
                            <li class="nav-item">
                                <button style="background: white; border: 1px white;"><a class="nav-link" href="#"><i class="fa-regular fa-message head_icon"></i></a></button>
                            </li>
                            <!-- Schedule of student -->
                            <li class="nav-item">
                                <button style="background: white; border: 1px white;"><a class="nav-link" href="../Tutorly/ScheduleController"><i class="fa-regular fa-calendar head_icon"></i></a></button>
                            </li>
                            <!-- List of favor tutor -->
                            <li class="nav-item">
                                <button style="background: white; border: 1px white;"><a class="nav-link" href="../Tutorly/SavedTutorController"><i class="fa-regular fa-heart head_icon"></i></a></button>
                            </li>
                            <!-- Student name -->
                            <li class="nav-item">

                                <a href="../Tutorly/StudentProfileController"><p class="nav-link learnername"> ${sessionScope.learner.name}</p></a>

                            </li>
                            <!-- Student profile image -->
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <img class="student_profile_image" src="image/${sessionScope.learner.image}">

                                </a>
                            </li>

                        </ul>
                    </div>
                </div>
            </div>
        </nav>
        <!-- Menu -->
        <nav class="navbar navbar-expand-sm navbarmenu">
            <!-- Links -->
            <ul class="navbar-nav">
                <li class="nav-item navmenuitem">
                    <!-- Dashboard -->
                    <a class="nav-link" href="${pageContext.request.contextPath}/DashboardController?type=learner&learnerid=${sessionScope.learner.getId()}">
                        <button style="background-color: #0E3C6E; color: white" type="button" class="btn">Dashboard</button>
                    </a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Schedule -->
                    <a class="nav-link" href="${pageContext.request.contextPath}/ScheduleController"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Schedule</button></a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Material -->
                    <a class="nav-link" href="${pageContext.request.contextPath}/MaterialController"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Material</button></a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Quiz -->
                    <a class="nav-link" href="${pageContext.request.contextPath}/QuizController"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Quiz</button></a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Change session -->
                    <a class="nav-link" href="submit-change-session-request"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Change session</button></a>
                </li>
              
                <li class="nav-item navmenuitem">
                    <!-- View Tutor Request -->
                    <a class="nav-link" href="${pageContext.request.contextPath}/RequestControllerForLearner"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Request</button></a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Save list -->
                    <a class="nav-link" href="${pageContext.request.contextPath}/SavedTutorController"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Save list tutors</button></a>
                </li>
<!--                <li class="nav-item navmenuitem">
                     View classes 
                    <a class="nav-link" href="StdudentDashboard"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">View Class</button></a>
                </li>-->
                <li class="nav-item navmenuitem">
                    <!-- Class -->
                    <a class="nav-link" href="ViewClassnew"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">View Class</button></a>
                </li>
            </ul>
        </nav>
    </body>
    <script>
        document.getElementById("logoutButton").addEventListener("click", function () {
            window.location.href = "logout"; // Chuyển hướng đến servlet LogoutController
        });
    </script>
</html>
