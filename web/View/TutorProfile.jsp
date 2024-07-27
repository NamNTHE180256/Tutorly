<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-QJHtvGhmr9b+2B4Kndd9+ma4u0p0z6ZR5q3tvJL+3ns5X8+bBiG8mQbtEVxneHBZ" crossorigin="anonymous"></script>
        <style>
            body {
                background: #D9D9D9;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
                margin: 0;
            }
            .content {
                flex: 1;
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
            .profile-image {
                width: 150px;
                height: 150px;
                object-fit: cover;
            }
            .progress {
                height: 5px;
            }
            .footer {
                background-color: #0E3C6E;
                color: white;
                padding: 20px 0;
                text-align: center;
            }
        </style>
        <title>Profile</title>
    </head>
    <body>
        <%@ include file="TutorHeader.jsp" %>
        <div class="content">
            <div class="container">
                <div class="row">
                    <div class="col-lg-4">
                        <div class="card">
                            <div class="card-body text-center">
                                <img src="image/${t.image}" alt="Tutor" class="rounded-circle p-1 profile-image" width="110">
                                <h4>${t.name}</h4>
                                <p class="text-secondary mb-1">${t.getUserInfo().role}</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-8">
                        <div class="card">
                            <div class="card-body">
                                <c:if test="${not empty sessionScope.successMessage}">
                                    <div class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center">
                                        ${sessionScope.successMessage}
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <%
                                        // Clear the notification after displaying it
                                        session.removeAttribute("successMessage");
                                    %>
                                </c:if>
                                <c:if test="${not empty sessionScope.errorMessage}">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert" style="text-align: center">
                                        ${sessionScope.errorMessage}
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <%
                                        // Clear the notification after displaying it
                                        session.removeAttribute("errorMessage");
                                    %>
                                </c:if>
                                <i>${requestScope.error}</i>
                                <form action="TutorProfileController" method="POST">

                                    <div class="row mb-3">
                                        <div class="col-sm-3">
                                            <h6 class="mb-0">ID</h6>
                                        </div>
                                        <div class="col-sm-9 text-secondary">
                                            <input readonly="" type="text" class="form-control" value="${t.id}">
                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-sm-3">
                                            <h6 class="mb-0">Full Name</h6>
                                        </div>
                                        <div class="col-sm-9 text-secondary">
                                            <input type="text" name="name" class="form-control" value="${t.name}">
                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-sm-3">
                                            <h6 class="mb-0">Email</h6>
                                        </div>
                                        <div class="col-sm-9 text-secondary">
                                            <input readonly="" type="text" class="form-control" value="${t.getUserInfo().email}">
                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-sm-3">
                                            <h6 class="mb-0">Join Date</h6>
                                        </div>
                                        <div class="col-sm-9 text-secondary">
                                            <input readonly="" type="text" class="form-control" value="${t.getUserInfo().createdAt}">
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
                                    <div class="row mb-3">
                                        <div class="col-sm-3">
                                            <h6 class="mb-0">Description</h6>
                                        </div>
                                        <div class="col-sm-9 text-secondary">
                                            <textarea name="bio" class="form-control" rows="6">${t.bio}</textarea>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#changePasswordModal" style="background-color: #0E3C6E;">Change Password</button>
                                            <button type="submit" class="btn btn-primary" style="background-color: #0E3C6E;">Save changes</button>
                                            <input type="hidden" class="btn btn-primary" style="background-color: #0E3C6E;" name="action" value="add">
                                            <input type="hidden" class="btn btn-primary" style="background-color: #0E3C6E;" name="id" value="${t.id}">


                                            <button class="btn btn-primary" type="button" style="    background-color: #0E3C6E;" id="logoutButton" type="button" class="btn btn-primary">Logout</button>




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
        </div>

        <div class="modal" id="changePasswordModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Change password</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;"></button>
                    </div>

                    <div class="modal-body">
                        <form action="${pageContext.request.contextPath}/changePassword" method="post">

                            <div class="form-group">
                                <label for="currentPassword">Current Password</label>
                                <input type="password" name="Currentpass" id="currentPassword" class="form-control" placeholder="Current Password">
                                <label for="newPassword" class="mt-2">New Password</label>
                                <input type="hidden" name="email" value="${sessionScope.user.email}">
                                <input type="password" name="newpass" id="newPassword" class="form-control" placeholder="New Password">
                                <label for="confirmNewPassword"  class="mt-2">Confirm New Password</label>
                                <input type="password" name="newpass2" id="confirmNewPassword" class="form-control" placeholder="Confirm New Password">
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

        <footer class="footer">
            <div class="container">
                <p style="margin: 0; font-size: 16px;">
                    Mọi góp ý, thắc mắc xin liên hệ Công ty cung cấp dịch vụ gia sư | Email: <a href="mailto:Tutory@gmail.com" style="color: #FFC107;">Tutory@gmail.com</a> | Điện thoại: <a href="tel:0123456789" style="color: #FFC107;">0123456789</a>
                </p>
                <p style="margin: 0; font-size: 16px;">© 2024 Power by TUTORLY</p>
            </div>
        </footer>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </body>
    <script>
        document.getElementById("logoutButton").addEventListener("click", function () {
            window.location.href = "logout"; // Chuyển hướng đến servlet LogoutController
        });
    </script>
</html>
