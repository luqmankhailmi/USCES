<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <title>Student Election System</title>
</head>
<body>
    <!-- Hero Section -->
    <section class="hero">
        <div class="container">
            <h1>Student Election System</h1>
            <p class="tagline">Secure and transparent digital voting for students</p>
            
            <div class="graphic">
                <i class="fas fa-vote-yea"></i>
            </div>

            <div class="login-cards">
                <div class="card">
                    <h2>Student Portal</h2>
                    <p>Cast your vote and view results</p>
                    <a href="${pageContext.request.contextPath}/auth/loginUser.jsp" class="btn btn-student">Student Login</a>
                </div>

                <div class="card">
                    <h2>Staff Portal</h2>
                    <p>Manage elections and candidates</p>
                    <a href="${pageContext.request.contextPath}/auth/loginAdmin.jsp" class="btn btn-staff">Staff Login</a>
                </div>
            </div>

        </div>
    </section>

    <!-- Footer -->
    <footer>
        <p>&copy; 2025 Student Election System. All Rights Reserved.</p>
    </footer>
</body>
</html>