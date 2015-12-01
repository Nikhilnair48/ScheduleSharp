/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaCode.admin;

import DbObjects.School;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nikhilnair
 */
public class ModifySchool extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");
        // RETRIEVE PARAMS
        String sName = request.getParameter("SchoolName");
        String days = request.getParameter("Days");
        String lunchRange = request.getParameter("lunchRange");
        String semester = request.getParameter("Semester");
        String year = request.getParameter("Year");
        String period = request.getParameter("Period");
        String schoolID = request.getParameter("SchoolID");
        // CONNECT TO DB
        DatabaseInfo dbInfo = new DatabaseInfo();
        Connection conn = null;
        Class.forName(dbInfo.getDriver()).newInstance();
        conn = DriverManager.getConnection(dbInfo.getUrl() + dbInfo.getDbName(), dbInfo.getDbUserName(), dbInfo.getDbPassword());

        /*UPDATE School
         SET Semester = '4'
         WHERE SchoolID = '1';*/
        String updateSchool = "UPDATE School SET SchoolName = ?, Semester = ?, Days = ?, Period = ?, RangeLunch = ? "
                + "WHERE SchoolID = ?";
        // USE PREPAREDSTATEMENTS TO MODIFY INFO IN DB
        PreparedStatement stm = conn.prepareStatement(updateSchool);
        stm.setString(1, sName);
        stm.setInt(2, Integer.parseInt(semester));
        stm.setInt(3, Integer.parseInt(days));
        stm.setInt(4, Integer.parseInt(period));
        stm.setString(5, lunchRange);
        stm.setInt(6, Integer.parseInt(schoolID));
        
        int num = stm.executeUpdate();
        // IF ALL IS WELL - JSP IS UPDATED WITH NEW INFO
        if(num > 0) {
            response.sendRedirect("SelectAllSchools?redirectTo=modifySchool");
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModifySchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ModifySchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ModifySchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ModifySchool.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModifySchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ModifySchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ModifySchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ModifySchool.class.getName()).log(Level.SEVERE, null, ex);
        }
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
