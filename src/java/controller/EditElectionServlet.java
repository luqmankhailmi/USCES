package controller;

import bean.ElectionBean;
import dao.ElectionDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class EditElectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String method = request.getMethod();

        
        if (method.equalsIgnoreCase("GET")) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int electionId = Integer.parseInt(idParam);
                    ElectionDAO electionDAO = new ElectionDAO();
                    ElectionBean election = electionDAO.getElectionById(electionId);
                    
                    if (election != null) {
                        request.setAttribute("election", election);
                        // Forward to the JSP to display the form
                        RequestDispatcher rd = request.getRequestDispatcher("/admin/editElection.jsp");
                        rd.forward(request, response);
                    } else {
                        request.setAttribute("errMessage", "Election not found.");
                        RequestDispatcher rd = request.getRequestDispatcher("/admin_list_election");
                        rd.forward(request, response);
                    }
                } catch (Exception e) {
                    request.setAttribute("errMessage", "Error loading election data.");
                    RequestDispatcher rd = request.getRequestDispatcher("/admin_list_election");
                    rd.forward(request, response);
                }
            }
        } 
        
        
        else if (method.equalsIgnoreCase("POST")) {
            try {
                int electionId = Integer.parseInt(request.getParameter("electionId"));
                String electionName = request.getParameter("electionName");
                
                String startStr = request.getParameter("startDate").replace(" ", "T"); 
                String endStr = request.getParameter("endDate").replace(" ", "T");

                
                ElectionBean election = new ElectionBean();
                election.setElectionID(electionId);
                election.setElectionName(electionName);
                election.setStartDate(LocalDateTime.parse(startStr));
                election.setEndDate(LocalDateTime.parse(endStr));

                ElectionDAO dao = new ElectionDAO();
                boolean isUpdated = dao.updateElection(election);

                if (isUpdated) {
                    
                    request.setAttribute("successMsg", "Election updated successfully!");
                    
                    RequestDispatcher rd = request.getRequestDispatcher("/admin_list_election");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("errMessage", "Database update failed.");
                    request.setAttribute("election", election); 
                    RequestDispatcher rd = request.getRequestDispatcher("/admin/editElection.jsp");
                    rd.forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errMessage", "Invalid data provided: " + e.getMessage());
                RequestDispatcher rd = request.getRequestDispatcher("/admin_list_election");
                rd.forward(request, response);
            }
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