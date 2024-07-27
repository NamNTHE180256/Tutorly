<%-- 
    Document   : ManageChangeRequestsOfTutor
    Created on : Jul 18, 2024, 1:05:07 AM
    Author     : Tung Duong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src='https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js'></script>
        <style>
            body{
                background: #D9D9D9;
                margin-top:20px;
            }
            .card {
                position: relative;
                display: flex;
                flex-direction: column;
                min-width: 0;
                word-wrap: break-word;
                background-color: #fff;
                background-clip: border-box;
                border: 0 solid transparent;
                border-radius: .25rem;
                margin-bottom: 1.5rem;
                box-shadow: 0 2px 6px 0 rgb(218 218 253 / 65%), 0 2px 6px 0 rgb(206 206 238 / 54%);
            }
            .me-2 {
                margin-right: .5rem!important;
            }
            .text-secondary{
                display: inline-flex;
            }
            .text-secondary input{
                margin: 5px;
            }
            .text-secondary i{
                padding-top: 15px;
            }
        </style>
        <title>Profile</title>
    </head>
    <body>
        <header>
            <c:choose>
                <c:when test="${user.role eq 'tutor'}">
                    <%@ include file="TutorHeader.jsp" %>
                </c:when>
                <c:otherwise>
                    <%@ include file="SearchTutorHeader.jsp" %>
                </c:otherwise>
            </c:choose>
        </header>

        <div class="container-fluid" style="margin-bottom: 20px">
            <div class="row justify-content-end">
                <div class="col-auto">
                    <select id="classDropdown" class="form-select" onchange="onRequestChange()">
                        <option value="#"  disabled selected hidden >Select type Request</option>
                        <option value="registerTrial">Register Trial Class</option>
                        <option value="sessionChange">Sessions Change Request</option>
                        <option value="cancelClass">Cancel Class Request</option>
                    </select>
                </div>
            </div>
        </div>

        <c:if test="${empty requestScope.trial}">
            <div class="blankslate">
                <img style="width: 200px" src="image/Click.png" />
                <div class="blankslate-body">
                    <h4>You must choose the type of Request</h4>
                    <p>To view the Request, you will need to choose the type of Request</p>
                </div>
                <div class="blankslate-actions">
                    <a class="nav-link" href="DashboardController?type=tutor&tutorid=${sessionScope.tutor.id}"><button class="btn btn-default" style="background-color: #0E3C6E; color: white;" type="button">Back to Dashboard</button></a>
                </div>
            </div>
        </c:if>

        <c:if test="${not empty requestScope.trial}">
            <table class="table table-bordered table-hover table-striped text-center">
                <thead>
                    <tr>
                        <th>id</th>
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
                            <th>${trial.id}</th>
                            <td>${trial.learner.name}</td>
                            <td>${trial.subject.name}</td>
                            <td>${trial.totalSession}</td>
                            <td><fmt:formatDate value="${trial.startDate}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${trial.endDate}" pattern="yyyy-MM-dd"/></td>
                            <td>${trial.status}</td>
                            <td>
                                <c:if test="${trial.status != 'denied' && trial.status != 'accepted'}">
                                    <button type="button" class="btn btn-default active" onclick="agreeRequest(${trial.id}, ${trial.learner.id}, '${trial.session.id}', '${trial.startDate}')">Approve</button>
                                    <button type="button" class="btn btn-danger" onclick="denyRequest(${trial.id}, ${trial.learner.id})">Deny</button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    JavaScript:
                </tbody>
            </table>
        </c:if>

        <script src="https://mozilla.github.io/pdf.js/build/pdf.js"></script>
        <script>
                                        function onRequestChange() {
                                            var selectedValue = document.getElementById('classDropdown').value;
                                            if (selectedValue) {
                                                window.location.href = 'RequestControllersForTutor?tutorid=${sessionScope.tutor.id}&requestType=' + selectedValue;
                                            }
                                        }

                                        function agreeRequest(trialId, learnerId, sessionId, startDate) {
                                            var trialid = trialId;
                                            var learnerid = learnerId;
                                            var sessionId = sessionId;
                                            var date = startDate;
                                            var tutorId = '${sessionScope.tutor.id}'; // Đảm bảo rằng sessionScope.tutor.id được định nghĩa và có giá trị
                                            var selectedValue = true;

                                            if (selectedValue) {
                                                window.location.href = 'TutorResponseTrialClassController?tutor_id=' + tutorId + '&service=accept&responseid=' + trialId + '&learner_id=' + learnerId + '&session_id=' + sessionId + '&date=' + startDate;
                                            }
                                        }

                                        function denyRequest(trialId, learnerId) {
                                            console.log('denyRequest called');
                                            console.log('trialId:', trialId, 'learnerId:', learnerId);

                                            var tutorId = '${sessionScope.tutor.id}'; // Đảm bảo rằng sessionScope.tutor.id được định nghĩa và có giá trị
                                            var selectedValue = true;

                                            if (selectedValue) {
                                                window.location.href = 'TutorResponseTrialClassController?tutorid=' + tutorId + '&service=deny&responseid=' + trialId + '&learnerid=' + learnerId;
                                            }
                                        }

        </script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
