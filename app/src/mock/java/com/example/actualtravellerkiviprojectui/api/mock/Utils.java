package com.example.actualtravellerkiviprojectui.api.mock;

import android.annotation.SuppressLint;
import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;

public class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private Utils() {
    }

    public static void init(Context context) {
        if (context == null) {
            throw new InvalidParameterException("context can't be null");
        }
        Utils.context = context;
    }

    public static <T> T loadObject(String path, TypeReference<T> type) {
        try {
            return objectMapper.readValue(loadMockJson(path), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream loadMockJson(String path) {
        if (context == null) {
            throw new IllegalStateException("MockFileLoader not initialized. Call MockFileLoader.init(context) in your Application class.");
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