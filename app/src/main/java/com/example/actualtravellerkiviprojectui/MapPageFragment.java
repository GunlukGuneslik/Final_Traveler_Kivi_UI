package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.actualtravellerkiviprojectui.adapter.Place_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * @author Güneş
 *
 * A simple {@link Fragment} subclass.
 * Use the {@link MapPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapPageFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private SearchView searchViewForMap;
    private GoogleMap mMap;
    private FrameLayout fragmentForMap;
    private RecyclerView recyclerView;
    private Place_RecyclerViewAdapter mapAdapter;
    private ArrayList<PlaceModel> placeModels = new ArrayList<PlaceModel>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapPageFragment() {
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
    public static MapPageFragment newInstance(String param1, String param2) {
        MapPageFragment fragment = new MapPageFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_page, container, false);
        fragmentForMap = view.findViewById(R.id.fragmentForMap);

        fillThePlaceArrayList();

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragmentForMap, mapFragment)
                .commit();
        getChildFragmentManager().executePendingTransactions();
        mapFragment.getMapAsync(this);

        searchViewForMap = view.findViewById(R.id.searchViewForMapPage);
        searchViewForMap.clearFocus();
        searchViewForMap.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // we dont use this one
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                flitterList(newText);
                return true;
            }
        });


        recyclerView = view.findViewById(R.id.mapPageRecyclerView);
        mapAdapter = new Place_RecyclerViewAdapter(getContext(), placeModels, this);
        recyclerView.setAdapter(mapAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    // TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage.
    private void flitterList(String Text) {
        ArrayList<PlaceModel> fliteredList = new ArrayList<>();

        for (PlaceModel current: placeModels) {
            if (current.getPlaceName().toLowerCase().contains(Text.toLowerCase())) {
                fliteredList.add(current);
            }
        }

        if (fliteredList.isEmpty()) {
            Toast.makeText(getContext(), "There is no place is found", Toast.LENGTH_SHORT).show();
        } else {
            mapAdapter.setFlitiredList(fliteredList);
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

        placeModels.add(testPlace1);
        placeModels.add(testPlace2);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (placeModels.isEmpty()) {
            LatLng defaultLocation =  new LatLng(39.925533, 32.866287);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
        } else {

            // Adding all available markers
            for (PlaceModel place : placeModels) {
                mMap.addMarker(new MarkerOptions().position(place.getLocation()).title(place.getPlaceName()));
            }
        }

        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        LatLng clickedLocation = marker.getPosition();

        for (PlaceModel place : placeModels) {
            if (place.getLocation().equals(clickedLocation)) {
                // when we click on a place on map it directly shows the info about this place
                flitterList(place.getPlaceName());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clickedLocation, 10));
                return true;
            }
        }
        return false;
    }

    public void showPlaceOnMap(LatLng latLng, String placeName) {
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15)); // Zoom in a bit
        }
    }

    // Overload the method to accept a PlaceModel directly
    public void showPlaceOnMap(PlaceModel place) {
        if (mMap != null && place != null) {
            showPlaceOnMap(place.getLocation(), place.getPlaceName());
        }
    }
}