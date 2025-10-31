package main.java.com.decentravote;

import java.sql.*;
import java.util.*;

public class VoteService {
    
    public boolean hasVoted(int electionId, int voterId) throws SQLException {
        Connection conn = DB.getConnection();
        String query = "SELECT * FROM votes WHERE election_id = ? AND voter_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, electionId);
        stmt.setInt(2, voterId);
        ResultSet rs = stmt.executeQuery();

        boolean exists = rs.next();

        rs.close();
        stmt.close();
        conn.close();
        return exists;
    }

    public int recordVote(int electionId, int candidateId, int voterId) throws SQLException {
        Connection conn = DB.getConnection();
        String query = "INSERT INTO votes (election_id, candidate_id, voter_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, electionId);
        stmt.setInt(2, candidateId);
        stmt.setInt(3, voterId);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int voteId = 0;
        if (rs.next()) {
            voteId = rs.getInt(1);
        }

        rs.close();
        stmt.close();
        conn.close();
        return voteId;
    }

    public Map<Integer, Integer> getVoteCounts(int electionId) throws SQLException {
        Map<Integer, Integer> voteCounts = new HashMap<>();
        Connection conn = DB.getConnection();
        String query = "SELECT candidate_id, COUNT(*) as votes FROM votes WHERE election_id = ? GROUP BY candidate_id";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, electionId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            voteCounts.put(rs.getInt("candidate_id"), rs.getInt("votes"));
        }

        rs.close();
        stmt.close();
        conn.close();
        return voteCounts;
    }

    public List<Vote> getAllVotes(int electionId) throws SQLException {
        List<Vote> votes = new ArrayList<>();
        Connection conn = DB.getConnection();
        String query = "SELECT * FROM votes WHERE election_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, electionId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Vote v = new Vote(
                rs.getInt("id"),
                rs.getInt("election_id"),
                rs.getInt("candidate_id"),
                rs.getInt("voter_id")
            );
            votes.add(v);
        }

        rs.close();
        stmt.close();
        conn.close();
        return votes;
    }
}