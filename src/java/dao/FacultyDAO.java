package dao;

import bean.FacultyBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class FacultyDAO {
    
    public List<FacultyBean> getAllFaculties() {
        List<FacultyBean> facultyList = new ArrayList<>();
        // Table and column names updated to match your DB screenshot
        String query = "SELECT FACULTY_ID, FACULTY_NAME FROM Faculty ORDER BY FACULTY_NAME ASC";

        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FacultyBean faculty = new FacultyBean();
                // Matching the exact column labels from your SQL result
                faculty.setFacultyID(rs.getInt("FACULTY_ID"));
                faculty.setFacultyName(rs.getString("FACULTY_NAME"));
                facultyList.add(faculty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facultyList;
    }
}