<div class="apply-container">
    <h2>Apply for: ${election.electionName}</h2>
    <form action="apply_candidate" method="POST">
        <input type="hidden" name="electionId" value="${election.electionID}">
        
        <div class="form-group">
            <label>Your Manifesto</label>
            <textarea name="manifesto" placeholder="Explain why students should vote for you..." required></textarea>
        </div>

        <div class="action-row">
            <button type="submit" class="btn-submit">Submit Application</button>
            <a href="student_dashboard" class="btn-cancel">Cancel</a>
        </div>
    </form>
</div>