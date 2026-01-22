package controller;

import bean.StudentBean;
import bean.FacultyBean;
import dao.StudentDAO;
import dao.FacultyDAO;
import dao.UserProfileDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManageStudentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        
        HttpSession userSession = request.getSession(false);
        String staffNumber = null;
        
        if (userSession != null && userSession.getAttribute("staffNumber") != null) {
            staffNumber = (String) userSession.getAttribute("staffNumber");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        StudentDAO studentDAO = new StudentDAO();
        FacultyDAO facultyDAO = new FacultyDAO();
        UserProfileDAO userDAO = new UserProfileDAO();
        
        try {
            
            ArrayList<FacultyBean> facultyList = facultyDAO.getAllFaculties();
            request.setAttribute("facultyList", facultyList);
            
            
            String facultyIdParam = request.getParameter("facultyId");
            ArrayList<StudentBean> studentList;
            
            if (facultyIdParam != null && !facultyIdParam.isEmpty()) {
                
                int facultyId = Integer.parseInt(facultyIdParam);
                studentList = studentDAO.fetchStudentsByFaculty(facultyId);
            } else {
                
                studentList = studentDAO.fetchAllStudents();
            }
            
            request.setAttribute("studentList", studentList);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading students: " + e.getMessage());
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manageStudent.jsp");
        dispatcher.forward(request, response);
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
        return "Manage Student Servlet";
    }
}