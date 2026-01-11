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
public class AdminLoginBean {
    private String staffNumber;
    private String password;

    public AdminLoginBean() {
    }

    public AdminLoginBean(String staffNumber, String password) {
        this.staffNumber = staffNumber;
        this.password = password;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
