<!DOCTYPE html>
<html>
<head>
    <title>Register - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/registerUser.css">
</head>

<body>

<div class="box">
    <a href="${pageContext.request.contextPath}/auth/loginUser.jsp" class="back_btn">Back</a>

    <h1>Student Registration</h1>

    <form action="${pageContext.request.contextPath}/auth/loginUser.jsp" method="post">
        <label for="studentID">Student ID:</label>
        <input type="text" id="studentID" name="username" >

        <label for="email">Student Email:</label>
        <input type="email" id="email" name="email" >

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" >

        <label for="confirm_password">Confirm Password:</label>
        <input type="password" id="confirm_password" name="confirm_password" >

        <button type="submit">Register</button>
    </form>
</div>

</body>
</html>
