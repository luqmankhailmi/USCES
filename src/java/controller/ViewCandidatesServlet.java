package controller;

import bean.CandidateBean;
import bean.ElectionBean;
import dao.CandidateDAO;
import dao.ElectionDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewCandidatesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Check session
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("studentNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        try {
            String idParam = request.getParameter("id");
            
            if (idParam != null && !idParam.isEmpty()) {
                int electionId = Integer.parseInt(idParam);
                
                ElectionDAO electionDAO = new ElectionDAO();
                CandidateDAO candidateDAO = new CandidateDAO();
                
                ElectionBean queryBean = new ElectionBean();
                queryBean.setElectionID(electionId);
                
                ElectionBean election = electionDAO.getElectionById(queryBean);
                
                if (election != null) {
                    ArrayList<CandidateBean> candidateList = candidateDAO.fetchCandidatesByElection(queryBean);
                    
                    request.setAttribute("election", election);
                    request.setAttribute("candidateList", candidateList);
                    
                    request.getRequestDispatcher("/user/viewCandidates.jsp").forward(request, response);
                    return;
                } else {
                    request.setAttribute("errorMessage", "Election not found.");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid election ID.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid election ID format.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving candidates.");
        }
        
        // If there's an error, redirect back to dashboard
        response.sendRedirect(request.getContextPath() + "/user_list_election");
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
        return "Handles viewing candidates for an election";
    }
}
