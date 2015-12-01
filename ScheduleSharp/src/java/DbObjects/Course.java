/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbObjects;

/**
 *
 * @author Richie
 */
public class Course implements java.io.Serializable {
    private String CourseName;
    private String CourseID;
    
    
    public void setCourseName(String CourseName){
        this.CourseName = CourseName;
    }
    public String getCourseName(){
        return CourseName;
    }
    
    public void setCourseID(String CourseID){
        this.CourseID = CourseID;
    }
    public String getCourseID(){
        return CourseID;
    }
    
    
}
