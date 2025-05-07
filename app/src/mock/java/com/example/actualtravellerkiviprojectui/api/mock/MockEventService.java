package com.example.actualtravellerkiviprojectui.api.mock;

import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.dto.EventCreateUpdate;
import com.example.actualtravellerkiviprojectui.dto.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.PagedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockEventService implements EventService {
    private final BehaviorDelegate<EventService> delegate;

    public MockEventService(BehaviorDelegate<EventService> delegate) {
        this.delegate = delegate;
    }

    /**
     * Creates new event.
     *
     * @param event
     */
    @Override
    public Call<EventDTO> createEvent(EventDTO event) {
        return null;
    }

    /**
     * Returns the list of all events.
     */
    @Override
    public Call<List<EventDTO>> getAllEvents() {
        return null;
    }

    /**
     * Returns paginated list of events.
     *
     * @param size
     * @param page
     * @param sort
     */
    @Override
    public Call<PagedModel<EventDTO>> getPaginatedEvents(int size, int page, String sort) {
        return null;
    }

    /**
     * Gets an event according to id.
     *
     * @param eventId
     */
    @Override
    public Call<EventDTO> getEvent(int eventId) {
        return null;
    }

    /**
     * Updates an event.
     *
     * @param eventId
     * @param update
     */
    @Override
    public Call<EventDTO> updateEvent(int eventId, EventCreateUpdate update) {
        return null;
    }

    /**
     * Deletes an event.
     *
     * @param eventId
     */
    @Override
    public Call<Void> deleteEvent(int eventId) {
        return null;
    }
}
