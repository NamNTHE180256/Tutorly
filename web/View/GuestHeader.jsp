<%-- 
    Document   : StudentHeader
    Created on : Jun 2, 2024, 5:43:03 PM
    Author     : TRANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>
            .head {
                margin: 0;
                padding: 0;
            }

            .navheader {
                margin-top: 0;
                margin-bottom: 0;
                margin-left: 20px;
                margin-right: 20px;
                padding: 0;
            }

            .logo_img {
                height: 40px;
            }

            .head_link a {
                color: #0E3C6E;
            }

            .head_icon {
                margin-top: 15px;
                margin-right: 5px;
                font-size: 20px;
                color: #0E3C6E;
            }

            .student_profile_image {
                margin-top: 15px;
                height: 50px;
            }

            .learnername {
                margin-top: 15px;
                color: #0E3C6E;
            }


            /*            .navbarmenu {
                            margin-top: 0;
                            margin-bottom: 10px;
                            margin-left: 20px;
                            
                        }
            
                        .navmenuitem {
                            margin-right: 25px;
                            background-color: #0E3C6E;
                            border-radius: 10px;
                            width: 120px;
                            
                             text-align: center;
                        }
                        
                        .navmenuitem a {
                            color: aliceblue;
                             justify-content: center;
                        }*/


            .content {
                padding-top: 20px;
                background-color: #E6E6E6;
            }
        </style>
        <title>JSP Page</title>
    </head>
    <body >
        <nav class="navbar navbar-expand-lg">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                        data-bs-target="#navbarExample" aria-controls="navbarExample" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <a class="navbar-brand" href="View/HomePage.jsp"><img src="image/LOGO_TUTORLY.png" class="logo_img"></a>
                <div class="collapse navbar-collapse" id="navbarExample">
                    <ul class="navbar-nav me-auto mb-0">
                        <!-- Find tutor -->
                        <li class="nav-item">

                            <a class="nav-link" href="../Tutorly/TutorController">Finding a tutor</a>

                        </li>

                    </ul>
                </div>   
            </div>
    </body>
</html>
