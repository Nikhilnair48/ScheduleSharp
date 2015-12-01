/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaCode.admin;

import static JavaCode.admin.AddSchool.initLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import JavaCode.admin.DatabaseInfo.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author nikhilnair
 */
public class DeleteSchool extends HttpServlet {

    private final static Logger logger = Logger.getLogger(AddSchool.class.getName());
    private static FileHandler fh = null;

    // INIT LOGGER
    public static void initLog() throws IOException {
        fh = new FileHandler("DeleteSchool.log", false);
        Logger l = Logger.getLogger("");
        fh.setFormatter(new SimpleFormatter());
        l.addHandler(fh);
        l.setLevel(Level.CONFIG);
    }

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
            throws ServletException, IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            initLog();
            logger.log(Level.INFO, "Connected to servlet : Deleting a school");

            // RETRIEVE PARAMS
            int ID = Integer.parseInt(request.getParameter("SchoolID"));

            logger.log(Level.INFO, "Deleting a school (Servlet) : parameters received & retrieved.");
            logger.log(Level.INFO, "Received parameters are : " + "School ID: {0}\n", new Object[]{ID});

            DatabaseInfo dbInfo = new DatabaseInfo();
            Connection conn = null;
            Class.forName(dbInfo.getDriver()).newInstance();
            conn = DriverManager.getConnection(dbInfo.getUrl() + dbInfo.getDbName(), dbInfo.getDbUserName(), dbInfo.getDbPassword());
            
            logger.log(Level.INFO, "Deleting a school (Servlet) : Connected to database. Proceeding to prepare query.");
            
            // QUERIES - WHEN A SCHOOL IS DELETED, ALL THE STUDENTS ASSOCIATED WITH THE SCHOOL ARE DELETED AS WELL
            String deleteSchool = "DELETE FROM School WHERE SchoolID = ?";
            String deleteStudentsFromSchool = "DELETE FROM Student WHERE SSchoolID = ?";
            
            // USE PREPAREDSTATEMENTS FOR THE QUERIES
            // DELETE ANY AND ALL STUENTS ASSOCIATED WITH THE SCHOOL
            PreparedStatement stm = conn.prepareStatement(deleteStudentsFromSchool);
            stm.setInt(1, ID);
            int result3 = stm.executeUpdate();
            
            // NOW WE CAN DELETE THE SCHOOL WITH THE GIVEN ID
            stm = conn.prepareStatement(deleteSchool);
            stm.setInt(1, ID);
            int result = stm.executeUpdate();
            
            logger.log(Level.INFO, "Deleting a school (Servlet) : Query executed.");
            
            if(result > 0) {   // SCHOOL WAS ADDED
                out.print("ok");
                logger.log(Level.INFO, "Deleting a school (Servlet) : Deleting school complete.");
            }
            else {          // DENIED!
                out.print("not ok");
                logger.log(Level.INFO, "Deleting a school (Servlet) : Deleting school not successful.");
            }
            
            logger.log(Level.INFO, "Deleting a school (Servlet) : end of servlet.");
            stm.close();
            
            //SelectAllStudents?redirectTo=deleteStudentAccount
            response.sendRedirect("SelectAllSchools?redirectTo=deleteSchool");
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
            Logger.getLogger(DeleteSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DeleteSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DeleteSchool.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DeleteSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DeleteSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DeleteSchool.class.getName()).log(Level.SEVERE, null, ex);
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
