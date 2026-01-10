package dao;

import bean.UserLoginBean;
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
            // Get database connection
            con = DBConnection.createConnection();
            
            // SQL query to check user credentials
            String query = "SELECT * FROM student WHERE student_number = ? AND password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, studentNumber);
            ps.setString(2, password);
            
            // Execute query
            rs = ps.executeQuery();
            
            // Check if user exists
            if (rs.next()) {
                return "SUCCESS";
            } else {
                return "FAILURE";
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "FAILURE";
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