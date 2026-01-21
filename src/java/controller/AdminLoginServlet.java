package controller;

import bean.AdminLoginBean;
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
public class AdminLoginServlet extends HttpServlet {

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
        String staffNumber = request.getParameter("staffNumber");
        String password = request.getParameter("password");
        
        // Create UserLoginBean with user input
        AdminLoginBean loginBean = new AdminLoginBean();
        loginBean.setStaffNumber(staffNumber);
        loginBean.setPassword(password);
        
        // Create DAO and attempt authentication
        LoginDAO loginDAO = new LoginDAO();
        
        try {
            String adminValidate = loginDAO.authenticateAdmin(loginBean);
            
            if (adminValidate.equals("SUCCESS")) {
                
                HttpSession session = request.getSession();
                session.setAttribute("staffNumber", staffNumber);

                
                response.sendRedirect(request.getContextPath() + "/admin_list_election");

            } else {
                
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("/auth/loginAdmin.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            
            request.setAttribute("errorMessage", "Database connection error. Please try again later.");
            request.getRequestDispatcher("/auth/loginAdmin.jsp").forward(request, response);
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
        response.sendRedirect(request.getContextPath() + "/auth/loginAdmin.jsp");
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