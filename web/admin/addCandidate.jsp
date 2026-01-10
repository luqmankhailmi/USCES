<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
<head>
    <title>Add New Candidate</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addCandidate.css">
</head>

<body>

    <div class="header">
        <div class="header-title">Add New Candidate</div>
    </div>

    <div class="form-container">
        <form action="AddCandidateServlet" method="POST">

            <label>Candidate Name</label>
            <input type="text" name="candidateName" required>

            <label>Programmes</label>
            <select name="electionId" required>
                <option value="">-- Select Election --</option>
                <option value="1">CDCS230</option>
                <option value="2">CDCS254</option>
            </select>

            <label>Candidate Description</label>
            <input type="text" name="description" placeholder="e.g., manifesto summary">

            <button class="btn" type="submit">Add Candidate</button>

        </form>

        <a href="${pageContext.request.contextPath}/admin/manageCandidate.jsp" class="back-link">Back to Manage Candidates</a>
    </div>

</body>
</html>