<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <title>Learner Request</title>
        <style>
            body {
                background-color: #A2A2A2;
                margin-top: 20px;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }
            .table th, .table td {
                vertical-align: middle;
            }
            .alert {
                text-align: center;
            }
            .content {
                flex: 1;
            }
            .footer {
                background-color: #0E3C6E;
                color: white;
                text-align: center;
                padding: 10px 0;
                position: relative;
                width: 100%;
            }
        </style>
    </head>
    <body>
        <header>
            <%@ include file="SearchTutorHeader.jsp" %>
        </header>

        <div class="container-fluid" style="margin-bottom: 20px">
            <div class="row justify-content-end">
                <div class="col-auto">
                    <select id="classDropdown" class="form-select" onchange="onRequestChange()">
                        <option value="#" disabled selected hidden> Select type Request</option>
                        <option value="registerTrial">Register Trial Class</option>

                        <option value="cancelClass"> Cancel Class Request</option>
                    </select>
                </div>
            </div>
        </div>

        <c:if test="${empty requestScope.trial && empty requestScope.cancel}">
            <div class="blankslate">
                <img style="width: 200px" src="image/Click.png" />
                <div class="blankslate-body">
                    <h4>You must choose the type of Request</h4>
                    <p>
                        To view the Request, you will need to choose the type of Request 
                    </p>
                </div>
                <div class="blankslate-actions">
                    <a class="nav-link" href="../Tutorly/DashboardController?type=learner&learnerid=${sessionScope.learner.id}">
                        <button class="btn btn-default" style="background-color: #0E3C6E; color: white;" type="button">Back to Dashboard</button>
                    </a>
                </div>
            </div>
        </c:if>

        <c:if test="${not empty requestScope.trial}">
            <table class="table table-bordered table-hover table-striped text-center">
                <thead>
                    <tr>
                        <th>Learner</th>
                        <th>Subject</th>
                        <th>Total Session</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="trial" items="${requestScope.trial}">
                        <tr>
                            <td>${trial.learner.name}</td>
                            <td>${trial.subject.name}</td>
                            <td>${trial.totalSession}</td>
                            <td><fmt:formatDate value="${trial.startDate}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${trial.endDate}" pattern="yyyy-MM-dd"/></td>
                            <td>${trial.status}</td>
                            <td></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${not empty requestScope.cancel}">
            <table class="table table-bordered table-hover table-striped text-center">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Subject</th>
                        <th>Tutor</th>
                        <th>StartDate</th>
                        <th>EndDate</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cancel" items="${requestScope.cancel}">
                        <tr>
                            <td>${cancel.id}</td>
                            <td>${cancel.AClass.getSubject().getName()}</td>
                            <td>${cancel.AClass.getTutor().getName()}</td>
                            <td>${cancel.AClass.getStartDate()}</td>
                            <td>${cancel.AClass.getEndDate()}</td>
                            <td>${cancel.getStatus()}</td>
                            <td>
                                <c:if test="${cancel.getStatus() != 'accepted' && cancel.getStatus() != 'denied'}">
                                    <form method="post" action="${pageContext.request.contextPath}/CancelClassController">
                                        <input type="hidden" name="cancelId" value="${cancel.id}" />
                                        <button type="submit" class="btn btn-danger">Cancel Request</button>
                                    </form>
                                </c:if></td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <script src="https://mozilla.github.io/pdf.js/build/pdf.js"></script>
        <script>
                        function onRequestChange() {
                            var selectedValue = document.getElementById('classDropdown').value;
                            if (selectedValue) {
                                window.location.href = 'RequestControllerForLearner?learnerid=${sessionScope.learner.id}&requestType=' + selectedValue;
                            }
                        }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>


        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </body>
</html>
