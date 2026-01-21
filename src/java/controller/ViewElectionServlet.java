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
import java.time.format.DateTimeFormatter; // ADD THIS IMPORT
import java.time.LocalDateTime;


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
                
                
                ElectionBean queryBean = new ElectionBean();
                queryBean.setElectionID(electionId);
                
                
                ElectionBean election = electionDao.getElectionById(queryBean);
                
                if (election != null) {
                    if (election != null) {
            // --- FIXED DATE FORMATTING LOGIC ---
                    try {
                        // Define the professional display format
                        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy, hh:mm a");

                        // Get LocalDateTime objects directly from the bean
                        LocalDateTime start = election.getStartDate();
                        LocalDateTime end = election.getEndDate();

                        // Format them into Strings for the JSP
                        if (start != null && end != null) {
                            request.setAttribute("formattedStart", start.format(outputFormat));
                            request.setAttribute("formattedEnd", end.format(outputFormat));
                        }
                    } catch (Exception e) {
                        // Fallback to raw objects if formatting fails
                        request.setAttribute("formattedStart", election.getStartDate());
                        request.setAttribute("formattedEnd", election.getEndDate());
                    }
                    Map<String, Integer> statistics = electionDao.getElectionStatistics(queryBean);
                    ArrayList<CandidateBean> candidateList = candidateDao.fetchCandidatesByElection(queryBean);
                    
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
                }
                
            } catch (NumberFormatException e) {
                request.setAttribute("errMessage", "Invalid Election ID format.");
                forwardToList(request, response);
            }
        } else {
            forwardToList(request, response);
        }
    }

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