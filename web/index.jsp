<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <title>Student Election System</title>
</head>
<body>

    <header>
        <h1>Welcome to the Student Election System</h1>
        
    </header>
    
    <div class="graphic">
    <img src="images\SCES-graphic.png" class="graphic-img">
    </div>

    <div class="content">
        <h2>About the Election</h2>
        <p>
            The Student Election System is designed to make voting and election management 
            easier and more transparent. Students can vote for their preferred candidates 
            in various school elections, while administrators can manage elections, candidates, 
            and results securely online.
        </p>

        <div class="btn-container">
            <a href="${pageContext.request.contextPath}/auth/loginUser.jsp" class="btn">Student</a>
            <a href="${pageContext.request.contextPath}/auth/loginAdmin.jsp" class="btn">Staff</a>
        </div>
    </div>

    <footer>
        &copy; 2025 Student Election System. All Rights Reserved.
    </footer>

</body>
</html>
