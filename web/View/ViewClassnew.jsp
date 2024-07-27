<%-- 
    Document   : ViewClassnew
    Created on : Jul 26, 2024, 12:49:44 PM
    Author     : TRANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="../style/assignment.css">
    <title>View Class</title>
    </head>
    <body>
        <%@ include file="StudentHeader.jsp" %>
        <span style="margin: 20px;"></span>
        <button style="margin: 15px; color: white; background: #0E3C6E" class="btn btn-default dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
      Status of class
    </button>
    <ul class="dropdown-menu" role="menu">
      <li>
        <a class="dropdown-item" href="#">Finish</a>
      </li>
      <li><a class="dropdown-item" href="#">Ongoing </a></li>
      <li><a class="dropdown-item" href="#">Schedule</a></li>
    </ul>
        <div class="container">
        <div class="row row-cols-4">
            <c:forEach var="c" items="${classes}">
          <div class="col-3">
            <div class="card">
                <div class="card-body">
                  <h5 class="card-title" style="background-color: #0E3C6E; padding: 5px; 
                  color: white; border-radius: 3px; text-align: center;">${c.tutor.name} - ${c.tutor.subject.name}</h5>
                  <p class="card-text">
                    Session : ${sessionData[c.id]}
                  </p>
                  <p class="card-text">
                    Start date : ${c.startDate} - End date: ${c.endDate}
                  </p>
                  <p class="card-text">
                    Total lesson : ${c.totalSession}
                  </p>
                  <p class="card-text">
                    Attendance : 
                  </p>
                  <p class="card-text">
                    Progress : 
                  </p>
                  <p class="card-text">
                    Status : ${c.status}
                  </p>
                <span style="display: inline-flex;">
                  <button style="margin-right: 15px;" type="button" class="btn btn-outline-primary">View details</button>
                  <!--<button type="button" class="btn btn-outline-danger">Cancel class</button>-->
                  <button type="button" class="btn btn-outline-warning">Rate tutor</button>
                </span>
                </div>
              </div>
          </div>
         </c:forEach>
          
        </div>
      </div>
    </body>
</html>
