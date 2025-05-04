package com.example.actualtravellerkiviprojectui.api;

import org.junit.Test;

import java.io.IOException;

public class PostServiceMockTest {

    @Test
    public void fetchFeed() {
        try {
            System.err.println(new PostServiceMock().fetchPosts(1, 10, "").execute().body().get(0).getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createPost() {
    }
}