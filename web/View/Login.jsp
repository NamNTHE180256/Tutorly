<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/LoginStyle.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <!-- Font Awesome CDN Link -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
        <meta name="google-signin-client_id" content="119477182092-7uq7v6a5vivsekvp6ia8sfdg2tn9296e.apps.googleusercontent.com">
        <style>
            .password-container {
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
                        <h1 style=" font-family: 'Poppins', sans-serif;font-weight: 700;
                            font-size: 30px;
                            line-height: 45px;
                            vertical-align: middle;
                            padding-bottom: 30px;    white-space: nowrap;
                            margin: 0 24%;">
                            Log in to Tutorly
                        </h1>

                        <a style="white-space: nowrap; margin: 0 26%;" href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:9999/tutorly/LoginGoogle&response_type=code&client_id=598103268131-cq29u3qtj4hci367mp2a6jip7gp02iii.apps.googleusercontent.com&approval_prompt=force" class="btn btn-lg btn-blue">
                            <svg style="max-height: 20px;max-width: 20px" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="100" height="100" viewBox="0 0 48 48">
                            <path fill="#fbc02d" d="M43.611,20.083H42V20H24v8h11.303c-1.649,4.657-6.08,8-11.303,8c-6.627,0-12-5.373-12-12	s5.373-12,12-12c3.059,0,5.842,1.154,7.961,3.039l5.657-5.657C34.046,6.053,29.268,4,24,4C12.955,4,4,12.955,4,24s8.955,20,20,20	s20-8.955,20-20C44,22.659,43.862,21.35,43.611,20.083z"></path><path fill="#e53935" d="M6.306,14.691l6.571,4.819C14.655,15.108,18.961,12,24,12c3.059,0,5.842,1.154,7.961,3.039	l5.657-5.657C34.046,6.053,29.268,4,24,4C16.318,4,9.656,8.337,6.306,14.691z"></path><path fill="#4caf50" d="M24,44c5.166,0,9.86-1.977,13.409-5.192l-6.19-5.238C29.211,35.091,26.715,36,24,36	c-5.202,0-9.619-3.317-11.283-7.946l-6.522,5.025C9.505,39.556,16.227,44,24,44z"></path><path fill="#1565c0" d="M43.611,20.083L43.595,20L42,20H24v8h11.303c-0.792,2.237-2.231,4.166-4.087,5.571	c0.001-0.001,0.002-0.001,0.003-0.002l6.19,5.238C36.971,39.205,44,34,44,24C44,22.659,43.862,21.35,43.611,20.083z"></path>
                            </svg>
                            <span class="ms-2 fs-6">Sign in with Google</span>
                        </a>
                        <p style="margin: 0 50%; font-weight: 300">or</p>

                        <form method="post" action="${pageContext.request.contextPath}/Login">
                            <input type="text" name="email" placeholder="Username" required>

                            <div class="password-container">
                                <input type="password" id="password" name="password" placeholder="Password" required>
                                <span class="toggle-password"><i class="fa-solid fa-eye"></i></span>
                            </div>

                            <button type="submit" style=" font-family: 'Poppins', sans-serif;font-weight: 700; margin: 0 29%;width: 42%;" class="btn btn-primary btn-sm">
                                Login Now
                            </button>
                            <div><p style="color: red">${requestScope.messageError}</p></div>
                        </form>
                        <a href="${pageContext.request.contextPath}/View/forgotPassword.jsp" style="margin: 0 30%; white-space: nowrap">Forgot your password ? </a>
                        <br />

                        <div class="options-container" style="padding-top: 34px;">
                            <a href="" class="option">Become a <b style="color:chocolate; font-family: 'Inter'">tutor</b></a>
                            <a href="${pageContext.request.contextPath}/View/Register.jsp" class="option">Become a <b style="color: brown; font-family: 'Inter';">Learner</b></a>
                        </div>
                        <div style="margin:0 36%" class="g-signin2" data-onsuccess="onSignIn"></div>
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
