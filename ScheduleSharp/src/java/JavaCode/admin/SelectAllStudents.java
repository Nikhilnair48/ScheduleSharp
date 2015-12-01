/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaCode.admin;

import DbObjects.Student;
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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nikhilnair
 */
public class SelectAllStudents extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            DatabaseInfo dbInfo = new DatabaseInfo();
            Connection conn = null;
            Class.forName(dbInfo.getDriver()).newInstance();
            conn = DriverManager.getConnection(dbInfo.getUrl() + dbInfo.getDbName(), dbInfo.getDbUserName(), dbInfo.getDbPassword());

            String redirect = request.getParameter("redirectTo");
            HttpSession session = request.getSession();
            String schools = "SELECT * FROM Student";
            PreparedStatement stm = conn.prepareStatement(schools);
            ResultSet rs = stm.executeQuery();

            ServletContext sc = getServletConfig().getServletContext();
            List<Student> studentList = new ArrayList<>();      // list to be returned

            while (rs.next()) {
                Student student = new Student();

                if (redirect.equals("viewStudentAccountRequests") && rs.getInt("Approved") == 0) {                    // STUDENTS WHO HAVE NOT BEEN ACCEPTED

                    student.setStudentID(rs.getInt("StudentID"));
                    student.setFirstName(rs.getString("FirstName"));
                    student.setLastName(rs.getString("LastName"));
                    student.setUserName(rs.getString("UserName"));
                    student.setPassWord(rs.getString("PassWord"));
                    student.setEmail(rs.getString("Email"));
                    student.setApproved("No");
                    student.setSSchoolID(rs.getInt("SSchoolID"));

                    studentList.add(student);
                } else if (redirect.equals("deleteStudentAccount") && rs.getInt("Approved") == 1) {        // WILL REDIRECT TO 'deleteStudentAccount.jsp'
                    
                    student.setStudentID(rs.getInt("StudentID"));
                    student.setFirstName(rs.getString("FirstName"));
                    student.setLastName(rs.getString("LastName"));
                    student.setUserName(rs.getString("UserName"));
                    student.setPassWord(rs.getString("PassWord"));
                    student.setEmail(rs.getString("Email"));
                    student.setApproved("Yes");
                    student.setSSchoolID(rs.getInt("SSchoolID"));
                    
                    studentList.add(student);
                }
            }
            
            session.setAttribute("student", studentList);
            response.sendRedirect("Admin/" + redirect + ".jsp");

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
        } catch (SQLException ex) {
            Logger.getLogger(SelectAllStudents.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SelectAllStudents.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SelectAllStudents.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SelectAllStudents.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(SelectAllStudents.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SelectAllStudents.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SelectAllStudents.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SelectAllStudents.class.getName()).log(Level.SEVERE, null, ex);
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
