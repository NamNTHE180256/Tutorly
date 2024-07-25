<%-- 
    Document   : register3
    Created on : 29 thg 5, 2024, 11:24:49
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
                <h1>CERTIFICATION</h1>
                <form id="certification-setup-form" action="<%=request.getContextPath()%>/TutorRegisterServlet" method="post" >
                    <input type="hidden" name="step" value="3">
                    <div class="form-group checkbox-group">
                        <input type="checkbox" id="no-certification" name="no-certification">
                        <label for="no-certification">I don't have a certification</label>
                    </div>
                    <div id="certification-fields">
                        <div class="form-group">
                            <label for="subject">Subject</label>
                            <input type="text" id="subject" name="subject" placeholder="Tutor's subject" required readonly>
                        </div>
                        <div class="upload-section">
                            <label for="upload-certification" class="upload-label">
                                <input type="file" id="upload-certification" name="upload-certification" accept="image/*">
                                Upload certification
                            </label>
                            <span>JPG or PNG format, maximum size of 20MB</span>
                        </div>
                        <div class="form-group">
                            <label for="description">Description</label>
                            <input type="text" id="description" name="description" placeholder="Description" >
                        </div>
                        <div class="form-group">
                            <label for="issued-by">Issued by</label>
                            <input type="text" id="issued-by" name="issued-by" placeholder="Issued by" >
                        </div>
                        <a href="#" id="more-certification">More certification</a>
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
            const subject = localStorage.getItem('subject-name');
            if (subject) {
                document.getElementById('subject').value = subject;
            }

            document.getElementById('no-certification').addEventListener('change', function (event) {
                const certificationFields = document.getElementById('certification-fields');
                if (event.target.checked) {
                    certificationFields.classList.add('hidden');
                } else {
                    certificationFields.classList.remove('hidden');
                }
            });

            document.getElementById('more-certification').addEventListener('click', function (event) {
                event.preventDefault();
                if (confirm("Do you want to enter another certification?")) {
                    storeCertification();

                    // Clear form for new certification
                    document.getElementById('upload-certification').value = "";
                    document.getElementById('description').value = "";
                    document.getElementById('issued-by').value = "";
                }
            });

            function storeCertification() {
                const certification = {
                    subject: document.getElementById('subject').value,
                    description: document.getElementById('description').value,
                    issuedBy: document.getElementById('issued-by').value
                };
                console.log("Certification stored:", certification);
                localStorage.setItem('certification', JSON.stringify(certification));
            }

        </script>
    </body>
</html>