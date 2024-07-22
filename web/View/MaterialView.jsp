<%-- 
    Document   : MaterialView
    Created on : Jun 16, 2024, 1:18:52 AM
    Author     : TRANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Material</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.10.377/pdf.min.js"></script>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    </head>
    <body>
        <%@ include file = "StudentHeader.jsp" %>

        <div class="container-fluid" style="margin-bottom: 20px">
            <div class="row justify-content-end">
                <div class="col-auto"> <!-- Adjust the column width as needed -->
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" 
                                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false" style="background-color: #0E3C6E">
                            Tutor - Subject
                        </button>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <c:forEach items="${class_vector}" var="c">
                                <li><a class="dropdown-item" href="../Tutorly/MaterialController?service=viewClassMaterial&classid=${c.getId()}">
                                        ${c.getTutor().getName()} - ${c.getTutor().getSubject().getName()} - ${c.getStartDate()}</a></li>
                                    </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <c:if test="${empty param.classid}">
            <div class="blankslate">
                <img style="width: 200px" src="image/Click.png" />
                <div class="blankslate-body">
                    <h4>You must choose the class to view material</h4>
                    <p>
                        To view the material, you will need to choose the class which you have been joined.
                    </p>
                </div>
                <div class="blankslate-actions">
                    <a class="nav-link" href="../Tutorly/DashboardController"><button class="btn btn-default" style="background-color: #0E3C6E; color: white;" type="button">Back to Dashboard</button></a>

                </div>
            </div>
        </c:if>

        <c:if test="${not empty param.classid}">
            <div class="row">
                <div class="col-2"  style="margin-left:50px">
                    <nav id="myScrollspy">
                        <ul class="nav nav-pills flex-column">
                            <div class="accordion" id="accordionPanelsStayOpenExample">
                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="panelsStayOpen-headingOne">
                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="false" aria-controls="panelsStayOpen-collapseOne">
                                            Documents
                                        </button>
                                    </h2>
                                    <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse" aria-labelledby="panelsStayOpen-headingOne">
                                        <div class="accordion-body">
                                            <c:forEach var="material" items="${doc}">
                                                <p><strong>${material.getFileName()}</strong> : <a href="#" onclick="loadPDF('${material.getFilePath()}'); return false;">${material.getFilePath()}</a></p>
                                                </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="panelsStayOpen-headingTwo">
                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false" aria-controls="panelsStayOpen-collapseTwo">
                                            Slides
                                        </button>
                                    </h2>
                                    <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse" aria-labelledby="panelsStayOpen-headingTwo">
                                        <div class="accordion-body">
                                            <c:forEach var="material" items="${slide}">
                                                <p><strong>${material.getFileName()}</strong> : <a href="${material.getFilePath()}">${material.getFilePath()}</a></p>
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
                                        <c:forEach var="material" items="${video}">
                                            <p><strong>${material.getFileName()}</strong> : <a href="#" onclick="displayMaterial('${material.getFilePath()}'); return false;">
                                                    Video
                                                </a></p>
                                            </c:forEach>
                                    </div>
                                </div>
                            </div>

                        </ul>
                    </nav>
                </div>
                <div class="col-9">
                    <div style="height:100%;overflow-y: scroll;padding:5px; border: 1px solid #ccc;">
                        <div id="pdf-container"></div>
                        <embed id="materialViewer" style="height: 100vh; width: 100%;" src="">
                        <iframe id="materialViewer" width="560" height="315" 
                                frameborder="0" 
                                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                                allowfullscreen></iframe>
                            <c:forEach var="material" items="${materials}">
                            <p><strong>${material.getFileName()}</strong> : <a href="${material.getFilePath()}">${material.getFilePath()}</a></p>
                            </c:forEach>
                    </div>

                </div>
            </div>
        </c:if>


        <script src="https://mozilla.github.io/pdf.js/build/pdf.js"></script>
        <script type="text/javascript">



                                                function displayMaterial(filePath) {
                                                    var iframeElement = document.getElementById('materialViewer');
                                                    iframeElement.src = getLinkVideo(filePath);
                                                }

                                                function getLinkVideo(filePath) {
                                                    // Assuming filePath contains the YouTube video ID
                                                    return filePath;
                                                }


                                                function displayMaterial(filePath) {
                                                    var embedElement = document.getElementById('materialViewer');
                                                    embedElement.src = filePath;
                                                }


                                                function loadPDF(filePath) {
                                                    const pdfUrl = 'pdf/' + filePath;

                                                    // Log the URL to verify it is being passed correctly
                                                    console.log("Loading PDF from URL:", pdfUrl);

                                                    // Get the container element
                                                    const container = document.getElementById('pdf-container');

                                                    // Clear any previously loaded PDF pages
                                                    container.innerHTML = '';

                                                    // Check if pdfUrl is valid
                                                    if (!pdfUrl) {
                                                        console.error("Invalid PDF URL");
                                                        return;
                                                    }

                                                    // Load PDF document
                                                    pdfjsLib.getDocument(pdfUrl).promise.then(pdfDoc => {
                                                        // Loop through all of the pages and load the document
                                                        for (let pageNum = 1; pageNum <= pdfDoc.numPages; pageNum++) {
                                                            pdfDoc.getPage(pageNum).then(page => {
                                                                // Create a viewport to render the PDF page. The higher number you add to the scale, the bigger the PDF file will look.
                                                                const viewport = page.getViewport({scale: 1.5});

                                                                // Prepare the canvas element to render the PDF page
                                                                const canvas = document.createElement('canvas');
                                                                container.appendChild(canvas);

                                                                // Set the canvas dimensions
                                                                canvas.width = viewport.width;
                                                                canvas.height = viewport.height;

                                                                // Render the PDF page on the canvas
                                                                const renderContext = {
                                                                    canvasContext: canvas.getContext('2d'),
                                                                    viewport: viewport
                                                                };
                                                                page.render(renderContext);
                                                            }).catch(err => {
                                                                console.error("Error loading page:", err);
                                                            });
                                                        }
                                                    }).catch(err => {
                                                        console.error("Error loading document:", err);
                                                    });
                                                }
        </script>




        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
