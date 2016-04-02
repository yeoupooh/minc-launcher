package com.subakstudio.mclauncher;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subakstudio.http.CookieUtils;
import com.subakstudio.http.OkHttpClientHelper;
import com.subakstudio.io.FileUtils;
import com.subakstudio.mclauncher.config.*;
import com.subakstudio.mclauncher.model.*;
import com.subakstudio.mclauncher.util.MinecraftDataFolder;
import com.subakstudio.mclauncher.util.ResStrings;
import com.subakstudio.util.PlatformUtils;
import com.sun.webkit.network.CookieManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.SetChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.controlsfx.dialog.ProgressDialog;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.subakstudio.mclauncher.Constants.MC_LAUNCHER_REPO_FORGES_FOLDER;
import static com.subakstudio.util.PlatformUtils.openFileBrowser;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.concurrent.Worker.State;

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
    private Application application;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="splitPane"
    private SplitPane splitPane; // Value injected by FXMLLoader

    @FXML // fx:id="paneLauncher"
    private AnchorPane paneLauncher; // Value injected by FXMLLoader

    @FXML // fx:id="checkBoxToggleEnabledAllMods"
    private CheckBox checkBoxToggleEnabledAllMods; // Value injected by FXMLLoader

    @FXML // fx:id="radioButtonModsAll"
    private RadioButton radioButtonModsAll; // Value injected by FXMLLoader

    @FXML // fx:id="toggleGrooupMods"
    private ToggleGroup toggleGrooupMods; // Value injected by FXMLLoader

    @FXML // fx:id="radioButtonModsEnabled"
    private RadioButton radioButtonModsEnabled; // Value injected by FXMLLoader

    @FXML // fx:id="radioButtonModsDisabled"
    private RadioButton radioButtonModsDisabled; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldModsFilter"
    private TextField textFieldModsFilter; // Value injected by FXMLLoader

    @FXML // fx:id="listViewMods"
    private ListView<ModRow> listViewMods; // Value injected by FXMLLoader

    @FXML // fx:id="tabPane"
    private TabPane tabPane; // Value injected by FXMLLoader

    @FXML // fx:id="tabWeb"
    private Tab tabWeb; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldWebUrl"
    private TextField textFieldWebUrl; // Value injected by FXMLLoader

    @FXML // fx:id="hboxBookmark"
    private HBox hboxBookmark; // Value injected by FXMLLoader

    @FXML // fx:id="webView"
    private WebView webView; // Value injected by FXMLLoader

    @FXML // fx:id="labelWebStatus"
    private Label labelWebStatus; // Value injected by FXMLLoader

    @FXML // fx:id="progressBarWeb"
    private ProgressBar progressBarWeb; // Value injected by FXMLLoader

    @FXML // fx:id="tabModsDownloader"
    private Tab tabModsDownloader; // Value injected by FXMLLoader

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

    @FXML // fx:id="tableColModsViaWeb"
    private TableColumn<DownloadableModRow, Boolean> tableColModsViaWeb; // Value injected by FXMLLoader

    @FXML // fx:id="tableColModsFileName"
    private TableColumn<DownloadableModRow, String> tableColModsFileName; // Value injected by FXMLLoader

    @FXML // fx:id="tableColModsUrl"
    private TableColumn<DownloadableModRow, String> tableColModsUrl; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldModsUrl"
    private TextField textFieldModsUrl; // Value injected by FXMLLoader

    @FXML
    private TableView<ForgeRow> tableViewForges;

    @FXML
    private TableColumn<ForgeRow, String> tableColForgeVersion;

    @FXML
    private TableColumn<ForgeRow, String> tableColForgeFileName;

    @FXML
    private TableColumn<ForgeRow, String> tableColForgeUrl;

    @FXML // fx:id="tabSettings"
    private Tab tabSettings; // Value injected by FXMLLoader

    @FXML
    private TextField textFieldMcDataFolder;

    @FXML
    private TextField textFieldMcExecuable;

    @FXML // fx:id="tabAbout"
    private Tab tabAbout; // Value injected by FXMLLoader

    @FXML // fx:id="labelMcLauncherVersion"
    private Label labelMcLauncherVersion; // Value injected by FXMLLoader

    @FXML // fx:id="hyperLinkMcLauncherSite"
    private Hyperlink hyperLinkMcLauncherSite; // Value injected by FXMLLoader

    @FXML // fx:id="labelEanbledModsCount"
    private Label labelEanbledModsCount; // Value injected by FXMLLoader

    @FXML
    void buttonDeleteSelectedModsClicked(ActionEvent event) {
        for (ModRow row : listViewMods.getSelectionModel().getSelectedItems()) {
            row.getFile().delete();
        }
        loadModList();
    }

    @FXML
    void buttonDonateClicked(ActionEvent event) {
        donate();
    }

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
        organizeMods();
        loadModList();
    }

    @FXML
    void buttonMcDataFolderBrowseClicked(ActionEvent event) {
        chooseMcDataFolder();
    }

    @FXML
    void buttonMcExecutableBrowseClicked(ActionEvent event) {
        chooseMcExectuable();
    }

    @FXML
    void buttonModsClearFilterClicked(ActionEvent event) {
        textFieldModsFilter.clear();
    }


    @FXML
    void buttonOpenDisabledModsFolderClicked(ActionEvent event) {
        try {
            openFileBrowser(SingletonUserConfigFile.getConfig().getDisabledModsFolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void buttonOpenEnabledModsFolderClicked(ActionEvent event) {
        try {
            openFileBrowser(SingletonUserConfigFile.getConfig().getModsFolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void buttonUpdateDownloadableModsListClicked(ActionEvent event) {
        updateDownloadableMods();
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
        webView.getEngine().reload();
    }

    @FXML
    void checkBoxToggleEnabledAllModsClicked(ActionEvent event) {
        modList.setAll(checkBoxToggleEnabledAllMods.isSelected());
    }

    @FXML
    void hyperLinkMcLauncherSiteClicked(ActionEvent event) {
        navigate(hyperLinkMcLauncherSite.getText());
        tabPane.getSelectionModel().select(tabWeb);
    }


    @FXML
    void hyperLinkMinecraftForumClicked(ActionEvent event) {
        navigate();
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

        setupAbout();
        setupSettings();
        setupSplitPane();
        setupWebView();
        setupDownloadableModsTable();
        setupForgesTable();
        setupModsListView();
        setupBookmarks();
    }

    private void setupAbout() {
        labelMcLauncherVersion.setText(Constants.VERSION);
    }

    private void chooseMcDataFolder() {
        DirectoryChooser dialog = new DirectoryChooser();
        dialog.setTitle("Choose Minecraft Data Folder");
        if (SingletonUserConfigFile.getConfig().getMcDataFolder() != null) {
            dialog.setInitialDirectory(new File(SingletonUserConfigFile.getConfig().getMcDataFolder()));
        }
        File folder = dialog.showDialog(this.getPrimaryStage());
        if (folder != null) {
            SingletonUserConfigFile.getConfig().setMcDataFolder(folder.getAbsolutePath());
            SingletonUserConfigFile.getInstance().save();
            textFieldMcDataFolder.setText(folder.getAbsolutePath());
            loadModList();
        }
    }

    private void chooseMcExectuable() {
        FileChooser dialog = new FileChooser();
        dialog.setTitle("Choose Minecraft Executable");
        if (PlatformUtils.getOs() == PlatformUtils.OS.Windows) {
            dialog.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Executables", "*.exe"),
                    new FileChooser.ExtensionFilter("All files", "*.*")
            );
        } else {
            dialog.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All files", "*")
            );
        }
        if (SingletonUserConfigFile.getConfig().getMcExecutable() != null) {
            dialog.setInitialDirectory(new File(SingletonUserConfigFile.getConfig().getMcExecutable()).getParentFile());
        }
        File file = dialog.showOpenDialog(this.getPrimaryStage());
        if (file != null) {
            SingletonUserConfigFile.getConfig().setMcExecutable(file.getAbsolutePath());
            SingletonUserConfigFile.getInstance().save();
            textFieldMcExecuable.setText(file.getAbsolutePath());
        }
    }

    private void setupBookmarks() {
        for (Bookmark bookmark : SingletonMcLauncherConfigFile.getConfig().getWebBrowser().getBookmarks()) {
            Hyperlink hl = new Hyperlink();
            hl.setText(bookmark.getName());
            hl.setOnAction((event) -> {
                navigate(bookmark.getUrl());
            });
            FontAwesomeIconView faIcon = new FontAwesomeIconView();
            faIcon.setGlyphName("GLOBE");
            faIcon.setSize("1.5em");
            hl.setGraphic(faIcon);
            hboxBookmark.getChildren().add(hl);
        }
    }

    private void launchMinecraft() {
        organizeMods();

        String fileName = SingletonUserConfigFile.getConfig().getMcExecutable();
        if (fileName == null || !(new File(fileName).exists())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ResStrings.get("label.error"));
            alert.setHeaderText(ResStrings.get("msg.minecraft.executable.not.set.header"));
            alert.setContentText(ResStrings.get("msg.minecraft.executable.not.set.content"));
            Optional<ButtonType> result = alert.showAndWait();
            tabPane.getSelectionModel().select(tabSettings);
            return;
        }

        ProcessBuilder pb = new ProcessBuilder(fileName);
        try {
            log.debug("executing " + fileName);
            Process p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void organizeMods() {
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
        listViewMods.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewMods.setCellFactory(CheckBoxListCell.forListView(new Callback<ModRow, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(ModRow item) {
                return item.onProperty();
            }
        }));

        // Set up filtered data
        FilteredList<ModRow> filteredModsByText = new FilteredList<>(modList.getMods(), p -> true);
        FilteredList<ModRow> filteredModsByStatus = new FilteredList<>(filteredModsByText, p -> true);

        // filter with name
        textFieldModsFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredModsByText.setPredicate(modRow -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every modRow with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (modRow.getFile() != null && modRow.getFile().getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name.
                }
                return false; // Does not match.
            });
        });

        toggleGrooupMods.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (toggleGrooupMods.getSelectedToggle() != null) {
                    System.out.println(toggleGrooupMods.getSelectedToggle());
                    if (radioButtonModsAll.isSelected()) {
                        filteredModsByStatus.setPredicate(row -> {
                            return true;
                        });
                    } else if (radioButtonModsEnabled.isSelected()) {
                        filteredModsByStatus.setPredicate(row -> {
                            return row.isChecked();
                        });
                    } else if (radioButtonModsDisabled.isSelected()) {
                        filteredModsByStatus.setPredicate(row -> {
                            return !row.isChecked();
                        });
                    } else {
                        log.error("what?");
                    }
                }
            }
        });

        listViewMods.setItems(filteredModsByStatus);

        modList.getEnabled().addListener(new SetChangeListener<ModRow>() {
            @Override
            public void onChanged(Change<? extends ModRow> c) {
                log.debug("modified: c=" + c);
                labelEanbledModsCount.setText(ResStrings.get("label.enabled.mods") + ": " + modList.getEnabled().size());
            }
        });

        loadModList();
    }

    private void loadModList() {
        modList.clear();

        log.debug("mcDataFolder:" + SingletonUserConfigFile.getConfig().getMcDataFolder());
        File dataFolder = new File(SingletonUserConfigFile.getConfig().getMcDataFolder());

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

        OkHttpClientHelper httpClient = new OkHttpClientHelper(cookieManager);
        try {
            String json = httpClient.downloadText(SingletonMcLauncherConfigFile.getConfig().getForgesUrl());
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ForgeList list = (ForgeList) om.readValue(json, ForgeList.class);

            // Fill file name if not exist
            for (ForgeRow row : list.getForges()) {
                if (row.getFileName() == null || row.getFileName().isEmpty()) {
                    row.setFileName(FilenameUtils.getName(row.getUrl()));
                }
                log.debug(row.toString());
            }

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
            if (row.isViaWeb()) {
                tabPane.getSelectionModel().select(tabWeb);
                navigate(row.getUrl());
            } else {
                downloadFile(row.getUrl(),
                        new File(SingletonUserConfigFile.getConfig().getModsFolder(), row.getFileName()).getAbsolutePath(),
                        () -> {
                            loadModList();
                        }
                );
            }
        }
    }

    private void downloadFile(String url, String fileName, IDownloadEventHandler eventHandler) {
        DownloadFileService service = new DownloadFileService(eventHandler);
        service.setUrl(url);
        service.setFileName(fileName);
        service.setCookieManager(cookieManager);
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
        tableColModsViaWeb.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, Boolean>("viaWeb"));
        tableColModsViaWeb.setCellFactory(CheckBoxTableCell.forTableColumn(tableColModsViaWeb));
        tableColModsFileName.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("fileName"));
        tableColModsUrl.setCellValueFactory(new PropertyValueFactory<DownloadableModRow, String>("url"));

        updateDownloadableMods();
    }

    private void updateDownloadableMods() {
        OkHttpClientHelper httpClient = new OkHttpClientHelper(cookieManager);
        try {
            String json = httpClient.downloadText(textFieldModsUrl.getText());
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            DownloadableModList list = (DownloadableModList) om.readValue(json, DownloadableModList.class);

            // Fill file name if not exist
            for (DownloadableModRow row : list.getMods()) {
                if (row.getFileName() == null || row.getFileName().isEmpty()) {
                    row.setFileName(FilenameUtils.getName(row.getUrl()));
                }
                log.debug(row.toString());
            }

            downloadableMods.clear();
            downloadableMods.addAll(list.getMods());

            // Set up filtered data
            FilteredList<DownloadableModRow> filteredMods = new FilteredList<>(downloadableMods, p -> true);

            textFieldDownloadableModsFilter.textProperty().addListener((observable, oldValue, newValue) -> {
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
        // Setup cookie manager
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);

        // Bind progress bar
        progressBarWeb.progressProperty().bind(webView.getEngine().getLoadWorker().progressProperty());

        // State listener
        webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> value,
                                State oldState, State newState) {
                switch (newState) {
                    case READY:
                        labelWebStatus.setText("Ready.");
                        break;
                    case RUNNING:
                        labelWebStatus.setText("Loading...");
                        break;
                    case CANCELLED:
                        labelWebStatus.setText("Cancelled.");
                        break;
                    case SUCCEEDED:
                        labelWebStatus.setText("Loaded.");
                        break;
                }
            }
        });

        // Capture Downloadable URL
        webView.getEngine().locationProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                log.debug(newValue.toString());
                if (newValue instanceof String) {
                    String url = (String) newValue;
                    ObjectMapper mapper = new ObjectMapper();
                    McLauncherConfig config = SingletonMcLauncherConfigFile.getConfig();
                    boolean acceptUrl = false;
                    String fileName = null;
                    for (DownloadableUrl dUrl : config.getDownloadableUrls()) {
                        if (dUrl.getStartsWith() != null) {
                            if (url.startsWith(dUrl.getStartsWith())) {
                                fileName = FilenameUtils.getName(url);
                                acceptUrl = true;
                                break;
                            }
                        } else if (dUrl.getMatch() != null) {
                            Pattern p = Pattern.compile(dUrl.getMatch());
                            Matcher m = p.matcher(url);
                            if (m.find()) {
                                fileName = m.group(1);
                                acceptUrl = true;
                                break;
                            }
                        } else if (dUrl.getEndsWith() != null) {
                            if (url.endsWith(dUrl.getEndsWith())) {
                                fileName = FilenameUtils.getName(url);
                                acceptUrl = true;
                                break;
                            }
                        }
                    }
                    if (acceptUrl) {
                        final String finalFileName = fileName;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showDownloadConfirmDialog(url, finalFileName);
                            }
                        });
                    }
                }
            }
        });

        navigate(SingletonMcLauncherConfigFile.getConfig().getWebBrowser().getInitialUrl());
    }

    private void navigate() {
        String url = textFieldWebUrl.getText();
        log.debug(url);
//        try {
//            Map<String, List<String>> headers = CookieUtils.createHeader();
//            log.debug("headers: " + CookieHandler.getDefault().get(URI.create(url), headers).toString());
//            CookieHandler.getDefault().put(uri, headers);
        webView.getEngine().load(url);
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
    }

    private void navigate(String url) {
        textFieldWebUrl.setText(url);
        navigate();
    }

    private void showDownloadConfirmDialog(String url, String fileName) {
        // Fix file name
        if (fileName == null) {
            fileName = FilenameUtils.getName(url);
        }

        try {
            fileName = URLDecoder.decode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        fileName = fileName.replaceAll("/", "_");
        fileName = fileName.replaceAll("\\\\", "_");

        log.debug(String.format("download: %s to %s", url, fileName));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(ResStrings.get("label.download.file"));
        alert.setHeaderText(ResStrings.get("mag.download.file.from") + ": " + url);
        alert.setContentText(ResStrings.get("msg.download.file.prompt"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            log.info("Downloading..." + url);
            final String finalFileName = fileName;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    downloadFile(
                            url,
                            new File(SingletonUserConfigFile.getConfig().getModsFolder(), finalFileName).getAbsolutePath(),
                            () -> {
                                System.out.println("download completed from web");
                                loadModList();
                            });
                }
            });
        } else {
            // ... user chose CANCEL or closed the dialog
            log.info("Canceled");
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
            downloadFile(row.getUrl(), new File(MC_LAUNCHER_REPO_FORGES_FOLDER, row.getFileName()).getAbsolutePath(), () -> {
                System.out.println("download forge installer completed.");
            });
        }
    }

    private void installSelectedForge() {
        ObservableList<ForgeRow> selection = tableViewForges.getSelectionModel().getSelectedItems();
        if (selection.size() == 1) {
            ProcessBuilder pb = new ProcessBuilder();
            pb.command("java", "-jar", new File(MC_LAUNCHER_REPO_FORGES_FOLDER, selection.get(0).getFileName()).getAbsolutePath());
            try {
                Process p = pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void donate() {
        application.getHostServices().showDocument("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=U6N4XDK3TB8NJ&lc=KR&item_name=Donate%20to%20McLauncher%20Developer&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted");
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
