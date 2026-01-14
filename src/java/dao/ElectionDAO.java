package dao;

import bean.ElectionBean;
import bean.CandidateBean; // Assuming you have this bean
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import util.DBConnection;

public class ElectionDAO {

    // Existing method: Fetches all elections for a faculty
    public ArrayList<ElectionBean> fetchElection(int facultyID) {
        ArrayList<ElectionBean> electionList = new ArrayList<>();
        String query = "SELECT * FROM election WHERE faculty_id = ?";
        
        try (Connection conn = DBConnection.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, facultyID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    electionList.add(mapRowToElection(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return electionList;
    }

    // NEW: Fetch details for a specific election
    public ElectionBean getElectionById(int electionId) {
        String query = "SELECT * FROM election WHERE election_id = ?";
        try (Connection conn = DBConnection.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, electionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToElection(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // NEW: Get statistics (Vote count per candidate)
    public Map<String, Integer> getElectionStatistics(int electionId) {
        Map<String, Integer> stats = new HashMap<>();
        // Query joins candidate and student to get names for the chart/display
        String query = "SELECT s.student_name, COUNT(v.vote_id) as vote_count " +
                       "FROM candidate c " +
                       "JOIN student s ON c.student_id = s.student_id " +
                       "LEFT JOIN vote v ON c.candidate_id = v.candidate_id " +
                       "WHERE c.election_id = ? " +
                       "GROUP BY s.student_name";
        
        try (Connection conn = DBConnection.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, electionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    stats.put(rs.getString("student_name"), rs.getInt("vote_count"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    // NEW: Delete election (Note: Dependent records must be handled)
    public boolean deleteElection(int electionId) {
        String query = "DELETE FROM election WHERE election_id = ?";
        try (Connection conn = DBConnection.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, electionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to keep code clean
    private ElectionBean mapRowToElection(ResultSet rs) throws SQLException {
        int id = rs.getInt("election_id");
        String name = rs.getString("election_name");
        int facId = rs.getInt("faculty_id");
        
        Timestamp ts1 = rs.getTimestamp("start_date");
        LocalDateTime start = (ts1 != null) ? ts1.toLocalDateTime() : null;
        
        Timestamp ts2 = rs.getTimestamp("end_date");
        LocalDateTime end = (ts2 != null) ? ts2.toLocalDateTime() : null;
        
        return new ElectionBean(id, name, facId, start, end);
    }
}