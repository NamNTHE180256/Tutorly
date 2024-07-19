<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/core@5.10.1/main.min.js'></script>
        <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@fullcalendar/core@5.10.1/main.min.css' />
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet"
              integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
        <style>
            .header {
                text-align: center;
                margin: 20px 0;
            }

            .document-list {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                padding: 20px;
                background-color: #f8f9fa;
                border-radius: 0.5rem;
            }

            .document-list nav {
                display: flex;
                flex-direction: column;
                align-items: center;
                margin: 91px;
            }

            .document-list h2 {
                font-size: 1.5rem;
                color: #333;
            }

            .document-list a {
                color: #007bff;
                margin-top: 10px;
            }

            @media (max-width: 768px) {
                .document-list {
                    flex-direction: column;
                    align-items: center;
                }

                .vertical-line {
                    border-left: none;
                    border-top: 2px solid #ccc;
                    width: 80%;
                    height: auto;
                    margin: 20px 0;
                }
            }
        </style>
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
        <hr/>
        <h1 class="header">Material</h1>
        <div class="layout">
            <main class="layout-main px-4">
                <c:if test="${not empty requestScope.mate.getFilePath()}">
                    <iframe src="${requestScope.mate.getFilePath()}" width="800" height="500" frameborder="0"></iframe>
                    </c:if>
            </main>

            <aside class="layout-sidebar bd-h-60 border-end">
                <ul class="list-group" style="width:250px">
                    <c:forEach var="x" items="${requestScope.listmaterial}">
                        <li class="list-group-item list-group-item-action">
                            <a href="Material?action=download&slotid=${requestScope.slotid}&id=${x.id}&classid=${requestScope.classid}">
                                ${x.fileName}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </aside>
        </div>
         <%@ include file="tutor-footer.jsp" %>
    </body>
</html>
