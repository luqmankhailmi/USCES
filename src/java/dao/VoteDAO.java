package dao;

import bean.ElectionBean; // Added import for Strict MVC
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBConnection;

public class VoteDAO {
    
    /**
     * Check if a student has already voted in an election
     * Updated to accept ElectionBean for Strict MVC compliance
     */
    public boolean hasStudentVoted(String studentNumber, ElectionBean election) {
        // FIX: Extract the ID from the Bean
        int electionId = election.getElectionID();
        
        String query = "SELECT COUNT(*) FROM vote v " +
                      "JOIN student s ON v.student_id = s.student_id " +
                      "WHERE s.student_number = ? AND v.election_id = ?";
        
        try (Connection conn = DBConnection.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, studentNumber);
            ps.setInt(2, electionId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Cast a vote for a candidate
     * Note: You may want to update this to use a VoteBean in the future
     */
    public boolean castVote(String studentNumber, int candidateId, int electionId) {
        int studentId = getStudentId(studentNumber);
        if (studentId == -1) {
            return false;
        }
        
        // Create a temporary bean to reuse the updated check method
        ElectionBean eb = new ElectionBean();
        eb.setElectionID(electionId);
        
        if (hasStudentVoted(studentNumber, eb)) {
            return false;
        }
        
        String query = "INSERT INTO vote (student_id, candidate_id, election_id) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, studentId);
            ps.setInt(2, candidateId);
            ps.setInt(3, electionId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private int getStudentId(String studentNumber) {
        String query = "SELECT student_id FROM student WHERE student_number = ?";
        
        try (Connection conn = DBConnection.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, studentNumber);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("student_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}