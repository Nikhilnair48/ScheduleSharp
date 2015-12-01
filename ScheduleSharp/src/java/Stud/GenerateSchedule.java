/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stud;


import System.StudentBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Richie
 */
@WebServlet(name = "GenerateSchedule", urlPatterns = {"/GenerateSchedule"})
public class GenerateSchedule extends HttpServlet{
        /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Fetching all the classes in a certain semester. 
        //Ignoring sememeter in the query right now.
        String msg = "";
                response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(msg);
        
        Connection conn = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://db4free.net:3306/schedulesharp";   
        String username = "schedulesharp";
        String password = "schedule";
        
        ResultSet rs = null;
        try{
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, username, password);
            
            StudentBean stud = (StudentBean) request.getSession(true).getAttribute("currentSessionUser");
            int schoolID = stud.getSchoolID();
            String studID = stud.getStudID();
            String lunchRange = "SELECT RangeLunch FROM School WHERE SchoolID = ?";
            
            PreparedStatement lunchR = conn.prepareStatement(lunchRange);
            lunchR.setInt(1, schoolID);
            
            ResultSet lR = lunchR.executeQuery();
            String rawLR = "";
            if(lR.next()){
                rawLR = lR.getString(1);
            }
            String lunchRangeS = rawLR.substring(0, rawLR.indexOf("-"));
            String lunchRangeE = rawLR.substring(rawLR.indexOf("-") + 1,rawLR.length());
            
            int lunchRangeStart = Integer.parseInt(lunchRangeS);
            int lunchRangeEnd = Integer.parseInt(lunchRangeE);
            
            //ResultSet rs = DBConnection.ExecQuery(Query);
            //ResultSetMetaData md = rs.getMetaData();
            
            /*
            List<String> columns = new ArrayList<String>();
            int rowCount = md.getColumnCount();
            for (int i = 1; i <= rowCount; i++) {
                columns.add(md.getColumnName(i));
                }
            List<String[]> classes = new ArrayList<String[]>();
            while(rs.next()){
                String[] row = new String[rowCount];
                for(int i = 1; i <= rowCount; i++){
                    row[i-1] = rs.getString(columns.get(i-1));
                    //System.out.println(i + " " +rs.getString(columns.get(i-1)));
                }
                classes.add(row);
            }
            */
            List<String[]> shoppingCart = (List<String[]>)request.getSession(true).getAttribute("shoppingCart");
            int[][] ScheduleGrid = new int[5][12]; //Day and then period
            
            //make an array and find all courses that don't have multiple sections
            String couldNotAdd = "Could not add:";
            List<String[]> duplicates = new ArrayList<String[]>();
            List<String[]> notDuplicates = new ArrayList<String[]>();
            List<String[]> fittedCourses = new ArrayList<String[]>();
            for(int i = 0; i < shoppingCart.size(); i++){
                boolean duplicateClass = false;
                for(int j = 0; j < shoppingCart.size(); j++){
                    String[] class1 = shoppingCart.get(i);
                    String[] class2 = shoppingCart.get(j);
                    if(class1[0].equals(class2[0]) && i != j){ //Checks not the same element.
                        duplicateClass = true;
                    }
                }
                if(!duplicateClass){
                    notDuplicates.add(shoppingCart.get(i));
                }
                else{
                    duplicates.add(shoppingCart.get(i));
                }
            }
            for(int i = 0; i < notDuplicates.size(); i++){
                String[] course = notDuplicates.get(i); //(Schedule Block)
                String periodBlock = course[4];
                List<String> periods = Arrays.asList(periodBlock.split(","));
                String[][] allBlocks = new String[2][periods.size()];
                for (int j = 0; j < periods.size(); j++){ //All ScheduleBlocks
                    String block = periods.get(j);
                    List<String> breakPeriods = Arrays.asList(block.split("_"));
                    allBlocks[0][j] = breakPeriods.get(0); //Day
                    allBlocks[1][j] = breakPeriods.get(1); //Period
                }
                boolean fits = true;
                for(int j = 0; j < allBlocks[0].length; j++){
                    int day = Integer.parseInt(allBlocks[0][j]);
                    int period = Integer.parseInt(allBlocks[1][j]);
                    if(ScheduleGrid[day-1][period-1] == 1){
                        fits = false;
                    }
                }
                if(fits){
                    for(int j = 0; j < allBlocks[0].length; j++){
                    int day = Integer.parseInt(allBlocks[0][j]);
                    int period = Integer.parseInt(allBlocks[1][j]);
                    ScheduleGrid[day-1][period-1] = 1;
                    }
                fittedCourses.add(notDuplicates.get(i));    
                }
                else{
                    couldNotAdd = couldNotAdd + " " + course[0];
                }
            }
            //System.out.println("This Was Processed");
            //System.out.println(couldNotAdd);
            /*
            List<Course> courseList = new ArrayList<>();
            
            while(rs.next()){
                Course course = new Course();
                
                course.setCourseID(rs.getString("ACourseID"));
                course.setCourseName(rs.getString("ACourseName"));
                courseList.add(course);
            }
            request.getSession(true).setAttribute("s", courseList);
            */
            //RequestDispatcher rd = request.getRequestDispatcher("Stud/desiredSearch.jsp");
            //rd.forward(request, response);
            int studentID = Integer.parseInt(studID);
            System.out.println(studentID);
            String strQuery = "INSERT into DesiredSchedule (DSchoolID, DStudentID, DSectionID, DCourse, DBlock, DYear, DSemester)values (?, ?, ?, ?, ?, 2015, 3);";
            if(couldNotAdd.equals("Could not add:")){//Meaning all classes went through
                String clearCourses = "DELETE FROM DesiredSchedule WHERE DStudentID = ?;";
                PreparedStatement clearStm = conn.prepareStatement(clearCourses);
                clearStm.setInt(1, studentID);
                clearStm.executeUpdate();
                for(int i = 0; i < fittedCourses.size(); i++){
                    String[] row = fittedCourses.get(i);
                    int section = Integer.parseInt(row[1]);
                    String courseID = row[0];
                    String block = row[4];
                    
                    PreparedStatement stm = conn.prepareStatement(strQuery);
                    stm.setInt(1, schoolID);
                    stm.setInt(2, studentID);
                    stm.setInt(3, section);
                    stm.setString(4, courseID);
                    stm.setString(5, block);
                    
                    stm.executeUpdate();
                }
            }
            
            //Add Lunch Periods
            String lunchCheck = (String)request.getSession(true).getAttribute("LunchPeriod");
            System.out.println(lunchCheck);
            if(lunchCheck != null){
                for(int i = 0; i < 5; i++){
                    for(int j = lunchRangeStart;j < lunchRangeEnd; j++){
                        if(ScheduleGrid[i][j-1] == 0){
                           ScheduleGrid[i][j-1] = 1;
                           PreparedStatement stm = conn.prepareStatement(strQuery);
                           int section = 1; 
                           stm.setInt(1, schoolID);
                           stm.setInt(2, studentID);
                           stm.setInt(3, section);
                           stm.setString(4, "LUNCH");
                           String block = i+"_"+j;
                           stm.setString(5, block);
                           
                           stm.executeUpdate();
                           j = lunchRangeEnd;
                        }
                    }
                }
            }
            
            request.getSession(true).setAttribute("couldNotAdd", couldNotAdd);
            response.sendRedirect("./desiredSearch");
        }
        catch(Exception e ){
            e.printStackTrace();
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
