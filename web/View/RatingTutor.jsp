<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DAO.AClassDAO"%>
<%@page import="Model.AClass"%>
<%@page import="Model.Tutor"%>
<%@page import="Model.Subject"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Rate Tutor</title>
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="../style/RatingForm.css"/>
    </head>
    <body>
        <%
        //    int classId = Integer.parseInt(request.getParameter("classId")); // Replace with the actual parameter name
            int classId = 1;
            AClassDAO classDAO = new AClassDAO();
            AClass aClass = classDAO.getClassById(classId);
            int learnerId = aClass.getLearner().getId();
            Tutor tutor = aClass.getTutor();
            Subject subject = tutor.getSubject();
            int tutorId = tutor.getId();
        %>

        <div class="wrapper">
            <h3>Rate the tutor</h3>
            <label for="tutorName" style="margin-bottom: 20px;">Tutor Name: <%= tutor.getName() %></label>
            <br>
            <label for="subject">Subject: <%= subject.getName() %></label>
            <form id="ratingForm" action="<%=request.getContextPath() %>/RatingTutorServlet" method="post">
                <div class="rating">
                    <input type="hidden" name="rating" value="0">
                    <i class='bx bx-star star' style="--i: 1;"></i>
                    <i class='bx bx-star star' style="--i: 2;"></i>
                    <i class='bx bx-star star' style="--i: 3;"></i>
                    <i class='bx bx-star star' style="--i: 4;"></i>
                    <i class='bx bx-star star' style="--i: 5;"></i>
                </div>
                <textarea name="review" id="review" cols="30" rows="5" placeholder="Your opinion..."></textarea>
                <input type="hidden" name="learnerId" value="<%= learnerId %>" />
                <input type="hidden" name="tutorId" value="<%= tutorId %>" />
                <div class="btn-group">
                    <button type="submit" class="btn submit">Submit</button>
                    <button type="button" class="btn cancel" onclick="window.location.href = 'home.jsp'">Cancel</button>
                </div>
            </form>
        </div>
        <c:if test="${not empty message}">
    <script>
        alert('${message}');
        if (${success}) {
            window.location.href = 'View/newPage.jsp';
        } else {
            window.location.href = 'View/RatingTutor.jsp';
        }
    </script>
</c:if>

        <script src="../js/RatingTutor.js"></script>

    </body>
</html>
