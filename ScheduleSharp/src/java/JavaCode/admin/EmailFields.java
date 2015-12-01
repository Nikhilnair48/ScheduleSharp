/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaCode.admin;

import java.util.List;

/**
 *
 * @author nikhilnair
 */
public class EmailFields {
    
    private static final String userName = "nnair@cs.stonybrook.edu";
    private static final String password = "modiSarkar14";
    private static final String fromEmail = "nnair@cs.stonybrook.edu";
    private static final String subject = "Schedule Sharp: Account request accepted.";
    private static String message;
    private String toEmail;
    
    public EmailFields(String t, String m) {
        toEmail = t;
        message = m;
        
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getFromEmail() {
        return fromEmail;
    }

    public static String getSubject() {
        return subject;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String aMessage) {
        message = aMessage;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

   
    
}
