<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Election Details - Admin</title>
    
    <link rel="stylesheet" type="text/css" href="../css/admin/manageCandidate.css">
    <link rel="stylesheet" type="text/css" href="../css/admin/adminDashboard.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Election Management</h1>
            <div class="nav-links">
                <a href="adminDashboard.jsp" class="back-link">← Back to Dashboard</a>
            </div>
        </header>

        <hr>

        <div class="section-card">
            <h2>Election: ${election.electionName}</h2>
            <div class="info-grid">
                <p><strong>Election ID:</strong> ${election.electionId}</p>
                <p><strong>Faculty ID:</strong> ${election.facultyId}</p>
                <p><strong>Status:</strong> 
                    <c:choose>
                        <c:when test="${not empty election.startDate}">Active Period</c:when>
                        <c:otherwise>Draft</c:otherwise>
                    </c:choose>
                </p>
                <p><strong>Duration:</strong> ${election.startDate} to ${election.endDate}</p>
            </div>
        </div>

        <div class="section-card">
            <h3>Live Vote Count</h3>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Candidate Name</th>
                        <th>Total Votes</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${stats}">
                        <tr>
                            <td>${entry.key}</td>
                            <td><strong style="color: green;">${entry.value}</strong></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty stats}">
                        <tr><td colspan="2">No votes recorded yet.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <div class="section-card">
            <div class="header-with-btn">
                <h3>Registered Candidates</h3>
                <a href="addCandidate.jsp?electionId=${election.electionId}" class="btn-add">Add Candidate</a>
            </div>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Student Number</th>
                        <th>Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="tempCandidate" items="${candidateList}">
                        <tr>
                            <td>${tempCandidate.studentNumber}</td>
                            <td>${tempCandidate.studentName}</td>
                            <td>
                                <a href="viewCandidate.jsp?id=${tempCandidate.candidateId}" class="action-view">View</a> | 
                                <a href="../DeleteCandidateServlet?id=${tempCandidate.candidateId}" 
                                   class="action-delete" 
                                   onclick="return confirm('Remove this candidate from the election?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty candidateList}">
                        <tr><td colspan="3">No candidates registered.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <div class="footer-actions">
            <button class="btn-edit" onclick="location.href='editElection.jsp?id=${election.electionId}'">Edit Election Details</button>
            <button class="btn-danger" onclick="confirmElectionDelete(${election.electionId})">Delete Entire Election</button>
        </div>
    </div>

    <script>
        function confirmElectionDelete(id) {
            if (confirm("DANGER: This will delete the election and ALL associated votes/candidates. Continue?")) {
                window.location.href = "../DeleteElectionServlet?id=" + id;
            }
        }
    </script>
</body>
</html>