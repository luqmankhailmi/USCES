package dao;

import bean.StudentBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.DBConnection;

public class StudentDAO {

    /**
     * Get faculty_id for a specific student
     * Used for validation when adding candidate
     */
    public int getFacultyByStudentId(int studentId) {
        String sql = "SELECT faculty_id FROM student WHERE student_id = ?";

        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("faculty_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // not found / error
    }
    
    /**
     * Fetch all students from database
     */
    public ArrayList<StudentBean> fetchAllStudents() {
        ArrayList<StudentBean> studentList = new ArrayList<>();
        String sql = "SELECT student_id, student_name, student_number, faculty_id, student_email " +
                     "FROM student ORDER BY student_name ASC";
        
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                StudentBean student = new StudentBean();
                student.setStudentId(rs.getInt("student_id"));
                student.setStudentName(rs.getString("student_name"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setFacultyId(rs.getInt("faculty_id"));
                student.setStudentEmail(rs.getString("student_email"));
                studentList.add(student);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return studentList;
    }
    
    /**
     * Fetch students by faculty ID
     */
    public ArrayList<StudentBean> fetchStudentsByFaculty(int facultyId) {
        ArrayList<StudentBean> studentList = new ArrayList<>();
        String sql = "SELECT student_id, student_name, student_number, faculty_id, student_email " +
                     "FROM student WHERE faculty_id = ? ORDER BY student_name ASC";
        
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, facultyId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentBean student = new StudentBean();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setStudentName(rs.getString("student_name"));
                    student.setStudentNumber(rs.getString("student_number"));
                    student.setFacultyId(rs.getInt("faculty_id"));
                    student.setStudentEmail(rs.getString("student_email"));
                    studentList.add(student);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return studentList;
    }
    
    /**
     * Delete a student from database
     * Note: This will also handle cascading deletes if foreign keys are set up
     */
    public boolean deleteStudent(int studentId) {
        Connection con = null;

        try {
            con = DBConnection.createConnection();
            con.setAutoCommit(false); // Start transaction

            // 1. Delete from VOTE table
            String deleteVotesSql = "DELETE FROM vote WHERE student_id = ?";
            try (PreparedStatement ps = con.prepareStatement(deleteVotesSql)) {
                ps.setInt(1, studentId);
                ps.executeUpdate();
            }

            // 2. Delete from CANDIDATE table
            String deleteCandidateSql = "DELETE FROM candidate WHERE student_id = ?";
            try (PreparedStatement ps = con.prepareStatement(deleteCandidateSql)) {
                ps.setInt(1, studentId);
                ps.executeUpdate();
            }

            // 3. Finally delete from STUDENT table
            String deleteStudentSql = "DELETE FROM student WHERE student_id = ?";
            try (PreparedStatement ps = con.prepareStatement(deleteStudentSql)) {
                ps.setInt(1, studentId);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    con.commit(); // Commit transaction
                    return true;
                } else {
                    con.rollback(); // Student not found
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); // Reset auto-commit
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}