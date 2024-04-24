package com.example.csit228f2_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.sql.*;

public class NotepadController {

//    @FXML
//    private TextField Search;
    @FXML
    private TextField Title;
    @FXML
    private TextArea Content;
    @FXML
    private VBox notepaduContainer;
//    private int selectedNoteID;

    @FXML
    private void initialize() {
        twotablisi();
    }

    private void twotablisi() {
        try (Connection c = MySqlConnection.getConnection();
             Statement statement = c.createStatement()){
            c.setAutoCommit(false);

            //tabla de usuarios si
            String usertablepedro = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL," +
                    "password VARCHAR(50) NOT NULL)";
            statement.execute(usertablepedro);

            //tabla de notas si
            String notetablepedro = "CREATE TABLE IF NOT EXISTS notes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "userid INT," +
                    "title VARCHAR(50) NOT NULL," +
                    "content VARCHAR(100) NOT NULL," +
                    "FOREIGN KEY (userid) REFERENCES users(id))";
            statement.executeUpdate(notetablepedro);

            System.out.println("Tables created successfully");
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    Tired
//    @FXML
//    private void onSearchNote() {
//        // Search all notes based on Title
//    }
//    @FXML
//    private void onSelectAll() {
//        // Select all note to be deleted
//    }

    @FXML
    private void onCreateNote() {
        String title = Title.getText();
        String content = Content.getText();

        try (Connection c = MySqlConnection.getConnection()) {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO notes (userid, title, content) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setInt(1, HelloApplication.LogedUser);
            statement.setString(2, title);
            statement.setString(3, content);
            statement.executeUpdate();

            ResultSet insertID = statement.getGeneratedKeys();
            insertID.next();
            int selectedNoteID = insertID.getInt(1);

            VBox noteBox = new VBox();
            //Let's make fireworks, baby!
            noteBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 5px;");

            Label titleLabel = new Label("Title: " + title);
            Label contentLabel = new Label("Content: " + content);

            Button updateButton = new Button("Update");
            updateButton.setOnAction(this::updateNote);

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(this::deleteNote);

            deleteButton.setId(Integer.toString(selectedNoteID));
            updateButton.setId(Integer.toString(selectedNoteID));

            noteBox.getChildren().addAll(titleLabel, contentLabel, updateButton, deleteButton);

            notepaduContainer.getChildren().add(noteBox);
            c.commit();

            Title.clear();
            Content.clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void updateNote(ActionEvent event) {
        try {
            VBox noteBox = (VBox) ((Button) event.getSource()).getParent();

            Label titleLabel = (Label) noteBox.getChildren().get(0);
            TextArea contentTextArea = new TextArea(((Label) noteBox.getChildren().get(1)).getText().substring(9));

            TextField titleTextField = new TextField(titleLabel.getText().substring(7));
            noteBox.getChildren().set(0, titleTextField);

            noteBox.getChildren().set(1, contentTextArea);

            Button confirmButton = getConfirmButton(noteBox);
            if (confirmButton == null) {
                confirmButton = new Button("OK");
                Button finalConfirmButton = confirmButton;
                confirmButton.setOnAction(confirmEvent -> {
                    String newTitle = titleTextField.getText();
                    String newContent = contentTextArea.getText();

                    int noteID = Integer.parseInt(((Button) event.getSource()).getId());

                    try (Connection c = MySqlConnection.getConnection()) {
                        c.setAutoCommit(false);

                        try (PreparedStatement preparedStatement = c.prepareStatement(
                                "UPDATE notes SET title = ?, content = ? WHERE id = ?")) {
                            preparedStatement.setString(1, newTitle);
                            preparedStatement.setString(2, newContent);
                            preparedStatement.setInt(3, noteID);

                            int rowsUpdated = preparedStatement.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("Note updated successfully!");
                            }
                        }

                        c.commit();
                    } catch (SQLException e) {
                        throw new RuntimeException("There is ewwow", e);
                    }

                    titleLabel.setText("Title: " + newTitle);
                    contentTextArea.setText(newContent);

                    noteBox.getChildren().set(0, titleLabel);
                    noteBox.getChildren().set(1, new Label("Content: " + newContent));
                    noteBox.getChildren().remove(finalConfirmButton);
                });
                noteBox.getChildren().add(confirmButton);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void deleteNote(ActionEvent event) {
        Button deleteButton = (Button) event.getSource();
        int noteID = Integer.parseInt(deleteButton.getId());
        VBox noteBox = (VBox) deleteButton.getParent();

        try (Connection connection = MySqlConnection.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM notes WHERE id = ?"
            );
            preparedStatement.setInt(1, noteID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Data deleted successfully!");
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        notepaduContainer.getChildren().remove(noteBox);
    }

    //stup 👌 dups
    private Button getConfirmButton(VBox noteBox) {
        for (Node node : noteBox.getChildren()) {
            if (node instanceof Button && ((Button) node).getText().equals("OK")) {
                return (Button) node;
            }
        }
        return null;
    }
}