package com.javamaster.javasafe.javasafe2;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class LoginController {
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginButtonClick() {
        String s = passwordField.getText();
        System.out.println(s);
    }
}