package com.subakstudio.mclauncher;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

/**
 * Created by yeoupooh on 4/20/16.
 */
public class DownloadFileServiceTest extends ApplicationTest {
    // NOTE It hangs on travis. So run this only in local.
    @Ignore
    @Test
    public void setUrl() throws Exception {
        DownloadFileService service = new DownloadFileService(() -> System.out.println("completed"));
        service.setUrl("http://www.google.com/");
        service.start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Pane(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}