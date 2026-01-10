<!DOCTYPE html>
<html>
<head>
    <title>Login - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/loginAdmin.css">
</head>

<body>

    <!-- Back Button at Top-Left -->
    <a href="${pageContext.request.contextPath}/index.jsp" class="back_btn">Back</a>

    <div class="login_box">
        <h1>Student Election System</h1>

        <form action="${pageContext.request.contextPath}/admin/adminDashboard.jsp">
            <table border="0" width="100%">
                <tr>
                    <td>Staff ID:</td>
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

       
    </div>

</body>
</html>
