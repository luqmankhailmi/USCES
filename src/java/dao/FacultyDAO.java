package dao;

import bean.FacultyBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.DBConnection;

public class FacultyDAO {
    
    /**
     * Fetch all faculties from database
     */
    public ArrayList<FacultyBean> getAllFaculties() {
        ArrayList<FacultyBean> facultyList = new ArrayList<>();
        String sql = "SELECT faculty_id, faculty_name FROM faculty ORDER BY faculty_name ASC";
        
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                FacultyBean faculty = new FacultyBean();
                faculty.setFacultyID(rs.getInt("faculty_id"));
                faculty.setFacultyName(rs.getString("faculty_name"));
                facultyList.add(faculty);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return facultyList;
    }
    
    /**
     * Get faculty name by ID
     */
    public String getFacultyNameById(int facultyId) {
        String sql = "SELECT faculty_name FROM faculty WHERE faculty_id = ?";
        
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, facultyId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("faculty_name");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}