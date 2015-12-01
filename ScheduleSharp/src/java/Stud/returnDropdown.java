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
public class returnDropdown extends HttpServlet {

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
        String Sem = "";
        String Query = "SELECT * FROM School WHERE SchoolID=" + schoolID + ";";
        ResultSet rs = DBConnection.ExecQuery(Query);
        String Friend = "SELECT * FROM Friend WHERE Student1ID=" + studID + " OR Student2ID=" + studID + " AND request=1";
        ResultSet rs2 = DBConnection.ExecQuery(Friend);
        String fList = "<select id=\"fList\" name=\"fList\" class=\"form-control\">\n"+
                        "<option value=\"NOFriend\">View Without Friend's Schedule</option>\n";
        try {
            while(rs.next()){
                int Semester = rs.getInt("Semester");

                switch(Semester){
                        case 1:
                            Sem += "<input id=\"SEMESTERDrop\" name=\"SEMESTERDrop\" type=\"text\" class=\"form-control\" placeholder=\"SEMESTER\" value=\"1\">";
                            break;
                        case 2:
                            Sem += "<select id=\"SEMESTERDrop\" name=\"SEMESTERDrop\" class=\"form-control\">\n"+
                                    "<option value=\"SPRING\">SPRING</option>\n" +
                                    "<option value=\"FALL\">FALL</option>\n" +
                                    "</select>";  
                            break;
                        case 3:
                            Sem += "<select id=\"SEMESTERDrop\" name=\"SEMESTERDrop\" class=\"form-control\">\n"+
                                    "<option value=\"SPRING\">SPRING</option>\n" +
                                    "<option value=\"SUMMER\">SUMMER</option>\n" +
                                    "<option value=\"FALL\">FALL</option>\n" +
                                    "</select>";
                            break;
                        case 4:
                            Sem += "<select id=\"SEMESTERDrop\" name=\"SEMESTERDrop\" class=\"form-control\">\n"+
                                    "<option value=\"SPRING\">SPRING</option>\n" +
                                    "<option value=\"SUMMER\">SUMMER</option>\n" +
                                    "<option value=\"FALL\">FALL</option>\n" +
                                    "<option value=\"WINTER\">WINTER</option>\n" +
                                    "</select>";    
                            break;
                }
            }
            while(rs2.next()){
                String f1 = rs2.getString("Student1ID");
                String f2 = rs2.getString("Student2ID");
                String user2;
                if(f1.equals(studID)){
                    String f = "SELECT * FROM Student WHERE StudentID=" + f2;
                    ResultSet ff1 = DBConnection.ExecQuery(f);
                    while(ff1.next()){
                        user2 = ff1.getString("UserName");
                        fList += "<option value=\"" + user2 + "\">" + user2 + "</option>\n";
                    }
                }
                else{
                    String f = "SELECT * FROM Student WHERE StudentID=" + f1;
                    ResultSet ff1 = DBConnection.ExecQuery(f);
                    while(ff1.next()){
                        user2 = ff1.getString("UserName");
                        fList += "<option value=\"" + user2 + "\">" + user2 + "</option>\n";
                    }
                }
            }
            fList += "</select>"; 
            

            request.getSession(true).setAttribute("fList", fList);
            request.getSession(true).setAttribute("Semester", Sem);
            response.sendRedirect("Stud/assignedSearch.jsp");
            
        } catch (SQLException ex) {
            Logger.getLogger(returnDropdown.class.getName()).log(Level.SEVERE, null, ex);
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
