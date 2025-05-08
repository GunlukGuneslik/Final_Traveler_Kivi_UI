package com.example.actualtravellerkiviprojectui.api.mock;

import android.content.Context;

import com.example.actualtravellerkiviprojectui.App;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private Utils() {
    }



    public static <T> T loadObject(String path, TypeReference<T> type) {
        try {
            return objectMapper.readValue(loadMockJson(path), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream loadMockJson(String path) {
        Context context = App.getContext();
        if (context == null) {
            throw new IllegalStateException("MockFileLoader not initialized.");
        }

        try (InputStream is = context.getAssets().open(path)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new ByteArrayInputStream(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading mock JSON file: " + path, e);
        }
    }
}