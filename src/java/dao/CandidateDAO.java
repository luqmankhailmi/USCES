package dao;

import bean.CandidateBean;
import bean.ElectionBean;
import bean.StudentBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.DBConnection;

/**
 * Data Access Object for Candidate operations.
 * Handles fetching, registering, and deleting candidates and their manifestos.
 */
public class CandidateDAO {

    /**
     * Fetches all registered students to populate dropdowns.
     */
    public ArrayList<StudentBean> getAllStudents() {
        ArrayList<StudentBean> studentList = new ArrayList<>();
        String query = "SELECT student_id, student_name FROM student";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                StudentBean s = new StudentBean();
                s.setStudentId(rs.getInt("student_id"));
                s.setStudentName(rs.getString("student_name"));
                studentList.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    /**
     * Registers a new candidate.
     * Uses CandidateBean to follow Strict MVC.
     */
    public boolean registerCandidate(CandidateBean candidate) {
        String manifestoSql = "INSERT INTO manifesto (manifesto_content) VALUES (?)";
        String candidateSql = "INSERT INTO candidate (student_id, election_id, manifesto_id) VALUES (?, ?, ?)";
        boolean success = false;

        try (Connection con = DBConnection.createConnection()) {
            con.setAutoCommit(false);

            int manifestoId = 0;
            try (PreparedStatement psM = con.prepareStatement(manifestoSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psM.setString(1, candidate.getManifestoContent());
                psM.executeUpdate();
                try (ResultSet rs = psM.getGeneratedKeys()) {
                    if (rs.next()) manifestoId = rs.getInt(1);
                }
            }

            if (manifestoId > 0) {
                try (PreparedStatement psC = con.prepareStatement(candidateSql)) {
                    psC.setInt(1, candidate.getStudentId());
                    psC.setInt(2, candidate.getElectionId());
                    psC.setInt(3, manifestoId);
                    if (psC.executeUpdate() > 0) {
                        con.commit();
                        success = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Updates candidate manifesto using CandidateBean.
     */
    public boolean updateManifesto(CandidateBean candidate) {
        String query = "UPDATE manifesto SET manifesto_content = ? WHERE manifesto_id = ?";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, candidate.getManifestoContent());
            ps.setInt(2, candidate.getManifestoId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches all candidates for a specific election.
     * UPDATED: Accepts ElectionBean to fix Servlet incompatible types error.
     */
    public ArrayList<CandidateBean> fetchCandidatesByElection(ElectionBean election) {
        ArrayList<CandidateBean> list = new ArrayList<>();
        String query = "SELECT c.candidate_id, s.student_number, s.student_name, e.election_name " +
                       "FROM candidate c " + 
                       "INNER JOIN student s ON c.student_id = s.student_id " +
                       "INNER JOIN election e ON c.election_id = e.election_id " +
                       "WHERE c.election_id = ?";

        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, election.getElectionID());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CandidateBean candidate = new CandidateBean();
                    candidate.setCandidateId(rs.getInt("candidate_id"));
                    candidate.setStudentName(rs.getString("student_name"));
                    candidate.setStudentNumber(rs.getString("student_number"));
                    candidate.setElectionName(rs.getString("election_name"));
                    list.add(candidate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Fetches candidate details by ID.
     */
    public CandidateBean getCandidateById(CandidateBean queryBean) {
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

            ps.setInt(1, queryBean.getCandidateId());
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
     * Deletes candidate and their linked data.
     * UPDATED: Accepts CandidateBean to fix Servlet incompatible types error.
     */
    public boolean deleteCandidate(CandidateBean candidate) {
        boolean success = false;
        int candidateId = candidate.getCandidateId();
        
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

            // 2. Delete linked Votes
            String delV = "DELETE FROM vote WHERE candidate_id = ?";
            try (PreparedStatement ps = con.prepareStatement(delV)) {
                ps.setInt(1, candidateId);
                ps.executeUpdate();
            }

            // 3. Delete Candidate record
            String delC = "DELETE FROM candidate WHERE candidate_id = ?";
            try (PreparedStatement ps = con.prepareStatement(delC)) {
                ps.setInt(1, candidateId);
                if (ps.executeUpdate() > 0) success = true;
            }

            // 4. Delete orphaned Manifesto
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
     * Checks if a student has already applied for an election.
     */
    public boolean hasAlreadyApplied(StudentBean student, ElectionBean election) {
        String query = "SELECT COUNT(*) FROM candidate WHERE student_id = ? AND election_id = ?";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, student.getStudentId());
            ps.setInt(2, election.getElectionID());

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
}