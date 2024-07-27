<%-- 
    Document   : TutorHeader
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
            .tutor_profile_image {
                margin-top: 15px;
                height: 60px;
            }

            .tutorname {
                margin-top: 30px;
                color: #0E3C6E;
                font-size: 15px;
            }

            .content {
                background-color: #E6E6E6;
            }
        </style>
        <title>Tutor</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                        data-bs-target="#navbarExample" aria-controls="navbarExample" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <a class="navbar-brand" href="View/HomePage.jsp"><img src="image/LOGO_TUTORLY.png" class="logo_img"></a>
                <div class="collapse navbar-collapse" id="navbarExample">
                    <ul class="navbar-nav ms-auto d-flex align-items-center flex-row">
                        <!-- Notification from system or learner -->
                        <li class="nav-item dropdown me-3">
                            <a class="nav-link dropdown-toggle" href="#" id="notificationDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fa-solid fa-bell head_icon"></i>
                                <c:if test="${not empty notifications}">
                                    <span class="badge badge-danger">${count}</span>
                                </c:if>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end" aria-labelledby="notificationDropdown">
                                <c:forEach var="notification" items="${notifications}">
                                    <c:if test="${notification.type eq 'StudentRequest'}" >
                                        <c:if test="${notification.isRead == 'false'}">
                                            <a class="dropdown-item" href="tutor-noti?action=request&id=${notification.id}">
                                                <strong> ${notification.message}</strong>
                                                <small class="text-muted">${notification.createdAt}</small>
                                            </a>
                                        </c:if>
                                        <c:if test="${notification.isRead == 'true'}">
                                            <a class="dropdown-item" href="tutor-noti?action=request&id=${notification.id}">
                                                ${notification.message} 
                                                <small class="text-muted">${notification.createdAt}</small>
                                            </a>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${notification.type eq 'StudentResponse'}" >
                                        <c:if test="${notification.isRead == 'false'}">
                                            <a class="dropdown-item" href="tutor-noti?action=request&id=${notification.id}">
                                                <strong> ${notification.message}</strong>
                                                <small class="text-muted">${notification.createdAt}</small>
                                            </a>
                                        </c:if>
                                        <c:if test="${notification.isRead == 'true'}">
                                            <a class="dropdown-item" href="tutor-noti?action=request&id=${notification.id}">
                                                ${notification.message} 
                                                <small class="text-muted">${notification.createdAt}</small>
                                            </a>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${empty notifications}">
                                    <a class="dropdown-item" href="#">No notifications available</a>
                                </c:if>
                            </div>
                        </li>
                        <!-- Schedule of student -->
                        <li class="nav-item me-3">
                            <button style="background: white; border: 1px white;"><a class="nav-link" href="TutorScheduleController">
                                    <i class="fa-regular fa-calendar head_icon"></i></a></button>
                        </li>
                        <!-- Tutor name -->
                        <li class="nav-item me-3">
                            <a href="TutorProfileController"><p class="nav-link tutorname">${tutor.name}</p></a>
                        </li>
                        <!-- Tutor profile image -->
                        <li class="nav-item me-3">
                            <a class="nav-link" href="TutorProfileController">
                                <img class="tutor_profile_image" src="${pageContext.request.contextPath}/image/${tutor.image}">
                            </a>
                        </li>
                        <li class="nav-item me-3">
                            <a href="${pageContext.request.contextPath}/logout"><p class="nav-link tutorname">Logout</p></a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Menu -->
        <nav class="navbar navbar-expand-sm navbarmenu">
            <!-- Links -->
            <ul class="navbar-nav">
                <li class="nav-item navmenuitem">
                    <!-- Dashboard -->
                    <a class="nav-link" href="${pageContext.request.contextPath}/DashboardController?type=tutor&tutorid=${sessionScope.tutor.id}"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Dashboard</button></a>
                </li>
                <li class="nav-item navmenuitem me-2">
                    <!-- Schedule -->
                    <a class="nav-link" href="TutorScheduleController"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Schedule</button></a>
                </li>
                <li class="nav-item navmenuitem">
                    <!-- Material -->
                    <a class="nav-link" href="${pageContext.request.contextPath}/MaterialController"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Material</button></a>
                </li>
                <li class="nav-item navmenuitem me-2">
                    <!-- Quiz -->
                    <a class="nav-link" href="#"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Quiz</button></a>
                </li>
                <li class="nav-item navmenuitem me-2">
                    <!-- Change session -->
                    <a class="nav-link" href="tutor-change-session"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Change session</button></a>
                </li>
                <li class="nav-item navmenuitem me-2">
                    <!-- Student Request -->
                    <a class="nav-link" href="tutor-requests"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">My Request</button></a>
                </li>
                <li class="nav-item navmenuitem me-2">
                    <!-- View Tutor Request -->
                    <a class="nav-link" href="${pageContext.request.contextPath}/RequestControllersForTutor?requestType"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">Learner Request</button></a>
                </li>
                <li class="nav-item navmenuitem me-2">
                    <!-- History -->
                    <a class="nav-link" href="history"><button style="background-color: #0E3C6E; color: white" type="button" class="btn">History</button></a>
                </li>
                <li class="nav-item navmenuitem">
                    <a class="nav-link" id="income-link" class="btn"  href="#" ><button   onclick="redirectToIncome()" style="background-color: #0E3C6E; color: white" type="button" class="btn">Income</button></a>
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
