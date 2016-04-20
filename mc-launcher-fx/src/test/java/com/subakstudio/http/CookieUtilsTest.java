package com.subakstudio.http;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by yeoupooh on 4/20/16.
 */
public class CookieUtilsTest {
    @Test
    public void createHeader() throws Exception {
        Map<String, List<String>> headers = CookieUtils.createHeader();
        assertNotNull(headers);
        assertNotNull(headers.get("Set-Cookie"));
    }
}