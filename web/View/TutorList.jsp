<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/style/tutorlist.css' />">
    <link rel="stylesheet" href="<c:url value='/style/menuviewtutor.css' />">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <title>Tutor</title>
</head>
<body >
    <!--Menu-->
    <%@ include file = "SearchTutorHeader.jsp" %>
    <hr/>
        <nav class="navbar navbar-expand-sm " >
            <!-- Links -->
            <ul class="navbar-nav navmenu">
                <li class="nav-item" style="margin-right: 10px;">
                    <p style="text-align: center; color: #0E3C6E;">I'm learning</p>
                    <div class="select-menu">
                        <div class="select-btn">
                            <span class="sBtn-text">Select your option</span>
                            <i class="fa-solid fa-chevron-down"></i>
                        </div>
                        <c:forEach items="${requestScope.subject_vector}" var="s">
                            <ul class="options">
                                <li class="option">
                                    <span class="option-text"><a href="TutorController?id=${s.getId()}">${s.getName()}</a></span>
                                </li>
                            </ul>
                        </c:forEach>
                    </div>
                </li>
                <div class = "vertical "></div>
                <li class="nav-item">
                    <p style="text-align: center; color: #0E3C6E;">Filter tutor by</p>
                    <div class="select-menu price ">
                        <div class="select-btn">
                            <span class="sBtn-text">Price</span>
                            <i class="fa-solid fa-chevron-down"></i>
                        </div>
                        <ul class="options">
                            <li class="option">

                                <span class="option-text"><a href="TutorController?price=1">Under 200k</a></span>
                            </li>
                            <li class="option">
                                <span class="option-text"><a href="TutorController?price=2">Between 200k - 400k</a></span>
                            </li>
                            <li class="option">
                                <span class="option-text"><a href="TutorController?price=3">Over 400k</a></span>
                            </li>
                        </ul>
                    </div>

                </li>
                <li class="nav-item">
                    <p class="hide">.</p>
                    <div class="select-menu Availability">
                        <div class="select-btn">
                            <span class="sBtn-text">Availability</span>
                            <i class="fa-solid fa-chevron-down"></i>
                        </div>
                        <ul class="options">
                            <li class="option">
                                <i class="fa-solid fa-cloud"></i>
                                <span class="option-text"><a href="TutorController?session=1">Morning (8am-12am)</a></span>
                            </li>
                            <li class="option">
                                <i class="fa-solid fa-cloud-sun"></i>
                                <span class="option-text"><a href="TutorController?session=2">Afternoon (2pm-5pm)</a></span>
                            </li>
                            <li class="option">
                                <i class="fa-solid fa-cloud-moon"></i>
                                <span class="option-text"><a href="TutorController?session=3">Evening (7pm-9pm)</a></span>
                            </li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <p class="hide">.</p>
                    <div class="select-menu" style="width: 200px; display: inline-block;">
                        <div class="select-btn">
                            <span class="sBtn-text">Rate</span>
                            <i class="fa-solid fa-chevron-down"></i>
                        </div>
                        <ul class="options">
    <li class="option">
        <a href="TutorController?star=1" style="text-decoration: none">
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
        </a>
    </li>
    <li class="option">
        <a href="TutorController?star=2" style="text-decoration: none">
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
        </a>
    </li>
    <li class="option">
        <a href="TutorController?star=3" style="text-decoration: none">
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
        </a>
    </li>
    <li class="option">
        <a href="TutorController?star=4" style="text-decoration: none">
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
        </a>
    </li>
    <li class="option">
        <a href="TutorController?star=5" style="text-decoration: none">
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
            <i class="fa-solid fa-star" style="color: #F7B500; display: inline;"></i>
        </a>
    </li>
</ul>

                    </div>
                </li>
                <li class="nav-item">
                    <p class="hide">.</p>
                    <form class="form-inline" action="TutorController" method ="get">
                        <div class="input-box">
                            <i class="fa-solid fa-magnifying-glass"></i>
                            <input type="text" name="tutorname" placeholder="Search tutor by name" />
                            <button class="button" type="submit">Search</button>
                        </div>
                    </form>
                </li>
            </ul>
        </nav>

        <script>
            document.querySelectorAll(".select-menu").forEach(menu => {
                const selectBtn = menu.querySelector(".select-btn");
                const options = menu.querySelectorAll(".option");
                const sBtn_text = menu.querySelector(".sBtn-text");

                selectBtn.addEventListener("click", () => menu.classList.toggle("active"));
                options.forEach(option => {
                    option.addEventListener("click", () => {
                        let selectedOption = option.querySelector(".option-text").innerText;
                        sBtn_text.innerText = selectedOption;
                        menu.classList.remove("active");
                    });
                });
            });
        </script>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        
    <!--Display tutor-->
    <c:forEach items="${requestScope.tutor_vector}" var="t">
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-md-9 p-3 rounded">
                    <div class="tutorinfo p-4 rounded">
                        <div class="row">
                            <!-- Left -->
                            <div class="col-md-3 text-center">
                                <img class="tutorimg" style="width: 150px; height: 150px; object-fit: contain;" src="<c:url value='/image/${t.getImage()}' />" alt="Tutor Image">
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
                                <h2 class="tutor-name">${t.getName()}</h2>
                                <div class="subject my-2">
                                    <i class="fa-solid fa-chalkboard-user mr-2"></i>
                                    <h3>${t.getSubject().name}</h3>
                                </div>
                                <div class="students my-2">
                                    <i class="fa-solid fa-user mr-2"></i>
                                    <h3>Students: 15</h3>
                                </div>
                                <p class="description">${t.getBio()}</p>
                                <a href="../Tutorly/TutorDetailController?id=${t.getId()}&idsub=${t.getSubject().id}" class="read-more">Read more</a>
                            </div>
                            <!-- Right -->
                            <div class="col-md-3 text-right">
                                <div class="fee">
                                    <i class="fa-solid fa-money-check-dollar mr-2"></i>
                                    <h2>${t.price}K</h2>
                                    <p>per session</p>
                                    <i class="fa-regular fa-heart heart ml-2"></i>
                                </div>
                                <div class="buttons mt-3">
                                    <button class="btn btn-primary btn-trial">Book trial lesson</button>
                                    <button class="btn btn-secondary btn-register">Register class</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</body>
</html>
