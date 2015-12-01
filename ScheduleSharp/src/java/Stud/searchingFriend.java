package Stud;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DBWorks.DBConnection;
import Stud.viewFriends;
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
 * @author chaehoon lim
 */
public class searchingFriend extends HttpServlet {

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

        StudentBean stud = (StudentBean) request.getSession(true).getAttribute("currentSessionUser"); //get my loged in information
        String studUserName = stud.getUsername(); //my username
        String studID = stud.getStudID();
        String searchingName = request.getParameter("name"); //user typed searching name
        String query = "select * from Student where UserName='" + searchingName + "'";
        ResultSet rs = DBConnection.ExecQuery(query);
        ResultSet rs2 = DBConnection.ExecQuery(query);
        String fSearchList = " ";
        try {
            if (rs2.next() && !searchingName.equals(studUserName)) {
                String searchingID = rs2.getString("StudentID");
                String searchingApproved = rs2.getString("Approved");
                String qu2 = "select F.Student1ID from Friend F where F.Student1ID='" + searchingID + "' and F.Student2ID='" + studID + "'";
                String qu3 = "select F.Student2ID from Friend F where F.Student2ID='" + searchingID + "' and F.Student1ID='" + studID + "'";
                ResultSet rs3 = DBConnection.ExecQuery(qu2);
                ResultSet rs4 = DBConnection.ExecQuery(qu3);
                if (!rs3.next() && !rs4.next() && searchingApproved.equals("1")) {
                    while (rs.next()) {
                        fSearchList += "<tr><td><div align=\"center\">" + rs.getString("UserName") + "</div></td>\n"
                                + "<td><div align=\"center\">" + rs.getString("FirstName") + "</div></td>\n"
                                + "<td><div align=\"center\">" + rs.getString("LastName") + "</div></td>\n"
                                + "<td><div align=\"center\"><a href=\"../requestFriend?id=" + rs.getString("StudentID") + "\">\n"
                                + "<button type=\"button\" class=\"btn btn-warning btn-xs\">Request Friend</button>\n"
                                + "</div></td></tr>";
                    }
                }
            }
            request.getSession(true).setAttribute("fSearchList", fSearchList);
            response.sendRedirect("Stud/searchFriend.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(searchingFriend.class.getName()).log(Level.SEVERE, null, ex);
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
