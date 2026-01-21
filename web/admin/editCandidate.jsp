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
            <input type="hidden" name="candidateId" value="${candidateId}">
            <input type="hidden" name="manifestoId" value="${manifestoId}">

            <div class="form-group">
                <label>Candidate Name (Read Only)</label>
                <input type="text" value="${studentName}" readonly>
            </div>

            <div class="form-group">
                <label>Election / Programme</label>
                <input type="text" value="${electionName}" readonly>
            </div>

            <div class="form-group">
                <label>Manifesto</label>
                <textarea name="manifestoContent" rows="8" required>${manifestoContent}</textarea>
            </div>

            <button class="btn" type="submit">Update Manifesto</button>
        </form>
        
        <div class="footer-links">
            <a href="ManageCandidateServlet" class="back-link">Cancel</a>
        </div>
    </div>
</body>
</html>