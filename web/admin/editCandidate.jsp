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
    <% if(session.getAttribute("successMsg") != null) { %>
        <div style="color: green; margin-bottom: 10px;"><%= session.getAttribute("successMsg") %></div>
        <% session.removeAttribute("successMsg"); %>
    <% } %>

    <form action="${pageContext.request.contextPath}/UpdateCandidateServlet" method="POST">
        <input type="hidden" name="candidateId" value="${candidate.candidateId}">
        <input type="hidden" name="manifestoId" value="${candidate.manifestoId}">

        <div class="form-group">
            <label>Candidate Name (Read Only)</label>
            <input type="text" value="${candidate.studentName}" readonly>
        </div>

        <div class="form-group">
            <label>Election / Programme</label>
            <input type="text" value="${candidate.electionName}" readonly>
        </div>

        <div class="form-group">
            <label>Manifesto</label>
            <textarea name="manifestoContent" rows="8" required>${candidate.manifestoContent}</textarea>
        </div>

        <button class="btn" type="submit">Update Manifesto</button>
        <div class="footer-links">
            <a href="ManageCandidateServlet" class="back-link">Back</a>
        </div>
    </form>
</div>
</form>
        
    </div>
</body>
</html>