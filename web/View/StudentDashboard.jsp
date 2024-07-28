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
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <title>Class</title>
    </head>
    <body>
        <%@ include file="StudentHeader.jsp" %>
        <main >
            <!--Content-->
            <div class="container-fluid content">
               
                <div class="col-sm-12 d-flex flex-column">



                    <div class="upcommingclass flex-grow-1">
                        
                        <div class="upcommingclassdetails">
                            <div class="container">
                                <div class="table-responsive">
                                    <table class="table table-hover table-borderless">
                                        <thead>
                                            <tr>
                                                <th>Name Class</th>
                                                <th>Date</th>
                                                <th>Time</th>
                                                <th>Total Sessions</th>
                                                <th>In Progress</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody class="table-group-divider">
                                            <c:forEach var="c" items="${classes}">
                                                <tr>
                                                    <td>
                                                        <span class="avatar"><i class="fas fa-user"></i></span><a href="${pageContext.request.contextPath}/ClassDetail?classId=${c.id}">${c.tutor.name} - ${c.tutor.subject.name}</a>
                                                    </td>
                                                    <c:set var="session" value="${sessionData[c.id]}" />
                                                    <td><fmt:formatDate value="${c.startDate}" pattern="dd/MM/yyyy"/></td>
                                                    <td>${fn:substring(sessionData[c.id].startTime, 0, 5)}</td>
                                                    <td>${c.totalSession}</td>
                                                    <td>
                                                        <c:set var="progress" value="${progressMap[c.id]}" />
                                                        <div class="progress">
                                                            <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" style="width: ${progress}%;" aria-valuenow="${progress}" aria-valuemin="0" aria-valuemax="100">Finished</div>
                                                            <div role="progressbar" style="width: ${100 - progress}%;" aria-valuenow="${100 - progress}" aria-valuemin="0" aria-valuemax="100"></div>
                                                        </div>
                                                        ${progress}%
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${c.status == 'finished'}">
                                                                <span style="color: #19692c; font-weight: bold">Class complete</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#exampleModal-${c.id}" style="height: 30px">
                                                                    <p >Cancel class</p>
                                                                </button>
                                                                <div class="modal fade" id="exampleModal-${c.id}" tabindex="-1" aria-labelledby="exampleModalLabel-${c.id}" aria-hidden="true">
                                                                    <div class="modal-dialog">
                                                                        <form method="get" action="CancelClassController">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <h5 class="modal-title" id="exampleModalLabel-${c.id}">Are you sure to cancel this class</h5>
                                                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                                </div>
                                                                                <div class="modal-body">
                                                                                    ${c.tutor.name} - ${c.tutor.subject.name} -  <fmt:formatDate value="${c.startDate}" pattern="dd/MM/yyyy"/>
                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <input type="hidden" name="class_id" value="${c.id}">

                                                                                    <button class="btn btn-primary" type="submit">Confirm</button>
                                                                                </div>
                                                                            </div>
                                                                        </form>
                                                                    </div>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        
    </body>
</html>
