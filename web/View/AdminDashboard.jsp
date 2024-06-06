<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Profile</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel='stylesheet' href='https://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>
    <script src='https://netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>
    <link rel="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript">
    </script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.2/Chart.min.js"></script>
    <link rel="stylesheet" href="../style/test.css">
</head>

<body>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>

    <div class="container">
        <div class="col-md-12">
            <div class="page-people-directory">
                <div class="row">
                    <div class="col-md-3">
                        <div class="list-group" id="myScrollspy">
                            <a class="list-group-item" href="#content-javascript">General</a>
                            <a class="list-group-item" href="#content-javascript">Student</a>
                            <a class="list-group-item" href="#content-css">Tutor</a>
                            <a class="list-group-item" href="#content-bootstrap">Subject</a>
                        </div>

                        
                    </div>
                    <div class="col-md-9">
                        
                        <div class="well">
                            <div class="row">
                                <div class="col-md-7">

                                </div>
                                <div class="col-md-5">
                                    <form class="form-inline" action="/somepage">
                                        <input class="form-control mr-sm-2" type="text" placeholder="Search">
                                        <button class="btn btn-success" type="submit"
                                            style="background-color: #0E3C6E;">Search</button>
                                    </form>

                                </div>
                            </div>
                        </div>
                        <br>
                        

                        <div class="row">
                            <div class="col-md-6">
                                <h3>All Student</h3>
                            </div>

                        </div>

                        <table class="table">
                            <thead style="background-color: #0E3C6E; color: white;">
                                <tr>
                                    <th>Student ID</th>
                                    <th>Avatar</th>
                                    <th>Student Name</th>
                                    <th>Student Email</th>
                                    <th>No. Class</th>
                                    <th>Detail</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td><img src="../asset/test_img.png" class="stu_image"></td>
                                    <td><strong>Cu trang beo</strong> </td>
                                    <td>cutrangbeo@top1vutru.gmail</td>
                                    <td>12</td>
                                    <td><a href="#"><i class="fa-solid fa-eye" style=" color: #0E3C6E;"></i>View Details</a></td>
                                    <td><i class="fa-solid fa-trash" style=" color: red;"></i></td>
                                </tr>
                            </tbody>
                        </table>


                        <div class="row">
                            <div class="col-md-6">
                                <h3>All Tutor</h3>
                            </div>

                        </div>

                        <table class="table">
                            <thead style="background-color: #0E3C6E; color: white;">
                                <tr>
                                    <th>Tutor ID</th>
                                    <th></th>
                                    <th>Tutor Name</th>
                                    <th>Tutor Email</th>
                                    <th>Subject</th>
                                    <th>Status</th>
                                    <th>Edit</th>
                                    <th>Detail</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td><img src="../asset/test_img.png" class="stu_image"></td>
                                    <td><strong>Cu trang beo</strong> </td>
                                    <td>cutrangbeo@top1vutru.gmail</td>
                                    <td>Maths</td>
                                    <td><span class="dot" style=" height: 10px;
                                        width: 10px;
                                        background-color: #00ff4c;
                                        border-radius: 50%;
                                        display: inline-block;"></span>Active</td>
                                    <td><a href="#"><i class="fa-solid fa-eye" style=" color: #0E3C6E;"></i>View Details</a></td>
                                    <td><i class="fa-solid fa-pen" style=" color: #0E3C6E;"></i></td>
                                    <td><i class="fa-solid fa-trash" style=" color: red;"></i></td>
                                </tr>

                                <tr>
                                    <td>1</td>
                                    <td><img src="../asset/test_img.png" class="stu_image"></td>
                                    <td><strong>Cu trang beo</strong> </td>
                                    <td>cutrangbeo@top1vutru.gmail</td>
                                    <td>Maths</td>
                                    <td><span class="dot" style=" height: 10px;
                                        width: 10px;
                                        background-color: #F7B500;
                                        border-radius: 50%;
                                        display: inline-block;"></span>Pending</td>
                                    <td><a href="#"><i class="fa-solid fa-eye" style=" color: #0E3C6E;"></i>View Details</a></td>
                                    <td><i class="fa-solid fa-pen" style=" color: #0E3C6E;"></i></td>
                                    <td><i class="fa-solid fa-trash" style=" color: red;"></i></td>
                                </tr>

                                <tr>
                                    <td>1</td>
                                    <td><img src="../asset/test_img.png" class="stu_image"></td>
                                    <td><strong>Cu trang beo</strong> </td>
                                    <td>cutrangbeo@top1vutru.gmail</td>
                                    <td>Maths</td>
                                    <td><span class="dot" style=" height: 10px;
                                        width: 10px;
                                        background-color: red;
                                        border-radius: 50%;
                                        display: inline-block;"></span>Lock</td>
                                    <td><a href="#"><i class="fa-solid fa-eye" style=" color: #0E3C6E;"></i>View Details</a></td>
                                    <td><i class="fa-solid fa-pen" style=" color: #0E3C6E;"></i></td>
                                    <td><i class="fa-solid fa-trash" style=" color: red;"></i></td>
                                </tr>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>