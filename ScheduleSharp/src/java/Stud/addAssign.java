/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stud;

import DBWorks.DBConnection;
import System.StudentBean;
import static java.awt.SystemColor.window;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MJ
 */
public class addAssign extends HttpServlet {

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
        String CName = request.getParameter("CourseName");
        String InName = request.getParameter("InstName");
        String p1 = request.getParameter("p1");
        String p2 = request.getParameter("p2");
        String d1 = request.getParameter("d1");
        String d2 = request.getParameter("d2");
        
                
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
        //for SBlock table
        String BlockID = request.getParameter("BlockID");
        
        //for AssingedSchedule table
        String StudID = stud.getStudID();
        String getAssign = "";
        
        if(CName!=null && InName!=null){
            String b1 = d1 + "_" + p1;
            String b2 = d2 + "_" + p2;
            String ABlocks = "";
            if(b1.length()==3){
                ABlocks += b1;
            }
            if(b2.length()==3){
                if(ABlocks.length()==3){
                    ABlocks += "," + b2;
                }
                else{
                    ABlocks += b2;
                }
            }
            if(ABlocks.length()>=3){
                String ass = "INSERT INTO `AssignedSchedule` VALUES (null,"+
                        SchoolID + ",\"" + CourseID + "\"," + SectionID + ",\"" + 
                        CName + "\",\"" + InName + "\",\"" + ABlocks + "\"," + Year +
                        "," + Semester + ")";
                DBConnection.ExecUpdateQuery(ass);
            }
        }

        
        if(BlockID==null){
            getAssign = "SELECT * FROM AssignedSchedule WHERE ASchoolID=" + SchoolID +
                    " AND AYear=" + Year + " AND ASemester=" + Semester + 
                    " AND ACourseID=\"" + CourseID + "\" AND ASectionID=" + SectionID;
        }
        else{
            getAssign = "SELECT * FROM AssignedSchedule WHERE ASchoolID=" + SchoolID +
                    " AND AYear=" + Year + " AND ASemester=" + Semester + 
                    " AND ACourseID=\"" + CourseID + "\" AND ASectionID=" + SectionID + " AND "+
                    "ABlocks LIKE \"%" + BlockID + "%\"";
        }
        
        ResultSet AddCourse = DBConnection.ExecQuery(getAssign);
        int flag = 0;
        try{
            while(AddCourse.next()){
                flag++;
                String [] Blocks = AddCourse.getString("ABlocks").split(",");
                Random rand = new Random();
                int Color = rand.nextInt(5)+1;
                for(int i=0; i<Blocks.length; i++){
                    String b = Blocks[i];
                    String getClass = "SELECT * FROM SBlock WHERE BStudID=" + StudID + 
                                " AND BYear=" + Year + " AND BSemester=" + Semester + " AND BBlockNum=\""+
                                b + "\"";
                    ResultSet hasClass = DBConnection.ExecQuery(getClass);
                    while(hasClass.next()){
                        String AssAssID = hasClass.getString("BAssignID");
                        
                        String finalDel = "DELETE FROM SBlock WHERE BAssignID=" + AssAssID;
                        DBConnection.ExecUpdateQuery(finalDel);
                    }
                    String AssID = AddCourse.getString("AScheduleID");
                    String SBlockT = "INSERT INTO SBlock VALUES (null,"+ StudID + "," + 
                                AssID + ",'"+ b +"'," + Year + "," + Semester + ","+ Color + ")";
                    DBConnection.ExecUpdateQuery(SBlockT);
                }
                
                String CourseName = AddCourse.getString("ACourseName");
                String CourseT = "INSERT INTO `Course` VALUES ('" + CourseID + "','" +
                                CourseName + "'," + SchoolID + ")";
                DBConnection.ExecUpdateQuery(CourseT);
                
                String InstName = AddCourse.getString("AInstName");
                String InstT = "INSERT INTO `Instructor` VALUES ('" + InstName + "'," +
                                SchoolID + ",'" + CourseID + "', " +SectionID + ")";
                DBConnection.ExecUpdateQuery(InstT);
                
                String SectionT = "INSERT INTO `Section` VALUES (" +SectionID + ",'" + CourseID + "'," +
                                SchoolID + "," + Year + ","+ Semester +",'" + InstName + "')";
                DBConnection.ExecUpdateQuery(SectionT);
            }
        } catch (SQLException ex) {
            Logger.getLogger(addAssign.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(flag==0){
            PrintWriter out = response.getWriter(); 
            out.print("new");
        }
        else{
            response.sendRedirect("Stud/assignedView.jsp");
        }
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
