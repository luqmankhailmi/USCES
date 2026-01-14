/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import dao.CandidateDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP
 */
public class AddCandidateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       try {
            // 1. Get parameters from the form/request
            // Using studentId and electionId passed from the JSP
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int electionId = Integer.parseInt(request.getParameter("electionId"));
            
            // 2. Initialize DAO and call the register method
            CandidateDAO dao = new CandidateDAO();
            boolean isAdded = dao.registerCandidate(studentId, electionId);
            
            if (isAdded) {
                // Redirect back to the View Election page to see the new candidate list
                response.sendRedirect("ViewElectionServlet?id=" + electionId + "&msg=CandidateAdded");
            } else {
                // Redirect back to add page with an error message
                response.sendRedirect("admin/addCandidate.jsp?electionId=" + electionId + "&error=db_error");
            }
            
        } catch (NumberFormatException e) {
            // Handle cases where IDs are missing or not numbers
            response.sendRedirect("adminDashboard.jsp?error=invalid_params");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
