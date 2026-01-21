<%@page import="bean.ElectionBean"%>
<%@page import="bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Candidate</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/editCandidate.css">
    <style>
        .msg-error { color: #d9534f; background: #f2dede; padding: 10px; border-radius: 4px; margin-bottom: 10px; text-align: center; }
        .msg-success { color: #3c763d; background: #dff0d8; padding: 10px; border-radius: 4px; margin-bottom: 10px; text-align: center; }
    </style>
</head>
<body>
    <div class="header">
        <div class="header-title">Add New Candidate</div>
    </div>

    <div class="form-container">
    <%-- Display error/success messages --%>
    <% if(request.getAttribute("errMessage") != null) { %>
        <div class="msg-error"><%= request.getAttribute("errMessage") %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/AddCandidateServlet" method="POST">
        
        <div class="form-group">
            <label>Search Student (Type Name or Number)</label>
            <%-- The name="studentId" ensures the ID is sent to the Servlet --%>
            <input type="text" name="studentId" list="studentData" placeholder="Search..." autocomplete="off" required>
            
            <datalist id="studentData">
                <% 
                    List<StudentBean> students = (List<StudentBean>) request.getAttribute("studentList");
                    if(students != null) {
                        for(StudentBean s : students) {
                %>
                    <%-- 
                        Value = The ID (What the server gets)
                        Label/Text = Name + Number (What the user searches for)
                    --%>
                    <option value="<%= s.getStudentId() %>">
                        <%= s.getStudentName() %> - <%= s.getStudentNumber() %>
                    </option>
                <%      }
                    } 
                %>
            </datalist>
        </div>

        <div class="form-group">
            <label>Programmes (Election)</label>
            <select name="electionId" required>
                <option value="" disabled selected>-- Select Election --</option>
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

        <div class="form-group">
            <label>Candidate Manifesto</label>
            <textarea name="manifesto" placeholder="Enter vision..." required></textarea>
        </div>

        <button type="submit" class="btn">Add Candidate</button>
        <a href="${pageContext.request.contextPath}/admin_list_election" class="back-link">Cancel</a>
    </form>
</div>
</body>
</html>