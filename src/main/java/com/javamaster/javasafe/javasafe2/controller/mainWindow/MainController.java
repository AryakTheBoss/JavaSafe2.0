package com.javamaster.javasafe.javasafe2.controller.mainWindow;

import com.javamaster.javasafe.javasafe2.util.FileManager;
import com.javamaster.javasafe.javasafe2.util.PasswordEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class MainController {

    @FXML
    private ListView<PasswordEntry> passwordListView;
    @FXML
    private TextField accountField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private FileManager fileManager;
    private ObservableList<PasswordEntry> passwordEntries;

    public void setMasterPassword(String masterPassword) {
        this.fileManager = new FileManager(masterPassword);
        loadPasswords();
    }

    @FXML
    public void initialize() {
        passwordListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPasswordDetails(newValue)
        );
    }

    private void loadPasswords() {
        List<PasswordEntry> loadedPasswords = fileManager.loadPasswords();
        passwordEntries = FXCollections.observableArrayList(loadedPasswords);
        passwordListView.setItems(passwordEntries);
    }

    private void showPasswordDetails(PasswordEntry entry) {
        if (entry != null) {
            accountField.setText(entry.getAccount());
            usernameField.setText(entry.getUsername());
            passwordField.setText(entry.getPassword());
        } else {
            clearFields();
        }
    }

    @FXML
    private void handleAdd() {
        PasswordEntry newEntry = new PasswordEntry(
                accountField.getText(),
                usernameField.getText(),
                passwordField.getText()
        );
        passwordEntries.add(newEntry);
        fileManager.savePasswords(passwordEntries);
        clearFields();
    }

    @FXML
    private void handleUpdate() {
        PasswordEntry selectedEntry = passwordListView.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            selectedEntry.setAccount(accountField.getText());
            selectedEntry.setUsername(usernameField.getText());
            selectedEntry.setPassword(passwordField.getText());
            passwordListView.refresh(); // Refresh the list view to show changes
            fileManager.savePasswords(passwordEntries);
            clearFields();
        }
    }

    @FXML
    private void handleDelete() {
        PasswordEntry selectedEntry = passwordListView.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            passwordEntries.remove(selectedEntry);
            fileManager.savePasswords(passwordEntries);
            clearFields();
        }
    }

    private void clearFields() {
        accountField.clear();
        usernameField.clear();
        passwordField.clear();
    }
}
