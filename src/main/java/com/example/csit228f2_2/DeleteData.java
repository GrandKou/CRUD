package com.example.csit228f2_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteData {
    public static void main(String[] args) {
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "DELETE FROM users WHERE id=?")) {
            int idToDelete = 2;
            statement.setInt(1, idToDelete);
            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0) {
                System.out.println("Data deleted successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
