<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Income</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
    <style>
        .table th, .table td {
                text-align: center;
                vertical-align: middle;
            }
    </style>
</head>
<body>
    <%@ include file="AdminHeader.jsp" %>
    <div class="container" style="background-color: #eee; margin-top: 10px;">
        <div class="row justify-content-center">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-6">
                        <h3></h3>
                    </div>
                </div>
                <table class="table table-hover">
                    <thead style="background-color: #0E3C6E; color: white;">
                        <tr>
                            <th>ID</th>
                            <th>User ID</th>
                            <th>Transaction Date</th>
                            <th>Amount</th>
                            <th>Payment Method</th>
                            <th>Transaction Type</th>
                            <th>Status</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="transaction" items="${trans}">
                            <tr>
                                <td>${transaction.id}</td>
                                <td>${transaction.user.getEmail()}</td>
                                <td>${transaction.transactionDate}</td>
                                <td>${transaction.amount}</td>
                                <td>${transaction.paymentMethod}</td>
                                <td>${transaction.transactionType}</td>
                                <td>${transaction.status}</td>
                                <td>${transaction.description}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
