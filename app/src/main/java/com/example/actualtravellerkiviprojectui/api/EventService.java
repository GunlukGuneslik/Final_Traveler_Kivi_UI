package com.example.actualtravellerkiviprojectui.api;


import com.example.actualtravellerkiviprojectui.dto.EventCreateUpdate;
import com.example.actualtravellerkiviprojectui.dto.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.PagedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventService {


    /**
     * Creates new event.
     */
    @POST("")
    Call<EventDTO> createEvent(EventDTO event);

    /**
     * Returns the list of all events.
     */
    @GET("api/events/all")
    Call<List<EventDTO>> getAllEvents();

    /**
     * Returns paginated list of events.
     */
    @GET("")
    Call<PagedModel<EventDTO>> getPaginatedEvents(@Query("size") int size, @Query("page") int page, @Query("sort") String sort);

    /**
     * Gets an event according to id.
     */
    @GET("api/events/{eventId}")
    Call<EventDTO> getEvent(@Path("eventId") int eventId);

    /**
     * Updates an event.
     */
    @PUT("api/events/{eventId}")
    Call<EventDTO> updateEvent(@Path("eventId") int eventId, EventCreateUpdate update);

    /**
     * Deletes an event.
     */
    @DELETE("api/events/{eventId}")
    Call<Void> deleteEvent(@Path("eventId") int eventId);
}
