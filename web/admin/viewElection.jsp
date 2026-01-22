<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Election Details - Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/viewElection.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <!-- Header -->
    <header class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-poll-h"></i>
                Election Details
            </h1>
            <a href="${pageContext.request.contextPath}/admin_list_election" class="back-btn">
                <i class="fas fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>
    </header>

    <div class="container">
        
        <div class="election-info-card">
            <h2>
                <i class="fas fa-vote-yea"></i>
                ${election.electionName}
            </h2>

            <div class="info-grid">
                <div class="info-item">
                    <span class="info-label">
                        <i class="fas fa-calendar-alt"></i> Duration
                    </span>
                    <div class="info-value">
                        <span class="date-badge">
                            <i class="fas fa-calendar-check"></i>
                            ${formattedStart}
                        </span>
                        <span class="date-separator">to</span>
                        <span class="date-badge">
                            <i class="fas fa-calendar-times"></i>
                            ${formattedEnd}
                        </span>
                    </div>
                </div>

                <div class="info-item">
                    <span class="info-label">
                        <i class="fas fa-info-circle"></i> Status
                    </span>
                    <div class="info-value">
                        <c:choose>
                            <c:when test="${not empty election.startDate}">
                                <span class="status-badge status-active">
                                    <i class="fas fa-check-circle"></i> Active Period
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="status-badge status-draft">
                                    <i class="fas fa-clock"></i> Draft
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <!-- Live Vote Count Section -->
        <div class="section-card">
            <h3>
                <i class="fas fa-chart-line"></i>
                Live Vote Count
            </h3>
            
            <div class="table-wrapper">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th><i class="fas fa-user"></i> Candidate Name</th>
                            <th><i class="fas fa-chart-bar"></i> Total Votes</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${stats}">
                            <tr>
                                <td>${entry.key}</td>
                                <td><span class="vote-count">${entry.value}</span></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty stats}">
                            <tr>
                                <td colspan="2" class="empty-state">
                                    <i class="fas fa-inbox"></i>
                                    <p>No votes recorded yet.</p>
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        
        <div class="section-card">
            <h3>
                <i class="fas fa-users"></i>
                Registered Candidates
            </h3>
            
            <div class="table-wrapper">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th><i class="fas fa-id-card"></i> Student Number</th>
                            <th><i class="fas fa-user-graduate"></i> Name</th>
                            <th><i class="fas fa-cog"></i> Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tempCandidate" items="${candidateList}">
                            <tr>
                                <td>${tempCandidate.studentNumber}</td>
                                <td>${tempCandidate.studentName}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/DeleteCandidateServlet?id=${tempCandidate.candidateId}" 
                                       class="action-link"
                                       onclick="return confirm('Are you sure you want to remove this candidate?')">
                                        <i class="fas fa-trash-alt"></i> Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty candidateList}">
                            <tr>
                                <td colspan="3" class="empty-state">
                                    <i class="fas fa-user-slash"></i>
                                    <p>No candidates registered.</p>
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        
        <div class="footer-actions">
            <a href="${pageContext.request.contextPath}/admin_list_election" class="btn-action btn-back">
                <i class="fas fa-arrow-left"></i> Back to Dashboard
            </a>
            
            <a href="${pageContext.request.contextPath}/DeleteElectionServlet?id=${election.electionID}" 
               class="btn-action btn-danger"
               onclick="return confirm('DANGER: This will delete the election and ALL associated votes. Continue?')">
                <i class="fas fa-exclamation-triangle"></i> Delete Entire Election
            </a>
        </div>
    </div>
</body>
</html>