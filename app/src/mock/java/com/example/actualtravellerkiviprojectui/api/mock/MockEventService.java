package com.example.actualtravellerkiviprojectui.api.mock;

import androidx.annotation.NonNull;

import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCommentDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventRatingDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventSkeletonDTO;
import com.example.actualtravellerkiviprojectui.dto.PagedModel;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockEventService implements EventService {
    private final BehaviorDelegate<EventService> delegate;

    public MockEventService(BehaviorDelegate<EventService> delegate) {
        this.delegate = delegate;
    }


    @NonNull
    private EventService getEventDelegate() {
        return delegate.returningResponse(Utils.loadObject("mock/events/singleevent.json", new TypeReference<EventDTO>() {
        }));
    }

    /**
     * Creates new event.
     *
     * @param event
     */
    @Override
    public Call<EventDTO> createEvent(EventCreateDTO event) {
        return getEventDelegate().createEvent(event);
    }

    @Override
    public Call<List<EventDTO>> getAllEvents() {
        return delegate.returningResponse(Utils.loadObject("mock/events/all.json", new TypeReference<List<EventDTO>>() {
        })).getAllEvents();
    }

    @Override
    public Call<List<EventDTO>> getPaginatedEvents(int size, int page, String sort) {
        var mockEvents = Utils.loadObject("mock/events/paged.json", new TypeReference<PagedModel<EventDTO>>() {
        });
        return delegate.returningResponse(mockEvents).getPaginatedEvents(size, page, sort);
    }

    /**
     * Returns a list of recommended events
     */
    @Override
    public Call<List<EventDTO>> getRecommendedTours() {
        var mockEvents = Utils.loadObject("mock/events/all.json", new TypeReference<List<EventDTO>>() {
        });
        return delegate.returningResponse(mockEvents).getRecommendedTours();
    }

    @Override
    public Call<EventDTO> getEvent(int eventId) {
        return getEventDelegate().getEvent(eventId);
    }


    @Override
    public Call<EventDTO> updateEvent(int eventId, EventCreateDTO update) {
        return getEventDelegate().updateEvent(eventId, update);
    }

    @Override
    public Call<Void> deleteEvent(int eventId) {
        return delegate.returningResponse(null).deleteEvent(eventId);
    }

    @Override
    public Call<List<EventDTO>> getOwnedEvents(int userId) {
        return delegate.returningResponse(Utils.loadObject("mock/events/ownedevents.json", new TypeReference<List<EventDTO>>() {
        })).getOwnedEvents(userId);

    }

    @Override
    public Call<List<EventCommentDTO>> getEventComment(Integer eventId) {
        return delegate.returningResponse(Utils.loadObject("mock/events/comments.json", new TypeReference<List<EventCommentDTO>>() {
        })).getEventComment(eventId);
    }

    @Override
    public Call<EventCommentDTO> postEventComment(Integer eventId, EventCommentDTO comment) {
        return delegate.returningResponse(Utils.loadObject("mock/events/singlecomment.json", new TypeReference<EventCommentDTO>() {
        })).postEventComment(eventId, comment);
    }


    @Override
    public Call<List<EventRatingDTO>> getEventRatings(Integer eventId) {
        return delegate.returningResponse(Utils.loadObject("mock/events/ratings.json", new TypeReference<List<EventRatingDTO>>() {
        })).getEventRatings(eventId);
    }

    @Override
    public Call<EventRatingDTO> postEventRating(Integer eventId, EventRatingDTO rating) {
        return delegate.returningResponse(Utils.loadObject("mock/events/singlerating.json", new TypeReference<EventRatingDTO>() {
        })).postEventRating(eventId, rating);
    }

    @Override
    public Call<List<EventCommentDTO>> getEventChatComments(Integer eventId) {
        return delegate.returningResponse(Utils.loadObject("mock/events/comments.json", new TypeReference<List<EventCommentDTO>>() {
        })).getEventChatComments(eventId);
    }

    @Override
    public Call<EventCommentDTO> postEventChatComment(Integer eventId, EventCommentDTO comment) {
        return delegate.returningResponse(Utils.loadObject("mock/events/singlecomment.json", new TypeReference<EventCommentDTO>() {
        })).postEventChatComment(eventId, comment);
    }

    /**
     * Create a new event location
     *
     * @param dto The location data
     * @return The created location
     */
    @Override
    public Call<EventLocationDTO> createEventLocation(EventLocationCreateDTO dto) {
        return delegate.returningResponse(Utils.loadObject("mock/events/location.json", new TypeReference<EventLocationDTO>() {
        })).createEventLocation(dto);
    }

    /**
     * Get an event location by ID
     *
     * @param locationId The location ID
     * @return The location details
     */
    @Override
    public Call<EventLocationDTO> getEventLocation(Integer locationId) {
        return delegate.returningResponse(Utils.loadObject("mock/events/location.json", new TypeReference<EventLocationDTO>() {
        })).getEventLocation(locationId);
    }

    /**
     * Get all event locations
     *
     * @return List of all locations
     */
    @Override
    public Call<List<EventLocationDTO>> getAllEventLocations() {
        return delegate.returningResponse(Utils.loadObject("mock/events/location.json", new TypeReference<List<EventLocationDTO>>() {
        })).getAllEventLocations();
    }

    /**
     * Update an existing event location
     *
     * @param locationId The location ID to update
     * @param dto        The updated location data
     * @return The updated location
     */
    @Override
    public Call<EventLocationDTO> updateEventLocation(Integer locationId, EventLocationCreateDTO dto) {
        return delegate.returningResponse(Utils.loadObject("mock/events/location.json", new TypeReference<EventLocationDTO>() {
        })).updateEventLocation(locationId, dto);
    }

    /**
     * Delete an event location
     *
     * @param locationId The location ID to delete
     */
    @Override
    public Call<Void> deleteEventLocation(Integer locationId) {
        return delegate.returningResponse(null).deleteEventLocation(locationId);
    }

    /**
     * Get an event skeleton by event ID
     *
     * @param eventId The event ID
     * @return The event skeleton
     */
    @Override
    public Call<EventSkeletonDTO> getEventSkeleton(Integer eventId) {
        return delegate.returningResponse(Utils.loadObject("mock/events/skeleton.json", new TypeReference<EventSkeletonDTO>() {
        })).getEventSkeleton(eventId);
    }

    @Override
    public Call<ResponseBody> getPhoto(int eventId) {
        byte[] bytes;
        try {
            bytes = Utils.loadMockJson("mock/events/eventimage.png").readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ResponseBody avatarBody = ResponseBody.create(MediaType.parse("image/png"), bytes);
        return delegate.returningResponse(avatarBody).getPhoto(eventId);
    }

    @Override
    public Call<EventDTO> setPhoto(int eventId, MultipartBody.Part image) {
        return getEventDelegate().setPhoto(eventId, image);

    }

}
