package controller;

import bean.StudentBean;
import bean.ElectionBean;
import dao.UserProfileDAO;
import dao.ElectionDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UListElectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession userSession = request.getSession(false);
        String studentNumber = null;
        
        if (userSession != null && userSession.getAttribute("studentNumber") != null) {
            studentNumber = (String) userSession.getAttribute("studentNumber");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        UserProfileDAO userDAO = new UserProfileDAO();
        ElectionDAO electionDAO = new ElectionDAO();
        
        try {
            StudentBean student = userDAO.getUserDetails(studentNumber);
            
            if (student != null) {
                int faculty_id = student.getFacultyId();
                
                // Get student name
                String studentName = student.getStudentName();
                
                // Get faculty name
                String facultyName = userDAO.getFaculty(faculty_id);
                if (facultyName == null) {
                    facultyName = "Unknown Faculty";
                }
                
                // Get elections for this faculty
                ArrayList<ElectionBean> electionList = electionDAO.fetchElection(faculty_id);
                
                // Set attributes
                request.setAttribute("studentName", studentName);
                request.setAttribute("facultyName", facultyName);
                request.setAttribute("electionList", electionList);
            } else {
                request.setAttribute("error", "Student not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/userDashboard.jsp");
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
        return "Student List Election Servlet";
    }
}