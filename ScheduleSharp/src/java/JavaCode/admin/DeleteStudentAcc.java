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
// single-thread="true"
/**
 *
 * @author nikhilnair
 */
public class DeleteStudentAcc extends HttpServlet {

    private final static Logger logger = Logger.getLogger(AddSchool.class.getName());
    private static FileHandler fh = null;

    // INIT LOGGER
    public static void initLog() throws IOException {
        fh = new FileHandler("DeleteStudentAccount.log", false);
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
            logger.log(Level.INFO, "Connected to servlet : Deleting a Student Account");

            // RETRIEVE PARAMS
            String ID = request.getParameter("studentID");

            logger.log(Level.INFO, "Deleting Student Account (Servlet) : parameters received & retrieved.");
            logger.log(Level.INFO, "Received parameters are : " + "Student ID: {0}\n", new Object[]{ID});

            DatabaseInfo dbInfo = new DatabaseInfo();
            Connection conn = null;
            Class.forName(dbInfo.getDriver()).newInstance();
            conn = DriverManager.getConnection(dbInfo.getUrl() + dbInfo.getDbName(), dbInfo.getDbUserName(), dbInfo.getDbPassword());
            
            logger.log(Level.INFO, "Deleting a Student Account (Servlet) : Connected to database. Proceeding to prepare query.");
            
            // DELETE RECORDS IN DESIRED
            String deleteStudentDesired = "DELETE FROM DesiredSchedule WHERE DStudentID = ?";
            PreparedStatement stm = conn.prepareStatement(deleteStudentDesired);    // USE PREPAREDSTATEMENTS FOR QUERY
            stm.setInt(1, Integer.parseInt(ID));
            int result1 = stm.executeUpdate();
            
            // DELETE RECORDS IN SBLOCK
            String deleteStudentBlock = "DELETE FROM SBlock WHERE BStudID = ?";
            stm = conn.prepareStatement(deleteStudentBlock);
            stm.setInt(1, Integer.parseInt(ID));
            int result2 = stm.executeUpdate();
            /*
            // DELETE RECORDS IN ASSIGNED
            String deleteStudentAssigned = "DELETE FROM AssignedSchedule WHERE DStudentID = ?";
            stm = conn.prepareStatement(deleteStudentAssigned);
            stm.setInt(1, Integer.parseInt(ID));
            int result3 = stm.executeUpdate();*/
            
            // CHECK LATER - DELETE FRIENDS BEFORE DELETING STUDENT
            String deleteFriends = "DELETE FROM Friend WHERE Student1ID = ? OR Student2ID = ?";
            stm = conn.prepareStatement(deleteFriends);
            stm.setInt(1, Integer.parseInt(ID));
            stm.setInt(2, Integer.parseInt(ID));
            int result4 = stm.executeUpdate();
            
            String deleteStudent = "DELETE FROM Student WHERE StudentID = ?";
            stm = conn.prepareStatement(deleteStudent);
            stm.setInt(1, Integer.parseInt(ID));
            int result = stm.executeUpdate();
            
            logger.log(Level.INFO, "Deleting a Student Account (Servlet) : Query executed.");
            
            if(result > 0) {   // SCHOOL ADDED
                out.print("ok");
                logger.log(Level.INFO, "Deleting a Student Account (Servlet) : Deleting Student Account complete.");
            }
            else {          // SCHOOL NOT ADDED
                out.print("not ok");
                logger.log(Level.INFO, "Deleting a Student Account (Servlet) : Deleting Student Account not successful.");
            }
            
            logger.log(Level.INFO, "Deleting a Student Account (Servlet) : end of servlet.");
            stm.close();
            
            // SEND IT THROUGH 'SelectAllStudents' SERVLET TO UPDATE THE SCHOOLS IN SESSION
            response.sendRedirect("SelectAllStudents?redirectTo=deleteStudentAccount");
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
