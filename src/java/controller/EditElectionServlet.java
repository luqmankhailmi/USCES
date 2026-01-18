package controller;

import bean.ElectionBean;
import dao.ElectionDAO;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditElectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. LOGIN CHECK - Centralized for both GET and POST
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String method = request.getMethod();

        // 2. HANDLE GET: Fetch data to populate the edit form
        if (method.equalsIgnoreCase("GET")) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int electionId = Integer.parseInt(idParam);
                    ElectionDAO electionDAO = new ElectionDAO();
                    ElectionBean election = electionDAO.getElectionById(electionId);
                    
                    if (election != null) {
                        request.setAttribute("election", election);
                        // Ensure this path matches your folder structure
                        request.getRequestDispatcher("/admin/editElection.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/admin_list_election?error=notfound");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect(request.getContextPath() + "/admin_list_election?error=load_failed");
                }
            }
        } 
        
        // 3. HANDLE POST: Process the update submitted from the form
        else if (method.equalsIgnoreCase("POST")) {
            try {
                int electionId = Integer.parseInt(request.getParameter("electionId"));
                String electionName = request.getParameter("electionName");
                
                // Convert HTML5 datetime-local string (YYYY-MM-DDTHH:MM) to SQL Timestamp
                String startStr = request.getParameter("startDate").replace(" ", "T"); 
                String endStr = request.getParameter("endDate").replace(" ", "T");

                ElectionBean election = new ElectionBean();
                election.setElectionID(electionId);
                election.setElectionName(electionName);
                election.setStartDate(LocalDateTime.parse(startStr));
                election.setEndDate(LocalDateTime.parse(endStr));

                ElectionDAO dao = new ElectionDAO();
                boolean isUpdated = dao.updateElection(election);

                if (isUpdated) {
                    response.sendRedirect(request.getContextPath() + "/admin_list_election?status=updated");
                } else {
                    // If DB update fails, go back to edit page with error
                    response.sendRedirect(request.getContextPath() + "/EditElectionServlet?id=" + electionId + "&error=db_fail");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/admin_list_election?error=invalid_data");
            }
        }
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