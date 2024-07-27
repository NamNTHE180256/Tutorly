<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/core@5.10.1/main.min.js'></script>
        <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@fullcalendar/core@5.10.1/main.min.css' />
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet"
              integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
        <style>
            .header {
                text-align: center;
                margin: 20px 0;
            }

            .document-list {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                padding: 20px;
                background-color: #f8f9fa;
                border-radius: 0.5rem;
            }

            .document-list nav {
                display: flex;
                flex-direction: column;
                align-items: center;
                margin: 91px;
            }

            .document-list h2 {
                font-size: 1.5rem;
                color: #333;
            }

            .document-list a {
                color: #007bff;
                margin-top: 10px;
            }

            @media (max-width: 768px) {
                .document-list {
                    flex-direction: column;
                    align-items: center;
                }

                .vertical-line {
                    border-left: none;
                    border-top: 2px solid #ccc;
                    width: 80%;
                    height: auto;
                    margin: 20px 0;
                }
            }
        </style>
    </head>
    <body>

        <header>
            <c:choose>
                <c:when test="${user.role eq 'tutor'}">
                    <%@ include file="TutorHeader.jsp" %>
                </c:when>
                <c:otherwise>
                    <%@ include file="StudentHeader.jsp" %>
                </c:otherwise>
            </c:choose>
        </header>
        <hr/>
        <h1 class="header">Material</h1>
        <div class="layout">
            <main style="width: 50%;" class="layout-main px-4">
                <c:if test="${not empty fileUrl}">
                    <iframe  src="${fileUrl}" width="1500" height="600" frameborder="0"></iframe>
                    </c:if>
                    <c:if test="${not empty fileUrlYtb}">
                    <iframe style="" src="${fileUrlYtb}" width="1200" height="600" frameborder="0"></iframe>
                    </c:if>

            </main>


            <div class="col-2"  style="margin-left:50px">
                <nav id="myScrollspy">
                    <ul class="nav nav-pills flex-column">
                        <div class="accordion" id="accordionPanelsStayOpenExample">
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="panelsStayOpen-headingOne">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="false" aria-controls="panelsStayOpen-collapseOne">
                                        Slide
                                    </button>
                                </h2>
                                <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse" aria-labelledby="panelsStayOpen-headingOne">
                                    <div class="accordion-body">
                                        <c:forEach var="x" items="${slide}">
                                            <li class="list-group-item list-group-item-action">
                                                <a href="Material?action=download&slotid=${slotid}&id=${x.id}&classid=${classid}">
                                                    ${x.fileName}
                                                </a>
                                            </li>
                                        </c:forEach>                                        </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="panelsStayOpen-headingTwo">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false" aria-controls="panelsStayOpen-collapseTwo">
                                        Documents
                                    </button>
                                </h2>
                                <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse" aria-labelledby="panelsStayOpen-headingTwo">
                                    <div class="accordion-body">
                                        <c:forEach var="x" items="${doc}">
                                            <a href="Material?action=download&slotid=${slotid}&id=${x.id}&classid=${classid}">
                                                ${x.fileName}
                                            </a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="panelsStayOpen-headingThree">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseThree" aria-expanded="false" aria-controls="panelsStayOpen-collapseThree">
                                        E-book
                                    </button>
                                </h2>
                                <div id="panelsStayOpen-collapseThree" class="accordion-collapse collapse" aria-labelledby="panelsStayOpen-headingThree">
                                    <c:forEach var="material" items="${book}">
                                        <p><strong>${material.getFileName()}</strong> : <a href="#" onclick="displayMaterial('${material.getFilePath()}'); return false;">Link book</a></p>
                                    </c:forEach>

                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="panelsStayOpen-headingThree">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseThree" aria-expanded="false" aria-controls="panelsStayOpen-collapseThree">
                                        Records/ Videos
                                    </button>
                                </h2>
                                <div id="panelsStayOpen-collapseThree" class="accordion-collapse collapse" aria-labelledby="panelsStayOpen-headingThree">
                                    <c:forEach var="x" items="${listvideo}">
                                        <li class="list-group-item list-group-item-action">
                                            <a href="Material?action=downloadVideo&slotid=${slotid}&id=${x.id}&classid=${classid}">
                                                ${x.fileName}
                                            </a>
                                        </li>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                    </ul>
                </nav>
            </div>

        </div>
        <footer style="
                margin-top: 5%;
                background-color: #0E3C6E;
                color: white;
                padding: 20px 0;
                text-align: center;
                bottom: 0;
                width: 100%;">
            <p style="margin: 0; font-size: 16px;">
                Mọi góp ý, thắc mắc xin liên hệ Công ty cung cấp dịch vụ gia sư | Email: <a href="mailto:Tutory@gmail.com" style="color: #FFC107;">Tutory@gmail.com</a> | Điện thoại: <a href="tel:0123456789" style="color: #FFC107;">0123456789</a>
            </p>
            <p style="margin: 0; font-size: 16px;">
                © 2024 Power by TUTORLY
            </p>
        </div>
    </footer>
</body>
</html>
