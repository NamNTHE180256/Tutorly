<%-- 
    Document   : Record
    Created on : Jul 22, 2024, 4:21:11 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <header>
            <c:choose>
                <c:when test="${user.role eq 'tutor'}">
                    <%@ include file="TutorHeader.jsp" %>
                </c:when>
                <c:otherwise>
                    <%@ include file="StudentHeader.jsp" %>
                </c:otherwise>
            </c:choose>
        </header>
        <div class="layout">
            <main class="layout-main px-4">
                <c:if test="${not empty fileUrl}">
                    <iframe src="${fileUrl}" width="800" height="500" frameborder="0"></iframe>
                    </c:if>
            </main>
            <aside class="layout-sidebar border-end">
                <ul class="list-group border-0" style="width:250px;">
                    <li class="list-group-item list-group-item-action">
                        <a href="#">Menu 1</a>
                    </li>

                </ul>
            </aside>
        </div>

</body>
</html>
