package com.subakstudio.mclauncher;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yeoupooh on 16. 3. 25.
 */
public class ControllerTest {

    @Test
    @Ignore
    public void buttonLaunchMinecraftClicked() throws Exception {
        ProcessBuilder pb = new ProcessBuilder("/mnt/ypdisk/apps/minecraft/run-mc.sh");
        Process p = pb.start();
        p.waitFor();
    }

}