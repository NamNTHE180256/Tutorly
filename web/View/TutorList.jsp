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
    </head>
    <body >
        <!--Menu-->
        <%@ include file = "SearchTutorHeader.jsp" %>
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
                                            <a class="dropdown-item" href="TutorController?service=filter&id=${s.getId()}">${s.getName()}</a>
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
                                    <li><a class="dropdown-item" href="TutorController?service=filter&price=1">Under 200k</a></li>
                                    <li><a class="dropdown-item" href="TutorController?service=filter&price=2">Between 200k - 400k</a></li>
                                    <li><a class="dropdown-item" href="TutorController?service=filter&price=3">Over 400k</a></li>
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item">
                            <div class="dropdown dropdown-hover">
                                <a class="btn btn-default dropdown-toggle" href="#" role="button" style="background-color: #0E3C6E; color: white;">
                                    <i class="fa-regular fa-clock"style="margin-right: 3px; color:#F7B500;"></i>Availability
                                </a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item" href="TutorController?service=filter&session=1"><i class="fa-solid fa-cloud" style="margin-right: 3px; color:#0E3C6E;"></i>Morning (8am-12am)</a></li>
                                    <li><a class="dropdown-item" href="TutorController?service=filter&session=2"><i class="fa-solid fa-cloud-sun" style="margin-right: 3px; color:#0E3C6E;"></i>Afternoon (2pm-5pm)</a></li>
                                    <li><a class="dropdown-item" href="TutorController?service=filter&session=3"><i class="fa-solid fa-cloud-moon" style="margin-right: 3px; color:#0E3C6E;"></i>Evening (7pm-9pm)</a></li>
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item">
                            <div class="dropdown dropdown-hover">
                                <a class="btn btn-default dropdown-toggle" href="#" role="button" style="background-color: #0E3C6E; color: white;">
                                    <i class="fa-solid fa-pen"style="margin-right: 3px; color:#F7B500;"></i>Rate by learner
                                </a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item" href="TutorController?service=filter&star=1" style="text-decoration: none">
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>

                                        </a></li>
                                    <li><a class="dropdown-item" href="TutorController?service=filter&star=2" style="text-decoration: none">
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                        </a></li>
                                    <li><a class="dropdown-item" href="TutorController?service=filter&star=3" style="text-decoration: none">
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>

                                        </a></li>
                                    <li><a class="dropdown-item" href="TutorController?service=filter&star=4" style="text-decoration: none">
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
                                            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>

                                        </a></li>
                                    <li><a class="dropdown-item" href="TutorController?service=filter&star=5" style="text-decoration: none">
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



        <c:if test="${sessionScope.successMessage!=null}">
            </br>
            <div class="alert alert-success" role="alert" id="successAlert">
                <div class="d-flex gap-4">
                    <span><i class="fa-solid fa-circle-check icon-success"></i></span>
                    <div>
                        ${sessionScope.successMessage}

                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${sessionScope.errorMessage!=null}">
            <div class="alert alert-danger" role="alert" id="errorAlert">
                <div class="d-flex gap-4">
                    <span><i class="fa-solid fa-circle-exclamation icon-danger"></i></span>
                    <div class="d-flex flex-column gap-2">
                        ${sessionScope.errorMessage}
                    </div>
                </div>
            </div>
        </c:if>
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
                                    <a href="../Tutorly/TutorDetailController?id=${t.id}&idsub=${t.subject.id}" class="read-more">Read more</a>
                                </div>
                                <!-- Right -->
                                <div class="col-md-3 text-right">
                                    <div class="fee">
                                        <i style="color: #F7B500" class="fa-solid fa-money-check-dollar mr-2"> </i> 
                                        <h2><fmt:formatNumber value="${t.price}" pattern="###,###" />VND</h2>
                                        <p style="font-size: 10px">per lesson </p>
                                        <form id="addtutorform-${t.id}" onsubmit="return false;">
                                            <button type="button" id="heart-button-${t.id}" style="background: white; border: 1px white;" onclick="change_heart('${t.id}')">
                                                <c:choose>
                                                    <c:when test="${t.saveStatus == 'saved'}">
                                                        <i class="fa-solid fa-heart heart ml-2" style="color: #A34343"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="fa-regular fa-heart heart ml-2"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </button>
                                            <input type="hidden" name="service" value="add">
                                            <input type="hidden" name="tutor_id" value="${t.id}">
                                            <input type="hidden" name="learn_id" value="1">
                                        </form>
                                    </div>

                                    <!-- Trial -->
                                    <div class="buttons mt-3">

                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#triallesson"
                                                style="margin-bottom: 5px; background-color: #0E3C6E">
                                            <a href="RegisterTrialCotroller?tutor_id=${t.id}" style="color:white">Book trial lesson</a>
                                        </button>

                                        <!-- Modal -->


                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registerclass"
                                                style="margin-bottom: 5px; background-color: #A2A2A2">
                                            <a href="RegisterClassController?tutor_id=${t.id}" style="color:white">Register class</a>

                                        </button>



                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        <script type="text/javascript">
            window.onload = function () {
                setTimeout(function () {
                    var successAlert = document.getElementById('successAlert');
                    if (successAlert) {
                        successAlert.style.display = 'none';
                    }
                    var errorAlert = document.getElementById('errorAlert');
                    if (errorAlert) {
                        errorAlert.style.display = 'none';
                    }
            <% session.removeAttribute("successMessage"); %>
            <% session.removeAttribute("errorMessage");%>
                }, 5000); // 5000 milliseconds = 5 seconds
            };

            function change_heart(tutorId) {
                var button = document.getElementById('heart-button-' + tutorId);
                var icon = button.querySelector('i');

                if (icon.classList.contains('fa-regular')) {
                    icon.classList.remove('fa-regular');
                    icon.classList.add('fa-solid');
                    icon.style.color = '#A34343';
                    button.onclick = function () {
                        reset_heart(tutorId);
                    };

                    // Perform the AJAX request to add the tutor
                    var formId = 'addtutorform-' + tutorId;
                    var form = document.getElementById(formId);
                    var tId = form.querySelector('input[name="tutor_id"]').value;
                    var lId = form.querySelector('input[name="learn_id"]').value;
                    var service = form.querySelector('input[name="service"]').value;

                    var xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            //alert('Data sent successfully!');
                        }
                    };

                    xhttp.onerror = function () {
                        //alert('Request failed');
                    };

                    xhttp.open("POST", "SavedTutorController", true);
                    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                    var data = "tutor_id=" + tId + "&learn_id=" + lId + "&service=" + service;
                    xhttp.send(data);
                } else {
                    reset_heart(tutorId);
                }
                return false; // Prevent form submission
            }

            function reset_heart(tutorId) {
                var button = document.getElementById('heart-button-' + tutorId);
                var icon = button.querySelector('i');

                icon.classList.remove('fa-solid');
                icon.classList.add('fa-regular');
                icon.style.color = '';
                button.onclick = function () {
                    change_heart(tutorId);
                };

                // Perform the AJAX request to remove the tutor
                var formId = 'addtutorform-' + tutorId;
                var form = document.getElementById(formId);
                var tId = form.querySelector('input[name="tutor_id"]').value;
                var lId = form.querySelector('input[name="learn_id"]').value;
                var service = "remove";

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        //alert(tId);
                        //alert(lID);
                        //alert('Data removed successfully!');
                    }
                };

                xhttp.onerror = function () {
                    //alert('Request failed');
                };

                xhttp.open("POST", "SavedTutorController", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                var data = "tutor_id=" + tId + "&learn_id=" + lId + "&service=" + service;
                xhttp.send(data);

                return false; // Prevent form submission
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
