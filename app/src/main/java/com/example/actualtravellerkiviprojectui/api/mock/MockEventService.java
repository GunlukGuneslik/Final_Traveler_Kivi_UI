package com.example.actualtravellerkiviprojectui.api.mock;

import com.example.actualtravellerkiviprojectui.api.EventService;

import retrofit2.mock.BehaviorDelegate;

public class MockEventService implements EventService {
    private final BehaviorDelegate<EventService> delegate;

    public MockEventService(BehaviorDelegate<EventService> delegate) {
        this.delegate = delegate;
    }
}
