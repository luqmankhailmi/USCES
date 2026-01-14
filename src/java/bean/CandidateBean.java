package bean;

import java.io.Serializable;

public class CandidateBean implements Serializable {
    private int candidateId;
    private int studentId;
    private String studentName;
    private String studentNumber;
    private String studentEmail;
    private int facultyId;
    private String facultyName;
    private int electionId;
    private String electionName;
    private int manifestoId;
    private String manifestoContent;
    
    // Normal Constructor
    public CandidateBean(int candidateId, int studentId, String studentName, String studentNumber, 
                         String studentEmail, int facultyId, String facultyName, 
                         int electionId, String electionName, int manifestoId, String manifestoContent) {
        this.candidateId = candidateId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.studentEmail = studentEmail;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.electionId = electionId;
        this.electionName = electionName;
        this.manifestoId = manifestoId;
        this.manifestoContent = manifestoContent;
    }
    
    //Default Constructor
    public CandidateBean() {}    

    // Getters
    public int getCandidateId() {
        return candidateId;
    }
    public int getStudentId() 
    { 
        return studentId; 
    }
    public String getStudentName() { 
       return studentName; 
    }
    public String getStudentNumber() { 
        return studentNumber; 
    }
    public String getStudentEmail() { 
        return studentEmail; 
    }
    public int getFacultyId() { 
        return facultyId; 
    }
    public String getFacultyName() { 
        return facultyName; 
    }
    public int getElectionId() { 
        return electionId; 
    }
    public String getElectionName() { return electionName; 
    }
    public int getManifestoId() { 
        return manifestoId; 
    }
    public String getManifestoContent() { 
        return manifestoContent; 
    }

    
    // Setters
    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    public void setManifestoId(int manifestoId) {
        this.manifestoId = manifestoId;
    }

    public void setManifestoContent(String manifestoContent) {
        this.manifestoContent = manifestoContent;
    }
    
    
    
}

