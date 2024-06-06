<%-- 
    Document   : authorize
    Created on : Jun 6, 2024, 2:24:31 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/LoginStyle.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"></script>

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
            }
            .google-icon {
                max-width: 20px;
                max-height: 20px;
            }
        </style>
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
                            Enter a code sent to your Email !!!!
                        </h1>

                        <c:choose>
                            <c:when test="${requestScope.action == 'register'}">
                                <form class="register-form" action="authorize" method="post">
                                    <input style="width: 68%; padding: 7px; border-radius: 6px;" type="number" name="codeAuthorize" placeholder="Enter a code" required>
                                    <input type="hidden" name="action" value="register">
                                    <button style="margin-bottom: 5px;" type="submit" class="btn btn-primary btn-sm register-button">
                                        Register
                                    </button>
                                </form>
                                <a id="send-again-btn" href="authorize?action=register" class="btn btn-link" style="margin-left: 10px; color:red">
                                    Send again
                                </a>
                            </c:when>
                            <c:when test="${requestScope.action == 'forgotPassword'}">
                                <form class="register-form" action="forgotPassword" method="get">
                                    <input style="width: 68%; padding: 7px; border-radius: 6px;" type="number" name="codeAuthorize" placeholder="Enter a code" required>
                                    <input type="hidden" name="action" value="forgotPassword">
                                    <button style="margin-bottom: 5px;" type="submit" class="btn btn-primary btn-sm register-button">
                                        Submit
                                    </button>
                                </form>
                                <a id="send-again-btn" href="authorize?action=forgotPassword" class="btn btn-link" style="margin-left: 10px; color:red">
                                    Send again
                                </a>
                            </c:when>
                        </c:choose>

                        <c:if test="${not empty requestScope.sendAgain}">
                            <p>${requestScope.sendAgain}</p>
                        </c:if>
                        <div>
                            <p style="color: red">${requestScope.messageError}</p>
                        </div>
                        <div>
                            Already have an account? <a href="../View/login.jsp" class="" style="margin-left: 10px; color:black">
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
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        var sendAgainBtn = document.getElementById("send-again-btn");
        var timeLeft = 30;
        var timerDisplay = document.createElement("div");
        timerDisplay.style.marginTop = "10px";
        timerDisplay.style.fontSize = "14px";
        timerDisplay.style.color = "red";
        sendAgainBtn.parentElement.appendChild(timerDisplay);

        function startCountdown() {
            sendAgainBtn.disabled = true;
            sendAgainBtn.style.pointerEvents = "none";
            timerDisplay.innerText = "Please wait " + timeLeft + " seconds to resend";

            var countdown = setInterval(function () {
                timeLeft--;
                timerDisplay.innerText = "Please wait " + timeLeft + " seconds to resend";
                if (timeLeft <= 0) {
                    clearInterval(countdown);
                    sendAgainBtn.disabled = false;
                    sendAgainBtn.style.pointerEvents = "auto";
                    timerDisplay.innerText = "";
                }
            }, 1000);
        }

        if (sendAgainBtn) {
            startCountdown(); // Start countdown on page load if the sendAgain message is present
        }

        sendAgainBtn.addEventListener("click", function (event) {
            startCountdown();
        });
    });
</script>
</body>
</html>
