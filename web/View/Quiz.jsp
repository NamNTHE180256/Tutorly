<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            .classhomework {
                background-color: white;
                padding: 10px;


            }
            .classhomework img {
                height: 115px;
            }
            .classhomework li {
                display: inline-block;
            }
            .tutorname {
                color: #0E3C6E;
            }
            .subject {
                color: #A2A2A2;
            }
            .btndohomework {
                background-color: #0E3C6E;
                width: fit-content;
                color: white;
                border-radius: 5px;
                padding: 5px;
                margin-left: auto;
            }
            .todohomework {
                margin-top: 17px;
                background-color: white;
                height: 200px;
                border-radius: 5px;
                text-align: center;
            }
            .todohomework h1 {
                font-size: 100px;
                color: #F7B500;
            }
            .todohomework p {
                color: #A2A2A2;
            }
        </style>
    </head>
    <body>

        <%@ include file = "StudentHeader.jsp" %>
        <!-- List homework by status -->

        <div class="container-fluid content">

            <nav class="navbar navbar-expand-lg" style="background-color: #e6e6e6">
                <div class="container-fluid">
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                            data-bs-target="#navbarExample" aria-controls="navbarExample" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarExample">
                        <ul class="navbar-nav me-auto mb-0">

                        </ul>
                        <div class="d-flex align-items-center flex-column flex-lg-row">
                            <div class="dropdown dropdown-hover">
                                <a style="background-color: #0E3C6E; color: white;" class="btn btn-default dropdown-toggle" href="#" role="button">
                                    Homework Status
                                </a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a class="dropdown-item" href="QuizController?service=done">Done</a></li>
                                    <li><a class="dropdown-item" href="QuizController?service=todo">On going</a></li>

                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>

            <div class="row">
                <!-- Homework of each class -->
                <div class="col-sm-9 d-flex flex-wrap" >
                    <div class="container mt-5">
                        <div class="row">
                            <c:forEach items="${classQuiz}" var="todo">
                                <div class="col-md-4" style="border-radius: 15px; margin-bottom: 15px ">
                                    <div class="card p-3">
                                        <ul style="">

                                            <h2 class="tutorname">${todo.getLessonbyId().getAClass().getTutor().getSubject().getName()}</h2>
                                            <p class="subject">${todo.getLessonbyId().getAClass().getTutor().getName()}</p>

                                        </ul>
                                        <hr>
                                        <p class="homework"><a href="#">${todo.getFileName()}</a></p>
                                        <p>Public date : ${todo.getCreatedAt()}</p>
                                        <c:if test="${todo.getStatus() == 'done'}">
                                            <button type="button" class="btn btn-danger">Results: ${todo.getScore()}</button>
                                            
                                        </c:if>
                                        
                                        <c:if test="${todo.getStatus() != 'done'}">
                                            <button type="button" class="btn btn-outline-warning">Do homework</button>
                                           
                                        </c:if>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="todohomework">
                        <h1>${todoQuiz}</h1>
                        <p>Homework(s) not completed</p>
                    </div>
                </div>
            </div>
        </div>
        
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
     
            
        
    </body>
</html>
