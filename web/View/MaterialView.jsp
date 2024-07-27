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

        <div class="container-fluid" style="margin-bottom: 20px">
            <div class="row justify-content-end">
                <div class="col-auto">
                    <select id="classDropdown" class="form-select" onchange="onClassChange()">
                        <option value="">Select a class</option>
                        <c:forEach items="${class_vector}" var="c">
                        <option value="${c.getId()}">${c.getTutor().getName()} - ${c.getTutor().getSubject().getName()} - ${c.getStartDate()}</option>
                        </c:forEach>
                    </select>
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
                <div class="col-2" style="margin-left:50px">
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
                                                <p><a href="../Tutorly/MaterialController?service=download&lessonid=${material.getLesson().getId()}&id=${material.getId()}&classid=${requestScope.classid}"><strong>${material.getFileName()}</strong></a></p>
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
                                                <p><a href="../Tutorly/MaterialController?service=download&lessonid=${material.getLesson().getId()}&id=${material.getId()}&classid=${requestScope.classid}"><strong>${material.getFileName()}</strong></a></p>
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
                                            <p>: <a href="../Tutorly/MaterialController?service=downloadVideo&lessonid=${material.getLesson().getId()}&id=${material.getId()}&classid=${requestScope.classid}"" >
                                                    <strong>${material.getFileName()}</strong> 
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
                        <c:if test="${not empty requestScope.fileUrl}">
                            <iframe src="${requestScope.fileUrl}" width="1500" height="600" frameborder="0"></iframe>
                            </c:if>
                            <c:if test="${not empty fileUrlYtb}">
                            <iframe src="${fileUrlYtb}" width="1200" height="600" frameborder="0"></iframe>
                            </c:if>
                    </div>
                </div>
            </div>
        </c:if>

        <script src="https://mozilla.github.io/pdf.js/build/pdf.js"></script>
        <script>
                                                function onClassChange() {
                                                    var classId = document.getElementById('classDropdown').value;
                                                    if (classId) {
                                                        window.location.href = '../Tutorly/MaterialController?service=viewClassMaterial&classid=' + classId;
                                                    }
                                                }
        </script>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
