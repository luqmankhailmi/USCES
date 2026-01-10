<!DOCTYPE html>
<html>
<head>
    <title>Login - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/loginUser.css">
</head>

<body>

    <!-- Back Button at Top-Left -->
    <a href="${pageContext.request.contextPath}/index.jsp" class="back_btn">Back</a>

    <div class="login_box">
        <h1>Student Election System</h1>

        <form action="${pageContext.request.contextPath}/user/userDashboard.jsp">
            <table border="0" width="100%">
                <tr>
                    <td>Student ID:</td>
                </tr>
                <tr>
                    <td><input type="text" name="username" ></td>
                </tr>

                <tr>
                    <td>Password:</td>
                </tr>
                <tr>
                    <td><input type="password" name="password" ></td>
                </tr>
            </table>

            <button type="submit" class="login_btn">Login</button>
        </form>

        <p class="register_link">
            <a href="${pageContext.request.contextPath}/auth/registerUser.jsp">Register New Account</a>
        </p>
    </div>

</body>
</html>
