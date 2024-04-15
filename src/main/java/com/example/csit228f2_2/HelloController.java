package com.example.csit228f2_2;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.sql.*;

public class HelloController {
    @FXML
    private TextField nameNewName;

    @FXML
    private PasswordField psNewPassword;

    @FXML
    private void onDeleteAccount() {
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = c.prepareStatement(
                     "DELETE FROM users WHERE id = ?")) {

            int userIdToDelete = HelloApplication.LogedUser;

            preparedStatement.setInt(1, userIdToDelete);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data deleted succesfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setNewUsername() {
        int currentUserId = HelloApplication.LogedUser;
        String newUsername = nameNewName.getText();

        UpdateData updateData = new UpdateData();
        try {
            updateData.updateUsername(currentUserId, newUsername);
            System.out.println("New username set successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setNewPassword() {
        int currentUserId = HelloApplication.LogedUser;
        String newPassword = psNewPassword.getText();

        UpdateData updateData = new UpdateData();
        try {
            updateData.updatePassword(currentUserId, newPassword);
            System.out.println("New password set successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
