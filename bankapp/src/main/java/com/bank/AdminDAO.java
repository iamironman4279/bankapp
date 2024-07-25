package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public static boolean authenticate(String username, String password) {
        boolean status = false;
        try (Connection conn = Database.getConnection()) {
            if (conn == null) {
                System.err.println("Database connection is null. Authentication failed.");
                return false;
            }
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            status = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}
