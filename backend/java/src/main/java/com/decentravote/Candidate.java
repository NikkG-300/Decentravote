package main.java.com.decentravote;

public class Candidate {
    public int id;
    public int electionId;
    public String name;
    public String description;

    public Candidate(int id, int electionId, String name, String description) {
        this.id = id;
        this.electionId = electionId;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", electionId=" + electionId +
                '}';
    }
}