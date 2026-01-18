<%@ page import="bean.ElectionBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Election</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/editCandidate.css">
</head>
<body>
    <div class="header">
        <div class="header-title">Update Election Details</div>
    </div>

    <div class="form-container">
        <% ElectionBean e = (ElectionBean) request.getAttribute("election"); %>
        <form action="${pageContext.request.contextPath}/UpdateElectionServlet" method="POST">
            <input type="hidden" name="electionId" value="<%= e.getElectionID() %>">

            <div class="form-group">
                <label>Election Name</label>
                <input type="text" name="electionName" value="<%= e.getElectionName() %>" required>
            </div>

            <div class="form-group">
                <label>Start Date</label>
                <%-- Replace .toString() with a formatted string that HTML5 understands --%>
                <input type="datetime-local" name="startDate" 
                       value="<%= e.getStartDate().toString().substring(0, 16).replace(" ", "T") %>" required>
            </div>

            <div class="form-group">
                <label>End Date</label>
                <input type="datetime-local" name="endDate" 
                       value="<%= e.getEndDate().toString().substring(0, 16).replace(" ", "T") %>" required>
            </div>

            <button type="submit" class="btn">Save Changes</button>
            <a href="${pageContext.request.contextPath}/admin_list_election" class="back-link">Cancel</a>
        </form>
    </div>
</body>
</html>