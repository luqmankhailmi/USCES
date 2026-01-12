package controller;

import bean.UserLoginBean;
import dao.LoginDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
public class UserLoginServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method for user login.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get login credentials from request
        String studentNumber = request.getParameter("studentNumber");
        String password = request.getParameter("password");
        
        // Create UserLoginBean with user input
        UserLoginBean loginBean = new UserLoginBean();
        loginBean.setStudentNumber(studentNumber);
        loginBean.setPassword(password);
        
        // Create DAO and attempt authentication
        LoginDAO loginDAO = new LoginDAO();
        
        try {
            String userValidate = loginDAO.authenticateUser(loginBean);
            
            if (userValidate.equals("SUCCESS")) {
                // Login successful - create session
                HttpSession session = request.getSession();
                session.setAttribute("studentNumber", studentNumber);

                // Redirect to home page or dashboard
                response.sendRedirect(request.getContextPath() + "/user_list_election");

            } else {
                // Login failed - send back to login page with error
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("/auth/loginUser.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Database error - send to error page
            request.setAttribute("errorMessage", "Database connection error. Please try again later.");
            request.getRequestDispatcher("/auth/loginUser.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * Redirects to login page.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests to login page
        response.sendRedirect(request.getContextPath() + "/auth/loginUser.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "User Login Servlet - Handles user authentication";
    }
}