<%@page import="bean.ElectionBean"%>
<%@page import="bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Candidate</title>
    <%-- Linking to the same CSS file you provided in the reference --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/editCandidate.css">
</head>
<body>
    <div class="header">
        <div class="header-title">Add New Candidate</div>
    </div>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/AddCandidateServlet" method="POST">
            
            <div class="form-group">
                <label>Select Student</label>
                <select name="studentId" required>
                    <option value="" disabled selected>-- Click to Search --</option>
                    <% 
                        List<StudentBean> students = (List<StudentBean>) request.getAttribute("studentList");
                        if(students != null) {
                            for(StudentBean s : students) {
                    %>
                        <option value="<%= s.getStudentId() %>"><%= s.getStudentName() %> (<%= s.getStudentNumber() %>)</option>
                    <%      }
                        } 
                    %>
                </select>
            </div>

            <div class="form-group">
                <label>Programmes (Election)</label>
                <select name="electionId" required>
                    <option value="" disabled selected>-- Click to Search --</option>
                    <% 
                        List<ElectionBean> elections = (List<ElectionBean>) request.getAttribute("electionList");
                        if(elections != null) {
                            for(ElectionBean e : elections) {
                    %>
                        <option value="<%= e.getElectionID() %>"><%= e.getElectionName() %></option>
                    <%      }
                        } 
                    %>
                </select>
            </div>

            <p style="font-size: 0.85rem; color: #666; margin-bottom: 1rem;">
                * A default manifesto will be created automatically.
            </p>

            <div class="form-group">
                <label>Candidate Manifesto</label>
                <textarea name="manifesto" placeholder="Enter candidate's vision and goals..."></textarea>
            </div>

            <button type="submit" class="btn">Add Candidate</button>
            <a href="${pageContext.request.contextPath}/ManageCandidateServlet" class="back-link">Cancel</a>
        </form>
    </div>
</body>
</html>