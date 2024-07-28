<%-- 
    Document   : RequestChangeSession
    Created on : Jul 18, 2024, 1:09:25 AM
    Author     : Tung Duong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    // Get the current page name from the request URL
    String currentPage = request.getRequestURI() + "?" + request.getQueryString();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
      
        <title>Tutor Request</title>
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
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#tutorId').change(function () {
                    var tutorId = $(this).val();
                    $.ajax({
                        url: 'get-tutor-sessions',
                        type: 'GET',
                        data: {tutorId: tutorId},
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
        <title>Change Session</title>
    </head>
    <body>
        <%@ include file="StudentHeader.jsp" %>
        <div class="container">
            <h2>Change Session Request</h2>
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center">
                    ${sessionScope.successMessage}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <%
                    // Clear the notification after displaying it
                    session.removeAttribute("successMessage");
                %>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert" style="text-align: center">
                    ${sessionScope.errorMessage}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <%
                    // Clear the notification after displaying it
                    session.removeAttribute("errorMessage");
                %>
            </c:if>

            <form action="submit-change-session-request" method="post">
                <div class="form-group">
                    <label for="tutorId">Tutor</label>
                    <select class="form-control" id="tutorId" name="tutorId">
                        <option value="">Select Tutor</option>
                        <c:forEach var="tutor" items="${tutors}">
                            <option value="${tutor.id}">${tutor.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <br>
                <div class="form-group">
                    <label for="fromSessionId">Current session</label>
                    <select class="form-control" id="fromSessionId" name="fromSessionId">
                        <option value="">Select a session</option>
                        <c:forEach var="session" items="${sessions}">
                            <option value="${session.id}/${session.date}">${fn:substring(session.startTime, 0, 5)} - ${fn:substring(session.endTime, 0, 5)} - ${session.dayOfWeek} - ${session.date}</option>
                        </c:forEach>
                    </select>
                </div>
                <br>
                <div class="form-group">
                    <label for="toSessionId">New session</label>
                    <select class="form-control" id="toSessionId" name="toSessionId">
                        <option value="">Select a session</option>
                        <c:forEach var="session" items="${availableSessions}">
                            <option value="${session.id}">${fn:substring(session.startTime, 0, 5)} - ${fn:substring(session.endTime, 0, 5)} - ${session.dayOfWeek} </option>
                        </c:forEach>
                    </select>
                </div>
                <br>
                <div class="form-group">
                    <label for="reason">Reason</label>
                    <textarea class="form-control" id="reason" required="" name="reason" rows="3"></textarea>
                </div>
                <br>
                <button type="submit" class="btn btn-primary">Submit Request</button>
            </form>
        </div>
       
    </body>
</html>
