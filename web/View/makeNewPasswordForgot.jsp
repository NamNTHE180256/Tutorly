<%-- 
    Document   : makeNewPasswordForgot
    Created on : Jun 6, 2024, 10:52:01 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/LoginStyle.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
        <meta name="google-signin-client_id" content="119477182092-7uq7v6a5vivsekvp6ia8sfdg2tn9296e.apps.googleusercontent.com">
        <style> .password-container {
                position: relative;
            }
            .toggle-password {
                position: absolute;
                top: 50%;
                right: 10px;
                transform: translateY(-50%);
                cursor: pointer;
            }</style>

        <script src="${pageContext.request.contextPath}/js/site.js"></script>
    </head>
    <body>
        <div class="container-fluid vh-100">
            <div class="row h-100">
                <div class="col-md-6 d-flex justify-content-center align-items-center bg-white text-dark position-relative left-half">
                    <div class="logo-container">
                        <a href="#"><img style="width: 100px;margin: -10% 1px;" src="${pageContext.request.contextPath}/Pictures/LOGO_TUTORLY.png" class="logo" alt="Brand Logo" /></a>
                    </div>
                    <div class="login-container mt-5">
                        <h1 style="font-family: 'Poppins', sans-serif; font-weight: 700; font-size: 30px; line-height: 45px; vertical-align: middle;">
                            Enter new Password!
                        </h1>

                        <form class="register-form" action="${pageContext.request.contextPath}/forgotPassword" method="post">

                            <div class="password-container">
                                <input type="password" id="password" name="password" placeholder="Password" required>
                                <span class="toggle-password"><i class="fa-solid fa-eye"></i></span>
                            </div>
                            <div class="password-container">
                                <input type="password" id="passwordConfirm" name="passwordConfirm" placeholder="Confirm Password" required>
                                <span class="toggle-password"><i class="fa-solid fa-eye"></i></span>
                            </div>

                            <!--    <input type="hidden" name="action" value="register"> -->
                            <button style="    padding-left: 2px;
                                    white-space: nowrap;
                                    margin: 0 35%;" type="submit" class="btn btn-primary btn-sm register-button">
                                Reset
                            </button>

                        </form>
                        <div><p style="color: red">${requestScope.messageError}</p></div>
                        <div>
                            Already have an account? <a href="login.jsp  " class="" style="margin-left: 10px; color:black">
                                Login
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 d-flex justify-content-center align-items-center  text-light right-half">
                    <div class="border border-light rounded" id="miniDescription-login">
                        <div class="banner">
                            <div class="content">
                                <p>Find Tutor</p>
                                <p><b>Find Future</b></p>
                                <p style="margin-left: 42px; font-weight:bold; margin-top: -24px;">
                                    With <img src="${pageContext.request.contextPath}/Pictures/LOGO_TUTORLY.png" alt="Tutorly Logo" />
                                </p>
                            </div>
                        </div>
                        <div class="banner-logo">
                            <img src="${pageContext.request.contextPath}/Pictures/banner-descrition.png" style="margin: 12%;height: 50%;width: 121%;">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
