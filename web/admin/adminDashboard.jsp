<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.ElectionBean" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Admin Dashboard - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/adminDashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <%-- 1. SUCCESS ALERT LOGIC --%>
    <%
        String msg = request.getParameter("msg");
        if ("success".equals(msg)) {
    %>
        <div class="alert-container">
            <div class="alert-box success">
                <i class="fas fa-check-circle"></i>
                <span>Election successfully created!</span>
                <button class="close-alert" onclick="this.parentElement.style.display='none'">&times;</button>
            </div>
        </div>
    <% } %>
    
    
    
    <%
        // Get the existing session (don't create new one)
        HttpSession userSession = request.getSession(false);
        String staffNumber = null;
        
        // Check if session exists and has staffNumber
        if (userSession != null && userSession.getAttribute("staffNumber") != null) {
            staffNumber = (String) userSession.getAttribute("staffNumber");
        } else {
            // No session found - redirect to login
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // Get the election list from request attribute
        ArrayList<ElectionBean> electionList = (ArrayList<ElectionBean>) request.getAttribute("electionList");
        
        // Get admin name from request attribute
        String adminName = (String) request.getAttribute("adminName");
        
        // Get faculty name from request attribute
        String facultyName = (String) request.getAttribute("facultyName");
        
        // Date formatter for displaying dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    %>
    
    <!-- Header -->
    <header class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-chart-line"></i>
                Admin Dashboard
            </h1>
            <div class="header-menu">
                <span class="welcome-text">
                    <i class="fas fa-user"></i> <%= adminName %>
                </span>
                <a href="${pageContext.request.contextPath}/admin_profile" class="header-link">
                    <i class="fas fa-user-cog"></i> Profile
                </a>
                <a href="${pageContext.request.contextPath}/user_logout" class="header-link logout">
                    <i class="fas fa-sign-out-alt"></i> Logout
                </a>
            </div>
        </div>
    </header>
    
    <div class="container">
        
        <!-- Quick Actions -->
        <div class="quick-actions">
            <h2 class="section-title">Quick Actions</h2>

            <div class="action-cards-wrapper">
                
                            <div class="action-card" onclick="location.href='${pageContext.request.contextPath}/AddElectionServlet'">
                <div class="action-icon">
                    <i class="fas fa-plus-circle"></i>
                </div>
                <div class="action-content">
                    <h3>Create New Election</h3>
                    <p>Set up a new election with candidates and schedules</p>
                </div>
            </div>
                    
                    
            

               <%-- FIXED: Points to ManageCandidateServlet --%>
                <div class="action-card" onclick="location.href='${pageContext.request.contextPath}/ManageCandidateServlet'">
                    <div class="action-icon manage-icon">
                        <i class="fas fa-users-cog"></i>
                    </div>
                    <div class="action-content">
                        <h3>Manage Candidates</h3>
                        <p>Add, edit, or remove candidates from elections</p>
                    </div>
                </div>
            </div>
        </div>
                
         
        <!-- Faculty Info -->
        <% if (facultyName != null) { %>
        <div class="faculty-info">
            <i class="fas fa-building"></i>
            <span>Managing Faculty: <strong><%= facultyName %></strong></span>
        </div>
        <% } %>
        <!-- Elections Section -->
        <div class="elections-section">
            <div class="section-header">
                <h2 class="section-title">All Elections</h2>
            </div>
            
            <%
                if (electionList != null && !electionList.isEmpty()) {
            %>
                <div class="table-wrapper">
                    <table class="elections-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Election Name</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                java.time.LocalDateTime now = java.time.LocalDateTime.now();
                                for (ElectionBean election : electionList) {
                                    String status = "";
                                    String statusClass = "";
                                    
                                    if (now.isBefore(election.getStartDate())) {
                                        status = "Upcoming";
                                        statusClass = "status-upcoming";
                                    } else if (now.isAfter(election.getEndDate())) {
                                        status = "Ended";
                                        statusClass = "status-ended";
                                    } else {
                                        status = "Ongoing";
                                        statusClass = "status-ongoing";
                                    }
                            %>
                            <tr>
                                <td><strong>#<%= election.getElectionID() %></strong></td>
                                <td class="election-name"><%= election.getElectionName() %></td>
                                <td><%= election.getStartDate().format(formatter) %></td>
                                <td><%= election.getEndDate().format(formatter) %></td>
                                <td><span class="status-badge <%= statusClass %>"><%= status %></span></td>
                                <td class="action-buttons">
                                    <a href="${pageContext.request.contextPath}/admin/viewElection.jsp?id=<%= election.getElectionID() %>" class="btn-action btn-view">
                                        <i class="fas fa-eye"></i> View
                                    </a>
                                    <a href="${pageContext.request.contextPath}/EditElectionServlet?id=<%= election.getElectionID() %>" class="btn-action btn-edit">
                                        <i class="fas fa-edit"></i> Edit
                                    </a>
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
                    <p>No elections found.</p>
                </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>