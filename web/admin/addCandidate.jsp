<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Candidate</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addCandidate.css">
</head>
<body>

    <div class="header">
        <div class="header-title">Add New Candidate</div>
    </div>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/AddCandidateServlet" method="POST">

            <label>Select Student</label>
            <select name="studentId" required>
                <option value="">-- Select Student --</option>
                <c:forEach var="student" items="${studentList}">
                    <option value="${student.studentId}">
                        ${student.studentName} (ID: ${student.studentId})
                    </option>
                </c:forEach>
            </select>

            <label>Programmes (Election)</label>
            <select name="electionId" required>
                <option value="">-- Select Election --</option>
                <c:forEach var="election" items="${electionList}">
                    <option value="${election.electionId}">
                        ${election.electionName}
                    </option>
                </c:forEach>
            </select>

            <p style="font-size: 0.8rem; color: #666; margin-bottom: 1rem;">
                * A default manifesto will be created automatically.
            </p>

            <button class="btn" type="submit">Add Candidate</button>
        </form>

        <a href="${pageContext.request.contextPath}/ManageCandidateServlet" class="back-link">Back to Manage Candidates</a>
    </div>

</body>
</html>