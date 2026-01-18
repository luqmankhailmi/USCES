package controller;

import bean.ElectionBean;
import dao.ElectionDAO;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateElectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. LOGIN CHECK
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            // Unauthorized access, redirect back to login
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String method = request.getMethod();

        // 2. HANDLE POST: Process the update submitted from the form
        if (method.equalsIgnoreCase("POST")) {
            try {
                // Get data from the editElection.jsp form fields
                int electionId = Integer.parseInt(request.getParameter("electionId"));
                String name = request.getParameter("electionName");
                String start = request.getParameter("startDate");
                String end = request.getParameter("endDate");

                ElectionBean election = new ElectionBean();
                election.setElectionID(electionId);
                election.setElectionName(name);

                // FIX DATA TYPE MISMATCH: Convert String -> Timestamp -> LocalDateTime
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

                // 2. Call the DAO to update the database
                ElectionDAO dao = new ElectionDAO();
                boolean success = dao.updateElection(election);

                if (success) {
                    // 3. Redirect back to the list with a success message
                    response.sendRedirect(request.getContextPath() + "/admin_list_election?msg=updated");
                } else {
                    response.sendRedirect("EditElectionServlet?id=" + electionId + "&error=failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/admin_list_election?error=exception");
            }
        } else {
            // If it's a GET request, redirect to the list as this servlet only handles updates
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