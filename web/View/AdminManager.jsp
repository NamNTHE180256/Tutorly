<%-- 
    Document   : AdminManager
    Created on : Jun 15, 2024, 5:40:02 PM
    Author     : Admin
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manager</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <style>
            .table th, .table td {
                text-align: center;
                vertical-align: middle;
            }

            .search {
                width: 80%;
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
            <div class="row w-100" >
                <div class="col d-flex align-items-center" style="margin-top: 15px">
                    <a class="btn" style="background-color: #0E3C6E; color: #ffffff" data-toggle="modal" data-target="#addManagerModal">Add a manager</a>
                </div>
                <div class="col d-flex justify-content-end align-items-center" style="margin-top: 15px">
                    <form class="search" action="AdminController">
                        <input type="hidden" name="action" value="searchManager"/>
                        <input type="text" class="search-input" placeholder="Search..." name="searchManager" value="${param.searchManager}">
                        <button type="submit" class="search-icon">
                            <i class="fa fa-search"></i>
                        </button>
                    </form>
                </div>
            </div>
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
            <div class="row justify-content-center">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-6">
                            <h3></h3>
                        </div>
                    </div>
                    <table class="table table-hover">
                        <thead style="background-color: #0E3C6E; color: white;">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Approved Tutors</th>
                                <th>Rejected Tutors</th>
                                <th>Blocked Tutors</th>
                                <th>Tools</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="manager" items="${managers}">
                                <tr>
                                    <td>${manager.id}</td>
                                    <td><strong>${manager.name}</strong> </td>
                                    <td>${manager.getUserInfo().email}</td>
                                    <td>${manager.getApprovedTutor()}</td>
                                    <td>${manager.getRejectedTutor()}</td>
                                    <td>${manager.blockedTutor}</td>
                                    <td>
                                        <a href="AdminController?action=deleteManager&managerId=${manager.id}" 
                                           class="btn btn-outline-danger btn-sm"
                                           onclick="return confirm('Are you sure you want to delete the manager with id = ${manager.id}?');">
                                            Delete
                                        </a>
                                        <button class="btn btn-outline-warning btn-sm" 
                                                data-toggle="modal" 
                                                data-target="#updateModal"
                                                data-id="${manager.id}"
                                                data-name="${manager.name}"
                                                data-email="${manager.getUserInfo().email}">
                                            Update
                                        </button>
                                        <a href="ManagerController?action=details&managerId=${manager.id}" class="btn btn-outline-primary btn-sm">Details</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form action="AdminController">
                        <div class="modal-header">
                            <h5 class="modal-title" id="updateModalLabel">Update Manager</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="action" value="updateManager">
                            <input type="hidden" name="managerId" id="managerId">
                            <div class="form-group">
                                <label for="managerName">Name</label>
                                <input type="text" class="form-control" id="managerName" name="name" required>
                            </div>
                            <div class="form-group">
                                <label for="managerEmail">Email</label>
                                <input type="email" class="form-control" id="managerEmail" name="email" required>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="addManagerModal" tabindex="-1" role="dialog" aria-labelledby="addManagerModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form id="addManagerForm" action="AdminController" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addManagerModalLabel">Add Manager</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <!-- Form Fields -->
                            <input type="hidden" name="action" value="addManager">
                            <div class="mb-3">
                                <label for="addManagerName" class="form-label">Name</label>
                                <input type="text" class="form-control" id="addManagerName" name="name">
                                <div class="invalid-feedback">This field is required.</div>
                            </div>
                            <div class="mb-3">
                                <label for="addManagerEmail" class="form-label">Email</label>
                                <input type="email" class="form-control" id="addManagerEmail" name="email">
                                <div class="invalid-feedback">This field is required.</div>
                            </div>
                            <div class="mb-3">
                                <label for="managerPassword" class="form-label">Password</label>
                                <input type="password" class="form-control" id="managerPassword" name="password">
                                <div class="invalid-feedback">This field is required.</div>
                            </div>
                            <div class="mb-3">
                                <label for="confirmPassword" class="form-label">Confirm Password</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword">
                                <div class="invalid-feedback">This field is required.</div>
                            </div>
                            <!-- Alert -->
                            <div class="alert alert-danger d-none" role="alert" id="passwordAlert">
                                <div d-flex align-items-center>
                                    <i class="fa-solid fa-circle-exclamation icon-danger mr-2"></i>
                                    <span>Passwords do not match</span>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>

        <script>
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

            $('#updateModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget); // Button that triggered the modal
                var id = button.data('id');
                var name = button.data('name');
                var email = button.data('email');

                var modal = $(this);
                modal.find('#managerId').val(id);
                modal.find('#managerName').val(name);
                modal.find('#managerEmail').val(email);
            });

            function validateForm() {
                var isValid = true;
                var alertDiv = document.getElementById("passwordAlert");

                // Reset alert
                alertDiv.classList.add("d-none");

                // Validate fields
                var fields = ["addManagerName", "addManagerEmail", "managerPassword", "confirmPassword"];
                fields.forEach(function (fieldId) {
                    var field = document.getElementById(fieldId);
                    if (!field.value.trim()) {
                        field.classList.add("is-invalid");
                        isValid = false;
                    } else {
                        field.classList.remove("is-invalid");
                    }
                });

                // Validate password match
                var password = document.getElementById("managerPassword").value;
                var confirmPassword = document.getElementById("confirmPassword").value;
                if (password !== confirmPassword) {
                    alertDiv.classList.remove("d-none");
                    isValid = false;
                }

                return isValid;
            }

            document.getElementById("addManagerForm").onsubmit = function () {
                return validateForm();
            };
        </script>
    </body>
</html>
