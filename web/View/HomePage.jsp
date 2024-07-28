<%-- 
    Document   : HomePage
    Created on : Jun 3, 2024, 2:42:13 PM
    Author     : Tung Duong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tutorly</title>
<!--        <link rel="icon" type="image/x-icon" href="image/LOGO_TUTORLY-_1_.ico">-->
        <!-- Font Awesome CDN Link -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Header CSS -->
        <link rel="stylesheet" href="../style/header.css">
        <!-- Custom CSS File Link -->
        <link rel="stylesheet" href="../style/homepage.css">
    </head>
    <body>
        <!-- Start Header -->
        <header class="header">
            <nav class="navbar navbar-expand-lg fixed-top">
                <div class="container-fluid">
                    <a href="HomePage.jsp" class="img me-auto">
                        <img src="../image/LOGO_TUTORLY.png" style="max-height: 50px;">
                    </a>
                    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
                        <div class="offcanvas-header">
                            <h5 class="offcanvas-title" id="offcanvasNavbarLabel">Tutorly</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                        </div>
                        <div class="offcanvas-body">
                            <ul class="navbar-nav justify-content-start flex-grow-1 pe-3">
                                <li class="nav-item">
                                    <a class="nav-link mx-lg-2 active" aria-current="page" href="../View/Login.jsp">Become a learner</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link mx-lg-2 active" aria-current="page" href="../View/register1.jsp">Become a tutor</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link mx-lg-2 active" aria-current="page" href="../FindingTutorController">Finding a tutor</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="login-icon">
                        <a href="Login.jsp" class="login-button">
                            <span class="button-icon">
                                <i class="fa-solid fa-arrow-right-to-bracket"></i>
                            </span>
                            <span class="button-text">
                                Login
                            </span>
                        </a>
                    </div>
                    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                </div>
            </nav>
        </header>
        <!-- End Header -->

        <!-- Start Body -->
        <main>
            <section class="hero">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-md-6">
                            <div class="hero-content">
                                <h1 style="font-weight: 750; font-size: 53px;">Unlock your potential <br> with the best <br> tutors.</h1>
                                <a href="Login.jsp" class="btn btn-primary">Get started <i class="fa-solid fa-arrow-right"></i></a>
                            </div>
                        </div>
                        <div class="col-md-6 text-center">
                            <img src="../image/banner.png" class="img-fluid" alt="Banner Image">
                        </div>
                    </div>
                </div>
            </section>

            <section class="stats">
                <div class="container">
                    <div class="row text-center">
                        <div class="col-4">
                            <div class="stat">
                                <h3>100+</h3>
                                <p>Tutors</p>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="stat">
                                <h3>150+</h3>
                                <p>Learners</p>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="stat">
                                <h3>10+</h3>
                                <p>Subjects</p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
        <!-- End Body -->

        <!-- Start Footer -->
        <span>
            <%@ include file = "Footer.jsp" %>
        </span>
            
      
        <!-- End Footer -->
        
        <!-- Scripts -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        
    </body>
    
</html>
