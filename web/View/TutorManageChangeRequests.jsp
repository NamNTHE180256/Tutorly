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
        <title>Tutor Request</title>
        <style>
            body {
                background-color: #A2A2A2;
                margin-top: 20px;
            }
            .table th, .table td {
                vertical-align: middle;
            }
            .alert {
                text-align: center;
            }
            .footer {
                position: relative;
                bottom: 0;
                width: 100%;
                background-color: #343a40;
                color: white;
                text-align: center;
                padding: 10px 0;
                margin-top: 20px;
            }
            .content-wrapper {
                min-height: 100vh;
                padding-bottom: 50px;
                box-sizing: border-box;
            }
        </style>
    </head>
    <body>
        <div class="content-wrapper">
            <c:if test="${tutor != null}">
                <%@ include file="TutorHeader.jsp" %>
            </c:if>
            <c:if test="${learner != null}">
                <%@ include file="StudentHeader.jsp" %>
            </c:if>
            <div class="container mt-5">
                <c:if test="${not empty sessionScope.successMessage}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${sessionScope.successMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <%
                        // Clear the notification after displaying it
                        session.removeAttribute("successMessage");
                    %>
                </c:if>
                <c:if test="${not empty sessionScope.errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${sessionScope.errorMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
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
                                    <td>
                                        ${fn:substring(request.fromSession.startTime, 0, 5)} - ${fn:substring(request.fromSession.endTime, 0, 5)} <br> 
                                        ${request.fromSession.dayOfWeek} <br>
                                        <fmt:formatDate value="${request.date}" pattern="dd/MM/yyyy"/>
                                    </td>
                                    <td>
                                        ${fn:substring(request.toSession.startTime, 0, 5)} - ${fn:substring(request.toSession.endTime, 0, 5)} <br> 
                                        ${request.toSession.dayOfWeek}
                                    </td>
                                    <td>${request.reason}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${request.status eq 'Pending'}">
                                                <span class="text-warning">${request.status}</span>
                                            </c:when>
                                            <c:when test="${request.status eq 'Approved'}">
                                                <span class="text-success">${request.status}</span>
                                            </c:when>
                                            <c:when test="${request.status eq 'Rejected'}">
                                                <span class="text-danger">${request.status}</span>
                                            </c:when>
                                            <c:when test="${request.status eq 'Cancel'}">
                                                <span class="text-secondary">${request.status}</span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td style="display: flex; gap: 5px">
                                        <c:if test="${request.status eq 'Pending'}">
                                            <form action="tutor-requests" method="post">
                                                <input type="hidden" name="action" value="change-status" />
                                                <input type="hidden" name="requestId" value="${request.id}" />
                                                <input type="hidden" name="lid" value="${request.learner.id}" />
                                                <input type="hidden" name="from_session" value="${request.fromSessionId}" />
                                                <input type="hidden" name="to_session" value="${request.toSessionId}" />
                                                <input type="hidden" name="date" value="${request.date}" />
                                                <button type="submit" name="status" value="Cancel" class="btn btn-warning">Cancel</button>
                                            </form>
                                            <form action="tutor-requests" method="post">
                                                <input type="hidden" name="action" value="delete" />
                                                <input type="hidden" name="requestId" value="${request.id}" />
                                                <button type="submit" class="btn btn-danger">Delete</button>
                                            </form>
                                        </c:if>
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
        </div>
        <div class="footer">
            <%@ include file="tutor-footer.jsp" %>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
