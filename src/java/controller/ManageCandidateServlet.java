package controller;

import bean.CandidateBean;
import dao.CandidateDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageCandidateServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Initialize DAO to fetch data
            CandidateDAO dao = new CandidateDAO();
            
            // 2. Check if the user is filtering by a specific election
            String electionIdParam = request.getParameter("electionId");
            ArrayList<CandidateBean> candidateList;

            if (electionIdParam != null && !electionIdParam.isEmpty()) {
                // If a filter is applied, use the method we created to fix the previous error
                int electionId = Integer.parseInt(electionIdParam);
                candidateList = dao.fetchCandidatesByElection(electionId);
                request.setAttribute("selectedElectionId", electionId);
            } else {
                // Otherwise, get the list of all candidates from database
                candidateList = dao.getAllCandidates();
            }

            // 3. Set the list as a request attribute for the JSP to read
            request.setAttribute("candidateList", candidateList);

            // 4. Forward to the JSP file in the admin folder
            request.getRequestDispatcher("/admin/manageCandidate.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Optional: Redirect to dashboard with an error message
            response.sendRedirect(request.getContextPath() + "/adminDashboard.jsp?error=fetch_failed");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Typically, POST here would handle the DELETE action if triggered from the management table
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller for viewing and filtering candidate lists";
    }
}