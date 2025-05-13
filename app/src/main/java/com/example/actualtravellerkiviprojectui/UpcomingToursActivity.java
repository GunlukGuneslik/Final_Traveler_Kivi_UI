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

import com.example.actualtravellerkiviprojectui.adapter.Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.example.actualtravellerkiviprojectui.model.Tour;

import java.util.ArrayList;

/**
 * @author Güneş
 */
public class UpcomingToursActivity extends AppCompatActivity {

    private ArrayList<EventDTO> TodaysToursList = new ArrayList<>();
    private ArrayList<EventDTO> UpcomingToursList = new ArrayList<>();
    private SearchView tourSearchBar;
    private RecyclerView recyclerViewForTodaysTours;
    private RecyclerView recyclerViewForUpcomingTours;
    private Button returnButton;
    private Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page adapterForTodaysTours;
    private Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page adapterForUpcomingTours;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_tours);

        recyclerViewForTodaysTours = findViewById(R.id.recyclerViewUpcomingToursToday);
        recyclerViewForUpcomingTours = findViewById(R.id.recyclerViewUpcomingTours);

        returnButton = findViewById(R.id.UpcomingToursPageReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        UpcomingToursList = loadTours();
        TodaysToursList = loadTours();

        if (TodaysToursList.isEmpty()) {
            TextView waitingToursText = findViewById(R.id.textViewUpcomingToursToday);
            waitingToursText.setVisibility(View.GONE);
            recyclerViewForTodaysTours.setVisibility(View.GONE);
        } else {
            adapterForTodaysTours = new Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page(this, TodaysToursList);
            recyclerViewForTodaysTours.setAdapter(adapterForTodaysTours);
            recyclerViewForTodaysTours.setLayoutManager(new LinearLayoutManager(this));
        }

        if (UpcomingToursList.isEmpty()) {
            TextView ratedToursText = findViewById(R.id.textViewUpcomingTours);
            ratedToursText.setVisibility(View.GONE);
            recyclerViewForUpcomingTours.setVisibility(View.GONE);
        } else {
            adapterForUpcomingTours = new Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page(this, UpcomingToursList);
            recyclerViewForUpcomingTours.setAdapter(adapterForUpcomingTours);
            recyclerViewForUpcomingTours.setLayoutManager(new LinearLayoutManager(this));
        }


        tourSearchBar = findViewById(R.id.searchViewForUpcomingTours);
        tourSearchBar.clearFocus();
        tourSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // we dont use this one
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                flitterList(newText, TodaysToursList, adapterForTodaysTours);
                flitterList(newText, UpcomingToursList, adapterForUpcomingTours);
                return true;
            }
        });
    }

    /*
     * TODO: this method suppose to filter the tours according to both their name and places in it
     */
    private void flitterList(String Text, ArrayList<EventDTO> givenList, Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page adapter) {
        if (adapter == null || givenList.isEmpty() || givenList == null){
            return;
        }
        ArrayList<EventDTO> filteredList = new ArrayList<>();

        for (EventDTO currentTour: givenList) {
            if (currentTour.name.toLowerCase().contains(Text.toLowerCase())) {
                filteredList.add(currentTour);
            } else {
                for (EventLocationDTO currentPlace: currentTour.locations) {
                    if (currentPlace.title.toLowerCase().contains(Text.toLowerCase())) {
                        filteredList.add(currentTour);
                    }
                }
            }
        }

        adapter.setFilteredList(filteredList);

        if (filteredList.isEmpty()) {
            Toast.makeText(this, R.string.toast_no_matching_tours, Toast.LENGTH_SHORT).show();
        }
    }

    /*
    TODO: tarihe göre sorted bir şekilde sıralamalı
    TODO: bu method tamamen test amaçlı normalde o gün olan turları ve dğer turları ayrı ayrı yükleyen methodlar lazım
     */
    private ArrayList<EventDTO> loadTours() {
        ArrayList<EventDTO> tours = new ArrayList<>();
        return tours;
    }
}
