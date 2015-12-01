package Stud;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chaehoon
 */
public class FriendBean {
    
    private int student1ID;
    private int student2ID;
    private boolean request;
    
    public int getStudent1ID() {
        return student1ID;
    }
    
    public void setStudent1ID(int newStudent1ID) {
        student1ID = newStudent1ID;
    }
    
     public int getStudent2ID() {
        return student2ID;
    }
    
    public void setStudent2ID(int newStudent2ID) {
        student2ID = newStudent2ID;
    }
    
    public boolean isRequested() {
        return request;
    }            
    
    public void setRequested(boolean newRequest) {
        request = newRequest;
    }
}
