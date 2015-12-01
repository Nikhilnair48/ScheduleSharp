/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stud;

import DBWorks.DBConnection;
import System.StudentBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MJ
 */
@WebServlet(name = "AssignedAdditional", urlPatterns = {"/AssignedAdditional"})
public class AssignedAdditional extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            StudentBean stud = (StudentBean) request.getSession(true).getAttribute("currentSessionUser");
            
            //for Course table
            int SchoolID = stud.getSchoolID();
            String CourseID = request.getParameter("CourseID").toUpperCase();
            //for Secntion table
            String SectionID = request.getParameter("SectionNum");
            String Year = request.getParameter("YEAR");
            String Semester = request.getParameter("SEMESTER");
            String BlockID = request.getParameter("BlockID");
            
            switch(Semester){
                case "SPRING":
                    Semester = "" + 1;
                    break;
                case "SUMMER":
                    Semester = "" + 2;
                    break;
                case "FALL":
                    Semester = "" + 3;
                    break;
                case "WINTER":
                    Semester = "" + 4;
                    break;
            }
            String Query = "SELECT * FROM School WHERE SchoolID=" + SchoolID + ";";
            ResultSet rs = DBConnection.ExecQuery(Query);
            String blockOption = "";
            String sectionOption = "";
            String courseOption = "";
            try {
                while(rs.next()){
                    int Days = rs.getInt("Days");
                    int Period = rs.getInt("Period");
                    blockOption += "<tr><td><b>TIME OPTION #1</b></td>";
                    blockOption += "<td><select id=\"dOption1\" name=\"dOption1\">";
                    blockOption += "<option value=\"noSel\">Select Day</option>";
                    for(int i=0; i<Days; i++){
                        switch(i){
                            case 0:
                                blockOption += "<option value=\"1\">MON</option>";
                                break;
                            case 1:
                                blockOption += "<option value=\"2\">TUE</option>";
                                break;
                            case 2:
                                blockOption += "<option value=\"3\">WED</option>";
                                break;
                            case 3:
                                blockOption += "<option value=\"4\">THU</option>";
                                break;
                            case 4:
                                blockOption += "<option value=\"5\">FRI</option>";
                                break;
                            case 5:
                                blockOption += "<option value=\"6\">SAT</option>";
                                break;
                            case 6:
                                blockOption += "<option value=\"7\">SUN</option>";
                                break;
                        }
                    }
                    blockOption += "</select>&nbsp;&nbsp;<select id=\"pOption1\" name=\"pOption1\">";
                    blockOption += "<option value=\"noSel\">Select Period</option>";
                    for(int i=1; i<=Period; i++){
                        blockOption += "<option value=\"" + i + "\">" + i + "</option>";
                    }
                    blockOption += "</select></td>";
                    
                    blockOption += "<td><b>TIME OPTION #2</b></td>";
                    blockOption += "<td><select id=\"dOption2\" name=\"dOption2\">";
                    blockOption += "<option value=\"noSel\">Select Day</option>";
                    for(int i=0; i<Days; i++){
                        switch(i){
                            case 0:
                                blockOption += "<option value=\"1\">MON</option>";
                                break;
                            case 1:
                                blockOption += "<option value=\"2\">TUE</option>";
                                break;
                            case 2:
                                blockOption += "<option value=\"3\">WED</option>";
                                break;
                            case 3:
                                blockOption += "<option value=\"4\">THU</option>";
                                break;
                            case 4:
                                blockOption += "<option value=\"5\">FRI</option>";
                                break;
                            case 5:
                                blockOption += "<option value=\"6\">SAT</option>";
                                break;
                            case 6:
                                blockOption += "<option value=\"7\">SUN</option>";
                                break;
                        }
                    }
                    blockOption += "</select>&nbsp;&nbsp;<select id=\"pOption2\" name=\"pOption2\">";
                    blockOption += "<option value=\"noSel\">Select Period</option>";
                    for(int i=1; i<=Period; i++){
                        blockOption += "<option value=\"" + i + "\">" + i + "</option>";
                    }
                    blockOption += "</select></td><tr>";
                }

                String getAssign = "SELECT * FROM AssignedSchedule WHERE ASchoolID=" + SchoolID +
                " AND AYear=" + Year + " AND ASemester=" + Semester + 
                " AND ACourseID=\"" + CourseID + "\"";
                ResultSet AddCourse = DBConnection.ExecQuery(getAssign);
                
                while(AddCourse.next()){
                    String ac = AddCourse.getString("ACourseID");
                    String as = AddCourse.getString("ASectionID");
                    sectionOption += "<tr>";
                    sectionOption += "<td><div align=\"center\"><b>" + ac + "</b></div></td>";
                    sectionOption += "<td><div align=\"center\"><b>" + as + "</b></div></td>";
                    sectionOption += "<td><div align=\"center\"><b>" + AddCourse.getString("ACourseName") + "</b></div></td>";
                    sectionOption += "<td><div align=\"center\"><b>" + AddCourse.getString("AInstName") + "</b></div></td>";
                    sectionOption += "<td><div align=\"center\"><b>" + AddCourse.getString("ABlocks") + "</b></div></td>";
                    sectionOption += "<td><div align=\"center\"><a href=\"http://localhost:8080/ScheduleSharp/addAssign?CourseID=" + ac + "&SectionNum="+
                            as + "&YEAR=" + Year + "&SEMESTER=" + Semester + "\">" +
                            "<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"javascript:window.close()\">"+
                            "Add This Course</button></a></div></td></tr>";  
                }
                
                
                String getAssign2 = "SELECT * FROM AssignedSchedule WHERE ASchoolID=" + SchoolID +
                    " AND AYear=" + Year + " AND ASemester=" + Semester + 
                    " AND ABlocks LIKE \"%" + BlockID + "%\"";
                ResultSet AddBlock = DBConnection.ExecQuery(getAssign2);
                while(AddBlock.next()){
                    String ac = AddBlock.getString("ACourseID");
                    String as = AddBlock.getString("ASectionID");
                    courseOption += "<tr>";
                    courseOption += "<td><div align=\"center\"><b>" + ac + "</b></div></td>";
                    courseOption += "<td><div align=\"center\"><b>" + as + "</b></div></td>";
                    courseOption += "<td><div align=\"center\"><b>" + AddBlock.getString("ACourseName") + "</b></div></td>";
                    courseOption += "<td><div align=\"center\"><b>" + AddBlock.getString("AInstName") + "</b></div></td>";
                    courseOption += "<td><div align=\"center\"><b>" + AddBlock.getString("ABlocks") + "</b></div></td>";
                    courseOption += "<td><div align=\"center\"><a href=\"http://localhost:8080/ScheduleSharp/addAssign?CourseID=" + ac + "&SectionNum="+
                            as + "&YEAR=" + Year + "&SEMESTER=" + Semester + "\">" +
                            "<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"javascript:window.close()\">"+
                            "Add This Course</button></a></div></td></tr>";  
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(AssignedAdditional.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getSession(true).setAttribute("CourseID", CourseID);
            request.getSession(true).setAttribute("SectionID", SectionID);
            request.getSession(true).setAttribute("courseOption", courseOption);
            request.getSession(true).setAttribute("sectionOption", sectionOption);
            request.getSession(true).setAttribute("blockOption", blockOption);
            response.sendRedirect("Stud/assignedAdditional.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
