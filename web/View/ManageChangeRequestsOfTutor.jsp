
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            body {
                background: #D9D9D9;
                margin-top: 20px;
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
                margin-right: .5rem !important;
            }
            .text-secondary {
                display: inline-flex;
            }
            .text-secondary input {
                margin: 5px;
            }
            .text-secondary i {
                padding-top: 15px;
            }
        </style>
        <title>Manage Change Requests</title>
    </head>
    <body>
        <header>
            <%@ include file="TutorHeader.jsp" %>
        </header>

        <div class="container-fluid" style="margin-bottom: 20px">
            <div class="row justify-content-end">
                <div class="col-auto">
                    <select id="classDropdown" class="form-select" onchange="onRequestChange()">
                        <option value="#" disabled selected hidden>Select type Request</option>
                        <option value="registerTrial">Register Trial Class</option>
                        <option value="cancelClass">Cancel Class Request</option>
                    </select>
                </div>
            </div>
        </div>

        <c:if test="${not empty requestScope.error}">
            <div class="alert alert-danger" role="alert" id="errorAlert">
                <div class="d-flex gap-4">
                    <span><i class="fa-solid fa-circle-check icon-error"></i></span>
                    <div>
                        ${requestScope.error}
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${empty requestScope.trial && empty requestScope.cancel}">
            <div class="blankslate">
                <img style="width: 200px" src="image/Click.png" />
                <div class="blankslate-body">
                    <h4>You must choose the type of Request</h4>
                    <p>To view the Request, you will need to choose the type of Request</p>
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
                            <td><fmt:formatDate value="${trial.startDate}" pattern="yyyy-MM-dd" /></td>
                            <td><fmt:formatDate value="${trial.endDate}" pattern="yyyy-MM-dd" /></td>
                            <td>${trial.status}</td>
                            <td>
                                <c:if test="${trial.status != 'denied' && trial.status != 'accepted'}">
                                    <button type="button" class="btn btn-success" onclick="agreeRequest(${trial.id}, ${trial.learner.id}, '${trial.session.id}', '${trial.startDate}')">Approve</button>
                                    <button type="button" class="btn btn-danger" onclick="denyRequest(${trial.id}, ${trial.learner.id})">Deny</button>
                                </c:if>
                            </td>
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
                            <td>${cancel.AClass.subject.name}</td>
                            <td>${cancel.AClass.learner.name}</td>
                            <td><fmt:formatDate value="${cancel.AClass.startDate}" pattern="yyyy-MM-dd" /></td>
                            <td><fmt:formatDate value="${cancel.AClass.endDate}" pattern="yyyy-MM-dd" /></td>
                            <td>${cancel.status}</td>
                            <td>
                            <c:if test="${cancel.status == 'wait'}">
    <button type="button" class="btn btn-success" onclick="sendRequest('approve', ${cancel.AClass.tutor.id}, ${cancel.id}, '${cancel.AClass.startDate}')">Approve</button>
    <button type="button" class="btn btn-danger" onclick="sendRequest('deny', ${cancel.AClass.tutor.id}, ${cancel.id}, '${cancel.AClass.startDate}')">Deny</button>
</c:if>

                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <script>
            function sendRequest(action, tutorId, cancelId, startDate) {
                window.location.href = 'TutorResponseCancelRequest?action=' + action + '&tutorId=' + tutorId + '&cancelId=' + cancelId + '&startDate=' + encodeURIComponent(startDate);
            }

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
                var tutorId = '${sessionScope.tutor.id}'; // ??m b?o r?ng sessionScope.tutor.id ???c ??nh ngh?a và có giá tr?
                var selectedValue = true;

                if (selectedValue) {
                    window.location.href = 'TutorResponseTrialClassController?tutor_id=' + tutorId + '&service=accept&responseid=' + trialId + '&learner_id=' + learnerId + '&session_id=' + sessionId + '&date=' + startDate;
                }
            }

            function denyRequest(trialId, learnerId) {
                console.log('denyRequest called');
                console.log('trialId:', trialId, 'learnerId:', learnerId);

                var tutorId = '${sessionScope.tutor.id}'; // ??m b?o r?ng sessionScope.tutor.id ???c ??nh ngh?a và có giá tr?
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
