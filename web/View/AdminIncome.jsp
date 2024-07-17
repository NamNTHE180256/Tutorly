<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>View Transactions</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
    </style>
</head>
<body>
    <%@ include file="AdminHeader.jsp" %>
    <h1>All Transactions</h1>
    <table>
        <thead>
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
</body>
</html>
