<%-- 
    Document   : newjsp
    Created on : May 29, 2024, 11:13:15 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${requestScope.class.toString()}</h1>
        <p> ${sessionScope.tutor}</p>
        <p> ${sessionScope.user.toString()}</p>
           <p> ${requestScope.status}</p>
    </body>
</html>
