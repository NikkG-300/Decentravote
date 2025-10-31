package main.java.com.decentravote;

import java.sql.*;

public class UserService {
    
    public User getUser(String username) throws SQLException {
        Connection conn = DB.getConnection();
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        User user = null;
        if (rs.next()) {
            user = new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role")
            );
        }

        rs.close();
        stmt.close();
        conn.close();
        return user;
    }

    public User getUserById(int id) throws SQLException {
        Connection conn = DB.getConnection();
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        User user = null;
        if (rs.next()) {
            user = new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role")
            );
        }

        rs.close();
        stmt.close();
        conn.close();
        return user;
    }

    public int createUser(String username, String password, String role) throws SQLException {
        Connection conn = DB.getConnection();
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, role);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int userId = 0;
        if (rs.next()) {
            userId = rs.getInt(1);
        }

        rs.close();
        stmt.close();
        conn.close();
        return userId;
    }
}
