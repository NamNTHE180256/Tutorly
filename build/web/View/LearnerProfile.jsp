<%-- 
    Document   : StudentProfile
    Created on : Jun 2, 2024, 12:35:59 AM
    Author     : TRANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src='https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js'></script>
        <style>
            body{
                background: #D9D9D9;
                margin-top:20px;
            }
            .card {
                position: relative;
                display: flex;
                flex-direction: column;
                min-width: 0;
                word-wrap: break-word;
                background-color: #fff;
                background-clip: border-box;
                border: 0 solid transparent;
                border-radius: .25rem;
                margin-bottom: 1.5rem;
                box-shadow: 0 2px 6px 0 rgb(218 218 253 / 65%), 0 2px 6px 0 rgb(206 206 238 / 54%);
            }
            .me-2 {
                margin-right: .5rem!important;
            }
            .text-secondary{
                display: inline-flex;
            }
            .text-secondary input{
                margin: 5px;
            }
            .text-secondary i{
                padding-top: 15px;
            }
        </style>
        <title>Profile</title>
    </head>
    <body>
        <%@ include file = "StudentHeader.jsp" %>
        <hr/>
        <div class="container">
            <div class="main-body">
                <div class="row">
                    <div class="col-lg-4">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex flex-column align-items-center text-center">
                                    <img src="image/${linfo.image}" alt="Admin" class="rounded-circle p-1 " width="110">
                                    <div class="mt-3">
                                        <h4>${linfo.name}</h4>
                                        <p class="text-secondary mb-1">${linfo.getUserInfo().role}</p>

                                    </div>
                                </div>
                                <hr class="my-4">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-8">
                        <div class="card">
                            <div class="card-body">
                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">ID</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <input type="text" readonly class="form-control" value="${linfo.id}">
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Full Name</h6>
                                    </div>

                                    <div class="col-sm-9 text-secondary">
                                        <input type="text" readonly class="form-control" value="${linfo.name}">
                                        <a href="StudentProfileController?service=updateRequest"><i class="fa-solid fa-pen"></i></a>
                                    </div>




                                </div>
                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Email</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <input type="text" readonly class="form-control" value="${linfo.getUserInfo().email}">
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Join Date</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <input type="text" readonly class="form-control" value="${linfo.getUserInfo().createdAt}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3"></div>
                                    <div class="col-sm-9 text-secondary">
                                        <input type="button" class="btn btn-primary px-4" class="btn btn-primary" data-toggle="modal" 
                                               data-target="#myModal" style="background-color: #0E3C6E;" value="Change password">
                                    </div>
                                </div>


                                <div class="modal" id="myModal">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <!-- Modal Header -->
                                            <div class="modal-header">
                                                <h4 class="modal-title">Change password</h4>
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            </div>
                                            <!-- Modal body -->
                                            <div class="modal-body">
                                                <div class="container-fluid mt-3">
                                                    <form>
                                                        <!-- Vertical -->
                                                        <div class="form-group">
                                                            <label for="myEmail">Current Password</label>
                                                            <input type="email" id = "myEmail" class="form-control" placeholder="Email">
                                                            <label for="myPassword">Password</label>
                                                            <input type="password" id="myPassword" class="form-control" placeholder="Password" style="margin-bottom: 5px">
                                                            <input type="password" id="myPassword" class="form-control" placeholder="Enter password again" style="margin-bottom: 5px">
                                                            <button type="submit" class="btn btn-primary">Submit</button>
                                                        </div>
                                                    </form>
                                                </div>
                                                <!-- Modal footer -->
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
                                    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
                                    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>


                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="card">
                                        <div class="card-body">
                                            <c:forEach items="${scp_vector}" var="scp">
                                                <h5 class="d-flex align-items-center mb-3">Subject join</h5>

                                                <!-- Maths -->
                                                <c:choose>
                                                    <c:when test="${scp.subjectName == 'Maths'}">
                                                        <p>Maths</p>
                                                        <div class="progress mb-3" style="height: 5px">
                                                            <div class="progress-bar bg-primary" role="progressbar" style="width: ${scp.averagePercentage}%" aria-valuenow="${scp.averagePercentage}" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p>Maths</p>
                                                        <div class="progress mb-3" style="height: 5px">
                                                            <div class="progress-bar bg-primary" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>

                                                <!-- Physics -->
                                                <c:choose>
                                                    <c:when test="${scp.subjectName == 'Physics'}">
                                                        <p>Physics</p>
                                                        <div class="progress mb-3" style="height: 5px">
                                                            <div class="progress-bar bg-danger" role="progressbar" style="width: ${scp.averagePercentage}%" aria-valuenow="${scp.averagePercentage}" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p>Physics</p>
                                                        <div class="progress mb-3" style="height: 5px">
                                                            <div class="progress-bar bg-danger" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>

                                                <!-- Chemistry -->
                                                <c:choose>
                                                    <c:when test="${scp.subjectName == 'Chemistry'}">
                                                        <p>Chemistry</p>
                                                        <div class="progress mb-3" style="height: 5px">
                                                            <div class="progress-bar bg-success" role="progressbar" style="width: ${scp.averagePercentage}%" aria-valuenow="${scp.averagePercentage}" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p>Chemistry</p>
                                                        <div class="progress mb-3" style="height: 5px">
                                                            <div class="progress-bar bg-success" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>

                                                <!-- English -->
                                                <c:choose>
                                                    <c:when test="${scp.subjectName == 'English'}">
                                                        <p>English</p>
                                                        <div class="progress mb-3" style="height: 5px">
                                                            <div class="progress-bar bg-warning" role="progressbar" style="width: ${scp.averagePercentage}%" aria-valuenow="${scp.averagePercentage}" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p>English</p>
                                                        <div class="progress mb-3" style="height: 5px">
                                                            <div class="progress-bar bg-warning" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>

                                                <!-- Literature -->
                                                <c:choose>
                                                    <c:when test="${scp.subjectName == 'Literature'}">
                                                        <p>Literature</p>
                                                        <div class="progress mb-3" style="height: 5px">
                                                            <div class="progress-bar bg-info" role="progressbar" style="width: ${scp.averagePercentage}%" aria-valuenow="${scp.averagePercentage}" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p>Literature</p>
                                                        <div class="progress mb-3" style="height: 5px">
                                                            <div class="progress-bar bg-info" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </body>
</html>
