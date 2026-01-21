<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Candidate</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addCandidate.css">
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
</head>
<body>

    <div class="header">
        <div class="header-title">Add New Candidate</div>
        <a href="${pageContext.request.contextPath}/AdminDashboardServlet" class="btn-back-header">Back to Dashboard</a>
    </div>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/AddCandidateServlet" method="POST">

            <label>Select Student</label>
            <select name="studentId" class="js-searchable" required style="width: 100%;">
                <option value="">-- Search Name or Student Number --</option>
                <c:forEach var="student" items="${studentList}">
                    <option value="${student.studentId}">
                        ${student.studentName} (${student.studentNumber})
                    </option>
                </c:forEach>
            </select>

            <label>Programmes (Election)</label>
            <select name="electionId" class="js-searchable" required style="width: 100%;">
                <option value="">-- Select Election --</option>
                <c:forEach var="election" items="${electionList}">
                    <option value="${election.electionID}">
                        ${election.electionName}
                    </option>
                </c:forEach>
            </select>

            <p style="font-size: 0.8rem; color: #666; margin-top: 1.5rem;">
                * A default manifesto will be created automatically.
            </p>

            <label>Candidate Manifesto</label>
            <textarea name="manifesto" rows="5" placeholder="Enter candidate's vision and goals..." required 
                      style="width: 100%; padding: 12px; border-radius: 8px; border: 1px solid #e0e0e0; margin-top: 8px;"></textarea>
            
            <button class="btn" type="submit">Add Candidate</button>
        </form>

        <a href="${pageContext.request.contextPath}/ManageCandidateServlet" class="back-link" 
           style="display: block; text-align: center; margin-top: 20px; color: #6a11cb; text-decoration: none; font-weight: 600;">
            Back to Manage Candidates
        </a>
    </div>

    <script>
        $(document).ready(function() {
            $('.js-searchable').select2({
                placeholder: "-- Click to Search --",
                allowClear: true
            });
        });
    </script>

</body>
</html>