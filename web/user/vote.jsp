<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.ElectionBean"%>
<%@page import="bean.CandidateBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vote - Student Election System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/vote.css">
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
        ArrayList<CandidateBean> candidateList = (ArrayList<CandidateBean>) request.getAttribute("candidateList");
        Boolean hasVoted = (Boolean) request.getAttribute("hasVoted");
        String errorMessage = (String) request.getAttribute("errorMessage");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
    %>
    
    <!-- Header -->
    <header class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-vote-yea"></i>
                Cast Your Vote
            </h1>
            <a href="${pageContext.request.contextPath}/user_list_election" class="back-btn">
                <i class="fas fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>
    </header>
    
    <div class="container">
        <% if (errorMessage != null) { %>
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle"></i>
                <%= errorMessage %>
            </div>
        <% } %>
        
        <% if (election != null) { %>
            <!-- Election Info -->
            <div class="election-card">
                <h2><%= election.getElectionName() %></h2>
                <div class="election-meta">
                    <span class="meta-item">
                        <i class="fas fa-calendar"></i>
                        <%= election.getStartDate().format(formatter) %> - <%= election.getEndDate().format(formatter) %>
                    </span>
                    <span class="status-badge status-ongoing">
                        <i class="fas fa-circle"></i> Ongoing
                    </span>
                </div>
            </div>
            
            <% if (hasVoted != null && hasVoted) { %>
                <!-- Already Voted Message -->
                <div class="vote-complete">
                    <i class="fas fa-check-circle"></i>
                    <h3>Thank You for Voting!</h3>
                    <p>You have already cast your vote in this election.</p>
                    <a href="${pageContext.request.contextPath}/user_list_election" class="btn-primary">
                        Return to Dashboard
                    </a>
                </div>
            <% } else { %>
                <!-- Voting Form -->
                <div class="voting-section">
                    <h3>Select Your Candidate</h3>
                    
                    <% if (candidateList != null && !candidateList.isEmpty()) { %>
                        <form action="${pageContext.request.contextPath}/submit_vote" method="post" onsubmit="return confirmVote()">
                            <input type="hidden" name="electionId" value="<%= election.getElectionID() %>">
                            
                            <div class="candidates-grid">
                                <% for (CandidateBean candidate : candidateList) { %>
                                    <div class="candidate-wrapper">
                                        <label class="candidate-card">
                                            <input type="radio" name="candidateId" value="<%= candidate.getCandidateId() %>" required>
                                            <div class="candidate-content">
                                                <div class="candidate-avatar">
                                                    <i class="fas fa-user"></i>
                                                </div>
                                                <div class="candidate-info">
                                                    <h4><%= candidate.getStudentName() %></h4>
                                                    <p class="candidate-id"><%= candidate.getStudentNumber() %></p>
                                                </div>
                                                <div class="radio-check">
                                                    <i class="fas fa-check-circle"></i>
                                                </div>
                                            </div>
                                        </label>
                                        <a href="${pageContext.request.contextPath}/view_candidate_profile?id=<%= candidate.getCandidateId() %>" 
                                           class="btn-view">
                                            <i class="fas fa-eye"></i> View
                                        </a>
                                    </div>
                                <% } %>
                            </div>
                            
                            <div class="vote-actions">
                                <button type="submit" class="btn-vote">
                                    <i class="fas fa-check"></i> Submit Vote
                                </button>
                            </div>
                        </form>
                    <% } else { %>
                        <div class="no-candidates">
                            <i class="fas fa-inbox"></i>
                            <p>No candidates available for this election yet.</p>
                        </div>
                    <% } %>
                </div>
            <% } %>
        <% } else { %>
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle"></i>
                Election not found or not available.
            </div>
        <% } %>
    </div>
    
    <script>
        function confirmVote() {
            return confirm("Are you sure you want to submit your vote? This action cannot be undone.");
        }
    </script>
</body>
</html>