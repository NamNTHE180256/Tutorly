<%-- 
    Document   : register5
    Created on : 29 thg 5, 2024, 11:25:13
    Author     : asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Become a tutor</title>
        <link rel="stylesheet" href="../style/styleTutorRegister.css"/>
    </head>
    <body>
        <header>
            <img src="../image/LOGO_Main.png" alt="Tutorly Logo" class="logo">
        </header>
        <div class="container">
            <div class="registration-form">
                <h1>INTRODUCE YOURSELF</h1>
                <form id="introduce-setup-form" action="<%=request.getContextPath() %>/TutorRegisterServlet" method="post">
                    <input type="hidden" name="step" value="5">
                    <div class="form-group">
                        <label for="introduction">Introduce yourself</label>
                        <textarea id="introduction" name="introduction" placeholder="Teaching experience, ......" required></textarea>
                    </div>
                    <div class="form-buttons">
                        <button type="submit" id="done-button">Done</button>
                    </div>
                </form>
            </div>
            <div class="image-section">
                <div class="image-content">
                    <div class="transparent-square">
                        <h2>Become a <div><span style="color: orange;">part</span> of</div></h2>
                        <img id="logo-sentence" src="../image/LOGO_BIG.png" alt="logo">
                    </div>
                </div>
                <img src="../image/rightPic.png" alt="Blackboard">
            </div>
        </div>
        <script>
            <% if (request.getAttribute("message") != null) { %>
                alert('<%= request.getAttribute("message") %>');
                window.location.href = 'View/Login.jsp';
            <% } %>
        </script>
    </body>
</html>