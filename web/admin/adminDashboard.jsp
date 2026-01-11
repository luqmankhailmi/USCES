<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.ElectionBean" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/adminDashboard.css">
</head>
<body>
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
        
        // Date formatter for displaying dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    %>
    <!-- HEADER -->
    <div class="header">
        <div class="header-title">ADMIN DASHBOARD</div>
        <div class="top-menu">
            <span>Welcome, <%= staffNumber %></span>
            <a href="${pageContext.request.contextPath}/admin_profile">Profile</a>
            <a href="${pageContext.request.contextPath}/user_logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <!-- Quick Actions -->
        <div class="grid">
            <div class="card" onclick="location.href='${pageContext.request.contextPath}/admin/manageCandidate.jsp'">
                <div class="icon-box">##</div>
                <div class="card-title">Manage Candidates</div>
            </div>
            <div class="card" onclick="location.href='${pageContext.request.contextPath}/admin/addElection.jsp'">
                <div class="icon-box">+</div>
                <div class="card-title">Add New Election</div>
            </div>
        </div>
        
        <!-- Elections Section -->
        <div class="elections-section">
            <h2>Elections</h2>
            
            <%
                if (electionList != null && !electionList.isEmpty()) {
            %>
                <table class="elections-table">
                    <thead>
                        <tr>
                            <th>Election ID</th>
                            <th>Election Name</th>
                            <th>Faculty ID</th>
                            <th>Start Time</th>
                            <th>End Time</th>
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
                            <td><%= election.getElectionID() %></td>
                            <td><%= election.getElectionName() %></td>
                            <td><%= election.getFacultyID() %></td>
                            <td><%= election.getStartDate().format(formatter) %></td>
                            <td><%= election.getEndDate().format(formatter) %></td>
                            <td><span class="status-badge <%= statusClass %>"><%= status %></span></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/viewElection.jsp?id=<%= election.getElectionID() %>" class="action-btn">View</a>
                                <a href="${pageContext.request.contextPath}/admin/editElection.jsp?id=<%= election.getElectionID() %>" class="action-btn">Edit</a>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            <%
                } else {
            %>
                <p class="no-data">No elections found.</p>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>