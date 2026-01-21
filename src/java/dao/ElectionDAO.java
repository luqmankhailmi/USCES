package dao;

import bean.ElectionBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * UPDATED: Fetches all elections across all faculties.
     * This resolves the "Create method" error in AddCandidateServlet.
     */
    public List<ElectionBean> getAllElections() {
        List<ElectionBean> electionList = new ArrayList<>();
        String query = "SELECT * FROM election ORDER BY election_name ASC";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                electionList.add(mapRowToElection(rs));
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

    // NEW: Delete election
    public boolean deleteElection(int electionId) {
    Connection conn = null;
    try {
        // FIX: Use the correct utility class name
        conn = util.DBConnection.createConnection(); 
        conn.setAutoCommit(false); // Start transaction

        // STEP 1: Delete votes first (Crucial for image_1058a7.png scenario)
        String sql0 = "DELETE FROM vote WHERE candidate_id IN (SELECT candidate_id FROM candidate WHERE election_id = ?)";
        try (PreparedStatement ps0 = conn.prepareStatement(sql0)) {
            ps0.setInt(1, electionId);
            ps0.executeUpdate();
        }

        // STEP 2: Delete candidates
        String sql1 = "DELETE FROM candidate WHERE election_id = ?";
        try (PreparedStatement ps1 = conn.prepareStatement(sql1)) {
            ps1.setInt(1, electionId);
            ps1.executeUpdate();
        }

        // STEP 3: Delete the election itself
        String sql2 = "DELETE FROM election WHERE election_id = ?";
        int rows;
        try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
            ps2.setInt(1, electionId);
            rows = ps2.executeUpdate();
        }

        conn.commit(); 
        return rows > 0; 
    } catch (Exception e) {
        if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        e.printStackTrace();
        return false;
    } finally {
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
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
    
    
 public boolean addElection(String name, int facultyId, String start, String end) {
    // 1. Ensure columns match your schema (UPPERCASE for Derby is safest)
    String query = "INSERT INTO ELECTION (ELECTION_NAME, FACULTY_ID, START_DATE, END_DATE) VALUES (?, ?, ?, ?)";
    
    try (Connection con = DBConnection.createConnection();
         PreparedStatement ps = con.prepareStatement(query)) {
        
        ps.setString(1, name);
        ps.setInt(2, facultyId);
        
        // 2. DERBY FIX: Add ":00" for seconds, otherwise Derby rejects it
        // From: 2026-01-15T22:00 -> To: 2026-01-15 22:00:00
        String formattedStart = start.replace("T", " ") + ":00";
        String formattedEnd = end.replace("T", " ") + ":00";
        
        ps.setString(3, formattedStart); 
        ps.setString(4, formattedEnd);
        
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("SQL ERROR: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
 
 public boolean updateElection(ElectionBean election) {
    String query = "UPDATE ELECTION SET ELECTION_NAME = ?, START_DATE = ?, END_DATE = ? WHERE ELECTION_ID = ?";
    
    try (Connection con = DBConnection.createConnection();
         PreparedStatement ps = con.prepareStatement(query)) {
        
        ps.setString(1, election.getElectionName());
        
        // Convert LocalDateTime from Bean back to Timestamp for the Database
        ps.setTimestamp(2, Timestamp.valueOf(election.getStartDate()));
        ps.setTimestamp(3, Timestamp.valueOf(election.getEndDate()));
        ps.setInt(4, election.getElectionID());
        
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("UPDATE ERROR: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
}
