package com.javamaster.javasafe.javasafe2.controller.login;

import com.javamaster.javasafe.javasafe2.controller.mainWindow.MainController;
import com.javamaster.javasafe.javasafe2.util.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private Label statusLabel;

    private FileManager fileManager;

    @FXML
    public void initialize() {
        // We pass a dummy password here. The actual password check will use the entered password.
        fileManager = new FileManager("dummy");
        if (!fileManager.isMasterPasswordSet()) {
            showCreateMasterPasswordDialog();
        }
    }

    @FXML
    private void handleLogin() {
        String password = passwordField.getText();
        fileManager = new FileManager(password);

        if (fileManager.checkMasterPassword(password)) {
            openMainWindow(password);
            closeWindow();
        } else {
            statusLabel.setText("Incorrect password.");
        }
    }

    private void showCreateMasterPasswordDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateMasterPassword.fxml"));
            Parent root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Master Password");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(passwordField.getScene().getWindow());
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            CreateMasterPasswordController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMainWindow(String masterPassword) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Parent root = loader.load();

            MainController controller = loader.getController();
            controller.setMasterPassword(masterPassword);

            Stage stage = new Stage();
            stage.setTitle("Password Manager");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) passwordField.getScene().getWindow();
        stage.close();
    }
}
