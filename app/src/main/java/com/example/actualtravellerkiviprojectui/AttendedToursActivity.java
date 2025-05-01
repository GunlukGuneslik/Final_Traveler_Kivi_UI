package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.Attended_Tour_RecyclerViewAdapter_for_tour_frames;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.example.actualtravellerkiviprojectui.model.Tour;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class AttendedToursActivity extends AppCompatActivity {

    private ArrayList<Tour> WaitingToursList = new ArrayList<Tour>();
    private ArrayList<Tour> RatedToursList = new ArrayList<Tour>();
    private SearchView tourSearchBar;
    private RecyclerView recyclerViewForAttendedToursWaitingForRating;
    private RecyclerView recyclerViewForAttendedToursRated;
    private Button returnButton;
    private Attended_Tour_RecyclerViewAdapter_for_tour_frames adapterForWaitingTours;
    private Attended_Tour_RecyclerViewAdapter_for_tour_frames adapterForRatedTours;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attended_tours);

        recyclerViewForAttendedToursWaitingForRating = findViewById(R.id.recyclerViewAttendedToursWaitingForRating);
        recyclerViewForAttendedToursRated = findViewById(R.id.recyclerViewAttendedToursRated);

        returnButton = findViewById(R.id.AttendedToursPageReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RatedToursList = loadTours();
        //WaitingToursList = loadTours();

        if (WaitingToursList.isEmpty()) {
            TextView waitingToursText = findViewById(R.id.textViewAttendedToursWaitingForRating);
            waitingToursText.setVisibility(View.GONE);
            recyclerViewForAttendedToursWaitingForRating.setVisibility(View.GONE);
        } else {
            adapterForWaitingTours = new Attended_Tour_RecyclerViewAdapter_for_tour_frames(this, WaitingToursList);
            recyclerViewForAttendedToursWaitingForRating.setAdapter(adapterForWaitingTours);
            recyclerViewForAttendedToursWaitingForRating.setLayoutManager(new LinearLayoutManager(this));
        }

        if (RatedToursList.isEmpty()) {
            TextView ratedToursText = findViewById(R.id.textViewAttendedToursRated);
            ratedToursText.setVisibility(View.GONE);
            recyclerViewForAttendedToursRated.setVisibility(View.GONE);
        } else {
            adapterForRatedTours = new Attended_Tour_RecyclerViewAdapter_for_tour_frames(this, RatedToursList);
            recyclerViewForAttendedToursRated.setAdapter(adapterForRatedTours);
            recyclerViewForAttendedToursRated.setLayoutManager(new LinearLayoutManager(this));
        }


        tourSearchBar = findViewById(R.id.searchViewForAttendedTours);
        tourSearchBar.clearFocus();
        tourSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // we dont use this one
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                flitterList(newText, WaitingToursList, adapterForWaitingTours);
                flitterList(newText, RatedToursList, adapterForRatedTours);
                return true;
            }
        });
    }

    /*
    * TODO: this method suppose to filter the tours according to both their name and places in it
     */
    private void flitterList(String Text, ArrayList<Tour> givenList, Attended_Tour_RecyclerViewAdapter_for_tour_frames adapter) {
        Text.toLowerCase();
        ArrayList<Tour> filteredList = new ArrayList<>();

        for (Tour currentTour: givenList) {
            if (currentTour.getTourName().toLowerCase().contains(Text)) {
                filteredList.add(currentTour);
            } else {
                for (PlaceModel currentPlace: currentTour.getPlaces()) {
                    if (currentPlace.getPlaceName().toLowerCase().contains(Text)) {
                        filteredList.add(currentTour);
                    }
                }
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No matching tours found.", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }

    /*
    TODO: tarihe göre sorted bir şekilde sıralamalı
     */
    private ArrayList<Tour> loadTours() {
        ArrayList<Tour> tours = new ArrayList<>();

        PlaceModel testPlace1 = new PlaceModel("Ankara Kalesi",5,8,"f", "Ankara", "Altındağ", new LatLng(39.925533, 32.866287));
        PlaceModel testPlace2 = new PlaceModel("f",5,8,"f\nk\nh", "Ankara", "Çankaya", new LatLng(41.0082, 28.9784));
        ArrayList<PlaceModel> testPlaceList = new ArrayList<>();
        testPlaceList.add(testPlace1);
        testPlaceList.add(testPlace2);

        tours.add(new Tour("Location A", new Date(), "Alice", 150, testPlaceList));
        tours.add(new Tour("Location B", new Date(), "Bob",   200, testPlaceList));
        return tours;
    }
}
