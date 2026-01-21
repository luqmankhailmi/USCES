package controller;

import util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserRegisterServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get form parameters
        String studentName = request.getParameter("studentName");
        String studentNumber = request.getParameter("studentNumber");
        String facultyIdStr = request.getParameter("facultyId");
        String studentEmail = request.getParameter("studentEmail");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validation
        if (studentName == null || studentName.trim().isEmpty() ||
            studentNumber == null || studentNumber.trim().isEmpty() ||
            facultyIdStr == null || facultyIdStr.trim().isEmpty() ||
            studentEmail == null || studentEmail.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            
            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("/auth/registerUser.jsp").forward(request, response);
            return;
        }
        
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match!");
            request.getRequestDispatcher("/auth/registerUser.jsp").forward(request, response);
            return;
        }
        
        // Parse faculty ID
        int facultyId;
        try {
            facultyId = Integer.parseInt(facultyIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid faculty selection!");
            request.getRequestDispatcher("/auth/registerUser.jsp").forward(request, response);
            return;
        }
        
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement checkEmailStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;
        ResultSet rsEmail = null;
        
        try {
            conn = DBConnection.createConnection();
            
            // Check if student number already exists
            String checkSql = "SELECT student_id FROM STUDENT WHERE student_number = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, studentNumber);
            rs = checkStmt.executeQuery(); // FIX: Use executeQuery() instead of getResultSet()
            
            if (rs.next()) { // FIX: Removed null check, just use next()
                request.setAttribute("errorMessage", "Student ID already registered!");
                request.getRequestDispatcher("/auth/registerUser.jsp").forward(request, response);
                return;
            }
            
            // Check if email already exists
            String checkEmailSql = "SELECT student_id FROM STUDENT WHERE student_email = ?";
            checkEmailStmt = conn.prepareStatement(checkEmailSql); // FIX: Use separate statement
            checkEmailStmt.setString(1, studentEmail);
            rsEmail = checkEmailStmt.executeQuery(); // FIX: Use executeQuery() and separate ResultSet
            
            if (rsEmail.next()) { // FIX: Removed null check
                request.setAttribute("errorMessage", "Email already registered!");
                request.getRequestDispatcher("/auth/registerUser.jsp").forward(request, response);
                return;
            }
            
            // Insert new student
            String insertSql = "INSERT INTO STUDENT (student_name, student_number, faculty_id, student_email, password) " +
                             "VALUES (?, ?, ?, ?, ?)";
            insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, studentName.trim());
            insertStmt.setString(2, studentNumber.trim());
            insertStmt.setInt(3, facultyId);
            insertStmt.setString(4, studentEmail.trim());
            insertStmt.setString(5, password); // In production, hash the password!
            
            int rowsInserted = insertStmt.executeUpdate();
            
            if (rowsInserted > 0) {
                request.setAttribute("successMessage", "Registration successful! Please login with your credentials.");
                request.getRequestDispatcher("/auth/registerUser.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("/auth/registerUser.jsp").forward(request, response);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/auth/registerUser.jsp").forward(request, response);
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (rsEmail != null) rsEmail.close();
                if (checkStmt != null) checkStmt.close();
                if (checkEmailStmt != null) checkEmailStmt.close();
                if (insertStmt != null) insertStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to registration page
        response.sendRedirect(request.getContextPath() + "/auth/registerUser.jsp");
    }
}