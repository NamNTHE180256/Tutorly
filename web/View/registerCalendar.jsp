<%-- 
    Document   : registerCalendar
    Created on : 27 thg 7, 2024, 18:20:56
    Author     : asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Become a tutor</title>
        <link rel="stylesheet" href="../style/styleTutorRegister.css"/>
        <style>
            .table-responsive {
                overflow-x: auto;
            }
            .table {
                width: 100%;
                max-width: 100%;
                margin-bottom: 1rem;
                background-color: transparent;
                border-collapse: collapse;
            }
            .table th,
            .table td {
                padding: 1rem;
                vertical-align: top;
                border-top: 1px solid #dee2e6;
            }
            .table thead th {
                vertical-align: bottom;
                border-bottom: 2px solid #dee2e6;
            }
            .table tbody + tbody {
                border-top: 2px solid #dee2e6;
            }
            .table-bordered {
                border: 1px solid #dee2e6;
            }
            .table-bordered th,
            .table-bordered td {
                border: 1px solid #dee2e6;
            }
            .text-center {
                text-align: center;
            }
            .bg-light-gray {
                background-color: #f8f9fa;
            }
            .align-middle {
                vertical-align: middle;
            }
            .text-uppercase {
                text-transform: uppercase;
            }
            .form-buttons {
                margin-top: 1rem;
                text-align: center;
            }
            .form-buttons button {
                padding: 0.5rem 1rem;
                font-size: 1rem;
                color: #fff;
                background-color: #007bff;
                border: none;
                border-radius: 0.25rem;
                cursor: pointer;
            }
            .form-buttons button:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <header>
            <img src="../image/LOGO_Main.png" alt="Tutorly Logo" class="logo">
        </header>

        <div class="container">
            <div class="registration-form">
                <h1 >Please choose the days you can teach</h1>
                <form id="availability-setup-form" action="<%=request.getContextPath()%>/TutorRegisterServlet" method="post">
                    <input type="hidden" name="step" value="6">
                    <div class="table-responsive" >
                        <table class="table table-bordered text-center">
                            <thead>
                                <tr class="bg-light-gray">
                                    <th class="text-uppercase">Time</th>
                                    <th class="text-uppercase">Monday</th>
                                    <th class="text-uppercase">Tuesday</th>
                                    <th class="text-uppercase">Wednesday</th>
                                    <th class="text-uppercase">Thursday</th>
                                    <th class="text-uppercase">Friday</th>
                                    <th class="text-uppercase">Saturday</th>
                                    <th class="text-uppercase">Sunday</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="align-middle">08:00</td>
                                    <% for (String day : new String[]{"M", "T", "W", "R", "F", "SA", "SU"}) { %>
                                    <td><input type="checkbox" name="availability" value="<%=day%>1"></td>
                                        <% } %>
                                </tr>
                                <tr>
                                    <td class="align-middle">10:00</td>
                                    <% for (String day : new String[]{"M", "T", "W", "R", "F", "SA", "SU"}) { %>
                                    <td><input type="checkbox" name="availability" value="<%=day%>2"></td>
                                        <% } %>
                                </tr>
                                <tr>
                                    <td class="align-middle">14:00</td>
                                    <% for (String day : new String[]{"M", "T", "W", "R", "F", "SA", "SU"}) { %>
                                    <td><input type="checkbox" name="availability" value="<%=day%>3"></td>
                                        <% } %>
                                </tr>
                                <tr>
                                    <td class="align-middle">16:00</td>
                                    <% for (String day : new String[]{"M", "T", "W", "R", "F", "SA", "SU"}) { %>
                                    <td><input type="checkbox" name="availability" value="<%=day%>4"></td>
                                        <% } %>
                                </tr>
                                <tr>
                                    <td class="align-middle">19:00</td>
                                    <% for (String day : new String[]{"M", "T", "W", "R", "F", "SA", "SU"}) { %>
                                    <td><input type="checkbox" name="availability" value="<%=day%>5"></td>
                                        <% } %>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div style=" width: 50%; margin-left: 25%"> 
                        <label for="link-meet" style="font-weight: bold">Please create and enter a GoogleMeet link (This will be used for teaching student)</label>
                        <input type="text" id="link-meet" name="link-meet" placeholder="Enter a GoogleMeet link" required></div>
                    <div class="form-buttons">
                        <button type="submit" id="save-continue">Save and continue</button>
                    </div>
                </form>
            </div>
            <div class="image-section">
                <div class="image-content">
                    <div class="transparent-square">
                        <h2>Become a <div><span style="color: orange;">part</span> of</div></h2>
                        <img id="logo-sentence" src="../image/LOGO_BIG.png" alt="logo">
                    </div>
                </div>
                <img src="../image/rightPic.png" alt="Blackboard">
            </div>
        </div>
    </body>
</html>
