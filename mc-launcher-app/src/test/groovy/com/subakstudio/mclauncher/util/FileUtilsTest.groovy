package com.subakstudio.mclauncher.util

import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Created by yeoupooh on 1/9/16.
 */
class FileUtilsTest {

    @Test
    public void testGetFileNameFromUrlWithoutFileName() throws Exception {
        assertEquals("file.ext", FileUtils.getFileNameFromUrl("", "http://host/file.ext"))
    }

    @Test
    public void testGetFileNameFromUrlWithNullFileNameAndUrl() throws Exception {
        assertEquals("file.ext", FileUtils.getFileNameFromUrl(null, "http://host/file.ext"))
    }

    @Test
    public void testGetFileNameFromUrlWithFilenName() throws Exception {
        assertEquals("file1.ext", FileUtils.getFileNameFromUrl("file1.ext", "http://host/file.ext"))
    }

    @Test
    public void testGetFileNameFromUrlWithEscapedFilenName() throws Exception {
        assertEquals("file 1#.ext", FileUtils.getFileNameFromUrl("", "http://host/file%201%23.ext"))
        assertEquals("MC 1.8 - Player API core 1.4.zip", FileUtils.getFileNameFromUrl("", "http://dl.dropbox.com/u/41082508/Minecraft/Modding/Player%20API/MC%201.8%20-%20Player%20API%20core%201.4.zip"))
    }

    @Test
    public void testGetFileNameFromUrlWithoutFilenNameAndNullUrl() throws Exception {
        assertEquals(null, FileUtils.getFileNameFromUrl("", null));
    }
}
