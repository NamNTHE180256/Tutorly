<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <title>Income Tracker</title>
        <style>
            body, html {
                height: 100%;
                margin: 0;
                padding: 0;
                display: flex;
                flex-direction: column;
            }
            .container1 {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f7f9fc;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: flex-start;
                flex-grow: 1;
                padding: 20px;
            }
            .wrapper {
                width: 80%;
                margin-top: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
                background-color: #fff;
                padding: 30px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                text-align: center;
            }
            .time-buttons {
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }
            .time-button {
                padding: 10px 20px;
                margin: 0 10px;
                cursor: pointer;
                border: none;
                border-radius: 4px;
                background-color: #e7e7e7;
                transition: background-color 0.3s;
                font-size: 16px;
            }
            .time-button:hover {
                background-color: #d7d7d7;
            }
            .active {
                background-color: #007bff;
                color: #fff;
            }
            .navigation-buttons {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-bottom: 20px;
            }
            .nav-button {
                font-size: 24px;
                cursor: pointer;
                margin: 0 20px;
                color: #007bff;
                transition: color 0.3s;
            }
            .nav-button:hover {
                color: #0056b3;
            }
            .chart {
                width: 100%;
                border: 1px solid #ccc;
                border-radius: 8px;
                background-color: #fafafa;
                display: flex;
                align-items: center;
                justify-content: center;
                color: #777;
            }
            h1 {
                font-size: 36px;
                font-weight: 700;
                margin-bottom: 20px;
                color: #333;
            }
            h2 {
                font-size: 28px;
                font-weight: 600;
                margin-bottom: 20px;
                color: #555;
            }
            .form-group label {
                font-size: 18px;
                color: #333;
                font-weight: 500;
            }
            .form-group input {
                font-size: 16px;
                padding: 10px;
                border-radius: 5px;
                border: 1px solid #ccc;
                width: 100%;
                margin-top: 5px;
            }
            .btn-primary {
                background-color: #007bff;
                border: none;
                padding: 10px 20px;
                font-size: 18px;
                border-radius: 5px;
                transition: background-color 0.3s;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
            .table th, .table td {
                font-size: 16px;
                padding: 12px;
                text-align: center;
                border-top: none;
            }
            .table thead th {
                border-bottom: 2px solid #dee2e6;
                color: #555;
                font-weight: 600;
            }
            .total-income-box {
                width: 80%;
                margin: 20px auto;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                background-color: #fff;
                text-align: center;
            }
            .total-income {
                font-size: 24px;
                font-weight: bold;
                cursor: pointer;
                color: #007bff;
                transition: color 0.3s;
            }
            .total-income:hover {
                color: #0056b3;
            }
            footer {
                background-color: #002147;
                color: #fff;
                padding: 20px 0;
                text-align: center;
                width: 100%;
                flex-shrink: 0;
                margin-top: auto;
            }
        </style>
    </head>
    <body>
        <%@include file="TutorHeader.jsp" %>
        <div class="container1">
            <h1>Income</h1>
            <div class="wrapper">
                <form action="${pageContext.request.contextPath}/IncomeController" method="POST">
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <input type="hidden" name="view" value="week">
                            <input type="text" placeholder="StartDate" id="startDate" name="start" class="form-control datepicker" required>
                            <input type="hidden" name="tutorID" value="${sessionScope.tutor.id}">
                        </div>
                        <div class="form-group col-md-6">
                            <input type="text" placeholder="EndDate " id="endDate" name="end" class="form-control datepicker" required>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary mt-3">Filter</button>
                </form>
            </div>
            <div class="total-income-box">
                <h2 class="total-income" onclick="showTotalModal()">Total Income: 
                    <c:set var="totalIncome" value="0" />
                    <c:forEach var="x" items="${PaidList}">
                        <c:set var="totalIncome" value="${totalIncome + x.total}" />
                    </c:forEach>
                    ${totalIncome}
                </h2>
            </div>
            <div class="wrapper">
                <h5>All of Income</h5>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Amount</th>
                            <th scope="col">Tax</th>
                            <th scope="col">Total</th>
                            <th scope="col">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="totalIncome2" value="0" />
                        <c:if test="${not empty listIncome}">
                            <c:forEach var="x" items="${listIncome}">
                                <c:set var="totalIncome2" value="${totalIncome2 + x.total}" />
                                <tr onclick="showModal('${x.amount}', '${x.tax}', '${x.total}', '${x.status}', '${x.createdAt}', '${x.dayPaid}')">
                                    <td>${x.amount}</td>
                                    <td>${x.tax}</td>
                                    <td>${x.total}</td>
                                    <td>
                                        <c:if test="${x.status eq 'paid'}">
                                            <span style="color: blue">${x.status}</span>
                                        </c:if>
                                        <c:if test="${x.status eq 'processing'}">
                                            <span style="color: #8B8000">${x.status}</span>
                                        </c:if>
                                        <c:if test="${x.status eq 'pending confirmation'}">
                                            <span style="color: red">${x.status}</span>
                                        </c:if>
                                    </td>
                                </tr> 
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty listIncome}">
                            <tr>
                                <td colspan="4">No income records found for the specified period.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal for Total Income -->
        <div class="modal fade" id="totalIncomeModal" tabindex="-1" role="dialog" aria-labelledby="totalIncomeModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="totalIncomeModalLabel">Total Income Details</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p><strong>Start Date:</strong> <span id="modalStartDate">${param.start}</span></p>
                        <p><strong>End Date:</strong> <span id="modalEndDate">${param.end}</span></p>
                        <p><strong>Total Income:</strong> <span id="modalTotalIncome">${totalIncome}</span></p>
                        <p><strong>Potential Income:</strong> <span id="modalPotentialIncome">${totalIncome2}</span></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal for Income Details -->
        <div class="modal fade" id="incomeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Income Details</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p><strong>Amount:</strong> <span id="modalAmount"></span></p>
                        <p><strong>Tax:</strong> <span id="modalTax"></span></p>
                        <p><strong>Total:</strong> <span id="modalTotal"></span></p>
                        <p><strong>Created At:</strong> <span id="modalCreatedAt"></span></p>
                        <p><strong>Day Paid:</strong> <span id="modalDayPaid"></span></p>
                        <p><strong>Status:</strong> <span id="modalStatus"></span></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                flatpickr('.datepicker', {
                    dateFormat: 'Y-m-d',
                    allowInput: true
                });
            });

            function showTotalModal() {
                $('#totalIncomeModal').modal('show');
            }

            function showModal(amount, tax, total, status, createdAt, dayPaid) {
                document.getElementById('modalAmount').innerText = amount;
                document.getElementById('modalTax').innerText = tax;
                document.getElementById('modalTotal').innerText = total;
                document.getElementById('modalStatus').innerText = status;
                document.getElementById('modalCreatedAt').innerText = createdAt;
                document.getElementById('modalDayPaid').innerText = dayPaid;
                $('#incomeModal').modal('show');
            }
        </script>
        <footer>
            <div class="container">
                <p style="margin: 0; font-size: 16px;">
                    Mọi góp ý, thắc mắc xin liên hệ Công ty cung cấp dịch vụ gia sư | Email: <a href="mailto:Tutory@gmail.com" style="color: #FFC107;">Tutory@gmail.com</a> | Điện thoại: <a href="tel:0123456789" style="color: #FFC107;">0123456789</a>
                </p>
                <p style="margin: 0; font-size: 16px;">
                    © 2024 Power by TUTORLY
                </p>
            </div>
        </footer>
    </body>
</html>
