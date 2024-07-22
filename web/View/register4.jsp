<%-- 
    Document   : register4
    Created on : 29 thg 5, 2024, 11:24:57
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
            <div class="registration-form">
                <h1>EDUCATION</h1>
                <form id="education-setup-form" action="<%=request.getContextPath()%>/TutorRegisterServlet" method="post">
                    <input type="hidden" name="step" value="4">
                    <div class="form-group checkbox-group">
                        <input type="checkbox" id="no-education" name="no-education">
                        <label for="no-education">I don't have a higher education degree</label>
                    </div>
                    <div id="education-fields">
                        <div class="form-group">
                            <label for="school">School</label>
                            <input type="text" id="school" name="school" placeholder="University, High School, etc" >
                        </div>
                        <div class="form-group years-of-study">
                            <label for="years-of-study">Years of study</label>
                            <div class="years-inputs">
                                <select id="start-year" name="start-year">
                                    <!-- Options for start year -->
                                </select>
                                <span>-</span>
                                <select id="end-year" name="end-year">
                                    <!-- Options for end year -->
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-buttons">
                        <button type="submit" id="save-continue">Save and continue</button>
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
            <% if (request.getAttribute("message") != null) {%>
            alert('<%= request.getAttribute("message")%>');
            <% }%>
        </script>
        <script >
            const startYearSelect = document.getElementById('start-year');
            const endYearSelect = document.getElementById('end-year');

            const currentYear = new Date().getFullYear();
            for (let year = currentYear + 10; year >= 1990; year--) {
                let option = document.createElement('option');
                option.value = year;
                option.text = year;
                startYearSelect.add(option);

                option = document.createElement('option');
                option.value = year;
                option.text = year;
                endYearSelect.add(option);
            }

            document.getElementById('no-education').addEventListener('change', function (event) {
                const educationFields = document.getElementById('education-fields');
                const saveContinueButton = document.getElementById('save-continue');
                if (event.target.checked) {
                    educationFields.classList.add('hidden');
                    saveContinueButton.style.marginTop = '20px';
                } else {
                    educationFields.classList.remove('hidden');
                    saveContinueButton.style.marginTop = '0';
                }
            });
        </script>
    </body>
</html>