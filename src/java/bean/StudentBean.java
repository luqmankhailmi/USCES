package bean;
import java.io.Serializable;

public class StudentBean implements Serializable {
    private int studentId;
    private String studentName;
    private String studentNumber;
    private int facultyId;
    private String studentEmail;
    
    // Normal Constructor
    public StudentBean(String studentName, String studentNumber, int facultyId, String studentEmail) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.facultyId = facultyId;
        this.studentEmail = studentEmail;
    }
    
    //Default Constructor

    public StudentBean() {
    }
    
    
    // Getters
    
    public int getStudentId() { return studentId; }

    public String getStudentName() {
        return studentName;
    }
    public String getStudentNumber() { return studentNumber; }
    public int getFacultyId() { return facultyId; }
    public String getStudentEmail() { return studentEmail; }

    
    // Setters
    
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
    
    
}