package org.example.util;

import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileLoader {
    public static String loadContent(String filepath) {
        try {
            URL url = Resources.getResource(filepath);
            return Resources.toString(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> readLines(String filepath) {
        try {
            URL url = Resources.getResource(filepath);
            return Resources.readLines(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
