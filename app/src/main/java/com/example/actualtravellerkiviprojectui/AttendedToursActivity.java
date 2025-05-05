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
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;
import com.example.actualtravellerkiviprojectui.model.Tour;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Güneş
 */
public class AttendedToursActivity extends AppCompatActivity {

    private ArrayList<Tour> WaitingToursList = new ArrayList<Tour>();
    private ArrayList<Tour> RatedToursList = new ArrayList<Tour>();
    private SearchView tourSearchBar;
    private RecyclerView recyclerViewForAttendedToursWaitingForRating;
    private RecyclerView recyclerViewForAttendedToursRated;
    private Button returnButton;
    private Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page adapterForWaitingTours;
    private Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page adapterForRatedTours;

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
        WaitingToursList = loadTours();

        if (WaitingToursList.isEmpty()) {
            TextView waitingToursText = findViewById(R.id.textViewAttendedToursWaitingForRating);
            waitingToursText.setVisibility(View.GONE);
            recyclerViewForAttendedToursWaitingForRating.setVisibility(View.GONE);
        } else {
            adapterForWaitingTours = new Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page(this, WaitingToursList);
            recyclerViewForAttendedToursWaitingForRating.setAdapter(adapterForWaitingTours);
            recyclerViewForAttendedToursWaitingForRating.setLayoutManager(new LinearLayoutManager(this));
        }

        if (RatedToursList.isEmpty()) {
            TextView ratedToursText = findViewById(R.id.textViewAttendedToursRated);
            ratedToursText.setVisibility(View.GONE);
            recyclerViewForAttendedToursRated.setVisibility(View.GONE);
        } else {
            adapterForRatedTours = new Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page(this, RatedToursList);
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
    private void flitterList(String Text, ArrayList<Tour> givenList, Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page adapter) {
        if (adapter == null || givenList.isEmpty() || givenList == null){
            return;
        }
        ArrayList<Tour> filteredList = new ArrayList<>();

        for (Tour currentTour: givenList) {
            if (currentTour.getTourName().toLowerCase().contains(Text.toLowerCase())) {
                filteredList.add(currentTour);
            } else {
                for (PlaceModel currentPlace: currentTour.getPlaces()) {
                    if (currentPlace.getPlaceName().toLowerCase().contains(Text.toLowerCase())) {
                        filteredList.add(currentTour);
                    }
                }
            }
        }

        adapter.setFilteredList(filteredList);

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No matching tours found.", Toast.LENGTH_SHORT).show();
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
        ArrayList<PlaceModel> testPlaceList2 = new ArrayList<>();
        testPlaceList2.add(testPlace2);

        UserDTO user1 = new UserDTO(R.drawable.avatar, null, null, "Alice");
        UserDTO user2 = new UserDTO(R.drawable.avatar, null, null, "Bob");

        tours.add(new Tour("Location A", new Date(), 4.3, 100,"Türkçe", testPlaceList, user1, R.drawable.ankara, "Aşti otobus terminalinde saat 07.00'da buluşup yolculuğa başlıyoruz..."));
        tours.add(new Tour("Location B", new Date(), 3.2, 150,   "Türkçe", testPlaceList2, user2, R.drawable.ankara,"Aşti otobus terminalinde saat 07.00'da buluşup yolculuğa başlıyoruz..."));
        return tours;
    }
}
