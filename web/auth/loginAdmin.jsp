<!DOCTYPE html>
<html lang="en">
<head>
    <title>Staff Login - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/loginAdmin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <a href="${pageContext.request.contextPath}/index.jsp" class="back-btn">
        <i class="fas fa-arrow-left"></i> Back
    </a>

    <div class="login-container">
        <div class="login-box">
            <div class="login-header">
                <i class="fas fa-user-tie"></i>
                <h1>Staff Login</h1>
                <p>Enter your credentials to access the admin portal</p>
            </div>
            
            <form action="${pageContext.request.contextPath}/admin_login" method="post">
                <div class="form-group">
                    <label for="staffNumber">Staff ID</label>
                    <input type="text" id="staffNumber" name="staffNumber" placeholder="Enter your staff ID" required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password" required>
                </div>

                <button type="submit" class="login-btn">
                    Login <i class="fas fa-sign-in-alt"></i>
                </button>
            </form>
        </div>
    </div>
    
    <!-- Display Error Message as Alert -->
    <% 
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
        <script type="text/javascript">
            alert("<%= errorMessage %>");
        </script>
    <% } %>
</body>
</html>