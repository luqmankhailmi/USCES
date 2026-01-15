package dao;

import bean.CandidateBean;
import bean.StudentBean; // Ensure this matches your Student model package
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.DBConnection;

/**
 * Data Access Object for Candidate operations
 * Handles fetching, registering, and deleting candidates and their manifestos.
 */
public class CandidateDAO {

    /**
     * Fetches all registered students to populate the "Select Student" dropdown.
     */
   // Change return type to StudentBean
    public ArrayList<StudentBean> getAllStudents() { // Use StudentBean
        ArrayList<StudentBean> studentList = new ArrayList<>();
        String query = "SELECT student_id, student_name FROM student";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                StudentBean s = new StudentBean(); // Use StudentBean
                s.setStudentId(rs.getInt("student_id"));
                s.setStudentName(rs.getString("student_name"));
                studentList.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return studentList;
    }

    /**
     * Registers a new candidate.
     * 1. Creates a default manifesto record.
     * 2. Links the student to the election using the new manifesto ID.
     */
    public boolean registerCandidate(int studentId, int electionId) {
        String manifestoSql = "INSERT INTO manifesto (manifesto_content) VALUES ('No manifesto yet.')";
        String candidateSql = "INSERT INTO candidate (student_id, election_id, manifesto_id) VALUES (?, ?, ?)";
        boolean success = false;

        Connection con = null;
        try {
            con = DBConnection.createConnection();
            con.setAutoCommit(false); // Start transaction for data integrity

            int manifestoId = 0;
            try (PreparedStatement psM = con.prepareStatement(manifestoSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psM.executeUpdate();
                try (ResultSet rs = psM.getGeneratedKeys()) {
                    if (rs.next()) {
                        manifestoId = rs.getInt(1);
                    }
                }
            }

            try (PreparedStatement psC = con.prepareStatement(candidateSql)) {
                psC.setInt(1, studentId);
                psC.setInt(2, electionId);
                psC.setInt(3, manifestoId);
                int rows = psC.executeUpdate();
                
                if (rows > 0) {
                    con.commit(); // Save transaction
                    success = true;
                }
            }
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            if (con != null) {
                try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return success;
    }

    /**
     * Fetches all candidates currently registered for any election.
     */
    public ArrayList<CandidateBean> getAllCandidates() {
        ArrayList<CandidateBean> list = new ArrayList<>();
        String query = "SELECT c.candidate_id, s.student_name, e.election_name " +
                       "FROM candidate c " +
                       "INNER JOIN student s ON c.student_id = s.student_id " +
                       "INNER JOIN election e ON c.election_id = e.election_id"; 

        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CandidateBean candidate = new CandidateBean();
                candidate.setCandidateId(rs.getInt("candidate_id"));
                candidate.setStudentName(rs.getString("student_name"));
                candidate.setElectionName(rs.getString("election_name")); 
                list.add(candidate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Get candidate details including manifesto by candidate ID.
     */
    public CandidateBean getCandidateById(int candidateId) {
        String query = "SELECT c.candidate_id, c.student_id, c.election_id, c.manifesto_id, " +
                       "s.student_name, s.student_number, s.student_email, s.faculty_id, " +
                       "f.faculty_name, e.election_name, m.manifesto_content " +
                       "FROM candidate c " +
                       "INNER JOIN student s ON c.student_id = s.student_id " +
                       "INNER JOIN faculty f ON s.faculty_id = f.faculty_id " +
                       "INNER JOIN election e ON c.election_id = e.election_id " +
                       "INNER JOIN manifesto m ON c.manifesto_id = m.manifesto_id " +
                       "WHERE c.candidate_id = ?";
        
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, candidateId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CandidateBean(
                        rs.getInt("candidate_id"), rs.getInt("student_id"),
                        rs.getString("student_name"), rs.getString("student_number"),
                        rs.getString("student_email"), rs.getInt("faculty_id"),
                        rs.getString("faculty_name"), rs.getInt("election_id"),
                        rs.getString("election_name"), rs.getInt("manifesto_id"),
                        rs.getString("manifesto_content")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delete candidate and cleanup associated votes and manifestos.
     */
    public boolean deleteCandidate(int candidateId) {
        boolean success = false;
        try (Connection con = DBConnection.createConnection()) {
            con.setAutoCommit(false);

            // 1. Get Manifesto ID
            int manifestoId = 0;
            String getM = "SELECT manifesto_id FROM candidate WHERE candidate_id = ?";
            try (PreparedStatement ps = con.prepareStatement(getM)) {
                ps.setInt(1, candidateId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) manifestoId = rs.getInt("manifesto_id");
                }
            }

            // 2. Delete Votes
            String delV = "DELETE FROM vote WHERE candidate_id = ?";
            try (PreparedStatement ps = con.prepareStatement(delV)) {
                ps.setInt(1, candidateId);
                ps.executeUpdate();
            }

            // 3. Delete Candidate
            String delC = "DELETE FROM candidate WHERE candidate_id = ?";
            try (PreparedStatement ps = con.prepareStatement(delC)) {
                ps.setInt(1, candidateId);
                int rows = ps.executeUpdate();
                if (rows > 0) success = true;
            }

            // 4. Delete Manifesto if orphaned
            if (manifestoId > 0) {
                String delM = "DELETE FROM manifesto WHERE manifesto_id = ?";
                try (PreparedStatement ps = con.prepareStatement(delM)) {
                    ps.setInt(1, manifestoId);
                    ps.executeUpdate();
                }
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Updates the content of a candidate's manifesto.
     */
    public boolean updateManifesto(int manifestoId, String newContent) {
        String query = "UPDATE manifesto SET manifesto_content = ? WHERE manifesto_id = ?";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, newContent);
            ps.setInt(2, manifestoId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    /**
     * NEW: Fetches all candidates for a specific election ID.
     * This resolves the error in ManageCandidateServlet.
     */
    public ArrayList<CandidateBean> fetchCandidatesByElection(int electionId) {
        ArrayList<CandidateBean> list = new ArrayList<>();
        String query = "SELECT c.candidate_id, s.student_name, e.election_name " +
                       "FROM candidate c " + 
                       "INNER JOIN student s ON c.student_id = s.student_id " +
                       "INNER JOIN election e ON c.election_id = e.election_id " +
                       "WHERE c.election_id = ?";

        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, electionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CandidateBean candidate = new CandidateBean();
                    candidate.setCandidateId(rs.getInt("candidate_id"));
                    candidate.setStudentName(rs.getString("student_name"));
                    candidate.setElectionName(rs.getString("election_name"));
                    list.add(candidate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}