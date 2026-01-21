<!DOCTYPE html>
<html>
<head>
    <title>Admin Profile - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/adminProfile.css">
</head>
<body>
    <%
        String adminName = (String) request.getAttribute("adminName");
        String staffNumber = (String) request.getAttribute("staffNumber");
        String adminEmail = (String) request.getAttribute("adminEmail");
    %>
    <!-- Back Button -->
    <a href="${pageContext.request.contextPath}/admin_list_election" class="back-btn">
        <i class="fas fa-arrow-left"></i>
        Back to Dashboard
    </a>
    <!-- Profile Container -->
    <div class="profile-container">
        <div class="profile-box">
            <!-- Profile Header -->
            <div class="profile-header">
                <div class="profile-icon">
                    <i class="fas fa-user-shield"></i>
                </div>
                <h2><%= adminName %></h2>
                <p class="staff-id">Staff ID: <%= staffNumber %></p>
            </div>
            <!-- Profile Information -->
            <div class="profile-info">
                <div class="info-item">
                    <div class="info-icon">
                        <i class="fas fa-envelope"></i>
                    </div>
                    <div class="info-content">
                        <div class="info-label">Email Address</div>
                        <div class="info-value"><%= adminEmail %></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>