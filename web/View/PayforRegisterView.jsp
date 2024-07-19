<%-- 
    Document   : PayforRegisterView
    Created on : Jul 16, 2024, 3:05:54 PM
    Author     : TRANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Poppins&display=swap');

            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Poppins', sans-serif
            }

            .p{
                margin: 0
            }

            .container {
                max-width: 900px;
                margin: 30px auto;
                background-color: #0E3C6E;
                padding: 35px;
            }

            .box-right {
                padding: 30px 25px;
                background-color: white;
                border-radius: 15px
            }

            .box-left {
                padding: 20px 20px;
                background-color: white;
                border-radius: 15px
            }

            .textmuted {
                color: #7a7a7a
            }

            .bg-green {
                background-color: #d4f8f2;
                color: #06e67a;
                padding: 3px 0;
                display: inline;
                border-radius: 25px;
                font-size: 11px
            }

            .p-blue {
                font-size: 14px;
                color: #1976d2
            }

            .fas.fa-circle {
                font-size: 12px
            }

            .p-org {
                font-size: 14px;
                color: #fbc02d
            }

            .h7 {
                font-size: 15px
            }

            .h8 {
                font-size: 12px
            }

            .h9 {
                font-size: 10px
            }

            .bg-blue {
                background-color: #dfe9fc9c;
                border-radius: 5px
            }

            .form-control {
                box-shadow: none !important
            }

            .card input::placeholder {
                font-size: 14px
            }

            ::placeholder {
                font-size: 14px
            }

            input.card {
                position: relative
            }

            .far.fa-credit-card {
                position: absolute;
                top: 10px;
                padding: 0 15px
            }

            .fas,
            .far {
                cursor: pointer
            }

            .cursor {
                cursor: pointer
            }

            .btn.btn-primary {
                box-shadow: none;
                height: 40px;
                padding: 11px
            }

            .bg.btn.btn-primary {
                background-color: transparent;
                border: none;
                color: #1976d2
            }

            .bg.btn.btn-primary:hover {
                color: #539ee9
            }

            @media(max-width:320px) {
                .h8 {
                    font-size: 11px
                }

                .h7 {
                    font-size: 13px
                }

                ::placeholder {
                    font-size: 10px
                }
            }
        </style>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet"
              integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <title>Payment</title>
    </head>
    <body>
        <%@ include file="StudentHeader.jsp" %>
        <div class="container" >
            <div class="row justify-content-center m-0">
                <div class="col-md-7 col-12" >
                    <div class="row  justify-content-center" >
                        <div class="col-12 mb-4">
                            <p style="color: #fbc02d; font-size: 30px"><strong>Your invoice</strong> </p>
                        </div>
                        <div class="col-12 mb-4">
                            <div class="row box-right">
                                <div class="col-md-8 ps-0">
                                    <span style="display: inline-flex; align-items: center;">
                                        <p class="ps-3 textmuted fw-bold h6 mb-0" style="color: #fbc02d; font-size: 25px;">You are registering for tutor</p>

                                    </span>

                                    <span style="display: inline-flex;">
                                        <img style="height: 120px; width: 120px; margin: 5px;" src="image/${tutor.image}">
                                        <span style="margin-top: auto; margin-left: 5px;">
                                            <p style="color: #0E3C6E; font-size: 20px;"><strong>${tutor.name}</strong></p>
                                            <span style="display: inline-flex;">
                                                <i class="fa-solid fa-chalkboard-user mr-2"></i>
                                                <p style="margin-left: 5px;">${tutor.subject.name}</p>
                                            </span>
                                        </span>
                                    </span>
                                </div>
                                <div class="col-md-4" >
                                    <img style="height: 40px;margin-left: 100px; " src="image/LOGO_TUTORLY.png">
                                </div>
                                <hr/>
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th scope="col"></th>
                                            <th scope="col"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <p>InvoiceID</p>
                                            </td>
                                            <td>1wq2344335</td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <p >Session</p>
                                            </td>
                                            <td> ${session.dayOfWeek} : ${fn:substring(session.startTime, 0, 5)}-${fn:substring(session.endTime, 0, 5)}</td>

                                        </tr>
                                        <tr>
                                            <td>
                                                <p >Total lesson</p>
                                            </td>
                                            <td>${totallesson}</td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <p >Fee per lesson</p>
                                            </td>
                                            <td>${tutor.price}00</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <hr/>
                                <table class="table table-borderless">
                                    <thead>
                                        <tr>
                                            <th scope="col"></th>
                                            <th scope="col"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>Discount: </td>
                                            <td>0</td>
                                        </tr>

                                        <tr>
                                            <td>Subtotal:</td>
                                            <td>${tutor.price*totallesson}00</td>
                                        </tr>
                                        <tr>
                                            <td style="color: #0E3C6E;"><strong>You must pay:</strong> </td>
                                            <td style="color: #0E3C6E;"><strong>${tutor.price*totallesson}00</strong> </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <!-- <table class="table table-striped">
                                    <thead>
                                        <tr>
                                          <th scope="col"></th>
                                          <th scope="col"></th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <tr>
                                            <td>Discount: </td>
                                            <td>15%</td>
                                          </tr>
                                          <tr>
                                            <td>Subtotal:</td>
                                            <td>800.000</td>
                                          </tr>
                                        <tr>
                                          <td style="color: #0E3C6E;"><strong>You must pay:</strong> </td>
                                          <td style="color: #0E3C6E;"><strong>800.000</strong> </td>
                                         
                                        </tr>
                                      </tbody>
                                    </table> -->
                                <a href="RegisterOfficialClass?tutor_id=${tutor.id}&learner_id=${learner_id}&session=${session.id}&totallesson=${totallesson}">
                                    <div class="btn btn-primary d-block h8">PAY <span
                                            class="fas fa-dollar-sign ms-2"></span>${tutor.price*totallesson}00<span
                                            class="ms-3 fas fa-arrow-right"></span></div></a>
                            </div>

                            <div class="col-md-4" >
                                <img style="height: 40px;margin-left: 100px; " src="image/LOGO_TUTORLY.png">
                            </div>
                            <hr/>
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th scope="col"></th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>    
                                <tbody>
                                    <tr>
                                        <td>
                                            <p>InvoiceID</p>
                                        </td>
                                        <td>1wq2344335</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <p >Session</p>
                                        </td>
                                        <td> ${session.dayOfWeek} : ${fn:substring(session.startTime, 0, 5)}-${fn:substring(session.endTime, 0, 5)}</td>
                                        
                                    </tr>
                                    <tr>
                                        <td>
                                            <p >Total lesson</p>
                                        </td>
                                        <td>${totallesson}</td>
                                    </tr>
                                    
                                    <tr>
                                        <td>
                                            <p >Fee per lesson</p>
                                        </td>
                                        <td><span><fmt:formatNumber value="${tutor.price}" pattern="###,###" /></td>
                                    </tr>
                                </tbody>
                            </table>
                            <hr/>
                            <table class="table table-borderless">
                                <thead>
                                    <tr>
                                        <th scope="col"></th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Discount: </td>
                                        <td>0</td>
                                    </tr>
                                        
                                    <tr>
                                        <td>Subtotal:</td>
                                        <td><span><fmt:formatNumber value="${tutor.price*totallesson}" pattern="###,###" /></td>
                                    </tr>
                                    <tr>
                                        <td style="color: #0E3C6E;"><strong>You must pay:</strong> </td>
                                        <td style="color: #0E3C6E;"><strong><span><fmt:formatNumber value="${tutor.price*totallesson}" pattern="###,###" /></strong> </td>
                                    </tr>
                                </tbody>
                            </table>
                            <!-- <table class="table table-striped">
                                <thead>
                                    <tr>
                                      <th scope="col"></th>
                                      <th scope="col"></th>
                                    </tr>
                                  </thead>
                                  <tbody>
                                    <tr>
                                        <td>Discount: </td>
                                        <td>15%</td>
                                      </tr>
                                      <tr>
                                        <td>Subtotal:</td>
                                        <td>800.000</td>
                                      </tr>
                                    <tr>
                                      <td style="color: #0E3C6E;"><strong>You must pay:</strong> </td>
                                      <td style="color: #0E3C6E;"><strong>800.000</strong> </td>
                                        
                                    </tr>
                                  </tbody>
                                </table> -->
                            <a href="PaymentServlet?tutor_id=${tutor.id}&learner_id=${learner_id}&session=${session.id}&totallesson=${totallesson}&amount=${tutor.price*totallesson}">
                                <div class="btn btn-primary d-block h8">PAY <span
                                    class="fas fa-dollar-sign ms-2"></span><fmt:formatNumber value="${tutor.price*totallesson}" pattern="###,###" /><span
                                    class="ms-3 fas fa-arrow-right"></span></div></a>
                        </div>

                    </div>
                </div>

            </div>
        </div>

    </body>
</html>
