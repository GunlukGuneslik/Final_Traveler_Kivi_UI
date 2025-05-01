package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.Attended_Tour_RecyclerViewAdapter_for_tour_frames;
import com.example.actualtravellerkiviprojectui.model.Tour;

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

        WaitingToursList = loadTours();
        RatedToursList = loadTours();

        returnButton = findViewById(R.id.AttendedToursPageReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapterForWaitingTours = new Attended_Tour_RecyclerViewAdapter_for_tour_frames(this, WaitingToursList);
        recyclerViewForAttendedToursWaitingForRating.setAdapter(adapterForWaitingTours);
        recyclerViewForAttendedToursWaitingForRating.setLayoutManager(new LinearLayoutManager(this));

        adapterForRatedTours = new Attended_Tour_RecyclerViewAdapter_for_tour_frames(this, RatedToursList);
        recyclerViewForAttendedToursRated.setAdapter(adapterForRatedTours);
        recyclerViewForAttendedToursRated.setLayoutManager(new LinearLayoutManager(this));

        tourSearchBar = findViewById(R.id.searchViewForAttendedTours);
        tourSearchBar.clearFocus();
        tourSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // we dont use this one
            @Override
            public boolean onQueryTextSubmit(String query){
                tourSearchBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                flitterList(newText, WaitingToursList, adapterForWaitingTours);
                flitterList(newText, RatedToursList, adapterForRatedTours);
                return true;
            }
        });
    }

    private void flitterList(String Text, ArrayList<Tour> givenList, Attended_Tour_RecyclerViewAdapter_for_tour_frames adapter) {
        ArrayList<Tour> filteredList = new ArrayList<>();

        for (Tour current: givenList) {
            if (current.getDestination().toLowerCase().contains(Text.toLowerCase())) {
                filteredList.add(current);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No matching tours found.", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFlirtedList(filteredList);
        }
    }

    private ArrayList<Tour> loadTours() {
        ArrayList<Tour> tours = new ArrayList<>();
        tours.add(new Tour("Location A", new Date(), "Alice", 150));
        tours.add(new Tour("Location B", new Date(), "Bob",   200));
        return tours;
    }
}
