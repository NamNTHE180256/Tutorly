<%-- 
    Document   : AdminLearner
    Created on : Jun 1, 2024, 9:57:54 PM
    Author     : Admin
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Tutor</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <style>
            .modal-xl {
                max-width: 75% !important;
            }
            .stu_image {
                width: 50px;  /* Set the desired width */
                height: 50px; /* Set the desired height */
                object-fit: cover; /* Ensures the image covers the specified width and height while maintaining aspect ratio */
                border-radius: 50%; /* Optional: If you want the image to be circular */
                border: 2px solid #ccc; /* Optional: Add a border */
            }
            .status-active {
                color: green;
            }

            .status-pending {
                color: #F7B500;
            }

            .search {
                width: 40%;
                height: 40px;
                background-color: #fff;
                padding: 5px;
                border-radius: 5px;
            }

            .search-input {
                color: white;
                border: 0;
                outline: 0;
                background: none;
                width: 0;
                caret-color: transparent;
                line-height: 30px;
                transition: width 0.4s linear
            }

            .search .search-input {
                padding: 0 10px;
                width: 100%;
                caret-color: #536bf6;
                font-size: 19px;
                font-weight: 300;
                color: black;
                transition: width 0.4s linear;
            }


            .search-icon {
                height: 34px;
                width: 34px;
                float: right;
                display: flex;
                justify-content: center;
                align-items: center;
                color: white;
                background-color: #536bf6;
                font-size: 10px;
                bottom: 30px;
                position: relative;
                border-radius: 5px;
            }

            .search-icon:hover{

                color: #fff !important;
            }

        </style>
    </head>
    <body>
        <%@ include file="AdminHeader.jsp" %>
        </br>
        <div class="container" style="background-color: #eee">
            <div class="col d-flex justify-content-end align-items-center" >
                <form class="search" action="AdminController" style="margin-top: 15px">
                    <input type="hidden" name="action" value="searchTutor"/>
                    <input type="text" class="search-input" placeholder="Search..." name="searchTutor" value="${param.searchTutor}">
                    <button type="submit" class="search-icon">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>
            <div class="row justify-content-center">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-6">
                            <h3></h3>
                        </div>
                    </div>
                    <table class="table">
                        <thead style="background-color: #0E3C6E; color: white;">
                            <tr>
                                <th>Tutor ID</th>
                                <th></th>
                                <th>Tutor Name</th>
                                <th>Tutor Email</th>
                                <th>Subject</th>
                                <th>Price</th>
                                <th>Rating</th>
                                <th>Status</th>
                                <th>Detail</th>
                            </tr>
                        </thead>
                        <tbody id="tutorTable">
                            <c:forEach var="tutor" items="${tutors}">
                                <tr id="tutorRow${tutor.id}">
                                    <td>${tutor.id}</td>
                                    <td><img src="image/${tutor.image}" class="stu_image"></td>
                                    <td><strong>${tutor.name}</strong></td>
                                    <td>${tutor.getUserInfo().email}</td>
                                    <td>${tutor.subject.name}</td>
                                    <td><fmt:formatNumber value="${tutor.price}" pattern="###,###"/></td>
                                    <td><i class="fa-solid fa-star text-warning"></i> ${tutor.getAvgRating()}</td>
                                    <td id="tutorStatus${tutor.id}">
                                        <c:choose>
                                            <c:when test="${tutor.status == 'Active'}">
                                                <span class="status-active">
                                                    ${tutor.status} <i class="fa-regular fa-circle-check"></i>
                                                </span>
                                            </c:when>
                                            <c:when test="${tutor.status == 'Pending'}">
                                                <span class="status-pending">
                                                    ${tutor.status} <i class="fa-regular fa-clock"></i>
                                                </span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#TutorDetail${tutor.id}">Detail</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Modals -->
                    <c:forEach var="tutor" items="${tutors}">
                        <div class="modal fade" id="TutorDetail${tutor.id}">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title text-center">${tutor.name}</h4>
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="container mt-3">
                                            <div class="accordion" id="accordion${tutor.id}">
                                                <div class="card">
                                                    <div class="card-header" id="headingOne${tutor.id}">
                                                        <h5 class="mb-0">
                                                            <button class="btn btn-link" type="button" data-toggle="collapse"
                                                                    data-target="#collapseOne${tutor.id}"
                                                                    aria-expanded="true" aria-controls="collapseOne${tutor.id}">
                                                                Description
                                                            </button>
                                                        </h5>
                                                    </div>
                                                    <div id="collapseOne${tutor.id}" class="collapse show"
                                                         aria-labelledby="headingOne${tutor.id}" data-parent="#accordion${tutor.id}">
                                                        <div class="card-body">
                                                            ${tutor.bio}
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="card">
                                                    <div class="card-header" id="headingTwo${tutor.id}">
                                                        <h5 class="mb-0">
                                                            <button class="btn btn-link collapsed" type="button" data-toggle="collapse"
                                                                    data-target="#collapseTwo${tutor.id}"
                                                                    aria-expanded="false" aria-controls="collapseTwo${tutor.id}">
                                                                Education
                                                            </button>
                                                        </h5>
                                                    </div>
                                                    <div id="collapseTwo${tutor.id}" class="collapse" aria-labelledby="headingTwo${tutor.id}"
                                                         data-parent="#accordion${tutor.id}">
                                                        <div class="card-body">
                                                            ${tutor.edu}
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="card">
                                                    <div class="card-header" id="headingThree${tutor.id}">
                                                        <h5 class="mb-0">
                                                            <button class="btn btn-link collapsed" type="button"
                                                                    data-toggle="collapse" data-target="#collapseThree${tutor.id}"
                                                                    aria-expanded="false" aria-controls="collapseThree${tutor.id}">
                                                                Certificate
                                                            </button>
                                                        </h5>
                                                    </div>
                                                    <div id="collapseThree${tutor.id}" class="collapse" aria-labelledby="headingThree${tutor.id}"
                                                         data-parent="#accordion${tutor.id}">
                                                        <div class="card-body">
                                                            none
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="card">
                                                    <div class="card-header" id="headingFour${tutor.id}">
                                                        <h5 class="mb-0">
                                                            <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseFour${tutor.id}"
                                                                    aria-expanded="false" aria-controls="collapseFour${tutor.id}">
                                                                Availability
                                                            </button>
                                                        </h5>
                                                    </div>
                                                    <div id="collapseFour${tutor.id}" class="collapse" aria-labelledby="headingFour${tutor.id}" data-parent="#accordion${tutor.id}">
                                                        <div class="card-body">
                                                            <div class="table-responsive">
                                                                <table class="table table-bordered text-center">
                                                                    <thead>
                                                                        <tr class="bg-light-gray">
                                                                            <th class="text-uppercase">Time</th>
                                                                            <th class="text-uppercase">Monday</th>
                                                                            <th class="text-uppercase">Tuesday</th>
                                                                            <th class="text-uppercase">Wednesday</th>
                                                                            <th class="text-uppercase">Thursday</th>
                                                                            <th class="text-uppercase">Friday</th>
                                                                            <th class="text-uppercase">Saturday</th>
                                                                            <th class="text-uppercase">Sunday</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <tr>
                                                                            <td class="align-middle">08:00</td>
                                                                            <c:forEach var="availability" items="${tutor.getAvailabilities()}" begin="0" end="6">
                                                                                <c:choose>
                                                                                    <c:when test="${availability.status == 'Available'}">
                                                                                        <td class="text-success">
                                                                                            <i class="fa fa-check"></i> Available
                                                                                        </td>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <td class="bg-light-gray"></td>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:forEach>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="align-middle">10:00</td>
                                                                            <c:forEach var="availability" items="${tutor.getAvailabilities()}" begin="0" end="6">
                                                                                <c:choose>
                                                                                    <c:when test="${availability.status == 'Available'}">
                                                                                        <td class="text-success">
                                                                                            <i class="fa fa-check"></i> Available
                                                                                        </td>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <td class="bg-light-gray"></td>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:forEach>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="align-middle">14:00</td>
                                                                            <c:forEach var="availability" items="${tutor.getAvailabilities()}" begin="14" end="20">
                                                                                <c:choose>
                                                                                    <c:when test="${availability.status == 'Available'}">
                                                                                        <td class="text-success">
                                                                                            <i class="fa fa-check"></i> Available
                                                                                        </td>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <td class="bg-light-gray"></td>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:forEach>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="align-middle">16:00</td>
                                                                            <c:forEach var="availability" items="${tutor.getAvailabilities()}" begin="21" end="27">
                                                                                <c:choose>
                                                                                    <c:when test="${availability.status == 'Available'}">
                                                                                        <td class="text-success">
                                                                                            <i class="fa fa-check"></i> Available
                                                                                        </td>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <td class="bg-light-gray"></td>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:forEach>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="align-middle">19:00</td>
                                                                            <c:forEach var="availability" items="${tutor.getAvailabilities()}" begin="28" end="34">
                                                                                <c:choose>
                                                                                    <c:when test="${availability.status == 'Available'}">
                                                                                        <td class="text-success">
                                                                                            <i class="fa fa-check"></i> Available
                                                                                        </td>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <td class="bg-light-gray"></td>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:forEach>
                                                                        </tr>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>        
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer d-flex justify-content-between">
                                        <div>
                                            <c:if test="${tutor.status == 'Pending'}">
                                                <button type="button" class="btn btn-outline-success" onclick="approveTutor(${tutor.id})">Approve</button>
                                                <button type="button" class="btn btn-outline-warning" onclick="rejectTutor(${tutor.id})">Reject</button>
                                            </c:if>
                                        </div>
                                        <button type="button" class="btn btn-outline-danger" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
            function approveTutor(tutorId) {
                // Use the result of the confirm dialog to proceed with the approval
                if (confirm('Approve tutor with ID: ' + tutorId)) {
                    $.ajax({
                        url: 'approveTutor', // Your backend URL to approve tutor
                        type: 'POST',
                        data: { id: tutorId },
                        success: function (response) {
                            // Update the status in the table
                            $('#tutorStatus' + tutorId).html(
                                '<span class="status-active">Active <i class="fa-regular fa-circle-check"></i></span>'
                            );
                            // Hide the modal
                            $('#TutorDetail' + tutorId).modal('hide');
                        },
                        error: function (error) {
                            console.log('Error approving tutor:', error);
                        }
                    });
                }
            }

            function rejectTutor(tutorId) {
                if (confirm('Reject tutor with ID: ' + tutorId)){
                    $.ajax({
                        url: 'rejectTutor', // Your backend URL to reject tutor
                        type: 'POST',
                        data: {id: tutorId},
                        success: function (response) {
                            // Remove the tutor row from the table
                            $('#tutorRow' + tutorId).remove();
                            // Hide the modal
                            $('#TutorDetail' + tutorId).modal('hide');
                        },
                        error: function (error) {
                            console.log('Error rejecting tutor:', error);
                        }
                    });
                }
            }
        </script>

    </body>
</html>
