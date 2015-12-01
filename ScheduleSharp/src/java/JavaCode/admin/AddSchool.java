/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaCode.admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.servlet.SingleThreadModel;

/**
 * ADDSCHOOL - Adds a given school to the database.
 *
 * @author Schedule Sharp
 */
public class AddSchool extends HttpServlet {    //implements SingleThreadModel

    private final static Logger logger = Logger.getLogger(AddSchool.class.getName());
    private static FileHandler fh = null;

    // INIT LOGGER
    public static void initLog() throws IOException {
        fh = new FileHandler("AddSchoolLog.log", false);
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
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //Thread.currentThread().sleep((long) Math.random() * 1000);
            initLog();

            logger.log(Level.INFO, "Connected to servlet : Adding a school");

            // RETRIEVE PARAMS
            String sName = request.getParameter("SchoolName");
            String days = request.getParameter("Days");
            String rLunch1 = request.getParameter("RangeLunch1");
            String rLunch2 = request.getParameter("RangeLunch2");
            String semester = request.getParameter("Semester");
            String year = request.getParameter("Year");
            String period = request.getParameter("Period");
            //String scheduleBlocks = request.getParameter("scheduleBlocks");

            logger.log(Level.INFO, "Adding a school (Servlet) : parameters received & retrieved.");
            logger.log(Level.INFO, "Received parameters are : " + "School Name: {0}\n Days: {1}\n Range for lunch: {2}\n Semester: {3}\n Period: {4}", new Object[]{sName, days, rLunch1, rLunch2, semester, period});

            DatabaseInfo dbInfo = new DatabaseInfo();
            Connection conn = null;
            Class.forName(dbInfo.getDriver()).newInstance();
            conn = DriverManager.getConnection(dbInfo.getUrl() + dbInfo.getDbName(), dbInfo.getDbUserName(), dbInfo.getDbPassword());

            logger.log(Level.INFO, "Adding a school (Servlet) : Connected to database. Proceeding to prepare query.");

            // CREATE QUERY
            String insertSchool = "INSERT INTO School"
                    + " (SchoolName, Semester, Days, Period, RangeLunch)"
                    + " VALUES(?,?,?,?,?)";

            // USE PREPAREDSTATEMENTS FOR QUERY
            PreparedStatement stm = conn.prepareStatement(insertSchool);
            stm.setString(1, sName);
            stm.setInt(2, Integer.parseInt(semester));
            stm.setInt(3, Integer.parseInt(days));
            stm.setInt(4, Integer.parseInt(period));
            stm.setString(5, rLunch1 + "-" + rLunch2);
            int num = stm.executeUpdate();

            logger.log(Level.INFO, "Adding a school (Servlet) : Query executed.");

            if (num > 0) {   // SCHOOL ADDED
                out.print("ok");
                logger.log(Level.INFO, "Adding a school (Servlet) : Adding school complete.");
            } else {          // SCHOOL NOT ADDED
                out.print("not ok");
                logger.log(Level.INFO, "Adding a school (Servlet) : Adding school not successful.");
            }

            logger.log(Level.INFO, "Adding a school (Servlet) : end of servlet.");
            stm.close();
            
            response.sendRedirect("SelectAllSchools?redirectTo=addSchool");
        }
    }
    
    /*
            // GET SCHOOLID BEFORE ADDING SCHEDULE-BLOCKS
            String getSchool = "SELECT SchoolID FROM School WHERE SchoolName = ?";
            stm = conn.prepareStatement(getSchool);
            stm.setString(1, sName);
            ResultSet rs = stm.executeQuery();
            int schoolID = 0;
            while(rs.next()) {
                schoolID = rs.getInt("SchoolID");
            }
            
            // ENTER SCHEDULE BLOCKS FOR A SCHOOL
            String insertBlock = "INSERT INTO ScheduleBlock"
                    + " (SBSchoolID, Period, Days)"
                    + " VALUES(?,?,?)";

            stm = conn.prepareStatement(insertBlock);
            String[] block = scheduleBlocks.split("_");
            for (int i = 0; i < block.length; i++) {
                
                int index2 = block[i].indexOf(";");
                String dayy = block[i].substring(0, index2);
                String periodd = block[i].substring(index2 + 1);
                
                stm.setInt(1, schoolID);
                stm.setInt(2, Integer.parseInt(dayy));
                stm.setString(3, periodd);
                stm.executeUpdate();
            }
            */

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
            Logger.getLogger(AddSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AddSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AddSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AddSchool.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AddSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AddSchool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AddSchool.class.getName()).log(Level.SEVERE, null, ex);
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
