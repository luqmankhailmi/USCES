/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.time.LocalDateTime;

/**
 *
 * @author User
 */
public class VoteBean {
    private int vote_id;
    private int student_id;
    private int election_id;
    private int candidate_id;
    private LocalDateTime voted_at;

    public VoteBean(int vote_id, int student_id, int election_id, int candidate_id, LocalDateTime voted_at) {
        this.vote_id = vote_id;
        this.student_id = student_id;
        this.election_id = election_id;
        this.candidate_id = candidate_id;
        this.voted_at = voted_at;
    }

    public VoteBean() {
    }

    public int getVote_id() {
        return vote_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public int getElection_id() {
        return election_id;
    }

    public int getCandidate_id() {
        return candidate_id;
    }

    public LocalDateTime getVoted_at() {
        return voted_at;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setElection_id(int election_id) {
        this.election_id = election_id;
    }

    public void setCandidate_id(int candidate_id) {
        this.candidate_id = candidate_id;
    }

    public void setVoted_at(LocalDateTime voted_at) {
        this.voted_at = voted_at;
    }
    
    
}
