/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class AdminBean implements Serializable{
    private String adminName;
    private int facultyId;
    private String adminEmail;
    private String staffNumber;

    public AdminBean(String adminName, String staffNumber, int facultyId, String adminEmail) {
        this.adminName = adminName;
        this.facultyId = facultyId;
        this.adminEmail = adminEmail;
        this.staffNumber = staffNumber;
    }

    public AdminBean() {
    }

    public String getAdminName() {
        return adminName;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }
    
    
}
