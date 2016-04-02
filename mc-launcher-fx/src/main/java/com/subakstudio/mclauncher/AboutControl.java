package com.subakstudio.mclauncher;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by yeoupooh on 2/22/16.
 */
public class AboutControl extends VBox {
    public AboutControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
                "about.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
