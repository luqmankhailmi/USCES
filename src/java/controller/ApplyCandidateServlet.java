package controller;

import bean.ElectionBean;
import dao.ElectionDAO;
import dao.CandidateDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ApplyCandidateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Unified Session Check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentNumber") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String method = request.getMethod();
        CandidateDAO candidateDAO = new CandidateDAO();

        // 2. GET Logic: Loading the Application Form
        if (method.equalsIgnoreCase("GET")) {
            try {
                int electionId = Integer.parseInt(request.getParameter("id"));
                ElectionDAO electionDAO = new ElectionDAO();
                ElectionBean election = electionDAO.getElectionById(electionId);
                
                if (election != null) {
                    request.setAttribute("election", election);
                    request.getRequestDispatcher("/user/applyCandidate.jsp").forward(request, response);
                } else {
                    response.sendRedirect("student_dashboard?error=election_not_found");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("student_dashboard?error=invalid_id");
            }
        } 
        
        // 3. POST Logic: Processing the Application Submission
        else if (method.equalsIgnoreCase("POST")) {
            try {
                int electionId = Integer.parseInt(request.getParameter("electionId"));
                // Ensure studentID (Integer) was stored in session during login
                int studentId = (int) session.getAttribute("studentID"); 
                String manifesto = request.getParameter("manifesto");

                // Check for existing application to prevent duplicates
                if (candidateDAO.hasAlreadyApplied(studentId, electionId)) {
                    response.sendRedirect("student_dashboard?error=already_applied");
                } else {
                    // Save to CANDIDATE and MANIFESTO tables
                    boolean success = candidateDAO.registerCandidate(studentId, electionId, manifesto);
                    
                    if (success) {
                        response.sendRedirect("student_dashboard?msg=applied");
                    } else {
                        response.sendRedirect("apply_candidate?id=" + electionId + "&error=failed");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("student_dashboard?error=process_failed");
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