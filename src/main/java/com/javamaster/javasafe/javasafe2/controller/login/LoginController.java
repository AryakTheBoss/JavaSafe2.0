package com.javamaster.javasafe.javasafe2.controller.login;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class LoginController {
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginButtonClick() {
        String s = passwordField.getText();
        //check password and load password data and open main window if right
        System.out.println(s);
    }
}