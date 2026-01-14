<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Sign Up</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="signup-container">
        <h2>Student Sign Up</h2>

        <form action="RegisterServlet" method="post">

            <label for="fullName">Full Name:</label><br>
            <input type="text" id="fullName" name="fullName" required><br><br>

            <label for="username">Username:</label><br>
            <input type="text" id="username" name="username" required><br><br>

            <label for="password">Password:</label><br>
            <input type="password" id="password" name="password" required><br><br>

            <label for="email">Email:</label><br>
            <input type="email" id="email" name="email" required><br><br>

            <label for="phone">Phone:</label><br>
            <input type="text" id="phone" name="phone"><br><br>

            <label for="address">Address:</label><br>
            <textarea id="address" name="address"></textarea><br><br>

            <input type="submit" value="Sign Up">
        </form>

        <p>Already have an account? <a href="auth/login.jsp">Login here</a></p>
    </div>
</body>
</html>
