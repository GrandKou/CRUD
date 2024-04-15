package com.example.csit228f2_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {
    public static void main(String[] args) {
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE users SET name=?, password=? WHERE id=?")) {
            String name = "Rienel Bas";
            String password = "rieub@gmail.com";
            int idToUpdate = 2;
            statement.setString(1, name);
            statement.setString(2, password);
            statement.setInt(3, idToUpdate);
            int rowsUpdated = statement.executeUpdate();
            if(rowsUpdated > 0) {
                System.out.println("Data updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
