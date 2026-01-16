<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.ElectionBean"%>
<%@page import="java.util.Map"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Election Results - Student Election System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/viewResults.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <%
        // Session check
        HttpSession userSession = request.getSession(false);
        String studentNumber = null;
        
        if (userSession != null && userSession.getAttribute("studentNumber") != null) {
            studentNumber = (String) userSession.getAttribute("studentNumber");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // Get data from servlet
        ElectionBean election = (ElectionBean) request.getAttribute("election");
        Map<String, Integer> stats = (Map<String, Integer>) request.getAttribute("stats");
        Integer totalVotes = (Integer) request.getAttribute("totalVotes");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
    %>
    
    <!-- Header -->
    <header class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-chart-bar"></i>
                Election Results
            </h1>
            <a href="${pageContext.request.contextPath}/user_list_election" class="back-btn">
                <i class="fas fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>
    </header>
    
    <div class="container">
        <% if (election != null) { %>
            <!-- Election Info -->
            <div class="election-card">
                <h2><%= election.getElectionName() %></h2>
                <div class="election-meta">
                    <span class="meta-item">
                        <i class="fas fa-calendar"></i>
                        <%= election.getStartDate().format(formatter) %> - <%= election.getEndDate().format(formatter) %>
                    </span>
                    <span class="status-badge status-ended">
                        <i class="fas fa-check-circle"></i> Ended
                    </span>
                </div>
            </div>
            
            <!-- Total Votes -->
            <div class="stats-card">
                <div class="stat-item">
                    <i class="fas fa-users"></i>
                    <div class="stat-content">
                        <h3><%= totalVotes != null ? totalVotes : 0 %></h3>
                        <p>Total Votes Cast</p>
                    </div>
                </div>
            </div>
            
            <!-- Results Table -->
            <div class="results-section">
                <h3>Final Results</h3>
                
                <% if (stats != null && !stats.isEmpty()) { %>
                    <div class="results-table">
                        <table>
                            <thead>
                                <tr>
                                    <th>Rank</th>
                                    <th>Candidate Name</th>
                                    <th>Votes</th>
                                    <th>Percentage</th>
                                    <th>Vote Bar</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    int rank = 1;
                                    int maxVotes = 0;
                                    
                                    // Find max votes for percentage calculation
                                    for (Integer votes : stats.values()) {
                                        if (votes > maxVotes) maxVotes = votes;
                                    }
                                    
                                    for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                                        String candidateName = entry.getKey();
                                        int votes = entry.getValue();
                                        double percentage = totalVotes > 0 ? (votes * 100.0 / totalVotes) : 0;
                                        String rankClass = rank == 1 ? "rank-winner" : "";
                                %>
                                <tr class="<%= rankClass %>">
                                    <td class="rank-cell">
                                        <% if (rank == 1) { %>
                                            <i class="fas fa-trophy trophy-gold"></i>
                                        <% } else { %>
                                            <span class="rank-number">#<%= rank %></span>
                                        <% } %>
                                    </td>
                                    <td class="candidate-name"><%= candidateName %></td>
                                    <td class="votes-count"><strong><%= votes %></strong></td>
                                    <td class="percentage"><%= String.format("%.1f", percentage) %>%</td>
                                    <td class="bar-cell">
                                        <div class="vote-bar">
                                            <div class="vote-bar-fill" style="width: <%= percentage %>%"></div>
                                        </div>
                                    </td>
                                </tr>
                                <%
                                        rank++;
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                <% } else { %>
                    <div class="no-results">
                        <i class="fas fa-inbox"></i>
                        <p>No votes were cast in this election.</p>
                    </div>
                <% } %>
            </div>
        <% } else { %>
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle"></i>
                Election not found or results not available.
            </div>
        <% } %>
    </div>
</body>
</html>