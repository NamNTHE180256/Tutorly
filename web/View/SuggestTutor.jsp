<%-- 
    Document   : SuggestTutor
    Created on : Jun 9, 2024, 4:05:31 AM
    Author     : TRANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <style>
            section{
                margin-top: 40px;
            }
            .card {
                margin: 10px 4px;
                transition: .6s ease;
            }

            .card:hover {
                transform: scale(1.05);
            }

            .card-block {
                padding: 10px;
            }

            .scrollcards {
                background-color: #fff;
                overflow: auto;
                white-space: nowrap;
            }

            ::-webkit-scrollbar {
                width: 0px;
                height: 0px;
                background: transparent;
            }

            div.scrollcards .card {
                display: inline-block;
                padding: 14px;
                text-decoration: none;
                height: auto;
                width: 300px;
            }

            .profile-card {
                display: flex;
                flex-direction: column;
                align-items: center;
                height: 200px;
                width: 100%;
                border-radius: 15px;
                padding: 10px;
                border: 1px solid #ffffff40;
                box-shadow: 0 5px 20px rgba(0,0,0,0.4);
                background: #fff;
                position: relative;
            }

            .profile-card .image {
                position: relative;
                height: 80px;
                width: 80px;
                margin-top: 10px;
            }

            .profile-card .image .profile-pic {
                width: 100%;
                height: 100%;
                object-fit: cover;
                border-radius: 50%;
                box-shadow: 0 5px 20px rgba(0,0,0,0.4);
            }

            .profile-card .data {
                display: flex;
                flex-direction: column;
                align-items: center;
                margin-top: 10px;
                text-align: center;
            }

            .profile-card .data h2 {
                font-size: 16px;
                font-weight: 600;
                margin: 5px 0;
            }

            .profile-card .data span {
                font-size: 12px;
                color: #777;
            }

            .profile-card .buttons {
                display: flex;
                justify-content: center;
                align-items: center;
                position: absolute;
                bottom: 8px;
                width: 100%;
            }

            .profile-card .buttons .btn {
                color: #fff;
                text-decoration: none;
                padding: 3px 3px 3px 3px; /* Adjust padding to make the button smaller */
                border-radius: 15px; /* Adjust border-radius to match the card style */
                font-size: 12px;
                white-space: nowrap;
                background: linear-gradient(to left, #1B74D4 0%, #0E3C6E 100%);
            }

            .profile-card .buttons .btn:hover {
                box-shadow: inset 0 5px 20px rgba(0,0,0,0.4);
            }


        </style>
    </head>
    <body>
        <div class="container-fluid">
    <div class="row">
      <div class="scrollcards">
        <c:forEach items="${suggesttutor_vector}" var="s">
          <div class="card">
            <div class="profile-card">
                <div class="image">
                  <img src="image/${s.getImage()}" alt="" class="profile-pic">
                </div>
                <div class="data">
                  <h2>${s.getName()}</h2>
                  <span>${s.getSubject().getName()}</span>
                </div>
                <div class="buttons">
                  <a href="#" class="btn">View Details</a>
                </div>
              </div>
          </div>
        </c:forEach>
      </div>
    </div>
   </div>
    </body>
</html>
