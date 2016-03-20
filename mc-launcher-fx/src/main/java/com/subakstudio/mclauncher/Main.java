package com.subakstudio.mclauncher;

import com.subakstudio.mclauncher.config.SingletonMcLauncherConfigFile;
import com.subakstudio.mclauncher.config.SingletonUserConfigFile;
import com.subakstudio.mclauncher.util.MinecraftDataFolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ResourceBundle;

/**
 * Created by yeoupooh on 2/14/16.
 */
@Slf4j
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getProtectionDomain().getCodeSource().getLocation());
        fxmlLoader.setResources(ResourceBundle.getBundle("strings"));
        Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("main2.fxml").openStream());
        primaryStage.setTitle("McLauncher 2.0 alpha");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/5548-256x256x8.png")));
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        SingletonMcLauncherConfigFile.getInstance().load();
        SingletonUserConfigFile.getInstance().load();

        // Create data folder if not exists
        File modsFolder = MinecraftDataFolder.getModsFolder(SingletonUserConfigFile.getConfig().getMcDataFolder());
        if (!modsFolder.exists()) {
            modsFolder.mkdirs();
            log.info("Created mods folder: " + modsFolder.getAbsolutePath());
        }
        File disabledModsFoldr = MinecraftDataFolder.getDisabledModsFolder(SingletonUserConfigFile.getConfig().getMcDataFolder());
        if (!disabledModsFoldr.exists()) {
            disabledModsFoldr.mkdirs();
            log.info("Created disabled mods folder: " + disabledModsFoldr);
        }

        launch(args);
    }

}
