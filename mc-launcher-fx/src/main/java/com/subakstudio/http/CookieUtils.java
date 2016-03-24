package com.subakstudio.http;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yeoupooh on 16. 3. 25.
 */
public class CookieUtils {
    public static Map<String, List<String>> createHeader() {
        Map<String, List<String>> headers = new LinkedHashMap<>();
        headers.put("Set-Cookie", Arrays.asList("name=value"));
        return headers;
    }
}
