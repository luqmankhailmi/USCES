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
        
        
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("studentNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int electionId = Integer.parseInt(idParam);

                ElectionDAO electionDAO = new ElectionDAO();

                
                ElectionBean queryBean = new ElectionBean();
                queryBean.setElectionID(electionId);

                
                ElectionBean election = electionDAO.getElectionById(queryBean);

                if (election != null) {
                    
                    java.time.LocalDateTime now = java.time.LocalDateTime.now();
                    if (now.isBefore(election.getEndDate())) {
                        
                        response.sendRedirect(request.getContextPath() + "/student_vote?id=" + electionId);
                        return;
                    }

                    
                    Map<String, Integer> stats = electionDAO.getElectionStatistics(queryBean);
                    
                    
                    int totalVotes = 0;
                    if (stats != null) {
                        for (Integer votes : stats.values()) {
                            totalVotes += votes;
                        }
                    }
                    
                  
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