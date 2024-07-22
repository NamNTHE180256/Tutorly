<%-- 
    Document   : lessonDetails
    Created on : Jun 30, 2024, 1:32:50 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lesson Details</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet"
              integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <style>
            .lesson-details {
                margin-top: 50px;
            }
            .lesson-details h1 {
                margin-bottom: 30px;
            }
            .lesson-details .details-row {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .lesson-details .details-col {
                flex: 1;
            }
            .lesson-details .details-col:first-child {
                margin-right: 20px;
            }
            .lesson-details .btn {
                width: 100%;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }
            .lesson-details .materials,
            .lesson-details .Quiz {
                margin-top: 20px;
            }
            .buttons-row {
                display: flex;
                justify-content: space-between;
                gap: 10px;
                width: 100%;
            }
            .modal-content {
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
            }
            .border-container {
                border: 1px dashed #000;
                padding: 20px;
                margin: 20px auto;
                max-width: 600px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body>
        <header>
            <c:choose>
                <c:when test="${sessionScope.user.role eq 'tutor'}">
                    <jsp:include page="TutorHeader.jsp" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="StudentHeader.jsp" />
                </c:otherwise>
            </c:choose>
        </header>
        <div class="container lesson-details border-container">
            <h1 class="text-center">Lesson Details</h1>
            <div class="row">
                <div class="col-md-12">
                    <div class="details-row">
                        <div class="details-col">
                            <p><strong>Date:</strong> ${requestScope.lesson.getDate()}</p>
                            <p><strong>Slot:</strong> ${requestScope.lesson.getSession().getId()}</p>
                            <p><strong>Class:</strong> ${requestScope.lesson.getAClass().getId()}</p>
                            <p><strong>Status:</strong> ${requestScope.lesson.getStatus()}</p>
                            <p><strong>Meet URL:</strong></p>
                        </div>
                        <div class="details-col text-right">
                            <button id="joinClass" class="btn btn-primary">Enter Class</button>
                        </div>
                    </div>
                    <div class="Quiz mt-3">
                        <p><strong>Quiz:</strong></p>
                        <div class="buttons-row">

                            <c:if test="${sessionScope.user.role eq 'tutor'}">
                                <button class="btn btn-primary" type="button" onclick="location.href = 'View/QuestionList.jsp?lessonId=${requestScope.lesson.getId()}'">View</button>
                                <button class="btn btn-primary" type="button" onclick="location.href = 'View/UploadQuiz.jsp?lessonId=${requestScope.lesson.getId()}'">Upload</button>
                            </c:if>
                            <c:if test="${sessionScope.user.role eq 'learner'}">
                                <button class="btn btn-primary" type="button" onclick="location.href = 'QuizServlet?lessonId=${requestScope.lesson.getId()}'">Take Quiz</button>
                            </c:if>
                        </div>
                    </div>
                    <div class="materials mt-3">
                        <p><strong>Material:</strong></p>
                        <div class="buttons-row">
                            <button class="btn btn-primary" type="button" id="viewButton">View</button>

                            <c:if test="${sessionScope.user.role eq 'tutor'}">
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#upload">Upload</button>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal" id="upload">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Upload Material</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <i style="color: red"> ${requestScope.error}</i>
                        <form action="${pageContext.request.contextPath}/Material" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="classid" value="${requestScope.lesson.getAClass().getId()}">
                            <input type="hidden" name="slotid" value="${requestScope.lesson.getId()}">
                            <input type="hidden" value="upload" name="action">
                            <div class="form-group">
                                <label for="fileName">Name</label>
                                <input type="text" id="fileName" class="form-control" name="fileName" placeholder="Enter a file's name">
                                <label for="file" class="mt-2">Choose File</label>
                                <input type="file" id="file" name="file" class="form-control">
                            </div>
                            <button style="display: block; margin: 0 auto;" type="submit" class="btn btn-primary">Submit</button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                document.getElementById("viewButton").addEventListener("click", function () {
                    window.location.href = "Material?classid=${requestScope.lesson.getAClass().getId()}&slotid=${requestScope.lesson.getId()}&action=getall";
                });
            });
            document.addEventListener("DOMContentLoaded", function () {
                document.getElementById("joinClass").addEventListener("click", function () {
                    window.location.href = "${requestScope.lesson.getAClass().getTutor().getLinkmeet() 
            }";
                });
            });
        </script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
