<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Become a tutor</title>
        <link rel="stylesheet" href="../style/styleTutorRegister.css"/>
        <style>
            .image-preview {
                width: 150px;
                height: 150px;
                border: 1px solid #ccc;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #f0f0f0;
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
            }
        </style>
    </head>
    <body>
        <header>
            <img src="../image/LOGO_Main.png" alt="Tutorly Logo" class="logo">
        </header>
        <div class="container">
            <div class="registration-form">
                <h1>CHOOSE A PROFILE PHOTO</h1>
                <p>Choose a photo that helps learners get to know you</p>
                <form id="profile-setup-form" action="<%=request.getContextPath()%>/TutorRegisterServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="step" value="2">
                    <div class="form-group">
                        <hr class="separator">
                        <div class="profile-display">
                            <div id="image-preview" class="image-preview">Tutor image</div>
                            <div class="name-subject">
                                <div id="display-name" class="name">Name: <%= (String) session.getAttribute("name") %></div>
                                <div id="display-subject" class="subject">Subject: <%= (String) session.getAttribute("subject") %></div>
                            </div>
                        </div>
                    </div>
                    <hr class="separator">
                    <div class="upload-section">
                        <label for="upload-image" class="upload-label">
                            <input type="file" id="upload-image" name="upload-image" accept="image/*" required>
                            <div>Upload image</div>
                        </label>
                    </div>
                    <p>What a profile photo should be?</p>
                    <div class="sample-images">
                        <img src="../image/girl.png" alt="Sample 1">
                        <img src="../image/man.png" alt="Sample 2">
                        <img src="../image/woman.png" alt="Sample 3">
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
            <% if (request.getAttribute("message") != null) { %>
            alert('<%= request.getAttribute("message") %>');
            window.location.href = 'View/register2.jsp';
            <% } %>
            window.onload = function () {
                document.getElementById('upload-image').addEventListener('change', function (event) {
                    const imagePreview = document.getElementById('image-preview');
                    imagePreview.innerHTML = ''; // Clear any existing content

                    const file = event.target.files[0];
                    if (file) {
                        const reader = new FileReader();
                        reader.onload = function (e) {
                            imagePreview.style.backgroundImage = `url(${e.target.result})`;
                            imagePreview.style.backgroundSize = 'cover';
                            imagePreview.style.backgroundPosition = 'center';
                            imagePreview.style.backgroundRepeat = 'no-repeat';
                        };
                        reader.readAsDataURL(file);
                    }
                });
            };
        </script>

    </body>
</html>
