package controller;

import bean.StudentBean;
import dao.RegisterDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/student_register")
public class UserRegisterServlet extends HttpServlet {
    
    private RegisterDAO registerDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        registerDAO = new RegisterDAO();
    }
    
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
        
        // Validation: Check if all fields are filled
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
        
        // Validation: Check if passwords match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match!");
            request.getRequestDispatcher("/auth/registerUser.jsp").forward(request, response);
            return;
        }
        
        // Validation: Password length (minimum 6 characters)
        if (password.length() < 6) {
            request.setAttribute("errorMessage", "Password must be at least 6 characters long!");
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
        
        // Create StudentBean object
        StudentBean student = new StudentBean(studentName, studentNumber, facultyId, studentEmail);
        
        // Register student using DAO
        String result = registerDAO.registerStudent(student, password);
        
        if ("SUCCESS".equals(result)) {
            request.setAttribute("successMessage", "Registration successful! Please login with your credentials.");
            request.getRequestDispatcher("/auth/registerUser.jsp").forward(request, response);
        } else {
            // Registration failed - show error message
            request.setAttribute("errorMessage", result);
            request.getRequestDispatcher("/auth/registerUser.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to registration page
        response.sendRedirect(request.getContextPath() + "/auth/registerUser.jsp");
    }
}