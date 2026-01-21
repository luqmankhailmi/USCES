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
                    request.setAttribute("successMsg", "Candidate updated successfully!");
                    
                    request.setAttribute("candidateBean", candidate); 
                    
                    RequestDispatcher rd = request.getRequestDispatcher("/CandidateDetailServlet?id=" + candidate.getCandidateId());
                    rd.forward(request, response);
                } else {
                    request.setAttribute("errMessage", "Failed to save changes.");
                    RequestDispatcher rd = request.getRequestDispatcher("/CandidateDetailServlet?id=" + cIdStr + "&mode=edit");
                    rd.forward(request, response);
                }
            }
        } catch (Exception e) {
            request.setAttribute("errMessage", "Update Error: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/ManageCandidateServlet");
            rd.forward(request, response);
        }
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