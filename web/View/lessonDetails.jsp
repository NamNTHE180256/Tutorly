<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lesson Details</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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
            #quizModal .modal-content {
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
            }
            #questionsTable {
                width: 100%;
                border-collapse: collapse;
            }
            #questionsTable th, #questionsTable td {
                border: 1px solid #ddd;
                padding: 8px;
            }
            #questionsTable th {
                padding-top: 12px;
                padding-bottom: 12px;
                text-align: left;
                background-color: #f2f2f2;
                color: black;
            }
            #questionsTable td button {
                width: auto;
            }
        </style>
    </head>
    <body>
        <header>
            <c:choose>
                <c:when test="${sessionScope.user.role eq 'tutor'}">
                    <jsp:include page="TutorHeader.jsp"/>
                </c:when>
                <c:otherwise>
                    <jsp:include page="StudentHeader.jsp"/>
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
                                <button id="viewQuizButton" class="btn btn-primary" type="button" data-toggle="modal" data-target="#quizModal">View</button>
                                <button class="btn btn-primary" type="button" onclick="location.href = 'View/UploadQuiz.jsp?lessonId=${requestScope.lesson.getId()}&classId=${requestScope.classId}'">Upload</button>
                            </c:if>
                            <c:if test="${sessionScope.user.role eq 'learner'}">
                                <button class="btn btn-primary" type="button" onclick="takeQuiz(${requestScope.lesson.getId()}, ${requestScope.classId})">Take Quiz</button>
                            </c:if>
                        </div>
                    </div>

                    <c:if test="${sessionScope.user.role eq 'tutor'}">
                        <div class="materials mt-3">
                            <p><strong>Material:</strong></p>
                            <div class="buttons-row">
                            </div>
                        </div>
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#upload">Upload</button>
                    </c:if>



                    <c:if test="${sessionScope.user.role eq 'tutor'}">
                        <div class="assignment mt-3">
                            <p><strong>Records</strong></p>
                            <div class="buttons-row">
                            </div>
                        </div>
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#uploadRecords">Upload</button>
                    </c:if>

                    <div class="materials mt-3">
                        <p><strong>Material And Records:</strong></p>
                        <div class="buttons-row">
                            <button class="btn btn-primary" type="button" id="viewButton">View</button>


                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Quiz Modal -->
        <div class="modal" id="quizModal">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Uploaded Questions</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <table id="questionsTable" class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Number</th>
                                    <th>Question</th>
                                    <th>Answer A</th>
                                    <th>Answer B</th>
                                    <th>Answer C</th>
                                    <th>Answer D</th>
                                    <th>Correct Answer</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- Questions will be loaded here via AJAX -->
                            </tbody>
                        </table>
                        <div class="form-group">
                            <label for="quizTime">Quiz Time (minutes):</label>
                            <input type="number" class="form-control" id="quizTime">
                        </div>
                        <div class="text-right">
                            <button class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <button class="btn btn-primary" id="saveQuizButton">Save</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Upload Material Modal -->
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
<<<<<<< HEAD
=======
        <div  class="modal" id="uploadRecords">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Upload Records</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <i style="color: red"> ${requestScope.error}</i>
                        <form action="${pageContext.request.contextPath}/addVideoControllers" method="get" enctype="multipart/form-data">
                            <input type="hidden" name="classid" value="${requestScope.lesson.getAClass().getId()}">
                            <input type="hidden" name="slotid" value="${requestScope.lesson.getId()}">
                            <input type="hidden" value="upload" name="action">
                            <div class="form-group">
                                <label for="fileName">Name</label>
                                <input type="text" id="fileName" class="form-control" name="fileName" placeholder="Enter a file's name">
                                <label for="file" class="mt-2">Choose File</label>
                                <input type="text" name="linkYtb" id="file" name="file" class="form-control">
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
                    window.location.href = "${requestScope.lesson.getAClass().getTutor().getLinkmeet()}";
                });
            });
>>>>>>> 6bba887d95ba536aff8fb3d5cd7f91eb00c8350a

        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
                                    document.addEventListener("DOMContentLoaded", function () {
                                        // Sự kiện khi bấm nút View để xem tài liệu
                                        document.getElementById("viewButton").addEventListener("click", function () {
                                            window.location.href = "Material?classid=${requestScope.lesson.getAClass().getId()}&slotid=${requestScope.lesson.getId()}&action=getall";
                                        });

                                        // Sự kiện khi bấm nút "Enter Class" để tham gia lớp học
                                        document.getElementById("joinClass").addEventListener("click", function () {
                                            window.location.href = "${requestScope.lesson.getAClass().getTutor().getLinkmeet()}";
                                        });

                                        // Sự kiện khi bấm nút "View" để hiển thị quiz
                                        document.getElementById("viewQuizButton").addEventListener("click", function () {
                                            var lessonId = ${requestScope.lesson.getId()};
                                            var classId = ${requestScope.lesson.getAClass().getId()};

                                            // Gửi yêu cầu AJAX đến servlet để lấy dữ liệu quiz
                                            $.ajax({
                                                url: 'QuizAction',
                                                method: 'GET',
                                                data: {lessonId: lessonId, classId: classId, action: 'getQuestions'},
                                                success: function (response) {
                                                    console.log(response); // Log the response to inspect its structure
                                                    var tableBody = $('#questionsTable tbody');
                                                    tableBody.empty();
                                                    response.questions.forEach(function (question) {
                                                        var row = `<tr>
                                   <td>${question.number}</td>
                                   <td>${question.questionText}</td>
                                   <td>${question.answerA}</td>
                                   <td>${question.answerB}</td>
                                   <td>${question.answerC}</td>
                                   <td>${question.answerD}</td>
                                   <td>${question.correctAnswer}</td>
                                   <td>
                                       <button class="btn btn-warning btn-sm edit-question" data-id="${question.quizId}">Edit</button>
                                       <button class="btn btn-danger btn-sm delete-question" data-id="${question.quizId}">Delete</button>
                                   </td>
                               </tr>`;
                                                        tableBody.append(row);
                                                    });
                                                    $('#quizTime').val(response.quizTime);
                                                    $('#quizModal').modal('show'); // Hiển thị modal sau khi cập nhật dữ liệu
                                                },
                                                error: function (xhr, status, error) {
                                                    console.error("AJAX error:", status, error);
                                                }
                                            });
                                        });

                                        // Sự kiện khi bấm nút "Save" để lưu quiz
                                        $('#saveQuizButton').on('click', function () {
                                            var quizTime = $('#quizTime').val();
                                            var questions = [];
                                            $('#questionsTable tbody tr').each(function () {
                                                var question = {
                                                    quizId: $(this).find('.edit-question').data('id'),
                                                    number: $(this).find('td').eq(0).text(),
                                                    questionText: $(this).find('td').eq(1).text(),
                                                    answerA: $(this).find('td').eq(2).text(),
                                                    answerB: $(this).find('td').eq(3).text(),
                                                    answerC: $(this).find('td').eq(4).text(),
                                                    answerD: $(this).find('td').eq(5).text(),
                                                    correctAnswer: $(this).find('td').eq(6).text()
                                                };
                                                questions.push(question);
                                            });
                                            $.ajax({
                                                url: 'QuizAction',
                                                method: 'POST',
                                                data: {
                                                    action: 'saveQuiz',
                                                    quizTime: quizTime,
                                                    questions: JSON.stringify(questions)
                                                },
                                                success: function () {
                                                    $('#quizModal').modal('hide');
                                                    location.reload();
                                                }
                                            });
                                        });
                                    });

                                    function takeQuiz(lessonId, classId) {
                                        window.location.href = "QuizServlet?lessonId=" + lessonId + "&classId=" + classId + "&action=takeQuiz";
                                    }
        </script>
    </body>
</html>
