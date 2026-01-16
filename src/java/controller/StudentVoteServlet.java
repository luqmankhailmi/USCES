package controller;

import bean.ElectionBean;
import bean.CandidateBean;
import dao.ElectionDAO;
import dao.CandidateDAO;
import dao.VoteDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StudentVoteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Check session
        HttpSession userSession = request.getSession(false);
        String studentNumber = null;
        
        if (userSession != null && userSession.getAttribute("studentNumber") != null) {
            studentNumber = (String) userSession.getAttribute("studentNumber");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // Get election ID from parameter
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int electionId = Integer.parseInt(idParam);
                
                ElectionDAO electionDAO = new ElectionDAO();
                CandidateDAO candidateDAO = new CandidateDAO();
                VoteDAO voteDAO = new VoteDAO();
                
                // Get election details
                ElectionBean election = electionDAO.getElectionById(electionId);
                
                if (election != null) {
                    // Check if election is ongoing
                    java.time.LocalDateTime now = java.time.LocalDateTime.now();
                    if (now.isBefore(election.getStartDate())) {
                        request.setAttribute("errorMessage", "This election has not started yet.");
                        request.setAttribute("election", election);
                        request.getRequestDispatcher("/user/vote.jsp").forward(request, response);
                        return;
                    } else if (now.isAfter(election.getEndDate())) {
                        // Redirect to results page
                        response.sendRedirect(request.getContextPath() + "/view_results?id=" + electionId);
                        return;
                    }
                    
                    // Check if student has already voted
                    boolean hasVoted = voteDAO.hasStudentVoted(studentNumber, electionId);
                    
                    // Get candidates for this election
                    ArrayList<CandidateBean> candidateList = candidateDAO.fetchCandidatesByElection(electionId);
                    
                    // Set attributes
                    request.setAttribute("election", election);
                    request.setAttribute("candidateList", candidateList);
                    request.setAttribute("hasVoted", hasVoted);
                    
                    request.getRequestDispatcher("/user/vote.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/user_list_election");
                }
                
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/user_list_election");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/user_list_election");
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

    @Override
    public String getServletInfo() {
        return "Student Vote Servlet";
    }
}