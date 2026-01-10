/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.*;

/**
 *
 * @author User
 */
public class DBConnection {
    public static Connection createConnection() throws SQLException {
        String url = "jdbc:derby://localhost:1527/USCES";
        String username = "app";
        String password = "app";

        return DriverManager.getConnection(url, username, password);
        // If SQLException occurs, it automatically propagates to the caller
    }
}
