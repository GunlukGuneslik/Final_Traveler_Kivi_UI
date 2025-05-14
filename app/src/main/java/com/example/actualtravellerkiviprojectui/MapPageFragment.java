package com.example.actualtravellerkiviprojectui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.Place_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.dto.Event.CoordinateDTO;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private List<EventLocationDTO> places = new ArrayList<>();



    public MapPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapPageFragment newInstance() {
        MapPageFragment fragment = new MapPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static LatLng fromCoordinateDTO(CoordinateDTO coordinateDTO) {
        return new LatLng(coordinateDTO.latitude, coordinateDTO.longtitude);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_page, container, false);
        fragmentForMap = view.findViewById(R.id.fragmentForMap);

        fillPlaceArrayList();

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragmentForMap, mapFragment)
                .commit();
        getChildFragmentManager().executePendingTransactions();
        mapFragment.getMapAsync(this);

        searchViewForMap = view.findViewById(R.id.searchViewForMapPage);
        int id = searchViewForMap.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = searchViewForMap.findViewById(id);
        searchEditText.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown));
        searchEditText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.brown));

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
        mapAdapter = new Place_RecyclerViewAdapter(getContext(), places, this);
        recyclerView.setAdapter(mapAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    // TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage.

    // TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage.
    private void flitterList(String Text) {
        ArrayList<EventLocationDTO> fliteredList = new ArrayList<>();

        for (EventLocationDTO current : places) {
            if (current.title.toLowerCase().contains(Text.toLowerCase())) {
                fliteredList.add(current);
            }
        }

        if (fliteredList.isEmpty()) {
            Toast.makeText(getContext(), R.string.Noplaceisfound, Toast.LENGTH_SHORT).show();
        } else {
            mapAdapter.setFilteredList(fliteredList);
        }
    }

    /**it would be nice if the methods checks weather the data changed or not.
     *if data is not changed it might be cause delay.
     * also prevent the duplication of items
     */
    private void fillPlaceArrayList() {
        ServiceLocator.getEventService().getFeaturedLocations().enqueue(new Callback<List<EventLocationDTO>>() {
            @Override
            public void onResponse(Call<List<EventLocationDTO>> call, Response<List<EventLocationDTO>> response) {
                places.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<EventLocationDTO>> call, Throwable throwable) {
                Toast.makeText(getContext(), "Couldn't fetch places", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (places.isEmpty()) {
            LatLng defaultLocation =  new LatLng(39.925533, 32.866287);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
        } else {

            // Adding all available markers
            for (EventLocationDTO place : places) {
                mMap.addMarker(new MarkerOptions().position(fromCoordinateDTO(place.location)).title(place.title));
            }
        }

        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public void showPlaceOnMap(LatLng latLng, String placeName) {
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15)); // Zoom in a bit
        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        LatLng clickedLocation = marker.getPosition();

        for (EventLocationDTO place : places) {
            if (fromCoordinateDTO(place.location).equals(clickedLocation)) {
                // when we click on a place on map it directly shows the info about this place
                flitterList(place.title);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clickedLocation, 10));
                return true;
            }
        }
        return false;
    }

    // Overload the method to accept a PlaceModel directly
    public void showPlaceOnMap(EventLocationDTO place) {
        if (mMap != null && place != null) {
            showPlaceOnMap(fromCoordinateDTO(place.location), place.title);
        }
    }
}