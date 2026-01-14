/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import bean.CandidateBean;
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
public class CandidateDetailServlet extends HttpServlet {

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
            // 1. Retrieve the candidate ID from the request parameter
            String idParam = request.getParameter("id");
            
            if (idParam != null && !idParam.isEmpty()) {
                int candidateId = Integer.parseInt(idParam);
                
                // 2. Call the DAO to get full details (Student + Faculty + Manifesto)
                CandidateDAO dao = new CandidateDAO();
                CandidateBean candidate = dao.getCandidateById(candidateId);

                if (candidate != null) {
                    // 3. Set attributes that match the variable names in your viewCandidate.jsp
                    request.setAttribute("candidateId", candidate.getCandidateId());
                    request.setAttribute("studentName", candidate.getStudentName());
                    request.setAttribute("studentNumber", candidate.getStudentNumber());
                    request.setAttribute("studentEmail", candidate.getStudentEmail());
                    request.setAttribute("facultyName", candidate.getFacultyName());
                    request.setAttribute("electionName", candidate.getElectionName());
                    request.setAttribute("manifestoContent", candidate.getManifestoContent());
                } else {
                    request.setAttribute("error", "Candidate not found in the system.");
                }
            } else {
                request.setAttribute("error", "No Candidate ID provided.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid ID format.");
        } catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
        }

        // 4. Forward the request to the JSP page
        request.getRequestDispatcher("admin/viewCandidate.jsp").forward(request, response);
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
