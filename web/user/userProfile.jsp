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
    <a href="${pageContext.request.contextPath}/user/userDashboard.jsp" class="back_btn">Back</a>

<div class="container">
    <div class="profile-header">
        
        <h2>Ahmad Nasrullah</h2>
        <p>Student ID: 2025128281</p>
    </div>

    <div class="profile-info">
        
        <div>
            <label>Faculty:</label>
            <span>Faculty of Computer and Mathematical Sciences </span>
        </div><br>
        
        <div>
            <label>Semester:</label>
            <span>4</span>
        </div><br>
       
        
        <div>
            <label>Email:</label>
            <span>2025128281@student.uitm.edu.my</span>
        </div><br>
       
       
        <div>
            <label>Phone:</label>
            <span>+6012-3456789</span>
        </div><br>
    </div>

    <div class="btn-group">
        <a href="${pageContext.request.contextPath}/user/editProfile.jsp" class="btn-edit">Edit Profile</a>
        
    </div>
</div>

</body>
</html>
