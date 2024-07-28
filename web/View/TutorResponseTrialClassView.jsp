<%-- 
    Document   : TutorResponseTrialClassView
    Created on : Jul 16, 2024, 6:56:53 PM
    Author     : TRANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src='js/newjavascript.js'></script>
        <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/core@5.10.1/main.min.js'></script>
        <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@fullcalendar/core@5.10.1/main.min.css' />
        <title>JSP Page</title>
    </head>
    <body>
        <div class="table-responsive">
            <table class="table table-hover table-borderless">
                <thead>
                    <tr>
                        <th>Learner Name</th>
                        <th>Date</th>
                        <th>Session</th>
                        <th>Class Type</th>
                        <th>Status</th>
                        <th>Response</th>
                    </tr>
                </thead>
                <tbody class="table-group-divider">

                    <c:forEach items="${requestScope.trial}" var="li">

                    <td>
                        <span class="avatar"><i class="fas fa-user"></i></span>
                        <a href="#">${li.learner.name}</a>
                    </td>
                    <td>${li.startDate}</td>
                    <td>${fn:substring(li.session.startTime, 0, 5)}-${fn:substring(li.session.endTime, 0, 5)}</td>
                    <td>Trial Class</td>
                    <td>
                        <c:choose>
                            <c:when test="${li.status == 'wait'}">
                                <span style="color: #F7B500;">${li.status}</span>
                            </c:when>
                            <c:when test="${li.status == 'accepted'}">
                                <span style="color: #1c7430;">${li.status}</span>
                            </c:when>
                            <c:when test="${li.status == 'denied'}">
                                <span style="color: #bd2130;">${li.status}</span>
                            </c:when>
                            <c:otherwise>
                                <span>${li.status}</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <c:if test="${li.status != 'accepted' && li.status != 'denied'}">
                        <td>
                            <a href="TutorResponseTrialClassController?responseid=${li.id}&service=accept&date=${li.startDate}&learner_id=${li.learner.id}&session_id=${li.session.id}&tutor_id=${li.tutor.id}"><button type="button" class="btn btn-outline-success">Accept</button></a>
                            <a href="TutorResponseTrialClassController?service=deny&responseid=${li.id}"><button type="button" class="btn btn-outline-danger">Deny</button></a>
                        </td>
                    </c:if>
                    </tr>
                </c:forEach>




                </tbody>
            </table>
        </div>
    
    </body>
</html>
