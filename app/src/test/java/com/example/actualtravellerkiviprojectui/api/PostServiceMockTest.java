package com.example.actualtravellerkiviprojectui.api;

import static org.junit.Assert.*;

import org.junit.Test;

public class PostServiceMockTest {

    @Test
    public void fetchPosts() {
        System.err.println(new PostServiceMock().fetchPosts(1,10,"").get(0).getBody());
    }

    @Test
    public void createPost() {
    }
}