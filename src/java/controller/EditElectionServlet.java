package controller;

import bean.ElectionBean;
import dao.ElectionDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class EditElectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String method = request.getMethod();

        // --- GET METHOD: Loading the form ---
        if (method.equalsIgnoreCase("GET")) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int electionId = Integer.parseInt(idParam);
                    
                    // 1. Create a Bean to pass to the DAO (Strict MVC)
                    ElectionBean queryBean = new ElectionBean();
                    queryBean.setElectionID(electionId);
                    
                    ElectionDAO electionDAO = new ElectionDAO();
                    // 2. Updated to pass the Bean object
                    ElectionBean election = electionDAO.getElectionById(queryBean);
                    
                    if (election != null) {
                        request.setAttribute("election", election);
                        request.getRequestDispatcher("/admin/editElection.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errMessage", "Election not found.");
                        request.getRequestDispatcher("/admin_list_election").forward(request, response);
                    }
                } catch (Exception e) {
                    request.setAttribute("errMessage", "Error loading election data.");
                    request.getRequestDispatcher("/admin_list_election").forward(request, response);
                }
            }
        } 
        
        // --- POST METHOD: Saving the changes ---
        else if (method.equalsIgnoreCase("POST")) {
            try {
                int electionId = Integer.parseInt(request.getParameter("electionId"));
                String electionName = request.getParameter("electionName");
                
                // 3. Clean and parse date strings
                // HTML5 datetime-local uses 'T' (e.g., 2026-01-21T19:00)
                String startStr = request.getParameter("startDate").replace(" ", "T"); 
                String endStr = request.getParameter("endDate").replace(" ", "T");

                // 4. Populate the Model (Bean)
                ElectionBean election = new ElectionBean();
                election.setElectionID(electionId);
                election.setElectionName(electionName);
                election.setStartDate(LocalDateTime.parse(startStr));
                election.setEndDate(LocalDateTime.parse(endStr));

                // 5. Pass Bean to DAO
                ElectionDAO dao = new ElectionDAO();
                boolean isUpdated = dao.updateElection(election);

                if (isUpdated) {
                    request.setAttribute("successMsg", "Election updated successfully!");
                    request.getRequestDispatcher("/admin_list_election").forward(request, response);
                } else {
                    request.setAttribute("errMessage", "Database update failed.");
                    request.setAttribute("election", election); 
                    request.getRequestDispatcher("/admin/editElection.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errMessage", "Invalid data: " + e.getMessage());
                // If it fails, try to go back to the list
                request.getRequestDispatcher("/admin_list_election").forward(request, response);
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