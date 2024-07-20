<%-- 
    Document   : TutorChangeSession
    Created on : Jul 18, 2024, 1:23:46 AM
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
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">

        <title>Change Session</title>
        <style>
            body {
                background-color: #A2A2A2;
                margin-top: 20px;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }
            .container {
                flex: 1;
            }
            .footer {
                background-color: #0E3C6E;
                color: white;
                padding: 20px 0;
                text-align: center;
                position: relative;
                bottom: 0;
                width: 100%;
            }
            .footer a {
                color: #FFC107;
            }
            .alert {
                text-align: center;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#learnerId').change(function () {
                    var learnerId = $(this).val();
                    $.ajax({
                        url: 'get-learner-sessions',
                        type: 'GET',
                        data: {learnerId: learnerId},
                        success: function (data) {
                            var fromSessionId = $('#fromSessionId');
                            fromSessionId.empty();
                            $.each(data, function (index, session) {
                                fromSessionId.append('<option value="' + session.id + '">' + session.startTime + ' - ' + session.endTime + '</option>');
                            });
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <c:if test="${tutor != null}">
            <%@ include file="TutorHeader.jsp" %>
        </c:if>
        <c:if test="${learner != null}">
            <%@ include file="StudentHeader.jsp" %>
        </c:if>
        <div class="container">
            <h2>Change Session</h2>
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${sessionScope.successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">X</button>
                </div>
                <%
                    // Clear the notification after displaying it
                    session.removeAttribute("successMessage");
                %>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${sessionScope.errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">X</button>
                </div>
                <%
                    // Clear the notification after displaying it
                    session.removeAttribute("errorMessage");
                %>
            </c:if>

            <form action="tutor-change-session" method="post">
                <div class="form-group">
                    <input type="hidden" name="action" value="null">
                    <label for="learnerId">Learner</label>
                    <select class="form-control" id="learnerId" name="learnerId" onchange="this.form.submit()">
                        <option value="">Select Learner</option>
                        <c:forEach var="dto" items="${learnerSessionsDTOs}">
                            <option value="${dto.learner.id}" ${dto.learner.id == selectedLearnerId ? 'selected' : ''}>${dto.learner.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </form>
            <form id="changeSessionForm" action="tutor-change-session" method="post" onsubmit="return validateForm()">
                <div class="form-group">
                    <label for="fromSessionId">Current Session</label>
                    <select class="form-control" id="fromSessionId" name="fromSessionId">
                        <option value="">Select a session</option>
                        <c:forEach var="dto" items="${learnerSessionsDTOs}">
                            <c:forEach var="session" items="${dto.sessions}">
                                <option value="${session.id}/${session.date}">${fn:substring(session.startTime, 0, 5)} - ${fn:substring(session.endTime, 0, 5)} - ${session.dayOfWeek} - <fmt:formatDate value="${session.date}" pattern="dd/MM/yyyy"/></option>
                            </c:forEach>
                        </c:forEach>
                    </select>
                    <span id="fromSessionError" class="text-danger" style="display:none;">Please select a session</span>
                </div>
                <div class="form-group">
                    <label for="toSessionId">New Session</label>
                    <select class="form-control" id="toSessionId" name="toSessionId">
                        <option value="">Select a session</option>
                        <c:forEach var="session" items="${allSessions}">
                            <option value="${session.id}">${fn:substring(session.startTime, 0, 5)} - ${fn:substring(session.endTime, 0, 5)} - ${session.dayOfWeek}</option>
                        </c:forEach>
                    </select>
                    <span id="toSessionError" class="text-danger" style="display:none;">Please select a session</span>
                </div>
                <div class="form-group">
                    <label for="reason">Reason</label>
                    <textarea class="form-control" id="reason" required="" name="reason" rows="3"></textarea>
                </div>
                <input type="hidden" name="action" value="change">
                <input type="hidden" name="selectedLearnerId" value="${selectedLearnerId}">
                <button type="submit" class="btn btn-primary">Submit request</button>
            </form>
        </div>
        <%@ include file = "tutor-footer.jsp" %>
        <script>
            function validateForm() {
                let isValid = true;

                // Validate From Session
                const fromSessionId = document.getElementById('fromSessionId').value;
                const fromSessionError = document.getElementById('fromSessionError');
                if (fromSessionId === "") {
                    fromSessionError.style.display = 'block';
                    isValid = false;
                } else {
                    fromSessionError.style.display = 'none';
                }

                // Validate To Session
                const toSessionId = document.getElementById('toSessionId').value;
                const toSessionError = document.getElementById('toSessionError');
                if (toSessionId === "") {
                    toSessionError.style.display = 'block';
                    isValid = false;
                } else {
                    toSessionError.style.display = 'none';
                }

                return isValid;
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</html>
