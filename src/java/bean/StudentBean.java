package bean;
import java.io.Serializable;

public class StudentBean implements Serializable {
    private String studentName;
    private String studentNumber;
    private int facultyId;
    private String studentEmail;
    
    // Constructor
    public StudentBean(String studentName, String studentNumber, int facultyId, String studentEmail) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.facultyId = facultyId;
        this.studentEmail = studentEmail;
    }
    
    // Getters
    public String getStudentName() { return studentName; }
    public String getStudentNumber() { return studentNumber; }
    public int getFacultyId() { return facultyId; }
    public String getStudentEmail() { return studentEmail; }
}