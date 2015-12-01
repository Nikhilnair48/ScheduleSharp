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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sizpl_000
 */
public class showRequestFriend extends HttpServlet {

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

        StudentBean stud = (StudentBean) request.getSession(true).getAttribute("currentSessionUser"); //get user
        String Mid = stud.getStudID(); //get user id
        String Query = "SELECT * FROM Friend where (Student2ID='" + Mid + "' and request=0)";
       
        ResultSet rs = DBConnection.ExecQuery(Query);
        String fRelist = "";

        try {
            while (rs.next()) {
                String id2 = rs.getString("Student1ID"); //friend ID
                String query2 = "SELECT * FROM Student Where StudentID='" + id2 + "'";
                ResultSet rs2 = DBConnection.ExecQuery(query2);
                while (rs2.next()) {

                    fRelist += "<tr><td><div align=\"center\"><input type=\"checkbox\" name=\"approve[]\"/></div></td>\n"
                            + "<td><div align=\"center\">" + rs2.getString("UserName") + "</div></td>\n"
                            + "<td><div align=\"center\">" + rs2.getString("FirstName") + "</div></td>\n"
                            + "<td><div align=\"center\">" + rs2.getString("LastName") + "</div></td>\n"
                            + "<td><div align=\"center\">" + rs2.getString("Email") + "</div></td>\n"
                            + "<td><div align=\"center\"><a href=\"../acceptFriend?id=" + rs2.getString("StudentID") + "\">\n"
                            + "<button type=\"button\" class=\"btn btn-danger btn-xs\">ACCEPT</button></a>\n"
                            + "</div></td>\n"
                            + "<td><div align=\"center\"><a href=\"../deleteFriend?id=" + rs2.getString("StudentID") + "\">\n"
                            + "<button type=\"button\" class=\"btn btn-danger btn-xs\">DELETE</button></a>\n"
                            + "</div></td></tr>";
                }
            }
            request.getSession(true).setAttribute("fRelist", fRelist);           
            response.sendRedirect("Stud/viewRequestFriend.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(showRequestFriend.class.getName()).log(Level.SEVERE, null, ex);
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
