package controller;

import bean.CandidateBean; // 1. IMPORT THE BEAN
import dao.CandidateDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteCandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String idStr = request.getParameter("id");
        
        if (idStr != null && !idStr.isEmpty()) {
            try {
               
                int candidateId = Integer.parseInt(idStr);

                
                CandidateBean candidate = new CandidateBean();
                candidate.setCandidateId(candidateId);

                
                CandidateDAO dao = new CandidateDAO();
                boolean isDeleted = dao.deleteCandidate(candidate); // Pass the object, not the ID

                if (isDeleted) {
                    request.setAttribute("successMsg", "Candidate deleted successfully!");
                } else {
                    request.setAttribute("errMessage", "Failed to delete candidate.");
                }
                
            } catch (NumberFormatException e) {
                request.setAttribute("errMessage", "Invalid Candidate ID.");
            }
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/ManageCandidateServlet");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}