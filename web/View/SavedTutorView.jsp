<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Saved Tutors</title>
        <link rel="stylesheet" href="<c:url value='/style/tutorlist.css' />">
        <link rel="stylesheet" href="<c:url value='/style/menuviewtutor.css' />">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            .tutorinfo {
                border: 1px solid #ddd;
                background-color: #f9f9f9;
            }
            .tutor-name, .subject h3, .students h3 {
                margin: 0;
            }
            .description {
                margin-top: 15px;
            }
            .fee h2 {
                margin: 0;
            }
            .fee p {
                margin: 0;
            }
            .read-more {
                display: inline-block;
                margin-top: 10px;
            }
        </style>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>
    <body>
        <%@ include file="StudentHeader.jsp" %>
        <hr/>
        <c:forEach items="${requestScope.savedTutors}" var="t">
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
                                         <h2><fmt:formatNumber value="${t.price}" pattern="###,###" />VND</h2>
                                        <p>per session</p>
                                        <form id="addtutorform-${t.getId()}" onsubmit="return false;">
                                            <button type="button" id="heart-button-${t.getId()}" style="background: white; border: 1px white;" onclick="change_heart('${t.getId()}')">
                                                <i class="fa-solid fa-heart heart ml-2" style="color: #A34343"></i>
                                            </button>
                                            <input type="hidden" name="service" value="add">
                                            <input type="hidden" name="tutor_id" value="${t.getId()}">
                                            <input type="hidden" name="learn_id" value="1">
                                        </form>

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
        <script type="text/javascript">
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
                            alert('Data sent successfully!');
                        }
                    };

                    xhttp.onerror = function () {
                        alert('Request failed');
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
                        alert(tId);
                        alert(lID);
                        alert('Data removed successfully!');
                    }
                };

                xhttp.onerror = function () {
                    alert('Request failed');
                };

                xhttp.open("POST", "SavedTutorController", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                var data = "tutor_id=" + tId + "&learn_id=" + lId + "&service=" + service;
                xhttp.send(data);

                return false; // Prevent form submission
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
