package com.javamaster.javasafe.javasafe2.util;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String APP_DATA_DIRECTORY = System.getenv("APPDATA") + "/PasswordManager";
    private static final String PASSWORD_FILE = APP_DATA_DIRECTORY + "/passwords.dat";
    private static final String MASTER_KEY_HASH_FILE = APP_DATA_DIRECTORY + "/masterkey.dat";

    private final AES256TextEncryptor textEncryptor;
    private final StrongPasswordEncryptor passwordEncryptor;

    public FileManager(String masterPassword) {
        this.textEncryptor = new AES256TextEncryptor();
        this.textEncryptor.setPassword(masterPassword);
        this.passwordEncryptor = new StrongPasswordEncryptor();

        // Create directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(APP_DATA_DIRECTORY));
        } catch (IOException e) {
            System.err.println("Could not create app data directory.");
            e.printStackTrace();
        }
    }

    public boolean isMasterPasswordSet() {
        return Files.exists(Paths.get(MASTER_KEY_HASH_FILE));
    }

    public void createMasterPassword(String password) {
        String hashed = passwordEncryptor.encryptPassword(password);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MASTER_KEY_HASH_FILE))) {
            oos.writeObject(hashed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkMasterPassword(String password) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MASTER_KEY_HASH_FILE))) {
            String storedHash = (String) ois.readObject();
            return passwordEncryptor.checkPassword(password, storedHash);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PasswordEntry> loadPasswords() {
        File file = new File(PASSWORD_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            String encryptedData = (String) ois.readObject();
            String decryptedData = textEncryptor.decrypt(encryptedData);

            // This is a simple way to store the list.
            // A more robust solution might use a proper serialization format like JSON or XML inside the encrypted string.
            try (ObjectInputStream dataOis = new ObjectInputStream(new ByteArrayInputStream(decryptedData.getBytes(StandardCharsets.ISO_8859_1)))) {
                return (List<PasswordEntry>) dataOis.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void savePasswords(List<PasswordEntry> passwords) {
        // This is the key fix. The ObservableList from JavaFX is not serializable.
        // We must convert it to a standard ArrayList before attempting to serialize it.
        List<PasswordEntry> serializableList = new ArrayList<>(passwords);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            // We write the serializable ArrayList, not the original ObservableList.
            oos.writeObject(serializableList);
            String serializedData = baos.toString(StandardCharsets.ISO_8859_1);
            String encryptedData = textEncryptor.encrypt(serializedData);

            try (ObjectOutputStream fileOos = new ObjectOutputStream(new FileOutputStream(PASSWORD_FILE))) {
                fileOos.writeObject(encryptedData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
