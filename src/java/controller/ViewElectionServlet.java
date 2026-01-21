package controller;

import bean.CandidateBean;
import bean.ElectionBean;
import dao.ElectionDAO;
import dao.CandidateDAO;
import dao.AdminProfileDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewElectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
       
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int electionId = Integer.parseInt(idParam);
                
               
                ElectionDAO electionDao = new ElectionDAO();
                CandidateDAO candidateDao = new CandidateDAO();
                AdminProfileDAO adminDAO = new AdminProfileDAO();
                
                
                ElectionBean election = electionDao.getElectionById(electionId);
                
                if (election != null) {
                    Map<String, Integer> statistics = electionDao.getElectionStatistics(electionId);
                    ArrayList<CandidateBean> candidateList = candidateDao.fetchCandidatesByElection(electionId);
                    String facultyName = adminDAO.getFaculty(election.getFacultyID());

                    
                    request.setAttribute("election", election);
                    request.setAttribute("facultyName", facultyName);
                    request.setAttribute("stats", statistics);
                    request.setAttribute("candidateList", candidateList);

                    
                    RequestDispatcher rd = request.getRequestDispatcher("/admin/viewElection.jsp");
                    rd.forward(request, response);
                } else {
                   
                    request.setAttribute("errMessage", "The requested election does not exist.");
                    forwardToList(request, response);
                }
                
            } catch (NumberFormatException e) {
                request.setAttribute("errMessage", "Invalid Election ID format.");
                forwardToList(request, response);
            }
        } else {
            forwardToList(request, response);
        }
    }

    /**
     * Helper method to forward back to the list controller
     */
    private void forwardToList(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/admin_list_election");
        rd.forward(request, response);
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