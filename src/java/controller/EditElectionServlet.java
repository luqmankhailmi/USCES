package controller;

import bean.ElectionBean;
import dao.ElectionDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditElectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get the ID from the URL
        String idParam = request.getParameter("id");
        
        if (idParam != null) {
            try {
                int electionId = Integer.parseInt(idParam);
                ElectionDAO electionDAO = new ElectionDAO();
                
                // Get election details from DAO
                ElectionBean election = electionDAO.getElectionById(electionId);
                
                if (election != null) {
                    // Send the object to the JSP
                    request.setAttribute("election", election);
                    request.getRequestDispatcher("/admin/editElection.jsp").forward(request, response);
                } else {
                    response.sendRedirect("AListElectionServlet");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("AListElectionServlet");
            }
        }
    }

    // No processRequest needed. 
    // If a POST hits this servlet, we just ignore it or send it to doGet.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}