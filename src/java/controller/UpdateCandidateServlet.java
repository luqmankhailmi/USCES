package controller;

import bean.CandidateBean; // Using the Bean
import dao.CandidateDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateCandidateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession userSession = request.getSession(false);
    if (userSession == null || userSession.getAttribute("staffNumber") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    
    try {
        String cIdStr = request.getParameter("candidateId");
        String mIdStr = request.getParameter("manifestoId");
        String newContent = request.getParameter("manifestoContent");

        if (cIdStr != null && mIdStr != null) {
            CandidateBean candidate = new CandidateBean();
            candidate.setCandidateId(Integer.parseInt(cIdStr));
            candidate.setManifestoId(Integer.parseInt(mIdStr));
            candidate.setManifestoContent(newContent);

            CandidateDAO dao = new CandidateDAO();
            boolean isUpdated = dao.updateManifesto(candidate);

            if (isUpdated) {
                
                userSession.setAttribute("successMsg", "Candidate updated successfully!");
                
                response.sendRedirect(request.getContextPath() + "/CandidateDetailServlet?id=" + cIdStr);
                return;
            } else {
                request.setAttribute("errMessage", "Update failed in database.");
            }
        }
    } catch (Exception e) {
        request.setAttribute("errMessage", "Error: " + e.getMessage());
    }
    
    request.getRequestDispatcher("/CandidateDetailServlet?id=" + request.getParameter("candidateId") + "&mode=edit").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}