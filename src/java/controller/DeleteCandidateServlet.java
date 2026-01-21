package controller;

import dao.CandidateDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher; // Required for forward
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteCandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. LOGIN CHECK
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String idStr = request.getParameter("id");
        String message = ""; 
        
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int candidateId = Integer.parseInt(idStr);
                CandidateDAO dao = new CandidateDAO();
                
                boolean isDeleted = dao.deleteCandidate(candidateId);
                
                if (isDeleted) {
                    // Lecturer Requirement: Use request attributes for feedback
                    request.setAttribute("successMsg", "Candidate deleted successfully!");
                } else {
                    request.setAttribute("errMessage", "Failed to delete candidate.");
                }
                
            } catch (NumberFormatException e) {
                request.setAttribute("errMessage", "Invalid Candidate ID.");
            }
        }
        
        // 2. FORWARD TO CONTROLLER (Lecturer Requirement)
        // We forward to ManageCandidateServlet so it can refresh the list 
        // and then display manageCandidate.jsp
        RequestDispatcher rd = request.getRequestDispatcher("/ManageCandidateServlet");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}