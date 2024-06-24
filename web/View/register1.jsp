<%-- 
    Document   : register1
    Created on : 29 thg 5, 2024, 11:24:25
    Author     : asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <div class="form-section">
                <h1>Join With Us</h1>
                <form id="registration-form" action="<%=request.getContextPath() %>/TutorRegisterServlet" method="post">
                    <input type="hidden" name="step" value="1">
                    <label for="full-name">Full name</label>
                    <input type="text" id="full-name" name="full-name" placeholder="Your full name" required>
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="Your email" required>
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password" required>
                    <label for="subject-name">Subject</label>
                    <select id="subject-id" name="subject-id" required>
                        <option value="" disabled selected>Subject taught</option>
                        <option value="1">Maths 10</option>
                        <option value="2">Maths 11</option>
                        <option value="3">Maths 12</option>
                        <option value="4">Physics 10</option>
                        <option value="5">Physics 11</option>
                        <option value="6">Physics 12</option>
                        <option value="7">Chemistry 10</option>
                        <option value="8">Chemistry 11</option>
                        <option value="9">Chemistry 12</option>
                        <option value="10">English 10</option>
                        <option value="11">English 11</option>
                        <option value="12">English 12</option>
                        <option value="13">Literature 10</option>
                        <option value="14">Literature 11</option>
                        <option value="15">Literature 12</option>
                    </select>
                    <label for="gender">Gender</label>
                    <select id="gender" name="gender" required>
                        <option value="" disabled selected>Your gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>

                    </select>
                    <button type="submit">Save and continue</button>
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
            window.location.href = 'View/register1.jsp';

            <% } %>
        </script>
        <script>
            const name = localStorage.getItem('full-name');
            const subjectName = localStorage.getItem('subject-name');
            const grade = localStorage.getItem('grade');

            if (name && subjectName && grade) {
                document.getElementById('display-name').textContent = name;
                document.getElementById('display-subject').textContent = `${subjectName} ${grade}`;
                    }

                    document.getElementById('upload-image').addEventListener('change', function (event) {
                        const imagePreview = document.getElementById('image-preview');
                        imagePreview.innerHTML = '';

                        const file = event.target.files[0];
                        if (file) {
                            const reader = new FileReader();
                            reader.onload = function (e) {
                                imagePreview.style.backgroundImage = `url(${e.target.result})`;
                            };
                            reader.readAsDataURL(file);
                        }
                    });
        </script>
    </body>
</html>