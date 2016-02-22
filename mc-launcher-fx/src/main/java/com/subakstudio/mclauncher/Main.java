package com.subakstudio.mclauncher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Created by yeoupooh on 2/14/16.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getProtectionDomain().getCodeSource().getLocation());
        fxmlLoader.setResources(ResourceBundle.getBundle("strings"));
        Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("main2.fxml").openStream());
        primaryStage.setTitle("McLauncher");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        SingletonUserConfigFile.getInstance().load();
        SingletonMcLauncherConfigFile.getInstance().load();
        launch(args);
    }

}
