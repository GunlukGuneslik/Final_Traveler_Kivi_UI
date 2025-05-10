package com.example.actualtravellerkiviprojectui.api;


import com.example.actualtravellerkiviprojectui.dto.Event.EventCommentDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventRatingDTO;
import com.example.actualtravellerkiviprojectui.dto.PagedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<EventDTO> createEvent(EventCreateDTO event);

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
    Call<EventDTO> updateEvent(@Path("eventId") int eventId, EventCreateDTO update);

    /**
     * Deletes an event.
     */
    @DELETE("api/events/{eventId}")
    Call<Void> deleteEvent(@Path("eventId") int eventId);

    @GET("api/events/owned/{userId}")
    Call<List<EventDTO>> getOwnedEvents(@Path("userId") int userId);

    @GET("api/events/{eventId}/comments")
    public Call<List<EventCommentDTO>> getEventComment(@Path("eventId") Integer eventId) ;

    @POST("api/events/{eventId}/comments")
    public Call<EventCommentDTO> postEventComment(@Path("eventId") Integer eventId, @Body EventCommentDTO comment) ;

    @GET("api/events/{eventId}/ratings")
    public Call<List<EventRatingDTO>> getEventRatings(@Path("eventId") Integer eventId);
    @POST("api/events/{eventId}/ratings")
    public Call<EventRatingDTO> postEventRating(@Path("eventId") Integer eventId, @Body EventRatingDTO rating);

    @GET("api/events/{eventId}/chat")
    public Call<List<EventCommentDTO>> getEventChatComments(@Path("eventId") Integer eventId);
    
    @POST("api/events/{eventId}/chat")
    public Call<EventCommentDTO> postEventChatComment(@Path("eventId") Integer eventId, @Body EventCommentDTO comment);
}
