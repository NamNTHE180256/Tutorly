<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src='https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js'></script>
        <style>
            body {
                background: #D9D9D9;
                margin-top: 20px;
            }
            .card {
                background-color: #fff;
                border-radius: .25rem;
                margin-bottom: 1.5rem;
                box-shadow: 0 2px 6px 0 rgb(218 218 253 / 65%), 0 2px 6px 0 rgb(206 206 238 / 54%);
            }
            .navbar {
                margin-bottom: 20px;
            }
            .navbar .navbar-brand, .navbar-nav .nav-link {
                color: #fff;
            }
            .container .card-body {
                padding: 15px;
            }
            .profile-image{
                width: 150px;
                height: 150px;
                object-fit: cover;
            }
            .progress {
                height: 5px;
            }
        </style>
        <title>Profile</title>
    </head>
    <body>
        <%@ include file="TutorHeader.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-lg-4">
                    <div class="card">
                        <div class="card-body text-center">
                            <img src="image/${sessionScope.tutor.image}" alt="Tutor" class="rounded-circle p-1 profile-image" width="110">
                            <h4>${t.name}</h4>
                            <p class="text-secondary mb-1">${t.getUserInfo().role}</p>
                            <a href="TutorProfileController?service=updateRequest"><i class="fa-solid fa-pen"></i></a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-8">
                    <div class="card">
                        <div class="card-body">
                            <form action="TutorProfileController" method="POST" >

                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">ID</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <input readonly=""type="text"  class="form-control" value="${t.id}">
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Full Name</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <input type="text" name="name"  class="form-control" value="${t.name}">
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Email</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <input readonly=""type="text"  class="form-control" value="${t.getUserInfo().email}">
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Join Date</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <input readonly="" type="text"  class="form-control" value="${t.getUserInfo().createdAt}">
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Upload cert.</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <input type="file" class="form-control">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#changePasswordModal" style="background-color: #0E3C6E;">Change Password</button>
                                        <button type="submit" class="btn btn-primary" style="background-color: #0E3C6E;">Save changes</button>
                                        <input type="hidden" class="btn btn-primary" style="background-color: #0E3C6E;" name="action" value="add">
                                        <input type="hidden" class="btn btn-primary" style="background-color: #0E3C6E;" name="id" value="${t.id}">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="card mt-3">
                        <div class="card-body">
                            <h5 class="d-flex align-items-center mb-3">Subjects registered to teach:</h5>
                            <ul class="list-unstyled mb-3">
                                <c:forEach items="${subjectsTaught}" var="subject">
                                    <li>${subject.name}</li>
                                    </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal" id="changePasswordModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Change password</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;"></button>
                    </div>
                    <div class="modal-body">
                        <form> <!--<!-- them duong dan kem servlet -->
                            <div class="form-group">
                                <label for="currentPassword">Current Password</label>
                                <input type="password" id="currentPassword" class="form-control" placeholder="Current Password">
                                <label for="newPassword" class="mt-2">New Password</label>
                                <input type="password" id="newPassword" class="form-control" placeholder="New Password">
                                <label for="confirmNewPassword" class="mt-2">Confirm New Password</label>
                                <input type="password" id="confirmNewPassword" class="form-control" placeholder="Confirm New Password">
                            </div>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file = "tutor-footer.jsp" %>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </body>
</html>
