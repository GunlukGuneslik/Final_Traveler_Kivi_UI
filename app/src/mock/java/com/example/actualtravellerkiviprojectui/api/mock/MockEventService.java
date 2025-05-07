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

    @Override
    public Call<EventDTO> createEvent(EventDTO event) {
        // Mock a response
        return delegate.returningResponse(utils.loadMockJson("mock/events/event_1.json")).createEvent(event);
    }

    @Override
    public Call<List<EventDTO>> getAllEvents() {
        return delegate.returningResponse(utils.loadMockJson("mock/events/event_list.json")).getAllEvents();
    }

    @Override
    public Call<PagedModel<EventDTO>> getPaginatedEvents(int size, int page, String sort) {
        return delegate.returningResponse(utils.loadMockJson("mock/events/event_list.json")).getPaginatedEvents(size, page, sort);
    }

    @Override
    public Call<EventDTO> getEvent(int eventId) {
        return delegate.returningResponse(utils.loadMockJson("mock/events/event_" + eventId + ".json")).getEvent(eventId);
    }

    @Override
    public Call<EventDTO> updateEvent(int eventId, EventCreateUpdate update) {
        return delegate.returningResponse(utils.loadMockJson("mock/events/event_" + eventId + ".json")).updateEvent(eventId, update);
    }

    @Override
    public Call<Void> deleteEvent(int eventId) {
        return delegate.returningResponse("").deleteEvent(eventId);
    }

    public String loadMockJson(String fileName) {
        return utils.loadMockJson(fileName);
    }
}
