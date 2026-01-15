/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import bean.FacultyBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;
/**
 *
 * @author HP
 */
public class FacultyDAO {
    
    public List<FacultyBean> getAllFaculties() {
        List<FacultyBean> facultyList = new ArrayList<>();
        String query = "SELECT faculty_id, faculty_name FROM faculty ORDER BY faculty_name ASC";

        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FacultyBean faculty = new FacultyBean();
                faculty.setFacultyId(rs.getInt("faculty_id"));
                faculty.setFacultyName(rs.getString("faculty_name"));
                facultyList.add(faculty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facultyList;
    }
}
