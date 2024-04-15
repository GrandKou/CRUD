package com.example.csit228f2_2;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import java.sql.*;

public class HelloController {
    @FXML
    private TextField tfNewUsername;
    @FXML
    private TextField tfCurrentUsername;
    @FXML
    private PasswordField pfCurrentPassword;

    @FXML
    private PasswordField pfNewPassword;

    @FXML
    private PasswordField tfNewPassword;
    @FXML
    private Text tfStatus;
    @FXML
    private TextField tfUsername;

    @FXML
    private void onDeleteAccount() {
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = c.prepareStatement(
                     "DELETE FROM users WHERE id = ?")) {

            int userIdToDelete = HelloApplication.loggedInUserID;

            preparedStatement.setInt(1, userIdToDelete);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data deleted successfully!");
            } else {
                System.out.println("No user found with the provided ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setNewUsername() {
        int currentUserId = HelloApplication.loggedInUserID;
        String newUsername = tfNewUsername.getText();

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
        int currentUserId = HelloApplication.loggedInUserID;
        String newPassword = tfNewPassword.getText();

        UpdateData updateData = new UpdateData();
        try {
            updateData.updatePassword(currentUserId, newPassword);
            System.out.println("New password set successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
