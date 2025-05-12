package com.example.actualtravellerkiviprojectui;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.actualtravellerkiviprojectui.dto.Event.CoordinateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class CreateTourAddPlaceFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap mMap;
    private LaunchTourCreateActivity activity;
    private ArrayList<EventLocationCreateDTO> placeModels;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateTourAddPlaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateTourAddPlaceFragment newInstance(String param1, String param2) {
        CreateTourAddPlaceFragment fragment = new CreateTourAddPlaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        activity = (LaunchTourCreateActivity) getActivity();
        placeModels = activity.placeModels;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.CreateTourMapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                String cityName = null;
                String district = null;
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        Address address = addresses.get(0);
                        cityName = address.getLocality();
                        district = address.getSubAdminArea();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CoordinateDTO coordinate = new CoordinateDTO(latLng.latitude, latLng.longitude);
                EventLocationCreateDTO place = new EventLocationCreateDTO(coordinate, false,null,"", "", district, cityName);
                placeModels.add(place);

                mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });

        if (placeModels.isEmpty()) {
            LatLng defaultLocation =  new LatLng(39.925533, 32.866287);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
        } else {

            // Adding all available markers
            for (EventLocationCreateDTO current : placeModels) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(current.location.latitude, current.location.longtitude)).title(current.title));
            }
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
