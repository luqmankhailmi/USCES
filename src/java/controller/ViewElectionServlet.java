/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import bean.CandidateBean;
import bean.ElectionBean;
import bean.CandidateBean;
import dao.ElectionDAO;
import dao.CandidateDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP
 */
public class ViewElectionServlet extends HttpServlet {

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
    // 1. Get the election_id from the request
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int electionId = Integer.parseInt(idParam);
                
                // 2. Initialize DAOs
                ElectionDAO electionDao = new ElectionDAO();
                CandidateDAO candidateDao = new CandidateDAO();
                
                // 3. Fetch data from DAOs
                // Fetches basic election info (Name, dates, etc.)
                ElectionBean election = electionDao.getElectionById(electionId);
                
                // Fetches live vote counts for each candidate
                Map<String, Integer> statistics = electionDao.getElectionStatistics(electionId);
                
                // Fetches the list of candidates assigned to this election
                ArrayList<CandidateBean> candidateList = candidateDao.fetchCandidatesByElection(electionId);
                
               if (election != null) {
                // 1. Initialize Admin DAO to get faculty name
                dao.AdminProfileDAO adminDAO = new dao.AdminProfileDAO();
                String facultyName = adminDAO.getFaculty(election.getFacultyID());

                // 2. Set the attributes
                request.setAttribute("election", election);
                request.setAttribute("facultyName", facultyName); // Pass name instead of ID
                request.setAttribute("stats", statistics);
                request.setAttribute("candidateList", candidateList);

                request.getRequestDispatcher("/admin/viewElection.jsp").forward(request, response);
                } else {
                    // If the election ID doesn't exist in the database
                    response.sendRedirect(request.getContextPath() + "/admin_list_election?error=NotFound");
                }
                
            } catch (NumberFormatException e) {
                // If the ID parameter is not a valid number
                response.sendRedirect(request.getContextPath() + "/admin_list_election?error=InvalidID");
            }
        } else {
            // If no ID was provided in the URL
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
