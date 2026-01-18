package controller;

import bean.FacultyBean;
import dao.FacultyDAO;
import dao.ElectionDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddElectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession userSession = request.getSession(false);
        String staffNumber = null;
        
        if (userSession != null && userSession.getAttribute("staffNumber") != null) {
            staffNumber = (String) userSession.getAttribute("staffNumber");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String method = request.getMethod();

        if (method.equalsIgnoreCase("GET")) {
            try {
                FacultyDAO facultyDao = new FacultyDAO();
                List<FacultyBean> facultyList = facultyDao.getAllFaculties();
                
                request.setAttribute("facultyList", facultyList);
                // Ensure this path matches your Web Pages folder structure
                request.getRequestDispatcher("/admin/addElection.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                // Redirecting to the Servlet mapping is safer than the JSP file
                response.sendRedirect(request.getContextPath() + "/admin_list_election?error=load_failed");
            }

        } else if (method.equalsIgnoreCase("POST")) {
            try {
                String electionName = request.getParameter("electionName");
                String facultyIdStr = request.getParameter("facultyId");
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");

                // Basic Validation: Prevent NullPointerException or NumberFormatException
                if (electionName == null || facultyIdStr == null || startDate == null || endDate == null) {
                    response.sendRedirect(request.getContextPath() + "/AddElectionServlet?error=missing_data");
                    return;
                }

                int facultyId = Integer.parseInt(facultyIdStr);

                ElectionDAO electionDao = new ElectionDAO();
                boolean isAdded = electionDao.addElection(electionName, facultyId, startDate, endDate);

                if (isAdded) {
                    // Redirect to the ALIST servlet mapping as defined in your web.xml
                    response.sendRedirect(request.getContextPath() + "/admin_list_election?msg=success");
                } else {
                    response.sendRedirect(request.getContextPath() + "/AddElectionServlet?error=db_error");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/AddElectionServlet?error=invalid_id");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/AddElectionServlet?error=unknown");
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

    @Override
    public String getServletInfo() {
        return "Handles election creation and faculty data loading";
    }
}