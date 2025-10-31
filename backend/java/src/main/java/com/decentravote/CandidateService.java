package main.java.com.decentravote;

import java.sql.*;
import java.util.*;

public class CandidateService {
    
    public List<Candidate> getCandidatesByElection(int electionId) throws SQLException {
        List<Candidate> candidates = new ArrayList<>();
        Connection conn = DB.getConnection();
        String query = "SELECT * FROM candidates WHERE election_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, electionId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Candidate c = new Candidate(
                rs.getInt("id"),
                rs.getInt("election_id"),
                rs.getString("name"),
                rs.getString("description")
            );
            candidates.add(c);
        }

        rs.close();
        stmt.close();
        conn.close();
        return candidates;
    }

    public Candidate getCandidateById(int id) throws SQLException {
        Connection conn = DB.getConnection();
        String query = "SELECT * FROM candidates WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        Candidate candidate = null;
        if (rs.next()) {
            candidate = new Candidate(
                rs.getInt("id"),
                rs.getInt("election_id"),
                rs.getString("name"),
                rs.getString("description")
            );
        }

        rs.close();
        stmt.close();
        conn.close();
        return candidate;
    }

    public int createCandidate(int electionId, String name, String description) throws SQLException {
        Connection conn = DB.getConnection();
        String query = "INSERT INTO candidates (election_id, name, description) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, electionId);
        stmt.setString(2, name);
        stmt.setString(3, description);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int candidateId = 0;
        if (rs.next()) {
            candidateId = rs.getInt(1);
        }

        rs.close();
        stmt.close();
        conn.close();
        return candidateId;
    }
}
