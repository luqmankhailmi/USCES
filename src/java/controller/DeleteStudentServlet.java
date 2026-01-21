package controller;

import dao.StudentDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteStudentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Check session
        HttpSession userSession = request.getSession(false);
        if (userSession == null || userSession.getAttribute("staffNumber") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String studentIdParam = request.getParameter("studentId");
        
        if (studentIdParam != null && !studentIdParam.isEmpty()) {
            try {
                int studentId = Integer.parseInt(studentIdParam);
                StudentDAO studentDAO = new StudentDAO();
                
                boolean deleted = studentDAO.deleteStudent(studentId);
                
                if (deleted) {
                    response.sendRedirect(request.getContextPath() + 
                        "/ManageStudentServlet?success=Student deleted successfully");
                } else {
                    response.sendRedirect(request.getContextPath() + 
                        "/ManageStudentServlet?error=Failed to delete student");
                }
                
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + 
                    "/ManageStudentServlet?error=Invalid student ID");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + 
                    "/ManageStudentServlet?error=Error: " + e.getMessage());
            }
        } else {
            response.sendRedirect(request.getContextPath() + 
                "/ManageStudentServlet?error=No student ID provided");
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
        return "Delete Student Servlet";
    }
}