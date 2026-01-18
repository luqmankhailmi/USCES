<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Election Details - Admin</title>
    
    <%-- Absolute paths ensure CSS loads correctly regardless of URL depth --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/manageCandidate.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/adminDashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Election</h1>
            <div class="nav-links">
               <%--<a href="${pageContext.request.contextPath}/admin_list_election" class="back-link">← Back to Dashboard</a> <%-- Remove this div and link from here --%>
            </div>
        </header>

            <hr><br>

        <div class="section-card">
            <h2>Election: ${election.electionName}</h2> 

            <div class="info-grid">
                
                <p><strong>Duration:</strong> ${election.startDate} to ${election.endDate}</p>
                <p><strong>Status:</strong> 
                    <c:choose>
                        <c:when test="${not empty election.startDate}">
                            <span class="status-badge status-ongoing">Active Period</span>
                        </c:when>
                        <c:otherwise>
                            <span class="status-badge">Draft</span>
                        </c:otherwise>
                    </c:choose>
                </p>
                
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
                                <a href="${pageContext.request.contextPath}/DeleteCandidateServlet?id=${tempCandidate.candidateId}" 
                                    style="color: #e74c3c; text-decoration: none; font-weight: 600; margin-left: 10px;"
                                    onclick="return confirm('Are you sure you want to remove this candidate?')">
                                    Delete
                                 </a>
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
            <%-- The Back button styled to look like your other dashboard buttons --%>
            <a href="${pageContext.request.contextPath}/admin_list_election" 
               class="btn-action btn-view" 
               style="text-decoration: none; display: inline-flex; align-items: center; padding: 10px 20px;">
                <i class="fas fa-arrow-left" style="margin-right: 8px;"></i> Back to Dashboard
            </a>
               
            <button class="btn-danger" onclick="confirmElectionDelete(${election.electionID})">
                <i class="fas fa-trash-alt"></i> Delete Entire Election
            </button>
        </div>
    </div>

    <script>
        function confirmElectionDelete(id) {
            if (confirm("DANGER: This will delete the election and ALL associated votes/candidates. Continue?")) {
                window.location.href = "${pageContext.request.contextPath}/DeleteElectionServlet?id=" + id;
            }
        }
    </script>
</body>
</html>