package dao;

import bean.ElectionBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import util.DBConnection;

public class ElectionDAO {
    
    public ArrayList<ElectionBean> fetchElection(int facultyID) {
        ArrayList<ElectionBean> electionList = new ArrayList<>();
        
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBConnection.createConnection();
            String query = "SELECT * FROM election WHERE faculty_id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, facultyID);
            rs = ps.executeQuery();
            
            // Changed from if to while to fetch ALL elections
            while (rs.next()) {
                // Extract data and create Election object
                int election_id = rs.getInt("election_id");
                String election_name = rs.getString("election_name");
                int faculty_id = rs.getInt("faculty_id");
                
                // Handle potential null timestamps
                Timestamp ts1 = rs.getTimestamp("start_date");
                LocalDateTime start_date = (ts1 != null) ? ts1.toLocalDateTime() : null;
                
                Timestamp ts2 = rs.getTimestamp("end_date");
                LocalDateTime end_date = (ts2 != null) ? ts2.toLocalDateTime() : null;
                
                electionList.add(new ElectionBean(election_id, election_name, faculty_id, start_date, end_date));
            }
            
            return electionList;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}