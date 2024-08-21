package com.javamaster.javasafe.javasafe2;

import com.javamaster.javasafe.javasafe2.util.JSafe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaSafeApplication extends Application {

    private static boolean isSetup = false;

    @Override
    public void start(Stage stage) throws IOException {
        if(isSetup) {
            startLoginScreen(stage);
        }else{
            startSetupScreen(stage);
        }
    }

    @Override
    public void init() throws Exception {
        //load data and check if user has been created
        if(JSafe.USER_DIRECTORY.exists()){
            if(JSafe.USER_MASTER_KEY_HASH.exists()){
                System.out.println("master key exists");
                isSetup = true;
            }else{
                JSafe.USER_MASTER_KEY_HASH.createNewFile();
            }
            if(!JSafe.USER_PASSWORD_DATA.exists()){
                JSafe.USER_PASSWORD_DATA.createNewFile();
            }
        }else{
            JSafe.USER_DIRECTORY.mkdirs();
        }
        super.init();
    }

    public static void main(String[] args) {
        launch();
    }

    public void startLoginScreen(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaSafeApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Enter Master Key");
        stage.setScene(scene);
        stage.show();
    }

    public void startSetupScreen(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaSafeApplication.class.getResource("setup-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 240);
        stage.setTitle("Setup Master Key");
        stage.setScene(scene);
        stage.show();
    }


}