/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author User
 */
public class ElectionBean implements Serializable {
    private int electionID;
    private String electionName;
    private int facultyID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ElectionBean(int electionID, String electionName, int facultyID, LocalDateTime startDate, LocalDateTime endDate) {
        this.electionID = electionID;
        this.electionName = electionName;
        this.facultyID = facultyID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ElectionBean() {
    }

    public int getElectionID() {
        return electionID;
    }

    public String getElectionName() {
        return electionName;
    }

    public int getFacultyID() {
        return facultyID;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setElectionID(int electionID) {
        this.electionID = electionID;
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    public void setFacultyID(int facultyID) {
        this.facultyID = facultyID;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    
}
