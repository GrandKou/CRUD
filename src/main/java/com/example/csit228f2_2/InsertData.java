package com.example.csit228f2_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertData {
    public static void main(String[] args) {
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO users (name, password) VALUES (?, ?)")) {
             String name = "rienel";
             String password = "12345";
             statement.setString(1, name);
             statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            if(rowsInserted > 0) {
                System.out.println("Data inserted successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
