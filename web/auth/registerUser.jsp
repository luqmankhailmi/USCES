<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/registerUser.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>

<body>
    <a href="${pageContext.request.contextPath}/auth/loginUser.jsp" class="back-btn">
        <i class="fas fa-arrow-left"></i> Back
    </a>

    <div class="register-container">
        <div class="box">
            <div class="register-header">
                <i class="fas fa-user-plus"></i>
                <h1>Student Registration</h1>
                <p>Create your account to participate in elections</p>
            </div>

            <form action="${pageContext.request.contextPath}/student_register" method="post">
                <div class="form-group">
                    <label for="studentName">Full Name</label>
                    <input type="text" id="studentName" name="studentName" placeholder="Enter your full name" required>
                </div>

                <div class="form-group">
                    <label for="studentNumber">Student ID</label>
                    <input type="text" id="studentNumber" name="studentNumber" placeholder="Enter your student ID" required>
                </div>

                <div class="form-group">
                    <label for="facultyId">Faculty</label>
                    <select id="facultyId" name="facultyId" required>
                        <option value="">Select your faculty</option>
                        <!-- Faculty options will be populated dynamically from database -->
                        <option value="1">Faculty of Engineering</option>
                        <option value="2">Faculty of Science</option>
                        <option value="3">Faculty of Arts</option>
                        <option value="4">Faculty of Business</option>
                        <option value="5">Faculty of Medicine</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="email">Student Email</label>
                    <input type="email" id="email" name="studentEmail" placeholder="Enter your email address" required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Create a password" required>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm your password" required>
                </div>

                <button type="submit">
                    Register <i class="fas fa-user-check"></i>
                </button>
            </form>

            <div class="login-link">
                Already have an account? <a href="${pageContext.request.contextPath}/auth/loginUser.jsp">Login here</a>
            </div>
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

    <!-- Display Success Message as Alert -->
    <% 
        String successMessage = (String) request.getAttribute("successMessage");
        if (successMessage != null) {
    %>
        <script type="text/javascript">
            alert("<%= successMessage %>");
            window.location.href = "${pageContext.request.contextPath}/auth/loginUser.jsp";
        </script>
    <% } %>
</body>
</html>