package com.javamaster.javasafe.javasafe2.controller.login;

import com.javamaster.javasafe.javasafe2.util.JSafe;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginController {
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginButtonClick() {
        String enteredKey = passwordField.getText();
        StrongPasswordEncryptor enc = new StrongPasswordEncryptor();
        try(BufferedReader br = new BufferedReader(new FileReader(JSafe.USER_MASTER_KEY_HASH))) {
            String masterKeyHash = br.readLine();
            if(enc.checkPassword(enteredKey, masterKeyHash)){
                //open main window here
                System.out.println("open main window here");
                JSafe.ENCRYPTED_MASTER_KEY = masterKeyHash;
            }else{
                JOptionPane.showMessageDialog(null,"Master key is incorrect!","Try Again", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}