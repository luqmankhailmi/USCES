package controller;

import bean.CandidateBean;
import dao.CandidateDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CandidateDetailServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
    try {
        // 1. Get the ID from the URL (e.g., ?id=2)
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            int candidateId = Integer.parseInt(idParam);
            
            // 2. Fetch data from Database
            CandidateDAO dao = new CandidateDAO();
            CandidateBean candidate = dao.getCandidateById(candidateId);

            if (candidate != null) {
                // 3. Map database values to JSP variables
                request.setAttribute("candidateId", candidate.getCandidateId());
                request.setAttribute("manifestoId", candidate.getManifestoId());
                request.setAttribute("studentName", candidate.getStudentName());
                request.setAttribute("electionName", candidate.getElectionName());
                request.setAttribute("manifestoContent", candidate.getManifestoContent());

                // 4. FORWARD DIRECTLY TO EDIT PAGE (Ignoring viewCandidate.jsp)
                request.getRequestDispatcher("admin/editCandidate.jsp").forward(request, response);
                return;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    // Fallback to the list if something goes wrong
    response.sendRedirect("ManageCandidateServlet");
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

    @Override
    public String getServletInfo() {
        return "Handles Candidate viewing and routing to edit mode";
    }
}