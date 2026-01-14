<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create New Election</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addElection.css">
</head>
<body>
    <div class="container">
        <a href="${pageContext.request.contextPath}/admin/adminDashboard.jsp" class="back-link">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
        </a><br><br>
        <h2 class="form-title">Setup New Election</h2>
        
        <form action="${pageContext.request.contextPath}/AddElectionServlet" method="POST" class="election-form">
            <div class="form-group">
                <label>Election Name</label>
                <input type="text" name="electionName" placeholder="e.g., Student Council 2026" required>
            </div>

            <div class="form-group">
                <label>Start Date & Time</label>
                <input type="datetime-local" name="startDate" required>
            </div>

            <div class="form-group">
                <label>End Date & Time</label>
                <input type="datetime-local" name="endDate" required>
            </div>

            <input type="hidden" name="facultyId" value="<%= session.getAttribute("facultyId") %>">

            <button type="submit" class="btn-primary">
                <i class="fas fa-check-circle"></i> Create Election
            </button>
        </form>
    </div>
</body>
</html>