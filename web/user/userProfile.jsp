<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
<head>
    <title>User Profile - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/userProfile.css">
</head>
<body>
    <%
        String studentName = (String) request.getAttribute("studentName");
        String studentNumber = (String) request.getAttribute("studentNumber");
        String studentEmail = (String) request.getAttribute("studentEmail");
        Integer facultyId = (Integer) request.getAttribute("facultyId");  // Cast to Integer
        String facultyName = (String) request.getAttribute("facultyName");
    %>

<div class="container">
    <div class="profile-header">
        
        <h2><%= studentName %></h2>
        <p>Student ID: <%= studentNumber %></p>
    </div>

    <div class="profile-info">
        
        <div>
            <label>Faculty:</label>
            <span><%= facultyName %></span>
        </div><br>
       
        
        <div>
            <label>Email:</label>
            <span><%= studentEmail %></span>
        </div><br>
       
    </div>

    <div class="btn-group">
        <a href="${pageContext.request.contextPath}/user/userDashboard.jsp" class="btn-edit">Back to Dashboard</a>
        
    </div>
</div>

</body>
</html>
