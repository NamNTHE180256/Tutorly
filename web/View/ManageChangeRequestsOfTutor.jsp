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
        <%@ include file = "StudentHeader.jsp" %>
        <div class="container">
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
            <h2>Tutor Request</h2>
            <c:if test="${not empty requests}">
                <table class="table table-bordered table-hover table-striped text-center">
                    <thead>
                        <tr>
                            <th>Tutor</th>
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
                                <td>${request.tutor.name}</td>
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
                                    <form action="manage-tutor-request" method="post">
                                        <c:if test="${request.status eq 'Pending'}">
                                            <input type="hidden" name="action" value="change-status" />
                                            <input type="hidden" name="requestId" value="${request.id}" />
                                            <input type="hidden" name="lid" value="${request.tutor.id}" />
                                            <input type="hidden" name="from_session" value="${request.fromSessionId}" />
                                            <input type="hidden" name="to_session" value="${request.toSessionId}" />
                                            <input type="hidden" name="date" value="${request.date}" />
                                            <button type="submit" name="status" value="Approved" class="btn btn-success">Approve</button>
                                            <button type="submit" name="status" value="Rejected" class="btn btn-danger">Reject</button>
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
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
                                    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
                                    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>


                          
    </body>
</html>
