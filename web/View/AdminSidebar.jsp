<%-- 
    Document   : AdminSidebar
    Created on : Jun 25, 2024, 11:41:34 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpSession" %>

<%
    // Get the current page name from the request URL
    String currentPage = request.getRequestURI() + "?" + request.getQueryString();
%>
<!doctype html>
<html lang="en">
    <head>
        <title>Manage</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="../style/AdminSidebar.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body >
        <div class="wrapper d-flex align-items-stretch">
            <nav id="sidebar">
                <div class="custom-menu">
                    <button type="button" id="sidebarCollapse" class="btn btn-primary">
                        <i class="fa fa-bars"></i>
                        <span class="sr-only">Toggle Menu</span>
                    </button>
                </div>
                <div class="p-4" >
                    <h1><a href="index.html" class="logo"><img style="width: 150px;" src="../image/LOGO_TUTORLY (1).png"> <span>Find tutor Find future</span></a></h1>
                    <ul class="list-unstyled components mb-5">
                        <li class="active">
                            <a class="nav-link <%= currentPage.contains("dashboard") ? "active" : "" %>" href="${pageContext.request.contextPath}/AdminController?action=dashboard"><span class="mr-3"><i class="fa-solid fa-house"></i></span> Dashboard</a>
                        </li>
                        <li>
                            <a href="#"><span class="mr-3"><i class="fa-solid fa-chalkboard-user"></i></span> Tutor</a>
                        </li>
                        <li>
                            <a href="#"><span class="mr-3"><i class="fa-solid fa-user"></i></span> Learner</a>
                        </li>
                        <li>
                            <a href="#"><span class="mr-3"><i class="fa-solid fa-book-bookmark"></i></span> Subject</a>
                        </li>
                        <li>
                            <a href="#"><span class="mr-3"><i class="fa-solid fa-sack-dollar"></i></span> Income</a>
                        </li>

                    </ul>


                    <div class="footer">
                        <p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                            Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib.com</a>
                            <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
                    </div>

                </div>
            </nav>
        </div>

        <script src="js/jquery.min.js"></script>
        <script src="js/popper.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>
