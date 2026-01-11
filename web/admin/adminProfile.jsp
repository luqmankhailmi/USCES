<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
<head>
    <title>User Profile - Student Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/adminProfile.css">
</head>
<body>
    <%
        String adminName = (String) request.getAttribute("adminName");
        String staffNumber = (String) request.getAttribute("staffNumber");
        String adminEmail = (String) request.getAttribute("adminEmail");
        Integer facultyId = (Integer) request.getAttribute("facultyId");  // Cast to Integer
        String facultyName = (String) request.getAttribute("facultyName");
    %>

<div class="container">
    <div class="profile-header">
        
        <h2><%= adminName %></h2>
        <p>Staff ID: <%= staffNumber %></p>
    </div>

    <div class="profile-info">
        
        <div>
            <label>Email:</label>
            <span><%= adminEmail %></span>
        </div><br>
       
       
        <div>
            <label>Faculty:</label>
            <span><%= facultyName %></span>
        </div><br>
    </div>

    <div class="btn-group">
        <a href="${pageContext.request.contextPath}/admin/adminDashboard.jsp" class="btn-edit">Back to Dashboard</a>
        
    </div>
</div>

</body>
</html>