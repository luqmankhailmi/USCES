<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create New Election</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addElection.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <!-- Updated Header to Match Add Candidate --> <!-- Updated Header to Match Add Candidate --> 
    <div class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-calendar-plus"></i>
                Create New Election
            </h1>
            <div class="header-menu">
                <a href="${pageContext.request.contextPath}/admin_list_election" class="header-link">
                    <i class="fas fa-arrow-left"></i> Back to Dashboard
                </a>
            </div>
        </div>
    </div>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/AddElectionServlet" method="POST">

            <div class="form-group">
                <label>
                    <i class="fas fa-vote-yea"></i> Election Name
                </label>
                <input type="text" name="electionName" placeholder="e.g., Campus Election 2026" required>
            </div>

            <div class="form-group">
                <label>
                    <i class="fas fa-building"></i> Assign to Faculty
                </label>
                <select name="facultyId" required>
                    <option value="">-- Select Faculty --</option>
                    <c:forEach var="faculty" items="${facultyList}">
                        <option value="${faculty.facultyID}">
                            ${faculty.facultyName}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label>
                    <i class="fas fa-calendar-check"></i> Start Date & Time
                </label>
                <input type="datetime-local" name="startDate" required>
            </div>

            <div class="form-group">
                <label>
                    <i class="fas fa-calendar-times"></i> End Date & Time
                </label>
                <input type="datetime-local" name="endDate" required>
            </div>

            <p class="info-text">
                <i class="fas fa-info-circle"></i> Setting the correct timeframe is crucial for student voting access.
            </p>

            <button class="btn" type="submit">
                <i class="fas fa-save"></i> Create Election
            </button>
        </form>
    </div>

</body>
</html>