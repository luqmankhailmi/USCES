package controller;

import bean.CandidateBean;
import bean.ElectionBean;
import bean.StudentBean;
import dao.CandidateDAO;
import dao.ElectionDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddCandidateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String method = request.getMethod(); 

        if (method.equalsIgnoreCase("GET")) {
            loadDropdowns(request);
            
            RequestDispatcher rd = request.getRequestDispatcher("/admin/addCandidate.jsp");
            rd.forward(request, response);

        } else if (method.equalsIgnoreCase("POST")) {
            try {
                
                String sIdStr = request.getParameter("studentId");
                String eIdStr = request.getParameter("electionId");
                String manifesto = request.getParameter("manifesto");

                
                if (sIdStr == null || eIdStr == null || sIdStr.isEmpty()) {
                    throw new Exception("Please select a student and an election.");
                }

                int studentId = Integer.parseInt(sIdStr);
                int electionId = Integer.parseInt(eIdStr);

                
                CandidateBean bean = new CandidateBean();
                bean.setStudentId(studentId);
                bean.setElectionId(electionId);
                bean.setManifestoContent(manifesto);

                
                request.setAttribute("candidateBean", bean);

                
                CandidateDAO dao = new CandidateDAO();
                boolean status = dao.registerCandidate(studentId, electionId, manifesto);

                if (status) {
                    request.setAttribute("successMsg", "Candidate added successfully!");

                    
                    RequestDispatcher rd = request.getRequestDispatcher("/ManageCandidateServlet");
                    rd.forward(request, response);

                    
                } else {
                    request.setAttribute("errMessage", "Failed to add candidate. Duplicate entry?");
                    loadDropdowns(request);
                    RequestDispatcher rd = request.getRequestDispatcher("/admin/addCandidate.jsp");
                    rd.forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errMessage", "Error: " + e.getMessage());
                loadDropdowns(request);
                RequestDispatcher rd = request.getRequestDispatcher("/admin/addCandidate.jsp");
                rd.forward(request, response);
            }
        }
    }

    private void loadDropdowns(HttpServletRequest request) {
        CandidateDAO cDao = new CandidateDAO(); 
        ElectionDAO eDao = new ElectionDAO();
        request.setAttribute("electionList", eDao.getAllElections());
        request.setAttribute("studentList", cDao.getAllStudents());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}