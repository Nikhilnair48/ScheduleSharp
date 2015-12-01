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
        <link href="./bootstrap/css/custom.css" rel="stylesheet" type="text/css">
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
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addSchool">Add School</button>
                        </div>
                    </li>
                </div>
            </div>
        </nav>

        <div id="confirmation" name="confirmation" class="modal fade" tabindex="-1" data-backdrop="false" role="dialog">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 id="title" name="title" class="modal-title">Title</h4>
            </div>
            <div id="body" name="body" class="modal-body">
                <p>My modal content</p>
            </div>
            <div class="modal-footer">
                <button class="btn" data-dismiss="modal">Close</button>
            </div>
        </div>
        
        <div id="main">
            <div id="myTabContent" class="tab-content" style="width:95%;margin-left:auto;margin-right:auto">
                <div align="center" style="vertical-align:bottom">
                    <div align="center" style="vertical-align:bottom">
                        <div align="center" style="vertical-align:bottom">
                            <div align="center" style="vertical-align:bottom">
                                <table class="table table-striped result">
                                    <thead>
                                        <tr>
                                            <th><div align="center">School ID</div></th>
                                    <th><div align="center">School Name</div></th>
                                    <th><div align="center">Total Semester</div></th>
                                    <th><div align="center">Total Days</div></th>
                                    <th><div align="center">Total Period</div></th>
                                    <th><div align="center">Lunch Range</div></th>

                                    </tr>  
                                    </thead>
                                    <tbody>
                                        <c:forEach var="i" items="${s}">
                                            <tr><!-- DYNAMICALLY DISPLAY THE SCHOOLS FROM THE SESSION SCOPE -- SET IN SelectAllSchools servlet -->
                                                <td>${i.getSchoolID()}</td>
                                                <td>${i.getSchoolName()}</td>
                                                <td>${i.getSemester()}</td>
                                                <td>${i.getDays()}</td>
                                                <td>${i.getPeriod()}</td>
                                                <td>${i.getRangeLunch()}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div> 
                    </div>
                </div>
            </div>
        </div>

        <!-- ADD SCHOOL MODAL BEGIN -->
        <div id="addSchool" name="dialog-ok" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
            <div class="modal-dialog modal-content-index">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                        <span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">Add School</h4>
                </div>

                <form class="form-horizontal form-inline" role="form" onSubmit="return sendDataToServlet()">
                    <div class="modal-body" align='center'>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">School Name: </label>
                            <div class="controls col-sm-6">
                                <input type="text" class="form-control" id="SchoolName" name="SchoolName" onBlur="validateSchoolName()">
                            </div>
                        </div>
                        <br>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Semester(s): </label>
                            <div class="col-sm-8">
                                <select id="Semester" name="Semester" class="form-control" onBlur="validateSemester()">
                                </select>
                            </div>
                        </div>
                        <br><br>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">Days Per Week: </label>
                            <div class="col-sm-2">
                                <select id="Days" name="Days" class="form-control" onBlur="validateDays()">
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Total Periods: </label>
                            <div class="col-sm-8">
                                <select id="Period" name="Period" class="form-control" onBlur="validatePeriods()">
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">Lunch Range: </label>
                            <div class="col-sm-8">
                                <select id="RangeLunch1" name="RangeLunch1"  class="form-control" style="width:50px" onBlur="validateLunchRange1()">
                                </select>
                                <span> To </span>
                                <select id="RangeLunch2" name="RangeLunch2"  class="form-control" style="width:50px" onBlur="validateLunchRange2()">
                                </select>
                            </div>
                        </div>
                        <div>
                            <span style="color: red" id ="schoolMessage"> </span>
                            <span style="color: red" id ="semesterMessage"> </span>
                            <span style="color: red" id ="daysMessage"> </span>
                            <span style="color: red" id ="periodsMessage"> </span>
                            <span style="color: red" id ="rangeLunch1Message"> </span>
                            <span style="color: red" id ="rangeLunch2Message"> </span>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="submit" id="addDialogButton" class="btn btn-default">Next</button>
                    </div>

                </form>
            </div>
        </div>
        <!-- ADD SCHOOL MODAL END -->

        <div id="dialog2" name="dialog2" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
            <div class="modal-dialog modal-content-index">
                <div class="modal-header">
                    <h4 class="modal-title">Please enter the schedule blocks (Eg. "Period 1; Day 1, 2" - 1;1,2)</h4>
                </div>
                <div class="modal-body" align="center">
                    <div class="input_fields_wrap">
                        <button class="add_field_button">Add More Fields</button>
                        <!--<div><input type="text" name="mytext[]"></div>-->
                    </div>
                </div>
                <div> <span style="color: red" id ="blockMessage"> </span></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" id="test">OK</button> 
                    <button type="button" class="btn btn-default" id="close" data-dismiss="modal">CLOSE</button>
                </div>
            </div>
        </div>

        <!-- script references -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="./bootstrap/js/bootstrap.min.js"></script>
        <script src="./bootstrap/js/docs.min.js"></script>
        <script src="http://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>

        <!-- CUSTOM JS FILE(S)-->
        <script src="./bootstrap/js/addSchool.js"></script>
    </body>
</html>
