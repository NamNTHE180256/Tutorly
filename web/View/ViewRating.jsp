<%-- 
    Document   : ViewRating
    Created on : 16 thg 6, 2024, 19:10:43
    Author     : asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Model.Rating"%>
<%@page import="Model.Tutor"%>
<%@page import="DAO.TutorDAO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tutor Ratings</title>
        <link rel="stylesheet" href="../style/RatingDisplay.css"/>
    </head>
    <body>
        <%
            int tutorId = Integer.parseInt(request.getParameter("tutorId"));
            TutorDAO tutorDAO = new TutorDAO();
            Tutor tutor = tutorDAO.getTutorById(tutorId);
            List<Rating> ratings = (List<Rating>) request.getAttribute("ratings");
            double averageRating = (double) request.getAttribute("averageRating");
            int totalRatings = (int) request.getAttribute("totalRatings");
        %>

        <div class="container">
            <h2>Ratings for <%= tutor.getName()%></h2>
            <div class="summary">
                <div class="average-rating">
                    <h3>Average Rating: <%= String.format("%.1f", averageRating)%> out of 5</h3>
                    <div class="stars">
                        <% for (int i = 0; i < 5; i++) {%>
                        <span class="star <%= i < averageRating ? "filled" : ""%>">&#9733;</span>
                        <% }%>
                    </div>
                </div>
                <div class="total-ratings">
                    <h3><%= totalRatings%> Ratings</h3>
                </div>
            </div>

            <div class="filter">
                <form action="TutorRatingsServlet" method="get">
                    <input type="hidden" name="tutorId" value="<%= tutorId%>">
                    <label for="ratingFilter">Filter by Rating:</label>
                    <select name="ratingFilter" id="ratingFilter" onchange="this.form.submit()">
                        <option value="all">All</option>
                        <option value="5">5 Stars</option>
                        <option value="4">4 Stars</option>
                        <option value="3">3 Stars</option>
                        <option value="2">2 Stars</option>
                        <option value="1">1 Star</option>
                    </select>
                </form>
            </div>

            <div class="ratings-list">
                <c:forEach var="rating" items="${ratings}">
                    <div class="rating-item">
                        <div class="rating-header">
                            <span class="learner-name">${rating.learner.name}</span>
                            <span class="rating-date">${rating.createdAt}</span>
                            <div class="stars">
                                <% for (int i = 0; i < rating.rating; i++) { %>
                                <span class="star filled">&#9733;</span>
                                <% } %>
                                <% for (int i = rating.rating; i < 5; i++) { %>
                                <span class="star">&#9733;</span>
                                <% }%>
                            </div>
                        </div>
                        <div class="rating-review">
                            <p>${rating.review}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
               
    </body>
</html>

