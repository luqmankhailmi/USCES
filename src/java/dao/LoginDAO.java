package dao;

import bean.UserLoginBean;
import bean.AdminLoginBean;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class LoginDAO {
    
    /**
     * Authenticates user credentials against the database
     * 
     * @param loginBean contains student number and password
     * @return "SUCCESS" if valid credentials, "FAILURE" otherwise
     */
    public String authenticateUser(UserLoginBean loginBean) {
        String studentNumber = loginBean.getStudentNumber();
        String password = loginBean.getPassword();
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
           
            con = DBConnection.createConnection();
            
            
            String query = "SELECT * FROM student WHERE student_number = ? AND password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, studentNumber);
            ps.setString(2, password);
            
            
            rs = ps.executeQuery();
            
            
            if (rs.next()) {
                return "SUCCESS";
            } else {
                return "FAILURE";
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "FAILURE";
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
    
    
    public String authenticateAdmin(AdminLoginBean loginBean) {
        String staffNumber = loginBean.getStaffNumber();
        String password = loginBean.getPassword();
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            con = DBConnection.createConnection();
            
            
            String query = "SELECT * FROM admin WHERE staff_number = ? AND password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, staffNumber);
            ps.setString(2, password);
            
            
            rs = ps.executeQuery();
            
            
            if (rs.next()) {
                return "SUCCESS";
            } else {
                return "FAILURE";
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "FAILURE";
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
}