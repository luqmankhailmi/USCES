/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.AdminBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBConnection;

/**
 *
 * @author User
 */
public class AdminProfileDAO {
    public AdminBean getAdminDetails(String staffNumber) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.createConnection();
            String query = "SELECT * FROM admin WHERE staff_number = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, staffNumber);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Extract data and create Student object
                String name = rs.getString("admin_name");
                String number = rs.getString("staff_number");
                int facultyId = rs.getInt("faculty_id");
                String email = rs.getString("admin_email");

                return new AdminBean(name, number, facultyId, email);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // Now safe to close because we've extracted the data
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Get Faculty
    public String getFaculty(int facultyId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Get database connection
            con = DBConnection.createConnection();
            
            // SQL query to check user credentials
            String query = "SELECT * FROM faculty WHERE faculty_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, facultyId);
            
            // Execute query
            rs = ps.executeQuery();
            
            // Check if user exists
            if (rs.next()) {
                return (String) rs.getString("faculty_name");
            } else {
                return null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // Close resources in reverse order
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
