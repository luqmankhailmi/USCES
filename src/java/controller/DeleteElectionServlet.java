package controller;

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
                int electionId = Integer.parseInt(electionIdStr);
                
                
                dao.ElectionDAO electionDAO = new dao.ElectionDAO();
                boolean isDeleted = electionDAO.deleteElection(electionId);
                
                if (isDeleted) {
                    
                    request.setAttribute("successMsg", "Election deleted successfully!");
                } else {
                    request.setAttribute("errMessage", "Failed to delete election. It might not exist.");
                }
                
            } catch (NumberFormatException e) {
                request.setAttribute("errMessage", "Invalid Election ID format.");
            }
        }

        
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