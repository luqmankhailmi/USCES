package controller;

import dao.VoteDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubmitVoteServlet extends HttpServlet {

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
        
        // Get form parameters
        String electionIdParam = request.getParameter("electionId");
        String candidateIdParam = request.getParameter("candidateId");
        
        if (electionIdParam != null && candidateIdParam != null) {
            try {
                int electionId = Integer.parseInt(electionIdParam);
                int candidateId = Integer.parseInt(candidateIdParam);
                
                VoteDAO voteDAO = new VoteDAO();
                
                // Check if student has already voted
                if (voteDAO.hasStudentVoted(studentNumber, electionId)) {
                    response.sendRedirect(request.getContextPath() + "/student_vote?id=" + electionId);
                    return;
                }
                
                // Submit the vote
                boolean success = voteDAO.castVote(studentNumber, candidateId, electionId);
                
                if (success) {
                    // Redirect back to vote page (will show "already voted" message)
                    response.sendRedirect(request.getContextPath() + "/student_vote?id=" + electionId);
                } else {
                    // Error occurred
                    request.setAttribute("errorMessage", "Failed to submit vote. Please try again.");
                    request.getRequestDispatcher("/student_vote?id=" + electionId).forward(request, response);
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
        return "Submit Vote Servlet";
    }
}