package org.example.pacman.database;

import org.example.pacman.User;

import java.sql.*;
import java.util.LinkedHashMap;

public class Connect {
    private static final String DB_URL = "jdbc:sqlite:identifier.sqlite";

    private static Connection connection;

    public static void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, scoreSeries) VALUES(?,?,?)";
        try {
            connectToDatabase();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getScoreSeries());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
    }

    public static void clearUsersTable() throws SQLException {
        try {
            connectToDatabase();
            Statement stmt = connection.createStatement();
            String deleteQuery = "DELETE FROM users";
            stmt.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
    }

    public static LinkedHashMap<String, User> getUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        LinkedHashMap<String, User> userMap = new LinkedHashMap<>();
        try {
            connectToDatabase();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("scoreSeries"));
                userMap.put(user.getUsername(), user);
            }
            return userMap;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            connection.close();
        }
    }
}
