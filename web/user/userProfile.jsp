<!DOCTYPE html>
<html>
<head>
    <title>User Profile - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/userProfile.css">
</head>
<body>
    <%
        String studentName = (String) request.getAttribute("studentName");
        String studentNumber = (String) request.getAttribute("studentNumber");
        String studentEmail = (String) request.getAttribute("studentEmail");
        Integer facultyId = (Integer) request.getAttribute("facultyId");
        String facultyName = (String) request.getAttribute("facultyName");
    %>

    <!-- Back Button -->
    <a href="${pageContext.request.contextPath}/user_list_election" class="back-btn">
        <i class="fas fa-arrow-left"></i>
        Back to Dashboard
    </a>

    <!-- Profile Container -->
    <div class="profile-container">
        <div class="profile-box">
            <!-- Profile Header -->
            <div class="profile-header">
                <div class="profile-icon">
                    <i class="fas fa-user-graduate"></i>
                </div>
                <h2><%= studentName %></h2>
                <p class="student-id">Student ID: <%= studentNumber %></p>
            </div>

            <!-- Profile Information -->
            <div class="profile-info">
                <div class="info-item">
                    <div class="info-icon">
                        <i class="fas fa-building"></i>
                    </div>
                    <div class="info-content">
                        <div class="info-label">Faculty</div>
                        <div class="info-value"><%= facultyName %></div>
                    </div>
                </div>

                <div class="info-item">
                    <div class="info-icon">
                        <i class="fas fa-envelope"></i>
                    </div>
                    <div class="info-content">
                        <div class="info-label">Email Address</div>
                        <div class="info-value"><%= studentEmail %></div>
                    </div>
                </div>

                <div class="info-item">
                    <div class="info-icon">
                        <i class="fas fa-id-card"></i>
                    </div>
                    <div class="info-content">
                        <div class="info-label">Faculty ID</div>
                        <div class="info-value"><%= facultyId %></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>