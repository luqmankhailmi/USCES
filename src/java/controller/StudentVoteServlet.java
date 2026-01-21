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
        
        // 1. Session & Security Check
        HttpSession userSession = request.getSession(false);
        String studentNumber = null;
        
        if (userSession != null && userSession.getAttribute("studentNumber") != null) {
            studentNumber = (String) userSession.getAttribute("studentNumber");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // 2. Extract raw data from request
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int electionId = Integer.parseInt(idParam);
                
                // Initialize DAOs
                ElectionDAO electionDAO = new ElectionDAO();
                CandidateDAO candidateDAO = new CandidateDAO();
                VoteDAO voteDAO = new VoteDAO();
                
                // 3. STRICT MVC: Wrap ID into a Bean before calling the Model layer
                ElectionBean queryBean = new ElectionBean();
                queryBean.setElectionID(electionId);
                
                // 4. Get election details using the Bean
                ElectionBean election = electionDAO.getElectionById(queryBean);
                
                if (election != null) {
                    // 5. Business Logic: Check election timeline
                    java.time.LocalDateTime now = java.time.LocalDateTime.now();
                    
                    if (now.isBefore(election.getStartDate())) {
                        request.setAttribute("errorMessage", "This election has not started yet.");
                        request.setAttribute("election", election);
                        request.getRequestDispatcher("/user/vote.jsp").forward(request, response);
                        return;
                    } else if (now.isAfter(election.getEndDate())) {
                        // Redirect to results if the election is finished
                        response.sendRedirect(request.getContextPath() + "/view_results?id=" + electionId);
                        return;
                    }
                    
                    // 6. STRICT MVC: Check if student has voted using the Bean
                    // This assumes you updated VoteDAO to accept an ElectionBean
                    boolean hasVoted = voteDAO.hasStudentVoted(studentNumber, queryBean);
                    
                    // 7. STRICT MVC: Get candidates using the Bean
                    // This assumes you updated CandidateDAO to accept an ElectionBean
                    ArrayList<CandidateBean> candidateList = candidateDAO.fetchCandidatesByElection(queryBean);
                    
                    // 8. Prepare data for the View (JSP)
                    request.setAttribute("election", election);
                    request.setAttribute("candidateList", candidateList);
                    request.setAttribute("hasVoted", hasVoted);
                    
                    request.getRequestDispatcher("/user/vote.jsp").forward(request, response);
                } else {
                    // Election ID doesn't exist in DB
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