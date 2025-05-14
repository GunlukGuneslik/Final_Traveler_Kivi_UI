package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.Adapter_For_Catolog;
import com.example.actualtravellerkiviprojectui.adapter.Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.model.Tour;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class LaunchTourCatalogeActivity extends AppCompatActivity {
    private static final UserService userService = ServiceLocator.getUserService();
    private ArrayList<EventDTO> previouslyCreatedToursByGuideUser;
    private Button returnButton;
    private RecyclerView recyclerView;
    private Adapter_For_Catolog adapter;

    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_tour_catalog);

        //previouslyCreatedToursByGuideUser = loadCreatedOldTours();

        returnButton = findViewById(R.id.CatalougeToursPageReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerViewLaunchTourCatalog);
        adapter = new Adapter_For_Catolog(this, previouslyCreatedToursByGuideUser);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchViewForAttendedTours);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = searchView.findViewById(id);
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.brown));
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.brown));
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // we dont use this one
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                flitterList(newText, previouslyCreatedToursByGuideUser, adapter);
                return false;
            }
        });

    }

    // TODO complete this
//    private ArrayList<EventDTO> loadCreatedOldTours() {
//        ArrayList<EventDTO> tours = new ArrayList<>();
//
//        PlaceModel testPlace1 = new EventLocationDTO("Ankara Kalesi",5,8,"f", "Ankara", "Altındağ", new LatLng(39.925533, 32.866287));
//        PlaceModel testPlace2 = new EventLocationDTO("f",5,8,"f\nk\nh", "Ankara", "Çankaya", new LatLng(41.0082, 28.9784));
//        ArrayList<EventLocationDTO> testPlaceList = new ArrayList<>();
//        testPlaceList.add(testPlace1);
//        testPlaceList.add(testPlace2);
//        ArrayList<EventLocationDTO> testPlaceList2 = new ArrayList<>();
//        testPlaceList2.add(testPlace2);
//
//        UserDTO user1 = null;
//        try {
//            user1 = userService.getUser(1).execute().body();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        UserDTO user2 = null;
//        try {
//            user2 = userService.getUser(2).execute().body();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        ArrayList<String> comments = new ArrayList<>();
//        comments.add("It was nice.");
//        comments.add(("Ankara'yı çok sevdim."));
//        comments.add("I didn't like it.");
//        tours.add(new Tour("Location A", new Date(), 4.3, 100,"Türkçe", testPlaceList, user1, R.drawable.ankara, "Aşti otobus terminalinde saat 07.00'da buluşup yolculuğa başlıyoruz...",comments));
//        tours.add(new Tour("Location B", new Date(), 3.2, 150,   "Türkçe", testPlaceList2, user2, R.drawable.ankara,"Aşti otobus terminalinde saat 07.00'da buluşup yolculuğa başlıyoruz...",comments));
//        return tours;
//    }

    /*
     * TODO: this method suppose to filter the tours according to both their name and places in it
     */
    private void flitterList(String Text, ArrayList<EventDTO> givenList, Adapter_For_Catolog adapter) {
        if (adapter == null || givenList.isEmpty() || givenList == null) {
            return;
        }
        ArrayList<EventDTO> filteredList = new ArrayList<>();

        for (EventDTO currentTour : givenList) {
            if (currentTour.name.toLowerCase().contains(Text.toLowerCase())) {
                filteredList.add(currentTour);
            } else {
                for (EventLocationDTO currentPlace : currentTour.locations) {
                    if (currentPlace.title.toLowerCase().contains(Text.toLowerCase())) {
                        filteredList.add(currentTour);
                    }
                }
            }
        }
    }
}
