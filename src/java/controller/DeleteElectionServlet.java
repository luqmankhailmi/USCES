/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
public class DeleteElectionServlet extends HttpServlet {

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
        // 1. Get the election ID from the request
    String electionIdStr = request.getParameter("id");
    
    if (electionIdStr != null && !electionIdStr.isEmpty()) {
        try {
            int electionId = Integer.parseInt(electionIdStr);
            
            // 2. Call the DAO to delete the record
            dao.ElectionDAO electionDAO = new dao.ElectionDAO();
            boolean isDeleted = electionDAO.deleteElection(electionId);
            
            if (isDeleted) {
                // 3. Redirect back to the dashboard on success
                response.sendRedirect(request.getContextPath() + "/admin_list_election?status=deleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin_list_election?error=notfound");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin_list_election?error=invalidid");
        }
    } else {
        response.sendRedirect(request.getContextPath() + "/admin_list_election");
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
