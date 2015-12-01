<%@page import="JavaCode.admin.DatabaseInfo"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="JavaCode.admin.DatabaseInfo"%>
<%@page import="DbObjects.School"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>Schedule Sharp</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <!-- Bootstrap core CSS -->
        <link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="./bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
        <link href="./bootstrap/css/styles.css" rel="stylesheet" type="text/css">
        <link href="http://cdn.datatables.net/1.10.4/css/jquery.dataTables.css" rel="stylesheet">
        <link href="./bootstrap/css/custom.css" rel="stylesheet">

    </head>

    <body>

        <nav class="navbar navbar-default navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="AdminMain.jsp"><b> Schedule Sharp</b></a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="AdminMain.jsp">Home</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">MANAGE SCHOOL <span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="http://localhost:8080/ScheduleSharp/SelectAllSchools?redirectTo=addSchool">ADD SCHOOL</a></li>
                                <li class="divider"></li>
                                <li><a href="http://localhost:8080/ScheduleSharp/SelectAllSchools?redirectTo=modifySchool">MODIFY SCHOOL</a></li>
                                <li class="divider"></li>
                                <li><a href="http://localhost:8080/ScheduleSharp/SelectAllSchools?redirectTo=deleteSchool">DELETE SCHOOL</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">MANAGE STUDENT <span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="http://localhost:8080/ScheduleSharp/SelectAllStudents?redirectTo=viewStudentAccountRequests">ACCOUNT REQUEST</a></li>
                                <li class="divider"></li>
                                <li><a href="http://localhost:8080/ScheduleSharp/SelectAllStudents?redirectTo=deleteStudentAccount">DELETE ACCOUNT</a></li>
                            </ul>
                        </li>
                    </ul>
                    <li>
                         <a class="navbar-brand" href="../signoutAdmin"><font size="2"><u>SIGN OUT?</u></font></a>
                    </li>
                </div>
            </div>
        </nav>

        <div id="main">
            <div align="center" style="vertical-align:bottom;">
                <div align="center" style="vertical-align:bottom">   
                    <div id="myTabContent" class="tab-content" style="width:70%">
                        <h3>Student Account Requests</h3>
                        <div align="center" style="vertical-align:bottom">
                            <!--<div align="center" style="vertical-align:bottom">-->
                                <table id="accountRequestsTable" class="table table-striped result">
                                    <thead>
                                        <tr>
                                            <th><div align="center"><input type="checkbox" name="approve" class="selectall"/></div></th>
                                    <th><div align="center">Student ID</div></th>
                                    <th><div align="center">User Name</div></th>
                                    <th><div align="center">First Name</div></th>
                                    <th><div align="center">Last Name</div></th>
                                    <th><div align="center">Email</div></th>
                                    <th><div align="center">Approved</div></th>
                                    <th><div align="center">
                                        <button id="acceptSelected" type="button" class="btn btn-warning btn-xs">ACCEPT Checked</button>
                                    </div></th>
                                    <th><div align="center">
                                        <button id="rejectSelected" type="button" class="btn btn-danger btn-xs">DELETE Checked</button>
                                    </div></th>
                                    </tr>  
                                    </thead>
                                    <tbody>
                                        <c:forEach var="i" items="${student}">
                                            <tr id="${i.getStudentID()}">   
                                                <td><div align="center"><input type="checkbox" name="approve"/></div></td>
                                                <td>${i.getStudentID()}</td>
                                                <td>${i.getUserName()}</td>
                                                <td>${i.getFirstName()}</td>
                                                <td>${i.getLastName()}</td>
                                                <td>${i.getEmail()}</td>
                                                <td>${i.getApproved()}</td>
                                                <td><div align="center"><a href="../AcceptStudent?studentID=${i.getStudentID()}">
                                                            <button type="button" class="btn btn-danger btn-xs">ACCEPT</button></a>
                                                    </div></td>
                                                <td><div align="center"><a href="../RejectStudent?studentID=${i.getStudentID()}">
                                                            <button type="button" class="btn btn-danger btn-xs">REJECT</button></a>
                                                    </div></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            <!--</div>-->
                        </div> 
                    </div>
                </div>
            </div>
        </div>

        <!-- script references -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="./bootstrap/js/bootstrap.min.js"></script>
        <script src="./bootstrap/js/docs.min.js"></script>
        <script src="http://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        
        <!-- CUSTOM JS FILE-->
        <script src="./bootstrap/js/viewStudentAccountRequests.js"></script>

    </body>
</html>
