package controller;

import bean.ElectionBean;
import dao.ElectionDAO;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.RequestDispatcher; // Required for strict MVC
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateElectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        if (request.getMethod().equalsIgnoreCase("POST")) {
            try {
                
                int electionId = Integer.parseInt(request.getParameter("electionId"));
                String name = request.getParameter("electionName");
                String start = request.getParameter("startDate");
                String end = request.getParameter("endDate");

                
                ElectionBean election = new ElectionBean();
                election.setElectionID(electionId);
                election.setElectionName(name);

                if (start != null && !start.isEmpty()) {
                    String formattedStart = start.replace("T", " ");
                    if (formattedStart.length() == 16) formattedStart += ":00";
                    election.setStartDate(Timestamp.valueOf(formattedStart).toLocalDateTime());
                }

                if (end != null && !end.isEmpty()) {
                    String formattedEnd = end.replace("T", " ");
                    if (formattedEnd.length() == 16) formattedEnd += ":00";
                    election.setEndDate(Timestamp.valueOf(formattedEnd).toLocalDateTime());
                }

                
                ElectionDAO dao = new ElectionDAO();
                boolean success = dao.updateElection(election);

                if (success) {
                    
                    request.setAttribute("successMsg", "Election updated successfully!");
                    
                    
                    RequestDispatcher rd = request.getRequestDispatcher("/admin_list_election");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("errMessage", "Failed to update election record.");
                    
                    RequestDispatcher rd = request.getRequestDispatcher("/EditElectionServlet?id=" + electionId);
                    rd.forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errMessage", "Error: " + e.getMessage());
                RequestDispatcher rd = request.getRequestDispatcher("/admin_list_election");
                rd.forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin_list_election");
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