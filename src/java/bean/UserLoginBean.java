package bean;

/**
 * @author User
 */
public class UserLoginBean implements java.io.Serializable {
    private int studentId; // This is the Primary Key from the STUDENT table
    public String studentNumber;
    public String password;

    public UserLoginBean(String studentNumber, String password) {
        this.studentNumber = studentNumber;
        this.password = password;
    }

    public UserLoginBean() {
    }

    // NEW: Getter for studentId
    public int getStudentId() {
        return studentId;
    }

    // NEW: Setter for studentId
    public void setStudentId(int studentId) {
        this.studentId = studentId;
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