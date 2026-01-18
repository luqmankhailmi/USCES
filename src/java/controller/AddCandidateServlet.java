package controller;

import bean.ElectionBean;
import bean.StudentBean;
import dao.CandidateDAO;
import dao.ElectionDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddCandidateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            // Unauthorized access, redirect back to login
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
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
                 // CHANGE THIS: From adminDashboard.jsp to your actual dashboard name
                 response.sendRedirect(request.getContextPath() + "/admin_list_election.jsp?error=load_failed");
             }

        } else if (method.equalsIgnoreCase("POST")) {
            try {
                // Get parameters from form
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                // Fixed: Matching the 'electionId' name usually used in JSPs
                int electionId = Integer.parseInt(request.getParameter("electionId"));
                String manifesto = request.getParameter("manifesto"); // Get the text area content

                CandidateDAO dao = new CandidateDAO();

                // Call the updated method with 3 arguments
                boolean isAdded = dao.registerCandidate(studentId, electionId, manifesto);

                if (isAdded) {
                    response.sendRedirect(request.getContextPath() + "/ManageCandidateServlet?msg=CandidateAdded");
                } else {
                    response.sendRedirect(request.getContextPath() + "/AddCandidateServlet?error=db_error");
                }
            } catch (NumberFormatException e) {
                // Redirect to actual dashboard to avoid 404
                response.sendRedirect(request.getContextPath() + "/admin_list_election.jsp?error=invalid_params");
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