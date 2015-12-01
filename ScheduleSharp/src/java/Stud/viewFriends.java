/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stud;

import System.StudentBean;
import DBWorks.DBConnection;
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
public class viewFriends extends HttpServlet {

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
        String id = stud.getStudID();
        String Query = "SELECT * FROM Friend where (Student1ID=" + id + " or Student2ID=" + id + ") and request=1;";
        
        ResultSet rs = DBConnection.ExecQuery(Query);
        String fList = "";
        try {
            while(rs.next()){
                String id1 = rs.getString("Student1ID");
                String id2 = rs.getString("Student2ID");
                ResultSet rs2;
                if(id.equals(id1)){
                    String Q2 = "SELECT * FROM Student WHERE StudentID=" + id2;
                    rs.getString("Student1ID");
                    rs2 = DBConnection.ExecQuery(Q2);
                }
                else{
                    String Q2 = "SELECT * FROM Student WHERE StudentID=" + id1;
                    rs2 = DBConnection.ExecQuery(Q2);
                }
                while(rs2.next()){
                    fList += "<tr><td><div align=\"center\"><input type=\"checkbox\" name=\"approve[]\"/></div></td>\n" +
                             "<td><div align=\"center\">" + rs2.getString("StudentID") + "</div></td>\n" +
                             "<td><div align=\"center\">" + rs2.getString("UserName") + "</div></td>\n" +
                             "<td><div align=\"center\">" + rs2.getString("FirstName") + "</div></td>\n" +
                             "<td><div align=\"center\">" + rs2.getString("LastName") + "</div></td>\n" +
                             "<td><div align=\"center\">" + rs2.getString("Email") + "</div></td>\n" +
                             "<td><div align=\"center\"><a href=\"../deleteFriend?id=" + rs2.getString("StudentID") + "\">\n" +
                             "<button type=\"button\" class=\"btn btn-danger btn-xs\">DELETE</button></a>\n" +
                             "</div></td></tr>";
                }
            }

            request.getSession(true).setAttribute("fList", fList);
            response.sendRedirect("Stud/viewFriend.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(viewFriends.class.getName()).log(Level.SEVERE, null, ex);
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
