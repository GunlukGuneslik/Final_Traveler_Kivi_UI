package com.example.actualtravellerkiviprojectui.api.mock;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
    private final Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public String loadMockJson(String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line).append("\n");
            }
            reader.close();
            return jsonBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading mock JSON file: " + fileName);
        }
    }
}