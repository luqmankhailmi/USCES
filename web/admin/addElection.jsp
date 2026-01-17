<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create New Election</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%-- Ensure this path points to the CSS file provided below --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addCandidate.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

    <div class="header">
        <div class="header-title">
            <i class="fas fa-calendar-plus"></i> Create New Election
        </div>
        <%-- Matches the reference translucent button style --%>
        <a href="${pageContext.request.contextPath}/admin_list_election" class="btn-back-header">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
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
                    <option value="${faculty.facultyID}">
                        ${faculty.facultyName}
                    </option>
                </c:forEach>
            </select>

            <%-- Grouping dates for a cleaner look within the container --%>
            <div style="display: flex; gap: 20px; margin-top: 10px;">
                <div style="flex: 1;">
                    <label>Start Date & Time</label>
                    <input type="datetime-local" name="startDate" required>
                </div>
                <div style="flex: 1;">
                    <label>End Date & Time</label>
                    <input type="datetime-local" name="endDate" required>
                </div>
            </div>

            <p style="font-size: 0.8rem; color: #666; margin-top: 1.5rem; display: flex; align-items: center; gap: 5px;">
                <i class="fas fa-info-circle"></i> Setting the correct timeframe is crucial for student voting access.
            </p>

            <button class="btn" type="submit">
                <i class="fas fa-save"></i> Create Election
            </button>
        </form>
    </div>

</body>
</html>