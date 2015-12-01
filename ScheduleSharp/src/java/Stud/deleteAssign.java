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
public class deleteAssign extends HttpServlet {

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
        String BlockID = request.getParameter("BlockID");
        String Year = request.getParameter("YEAR");
        String Semester = request.getParameter("SEMESTER");
        switch(Semester){
            case "SPRING":
                Semester = "" + 1;
                break;
            case "SUMMER":
                Semester = "" + 2;
                break;
            case "FALL":
                Semester = "" + 3;
                break;
            case "WINTER":
                Semester = "" + 4;
                break;
            }
        String StudID = stud.getStudID();
        String getBlock = "SELECT * FROM SBlock WHERE BStudID=" + StudID + 
                " AND BBlockNum=\"" + BlockID + "\" AND BYear=" + Year + 
                " AND BSemester=" + Semester;
        ResultSet hasClass = DBConnection.ExecQuery(getBlock);
        try {
            while(hasClass.next()){
                String AssNum = hasClass.getString("BAssignID");
                String finalDel = "DELETE FROM SBlock WHERE BAssignID=" + AssNum;
                DBConnection.ExecUpdateQuery(finalDel);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(deleteAssign.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("Stud/assignedView.jsp");
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
