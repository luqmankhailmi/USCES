<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create New Election</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <%-- Linking to your shared CSS file --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addCandidate.css">
</head>
<body>

    <div class="header">
        <div class="header-title">
            <i class="fas fa-calendar-plus"></i> Create New Election
        </div>
        <a href="${pageContext.request.contextPath}/ManageElectionServlet" class="btn-back-header">
            <i class="fas fa-arrow-left"></i> Back to List
        </a>
    </div>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/AddElectionServlet" method="POST">

            <label>Election Name</label>
            <input type="text" name="electionName" placeholder="e.g., Campus Election 2026" required>

            <label>Assign to Faculty</label>
            <select name="facultyId" required>
                <option value="">-- Select Faculty --</option>
                <c:forEach var="faculty" items="${facultyList}">
                    <option value="${faculty.facultyId}">${faculty.facultyName}</option>
                </c:forEach>
            </select>

            <label>Start Date & Time</label>
            <input type="datetime-local" name="startDate" required>

            <label>End Date & Time</label>
            <input type="datetime-local" name="endDate" required>

            <p style="font-size: 0.85rem; color: #666; margin: 20px 0 10px 0;">
                <i class="fas fa-info-circle"></i> Setting the correct timeframe is crucial for student voting access.
            </p>

            <button class="btn" type="submit">
                <i class="fas fa-save"></i> Create Election
            </button>
        </form>
    </div>

</body>
</html>