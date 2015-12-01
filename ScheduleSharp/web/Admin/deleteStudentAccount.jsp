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
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Schedule Sharp</title>
        
        <!-- Bootstrap CSS files -->
        <link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="./bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
        <link href="./bootstrap/css/styles.css" rel="stylesheet" type="text/css">
        <link href="http://cdn.datatables.net/1.10.4/css/jquery.dataTables.css" rel="stylesheet">
        
        <!-- Custom CSS file -->
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
                    <li>
                        <div id="addSchoolButton" class="navbar-right">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addSchool">Delete Account</button>
                        </div>
                    </li>
                </div>
            </div>
        </nav>

        <div id="addSchool" name="addSchool" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
            <div class="modal-dialog">
                <div class="modal-content2">

                    <div id="search">

                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                                <span class="sr-only">Close</span></button>
                            <h4 class="modal-title" id="myModalLabel">Delete Student Account</h4>
                        </div>

                        <!-- DELETE SCHOOL MODAL -->
                        <form onSubmit="return deleteAcc()" >
                            <div class="modal-dialog-index">
                                <div class="modal-content-index">

                                    <div class="modal-body" align='center'>
                                        <div class="control-group warning">
                                            <label class="control-label" for="StudentID">Student ID: </label>
                                            <div class="controls">
                                                <input type="text" id="StudentID" name="StudentID">
                                                <span class="help-inline"></span>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <button type="submit" class="btn btn-submit btn-lg">Delete Account</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div id="main">     
            <div id="myTabContent" class="tab-content" style="width:95%;margin-left:auto;margin-right:auto">
                <div id="myTabContent" class="tab-content" style="width:95%;margin-left:auto;margin-right:auto">
                    <h3>Delete a Student Account</h3>
                    <div align="center" style="vertical-align:bottom">
                        <div align="center" style="vertical-align:bottom">
                            <table id=myTable" class="table table-striped result">
                                <thead>
                                    <tr>
                                        <th><div align="center">Student ID</div></th>
                                <th><div align="center">First Name</div></th>
                                <th><div align="center">Last Name</div></th>
                                <th><div align="center">User Name</div></th>
                                <th><div align="center">Email</div></th>
                                <th><div align="center">Approved</div></th>
                                <th><div align="center">Delete</div></th>
                                </tr>  
                                </thead>
                                <tbody>
                                    <c:forEach var="i" items="${student}">
                                        <tr>   
                                            <td>${i.getStudentID()}</td>
                                            <td>${i.getFirstName()}</td>
                                            <td>${i.getLastName()}</td>
                                            <td>${i.getUserName()}</td>
                                            <td>${i.getEmail()}</td>
                                            <td>${i.getApproved()}</td>
                                            <td><div align="center"><a href="../DeleteStudentAcc?studentID=${i.getStudentID()}">
                                                        <button type="button" class="btn btn-danger btn-xs">DELETE</button></a>
                                                </div></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
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
            
            <!-- CUSTOM JS FILE(S)-->
            <script src="./bootstrap/js/deleteStudentAccount.js"></script>
    </body>
</html>
