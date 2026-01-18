package controller;

import dao.CandidateDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

public class DeleteCandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. LOGIN CHECK
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            // Unauthorized access, redirect to login
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String idStr = request.getParameter("id");
        String message = "error"; 
        
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int candidateId = Integer.parseInt(idStr);
                CandidateDAO dao = new CandidateDAO();
                
                // This calls your transaction-safe DAO method
                boolean isDeleted = dao.deleteCandidate(candidateId);
                
                if (isDeleted) {
                    message = "deleted"; // Matches the 'msg' check in JSP
                } else {
                    message = "fail";
                }
                
            } catch (NumberFormatException e) {
                e.printStackTrace();
                message = "invalid_id";
            }
        }
        
        // Redirecting back to the ManageCandidateServlet so it can reload the list
        // Changed "status" to "msg" to match the JSP alert logic
        response.sendRedirect(request.getContextPath() + "/ManageCandidateServlet?msg=" + message);
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