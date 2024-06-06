<%-- 
    Document   : AdminLearner
    Created on : Jun 1, 2024, 9:57:54 PM
    Author     : Admin
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Learner</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <style>
            .stu_image {
                width: 100px; /* Adjust the width as needed */
                height: 100px; /* Adjust the height as needed */
                object-fit: cover; /* Ensure the image fits within the dimensions without distortion */
                border-radius: 50%; /* Optional: make the image circular */
                border: 2px solid #ccc; /* Optional: add a border */
            }
        </style>
    </head>
    <body>
        <%@ include file="AdminHeader.jsp" %>
        <div class="container">
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
                                <th>Learner ID</th>
                                <th>Avatar</th>
                                <th>Learner Name</th>
                                <th>Learner Email</th>
                                <th>No. Class</th>
                                <th>Detail</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="learner" items="${learners}">
                                <tr>
                                    <td>${learner.id}</td>
                                    <td><img src="Image/${learner.image}" class="stu_image"></td>
                                    <td><strong>${learner.name}</strong> </td>
                                    <td>${learner.getUserInfo().email}</td>
                                    <td>1</td>
                                    <td><a href="#"><i class="fa-solid fa-eye" style=" color: #0E3C6E;"></i>View Details</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
