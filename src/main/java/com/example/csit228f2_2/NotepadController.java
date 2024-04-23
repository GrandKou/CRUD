package com.example.csit228f2_2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NotepadController {

    @FXML
    private TextField Search;
    @FXML
    private TextField Title;
    @FXML
    private TextArea Content;

    @FXML
    private void onSearchNote() {

    }

    @FXML
    private void onCreateNote() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Pop_pop_pop_teojigil.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void SelectAll() {

    }

    @FXML
    private void CreateNoteContent() {
        String title = Title.getText();
        String content = Content.getText();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("notepadu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
