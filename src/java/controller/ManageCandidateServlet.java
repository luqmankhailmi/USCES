package controller;

import bean.CandidateBean;
import bean.ElectionBean; // Added import for Strict MVC
import dao.CandidateDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManageCandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        try {
            CandidateDAO dao = new CandidateDAO();
            String electionIdParam = request.getParameter("electionId");
            ArrayList<CandidateBean> candidateList;

            if (electionIdParam != null && !electionIdParam.isEmpty()) {
                int electionId = Integer.parseInt(electionIdParam);

                // 1. STRICT MVC: Wrap the ID into a Bean
                ElectionBean electionQuery = new ElectionBean();
                electionQuery.setElectionID(electionId);

                // 2. FIX: Pass the Bean instead of the raw int
                candidateList = dao.fetchCandidatesByElection(electionQuery);
                
                request.setAttribute("selectedElectionId", electionId);
            } else {
                candidateList = dao.getAllCandidates();
            }

            request.setAttribute("candidateList", candidateList);

            RequestDispatcher rd = request.getRequestDispatcher("/admin/manageCandidate.jsp");
            rd.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errMessage", "Error retrieving candidate list: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/adminDashboard.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller for viewing and filtering candidate lists";
    }
}