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
            
            String idParam = request.getParameter("id");
            
            if (idParam != null && !idParam.isEmpty()) {
                int candidateId = Integer.parseInt(idParam);
                
                
                CandidateBean queryBean = new CandidateBean();
                queryBean.setCandidateId(candidateId);
                
                
                CandidateDAO dao = new CandidateDAO();
                
                CandidateBean candidate = dao.getCandidateById(queryBean);

                if (candidate != null) {
                    
                    request.setAttribute("candidate", candidate);

                    
                    request.getRequestDispatcher("admin/editCandidate.jsp").forward(request, response);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
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