<!DOCTYPE html>
<html>
<head>
    <title>Edit Candidate</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/editCandidate.css">
</head>
<body>
    
    <header class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-user-edit"></i>
                Edit Candidate Profile
            </h1>
            <div class="header-menu">
                <a href="${pageContext.request.contextPath}/ManageCandidateServlet" class="header-link">
                    <i class="fas fa-arrow-left"></i> Back to Manage
                </a>
            </div>
        </div>
    </header>

    <div class="form-container">
        <%-- Display success message if exists --%>
        <% if(session.getAttribute("successMsg") != null) { %>
            <div class="msg-success">
                <i class="fas fa-check-circle"></i> 
                <%= session.getAttribute("successMsg") %>
            </div>
            <% session.removeAttribute("successMsg"); %>
        <% } %>

        <form action="${pageContext.request.contextPath}/UpdateCandidateServlet" method="POST">
            <input type="hidden" name="candidateId" value="${candidate.candidateId}">
            <input type="hidden" name="manifestoId" value="${candidate.manifestoId}">

            <div class="form-group">
                <label>
                    <i class="fas fa-user"></i> Candidate Name (Read Only)
                </label>
                <input type="text" value="${candidate.studentName}" readonly>
            </div>

            <div class="form-group">
                <label>
                    <i class="fas fa-vote-yea"></i> Election / Programme
                </label>
                <input type="text" value="${candidate.electionName}" readonly>
            </div>

            <div class="form-group">
                <label>
                    <i class="fas fa-scroll"></i> Manifesto
                </label>
                <textarea name="manifestoContent" rows="8" required>${candidate.manifestoContent}</textarea>
            </div>

            <button class="btn" type="submit">
                <i class="fas fa-save"></i> Update Manifesto
            </button>
            
            <div class="footer-links">
                <a href="${pageContext.request.contextPath}/ManageCandidateServlet" class="back-link">
                    <i class="fas fa-times-circle"></i> Cancel
                </a>
            </div>
        </form>
    </div>
</body>
</html>