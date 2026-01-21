package controller;

import bean.ElectionBean; // 1. Import your Bean
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteElectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String electionIdStr = request.getParameter("id");
        
        if (electionIdStr != null && !electionIdStr.isEmpty()) {
            try {
                // 2. RETRIEVE DATA FROM VIEW
                int electionId = Integer.parseInt(electionIdStr);
                
                // 3. CREATE THE MODEL OBJECT (ElectionBean)
                ElectionBean election = new ElectionBean();
                election.setElectionID(electionId);
                
                // 4. PASS THE OBJECT TO DAO
                dao.ElectionDAO electionDAO = new dao.ElectionDAO();
                boolean isDeleted = electionDAO.deleteElection(election); // Passing Bean object
                
                if (isDeleted) {
                    request.setAttribute("successMsg", "Election deleted successfully!");
                } else {
                    request.setAttribute("errMessage", "Failed to delete election.");
                }
                
            } catch (NumberFormatException e) {
                request.setAttribute("errMessage", "Invalid Election ID format.");
            }
        }

        // 5. FORWARD TO LIST CONTROLLER
        RequestDispatcher rd = request.getRequestDispatcher("/admin_list_election");
        rd.forward(request, response);
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