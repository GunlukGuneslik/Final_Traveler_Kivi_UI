package com.example.actualtravellerkiviprojectui.api;


import com.example.actualtravellerkiviprojectui.dto.Event.EventCommentCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCommentDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventRatingCreateDTO;
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
    @POST("events/")
    Call<EventDTO> createEvent(@Body EventCreateDTO event);

    /**
     * Returns the list of all events.
     */
    @GET("events/all")
    Call<List<EventDTO>> getAllEvents();

    /**
     * Returns paginated list of events.
     */
    @GET("events")
    Call<PagedModel<EventDTO>> getPaginatedEvents(@Query("size") int size, @Query("page") int page, @Query("sort") String sort);

    @GET("events/by-location")
    public Call<List<EventDTO>> getEventsByLocation(@Query("locationName") String locationName);

    @GET("events/by-owner")
    public Call<List<EventDTO>> getEventsByOwner(@Query("ownerName") String ownerName);


    /**
     * Returns a list of recommended events
     */
    @GET("events/all")
    Call<List<EventDTO>> getRecommendedTours();

    /**
     * Gets an event according to id.
     */
    @GET("events/{eventId}")
    Call<EventDTO> getEvent(@Path("eventId") int eventId);

    /**
     * Updates an event.
     */
    @PUT("events/{eventId}")
    Call<EventDTO> updateEvent(@Path("eventId") int eventId, EventCreateDTO update);

    /**
     * Deletes an event.
     */
    @DELETE("events/{eventId}")
    Call<Void> deleteEvent(@Path("eventId") int eventId);

    @GET("events/owned/{userId}")
    Call<List<EventDTO>> getOwnedEvents(@Path("userId") int userId);

    @GET("events/{eventId}/comments")
    public Call<List<EventCommentDTO>> getEventComment(@Path("eventId") Integer eventId);

    @POST("events/{eventId}/comments")
    public Call<EventCommentDTO> postEventComment(@Path("eventId") Integer eventId, @Body EventCommentCreateDTO comment);

    @GET("events/{eventId}/ratings")
    public Call<List<EventRatingDTO>> getEventRatings(@Path("eventId") Integer eventId);

    @POST("events/{eventId}/ratings")
    public Call<EventRatingDTO> postEventRating(@Path("eventId") Integer eventId, @Body EventRatingCreateDTO rating);

    @GET("events/{eventId}/chat")
    public Call<List<EventCommentDTO>> getEventChatComments(@Path("eventId") Integer eventId);

    @POST("events/{eventId}/chat")
    public Call<EventCommentDTO> postEventChatComment(@Path("eventId") Integer eventId, @Body EventCommentCreateDTO comment);

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

    @GET("events/skeletons/{skeletonId")
    Call<EventSkeletonDTO> getEventSkeletonById(@Path("skeletonId") Integer skeletonId);

    @GET("events/{eventId}/photo")
    @Streaming
    Call<ResponseBody> getPhoto(@Path("eventId") int eventId);

    @POST("events/{eventId}/photo")
    @Multipart
    Call<EventDTO> setPhoto(@Path("eventId") int eventId, @Part("image") MultipartBody.Part image);


    @GET("locations/{locationId}/photo")
    @Streaming
    Call<ResponseBody> getLocationPhoto(@Path("locationId") int locationId);

    @POST("locations/{locationId}/photo")
    @Multipart
    Call<EventDTO> setLocationPhoto(@Path("locationId") int locationId, @Part("image") MultipartBody.Part image);

    @GET("locations/featured")
    Call<List<EventLocationDTO>> getFeaturedLocations();

    @GET("events/attended/{userId}")
    Call<List<EventDTO>> getAttendedEvents(@Path("userId") Integer userId);

    @PUT("events/{eventId}/register/{userId}")
    Call<EventDTO> registerEvent(@Path("eventId") Integer eventId, @Path("userId") Integer userId);

    @PUT("events/{eventId}/unregister/{userId}")
    Call<EventDTO> unregisterEvent(@Path("eventId") Integer eventId, @Path("userId") Integer userId);
    
}
