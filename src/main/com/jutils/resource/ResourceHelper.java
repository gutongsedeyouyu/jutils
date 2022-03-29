package com.jutils.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceHelper {
    public static String readResource(String resourceName) throws IOException {
        return readResource(resourceName, false);
    }

    public static String readResource(String resourceName, boolean trimLineBreaks) throws IOException {
        try (InputStream inputStream = ResourceHelper.class.getClassLoader().getResourceAsStream(resourceName);
             InputStreamReader reader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(trimLineBreaks ? line.replaceAll("\r", "").replaceAll("\n", "") : line);
            }
            return builder.toString();
        }
    }
}
