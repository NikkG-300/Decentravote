package main.java.com.decentravote;

import java.sql.*;
import java.util.*;

public class ElectionService {
    
    public List<Election> getAllElections() throws SQLException {
        List<Election> elections = new ArrayList<>();
        Connection conn = DB.getConnection();
        String query = "SELECT * FROM elections ORDER BY created_at DESC";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            Election e = new Election(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("status"),
                rs.getInt("created_by")
            );
            elections.add(e);
        }

        rs.close();
        stmt.close();
        conn.close();
        return elections;
    }

    public Election getElectionById(int id) throws SQLException {
        Connection conn = DB.getConnection();
        String query = "SELECT * FROM elections WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        Election election = null;
        if (rs.next()) {
            election = new Election(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("status"),
                rs.getInt("created_by")
            );
        }

        rs.close();
        stmt.close();
        conn.close();
        return election;
    }

    public int createElection(String title, String description, int createdBy) throws SQLException {
        Connection conn = DB.getConnection();
        String query = "INSERT INTO elections (title, description, created_by) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, title);
        stmt.setString(2, description);
        stmt.setInt(3, createdBy);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int electionId = 0;
        if (rs.next()) {
            electionId = rs.getInt(1);
        }

        rs.close();
        stmt.close();
        conn.close();
        return electionId;
    }

    public void updateElectionStatus(int id, String status) throws SQLException {
        Connection conn = DB.getConnection();
        String query = "UPDATE elections SET status = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, status);
        stmt.setInt(2, id);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
