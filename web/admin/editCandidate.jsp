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
        <div class="header-title">Edit Candidate Profile</div>
    </div>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/UpdateCandidateServlet" method="POST">

            <input type="hidden" name="candidateId" value="${candidate.candidateId}">
            <input type="hidden" name="manifestoId" value="${candidate.manifestoId}">

            <label>Candidate Name (Read Only)</label>
            <input type="text" value="${candidate.studentName}" readonly style="background-color: #f0f0f0;">

            <label>Election / Programme</label>
            <input type="text" value="${candidate.electionName}" readonly style="background-color: #f0f0f0;">

            <label>Manifesto / Description</label>
            <textarea name="manifestoContent" rows="10" required style="width: 100%; border-radius: 5px; border: 1px solid #ccc; padding: 10px;"><%= (request.getAttribute("manifestoContent") != null) ? request.getAttribute("manifestoContent") : "" %></textarea>

            <button class="btn" type="submit">Update Manifesto</button>
        </form>

        <a href="CandidateDetailServlet?id=${candidate.candidateId}" class="back-link">Cancel and Go Back</a>
    </div>
</body>
</html>
