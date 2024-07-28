<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<c:url value='/style/tutorlist.css' />">
        <link rel="stylesheet" href="<c:url value='/style/menuviewtutor.css' />">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <title>Tutor</title>
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
    </head>
    <body >
        <!--Menu-->
        <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                        data-bs-target="#navbarExample" aria-controls="navbarExample" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <a class="navbar-brand" href="View/HomePage.jsp"><img src="image/LOGO_TUTORLY.png" class="logo_img"></a>
                <div class="collapse navbar-collapse" id="navbarExample">
                    <ul class="navbar-nav me-auto mb-0">
                        <!-- Find tutor -->
                        <li class="nav-item">

                            <a class="nav-link" href="../Tutorly/TutorController">Finding a tutor</a>

                        </li>

                    </ul>
                </div>   
            </div>
        <hr/>


        <nav class="navbar navbar-expand-lg">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="navbarPillsExample">
                    <ul class="navbar-nav navbar-nav-pills">
                        <li class="nav-item">
                            <div class="dropdown dropdown-hover" >
                                <a class="btn btn-default dropdown-toggle" href="#" role="button" style="background-color: #0E3C6E; color: white;">
                                    <i class="fa-solid fa-book-open" style="margin-right: 3px; color:#F7B500;"></i>I'm learning
                                </a>
                                <ul class="dropdown-menu" role="menu">
                                    <c:forEach items="${requestScope.subject_vector}" var="s">

                                        <li class="option">
                                            <a class="dropdown-item" href="FindingTutorController?service=filter&id=${s.getId()}">${s.getName()}</a>
                                        </li>

                                    </c:forEach>
                                </ul>

                            </div>
                        </li>
                        <div class="vr"></div>
                        <li class="nav-item">
                            <a style="color: #A2A2A2; font-size: 12px;">Filter tutor by</a>
                        </li>
                        <li class="nav-item">
                            <div class="dropdown dropdown-hover">
                                <a class="btn btn-default dropdown-toggle" href="#" role="button" style="background-color: #0E3C6E; color: white;">
                                    <i class="fa-solid fa-dollar-sign" style="margin-right: 3px; color:#F7B500;"></i>Price
                                </a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item" href="FindingTutorController?service=filter&price=1">Under 200k</a></li>
                                    <li><a class="dropdown-item" href="FindingTutorController?service=filter&price=2">Between 200k - 400k</a></li>
                                    <li><a class="dropdown-item" href="FindingTutorController?service=filter&price=3">Over 400k</a></li>
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item">
                            <div class="dropdown dropdown-hover">
                                <a class="btn btn-default dropdown-toggle" href="#" role="button" style="background-color: #0E3C6E; color: white;">
                                    <i class="fa-regular fa-clock"style="margin-right: 3px; color:#F7B500;"></i>Availability
                                </a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item" href="FindingTutorController?service=filter&session=1"><i class="fa-solid fa-cloud" style="margin-right: 3px; color:#0E3C6E;"></i>Morning (8am-12am)</a></li>
                                    <li><a class="dropdown-item" href="FindingTutorController?service=filter&session=2"><i class="fa-solid fa-cloud-sun" style="margin-right: 3px; color:#0E3C6E;"></i>Afternoon (2pm-5pm)</a></li>
                                    <li><a class="dropdown-item" href="FindingTutorController?service=filter&session=3"><i class="fa-solid fa-cloud-moon" style="margin-right: 3px; color:#0E3C6E;"></i>Evening (7pm-9pm)</a></li>
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item">
                            <div class="dropdown dropdown-hover">
                                <a class="btn btn-default dropdown-toggle" href="#" role="button" style="background-color: #0E3C6E; color: white;">
                                    <i class="fa-solid fa-pen"style="margin-right: 3px; color:#F7B500;"></i>Rate by learner
                                </a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item" href="FindingTutorController?service=filter&star=1" style="text-decoration: none">
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>

                                        </a></li>
                                    <li><a class="dropdown-item" href="FindingTutorController?service=filter&star=2" style="text-decoration: none">
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                        </a></li>
                                    <li><a class="dropdown-item" href="FindingTutorController?service=filter&star=3" style="text-decoration: none">
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>

                                        </a></li>
                                    <li><a class="dropdown-item" href="FindingTutorController?service=filter&star=4" style="text-decoration: none">
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>

                                        </a></li>
                                    <li><a class="dropdown-item" href="FindingTutorController?service=filter&star=5" style="text-decoration: none">
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                        </a></li>
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item">
                            <form class="me-2 mb-2 mb-lg-0">
                                <input class="form-control form-control-sm" type="text" name="tutorname" placeholder="Search Tutor by name" />
                                <input type="hidden" name="service" value="filter">


                            </form>

                        </li>
                    </ul>
                </div>
            </div>
        </nav>



        
        <!--Display tutor-->
        <c:forEach items="${requestScope.tutor_vector}" var="t">
            <div class="container-fluid">
                <div class="row justify-content-center">
                    <div class="col-md-9 p-3 rounded">
                        <div class="tutorinfo p-4 rounded">
                            <div class="row">
                                <!-- Left -->
                                <div class="col-md-3 text-center">
                                    <img class="tutorimg" style="width: 150px; height: 150px; object-fit: contain;" src="<c:url value='/image/${t.image}' />" alt="Tutor Image">
                                    <div class="rating mt-2">
                                        <c:choose>
                                            <c:when test="${tutorRatings[t.id]['avgRate'] != null}">
                                                <i class="fa-solid fa-star text-warning"></i>
                                                <span class="rate">${tutorRatings[t.id]['avgRate']}</span>
                                                <p class="ratedby">rated by ${tutorRatings[t.id]['rateCount']} learner(s)</p>
                                            </c:when>
                                            <c:otherwise>
                                                <p>New tutor</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <!-- Center -->
                                <div class="col-md-6">
                                    <h2 class="tutor-name">${t.name}</h2>
                                    <div class="subject my-2">
                                        <i class="fa-solid fa-chalkboard-user mr-2"></i>
                                        <h3>${t.subject.name}</h3>
                                    </div>
                                    <div class="students my-2">
                                        <i class="fa-solid fa-user mr-2"></i>
                                        <h3>Students: 15</h3>
                                    </div>
                                    <p class="description">${t.bio}</p>
                                    <a href="../Tutorly/GuestTutorDetails?id=${t.id}&idsub=${t.subject.id}" class="read-more">Read more</a>
                                </div>
                                <!-- Right -->
                                <div class="col-md-3 text-right">
                                    <div class="fee">
                                        <i style="color: #F7B500" class="fa-solid fa-money-check-dollar mr-2"> </i> 
                                        <h2><fmt:formatNumber value="${t.price}" pattern="###,###" />VND</h2>
                                        <p style="font-size: 10px">per lesson </p>
                                        
                                        <a href="View/Login.jsp"> <i class="fa-regular fa-heart heart ml-2"></i></a>
                                                
                                        
                                    </div>

                                    <!-- Trial -->
                                    <div class="buttons mt-3">

                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#triallesson"
                                                style="margin-bottom: 5px; background-color: #0E3C6E">
                                            <a href="View/Login.jsp" style="color:white">Book trial lesson</a>
                                        </button>

                                        <!-- Modal -->


                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registerclass"
                                                style="margin-bottom: 5px; background-color: #A2A2A2">
                                            <a href="View/Login.jsp" style="color:white">Register class</a>

                                        </button>



                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
        
    </body>
</html>
