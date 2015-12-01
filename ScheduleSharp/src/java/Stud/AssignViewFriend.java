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
@WebServlet(name = "AssignViewFriend", urlPatterns = {"/AssignViewFriend"})
public class AssignViewFriend extends HttpServlet {

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
        String Year = request.getParameter("YEARDrop");
        String Semester = request.getParameter("SEMESTERDrop");
        String Friend = request.getParameter("fList");
        String current = Year + "/" + Semester + "  SCHEDULE FOR " + stud.getUsername();
        String sBlock = "";
        String DaysTable = "";
        String PeriodTable = "";
        String CurrentS = "";
        
        if(Friend.equals("NOFriend")){
            String Query = "SELECT * FROM School WHERE SchoolID=" + schoolID + ";";
            ResultSet rs = DBConnection.ExecQuery(Query);
            try {
                while(rs.next()){
                    int Days = rs.getInt("Days");
                    int Period = rs.getInt("Period");
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
                    for(int i=0; i<Period; i++){
                        for(int j=0; j<=Days; j++){
                            String id = j + "_" + (i+1);
                            if(j==0){
                                PeriodTable += "<tr><td class=\"td-sch\">"+ (i+1) +"</td>";
                            }
                            else if(j==Days){
                                PeriodTable += "<td class=\"td-sch\">\n" +
                                    "<button name=\"Block\" id=\"" + id + "\" class=\"btn btn-sch btn-nosch\">\n" +
                                    "</button></td></tr>";
                            }
                            else{
                                PeriodTable += "<td class=\"td-sch\">\n" +
                                    "<button name=\"Block\" id=\"" + id + "\" class=\"btn btn-sch btn-nosch\">\n" +
                                    "</button></td>";
                            }
                        }
                    }
                    switch(Semester){
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
                }
            } catch (SQLException ex) {
                Logger.getLogger(AssignedView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            current += " AND " + Friend;
            
            String Query = "SELECT * FROM School WHERE SchoolID=" + schoolID + ";";
            ResultSet rs = DBConnection.ExecQuery(Query);
            try {
                while(rs.next()){
                    int Days = rs.getInt("Days");
                    int Period = rs.getInt("Period");
                    for(int i=0; i<Days; i++){
                        switch(i){
                            case 0:
                                DaysTable += "<th class=\"th-sch\">MON</th><th class=\"th-sch\"><font color=\"orange\">MON</font></th>";
                                break;
                            case 1:
                                DaysTable += "<th class=\"th-sch\">TUE</th><th class=\"th-sch\"><font color=\"orange\">TUE</font></th>";
                                break;
                            case 2:
                                DaysTable += "<th class=\"th-sch\">WED</th><th class=\"th-sch\"><font color=\"orange\">WED</font></th>";
                                break;
                            case 3:
                                DaysTable += "<th class=\"th-sch\">THUR</th><th class=\"th-sch\"><font color=\"orange\">THUR</font></th>";
                                break;
                            case 4:
                                DaysTable += "<th class=\"th-sch\">FRI</th><th class=\"th-sch\"><font color=\"orange\">FRI</font></th>";
                                break;
                            case 5:
                                DaysTable += "<th class=\"th-sch\">SAT</th><th class=\"th-sch\"><font color=\"orange\">SAT</font></th>";
                                break;
                            case 6:
                                DaysTable += "<th class=\"th-sch\">SUN</th><th class=\"th-sch\"><font color=\"orange\">SUN</font></th>";
                                break;
                        }
                    }
                    for(int i=0; i<Period; i++){
                        for(int j=0; j<=Days; j++){
                            String id = j + "_" + (i+1);
                            if(j==0){
                                PeriodTable += "<tr><td class=\"td-sch\">"+ (i+1) +"</td>";
                            }
                            else if(j==Days){
                                 PeriodTable += "<td class=\"td-sch\">\n" +
                                    "<button name=\"Block\" id=\"" + id + "\" class=\"btn btn-sch2 btn-nosch\">\n" +
                                    "</button></td>";
                                PeriodTable += "<td class=\"td-sch\">\n" +
                                    "<button name=\"Block\" id=\"f_" + id + "\" class=\"btn btn-sch2 btn-nosch\">\n" +
                                    "</button></td></tr>";
                            }
                            else{
                                 PeriodTable += "<td class=\"td-sch\">\n" +
                                    "<button name=\"Block\" id=\"" + id + "\" class=\"btn btn-sch2 btn-nosch\">\n" +
                                    "</button></td>";
                                PeriodTable += "<td class=\"td-sch\">\n" +
                                    "<button name=\"Block\" id=\"f_" + id + "\" class=\"btn btn-sch2 btn-nosch\">\n" +
                                    "</button></td>";
                            }
                        }
                    }
                    switch(Semester){
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

                    while(Block.next()){
                        String Query4 = "SELECT * FROM AssignedSchedule WHERE AScheduleID=" + Block.getString("BAssignID");
                        ResultSet Ass = DBConnection.ExecQuery(Query4);
                        String BID = Block.getString("BBlockNum");
                        String Color = Block.getString("BColor");
                        while(Ass.next()){
                            String CID = Ass.getString("ACourseID");
                            String CName = Ass.getString("ACourseName");
                            String SesNum = Ass.getString("ASectionID");

                            sBlock += "document.getElementById(\""+ BID + "\").style.background = c" + Color + ";";
                            sBlock += "document.getElementById(\""+ BID +"\").innerHTML = \"" +
                                    CID + "<br>0" + SesNum + "<br>" + CName + "\";";
                        }
                    }
                    
                    String Query3 = "SELECT * FROM Student WHERE UserName=\"" + Friend + "\"";
                    ResultSet getFriend = DBConnection.ExecQuery(Query3);
                    while(getFriend.next()){
                        String FID = getFriend.getString("StudentID");
                        
                        String Query4 = "SELECT * FROM SBlock WHERE BStudID=" + FID + " AND BYear=" + Year + " AND BSemester=" + CurrentS;
                        ResultSet FBlock = DBConnection.ExecQuery(Query4);

                        while(FBlock.next()){
                            String Query5 = "SELECT * FROM AssignedSchedule WHERE AScheduleID=" + FBlock.getString("BAssignID");
                            ResultSet Ass = DBConnection.ExecQuery(Query5);
                            String BID = FBlock.getString("BBlockNum");
                            String Color = FBlock.getString("BColor");
                            while(Ass.next()){
                                String CID = Ass.getString("ACourseID");
                                String CName = Ass.getString("ACourseName");
                                String SesNum = Ass.getString("ASectionID");

                                sBlock += "document.getElementById(\"f_"+ BID + "\").style.background = c" + Color + ";";
                                sBlock += "document.getElementById(\"f_"+ BID +"\").innerHTML = \"" +
                                        CID + "<br>0" + SesNum + "<br>" + CName + "\";";
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(AssignedView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        request.getSession(true).setAttribute("Days", DaysTable);
        request.getSession(true).setAttribute("Period", PeriodTable);
        request.getSession(true).setAttribute("sBlock", sBlock);
        request.getSession(true).setAttribute("current", current);
        response.sendRedirect("Stud/assignedSearch.jsp");
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
