<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.StudentBean" %>
<%@ page import="bean.FacultyBean" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Students - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/manageStudent.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <%
        
        HttpSession userSession = request.getSession(false);
        String staffNumber = null;
        
        if (userSession != null && userSession.getAttribute("staffNumber") != null) {
            staffNumber = (String) userSession.getAttribute("staffNumber");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        
        ArrayList<StudentBean> studentList = (ArrayList<StudentBean>) request.getAttribute("studentList");
        ArrayList<FacultyBean> facultyList = (ArrayList<FacultyBean>) request.getAttribute("facultyList");
        String adminName = (String) request.getAttribute("adminName");
        
        
        String successMsg = request.getParameter("success");
        String errorMsg = request.getParameter("error");
    %>
    
    <%-- Success/Error Alert --%>
    <% if (successMsg != null) { %>
        <div class="alert-container">
            <div class="alert-box success">
                <i class="fas fa-check-circle"></i>
                <span><%= successMsg %></span>
                <button class="close-alert" onclick="this.parentElement.parentElement.style.display='none'">&times;</button>
            </div>
        </div>
    <% } %>
    
    <% if (errorMsg != null) { %>
        <div class="alert-container">
            <div class="alert-box error">
                <i class="fas fa-exclamation-circle"></i>
                <span><%= errorMsg %></span>
                <button class="close-alert" onclick="this.parentElement.parentElement.style.display='none'">&times;</button>
            </div>
        </div>
    <% } %>
    
    <!-- Header -->
    <header class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-user-graduate"></i>
                Manage Students
            </h1>
            <div class="header-menu">
                <a href="${pageContext.request.contextPath}/admin_list_election" class="header-link">
                    <i class="fas fa-arrow-left"></i> Back to Dashboard
                </a>
            </div>
        </div>
    </header>
    
    <div class="container">
        <!-- Filter Section -->
        <div class="filter-section">
            <div class="filter-header">
                <h2><i class="fas fa-filter"></i> Filter Students</h2>
            </div>
            <div class="filter-content">
                <form method="GET" action="${pageContext.request.contextPath}/ManageStudentServlet">
                    <div class="filter-group">
                        <label for="facultyFilter">Faculty:</label>
                        <select name="facultyId" id="facultyFilter" onchange="this.form.submit()">
                            <option value="">All Faculties</option>
                            <%
                                String selectedFacultyId = request.getParameter("facultyId");
                                if (facultyList != null) {
                                    for (FacultyBean faculty : facultyList) {
                                        String selected = "";
                                        if (selectedFacultyId != null && selectedFacultyId.equals(String.valueOf(faculty.getFacultyID()))) {
                                            selected = "selected";
                                        }
                            %>
                                <option value="<%= faculty.getFacultyID() %>" <%= selected %>>
                                    <%= faculty.getFacultyName() %>
                                </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </form>
            </div>
        </div>
        
        <!-- Students Table -->
        <div class="students-section">
            <%
                if (studentList != null && !studentList.isEmpty()) {
            %>
                <div class="table-wrapper">
                    <table class="students-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Student Name</th>
                                <th>Student Number</th>
                                <th>Faculty</th>
                                <th>Email</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (StudentBean student : studentList) {
                            %>
                            <tr>
                                <td><strong>#<%= student.getStudentId() %></strong></td>
                                <td class="student-name"><%= student.getStudentName() %></td>
                                <td><%= student.getStudentNumber() %></td>
                                <td>
                                    <%
                                        // Find faculty name for this student
                                        String facultyName = "Unknown";
                                        if (facultyList != null) {
                                            for (FacultyBean faculty : facultyList) {
                                                if (faculty.getFacultyID() == student.getFacultyId()) {
                                                    facultyName = faculty.getFacultyName();
                                                    break;
                                                }
                                            }
                                        }
                                    %>
                                    <%= facultyName %>
                                </td>
                                <td><%= student.getStudentEmail() %></td>
                                <td class="action-buttons">
                                    <button class="btn-action btn-delete" 
                                            onclick="confirmDelete(<%= student.getStudentId() %>, '<%= student.getStudentName() %>')">
                                        <i class="fas fa-trash-alt"></i> Delete
                                    </button>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            <%
                } else {
            %>
                <div class="no-data">
                    <i class="fas fa-user-slash"></i>
                    <p>No students found.</p>
                </div>
            <%
                }
            %>
        </div>
    </div>
    
    <!-- Delete Confirmation Modal -->
    <div id="deleteModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3><i class="fas fa-exclamation-triangle"></i> Confirm Delete</h3>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to delete student <strong id="studentNameDisplay"></strong>?</p>
                <p class="warning-text">This action cannot be undone.</p>
            </div>
            <div class="modal-footer">
                <button class="btn-cancel" onclick="closeModal()">Cancel</button>
                <form id="deleteForm" method="POST" action="${pageContext.request.contextPath}/DeleteStudentServlet" style="display: inline;">
                    <input type="hidden" name="studentId" id="studentIdInput">
                    <button type="submit" class="btn-confirm">Delete</button>
                </form>
            </div>
        </div>
    </div>
    
    <script>
        function confirmDelete(studentId, studentName) {
            document.getElementById('studentIdInput').value = studentId;
            document.getElementById('studentNameDisplay').textContent = studentName;
            document.getElementById('deleteModal').style.display = 'flex';
        }
        
        function closeModal() {
            document.getElementById('deleteModal').style.display = 'none';
        }
        
        // Close modal when clicking outside
        window.onclick = function(event) {
            const modal = document.getElementById('deleteModal');
            if (event.target == modal) {
                closeModal();
            }
        }
    </script>
</body>
</html>