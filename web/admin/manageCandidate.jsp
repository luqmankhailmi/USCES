<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
<head>
    <title>Manage Candidates</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/manageCandidate.css">
</head>

<body>

    <div class="header">
    <a href="${pageContext.request.contextPath}/admin/adminDashboard.jsp" class="back_btn">Back</a>
    <div class="header-title">Manage Candidates</div>
</div>

    <div class="container">

        <!-- Add New Candidate Button -->
        <a href="${pageContext.request.contextPath}/admin/addCandidate.jsp" class="btn">+ Add New Candidate</a><br><br>

        <h2>Candidate List</h2>

        <table>
            <tr>
                <th>Name</th>
                <th>Election</th>
                <th>Actions</th>
            </tr>

            <tr>
                <td>John Doe</td>
                <td>Student Council 2025</td>
                <td>
                    <!-- Edit Page -->
                    <a href="${pageContext.request.contextPath}/admin/editCandidate.jsp" class="btn-action">Edit</a>

                    <!-- Delete Page / Servlet -->
                    <a href="${pageContext.request.contextPath}/admin/deleteCandidate.jsp" class="btn-action" 
                       style="background: red;">Delete</a>
                </td>
            </tr>

        </table>
    </div>

</body>
</html>
