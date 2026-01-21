<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.ElectionBean" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Student Dashboard - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/userDashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <%
        // Get the existing session (don't create new one)
        HttpSession userSession = request.getSession(false);
        String studentNumber = null;
        
        // Check if session exists and has studentNumber
        if (userSession != null && userSession.getAttribute("studentNumber") != null) {
            studentNumber = (String) userSession.getAttribute("studentNumber");
        } else {
            // No session found - redirect to login
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // Get the election list from request attribute
        ArrayList<ElectionBean> electionList = (ArrayList<ElectionBean>) request.getAttribute("electionList");
        
        // Get student name from request attribute
        String studentName = (String) request.getAttribute("studentName");
        
        // Get faculty name from request attribute
        String facultyName = (String) request.getAttribute("facultyName");
        
        // Date formatter for displaying dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    %>
    
    <!-- Header -->
    <header class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-vote-yea"></i>
                Student Election System
            </h1>
            <div class="header-menu">
                <span class="welcome-text">
                    <i class="fas fa-user"></i> <%= studentName != null ? studentName : studentNumber %>
                </span>
                <a href="${pageContext.request.contextPath}/user_profile" class="header-link">
                    <i class="fas fa-user-cog"></i> Profile
                </a>
                <a href="${pageContext.request.contextPath}/user_logout" class="header-link logout">
                    <i class="fas fa-sign-out-alt"></i> Logout
                </a>
            </div>
        </div>
    </header>
    
    <div class="container">
        <!-- Faculty Info -->
        <% if (facultyName != null) { %>
        <div class="faculty-info">
            <i class="fas fa-building"></i>
            <span>Your Faculty: <strong><%= facultyName %></strong></span>
        </div>
        <% } %>
        
        <!-- Elections Section -->
        <div class="elections-section">
            <div class="section-header">
                <h2 class="section-title">Available Elections</h2>
            </div>
            
            <%
                if (electionList != null && !electionList.isEmpty()) {
            %>
                <div class="table-wrapper">
                    <table class="elections-table">
                        <thead>
                            <tr>
                                <th>Election Name</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                java.time.LocalDateTime now = java.time.LocalDateTime.now();
                                for (ElectionBean election : electionList) {
                                    String status = "";
                                    String statusClass = "";
                                    boolean canVote = false;
                                    
                                    if (now.isBefore(election.getStartDate())) {
                                        status = "Upcoming";
                                        statusClass = "status-upcoming";
                                    } else if (now.isAfter(election.getEndDate())) {
                                        status = "Ended";
                                        statusClass = "status-ended";
                                    } else {
                                        status = "Ongoing";
                                        statusClass = "status-ongoing";
                                        canVote = true;
                                    }
                            %>
                            <tr>
                                <td class="election-name"><%= election.getElectionName() %></td>
                                <td><%= election.getStartDate().format(formatter) %></td>
                                <td><%= election.getEndDate().format(formatter) %></td>
                                <td><span class="status-badge <%= statusClass %>"><%= status %></span></td>
                                <td class="action-buttons">
                                   
                                    <% if (canVote) { %>
                                        <a href="${pageContext.request.contextPath}/student_vote?id=<%= election.getElectionID() %>"
                                           class="btn-action btn-vote">
                                            <i class="fas fa-check-circle"></i> Vote Now
                                        </a>
                                    <% } else if (status.equals("Ended")) { %>
                                        <a href="${pageContext.request.contextPath}/view_results?id=<%= election.getElectionID() %>"
                                           class="btn-action btn-results">
                                            <i class="fas fa-chart-bar"></i> View Results
                                        </a>
                                    <% } else { %>
                                        <span class="btn-disabled">
                                            <i class="fas fa-clock"></i> Not Started
                                        </span>
                                    <% } %>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            <%
                } else {
            %>
                <div class="no-data">
                    <i class="fas fa-inbox"></i>
                    <p>No elections available for your faculty at the moment.</p>
                </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>