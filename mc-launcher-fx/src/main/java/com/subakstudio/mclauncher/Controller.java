package com.subakstudio.mclauncher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subakstudio.http.OkHttpClientHelper;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by yeoupooh on 2/14/16.
 */
@Slf4j
public class Controller implements Initializable {

    private CookieManager cookieManager;
    private String[] acceptableUrls;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="buttonLaunchMinecraft"
    private Button buttonLaunchMinecraft; // Value injected by FXMLLoader

    @FXML // fx:id="buttonGo"
    private Button buttonGo; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldUrl"
    private TextField textFieldUrl; // Value injected by FXMLLoader

    @FXML // fx:id="webView"
    private WebView webView; // Value injected by FXMLLoader

    @FXML
    void buttonGoClicked(ActionEvent event) {
        navigate();
    }

    @FXML
    void buttonGoForwardClicked(ActionEvent event) {
        goForward();
    }

    @FXML
    void buttonGoBackClicked(ActionEvent event) {
        goBack();
    }

    @FXML
    void buttonLaunchMinecraftClicked(ActionEvent event) {

    }

    @FXML
    void buttonRefreshClicked(ActionEvent event) {

    }

    @FXML
    void textFieldUrlKeyPressed(KeyEvent event) {
//        log.debug("keycode=" + event.getCode());
        if (event.getCode() == KeyCode.ENTER) {
            navigate();
        }
    }

    private void navigate() {
        log.debug(textFieldUrl.getText());
        webView.getEngine().load(textFieldUrl.getText());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert buttonLaunchMinecraft != null : "fx:id=\"buttonLaunchMinecraft\" was not injected: check your FXML file 'main2.fxml'.";
        assert buttonGo != null : "fx:id=\"buttonGo\" was not injected: check your FXML file 'main2.fxml'.";
        assert textFieldUrl != null : "fx:id=\"textFieldUrl\" was not injected: check your FXML file 'main2.fxml'.";
        assert webView != null : "fx:id=\"webView\" was not injected: check your FXML file 'main2.fxml'.";

        setupWebView();

    }

    private void setupWebView() {
        cookieManager = new java.net.CookieManager();
        CookieHandler.setDefault(cookieManager);
        webView.getEngine().locationProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                log.debug(newValue.toString());
                if (newValue instanceof String) {
                    String url = (String) newValue;
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        McLauncherConfig config = (McLauncherConfig) mapper.readValue(getClass().getResourceAsStream("/mclauncher.config.json"), McLauncherConfig.class);
                        boolean acceptUrl = false;
                        for (DownloadableUrl dUrl : config.getDownloadableUrls()) {
                            if (dUrl.getStartsWith() != null) {
                                if (url.startsWith(dUrl.getStartsWith())) {
                                    acceptUrl = true;
                                    break;
                                }
                            }
                        }
                        if (acceptUrl) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    showDownloadConfirmDialog(url);
                                }
                            });
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
//                    if (url.startsWith("https://dl.dropboxusercontent.com/content_link/")
//                            || url.startsWith("http://en.multip.net/view/down/id/")) {
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                showDownloadConfirmDialog(url);
//                            }
//                        });
//                    }
                }
            }

//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//            }
        });
    }

    private void showDownloadConfirmDialog(String url) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Download File");
        alert.setHeaderText("Download a file from " + url);
        alert.setContentText("Do you want to download this into mods folder?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            log.info("Downloading..." + url);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    downloadFile(url);
                }
            });
        } else {
            // ... user chose CANCEL or closed the dialog
            log.info("Canceled");
        }
    }

    private void downloadFile(String url) {
        for (HttpCookie cookie : cookieManager.getCookieStore().getCookies()) {
            log.debug(cookie.toString());
        }

        OkHttpClientHelper httpClient = new OkHttpClientHelper();
        try {
            httpClient.downloadBinary(url, new File("build/tmp/test.bin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goForward() {
        Platform.runLater(new Runnable() {
            public void run() {
                webView.getEngine().getHistory().go(1);
            }
        });
    }

    private void goBack() {
        Platform.runLater(new Runnable() {
            public void run() {
                webView.getEngine().getHistory().go(-1);
            }
        });
    }

}