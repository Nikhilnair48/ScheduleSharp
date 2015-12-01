/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbObjects;

/**
 *
 * @author nikhilnair
 */
public class Student implements java.io.Serializable {
    
    private int StudentID;
    private String FirstName;
    private String LastName;
    private String UserName;
    private String PassWord;
    private String Email;
    private String Approved;
    private int SSchoolID;

    public int getStudentID() {
        return StudentID;
    }

    public void setStudentID(int StudentID) {
        this.StudentID = StudentID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String PassWord) {
        this.PassWord = PassWord;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getApproved() {
        return Approved;
    }

    public void setApproved(String Approved) {
        this.Approved = Approved;
    }

    public int getSSchoolID() {
        return SSchoolID;
    }

    public void setSSchoolID(int SSchoolID) {
        this.SSchoolID = SSchoolID;
    }
    
    
    
}
