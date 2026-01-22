<%@ page import="bean.ElectionBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Election</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/editElection.css">
</head>
<body>
    
    <div class="header">
        <div class="header-content">
            <h1 class="header-title">
                <i class="fas fa-edit"></i>
                Update Election Details
            </h1>
            <div class="header-menu">
                <a href="${pageContext.request.contextPath}/admin_list_election" class="header-link">
                    <i class="fas fa-arrow-left"></i> Back to Dashboard
                </a>
            </div>
        </div>
    </div>

    <div class="form-container">
        <% ElectionBean e = (ElectionBean) request.getAttribute("election"); %>
        <form action="${pageContext.request.contextPath}/UpdateElectionServlet" method="POST">
            <input type="hidden" name="electionId" value="<%= e.getElectionID() %>">

            <div class="form-group">
                <label>
                    <i class="fas fa-vote-yea"></i> Election Name
                </label>
                <input type="text" name="electionName" value="<%= e.getElectionName() %>" required>
            </div>

            <div class="form-group">
                <label>
                    <i class="fas fa-calendar-check"></i> Start Date & Time
                </label>
                <input type="datetime-local" name="startDate" 
                       value="<%= e.getStartDate().toString().substring(0, 16).replace(" ", "T") %>" required>
            </div>

            <div class="form-group">
                <label>
                    <i class="fas fa-calendar-times"></i> End Date & Time
                </label>
                <input type="datetime-local" name="endDate" 
                       value="<%= e.getEndDate().toString().substring(0, 16).replace(" ", "T") %>" required>
            </div>

            <button type="submit" class="btn">
                <i class="fas fa-save"></i> Save Changes
            </button>
            <a href="${pageContext.request.contextPath}/admin_list_election" class="back-link">
                <i class="fas fa-times-circle"></i> Cancel
            </a>
        </form>
    </div>
</body>
</html>