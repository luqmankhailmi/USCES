<%@page import="bean.ElectionBean"%>
<%@page import="bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Candidate</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/editCandidate.css">
</head>
<body>
    <!-- Updated Header to Match Manage Candidate -->
    <header class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-user-plus"></i>
                Add New Candidate
            </h1>
            <div class="header-menu">
                <a href="${pageContext.request.contextPath}/admin_list_election" class="header-link">
                    <i class="fas fa-arrow-left"></i> Back to Dashboard
                </a>
            </div>
        </div>
    </header>

    <div class="form-container">
        <%-- Display error/success messages --%>
        <% if(request.getAttribute("errMessage") != null) { %>
            <div class="msg-error"><%= request.getAttribute("errMessage") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/AddCandidateServlet" method="POST">
            
            <!-- Election Dropdown (Primary Selection) -->
            <div class="form-group">
                <label>
                    <i class="fas fa-vote-yea"></i> Select Election Programme
                </label>
                <select name="electionId" id="electionSelect" required>
                    <option value="" disabled selected>-- Choose Election --</option>
                    <% 
                        List<ElectionBean> elections = (List<ElectionBean>) request.getAttribute("electionList");
                        if(elections != null) {
                            for(ElectionBean e : elections) {
                    %>
                        <option value="<%= e.getElectionID() %>" data-faculty="<%= e.getFacultyID() %>">
                            <%= e.getElectionName() %>
                        </option>
                    <%      }
                        } 
                    %>
                </select>
            </div>

            <!-- Student Dropdown (Filtered by Faculty) -->
            <div class="form-group">
                <label>
                    <i class="fas fa-user-graduate"></i> Select Student Candidate
                </label>
                <select name="studentId" id="studentSelect" required disabled>
                    <option value="" disabled selected>-- First, select an election --</option>
                    <% 
                        List<StudentBean> students = (List<StudentBean>) request.getAttribute("studentList");
                        if(students != null) {
                            for(StudentBean s : students) {
                    %>
                        <option value="<%= s.getStudentId() %>" 
                                data-faculty="<%= s.getFacultyId() %>" 
                                class="student-option" 
                                style="display:none;">
                            <%= s.getStudentName() %> - <%= s.getStudentNumber() %>
                        </option>
                    <%      }
                        } 
                    %>
                </select>
            </div>

            <!-- Manifesto Textarea -->
            <div class="form-group">
                <label>
                    <i class="fas fa-scroll"></i> Candidate Manifesto
                </label>
                <textarea name="manifesto" placeholder="Enter candidate's vision and goals..." required></textarea>
            </div>

            <button type="submit" class="btn">
                <i class="fas fa-check-circle"></i> Add Candidate
            </button>
            <a href="${pageContext.request.contextPath}/ManageCandidateServlet" class="back-link">
                <i class="fas fa-times-circle"></i> Cancel
            </a>
        </form>
    </div>

    <script>
        // Cascading Dropdown Logic
        const electionSelect = document.getElementById('electionSelect');
        const studentSelect = document.getElementById('studentSelect');
        const studentOptions = document.querySelectorAll('.student-option');

        electionSelect.addEventListener('change', function() {
            const selectedFacultyId = this.options[this.selectedIndex].getAttribute('data-faculty');
            
            // Reset student dropdown
            studentSelect.value = '';
            studentSelect.disabled = false;
            
            // Clear existing options except the placeholder
            studentSelect.innerHTML = '<option value="" disabled selected>-- Select a student --</option>';
            
            // Filter and show only students from the selected faculty
            let hasStudents = false;
            studentOptions.forEach(option => {
                const studentFacultyId = option.getAttribute('data-faculty');
                
                if (studentFacultyId === selectedFacultyId) {
                    // Clone and add the matching option
                    const clonedOption = option.cloneNode(true);
                    clonedOption.style.display = 'block';
                    studentSelect.appendChild(clonedOption);
                    hasStudents = true;
                }
            });
            
            // If no students found for this faculty
            if (!hasStudents) {
                studentSelect.innerHTML = '<option value="" disabled selected>-- No students found for this faculty --</option>';
                studentSelect.disabled = true;
            }
        });
    </script>
</body>
</html>