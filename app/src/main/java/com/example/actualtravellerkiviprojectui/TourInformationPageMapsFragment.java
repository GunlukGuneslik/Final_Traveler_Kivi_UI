package com.example.actualtravellerkiviprojectui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TourInformationPageMapsFragment extends Fragment {

    ArrayList<PlaceModel> placesOnTheTour;
    private GoogleMap mMap;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

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

            if (placesOnTheTour.isEmpty()) {
                LatLng defaultLocation =  new LatLng(39.925533, 32.866287);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
            } else {

                // Adding all available markers
                for (PlaceModel place : placesOnTheTour) {
                    mMap.addMarker(new MarkerOptions().position(place.getLocation()).title(place.getPlaceName()));
                }
            }

            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        placesOnTheTour = new ArrayList<PlaceModel>();
        fillThePlaceArrayList();

        return inflater.inflate(R.layout.fragment_tour_information_page_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    // TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage.
    /**it would be nice if the methods checks weather the data changed or not.
     *if data is not changed it might be cause delay.
     * also prevent the duplication of items
     */
    private void fillThePlaceArrayList() {
        PlaceModel testPlace1 = new PlaceModel("Ankara Kalesi",5,8,"f", "Ankara", "Altındağ", new LatLng(39.925533, 32.866287));
        PlaceModel testPlace2 = new PlaceModel("f",5,8,"f\nk\nh", "Ankara", "Çankaya", new LatLng(41.0082, 28.9784));

        placesOnTheTour.add(testPlace1);
        placesOnTheTour.add(testPlace2);
    }

}