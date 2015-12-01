/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registrationValidation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sizpl_000
 */
public class RegistrationStudent extends HttpServlet {

    static Connection currentCon = null;

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
        try (PrintWriter out = response.getWriter()) {
            try {
                String Uname = request.getParameter("username");
                String Fname = request.getParameter("firstname");
                String Lname = request.getParameter("lastname");
                String Password = request.getParameter("password1");
                String email = request.getParameter("email");
                int approved = 0;
                int SSchoolID = Integer.parseInt(request.getParameter("sList"));
                currentCon = ConnectionManager.getConnection();
                String insertStudent = "INSERT INTO Student"
                        + " (StudentID, firstname, lastname, username, password, email, Approved, SSchoolID)"
                        + " VALUES(?,?,?,?,?,?,?,?)";
                try (PreparedStatement stm = currentCon.prepareStatement(insertStudent)) {
                    stm.setString(1, null);
                    stm.setString(2, Fname);
                    stm.setString(3, Lname);
                    stm.setString(4, Uname);
                    stm.setString(5, Password);
                    stm.setString(6, email);
                    stm.setInt(7, approved);
                    stm.setInt(8, SSchoolID);

                    stm.executeUpdate();
                    response.sendRedirect("index.jsp");                   
                }
            } catch (InstantiationException | IllegalAccessException | SQLException | IOException e) {
                out.println(e);
            }
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
