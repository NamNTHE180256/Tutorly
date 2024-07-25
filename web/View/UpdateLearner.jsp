<%-- 
    Document   : UpdateLearner
    Created on : Jun 2, 2024, 3:51:20 AM
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
        <title>Update Profile</title>
    <body>
        <%@ include file = "StudentHeader.jsp" %>

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
                            <form action="StudentProfileController" method="get">
                                <input type="text" class="form-control" name="name" value="${linfo.name}">
                                <input type="hidden" name="service" value="update">
                                <input type="submit" class="btn btn-primary" value="Submit">
                            </form>
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




                </div>
            </div>
        </div>
    </div>
</body>
</html>
