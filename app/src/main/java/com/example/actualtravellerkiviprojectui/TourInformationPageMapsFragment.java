package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class TourInformationPageMapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String ARG_TOUR_ID = "tour_id";
    private final List<EventLocationDTO> locationsOfTour = new ArrayList<>();
    private int tourId;

    // Factory method
    public static TourInformationPageMapsFragment newInstance(int tourId) {
        TourInformationPageMapsFragment fragment = new TourInformationPageMapsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TOUR_ID, tourId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tourId = getArguments().getInt(ARG_TOUR_ID);
        }
    }

    private GoogleMap mMap;

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to
     * install it inside the SupportMapFragment. This method will only be triggered once the
     * user has installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (locationsOfTour.isEmpty()) {
            LatLng defaultLocation =  new LatLng(39.925533, 32.866287);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
        } else {

            // Adding all available markers
            for (EventLocationDTO place : locationsOfTour) {
                mMap.addMarker(new MarkerOptions().position(place.toLatLng()).title(place.title));
            }
        }

        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        LatLng clickedLocation = marker.getPosition();
        for (EventLocationDTO location : locationsOfTour) {
            if (location.toLatLng().equals(clickedLocation)) {
                Toast.makeText(getContext(), location.title, Toast.LENGTH_SHORT).show();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clickedLocation, 10));
                return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fillThePlaceArrayList();

        return inflater.inflate(R.layout.fragment_tour_information_page_maps, container, false);
    }

    private void fillThePlaceArrayList() {
        // Clear existing data to prevent duplication
        locationsOfTour.clear();

        // Use the EventService to fetch location data for this tour
        NetworkModule.toCompletableFuture(ServiceLocator.getEventService().getEvent(tourId))
                .thenAccept(event -> {
                    // Populate your places list based on the event data
                    // This is just an example - adjust according to your actual data structure
                    if (event != null && event.locations != null) {
                        locationsOfTour.addAll(event.locations);

                        // Update the map if it's already available
                        if (mMap != null) {
                            getActivity().runOnUiThread(() -> onMapReady(mMap));
                        }
                    }
                })
                .exceptionally(e -> {
                    // Handle error
                    return null;
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    // TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage.
    /**it would be nice if the methods checks weather the data changed or not.
     *if data is not changed it might be cause delay.
     * also prevent the duplication of items
     */


}