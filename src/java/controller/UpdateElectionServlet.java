package controller;

import dao.ElectionDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UpdateElectionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Get data from the editElection.jsp form fields
            int electionId = Integer.parseInt(request.getParameter("electionId"));
            String name = request.getParameter("electionName");
            String start = request.getParameter("startDate");
            String end = request.getParameter("endDate");

            // 2. Call the DAO to update the database
            ElectionDAO dao = new ElectionDAO();
            boolean success = dao.updateElection(electionId, name, start, end);

            if (success) {
                // 3. Redirect back to the list with a success message
                response.sendRedirect(request.getContextPath() + "/admin_list_election?msg=updated");
            } else {
                response.sendRedirect("EditElectionServlet?id=" + electionId + "&error=failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("AListElectionServlet?error=exception");
        }
    }
}