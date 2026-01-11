<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/userDashboard.css">
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
    %>
    
    <!-- HEADER -->
    <div class="header">
        <div class="header-title">STUDENT ELECTION SYSTEM</div>
        <div class="top-menu">
            <span>Welcome, <%= studentNumber %></span>
            <a href="${pageContext.request.contextPath}/user_profile">Profile</a>
            <a href="${pageContext.request.contextPath}/user_logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <div class="grid">
            <div class="card">
                <a href="${pageContext.request.contextPath}/user/listCandidate.jsp">
                    <div class="icon-box">##</div>
                    <div class="card-title">View Candidates</div>
                </a>
            </div>
        </div>
    </div>
</body>
</html>