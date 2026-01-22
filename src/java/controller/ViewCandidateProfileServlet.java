package controller;

import bean.CandidateBean;
import dao.CandidateDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewCandidateProfileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("studentNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
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
                    request.getRequestDispatcher("/user/viewCandidateProfile.jsp").forward(request, response);
                    return;
                } else {
                    request.setAttribute("errorMessage", "Candidate not found.");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid candidate ID.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid candidate ID format.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving candidate information.");
        }
        
        
        response.sendRedirect(request.getContextPath() + "/user_list_election");
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
        return "Handles viewing candidate profile and manifesto for students";
    }
}
