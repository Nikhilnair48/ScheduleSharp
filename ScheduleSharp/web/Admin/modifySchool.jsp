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
        <link href="./bootstrap/css/bootstrap.css" rel="stylesheet">
        <link href="./bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
        <link href="http://cdn.datatables.net/1.10.4/css/jquery.dataTables.css" rel="stylesheet">
        
        <!-- Custom css file -->
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
            <div align="center" style="vertical-align:bottom">
                <div align="center" style="vertical-align:bottom">
                    <div id="myTabContent" class="tab-content" style="width:95%">
                        <h3>Modify School</h3>
                        <!--<button type="button" class="btn-lg btn-submit" data-toggle="modal" data-target="#static">
                            Modify Schedule Block
                        </button>-->
                        <div align="center" style="vertical-align:bottom">
                            <div align="center" style="vertical-align:bottom">
                                <table id="newtable" class="table table-striped result">
                                    <thead>
                                        <tr>
                                            <th><div align="center">School ID</div></th>
                                    <th><div align="center">School Name</div></th>
                                    <th><div align="center">Total Semester</div></th>
                                    <th><div align="center">Total Days</div></th>
                                    <th><div align="center">Total Period</div></th>
                                    <th><div align="center">Lunch Range</div></th>
                                    <th><div align="center">Modify</div></th>
                                    </tr>  
                                    </thead>
                                    <tbody>
                                        <c:forEach var="i" items="${s}">
                                            <tr id="${i.getSchoolID()}">      
                                                <td>${i.getSchoolID()}</td>
                                                <td>${i.getSchoolName()}</td>
                                                <td>${i.getSemester()}</td>
                                                <td>${i.getDays()}</td>
                                                <td>${i.getPeriod()}</td>
                                                <td>${i.getRangeLunch()}</td>
                                                <td><button id="modifyButton" name="modifyButton" type="button" class="mybutton btn btn-danger btn-xs">MODIFY</button> </td>
                                                <!--<td><div align="center">
                                                        <button id="modifyButton" name="modify" type="button" class="btn btn-danger btn-xs">MODIFY</button>
                                                    </div></td>
                                                <td> <button id="modifyButton" name="modify" type="submit" style="" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#editModal" >MODIFY</button>
                                                </td>-->
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

        <div class="modal fade" id="static" name="static" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="myModalLabel">Editing Schedule Blocks </h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Enter school ID:</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="schoolIDBlocks" id="schoolIDBlocks" value=""/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="schoolIDForBlocks" name="changeButton" class="btn btn-primary">Get Blocks</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> 
                    </div>
                </div>
            </div>
        </div>

        <!-- EditModal -->
        <div class="modal fade" id="editModal" name="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="myModalLabel">Editing Information </h4>
                    </div>
                    <form id="updateInformation" method="post" class="form-horizontal" action="modifySchool.jsp">
                        <div class="modal-body">
                            <!-- The form is placed inside the body of modal -->
                            <input type="hidden" id="schoolID" name="schoolID" value=""> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label">School Name</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" name="SchoolName" id="SchoolName" value="" onBlur="validateSchoolName()"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Semester</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" name="Semester" id="Semester" value=""/>
                                </div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Days</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" name="Days" id="Days" value=""/>
                                </div>
                            </div> 
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Period</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" name="Period" id="Period" value=""/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Lunch Range</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" id="LunchRange" name="LunchRange" value=""/>
                                </div>
                            </div>
                        </div> 
                        <div class="col-sm-5"><span style="color: red" id ="schoolMessage"></span></div>
                        <div class="modal-footer">
                            <button type="button" id="changeButton" name="changeButton" class="btn btn-primary">Change</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> 
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- EditModal -->



        <!-- script references -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="./bootstrap/js/bootstrap.min.js"></script>
        <script src="./bootstrap/js/docs.min.js"></script>
        <script src="http://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
        
        <!-- CUSTOM JS FILE(S)-->
        <script src="./bootstrap/js/modifySchool.js"></script>
    </body>
</html>