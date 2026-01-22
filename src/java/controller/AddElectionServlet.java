package controller;

import bean.ElectionBean; // Use your Bean
import bean.FacultyBean;
import dao.FacultyDAO;
import dao.ElectionDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddElectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String method = request.getMethod();

        
        if (method.equalsIgnoreCase("GET")) {
            loadFacultyList(request);
            request.getRequestDispatcher("/admin/addElection.jsp").forward(request, response);
        } 
        
        
        else if (method.equalsIgnoreCase("POST")) {
            try {
                String electionName = request.getParameter("electionName");
                String facultyIdStr = request.getParameter("facultyId");
                String startDateStr = request.getParameter("startDate");
                String endDateStr = request.getParameter("endDate");

                if (electionName == null || facultyIdStr == null || startDateStr == null || endDateStr == null) {
                    request.setAttribute("errMessage", "Please fill in all required fields.");
                    loadFacultyList(request);
                    request.getRequestDispatcher("/admin/addElection.jsp").forward(request, response);
                    return;
                }

                
                ElectionBean election = new ElectionBean();
                election.setElectionName(electionName);
                election.setFacultyID(Integer.parseInt(facultyIdStr));

                
                java.time.LocalDateTime start = java.time.LocalDateTime.parse(startDateStr);
                java.time.LocalDateTime end = java.time.LocalDateTime.parse(endDateStr);

                election.setStartDate(start);
                election.setEndDate(end);

                // 3. PASS THE BEAN TO THE DAO (Strict MVC)
                ElectionDAO electionDao = new ElectionDAO();
                boolean isAdded = electionDao.addElection(election); // Updated to pass object

                if (isAdded) {
                    request.setAttribute("successMsg", "New election created successfully!");
                    // Forwarding to the list servlet to refresh data
                    request.getRequestDispatcher("/admin_list_election").forward(request, response);
                } else {
                    request.setAttribute("errMessage", "Database error: Could not save election.");
                    loadFacultyList(request);
                    request.getRequestDispatcher("/admin/addElection.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errMessage", "Error: " + e.getMessage());
                loadFacultyList(request);
                request.getRequestDispatcher("/admin/addElection.jsp").forward(request, response);
            }
        }
    }

    
    private void loadFacultyList(HttpServletRequest request) {
        FacultyDAO facultyDao = new FacultyDAO();
        List<FacultyBean> facultyList = facultyDao.getAllFaculties();
        request.setAttribute("facultyList", facultyList);
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
}