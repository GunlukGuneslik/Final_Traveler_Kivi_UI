package com.example.actualtravellerkiviprojectui;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Güneş
 */
public class UpcomingToursActivity extends AppCompatActivity {
    private static final EventService eventService = ServiceLocator.getEventService();
    private List<EventDTO> todaysToursList = new ArrayList<>();
    private List<EventDTO> upcomingToursList = new ArrayList<>();
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
        recyclerViewForTodaysTours.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewForTodaysTours.setVisibility(VISIBLE);
        recyclerViewForTodaysTours.setAdapter(adapterForTodaysTours = new Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page(this, todaysToursList));

        recyclerViewForUpcomingTours = findViewById(R.id.recyclerViewUpcomingTours);
        recyclerViewForUpcomingTours.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewForUpcomingTours.setVisibility(VISIBLE);

        recyclerViewForUpcomingTours.setAdapter(adapterForUpcomingTours = new Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page(this, upcomingToursList));


        returnButton = findViewById(R.id.UpcomingToursPageReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadUpcomingToursAsync();

        tourSearchBar = findViewById(R.id.searchViewForUpcomingTours);
        tourSearchBar.clearFocus();
        tourSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText, todaysToursList, adapterForTodaysTours);
                filterList(newText, upcomingToursList, adapterForUpcomingTours);
                return true;
            }
        });
    }

    private void loadUpcomingToursAsync() {
        Integer userId = UserState.getUserId();
        NetworkModule.toCompletableFuture(eventService.getAttendedEvents(UserState.getUserId())).thenAccept(allTours -> runOnUiThread(() -> {
            if (allTours != null) {
                upcomingToursList.clear();
                todaysToursList.clear();
                LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
                todaysToursList.addAll(allTours.stream().filter(event ->
                        event.startDate.isAfter(LocalDateTime.now()) &&
                        event.startDate.truncatedTo(ChronoUnit.DAYS).equals(today)).collect(Collectors.toList()));
                upcomingToursList.addAll(allTours.stream().filter(event -> event.startDate.truncatedTo(ChronoUnit.DAYS).isAfter(today)).collect(Collectors.toList()));
                adapterForTodaysTours.notifyDataSetChanged();
                adapterForUpcomingTours.notifyDataSetChanged();
            }
        }));
    }

//    private void updateRecyclerView(List<EventDTO> tours, RecyclerView recyclerView, Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page adapter) {
//        if (tours.isEmpty()) {
//            recyclerView.setVisibility(View.GONE);
//        } else {
//            if (adapter == null) {
//                adapter = new Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page(this, tours);
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            } else {
//                adapter.updateList(tours);
//            }
//        }
//    }

    private void filterList(String text, List<EventDTO> givenList, Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page adapter) {
        if (adapter == null || givenList == null || givenList.isEmpty()) {
            return;
        }
        List<EventDTO> filteredList = givenList.stream().filter(event ->
                event.name.toLowerCase().contains(text.toLowerCase()) ||
                event.locations.stream().anyMatch(location -> location.title.toLowerCase().contains(text.toLowerCase()))).collect(Collectors.toList());

        adapter.setFilteredList(filteredList);

        if (filteredList.isEmpty()) {
            Toast.makeText(this, R.string.toast_no_matching_tours, Toast.LENGTH_SHORT).show();
        }
    }
}
