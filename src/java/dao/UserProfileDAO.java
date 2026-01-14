package dao;

import bean.StudentBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBConnection;

public class UserProfileDAO {
    
    public StudentBean getUserDetails(String studentNumber) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.createConnection();
            String query = "SELECT * FROM student WHERE student_number = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, studentNumber);
            rs = ps.executeQuery();

            if (rs.next()) {
                // We create the bean and use setters to ensure we get the ID
                StudentBean student = new StudentBean();
                
                // ADDED THIS: We need the primary key ID for adding candidates
                student.setStudentId(rs.getInt("student_id")); 
                
                student.setStudentName(rs.getString("student_name"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setFacultyId(rs.getInt("faculty_id"));
                student.setStudentEmail(rs.getString("student_email"));

                return student;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // REMAINED: Your original manual closing logic
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFaculty(int facultyId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = DBConnection.createConnection();
            String query = "SELECT faculty_name FROM faculty WHERE faculty_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, facultyId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getString("faculty_name");
            } else {
                return null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // REMAINED: Your original manual closing logic
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}