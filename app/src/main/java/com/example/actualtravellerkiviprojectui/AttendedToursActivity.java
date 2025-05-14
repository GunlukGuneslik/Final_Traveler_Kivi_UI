package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Güneş
 */
public class AttendedToursActivity extends AppCompatActivity {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();

    private final List<EventDTO> waitingToursList = new ArrayList<EventDTO>();
    private final List<EventDTO> ratedToursList = new ArrayList<EventDTO>();
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
        recyclerViewForAttendedToursWaitingForRating.setAdapter(adapterForWaitingTours = new Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page(this, waitingToursList));
        recyclerViewForAttendedToursRated = findViewById(R.id.recyclerViewAttendedToursRated);
        recyclerViewForAttendedToursRated.setAdapter(adapterForRatedTours = new Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page(this, ratedToursList));

        returnButton = findViewById(R.id.AttendedToursPageReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadAttendedToursAsync();

        tourSearchBar = findViewById(R.id.searchViewForAttendedTours);
        tourSearchBar.clearFocus();
        tourSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText, waitingToursList, adapterForWaitingTours);
                filterList(newText, ratedToursList, adapterForRatedTours);
                return true;
            }
        });
    }

    private void loadAttendedToursAsync() {
        Integer userId = UserState.getUserId();
        NetworkModule.toCompletableFuture(eventService.getAttendedEvents(userId)).thenAccept(eventDTOS -> {
            if (eventDTOS == null) { return; }
            ratedToursList.clear();
            waitingToursList.clear();
            eventDTOS.stream().filter(eventDTO -> eventDTO.startDate.plusMinutes(eventDTO.duration).isBefore(LocalDateTime.now())).forEach(eventDTO -> {
                NetworkModule.toCompletableFuture(eventService.hasUserRated(UserState.getUserId(), eventDTO.id)).thenAccept(isRated -> (
                        isRated ? ratedToursList : waitingToursList).add(eventDTO));
            });
        });
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
