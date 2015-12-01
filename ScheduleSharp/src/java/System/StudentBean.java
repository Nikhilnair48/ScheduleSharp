package System;




/**
 *
 * @author chaehoon
 */
public class StudentBean {

    private String studID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    public boolean valid;
    private String email;
    private int approved;
    private int schoolID;
    private int singleLogin;

    public String getStudID(){
        return studID;
    }
    
    public void setStudID(String id){
        studID = id;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newLastName) {
        lastName = newLastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String newUsername) {
        username = newUsername;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean newValid) {
        valid = newValid;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setApproved(int newApproved) {
        approved = newApproved;
    }

    public int getApproved() {
        return approved;
    }

    public void setSchoolID(int newSchoolID) {
        schoolID = newSchoolID;
    }

    public int getSchoolID() {
        return schoolID;
    }
    
    public int getSingleLogin() {
        return singleLogin;
    }
    
    public void setSingleLogin(int newSingleLogin) {
        singleLogin = newSingleLogin;
    }
}

