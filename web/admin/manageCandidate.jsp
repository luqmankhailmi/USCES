<%-- Import List to match your Servlet's return type --%>
<%@page import="java.util.List"%>
<%@page import="bean.CandidateBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Candidates - Admin</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/manageCandidate.css">
</head>
<body>
    <header class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-users-cog"></i>
                Manage Candidates
            </h1>
            <div class="header-menu">
                <%-- Fixed Link: Usually, admins go back to the dashboard --%>
                <a href="${pageContext.request.contextPath}/admin_list_election" class="header-link">
                    <i class="fas fa-arrow-left"></i> Back to Dashboard
                </a>
            </div>
        </div>
    </header>

    <div class="container">
        <div class="section-header">
            <h2 class="section-title">Candidate List</h2>
            <%-- Redirect to Servlet instead of direct JSP to ensure dropdowns load --%>
            <a href="${pageContext.request.contextPath}/AddCandidateServlet" class="btn-add">
                <i class="fas fa-plus-circle"></i> Add New Candidate
            </a>
        </div>

        <div class="table-card">
            <div class="table-wrapper">
                <table class="manage-table">
                    <thead>
                        <tr>
                            <th>Candidate Name</th>
                            <th>Election Assignment</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            // Use List interface for better compatibility with ArrayList
                            List<CandidateBean> candidateList = (List<CandidateBean>) request.getAttribute("candidateList");

                            if (candidateList != null && !candidateList.isEmpty()) {
                                for (CandidateBean candidate : candidateList) {
                        %>
                            <tr>
                                <td>
                                    <div class="candidate-cell">
                                        <div class="candidate-avatar"><i class="fas fa-user"></i></div>
                                        <span class="candidate-name"><%= candidate.getStudentName() %></span>
                                    </div>
                                </td>
                                <td>
                                    <span class="election-badge"><%= candidate.getElectionName() %></span>
                                </td>
                                <td class="action-buttons">
                                    <a href="${pageContext.request.contextPath}/EditCandidateServlet?id=<%= candidate.getCandidateId() %>" class="btn-action btn-edit">
                                        <i class="fas fa-edit"></i> Edit
                                    </a>

                                    <a href="${pageContext.request.contextPath}/DeleteCandidateServlet?id=<%= candidate.getCandidateId() %>" 
                                       class="btn-action btn-delete" 
                                       onclick="return confirm('Are you sure you want to delete <%= candidate.getStudentName() %>?')">
                                        <i class="fas fa-trash-alt"></i> Delete
                                    </a>
                                </td>
                            </tr>
                        <% 
                                } 
                            } else { 
                        %>
                            <tr>
                                <td colspan="3" style="text-align: center; padding: 3rem; color: #666;">
                                    <i class="fas fa-folder-open" style="font-size: 2rem; display: block; margin-bottom: 1rem; opacity: 0.5;"></i>
                                    No candidates found in the database.
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>