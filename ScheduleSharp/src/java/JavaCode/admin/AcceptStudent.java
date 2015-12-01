/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaCode.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nikhilnair
 */
public class AcceptStudent extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // POSSIBLE ERROR - NONE OF THE BOXES ARE CHECKED
            String IDs = request.getParameter("IDs");
            
            if (IDs != null) {  // MULTIPLY STUDENTS
                
                String[] IDss = IDs.split("-");
                //String[] IDss = IDs.substring(0, IDs.length()).split("?");
                for (String IDs1 : IDss) {
                    doWork(Integer.parseInt(IDs1));
                }
                out.print("ok");
            } else if(request.getParameter("studentID") != null) {  // ONLY 1 STUDENT
                int ID = Integer.parseInt(request.getParameter("studentID"));
                doWork(ID);
                out.print("ok");
            } else {
                out.print("not ok");
            }

            // SEND IT THROUGH 'SelectAllStudents' SERVLET TO UPDATE THE STUDENTS IN SESSION
            response.sendRedirect("SelectAllStudents?redirectTo=viewStudentAccountRequests");

        }
    }

    public void doWork(int ID) throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        DatabaseInfo dbInfo = new DatabaseInfo();
        Connection conn = null;
        Class.forName(dbInfo.getDriver()).newInstance();
        conn = DriverManager.getConnection(dbInfo.getUrl() + dbInfo.getDbName(), dbInfo.getDbUserName(), dbInfo.getDbPassword());

        // QUERIES TO BE USED
        String rejectStudent = "UPDATE Student SET Approved = ? WHERE StudentID = ?";
        String studentInformation = "SELECT * FROM Student WHERE StudentID = ?";

        // USE PREPAREDSTATEMENTS FOR QUERY
        PreparedStatement stm = conn.prepareStatement(rejectStudent);
        stm.setInt(1, 1);
        stm.setInt(2, ID);
        int num = stm.executeUpdate();

            // BEFORE WE RETURN... WE HAVE TO SEND AN EMAIL TO THE ACCEPTED STUDENT
        // FIRST - GRAB THE STUDENT INFO FROM TABLE
        stm = conn.prepareStatement(studentInformation);
        stm.setInt(1, ID);
        ResultSet rs = stm.executeQuery();

        List<EmailFields> sendEmail = new ArrayList<>();

        while (rs.next()) {
            EmailFields fields;

            String firstName = rs.getString("firstName");
            String email = rs.getString("Email");
            String userName = rs.getString("UserName");
            String password = rs.getString("PassWord");
            String message = "Hello " + firstName + ", \n\n"
                    + "Your request for an account with Schedule Sharp has been accepted!\n"
                    + "Please log in using the following credentials:\n"
                    + "Username: " + userName + "\n"
                    + "Password: " + password + "\n\n"
                    + "Best, \n\n Team ScheduleSharp";
            fields = new EmailFields(email, message);
            sendEmail.add(fields);
        }

        SendEmailToAcceptedStudents setas = new SendEmailToAcceptedStudents();
        setas.sendEmail(sendEmail);
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
            Logger.getLogger(AcceptStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AcceptStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AcceptStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AcceptStudent.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AcceptStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AcceptStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AcceptStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AcceptStudent.class.getName()).log(Level.SEVERE, null, ex);
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
