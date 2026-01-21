package controller;

import bean.ElectionBean;
import dao.ElectionDAO;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StudentResultsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // 1. Session Check
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("studentNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // 2. Get election ID from parameter
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int electionId = Integer.parseInt(idParam);

                ElectionDAO electionDAO = new ElectionDAO();

                // 3. Create the Model Object (Bean) - REQUIRED FOR STRICT MVC
                ElectionBean queryBean = new ElectionBean();
                queryBean.setElectionID(electionId);

                // 4. FIX: Use the queryBean instead of raw int to match updated DAO
                ElectionBean election = electionDAO.getElectionById(queryBean);

                if (election != null) {
                    // 5. Business Logic: Check if election has ended
                    java.time.LocalDateTime now = java.time.LocalDateTime.now();
                    if (now.isBefore(election.getEndDate())) {
                        // Still ongoing, redirect back to voting page
                        response.sendRedirect(request.getContextPath() + "/student_vote?id=" + electionId);
                        return;
                    }

                    // 6. Use the Bean to get statistics
                    Map<String, Integer> stats = electionDAO.getElectionStatistics(queryBean);
                    
                    // Calculate total votes for the result summary
                    int totalVotes = 0;
                    if (stats != null) {
                        for (Integer votes : stats.values()) {
                            totalVotes += votes;
                        }
                    }
                    
                    // 7. Data Transfer to View
                    request.setAttribute("election", election);
                    request.setAttribute("stats", stats);
                    request.setAttribute("totalVotes", totalVotes);
                    
                    request.getRequestDispatcher("/user/viewResults.jsp").forward(request, response);
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
}