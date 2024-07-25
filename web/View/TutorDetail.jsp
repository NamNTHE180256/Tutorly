<%-- 
    Document   : TutorDetail
    Created on : May 31, 2024, 9:01:03 AM
    Author     : TRANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/fastbootstrap@2.2.0/dist/css/fastbootstrap.min.css" rel="stylesheet" integrity="sha256-V6lu+OdYNKTKTsVFBuQsyIlDiRWiOmtC8VQ8Lzdm2i4=" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <title>Tutor Profile</title>
        <style>
            body {
                background-color: #A2A2A2;
            }
            .leftconttent {
                padding: 10px;
                height: 100%;
            }
            .leftconttent .nav-item {
                width: 100px;
            }
            .rating {
                display: inline;
                align-items: center;
                text-align: center;
            }
            .fee {
                display: inline-flex;
            }
            .buttons {
                flex-direction: column;
                align-items: flex-end;
            }
            .btn-trial, .btn-register {
                background-color: #0E3C6E;
                color: white;
                padding: 10px 20px;
                border-radius: 5px;
                margin-top: 10px;
                text-align: center;
            }
            .btn-register {
                background-color: #6c757d;
            }
            .tutorimg {
                width: 200px;
                height: 200px;
                margin-right: 20px;
            }
            .tutor-name {
                color: #0E3C6E;
                font-size: 24px;
                font-weight: bold;
            }
            .subject, .students {
                display: flex;
                align-items: center;
            }
            .subject i, .students i {
                font-size: 20px;
            }
            .subject h3, .students h3 {
                font-size: 20px;
                margin: 0;
            }
            body {
                margin-top: 20px;
            }
            .bg-light-gray {
                background-color: #f7f7f7;
            }
            .table-bordered thead td, .table-bordered thead th {
                border-bottom-width: 2px;
            }
            .table thead th {
                vertical-align: bottom;
                border-bottom: 2px solid #dee2e6;
            }
            .table-bordered td, .table-bordered th {
                border: 1px solid #dee2e6;
            }
            .bg-sky.box-shadow {
                box-shadow: 0px 5px 0px 0px #0E3C6E
            }
            .bg-sky {
                background-color: #0E3C6E
            }
            .padding-15px-lr {
                padding-left: 15px;
                padding-right: 15px;
            }
            .padding-5px-tb {
                padding-top: 5px;
                padding-bottom: 5px;
            }
            .margin-10px-bottom {
                margin-bottom: 10px;
            }
            .border-radius-5 {
                border-radius: 5px;
            }
            .margin-10px-top {
                margin-top: 10px;
            }
            .font-size14 {
                font-size: 14px;
            }
            .text-light-gray {
                color: #d6d5d5;
            }
            .font-size13 {
                font-size: 13px;
            }
            .table-bordered td, .table-bordered th {
                border: 1px solid #dee2e6;
            }
            .table td, .table th {
                padding: .75rem;
                vertical-align: top;
                border-top: 1px solid #dee2e6;
            }
            body {
                margin-top: 20px;
                background: #eee;
            }
            .review-list ul li .left span {
                width: 32px;
                height: 32px;
                display: inline-block;
            }
            .review-list ul li .left {
                flex: none;
                max-width: none;
                margin: 0 10px 0 0;
            }
            .review-list ul li .left span img {
                border-radius: 50%;
            }
            .review-list ul li .right h4 {
                font-size: 16px;
                margin: 0;
                display: flex;
            }
            .review-list ul li .right h4 .gig-rating {
                display: flex;
                align-items: center;
                margin-left: 10px;
                color: #ffbf00;
            }
            .review-list ul li .right h4 .gig-rating svg {
                margin: 0 4px 0 0px;
            }
            .country .country-flag {
                width: 16px;
                height: 16px;
                vertical-align: text-bottom;
                margin: 0 7px 0 0px;
                border: 1px solid #fff;
                border-radius: 50px;
                box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
            }
            .country .country-name {
                color: #95979d;
                font-size: 13px;
                font-weight: 600;
            }
            .review-list ul li {
                border-bottom: 1px solid #dadbdd;
                padding: 0 0 30px;
                margin: 0 0 30px;
            }
            .review-list ul li .right {
                flex: auto;
            }
            .review-list ul li .review-description {
                margin: 20px 0 0;
            }
            .review-list ul li .review-description p {
                font-size: 14px;
                margin: 0;
            }
            .review-list ul li .publish {
                font-size: 13px;
                color: #95979d;
            }
            .review-section h4 {
                font-size: 20px;
                color: #222325;
                font-weight: 700;
            }
            .review-section .stars-counters tr .stars-filter.fit-button {
                padding: 6px;
                border: none;
                color: #0E3C6E;
                text-align: left;
            }
            .review-section .fit-progressbar-bar .fit-progressbar-background {
                position: relative;
                height: 8px;
                background: #efeff0;
                -webkit-box-flex: 1;
                -ms-flex-positive: 1;
                flex-grow: 1;
                box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
                background-color: #ffffff;
                ;
                border-radius: 999px;
            }
            .review-section .stars-counters tr .star-progress-bar .progress-fill {
                background-color: #0E3C6E;
            }
            .review-section .fit-progressbar-bar .progress-fill {
                background: #2cdd9b;
                background-color: rgb(29, 191, 115);
                height: 100%;
                position: absolute;
                left: 0;
                z-index: 1;
                border-radius: 999px;
            }
            .review-section .fit-progressbar-bar {
                display: flex;
                align-items: center;
            }
            .review-section .stars-counters td {
                white-space: nowrap;
            }
            .review-section .stars-counters tr .progress-bar-container {
                width: 100%;
                padding: 0 10px 0 6px;
                margin: auto;
            }
            .ranking h6 {
                font-weight: 600;
                padding-bottom: 16px;
            }
            .ranking li {
                display: flex;
                justify-content: space-between;
                color: #95979d;
                padding-bottom: 8px;
            }
            .review-section .stars-counters td.star-num {
                color: #0E3C6E;
            }
            .ranking li > span {
                color: #62646a;
                white-space: nowrap;
                margin-left: 12px;
            }
            .review-section {
                border-bottom: 1px solid #dadbdd;
                padding-bottom: 24px;
                margin-bottom: 34px;
                padding-top: 64px;
            }
            .review-section select, .review-section .select2-container {
                width: 188px !important;
                border-radius: 3px;
            }
            ul, ul li {
                list-style: none;
                margin: 0px;
            }
            .helpful-thumbs, .helpful-thumb {
                display: flex;
                align-items: center;
                font-weight: 700;
            }
            .m-t-5 {
                margin-top: 5px;
            }
            .card {
                background: #D9D9D9;
                margin-bottom: 30px;
                transition: .5s;
                border: 0;
                border-radius: 20px;
                display: inline-block;
                position: relative;
                width: 400px;
                box-shadow: none;
            }
            .card .body {
                font-size: 14px;
                color: #424242;
                padding: 20px;
                font-weight: 400;
            }
            .profile-page .profile-header {
                position: relative
            }
            .profile-page .profile-header .profile-image img {
                border-radius: 50%;
                width: 90px;
                border: 3px solid #fff;
                box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23)
            }
            .profile-page .profile-sub-header {
                min-height: 60px;
                width: 100%
            }
            .profile-page .profile-sub-header ul.box-list {
                display: inline-table;
                table-layout: fixed;
                width: 100%;
                background: #eee
            }
            .profile-page .profile-sub-header ul.box-list li {
                border-right: 1px solid #e0e0e0;
                display: table-cell;
                list-style: none
            }
            .profile-page .profile-sub-header ul.box-list li:last-child {
                border-right: none
            }
            .profile-page .profile-sub-header ul.box-list li a {
                display: block;
                padding: 15px 0;
                color: #424242
            }
            .title {
                font-size: 20px;
                color: #0E3C6E;
                font-weight: bolder;
            }
            section {
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
                box-shadow: 0 5px 20px rgba(0, 0, 0, 0.4);
                background: #fff;
                position: relative;
            }
            .profile-card .image {
                position: relative;
                height: 80px;
                width: 80px;
                margin-top: 10px;
                overflow: hidden;
                border-radius: 50%;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #eee; /* Fallback color for when the image is not loaded */
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
                bottom: 15px;
                width: 100%;
            }
            .profile-card .buttons .btn {
                color: #fff;
                text-decoration: none;
                padding: 3px 3px 3px 3px; /* Adjust padding to make the button smaller */
                border-radius: 10px; /* Adjust border-radius to match the card style */
                font-size: 12px;
                white-space: nowrap;
                background: white;
                color: #0E3C6E;
            }
            .profile-card .buttons .btn:hover {
                box-shadow: inset 0 5px 20px rgba(0,0,0,0.4);
            }
        </style>
        <title>JSP Page</title>
    </head>
    <body>

        <%@ include file = "SearchTutorHeader.jsp" %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-8 " style="background-color: white; margin: 20px; margin-left: 40px; border-radius: 20px; padding: 20px; box-shadow: 0px 5px 15px 0px rgba(0, 0, 0, 0.35);">
                    <!-- Tutor information -->
                    <nav class="navbar navbar-expand-sm " style="padding: 10px">
                        <!-- Links -->

                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <img class="tutorimg" src="image/${tutor.getImage()}" alt="Tutor Image">
                            </li>
                            <div class="vr"></div>
                            <li class="nav-item">
                                <ul class="">
                                    <li class="list-group-item list-group-item-action">
                                        <h2 class="tutor-name">${tutor.getName()}</h2>
                                    </li>
                                    <hr/>
                                    <li class="list-group-item list-group-item-action">
                                        <div class="subject my-2">
                                            <i style="margin-right: 3px; color:#0E3C6E;" class="fa-solid fa-chalkboard-user mr-2"></i>
                                            <h3>${tutor.getSubject().name}</h3>
                                        </div>
                                    </li>

                                    <li class="list-group-item list-group-item-action">
                                        <div class="students my-2">
                                            <i style="margin-right: 3px; color:#0E3C6E;" class="fa-solid fa-user mr-2"></i>
                                            <h3>Students: 15</h3>
                                        </div>
                                    </li>

                                </ul>


                            </li>
                        </ul>
                    </nav>

                    <div> 
                        <nav class="navbar navbar-expand-sm ">
                            <!-- Links -->
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <p class="title" >About</p>
                                </li>
                                <li class="nav-item">
                                    <div class="line"></div>
                                </li>
                            </ul>
                        </nav>
                        <p style="color: #95979d">${tutor.getBio()}</p>
                    </div>

                    <!--Schedule-->    
                    <nav class="navbar navbar-expand-sm ">
                        <!-- Links -->
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <p class="title" >Schedule</p>
                            </li>
                            <li class="nav-item">
                                <div class="line"></div>
                            </li>
                        </ul>
                    </nav>

                    <div class="container">
                        
                        <div class="table-responsive">
                            <table class="table table-bordered text-center">
                                <thead >
                                    <tr class="bg-light-gray" style="background-color: #95979d">
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
                                        <td class="align-middle">08:00am</td>
                                        <c:forEach var="availability" items="${tutorAvailabilities}" begin="0" end="6">
                                            <c:choose>
                                                <c:when test="${availability.status == 'Available'}">
                                                    <td>
                                                        <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-white font-size16 xs-font-size13">Available</span>
                                                        <div class="margin-10px-top font-size14">8:00 - 9:30</div>
                                                        <div class="font-size13 text-light-gray">Tutor ${availability.tutor.name}</div>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td class="bg-light-gray"></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td class="align-middle">10:00am</td>
                                        <c:forEach var="availability" items="${tutorAvailabilities}" begin="7" end="13">
                                            <c:choose>
                                                <c:when test="${availability.status == 'Available'}">
                                                    <td>
                                                        <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-white font-size16 xs-font-size13">Available</span>
                                                        <div class="margin-10px-top font-size14">10:00 - 11:30</div>
                                                        <div class="font-size13 text-light-gray">Tutor ${availability.tutor.name}</div>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td class="bg-light-gray"></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td class="align-middle">02:00pm</td>
                                        <c:forEach var="availability" items="${tutorAvailabilities}" begin="14" end="20">
                                            <c:choose>
                                                <c:when test="${availability.status == 'Available'}">
                                                    <td>
                                                        <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-white font-size16 xs-font-size13">Available</span>
                                                        <div class="margin-10px-top font-size14">14:00 - 15:30</div>
                                                        <div class="font-size13 text-light-gray">Tutor ${availability.tutor.name}</div>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td class="bg-light-gray"></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    <tr>
                                        <td class="align-middle">04:00pm</td>
                                        <c:forEach var="availability" items="${tutorAvailabilities}" begin="21" end="27">
                                            <c:choose>
                                                <c:when test="${availability.status == 'Available'}">
                                                    <td>
                                                        <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-white font-size16 xs-font-size13">Available</span>
                                                        <div class="margin-10px-top font-size14">16:00 - 17:30</div>
                                                        <div class="font-size13 text-light-gray">Tutor ${availability.tutor.name}</div>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td class="bg-light-gray"></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td class="align-middle">07:00pm</td>
                                        <c:forEach var="availability" items="${tutorAvailabilities}" begin="28" end="34">
                                            <c:choose>
                                                <c:when test="${availability.status == 'Available'}">
                                                    <td>
                                                        <span class="bg-sky padding-5px-tb padding-15px-lr border-radius-5 margin-10px-bottom text-white font-size16 xs-font-size13">Available</span>
                                                        <div class="margin-10px-top font-size14">19:00 - 20:30</div>
                                                        <div class="font-size13 text-light-gray">Tutor ${availability.tutor.name}</div>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td class="bg-light-gray"></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!--RATE-->    
                    <div class="container">
                        <p class="title">What students say</p>
                        <div id="reviews" class="review-section">
                            <div class="d-flex align-items-center justify-content-between mb-4">
                                <h4 class="m-0">${ratings.size()} Reviews</h4>
                            </div>
<!--                            <div class="row">
                                <div class="col-md-6">
                                    <table class="stars-counters">
    <tbody>
        <c:forEach begin="1" end="5" var="star">
            <tr class="">
                <td>
                    <span>
                        <button class="fit-button fit-button-color-blue fit-button-fill-ghost fit-button-size-medium stars-filter">${star} Stars</button>
                    </span>
                </td>
                <td class="progress-bar-container">
                    <div class="fit-progressbar fit-progressbar-bar star-progress-bar">
                        <div class="fit-progressbar-background">
                            <c:choose>
                                <c:when test="${ratecount[star - 1] != null}">
                                    <span class="progress-fill" style="width: ${ratecount[star - 1].percentage}%;"></span>
                                </c:when>
                                <c:otherwise>
                                    <span class="progress-fill" style="width: 0%;"></span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </td>
                <td class="star-num">
                    <c:choose>
                        <c:when test="${ratecount[star - 1] != null}">
                            (${ratecount[star - 1].count})
                        </c:when>
                        <c:otherwise>
                            (0)
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

                                </div>
                            </div>-->
                        </div>

                        <!-- RATE comment -->
                        <div class="review-list">
                            <ul>
                                <li>
                                    <c:forEach var="rating" items="${ratings}">
                                        <div class="d-flex">
                                            <div class="left">
                                                <span>
                                                    <img src="image/${rating.learner.image}" class="profile-pict-img img-fluid" alt="${rating.learner.name}'s photo" />
                                                </span>
                                            </div>
                                            <div class="right">
                                                <h4>
                                                    ${rating.learner.name}
                                                    <span class="gig-rating text-body-2">
                                                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1792 1792" width="15" height="15">
                                                        <path
                                                            fill="currentColor"
                                                            d="M1728 647q0 22-26 48l-363 354 86 500q1 7 1 20 0 21-10.5 35.5t-30.5 14.5q-19 0-40-12l-449-236-449 236q-22 12-40 12-21 0-31.5-14.5t-10.5-35.5q0-6 2-20l86-500-364-354q-25-27-25-48 0-37 56-46l502-73 225-455q19-41 49-41t49 41l225 455 502 73q56 9 56 46z"
                                                            ></path>
                                                        </svg>
                                                        ${rating.rating}
                                                    </span>
                                                </h4>

                                                <div class="review-description">
                                                    <p>
                                                        ${rating.review}
                                                    </p>
                                                </div>
                                                <span class="publish py-3 d-inline-block w-100">Published ${rating.createdAt}</span>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </li>
                            </ul>
                        </div>
                    </div>


                    <!--Certificate-->    
                    <p class="title">Certificate</p>
                    <div class="container mt-3">
                        <ul class="list-group">
                            <li class="list-group-item">${tutor.getEdu()}</li>
                        </ul>
                    </div>
                    <!--Suggest--> 

                    <p class="title">Suggest</p>
                    <div class="container-fluid">
                        <div class="row">
                            <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
                                <div class="carousel-inner">
                                    <c:forEach items="${suggesttutor_vector}" var="s">
                                        <div class="carousel-item active">
                                            <div class="profile-card" style="background-color: #0E3C6E">
                                                <div class="image">
                                                    <img src="image/${s.image}" alt="${s.name}'s profile picture" class="profile-pic">
                                                </div>
                                                <div class="data">
                                                    <h2 style="color: white">${s.name}</h2>
                                                    <span style="color: white">${s.subject.name}</span>
                                                </div>
                                                <div class="buttons">
                                                    <a href="../Tutorly/TutorDetailController?id=${s.id}&idsub=${s.getSubject().id}" class="btn">View Details</a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <button class="carousel-control-prev" type="button"  data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Previous</span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Next</span>
                                </button>
                            </div>


                        </div>
                    </div>
                </div>    

                <div class="col-md-3 leftconttent" style="background-color: white; margin: 20px;border-radius: 20px; box-shadow: 0px 5px 15px 0px rgba(0, 0, 0, 0.35);">
                    <div class="col-md-3 text-right ">

                        <nav class="navbar navbar-expand-lg">
                            <div class="container-fluid">

                                <div class="collapse navbar-collapse" id="navbarExample">
                                    <ul class="navbar-nav me-auto mb-0">
                                        <li class="nav-item">
                                            <c:choose>
                                                <c:when test="${tutorRatings.NewTutor == null}">

                                                    <div class="rating mt-2">
                                                        <span class="rate" style="font-size: 25px; display: inline-flex;"> <i class="fa-solid fa-star text-warning"></i><h3>${tutorRatings.avgRate}</h3></span>

                                                        <p class="ratedby" style="font-size: 10px;">rated by ${tutorRatings.rateCount} learner(s)</p>
                                                    </div>

                                                </c:when>
                                                <c:otherwise>
                                                    <p>New tutor</p>
                                                </c:otherwise>
                                            </c:choose>
                                        </li>
                                        <li class="nav-item">
                                            <div>
                                                <div class="fee">
                                                    <h3><fmt:formatNumber value="${tutor.price}" pattern="###,###" /></h3>
                                                </div>
                                                <p style="font-size: 10px;">per session</p>
                                            </div>
                                        </li>
                                        
                                        <li class="nav-item">
                                            <form id="addtutorform-${tutor.id}" onsubmit="return false;">
                                                <button type="button" id="heart-button-${tutor.id}" style="background: white; border: 1px white;" onclick="change_heart('${tutor.id}')">
                                                    <c:choose>
                                                        <c:when test="${tutor.saveStatus == 'saved'}">
                                                            <i class="fa-solid fa-heart heart ml-2" style="font-size:35px;color: #A34343;"></i>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i class="fa-regular fa-heart heart ml-2" style="font-size:35px;"></i>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </button>
                                                <input type="hidden" name="service" value="add">
                                                <input type="hidden" name="tutor_id" value="${tutor.id}">
                                                <input type="hidden" name="learn_id" value="1">
                                            </form>
                                        </li>

                                    </ul>

                                </div>
                            </div>
                                                
                        </nav>
                        
                    </div> 
                      <div class="buttons ">
                            <div class="d-grid gap-2">
                                <a href="RegisterTrialCotroller?tutor_id=${tutor.id}" style="color:white"><button style="width: 100%;background-color: #0E3C6E" class="btn btn-primary" type="button">Book trial lesson</button></a>
                                <a href="RegisterClassController?tutor_id=${tutor.id}" style="color:white"><button style="width: 100%; background-color: #A2A2A2" class="btn btn-primary" type="button">Register class</button></a>
                            </div>
                        </div>
                </div>
            </div>
        </div>
                                                <script type="text/javascript">
            window.onload = function () {
                setTimeout(function () {
                    var successAlert = document.getElementById('successAlert');
                    if (successAlert) {
                        successAlert.style.display = 'none';
                    }
                    var errorAlert = document.getElementById('errorAlert');
                    if (errorAlert) {
                        errorAlert.style.display = 'none';
                    }
            <% session.removeAttribute("successMessage"); %>
            <% session.removeAttribute("errorMessage"); %>
                }, 5000); // 5000 milliseconds = 5 seconds
            };

            function change_heart(tutorId) {
                var button = document.getElementById('heart-button-' + tutorId);
                var icon = button.querySelector('i');

                if (icon.classList.contains('fa-regular')) {
                    icon.classList.remove('fa-regular');
                    icon.classList.add('fa-solid');
                    icon.style.color = '#A34343';
                    button.onclick = function () {
                        reset_heart(tutorId);
                    };

                    // Perform the AJAX request to add the tutor
                    var formId = 'addtutorform-' + tutorId;
                    var form = document.getElementById(formId);
                    var tId = form.querySelector('input[name="tutor_id"]').value;
                    var lId = form.querySelector('input[name="learn_id"]').value;
                    var service = form.querySelector('input[name="service"]').value;

                    var xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            alert('Data sent successfully!');
                        }
                    };

                    xhttp.onerror = function () {
                        alert('Request failed');
                    };

                    xhttp.open("POST", "SavedTutorController", true);
                    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                    var data = "tutor_id=" + tId + "&learn_id=" + lId + "&service=" + service;
                    xhttp.send(data);
                } else {
                    reset_heart(tutorId);
                }
                return false; // Prevent form submission
            }

            function reset_heart(tutorId) {
                var button = document.getElementById('heart-button-' + tutorId);
                var icon = button.querySelector('i');

                icon.classList.remove('fa-solid');
                icon.classList.add('fa-regular');
                icon.style.color = '';
                button.onclick = function () {
                    change_heart(tutorId);
                };

                // Perform the AJAX request to remove the tutor
                var formId = 'addtutorform-' + tutorId;
                var form = document.getElementById(formId);
                var tId = form.querySelector('input[name="tutor_id"]').value;
                var lId = form.querySelector('input[name="learn_id"]').value;
                var service = "remove";

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        alert(tId);
                        alert(lID);
                        alert('Data removed successfully!');
                    }
                };

                xhttp.onerror = function () {
                    alert('Request failed');
                };

                xhttp.open("POST", "SavedTutorController", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                var data = "tutor_id=" + tId + "&learn_id=" + lId + "&service=" + service;
                xhttp.send(data);

                return false; // Prevent form submission
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
