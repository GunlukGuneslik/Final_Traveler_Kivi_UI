package com.example.actualtravellerkiviprojectui.api;


import com.example.actualtravellerkiviprojectui.dto.Event.EventCommentDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventRatingDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventSkeletonDTO;
import com.example.actualtravellerkiviprojectui.dto.PagedModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

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
    @GET("api/events")
    Call<PagedModel<EventDTO>> getPaginatedEvents(@Query("size") int size, @Query("page") int page, @Query("sort") String sort);

    /**
     * Returns a list of recommended events
     */
    @GET("api/events/recommended")
    Call<PagedModel<EventDTO>> getRecommendedEvents(@Query("size") int size, @Query("page") int page, @Query("sort") String sort);

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

    /**
     * Create a new event location
     *
     * @param dto The location data
     * @return The created location
     */
    @POST("events/locations")
    Call<EventLocationDTO> createEventLocation(@Body EventLocationCreateDTO dto);

    /**
     * Get an event location by ID
     *
     * @param locationId The location ID
     * @return The location details
     */
    @GET("events/locations/{locationId}")
    Call<EventLocationDTO> getEventLocation(@Path("locationId") Integer locationId);

    /**
     * Get all event locations
     *
     * @return List of all locations
     */
    @GET("events/locations")
    Call<List<EventLocationDTO>> getAllEventLocations();

    @GET("events/locations")
    Call<List<EventLocationDTO>> getRecommendedTours();

    /**
     * Update an existing event location
     *
     * @param locationId The location ID to update
     * @param dto        The updated location data
     * @return The updated location
     */
    @PUT("events/locations/{locationId}")
    Call<EventLocationDTO> updateEventLocation(
            @Path("locationId") Integer locationId,
            @Body EventLocationCreateDTO dto);

    /**
     * Delete an event location
     *
     * @param locationId The location ID to delete
     */
    @DELETE("events/locations/{locationId}")
    Call<Void> deleteEventLocation(@Path("locationId") Integer locationId);

    /**
     * Get an event skeleton by event ID
     *
     * @param eventId The event ID
     * @return The event skeleton
     */
    @GET("events/{eventId}/skeleton")
    Call<EventSkeletonDTO> getEventSkeleton(@Path("eventId") Integer eventId);

    @GET("events/{eventId}/photo")
    @Streaming
    Call<ResponseBody> getPhoto(@Path("eventId") int eventId);

    @POST("events/{eventId}/photo")
    @Multipart
    Call<EventDTO> setPhoto(@Path("eventId") int eventId, @Part("file") MultipartBody.Part image);

}
