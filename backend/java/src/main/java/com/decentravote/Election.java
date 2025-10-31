package main.java.com.decentravote;

public class Election {
    public int id;
    public String title;
    public String description;
    public String status;
    public int createdBy;

    public Election(int id, String title, String description, String status, int createdBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Election{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}