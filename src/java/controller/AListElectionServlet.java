package controller;

import bean.AdminBean;
import bean.ElectionBean;
import dao.AdminProfileDAO;
import dao.ElectionDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AListElectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession userSession = request.getSession(false);
        String staffNumber = null;
        
        if (userSession != null && userSession.getAttribute("staffNumber") != null) {
            staffNumber = (String) userSession.getAttribute("staffNumber");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        AdminProfileDAO adminDAO = new AdminProfileDAO();
        ElectionDAO electionDAO = new ElectionDAO();
        
        try {
            AdminBean admin = adminDAO.getAdminDetails(staffNumber);
            
            if (admin != null) {
                int faculty_id = admin.getFacultyId();
                
                // Get admin name
                String adminName = admin.getAdminName();
                
                // Get faculty name
                String facultyName = adminDAO.getFaculty(faculty_id);
                if (facultyName == null) {
                    facultyName = "Unknown Faculty";
                }
                
                // Get elections for this faculty
                ArrayList<ElectionBean> electionList = electionDAO.fetchElection(faculty_id);
                
                // Set attributes
                request.setAttribute("adminName", adminName);
                request.setAttribute("facultyName", facultyName);
                request.setAttribute("electionList", electionList);
            } else {
                request.setAttribute("error", "Admin not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/adminDashboard.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Admin List Election Servlet";
    }
}