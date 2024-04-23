package com.example.csit228f2_2;

import java.sql.*;
public class CreateTable {
    public static void main(String[] args) {
        try (Connection c = MySqlConnection.getConnection();
            Statement statement = c.createStatement()){

            //tabla de usuarios si
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL," +
                    "password VARCHAR(50) NOT NULL)";
            statement.execute(createTableQuery);

            //tabla de notas si
            String createNotesTableQuery = "CREATE TABLE IF NOT EXISTS notes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "userid INT," +
                    "title VARCHAR(50) NOT NULL," +
                    "content VARCHAR(100) NOT NULL," +
                    "FOREIGN KEY (userid) REFERENCES users(id))";
            statement.executeUpdate(createNotesTableQuery);

            System.out.println("Table created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
