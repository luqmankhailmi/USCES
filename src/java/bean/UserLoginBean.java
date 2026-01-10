/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author User
 */
public class UserLoginBean implements java.io.Serializable {
    
    public String studentNumber;
    public String password;

    public UserLoginBean(String studentNumber, String password) {
        this.studentNumber = studentNumber;
        this.password = password;
    }

    public UserLoginBean() {
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
