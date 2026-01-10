<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
<head>
    <title>Edit Candidate</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/editCandidate.css">
</head>

<body>

    <div class="header">
        <div class="header-title">Edit Candidate</div>
    </div>

    <div class="form-container">
        <form action="EditCandidateServlet" method="POST">

            <!-- Hidden ID -->
            <input type="hidden" name="candidateId" value="1">

            <label>Candidate Name</label>
            <input type="text" name="candidateName" value="John Doe" required>

            <label>Programmes</label>
            <select name="electionId" required>
                <option value="1" selected>CDCS230</option>
                <option value="2">CDCS254</option>
            </select>

            <label>Candidate Description</label>
            <input type="text" name="description" value="Committed to transparency">

            <button class="btn" type="submit">Save Changes</button>

        </form>

        <a href="${pageContext.request.contextPath}/admin/manageCandidate.jsp" class="back-link">Back to Manage Candidates</a>
    </div>

</body>
</html>
