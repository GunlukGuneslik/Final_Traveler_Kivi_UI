package com.example.actualtravellerkiviprojectui;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.actualtravellerkiviprojectui.dto.Event.CoordinateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EditTourAddPlaceFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private EditTourActivity activity;
    private ArrayList<EventLocationDTO> locationsList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditTourAddPlaceFragment() {
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
    public static EditTourAddPlaceFragment newInstance(String param1, String param2) {
        EditTourAddPlaceFragment fragment = new EditTourAddPlaceFragment();
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
        activity = (EditTourActivity) getActivity();
        locationsList = activity.locationList;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment_create_tour_add_place_from_map.xml içindeki <fragment> tag’ini yükleyecek
        return inflater.inflate(
                R.layout.fragment_create_tour_add_place_from_map,
                container,
                false
        );
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
                EventLocationDTO place = new EventLocationDTO(coordinate, false,null,"", "", district, cityName);
                locationsList.add(place);

                mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });

        if (locationsList.isEmpty()) {
            LatLng defaultLocation = new LatLng(0.0, 0.0);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 2));
        } else {

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            // Adding all available markers
            for (EventLocationDTO current : locationsList) {
                LatLng location = new LatLng(current.location.longtitude, current.location.latitude);
                mMap.addMarker(new MarkerOptions().position(location).title(current.title));
                builder.include(location);
            }

            LatLngBounds bounds = builder.build();
            int padding = 100;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.moveCamera(cameraUpdate);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
