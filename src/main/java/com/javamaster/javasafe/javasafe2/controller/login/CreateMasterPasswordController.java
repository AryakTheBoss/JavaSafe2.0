package com.javamaster.javasafe.javasafe2.controller.login;

import com.javamaster.javasafe.javasafe2.util.FileManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateMasterPasswordController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label statusLabel;

    private Stage dialogStage;

    // Regex to enforce:
    // - at least 6 characters
    // - at least one uppercase letter
    // - at least one digit
    // - at least one special character
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{6,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Validates the password against the defined regex pattern.
     * @param password The password to validate.
     * @return true if the password is valid, false otherwise.
     */
    private boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @FXML
    private void handleCreate() {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Check if passwords are empty or don't match
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            statusLabel.setText("Password fields cannot be empty.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match.");
            return;
        }

        // Validate the password strength
        if (!isPasswordValid(password)) {
            statusLabel.setText("Password must be at least 6 characters and include an uppercase letter, a number, and a special symbol.");
            statusLabel.setWrapText(true); // Ensure the full message is visible
            return;
        }

        // If all checks pass, create the master password
        FileManager fileManager = new FileManager(password);
        fileManager.createMasterPassword(password);

        // Close the dialog
        if(dialogStage != null) {
            dialogStage.close();
        }
    }
}
