package bean;

public class FacultyBean {
    private int facultyID;
    private String facultyName;

    // Default Constructor
    public FacultyBean() {}

    // Fixed Constructor: changed parameter to facultyID to match assignment
    public FacultyBean(int facultyID, String facultyName) {
        this.facultyID = facultyID;
        this.facultyName = facultyName;
    }

    // Fixed Getter: Standardized to facultyID
    public int getFacultyID() { 
        return facultyID; 
    }

    // Fixed Setter: MATCHED variable name to facultyID
    public void setFacultyID(int facultyID) { 
        this.facultyID = facultyID; 
    }

    public String getFacultyName() { 
        return facultyName; 
    }

    public void setFacultyName(String facultyName) { 
        this.facultyName = facultyName; 
    }
}