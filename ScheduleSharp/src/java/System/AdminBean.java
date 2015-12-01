/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

/**
 *
 * @author nikhilnair
 */
public class AdminBean implements java.io.Serializable {
    
    private String UserName;
    private String PassWord;
    private int singlelogin;

    public String getUserName() {
        return UserName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setPassWord(String PassWord) {
        this.PassWord = PassWord;
    }
    
    public int getSinglelogin() {
        return singlelogin;
    }
    
    public void setSinglelogin(int newSinglelogin) {
        singlelogin = newSinglelogin;
    }    
}
