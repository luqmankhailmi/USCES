<!DOCTYPE html>
<html>
<head>
    <title>Candidate List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/listCandidate.css">
</head>

<body>

<a href="${pageContext.request.contextPath}/user/userDashboard.jsp" class="back_btn">Back</a>

<h1>Candidate List</h1>

<!-- Candidate 1 -->
<div class="candidate_box">
    <div class="candidate_info">
    <div class="icon">
    <img src="images\male.png" class="icon-img">
    </div>
        <strong>Ahmad Faiz</strong><br>
        Fakulti Sains Komputer
    </div>

    <form action="submitVoteServlet" method="POST">
        <input type="hidden" name="candidate_id" value="1">
        <button type="submit" class="vote_btn">Vote</button>
    </form>
</div>

<!-- Candidate 2 -->
<div class="candidate_box">
    <div class="candidate_info">
    <div class="icon">
    <img src="images\female.png" class="icon-img">
    </div>        
        <strong>Siti Zulaikha</strong><br>
        Fakulti Perniagaan
    </div>

    <form action="submitVoteServlet" method="POST">
        <input type="hidden" name="candidate_id" value="2">
        <button type="submit" class="vote_btn">Vote</button>
    </form>
</div>

<!-- Candidate 3 -->
<div class="candidate_box">
    <div class="candidate_info">
    <div class="icon">
    <img src="images\male.png" class="icon-img">
    </div>
        <strong>Daniel Arif</strong><br>
        Fakulti Kejuruteraan
    </div>

    <form action="submitVoteServlet" method="POST">
        <input type="hidden" name="candidate_id" value="3">
        <button type="submit" class="vote_btn">Vote</button>
    </form>
</div>

</body>
</html>
