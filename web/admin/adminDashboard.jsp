<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
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
        
        // Check if session exists and has studentNumber
        if (userSession != null && userSession.getAttribute("staffNumber") != null) {
            staffNumber = (String) userSession.getAttribute("staffNumber");
        } else {
            // No session found - redirect to login
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
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
        <div class="grid">

           

            <div class="card" onclick="location.href='${pageContext.request.contextPath}/admin/manageCandidate.jsp'">
                <div class="icon-box">##</div>
                <div class="card-title">Manage Candidates</div>
            </div>

            
            
        </div>
    </div>

</body>
</html>
