package com.javamaster.javasafe.javasafe2.controller.login;

import com.javamaster.javasafe.javasafe2.util.FileManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class CreateMasterPasswordController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label statusLabel;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleCreate() {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password.isEmpty() || !password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match or are empty.");
            return;
        }

        FileManager fileManager = new FileManager(password);
        fileManager.createMasterPassword(password);

        if(dialogStage != null) {
            dialogStage.close();
        }
    }
}
