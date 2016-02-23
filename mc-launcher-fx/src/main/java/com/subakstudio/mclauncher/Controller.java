package com.subakstudio.mclauncher;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subakstudio.http.OkHttpClientHelper;
import com.subakstudio.io.FileUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.SetChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.controlsfx.dialog.ProgressDialog;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.*;

/**
 * Created by yeoupooh on 2/14/16.
 */
@Slf4j
public class Controller implements Initializable {

    private CookieManager cookieManager;
    private String[] acceptableUrls;
    private ModList modList = new ModList();
    private ObservableList<DownloadableModRow> downloadableMods = observableArrayList();
    private ObservableList<ForgeRow> forges = observableArrayList();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="splitPane"
    private SplitPane splitPane; // Value injected by FXMLLoader

    @FXML // fx:id="paneLauncher"
    private AnchorPane paneLauncher; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldModsFilter"
    private TextField textFieldModsFilter; // Value injected by FXMLLoader

    @FXML // fx:id="radioButtonModsAll"
    private RadioButton radioButtonModsAll; // Value injected by FXMLLoader

    @FXML // fx:id="toggleGrooupMods"
    private ToggleGroup toggleGrooupMods; // Value injected by FXMLLoader

    @FXML // fx:id="radioButtonModsEnabled"
    private RadioButton radioButtonModsEnabled; // Value injected by FXMLLoader

    @FXML // fx:id="radioButtonModsDisabled"
    private RadioButton radioButtonModsDisabled; // Value injected by FXMLLoader

    @FXML // fx:id="listViewMods"
    private ListView<ModRow> listViewMods; // Value injected by FXMLLoader

    @FXML // fx:id="labelEanbledModsCount"
    private Label labelEanbledModsCount; // Value injected by FXMLLoader

    @FXML // fx:id="tabPane"
    private TabPane tabPane; // Value injected by FXMLLoader

    @FXML // fx:id="tabWeb"
    private Tab tabWeb; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldWebUrl"
    private TextField textFieldWebUrl; // Value injected by FXMLLoader

    @FXML // fx:id="webView"
    private WebView webView; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldDownloadableModsFilter"
    private TextField textFieldDownloadableModsFilter; // Value injected by FXMLLoader

    @FXML // fx:id="tableDownloadableMods"
    private TableView<DownloadableModRow> tableDownloadableMods; // Value injected by FXMLLoader

    @FXML // fx:id="tableColModsName"
    private TableColumn<DownloadableModRow, String> tableColModsName; // Value injected by FXMLLoader

    @FXML // fx:id="tableColModsVersion"
    private TableColumn<DownloadableModRow, String> tableColModsVersion; // Value injected by FXMLLoader

    @FXML // fx:id="tableColModsForgeVersion"
    private TableColumn<DownloadableModRow, String> tableColModsForgeVersion; // Value injected by FXMLLoader

    @FXML // fx:id="tableColModsFileName"
    private TableColumn<DownloadableModRow, String> tableColModsFileName; // Value injected by FXMLLoader

    @FXML // fx:id="tableColModsUrl"
    private TableColumn<DownloadableModRow, String> tableColModsUrl; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldModsUrl"
    private TextField textFieldModsUrl; // Value injected by FXMLLoader

    @FXML
    private TextField textFieldMcDataFolder;

    @FXML
    private TextField textFieldMcExecuable;

    @FXML
    private TableView<ForgeRow> tableViewForges;

    @FXML
    private TableColumn<ForgeRow, String> tableColForgeVersion;

    @FXML
    private TableColumn<ForgeRow, String> tableColForgeFileName;

    @FXML
    private TableColumn<ForgeRow, String> tableColForgeUrl;


    @FXML
    void buttonDownloadSelectedForgesClicked(ActionEvent event) {
        downloadSelectedForges();
    }

    @FXML
    void buttonDownloadSelectedModsClicked(ActionEvent event) {
        downloadSelectedMods();
    }


    @FXML
    void buttonInstallSelectedForgeClicked(ActionEvent event) {
        installSelectedForge();
    }

    @FXML
    void buttonLaunchMinecraftClicked(ActionEvent event) {
        launchMinecraft();
    }

    @FXML
    void buttonLauncherRefreshClicked(ActionEvent event) {

    }

    @FXML
    void buttonMcDataFolderBrowseClicked(ActionEvent event) {

    }

    @FXML
    void buttonMcExecutableBrowseClicked(ActionEvent event) {

    }


    @FXML
    void buttonModsClearFilterClicked(ActionEvent event) {
        textFieldModsFilter.clear();
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

        setupSettings();
        setupSplitPane();
        setupWebView();
        setupDownloadableModsTable();
        setupForgesTable();
        setupModsListView();
    }

    private void launchMinecraft() {
        refreshMcDataFolder();
        ProcessBuilder pb = new ProcessBuilder(SingletonUserConfigFile.getConfig().getMcExecutable());
        try {
            Process p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshMcDataFolder() {
        for (ModRow row : modList.getModfied()) {
            log.debug("file=[$mod.file] newChecked=[$mod.newChecked]");
            if (row.isChecked()) {
                FileUtils.move(row.getFile(), MinecraftDataFolder.getModsFolder(new File(SingletonUserConfigFile.getConfig().getMcDataFolder())));
            } else {
                FileUtils.move(row.getFile(), MinecraftDataFolder.getDisabledModsFolder(new File(SingletonUserConfigFile.getConfig().getMcDataFolder())));
            }
        }
    }

    private void setupModsListView() {
        listViewMods.setCellFactory(CheckBoxListCell.forListView(new Callback<ModRow, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(ModRow item) {
                return item.onProperty();
            }
        }));

        // Set up filtered data
        FilteredList<ModRow> filteredMods = new FilteredList<>(modList.getMods(), p -> true);

        // TODO filter with name
//        textFieldModsFilter.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredMods.setPredicate(modRow -> {
//                // If filter text is empty, display all persons.
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//
//                // Compare first name and last name of every modRow with filter text.
//                String lowerCaseFilter = newValue.toLowerCase();
//
//                if (modRow.getFileName() != null && modRow.getName().toLowerCase().contains(lowerCaseFilter)) {
//                    return true; // Filter matches name.
//                } else if (modRow.getFileName() != null && modRow.getFileName().toLowerCase().contains(lowerCaseFilter)) {
//                    return true; // Filter matches file name.
//                } else if (modRow.getUrl() != null && modRow.getUrl().toLowerCase().contains(lowerCaseFilter)) {
//                    return true;
//                }
//                return false; // Does not match.
//            });
//        });

        toggleGrooupMods.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (toggleGrooupMods.getSelectedToggle() != null) {
                    System.out.println(toggleGrooupMods.getSelectedToggle());
                    if (radioButtonModsAll.isSelected()) {
                        filteredMods.setPredicate(row -> {
                            return true;
                        });
                    } else if (radioButtonModsEnabled.isSelected()) {
                        filteredMods.setPredicate(row -> {
                            return row.isChecked();
                        });
                    } else if (radioButtonModsDisabled.isSelected()) {
                        filteredMods.setPredicate(row -> {
                            return !row.isChecked();
                        });
                    } else {
                        log.error("what?");
                    }
                }
            }
        });

        listViewMods.setItems(filteredMods);

        modList.getEnabled().addListener(new SetChangeListener<ModRow>() {
            @Override
            public void onChanged(Change<? extends ModRow> c) {
                System.out.println("modified: c=" + c);
                labelEanbledModsCount.setText("Enabled Mods: " + modList.getEnabled().size());
            }
        });

        setMcDataFolder(SingletonUserConfigFile.getConfig().getMcDataFolder());
    }

    private void setMcDataFolder(String folder) {
        File dataFolder = new File(folder);

        FileFilter dirFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().equals(".DS_Store")) {
                    return false;
                }

                if (pathname.isDirectory()
                        || pathname.getName().toLowerCase().endsWith(".jar")
                        || pathname.getName().toLowerCase().endsWith(".zip")) {
                    return true;
                }

                return false;
            }
        };

        if (dataFolder.exists()) {
            if (MinecraftDataFolder.getModsFolder(dataFolder).exists()) {
                File[] modFiles = MinecraftDataFolder.getModsFolder(dataFolder).listFiles(dirFilter);
                for (File file : modFiles) {
                    addModRow(file, true);
                }
                log.info("mods files are loaded under mods folder.");
            } else {
                log.warn("No mods folder.");
            }
            if (MinecraftDataFolder.getDisabledModsFolder(dataFolder).exists()) {
                File[] modFiles = MinecraftDataFolder.getDisabledModsFolder(dataFolder).listFiles(dirFilter);
                for (File file : modFiles) {
                    addModRow(file, false);
                }
                log.info("mods files are loaded under disabled mods folder.");
            } else {
                log.warn("No disabled mods folder.");
            }
        }

        // TODO sort list
//        Collections.sort(mods, new Comparator<ModsTableRow>() {
//            @Override
//            public int compare(ModsTableRow o1, ModsTableRow o2) {
//                String str1 = o1.getFile().getName();
//                String str2 = o2.getFile().getName();
//                int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
//                if (res == 0) {
//                    res = str1.compareTo(str2);
//                }
//                return res;
//            }
//        });
    }

    private void addModRow(File file, boolean checked) {
        ModRow row = new ModRow();
        row.setFile(file);
        row.setOriginChecked(checked);
        row.setChecked(checked);
        modList.add(row);
    }

    private void updateEnabledModsCount() {

    }

    private void setupSettings() {
        // TODO property binding
        textFieldMcDataFolder.setText(SingletonUserConfigFile.getConfig().getMcDataFolder());
        textFieldMcExecuable.setText(SingletonUserConfigFile.getConfig().getMcExecutable());
        textFieldModsUrl.setText(SingletonUserConfigFile.getConfig().getModsUrl());
    }

    private void setupForgesTable() {
        tableViewForges.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableColForgeVersion.setCellValueFactory(new PropertyValueFactory<ForgeRow, String>("version"));
        tableColForgeFileName.setCellValueFactory(new PropertyValueFactory<ForgeRow, String>("fileName"));
        tableColForgeUrl.setCellValueFactory(new PropertyValueFactory<ForgeRow, String>("url"));

        OkHttpClientHelper httpClient = new OkHttpClientHelper();
        try {
            String json = httpClient.downloadText(SingletonMcLauncherConfigFile.getConfig().getForgesUrl());
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ForgeList list = (ForgeList) om.readValue(json, ForgeList.class);

            forges.addAll(list.getForges());

            tableViewForges.setItems(forges);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

        tableColModsName.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("name"));
        tableColModsVersion.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("version"));
        tableColModsForgeVersion.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("forgeVersion"));
        tableColModsFileName.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("fileName"));
        tableColModsUrl.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("url"));

        OkHttpClientHelper httpClient = new OkHttpClientHelper();
        try {
            String json = httpClient.downloadText(textFieldModsUrl.getText());
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            DownloadableModList list = (DownloadableModList) om.readValue(json, DownloadableModList.class);

            downloadableMods.addAll(list.getMods());

            // Set up filtered data
            FilteredList<DownloadableModRow> filteredMods = new FilteredList<>(downloadableMods, p -> true);

            textFieldModsFilter.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredMods.setPredicate(modRow -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every modRow with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (modRow.getFileName() != null && modRow.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches name.
                    } else if (modRow.getFileName() != null && modRow.getFileName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches file name.
                    } else if (modRow.getUrl() != null && modRow.getUrl().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false; // Does not match.
                });
            });

            tableDownloadableMods.setItems(filteredMods);

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
                    McLauncherConfig config = SingletonMcLauncherConfigFile.getConfig();
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
        alert.setContentText("Do you want to download this into downloadableMods folder?");

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

    private void downloadSelectedForges() {
        ObservableList<ForgeRow> selection = tableViewForges.getSelectionModel().getSelectedItems();
        for (ForgeRow row : selection) {
            downloadFile(row.getUrl(), row.getFileName());
        }
    }

    private void installSelectedForge() {
        ObservableList<ForgeRow> selection = tableViewForges.getSelectionModel().getSelectedItems();
        if (selection.size() == 1) {
            // TODO launch forge installer
        }
    }

}