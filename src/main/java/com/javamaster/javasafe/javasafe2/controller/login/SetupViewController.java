package com.javamaster.javasafe.javasafe2.controller.login;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

import javax.swing.*;

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

    }



}
