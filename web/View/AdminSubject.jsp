<%-- 
    Document   : AdminSubject
    Created on : Jun 17, 2024, 5:20:24 AM
    Author     : Admin
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <title>Subject Manager</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style type="text/css">
            body {
                margin-top: 20px;
                background-color: #f1f3f7;
            }

            .search-box .form-control {
                border-radius: 10px;
                padding-left: 40px
            }

            .search-box .search-icon {
                position: absolute;
                left: 13px;
                top: 50%;
                -webkit-transform: translateY(-50%);
                transform: translateY(-50%);
                fill: #545965;
                width: 16px;
                height: 16px
            }

            .card {
                margin-bottom: 24px;
                -webkit-box-shadow: 0 2px 3px #e4e8f0;
                box-shadow: 0 2px 3px #e4e8f0;
            }

            .card {
                position: relative;
                display: -webkit-box;
                display: -ms-flexbox;
                display: flex;
                -webkit-box-orient: vertical;
                -webkit-box-direction: normal;
                -ms-flex-direction: column;
                flex-direction: column;
                min-width: 0;
                word-wrap: break-word;
                background-color: #fff;
                background-clip: border-box;
                border: 1px solid #eff0f2;
                border-radius: 1rem;
            }

            .me-3 {
                margin-right: 1rem !important;
            }

            .font-size-24 {
                font-size: 24px !important;
            }

            .avatar-title {
                -webkit-box-align: center;
                -ms-flex-align: center;
                align-items: center;
                background-color: #3b76e1;
                color: #fff;
                display: -webkit-box;
                display: -ms-flexbox;
                display: flex;
                font-weight: 500;
                height: 100%;
                -webkit-box-pack: center;
                -ms-flex-pack: center;
                justify-content: center;
                width: 100%;
            }

            .bg-soft-info {
                background-color: rgba(87, 201, 235, .25) !important;
            }

            .bg-soft-primary {
                background-color: rgba(59, 118, 225, .25) !important;
}

            .avatar-xs {
                height: 1rem;
                width: 1rem
            }

            .avatar-sm {
                height: 2rem;
                width: 2rem
            }

            .avatar {
                height: 3rem;
                width: 3rem
            }

            .avatar-md {
                height: 4rem;
                width: 4rem
            }

            .avatar-lg {
                height: 5rem;
                width: 5rem
            }

            .avatar-xl {
                height: 6rem;
                width: 6rem
            }

            .avatar-title {
                -webkit-box-align: center;
                -ms-flex-align: center;
                align-items: center;
                background-color: #3b76e1;
                color: #fff;
                display: -webkit-box;
                display: -ms-flexbox;
                display: flex;
                font-weight: 500;
                height: 100%;
                -webkit-box-pack: center;
                -ms-flex-pack: center;
                justify-content: center;
                width: 100%
            }

            .avatar-group {
                display: -webkit-box;
                display: -ms-flexbox;
                display: flex;
                -ms-flex-wrap: wrap;
                flex-wrap: wrap;
                padding-left: 8px
            }

            .avatar-group .avatar-group-item {
                margin-left: -8px;
                border: 2px solid #fff;
                border-radius: 50%;
                -webkit-transition: all .2s;
                transition: all .2s
            }

            .avatar-group .avatar-group-item:hover {
                position: relative;
                -webkit-transform: translateY(-2px);
                transform: translateY(-2px)
            }

            .fw-medium {
                font-weight: 500;
            }

            a {
                text-decoration: none !important;
            }
        </style>
    </head>
    <body>
        <%@ include file="AdminHeader.jsp" %>
        </br>
        <div class="container">
            <div class="row">
                <div class="col-xl-12">
                    <div class="card">
                        <div class="card-body">
                            <div class="row mb-3">
                                <div class="col-lg-4 col-sm-6">
                                    <div class="search-box mb-2 me-2">

                                    </div>
                                </div>
                                <div class="col-lg-8 col-sm-6">
                                    <div class="mt-4 mt-sm-0 d-flex align-items-center justify-content-sm-end">
                                        <div class="mb-2 me-2">
                                            <div class="dropdown">

                                                <button class="btn" type="button" style="background-color: #0E3C6E; color: white" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                                    <i class="fa-solid fa-plus"></i> Create New Subject</a>
                                                </button>

                                                <!-- Modal -->
                                                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="exampleModalLabel">New Subject</h5>
                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                            </div>
                                                            <form action="AdminController" >
                                                                <div class="modal-body">
                                                                    <div class="input-group mb-3">
                                                                        <input type="hidden" name="action" value="addSubject"> 
                                                                        <span class="input-group-text" id="inputGroup-sizing-default">Subject name</span>
                                                                        <input type="text" name="subject_name" class="form-control" 
                                                                               aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" required="">
                                                                    </div>

                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                    <button type="submit" class="btn" style="background-color: #0E3C6E; color: white">Add Subject</button>
                                                                </div>
                                                            </form>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${successMessage!=null}">
                                </br>
                                <div class="alert alert-success" role="alert" id="successAlert">
                                    <div class="d-flex gap-4">
                                        <span><i class="fa-solid fa-circle-check icon-success"></i></span>
                                        <div>
                                            ${successMessage}
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${errorMessage!=null}">
                                <div class="alert alert-danger" role="alert" id="errorAlert">
                                    <div class="d-flex gap-4">
                                        <span><i class="fa-solid fa-circle-exclamation icon-danger"></i></span>
                                        <div class="d-flex flex-column gap-2">
                                            ${errorMessage}
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <hr/>

                            <h5 class="font-size-16 me-3 mb-0" style="color: #0E3C6E; font-size: 30px">Subject Manager:</h5>

                            <div class="row mt-4">
                                <c:forEach var="entry" items="${tutorsBySubject}">
                                    <div class="col-xl-4 col-sm-6">
                                        <div class="card shadow-none border">
                                            <div class="card-body p-3">
                                                <div class>
                                                    <div class="d-flex justify-content-between align-items-center">
                                                        <div>
                                                            <i class="fa-solid fa-book-open" style="color: #F7B500; font-size: 30px;"></i>
                                                        </div>
                                                        <div class="avatar-group">
                                                            <div class="avatar-group-item">
                                                                <a href="javascript: void(0);" class="d-inline-block">
                                                                    <img src="https://bootdey.com/img/Content/avatar/avatar1.png"
                                                                         alt class="rounded-circle avatar-sm">
                                                                </a>
                                                            </div>

                                                        </div>
                                                    </div>
                                                    <div class="d-flex mt-3">
                                                        <div class="overflow-hidden me-auto">
                                                            <h5 class="font-size-15 text-truncate mb-1" style="color: #0E3C6E">
                                                                ${entry.key.name}
                                                            </h5>
                                                            <p class="text-muted text-truncate mb-0">${entry.value} Tutors</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>

                            </div>
                            <hr/>
                            <div class="d-flex flex-wrap">
                                <h5 class="font-size-16 me-3" style="color: #0E3C6E;">Number of student</h5>
                                <div class="ms-auto">
                                    <a href="javascript: void(0);" class="fw-medium text-reset" style="color: #0E3C6E; ">View All</a>
                                </div>
                            </div>
                            <hr class="mt-2">
                            <div class="table-responsive">
                                <table class="table align-middle table-nowrap table-hover mb-0">
                                    <thead class="table-light">
                                        <tr>
                                            <th scope="col">Subject Name</th>
                                            <th scope="col">Number of class</th>
                                            <th scope="col">Number of student</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="entry" items="${learnersBySubject}">
                                        <tr>
                                            <td><a href="javascript: void(0);" class="text-dark fw-medium">
                                                    <i class="fa-solid fa-book-open" style="color: #0E3C6E; font-size: 15px;"></i>
                                                    ${entry.key.name}</a></td>
                                            <td>${entry.value}</td>
                                            <td>${entry.value}</td>

                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
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
            <% session.removeAttribute("errorMessage"); %>
                }, 5000); // 5000 milliseconds = 5 seconds
            };
        </script>
    </body>
</html>