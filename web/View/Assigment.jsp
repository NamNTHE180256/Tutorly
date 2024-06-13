<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .classhomework {
                background-color: white;
                padding: 10px;
                border-radius: 20px;
                margin: 5px;
                margin-bottom: 35px;
                margin-left: 35px;
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
                background-color: white;
                height: 200px;
                border-radius: 20px;
                text-align: center;
            }
            .todohomework h1 {
                font-size: 100px;
                color: #0E3C6E;
            }
            .todohomework p {
                color: #A2A2A2;
            }
        </style>
    </head>
    <body>
        <%@ include file = "StudentHeader.jsp" %>
        <!-- List homework by status -->
        <div class="navbar navbar-expand-sm">
            <ul class="navbar-nav mr-auto completedclass">
                <li class="nav-item">
                    <div class="btn-group">
                        <button class="btn dropdown-toggle choosetutor"
                           type="button"
                           id="dropdownMenuButton" data-toggle="dropdown">
                         Homework status
                        </button>
                        <div class="dropdown-menu">
                           <div class="dropdown-divider"></div>
                           <a class="dropdown-item" href="AssignmentController?service=done">Done</a>
                           <a class="dropdown-item" href="AssignmentController?service=todo">On going</a>
                        </div>
                     </div>
                </li>
            </ul>
        </div>

        <div class="container-fluid content">
            <div class="row">
                <!-- Homework of each class -->
                <div class="col-sm-9 d-flex flex-wrap" style="padding-left: 100px">
                    <c:forEach items="${classAssignments}" var="todo">
                        <div class="col-sm-3 classhomework">
                            <ul>
                                <li><img src="../asset/test_img.png" alt=""></li>
                                <li>
                                    <h2 class="tutorname">${todo.getLession().getAClass().getTutor().getSubject().getName()}</h2>
                                    <p class="subject">${todo.getLession().getAClass().getTutor().getName()}</p>
                                </li>
                            </ul>
                            <hr>
                            <p class="homework"><a href="#">${todo.getFileName()}</a></p>
                            <p>Public date : ${todo.getCreatedAt()}</p>
                            <c:if test="${todo.getStatus() != 'done'}">
                                <p class="btndohomework">Do homework</p>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
                <div class="col-sm-3">
                    <div class="todohomework">
                        <h1>${todoassignment}</h1>
                        <p>homework(s) not completed</p>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    </body>
</html>
