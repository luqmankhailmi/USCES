package controller;

import dao.CandidateDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Get the ID from the URL link
        String idStr = request.getParameter("id");
        String message = "error"; // Default status
        
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int candidateId = Integer.parseInt(idStr);
                
                // 2. Call your DAO to delete from the database
                CandidateDAO dao = new CandidateDAO();
                
                // Use the boolean return from your DAO
                boolean isDeleted = dao.deleteCandidate(candidateId);
                
                if (isDeleted) {
                    message = "success";
                } else {
                    message = "fail";
                }
                
            } catch (NumberFormatException e) {
                e.printStackTrace();
                message = "invalid_id";
            }
        }
        
        // 3. Go back to the Manage Candidates list with a status message
        // This allows the management page to show a "Candidate Deleted" alert
        response.sendRedirect(request.getContextPath() + "/ManageCandidateServlet?status=" + message);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Just in case a POST is sent, treat it like a GET
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles the deletion of candidates and cleanup of their manifestos/votes.";
    }
}