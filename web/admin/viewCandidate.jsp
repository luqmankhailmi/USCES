<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
<head>
    <title>View Candidate</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/viewCandidate.css">
</head>

<body>

    <div class="header">
        <a href="${pageContext.request.contextPath}/admin/manageCandidate.jsp" class="back_btn">Back</a>
        <div class="header-title">Candidate Details</div>
    </div>

    <div class="container">
        <% 
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="error-message"><%= error %></div>
            <a href="${pageContext.request.contextPath}/admin/manageCandidate.jsp" class="back-link">Back to Manage Candidates</a>
        <%
            } else {
                String studentName = (String) request.getAttribute("studentName");
                String studentNumber = (String) request.getAttribute("studentNumber");
                String studentEmail = (String) request.getAttribute("studentEmail");
                String facultyName = (String) request.getAttribute("facultyName");
                String electionName = (String) request.getAttribute("electionName");
                String manifestoContent = (String) request.getAttribute("manifestoContent");
                Integer candidateId = (Integer) request.getAttribute("candidateId");
        %>

        <!-- Candidate Details Card -->
        <div class="candidate-details-card">
            <div class="candidate-header">
                <div class="candidate-icon">
                    <img src="${pageContext.request.contextPath}/images/male.png" class="icon-img" alt="Candidate">
                </div>
                <div class="candidate-info">
                    <h2 class="candidate-name"><%= studentName != null ? studentName : "N/A" %></h2>
                    <p class="candidate-faculty"><%= facultyName != null ? facultyName : "N/A" %></p>
                </div>
            </div>

            <div class="details-section">
                <h3>Candidate Information</h3>
                <div class="info-row">
                    <span class="info-label">Student Number:</span>
                    <span class="info-value"><%= studentNumber != null ? studentNumber : "N/A" %></span>
                </div>
                <div class="info-row">
                    <span class="info-label">Email:</span>
                    <span class="info-value"><%= studentEmail != null ? studentEmail : "N/A" %></span>
                </div>
                <div class="info-row">
                    <span class="info-label">Election:</span>
                    <span class="info-value"><%= electionName != null ? electionName : "N/A" %></span>
                </div>
            </div>

            <div class="manifesto-section">
                <h3>Manifesto</h3>
                <div class="manifesto-content">
                    <%= manifestoContent != null ? manifestoContent.replace("\n", "<br>") : "No manifesto available" %>
                </div>
            </div>

            <div class="action-buttons">
                <form action="DeleteCandidateServlet" method="POST" style="display: inline;" 
                      onsubmit="return confirm('Are you sure you want to delete this candidate? This action cannot be undone.');">
                    <input type="hidden" name="candidateId" value="<%= candidateId != null ? candidateId : "" %>">
                    <button type="submit" class="btn-delete">Delete Candidate</button>
                </form>
                <a href="${pageContext.request.contextPath}/admin/manageCandidate.jsp" class="btn-cancel">Cancel</a>
            </div>
        </div>

        <%
            }
        %>
    </div>

</body>
</html>

