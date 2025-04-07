package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/search_engine";
    private static final String USER = "root"; // Change if needed
    private static final String PASSWORD = "Deep@5574"; // Change if needed

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void savePage(String url, String title) {
        String query = "INSERT INTO pages (url, title) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, url);
            stmt.setString(2, title);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public List<String> search(String keyword) {
        List<String> results = new ArrayList<>();
        String query = "SELECT url FROM pages WHERE title LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(rs.getString("url"));
            }
        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
        }

        return results;
    }
}
