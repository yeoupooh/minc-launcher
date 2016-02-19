package com.subakstudio.mclauncher;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subakstudio.http.OkHttpClientHelper;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.controlsfx.dialog.ProgressDialog;

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

    @FXML // fx:id="splitPane"
    private SplitPane splitPane; // Value injected by FXMLLoader

    @FXML // fx:id="paneLauncher"
    private AnchorPane paneLauncher; // Value injected by FXMLLoader

    @FXML // fx:id="tabPane"
    private TabPane tabPane; // Value injected by FXMLLoader

    @FXML // fx:id="tabWeb"
    private Tab tabWeb; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldWebUrl"
    private TextField textFieldWebUrl; // Value injected by FXMLLoader

    @FXML // fx:id="webView"
    private WebView webView; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldModsUrl"
    private TextField textFieldModsUrl; // Value injected by FXMLLoader

    @FXML // fx:id="tableDownloadableMods"
    private TableView<DownloadableModRow> tableDownloadableMods; // Value injected by FXMLLoader

    @FXML // fx:id="tableColName"
    private TableColumn<DownloadableModRow, String> tableColName; // Value injected by FXMLLoader

    @FXML // fx:id="tableColVersion"
    private TableColumn<DownloadableModRow, String> tableColVersion; // Value injected by FXMLLoader

    @FXML // fx:id="tableColForgeVersion"
    private TableColumn<DownloadableModRow, String> tableColForgeVersion; // Value injected by FXMLLoader

    @FXML // fx:id="tableColFileName"
    private TableColumn<DownloadableModRow, String> tableColFileName; // Value injected by FXMLLoader

    @FXML // fx:id="tableColUrl"
    private TableColumn<DownloadableModRow, String> tableColUrl; // Value injected by FXMLLoader

    @FXML
    void buttonDownloadSelectedModsClicked(ActionEvent event) {
        downloadSelectedMods();
    }

    @FXML
    void buttonUpdateDownloadableModsListClicked(ActionEvent event) {

    }

    @FXML
    void buttonWebBackClicked(ActionEvent event) {
        goBack();
    }

    @FXML
    void buttonWebForwardClicked(ActionEvent event) {
        goForward();
    }

    @FXML
    void buttonWebGoClicked(ActionEvent event) {
        navigate();
    }

    @FXML
    void buttonWebRefreshClicked(ActionEvent event) {

    }

    @FXML
    void textFieldWebUrlKeyPressed(KeyEvent event) {
//        log.debug("keycode=" + event.getCode());
        if (event.getCode() == KeyCode.ENTER) {
            navigate();
        }
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert log != null : "log is not injected.";

        assert textFieldWebUrl != null : "fx:id=\"textFieldWebUrl\" was not injected: check your FXML file 'main2.fxml'.";
        assert webView != null : "fx:id=\"webView\" was not injected: check your FXML file 'main2.fxml'.";
        assert textFieldModsUrl != null : "fx:id=\"textFieldModsUrl\" was not injected: check your FXML file 'main2.fxml'.";
        assert tableDownloadableMods != null : "fx:id=\"tableDownloadableMods\" was not injected: check your FXML file 'main2.fxml'.";

        setupSplitPane();
        setupWebView();
        setupDownloadableModsTable();
    }

    private void setupSplitPane() {
        // Fix size of launcher pane
        SplitPane.setResizableWithParent(paneLauncher, Boolean.FALSE);
    }

    private void downloadSelectedMods() {
        ObservableList<DownloadableModRow> selection = tableDownloadableMods.getSelectionModel().getSelectedItems();
        for (DownloadableModRow row : selection) {
            if (row.isUseWebBrowser()) {
                tabPane.getSelectionModel().select(tabWeb);
                navigate(row.getUrl());
            } else {
                downloadFile(row.getUrl(), row.getFileName());
            }
        }
    }

    private void downloadFile(String url, String fileName) {
        DownloadFileService service = new DownloadFileService();
        service.setUrl(url);
        service.setFileName(fileName);
        ProgressDialog progDiag = new ProgressDialog(service);
        progDiag.setTitle("Download File");
        progDiag.initOwner(getPrimaryStage());
        progDiag.setHeaderText("Downloading..." + url);
        progDiag.initModality(Modality.WINDOW_MODAL);
        service.start();
    }

    private Window getPrimaryStage() {
        return webView.getScene().getWindow();
    }

    private void setupDownloadableModsTable() {
        tableDownloadableMods.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableColName.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("name"));
        tableColVersion.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("version"));
        tableColForgeVersion.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("forgeVersion"));
        tableColFileName.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("fileName"));
        tableColUrl.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("url"));

        OkHttpClientHelper httpClient = new OkHttpClientHelper();
        try {
            String json = httpClient.downloadText(textFieldModsUrl.getText());
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            DownloadableModList list = (DownloadableModList) om.readValue(json, DownloadableModList.class);

            tableDownloadableMods.setItems(FXCollections.observableArrayList(list.getMods()));

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                }
            }
        });
    }

    private void navigate() {
        log.debug(textFieldWebUrl.getText());
        webView.getEngine().load(textFieldWebUrl.getText());
    }

    private void navigate(String url) {
        textFieldWebUrl.setText(url);
        navigate();
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
                    downloadFile(url, FilenameUtils.getName(url));
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