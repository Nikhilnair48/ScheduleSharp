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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MJ
 */
public class AssignedView extends HttpServlet {

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
        int schoolID = stud.getSchoolID();
        String studID = stud.getStudID();
        
        String Query = "SELECT * FROM School WHERE SchoolID=" + schoolID + ";";
        ResultSet rs = DBConnection.ExecQuery(Query);
        try {
            while(rs.next()){
                int Days = rs.getInt("Days");
                int Period = rs.getInt("Period");
                String DaysTable = "";
                for(int i=0; i<Days; i++){
                    switch(i){
                        case 0:
                            DaysTable += "<th class=\"th-sch\">MON</th>";
                            break;
                        case 1:
                            DaysTable += "<th class=\"th-sch\">TUE</th>";
                            break;
                        case 2:
                            DaysTable += "<th class=\"th-sch\">WED</th>";
                            break;
                        case 3:
                            DaysTable += "<th class=\"th-sch\">THUR</th>";
                            break;
                        case 4:
                            DaysTable += "<th class=\"th-sch\">FRI</th>";
                            break;
                        case 5:
                            DaysTable += "<th class=\"th-sch\">SAT</th>";
                            break;
                        case 6:
                            DaysTable += "<th class=\"th-sch\">SUN</th>";
                            break;
                    }
                }
                String PeriodTable = "";
                for(int i=0; i<Period; i++){
                    for(int j=0; j<=Days; j++){
                        String id = j + "_" + (i+1);
                        if(j==0){
                            PeriodTable += "<tr><td class=\"td-sch\">"+ (i+1) +"</td>";
                        }
                        else if(j==Days){
                            PeriodTable += "<td class=\"td-sch\">\n" +
                                "<button href=\"#Schedule\" name=\"Block\" id=\"" + id + "\" data-toggle=\"modal\" data-block-id=\""+ id +"\" class=\"btn btn-sch btn-nosch\">\n" +
                                "</button></td></tr>";
                        }
                        else{
                            PeriodTable += "<td class=\"td-sch\">\n" +
                                "<button href=\"#Schedule\" name=\"Block\" id=\"" + id + "\" data-toggle=\"modal\" data-block-id=\""+ id +"\" class=\"btn btn-sch btn-nosch\">\n" +
                                "</button></td>";
                        }
                    }
                }
               // int Semester = rs.getInt("Semester");
                String Year = request.getParameter("YEAR");
                String CurrentS = request.getParameter("SEMESTER");
                if (Year==null)
                    Year = "2015";
                if(CurrentS==null)
                    CurrentS="FALL";
                String Sem = "";

                switch(CurrentS){
                    case "SPRING":
                        CurrentS = "" + 1;
                        break;
                    case "SUMMER":
                        CurrentS = "" + 2;
                        break;
                    case "FALL":
                        CurrentS = "" + 3;
                        break;
                    case "WINTER":
                        CurrentS = "" + 4;
                        break;
                    }
                    
                String Query2 = "SELECT * FROM SBlock WHERE BStudID=" + studID + " AND BYear=" + Year + " AND BSemester=" + CurrentS;
                ResultSet Block = DBConnection.ExecQuery(Query2);
                String sBlock = "";
                        
                while(Block.next()){
                    String Query4 = "SELECT * FROM AssignedSchedule WHERE AScheduleID=" + Block.getString("BAssignID");
                    ResultSet Ass = DBConnection.ExecQuery(Query4);
                    String BID = Block.getString("BBlockNum");
                    String Color = Block.getString("BColor");
                    while(Ass.next()){
                        String CID = Ass.getString("ACourseID");
                        String CName = Ass.getString("ACourseName");
                        String SesNum = Ass.getString("ASectionID");
                        String InstName = Ass.getString("AInstName");
                        
                        sBlock += "document.getElementById(\""+ BID + "\").style.background = c" + Color + ";";
                        sBlock += "document.getElementById(\""+ BID +"\").innerHTML = \"" +
                                CID + "<br>0" + SesNum + "<br>" + CName + "<br>Instrunctor:<br>" 
                                + InstName + "\";";
                    }
                   }
        
                request.getSession(true).setAttribute("sBlock", sBlock);
                request.getSession(true).setAttribute("Days", DaysTable);
                request.getSession(true).setAttribute("Period", PeriodTable);
                request.getSession(true).setAttribute("Semester", Sem);
                response.sendRedirect("Stud/assignedView.jsp");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AssignedView.class.getName()).log(Level.SEVERE, null, ex);
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
