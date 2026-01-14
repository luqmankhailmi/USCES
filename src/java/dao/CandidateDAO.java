package dao;

import bean.CandidateBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.DBConnection;

public class CandidateDAO {
    
    /**
     * Get candidate details with manifesto by candidate ID
     */
    public CandidateBean getCandidateById(int candidateId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.createConnection();
            String query = "SELECT " +
                "c.candidate_id, c.student_id, c.election_id, c.manifesto_id, " +
                "s.student_name, s.student_number, s.student_email, s.faculty_id, " +
                "f.faculty_name, " +
                "e.election_name, " +
                "m.manifesto_content " +
                "FROM candidate c " +
                "INNER JOIN student s ON c.student_id = s.student_id " +
                "INNER JOIN faculty f ON s.faculty_id = f.faculty_id " +
                "INNER JOIN election e ON c.election_id = e.election_id " +
                "INNER JOIN manifesto m ON c.manifesto_id = m.manifesto_id " +
                "WHERE c.candidate_id = ?";
            
            ps = con.prepareStatement(query);
            ps.setInt(1, candidateId);
            rs = ps.executeQuery();

            if (rs.next()) {
                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("student_name");
                String studentNumber = rs.getString("student_number");
                String studentEmail = rs.getString("student_email");
                int facultyId = rs.getInt("faculty_id");
                String facultyName = rs.getString("faculty_name");
                int electionId = rs.getInt("election_id");
                String electionName = rs.getString("election_name");
                int manifestoId = rs.getInt("manifesto_id");
                String manifestoContent = rs.getString("manifesto_content");

                return new CandidateBean(candidateId, studentId, studentName, studentNumber,
                    studentEmail, facultyId, facultyName, electionId, electionName,
                    manifestoId, manifestoContent);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Delete candidate by candidate ID
     * Note: This also deletes associated votes and manifesto if needed
     */
    public boolean deleteCandidate(int candidateId) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            con = DBConnection.createConnection();
            
            // First, get manifesto_id to potentially delete it
            String getManifestoQuery = "SELECT manifesto_id FROM candidate WHERE candidate_id = ?";
            ps = con.prepareStatement(getManifestoQuery);
            ps.setInt(1, candidateId);
            ResultSet rs = ps.executeQuery();
            int manifestoId = 0;
            if (rs.next()) {
                manifestoId = rs.getInt("manifesto_id");
            }
            rs.close();
            ps.close();
            
            // Delete votes associated with this candidate
            String deleteVotesQuery = "DELETE FROM vote WHERE candidate_id = ?";
            ps = con.prepareStatement(deleteVotesQuery);
            ps.setInt(1, candidateId);
            ps.executeUpdate();
            ps.close();
            
            // Delete the candidate
            String deleteCandidateQuery = "DELETE FROM candidate WHERE candidate_id = ?";
            ps = con.prepareStatement(deleteCandidateQuery);
            ps.setInt(1, candidateId);
            int rowsAffected = ps.executeUpdate();
            ps.close();
            
            if (rowsAffected > 0) {
                // Optionally delete manifesto if no other candidate uses it
                // Check if manifesto is used by other candidates
                if (manifestoId > 0) {
                    String checkManifestoQuery = "SELECT COUNT(*) FROM candidate WHERE manifesto_id = ?";
                    ps = con.prepareStatement(checkManifestoQuery);
                    ps.setInt(1, manifestoId);
                    ResultSet rs2 = ps.executeQuery();
                    if (rs2.next() && rs2.getInt(1) == 0) {
                        // No other candidate uses this manifesto, delete it
                        rs2.close();
                        ps.close();
                        String deleteManifestoQuery = "DELETE FROM manifesto WHERE manifesto_id = ?";
                        ps = con.prepareStatement(deleteManifestoQuery);
                        ps.setInt(1, manifestoId);
                        ps.executeUpdate();
                        ps.close();
                    } else {
                        rs2.close();
                        ps.close();
                    }
                }
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return success;
    }
    
    
    
    /**
     * Fetch all candidates for a specific election
     */
    public ArrayList<CandidateBean> fetchCandidatesByElection(int electionId) {
        ArrayList<CandidateBean> candidateList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.createConnection();
            // Join with STUDENT to get the name and number to display in the list
            String query = "SELECT c.candidate_id, c.student_id, c.election_id, c.manifesto_id, " +
                           "s.student_name, s.student_number " +
                           "FROM candidate c " +
                           "INNER JOIN student s ON c.student_id = s.student_id " +
                           "WHERE c.election_id = ?";
            
            ps = con.prepareStatement(query);
            ps.setInt(1, electionId);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Creating a simplified bean for the list view
                // You can use your existing constructor and pass null/0 for fields you don't need in the list
                CandidateBean candidate = new CandidateBean();
                candidate.setCandidateId(rs.getInt("candidate_id"));
                candidate.setStudentId(rs.getInt("student_id"));
                candidate.setStudentName(rs.getString("student_name"));
                candidate.setStudentNumber(rs.getString("student_number"));
                candidate.setElectionId(rs.getInt("election_id"));
                candidate.setManifestoId(rs.getInt("manifesto_id"));
                
                candidateList.add(candidate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return candidateList;
    }
    
    
    public boolean registerCandidate(int studentId, int electionId) {
    Connection con = null;
    PreparedStatement psManifesto = null;
    PreparedStatement psCandidate = null;
    ResultSet rs = null;
    boolean success = false;

    try {
        con = DBConnection.createConnection();
        con.setAutoCommit(false); // Start transaction

        // 1. Insert empty Manifesto to get the ID
        String manifestoSql = "INSERT INTO manifesto (manifesto_content) VALUES ('No manifesto yet.')";
        psManifesto = con.prepareStatement(manifestoSql, PreparedStatement.RETURN_GENERATED_KEYS);
        psManifesto.executeUpdate();
        
        rs = psManifesto.getGeneratedKeys();
        int manifestoId = 0;
        if (rs.next()) {
            manifestoId = rs.getInt(1);
        }

        // 2. Insert Candidate using the studentId, electionId, and manifestoId
        String candidateSql = "INSERT INTO candidate (student_id, election_id, manifesto_id) VALUES (?, ?, ?)";
        psCandidate = con.prepareStatement(candidateSql);
        psCandidate.setInt(1, studentId);
        psCandidate.setInt(2, electionId);
        psCandidate.setInt(3, manifestoId);
        
        int rows = psCandidate.executeUpdate();
        
        if (rows > 0) {
            con.commit(); // Save everything
            success = true;
        }

    } catch (SQLException e) {
        if (con != null) try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (psManifesto != null) psManifesto.close();
            if (psCandidate != null) psCandidate.close();
            if (con != null) con.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    return success;
}
    
    
    public boolean updateManifesto(int manifestoId, String newContent) {
    Connection con = null;
    PreparedStatement ps = null;
    boolean success = false;

    try {
        con = DBConnection.createConnection();
        // We update the content in the MANIFESTO table
        String query = "UPDATE manifesto SET manifesto_content = ? WHERE manifesto_id = ?";
        ps = con.prepareStatement(query);
        ps.setString(1, newContent);
        ps.setInt(2, manifestoId);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            success = true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Standard manual closing logic
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return success;
}
    
    
    public ArrayList<CandidateBean> getAllCandidates() {
      ArrayList<CandidateBean> list = new ArrayList<>();
      // We must JOIN with student and election tables to get the actual names
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
              candidate.setStudentName(rs.getString("student_name")); // Matches database "student_name"
              candidate.setElectionName(rs.getString("election_name")); 
              list.add(candidate);
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
      return list;
  }
}

