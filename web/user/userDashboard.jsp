<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/userDashboard.css">
</head>

<body>

    <!-- HEADER -->
    <div class="header">
        <div class="header-title">STUDENT ELECTION SYSTEM</div>

        <div class="top-menu">
            <a href="${pageContext.request.contextPath}/user/userProfile.jsp">Profile</a>
            <a href="${pageContext.request.contextPath}/index.jsp">Logout</a>
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
