package com.subakstudio.io

import org.apache.commons.io.FilenameUtils
import org.junit.Assert
import org.junit.Test

/**
 * Created by yeoupooh on 1/9/16.
 */
class FilenameUtilsTest {

    @Test
    def void test() throws Exception {
        String url = "http://www.example.com/some/path/to/a/file.xml";

        String baseName = FilenameUtils.getBaseName(url);
        String extension = FilenameUtils.getExtension(url);

        System.out.println("Basename : " + baseName);
        System.out.println("extension : " + extension);

        Assert.assertEquals("file", baseName)
        Assert.assertEquals("xml", extension)
        Assert.assertEquals("file.xml", FilenameUtils.getName(url))
    }
}
