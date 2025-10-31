package main.java.com.decentravote;

public class Vote {
    public int id;
    public int electionId;
    public int candidateId;
    public int voterId;

    public Vote(int id, int electionId, int candidateId, int voterId) {
        this.id = id;
        this.electionId = electionId;
        this.candidateId = candidateId;
        this.voterId = voterId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", electionId=" + electionId +
                ", candidateId=" + candidateId +
                ", voterId=" + voterId +
                '}';
    }
}