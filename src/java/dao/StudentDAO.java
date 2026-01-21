package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
