<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.CandidateBean"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Candidates & Manifestos - Student Election System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/viewCandidateProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <%
        // Session check
        HttpSession userSession = request.getSession(false);
        String studentNumber = null;
        
        if (userSession != null && userSession.getAttribute("studentNumber") != null) {
            studentNumber = (String) userSession.getAttribute("studentNumber");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // Get candidate data from servlet
        CandidateBean candidate = (CandidateBean) request.getAttribute("candidate");
        String errorMessage = (String) request.getAttribute("errorMessage");
    %>
    
    <!-- Header -->
    <header class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-users"></i>
                Candidates & Manifestos
            </h1>
            <a href="${pageContext.request.contextPath}/user_list_election" class="back-btn">
                <i class="fas fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>
    </header>
    
    <div class="container">
        <% if (errorMessage != null) { %>
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle"></i>
                <%= errorMessage %>
            </div>
        <% } else if (candidate != null) { %>
            <!-- Election Info -->
            <div class="election-card">
                <h2><%= candidate.getElectionName() != null ? candidate.getElectionName() : "Election" %></h2>
                <div class="election-meta">
                    <span class="meta-item">
                        <i class="fas fa-calendar"></i>
                        Election Information
                    </span>
                    <span class="status-badge status-ongoing">
                        <i class="fas fa-circle"></i> Ongoing
                    </span>
                </div>
            </div>
            
            <!-- Registered Candidates Section -->
            <div class="candidates-section">
                <h3 class="section-title">
                    <i class="fas fa-users"></i>
                    Registered Candidates
                </h3>
                
                <!-- Candidate Card -->
                <div class="candidate-card">
                    <div class="candidate-header">
                        <div class="candidate-avatar">
                            <i class="fas fa-user"></i>
                        </div>
                        <div class="candidate-info">
                            <h4 class="candidate-name"><%= candidate.getStudentName() != null ? candidate.getStudentName() : "N/A" %></h4>
                            <p class="candidate-id"><%= candidate.getStudentNumber() != null ? candidate.getStudentNumber() : "N/A" %></p>
                            <p class="candidate-faculty">
                                <i class="fas fa-building"></i>
                                <%= candidate.getFacultyName() != null ? candidate.getFacultyName() : "N/A" %>
                            </p>
                        </div>
                    </div>
                    
                    <div class="manifesto-section">
                        <h4 class="manifesto-title">
                            <i class="fas fa-file-alt"></i>
                            Manifesto
                        </h4>
                        <div class="manifesto-content">
                            <%= candidate.getManifestoContent() != null && !candidate.getManifestoContent().isEmpty() 
                                ? candidate.getManifestoContent().replace("\n", "<br>") 
                                : "No manifesto available." %>
                        </div>
                    </div>
                </div>
            </div>
        <% } else { %>
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle"></i>
                Candidate information not available.
            </div>
        <% } %>
    </div>
</body>
</html>
