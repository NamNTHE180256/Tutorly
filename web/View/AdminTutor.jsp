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
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 50%;
            border: 2px solid #ccc;
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
        .search-icon:hover {
            color: #fff !important;
        }
    </style>
</head>
<body>
    <%@ include file="AdminHeader.jsp" %>
    </br>
    <div class="container" style="background-color: #eee">
        <div class="col d-flex justify-content-end align-items-center">
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
                                    <button type="button" class="btn btn-outline-primary" onclick="loadTutorDetails(${tutor.id})">Detail</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Empty Modal Template -->
                <div class="modal fade" id="tutorDetailModal" tabindex="-1" role="dialog" aria-labelledby="tutorDetailModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-xl" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="tutorDetailModalLabel">Tutor Details</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <!-- Modal content will be loaded here -->
                            </div>
                            <div class="modal-footer d-flex justify-content-between">
                                <div id="modalActions"></div>
                                <button type="button" class="btn btn-outline-danger" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End of Modal Template -->
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        function loadTutorDetails(tutorId) {
        $.ajax({
            url: 'TutorModal', // URL to fetch tutor details
            type: 'GET',
            data: { id: tutorId },
            success: function (response) {
                // Populate the modal content with the response
                $('#tutorDetailModal .modal-body').html(response);

                // Extract the tutor status from the hidden element
                var status = $('#tutorDetailModal .modal-body #tutorStatus').text().trim();

                // Update modal actions
                var modalActions = '';
                if (status === 'Pending') {
                    modalActions = `
                        <div class="d-flex justify-content-between">
                            <button type="button" id="approveButton" class="btn btn-outline-success flex-grow-1 mr-2">Approve</button>
                            <button type="button" id="rejectButton" class="btn btn-outline-warning flex-grow-1 ml-2">Reject</button>
                        </div>`;
                }
                $('#modalActions').html(modalActions);

                // Set the onclick handlers
                $('#approveButton').on('click', function() {
                    approveTutor(tutorId);
                });
                $('#rejectButton').on('click', function() {
                    rejectTutor(tutorId);
                });

                // Show the modal
                $('#tutorDetailModal').modal('show');
            },
            error: function (error) {
                console.log('Error loading tutor details:', error);
            }
        });
    }




        function approveTutor(tutorId) {
            if (confirm('Approve tutor with ID: ' + tutorId)) {
                $.ajax({
                    url: 'approveTutor', // Your backend URL to approve tutor
                    type: 'POST',
                    data: { id: tutorId },
                    success: function (response) {
                        if (response.status === 'success') {
                            $('#tutorStatus' + tutorId).html(
                                '<span class="status-active">Active <i class="fa-regular fa-circle-check"></i></span>'
                            );
                            $('#tutorDetailModal').modal('hide');
                        } else {
                            alert('Failed to approve tutor');
                        }
                    },
                    error: function (error) {
                        console.log('Error approving tutor:', error);
                        alert('Error approving tutor');
                    }
                });
            }
        }

        function rejectTutor(tutorId) {
            if (confirm('Reject tutor with ID: ' + tutorId)) {
                $.ajax({
                    url: 'rejectTutor', // Your backend URL to reject tutor
                    type: 'POST',
                    data: { id: tutorId },
                    success: function (response) {
                        if (response.status === 'success') {
                            $('#tutorRow' + tutorId).remove();
                            $('#tutorDetailModal').modal('hide');
                        } else {
                            alert('Failed to reject tutor');
                        }
                    },
                    error: function (error) {
                        console.log('Error rejecting tutor:', error);
                        alert('Error rejecting tutor');
                    }
                });
            }
        }
    </script>
</body>
</html>
