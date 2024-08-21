package com.javamaster.javasafe.javasafe2.controller.login;

import com.javamaster.javasafe.javasafe2.util.JSafe;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class SetupViewController {

    @FXML
    private PasswordField masterKey;

    @FXML
    private PasswordField confirmMasterKey;

    @FXML
    protected void onSetMasterKeyClick(){
        if(!masterKey.getText().equals(confirmMasterKey.getText())){
            JOptionPane.showMessageDialog(null,"The keys do not match!","Try Again", JOptionPane.ERROR_MESSAGE);
        }
        //encrypt the password and store in file
        StrongPasswordEncryptor enc = new StrongPasswordEncryptor();
        String masterKeyHash = enc.encryptPassword(masterKey.getText());
        try {
            FileWriter writer = new FileWriter(JSafe.USER_MASTER_KEY_HASH, true);
            writer.append(masterKeyHash);
            writer.flush();
            writer.close();
            JOptionPane.showMessageDialog(null,"Master Key successfully set!","Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Unknown error occurred","Try again later", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

        //open main window here
        System.out.println("open main window here");

    }



}
