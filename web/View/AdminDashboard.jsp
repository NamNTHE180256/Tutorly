<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">

        <style>
            .card {
                position: relative;
                overflow: hidden;
            }
            .card-body {
                position: relative;
                z-index: 2;
                color: black; /* Changed to black for better readability */
            }
            .card-title {
                font-size: 1.5rem;
            }
            .card-quantity {
                font-size: 2.5rem;
                font-weight: bold;
            }
            .card-overlay {
                position: absolute;
                top: 0;
                left: 0;
                width: 6%;
                height: 100%;
                z-index: 1;
            }
            .card-tutor .card-overlay {
                background-color: #007bff;
            }
            .card-tutor .card-title {
                color: #007bff;
            }
            .card-learner .card-overlay {
                background-color: #28a745;
            }
            .card-learner .card-title {
                color: #28a745;
            }
            .card-manager .card-overlay {
                background-color: #ffc107;
            }
            .card-manager .card-title {
                color: #ffc107;
            }
            .card-classes .card-overlay {
                background-color: #dc3545;
            }
            .card-classes .card-title {
                color: #dc3545;
            }
        </style>
    </head>
    <body>
        <%@ include file="AdminHeader.jsp" %>
        <div class="container mt-5">
            <div class="row">
                <div class="col-md-3">
                    <div class="card text-center card-tutor">
                        <div class="card-overlay"></div>
                        <div class="card-body">
                            <h5 class="card-title">Tutor</h5>
                            <p class="card-quantity" id="tutor-quantity">${tutorQuantity}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center card-learner">
                        <div class="card-overlay"></div>
                        <div class="card-body">
                            <h5 class="card-title">Learner</h5>
                            <p class="card-quantity" id="learner-quantity">${learnerQuantity}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center card-manager">
                        <div class="card-overlay"></div>
                        <div class="card-body">
                            <h5 class="card-title">Manager</h5>
                            <p class="card-quantity" id="manager-quantity">${managerQuantity}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center card-classes">
                        <div class="card-overlay"></div>
                        <div class="card-body">
                            <h5 class="card-title">Class</h5>
                            <p class="card-quantity" id="classes-quantity">${classQuantity}</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-5">
                <div class="col-md-6 offset-md-3">
                    <canvas id="subjectsPieChart"></canvas>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>
            $(document).ready(function () {
                // Parse the JSON string from the servlet
                var subjectTutorCounts = JSON.parse('${subjectTutorCountsJson}');

                var labels = [];
                var quantities = [];
                var backgroundColors = ['#007bff', '#28a745', '#ffc107', '#dc3545', '#6c757d', '#17a2b8', '#6610f2', '#e83e8c', '#fd7e14', '#20c997'];

                for (var subject in subjectTutorCounts) {
                    if (subjectTutorCounts.hasOwnProperty(subject)) {
                        labels.push(subject);
                        quantities.push(subjectTutorCounts[subject]);
                    }
                }

                var ctx = document.getElementById('subjectsPieChart').getContext('2d');
                var subjectsPieChart = new Chart(ctx, {
                    type: 'pie',
                    data: {
                        labels: labels,
                        datasets: [{
                                data: quantities,
                                backgroundColor: backgroundColors.slice(0, labels.length),
                            }]
                    },
                    options: {
                        responsive: true,
                        legend: {
                            position: 'top',
                        },
                        title: {
                            display: true,
                            text: 'Subjects Ratio'
                        },
                        animation: {
                            animateScale: true,
                            animateRotate: true
                        }
                    }
                });
            });
        </script>
    </body>
</html>
