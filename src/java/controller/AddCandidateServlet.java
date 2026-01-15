package controller;

import bean.ElectionBean;
import bean.StudentBean;
import dao.CandidateDAO;
import dao.ElectionDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCandidateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String method = request.getMethod(); 

        if (method.equalsIgnoreCase("GET")) {
            try {
                // Initialize DAOs
                CandidateDAO candidateDao = new CandidateDAO(); 
                ElectionDAO electionDao = new ElectionDAO();

                // 1. Fetch data for dropdowns
                // Ensure electionDao.getAllElections() is implemented
                List<ElectionBean> electionList = electionDao.getAllElections();
                
                // Ensure candidateDao.getAllStudents() uses StudentBean
                List<StudentBean> studentList = candidateDao.getAllStudents(); 

                // 2. Pass data to JSP via request attributes
                request.setAttribute("electionList", electionList);
                request.setAttribute("studentList", studentList);

                // 3. Forward to the view
                request.getRequestDispatcher("/admin/addCandidate.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/adminDashboard.jsp?error=load_failed");
            }

        } else if (method.equalsIgnoreCase("POST")) {
            try {
                // Get parameters from form
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                int electionId = Integer.parseInt(request.getParameter("electionId"));

                CandidateDAO dao = new CandidateDAO();
                
                // registerCandidate handles database transactions
                boolean isAdded = dao.registerCandidate(studentId, electionId);

                if (isAdded) {
                    response.sendRedirect(request.getContextPath() + "/ManageCandidateServlet?msg=CandidateAdded");
                } else {
                    response.sendRedirect(request.getContextPath() + "/AddCandidateServlet?error=db_error");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/adminDashboard.jsp?error=invalid_params");
            }
        }
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
}