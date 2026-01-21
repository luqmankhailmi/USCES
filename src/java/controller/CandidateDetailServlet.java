package controller;

import bean.CandidateBean;
import dao.CandidateDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CandidateDetailServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // 1. Get the ID from the URL (e.g., ?id=2)
            String idParam = request.getParameter("id");
            
            if (idParam != null && !idParam.isEmpty()) {
                int candidateId = Integer.parseInt(idParam);
                
                // 2. STRICT MVC: Create a query bean to carry the ID
                CandidateBean queryBean = new CandidateBean();
                queryBean.setCandidateId(candidateId);
                
                // 3. Fetch data using the updated DAO method signature
                CandidateDAO dao = new CandidateDAO();
                // FIX: Pass the queryBean object instead of the raw int
                CandidateBean candidate = dao.getCandidateById(queryBean);

                if (candidate != null) {
                    // 4. IMPROVED: Set the entire object as an attribute
                    // This is cleaner than setting 5 different strings
                    request.setAttribute("candidate", candidate);

                    // 5. Forward to the edit page
                    request.getRequestDispatcher("admin/editCandidate.jsp").forward(request, response);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Fallback to the list if ID is missing or candidate isn't found
        response.sendRedirect("ManageCandidateServlet");
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

    @Override
    public String getServletInfo() {
        return "Handles Candidate viewing and routing to edit mode";
    }
}