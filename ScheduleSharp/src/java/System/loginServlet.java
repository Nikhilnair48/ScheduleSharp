/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import DBWorks.DBConnection;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author sizpl_000
 */
@WebServlet(name = "loginServlet", urlPatterns = {"/loginServlet"})
public class loginServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try {

            String username = request.getParameter("id");
            String password = request.getParameter("pw");

            StudentBean stud = new StudentBean();
            stud.setUserName(username);
            stud.setPassword(password);
            int rs3 = 0;
            String searchQuery
                    = "select * from Student where UserName='" + username + "' AND PassWord='" + password + "'";
            ResultSet rs = DBConnection.ExecQuery(searchQuery);
            if (rs.next()) {

                String StudentID = rs.getString("StudentID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                int approved = rs.getInt("Approved");
                int schoolID = rs.getInt("SSchoolID");
                int singleLogin = rs.getInt("SingleLogin");

                if (approved == 1 && singleLogin == 0) {
                    stud.setFirstName(firstName);
                    stud.setLastName(lastName);
                    stud.setValid(true);
                    stud.setEmail(email);
                    stud.setApproved(approved);
                    stud.setSchoolID(schoolID);
                    stud.setStudID(StudentID);
                    singleLogin = 1;
                    stud.setSingleLogin(singleLogin);

                    String update = "UPDATE Student SET SingleLogin=" + singleLogin + " WHERE StudentID= " + StudentID;
                    rs3 = DBConnection.ExecUpdateQuery(update);

                } else {
                    stud.setValid(false);
                }
                if (stud.isValid() && rs3 > 0) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("currentSessionUser", stud);
                    response.sendRedirect("./Stud/StudMain.jsp"); //logged-in page      		
                } else {
                    response.sendRedirect("invalidLogin.jsp");
                }
            } else if (!rs.next()) {
                stud.setValid(false);

                String searchQ = "select * from Admin where UserName='" + username + "'AND PassWord='" + password + "'";
                ResultSet rs2 = DBConnection.ExecQuery(searchQ);

                if (rs2.next()) {
                    AdminBean admin = new AdminBean();

                    int singlelogin2 = rs2.getInt("SingleLogin");
                    String adminID = rs2.getString("UserName");
                    int rs4 = 0;
                    if (singlelogin2 == 0) {
                        singlelogin2 = 1;
                        admin.setUserName(username);
                        admin.setPassWord(password);
                        admin.setSinglelogin(singlelogin2);
                        String update = "UPDATE Admin SET SingleLogin=" + singlelogin2 + " WHERE UserName='" + adminID + "'";
                        rs4 = DBConnection.ExecUpdateQuery(update);

                        if (rs4 > 0) {
                            HttpSession session = request.getSession(true);
                            session.setAttribute("currentSessionUser", admin);
                            response.sendRedirect("./Admin/AdminMain.jsp"); //logged-in page
                        }
                    }
                } else {
                    response.sendRedirect("invalidLogin.jsp");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
