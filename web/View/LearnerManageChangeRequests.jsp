<%-- 
    Document   : LearnerManageChangeRequests
    Created on : Jul 18, 2024, 1:01:04 AM
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
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
        <title>Learner Manage Request</title>
        <style>
            body {
                background-color: #A2A2A2;
                margin-top: 20px;
                background: #eee;
            }
            .leftconttent {
                padding: 10px;
                height: 100%;
            }
            .leftconttent .nav-item {
                width: 100px;
            }
            .rating {
                display: inline;
                align-items: center;
                text-align: center;
            }
            .fee {
                display: inline-flex;
            }
            .buttons {
                flex-direction: column;
                align-items: flex-end;
            }
            .btn-trial, .btn-register {
                background-color: #0E3C6E;
                color: white;
                padding: 10px 20px;
                border-radius: 5px;
                margin-top: 10px;
                text-align: center;
            }
            .btn-register {
                background-color: #6c757d;
            }
            .tutorimg {
                width: 200px;
                height: 200px;
                margin-right: 20px;
            }
            .tutor-name {
                color: #0E3C6E;
                font-size: 24px;
                font-weight: bold;
            }
            .subject, .students {
                display: flex;
                align-items: center;
            }
            .subject i, .students i {
                font-size: 20px;
            }
            .subject h3, .students h3 {
                font-size: 20px;
                margin: 0;
            }
            .bg-light-gray {
                background-color: #f7f7f7;
            }
            .table-bordered thead td, .table-bordered thead th {
                border-bottom-width: 2px;
            }
            .table thead th {
                vertical-align: bottom;
                border-bottom: 2px solid #dee2e6;
            }
            .table-bordered td, .table-bordered th {
                border: 1px solid #dee2e6;
            }
            .bg-sky.box-shadow {
                box-shadow: 0px 5px 0px 0px #0E3C6E;
            }
            .bg-sky {
                background-color: #0E3C6E;
            }
            .padding-15px-lr {
                padding-left: 15px;
                padding-right: 15px;
            }
            .padding-5px-tb {
                padding-top: 5px;
                padding-bottom: 5px;
            }
            .margin-10px-bottom {
                margin-bottom: 10px;
            }
            .border-radius-5 {
                border-radius: 5px;
            }
            .margin-10px-top {
                margin-top: 10px;
            }
            .font-size14 {
                font-size: 14px;
            }
            .text-light-gray {
                color: #d6d5d5;
            }
            .font-size13 {
                font-size: 13px;
            }
            .finished {
                color: white;
                background-color: #0E3C6E;
                padding: 5px;
                border-radius: 5px;
            }
            .scheduled {
                color: black;
                background-color: #FFC107;
                padding: 5px;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <c:if test="${tutor != null}">
            <%@ include file="TutorHeader.jsp" %>
        </c:if>
        <c:if test="${learner != null}">
            <%@ include file="StudentHeader.jsp" %>
        </c:if>
        <div class="container">

            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center">
                    ${sessionScope.successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">X</button>
                </div>
                <%
                    // Clear the notification after displaying it
                    session.removeAttribute("successMessage");
                %>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert" style="text-align: center">
                    ${sessionScope.errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">X</button>
                </div>
                <%
                    // Clear the notification after displaying it
                    session.removeAttribute("errorMessage");
                %>
            </c:if>

            <h2>Learner Manage Request</h2>
            <c:if test="${not empty requests}">
                <table class="table table-bordered table-hover table-striped text-center">
                    <thead>
                        <tr>
                            <th>Learner</th>
                            <th>Current Session</th>
                            <th>New Session</th>
                            <th>Reason</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="request" items="${requests}">
                            <tr>
                                <td>${request.learner.name}</td>
                                <td>${fn:substring(request.fromSession.startTime, 0, 5)} - ${fn:substring(request.fromSession.endTime, 0, 5)} <br> ${request.fromSession.dayOfWeek} <br><fmt:formatDate value="${request.date}" pattern="dd/MM/yyyy"/></td>
                                <td>${fn:substring(request.toSession.startTime, 0, 5)} - ${fn:substring(request.toSession.endTime, 0, 5)} <br> ${request.toSession.dayOfWeek}</td>
                                <td>${request.reason}</td>
                                <td>
                                    <c:if test="${request.status eq 'Pending'}">
                                        <span style="color: orange">${request.status}</span>
                                    </c:if>
                                    <c:if test="${request.status eq 'Approved'}">
                                        <span style="color: green">${request.status}</span>
                                    </c:if>
                                    <c:if test="${request.status eq 'Rejected'}">
                                        <span style="color: red">${request.status}</span>
                                    </c:if>
                                    <c:if test="${request.status eq 'Cancel'}">
                                        <span style="color: red">${request.status}</span>
                                    </c:if>

                                </td>
                                <td>
                                    <form action="learner-change-requests" method="post">
                                        <c:if test="${request.status eq 'Pending'}">
                                            <input type="hidden" name="action" value="change-status" />
                                            <input type="hidden" name="requestId" value="${request.id}" />
                                            <button type="submit" name="status" value="Cancel" class="btn btn-danger">Cancel</button>
                                        </c:if>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty requests}">
                <p>Không có yêu cầu đổi buổi học nào.</p>
            </c:if>
        </div>
        <%@ include file = "tutor-footer.jsp" %>
         <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
