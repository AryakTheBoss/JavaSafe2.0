package com.javamaster.javasafe.javasafe2.controller.login;

import com.javamaster.javasafe.javasafe2.controller.mainWindow.MainController;
import com.javamaster.javasafe.javasafe2.util.FileManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class LoginController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private Label statusLabel;

    private FileManager fileManager;
    private final int ALLOWED_PASSWORD_ATTEMPTS = 5;
    private int failedAttempts = 0;

    @FXML
    public void initialize() {
        // We use a dummy password here just to create the FileManager instance
        // to check if the master password file exists.
        fileManager = new FileManager("dummy");
        if (!fileManager.isMasterPasswordSet()) {
            // This is the key fix. We use Platform.runLater() to ensure that this
            // code runs after the FXML has been fully loaded and the initial scene
            // is rendered. This guarantees that getScene().getWindow() will not be null.
            Platform.runLater(this::showCreateMasterPasswordDialog);
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
            failedAttempts++;
            if(failedAttempts == 5){
                try {
                    statusLabel.setText("Password entered incorrectly too many times. Wiping data...");
                    JOptionPane.showMessageDialog(null, "Password entered incorrectly too many times. Wiping data...", "Error!", JOptionPane.ERROR_MESSAGE);
                    fileManager.wipeData();
                    System.exit(0);
                } catch (Exception e) {
                    System.out.println("Error in wiping");
                    e.printStackTrace();
                }
            }else if(failedAttempts >= 3){
                statusLabel.setText("Incorrect password. "+(ALLOWED_PASSWORD_ATTEMPTS-failedAttempts)+" attempts remaining.");
            }
        }
    }

    private void showCreateMasterPasswordDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javamaster/javasafe/javasafe2/CreateMasterPassword.fxml"));
            Parent root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Master Password");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            // This line was causing the NullPointerException. By using Platform.runLater,
            // we ensure the passwordField's scene and window are available.
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javamaster/javasafe/javasafe2/Main.fxml"));
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
