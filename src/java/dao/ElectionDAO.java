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

    /**
     * UPDATED for MVC: You can now pass an ElectionBean containing the ID
     */
    public ElectionBean getElectionById(ElectionBean electionQuery) {
        String query = "SELECT * FROM election WHERE election_id = ?";
        try (Connection conn = DBConnection.createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, electionQuery.getElectionID());
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

    /**
     * UPDATED: Accepts ElectionBean instead of int.
     */
    public boolean deleteElection(ElectionBean election) {
        Connection conn = null;
        int electionId = election.getElectionID(); 
        
        try {
            conn = util.DBConnection.createConnection(); 
            conn.setAutoCommit(false); 

            
            String sql0 = "DELETE FROM vote WHERE candidate_id IN (SELECT candidate_id FROM candidate WHERE election_id = ?)";
            try (PreparedStatement ps0 = conn.prepareStatement(sql0)) {
                ps0.setInt(1, electionId);
                ps0.executeUpdate();
            }

            
            String sql1 = "DELETE FROM candidate WHERE election_id = ?";
            try (PreparedStatement ps1 = conn.prepareStatement(sql1)) {
                ps1.setInt(1, electionId);
                ps1.executeUpdate();
            }

            
            String sql2 = "DELETE FROM ELECTION WHERE ELECTION_ID = ?";
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

    /**
     * UPDATED: Accepts ElectionBean instead of separate strings/ints.
     * This is the "Create Object" part of your MVC flow.
     */
    public boolean addElection(ElectionBean election) {
        String query = "INSERT INTO ELECTION (ELECTION_NAME, FACULTY_ID, START_DATE, END_DATE) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, election.getElectionName());
            ps.setInt(2, election.getFacultyID());

            
            ps.setTimestamp(3, Timestamp.valueOf(election.getStartDate())); 
            ps.setTimestamp(4, Timestamp.valueOf(election.getEndDate()));

            
            return ps.executeUpdate() > 0; 

        } catch (SQLException e) {
            System.out.println("ADD ELECTION ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateElection(ElectionBean election) {
        String query = "UPDATE ELECTION SET ELECTION_NAME = ?, START_DATE = ?, END_DATE = ? WHERE ELECTION_ID = ?";
        
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, election.getElectionName());
            ps.setTimestamp(2, Timestamp.valueOf(election.getStartDate()));
            ps.setTimestamp(3, Timestamp.valueOf(election.getEndDate()));
            ps.setInt(4, election.getElectionID());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
    
     /**
     * NEW: Get statistics (Vote count per candidate)
     */
    public Map<String, Integer> getElectionStatistics(ElectionBean election) {
        Map<String, Integer> stats = new HashMap<>();

        
        int electionId = election.getElectionID();

       
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
                    // Map student name to their vote count
                    stats.put(rs.getString("student_name"), rs.getInt("vote_count"));
                }
            }
        } catch (SQLException e) {
            System.out.println("STATS ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return stats;
    }
    
    public int getFacultyByElectionId(int electionId) {
    String sql = "SELECT faculty_id FROM election WHERE election_id = ?";
    try (Connection conn = DBConnection.createConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, electionId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("faculty_id");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; 
}
}