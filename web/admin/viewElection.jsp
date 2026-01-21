<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Election Details - Admin</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/manageCandidate.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/adminDashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    
    <style>
        .date-badge {
            background-color: #f1f2f6;
            padding: 4px 10px;
            border-radius: 4px;
            color: #2d3436;
            font-weight: 500;
            border: 1px solid #dfe4ea;
            display: inline-block;
        }
        .date-sep { margin: 0 8px; color: #747d8c; font-style: italic; }
    </style>
</head>
<body>
    <div class="container">
        <header><h1>Election</h1></header>
        <hr><br>

        <div class="section-card">
            <h2>Election: ${election.electionName}</h2> 

            <div class="info-grid">
                <p><strong>Duration:</strong> 
                    <span class="date-badge">${formattedStart}</span> 
                    <span class="date-sep">to</span> 
                    <span class="date-badge">${formattedEnd}</span>
                </p>
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

        <%-- Live Vote Count Section --%>
        <div class="section-card">
            <h3>Live Vote Count</h3>
            <table class="data-table">
                <thead>
                    <tr><th>Candidate Name</th><th>Total Votes</th></tr>
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

        <%-- Registered Candidates Section --%>
        <div class="section-card">
            <h3>Registered Candidates</h3>
            <table class="data-table">
                <thead>
                    <tr><th>Student Number</th><th>Name</th><th>Actions</th></tr>
                </thead>
                <tbody>
                    <c:forEach var="tempCandidate" items="${candidateList}">
                        <tr>
                            <td>${tempCandidate.studentNumber}</td>
                            <td>${tempCandidate.studentName}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/DeleteCandidateServlet?id=${tempCandidate.candidateId}" 
                                   style="color: #e74c3c; text-decoration: none; font-weight: 600;"
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
            <a href="${pageContext.request.contextPath}/admin_list_election" class="btn-action btn-view" style="text-decoration: none; display: inline-flex; align-items: center; padding: 10px 20px;">
                <i class="fas fa-arrow-left" style="margin-right: 8px;"></i> Back to Dashboard
            </a>
            
            <a href="${pageContext.request.contextPath}/DeleteElectionServlet?id=${election.electionID}" 
               class="btn-danger" 
               style="text-decoration: none; display: inline-flex; align-items: center; padding: 10px 20px; border-radius: 4px;"
               onclick="return confirm('DANGER: This will delete the election and ALL associated votes. Continue?')">
                <i class="fas fa-trash-alt" style="margin-right: 8px;"></i> Delete Entire Election
            </a>
        </div>
    </div>
</body>
</html>