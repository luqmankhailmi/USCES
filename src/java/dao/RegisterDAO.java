package dao;

import bean.StudentBean;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for Student Registration
 * @author User
 */
public class RegisterDAO {
    
    /**
     * Check if a student number already exists in the database
     * 
     * @param studentNumber the student number to check
     * @return true if exists, false otherwise
     */
    public boolean isStudentNumberExists(String studentNumber) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = DBConnection.createConnection();
            String query = "SELECT student_id FROM STUDENT WHERE student_number = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, studentNumber);
            rs = ps.executeQuery();
            
            return rs.next(); // Returns true if record exists
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(rs, ps, con);
        }
    }
    
    /**
     * Check if an email already exists in the database
     * 
     * @param email the email to check
     * @return true if exists, false otherwise
     */
    public boolean isEmailExists(String email) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = DBConnection.createConnection();
            String query = "SELECT student_id FROM STUDENT WHERE student_email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            return rs.next(); // Returns true if record exists
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(rs, ps, con);
        }
    }
    
    /**
     * Register a new student in the database
     * 
     * @param student StudentBean containing student information
     * @param password the password for the student account
     * @return "SUCCESS" if registration successful, error message otherwise
     */
    public String registerStudent(StudentBean student, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DBConnection.createConnection();
            
            // Check if student number already exists
            if (isStudentNumberExists(student.getStudentNumber())) {
                return "Student ID already registered!";
            }
            
            // Check if email already exists
            if (isEmailExists(student.getStudentEmail())) {
                return "Email already registered!";
            }
            
            // Insert new student
            String query = "INSERT INTO STUDENT (student_name, student_number, faculty_id, student_email, password) " +
                          "VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, student.getStudentName().trim());
            ps.setString(2, student.getStudentNumber().trim());
            ps.setInt(3, student.getFacultyId());
            ps.setString(4, student.getStudentEmail().trim());
            ps.setString(5, password); // In production, hash the password!
            
            int rowsInserted = ps.executeUpdate();
            
            if (rowsInserted > 0) {
                return "SUCCESS";
            } else {
                return "Registration failed. Please try again.";
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        } finally {
            closeResources(null, ps, con);
        }
    }
    
    /**
     * Get all faculties from the database
     * Used to populate the faculty dropdown in registration form
     * 
     * @return ResultSet containing all faculties
     */
    public ResultSet getAllFaculties() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = DBConnection.createConnection();
            String query = "SELECT faculty_id, faculty_name FROM FACULTY ORDER BY faculty_name";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            return rs;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        // Note: Connection should be closed by the caller when done with ResultSet
    }
    
    /**
     * Helper method to close database resources
     * 
     * @param rs ResultSet to close
     * @param ps PreparedStatement to close
     * @param con Connection to close
     */
    private void closeResources(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}