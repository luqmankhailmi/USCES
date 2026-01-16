package controller;
import dao.CandidateDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet; // Ensure this is present if not using web.xml
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateCandidateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            // 1. Get parameters from the form in editCandidate.jsp
            // Matches names: candidateId, manifestoId, manifestoContent
            String cIdStr = request.getParameter("candidateId");
            String mIdStr = request.getParameter("manifestoId");
            String newContent = request.getParameter("manifestoContent");

            if (cIdStr != null && mIdStr != null) {
                int candidateId = Integer.parseInt(cIdStr);
                int manifestoId = Integer.parseInt(mIdStr);

                // 2. Execute the update via DAO
                CandidateDAO dao = new CandidateDAO();
                boolean isUpdated = dao.updateManifesto(manifestoId, newContent);

                if (isUpdated) {
                    // Success: Redirect back to the detail view through the Servlet
                    // Using "id" to match CandidateDetailServlet's expected parameter
                    response.sendRedirect("CandidateDetailServlet?id=" + candidateId + "&msg=update_success");
                } else {
                    // Failure: Go back to edit page via the DetailServlet mode
                    response.sendRedirect("CandidateDetailServlet?id=" + candidateId + "&mode=edit&error=db_error");
                }
            } else {
                response.sendRedirect("AListElectionServlet?error=missing_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect to the main list if something breaks completely
            response.sendRedirect("AListElectionServlet?error=invalid_input");
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