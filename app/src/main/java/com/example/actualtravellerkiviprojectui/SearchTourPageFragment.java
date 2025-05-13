package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.TourAdapter;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTourPageFragment extends Fragment {

    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();

    private final List<EventDTO> allTours = new ArrayList<>();
    private TextView recommendedTitle;
    private final List<EventDTO> filteredTours = new ArrayList<>();
    private final List<EventDTO> recommendedTours = new ArrayList<>();
    private RecyclerView rvFiltered, rvRecommended;
    private TourAdapter filteredAdapter;
    private TourAdapter recommendedAdapter;


    private EditText etSearch;
    private Spinner spinnerFilter, spinnerSort;
    private Button btnSearch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_tour_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bs) {
        super.onViewCreated(view, bs);
        initializeRecommendedTours();   // Önerilen tur listesi
        initializeAllTours();
        // View binding
        etSearch = view.findViewById(R.id.etSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        spinnerFilter = view.findViewById(R.id.spinnerFilter);
        spinnerSort = view.findViewById(R.id.spinnerSort);
        rvFiltered = view.findViewById(R.id.rvTours);
        rvRecommended = view.findViewById(R.id.rvRecommendedTours);
        recommendedTitle = view.findViewById(R.id.recommendedTitle);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item_background_brown, new String[]{
                "All", "City", "Guide"});
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(filterAdapter);

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item_background_brown, // Seçili öğe
                new String[]{"Date", "Popularity"});
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Açılır liste (normal)
        spinnerSort.setAdapter(sortAdapter);


        // Searched tour view
        filteredAdapter = new TourAdapter(getContext(), filteredTours);
        rvFiltered.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        rvFiltered.setAdapter(filteredAdapter);

        // Önerilen turlar listesi
        recommendedAdapter = new TourAdapter(getContext(), recommendedTours);
        rvRecommended.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        rvRecommended.setAdapter(recommendedAdapter);

        btnSearch.setOnClickListener(v -> applySearchFilterSort());

    }

    private void applySearchFilterSort() {
        String query = etSearch.getText().toString().trim().toLowerCase();
        if (query.isEmpty()) {
            showRecommended();
            return;
        }
        String filter = spinnerFilter.getSelectedItem().toString();
        String sortBy = spinnerSort.getSelectedItem().toString();
        // TODO: Use async
        List<EventDTO> result = allTours.stream().filter(t -> {
            switch (filter) {
                case "City":
                    if (!t.locations.isEmpty()) {
                        String locationTitle = t.locations.get(0).title;
                        return locationTitle != null && locationTitle.toLowerCase().contains(query);
                    }
                    return false;

                case "Guide":
                    if (t.ownerId == null) return false;
                    UserDTO guide = null;
                    try {
                        guide = userService.getUser(t.ownerId).execute().body();
                    } catch (IOException e) {
                    }
                    return (guide.username != null &&
                            guide.username.toLowerCase().contains(query)) ||
                           (guide.firstName != null &&
                            guide.firstName.toLowerCase().contains(query)) ||
                           (guide.lastName != null && guide.lastName.toLowerCase().contains(query));


                case "All":
                default:
                    return t.name != null && t.name.toLowerCase().contains(query);
            }
        }).collect(Collectors.toList());

        if (sortBy.equals("Date")) {
            result.sort(Comparator.comparing(t -> t.startDate));
        } else if (sortBy.equals("Popularity")) {
            result.sort((t1, t2) -> Integer.compare(t2.userIds.size(), t1.userIds.size()));
        }

        filteredTours.clear();
        filteredTours.addAll(result);
        filteredAdapter.notifyDataSetChanged();
        showAllTours();
    }


    private void showRecommended() {
        recommendedTitle.setVisibility(View.VISIBLE);
        rvRecommended.setVisibility(View.VISIBLE);
        rvFiltered.setVisibility(View.GONE);
    }

    private void showAllTours() {
        recommendedTitle.setVisibility(View.GONE);
        rvRecommended.setVisibility(View.GONE);
        rvFiltered.setVisibility(View.VISIBLE);
    }

    private void initializeAllTours() {
        if (!allTours.isEmpty()) {
            return;
        }
        eventService.getAllEvents().enqueue(new Callback<List<EventDTO>>() {
            @Override
            public void onResponse(Call<List<EventDTO>> call, Response<List<EventDTO>> response) {
                if (response.isSuccessful() && response.body() != null &&
                    !response.body().isEmpty()) {
                    allTours.clear();
                    allTours.addAll(response.body());
                    filteredTours.addAll(allTours);
                    filteredAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<EventDTO>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.Tourscouldnotbeaccessed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeRecommendedTours() {
        if (!recommendedTours.isEmpty()) {
            return;
        }
        eventService.getRecommendedTours().enqueue(new Callback<List<EventDTO>>() {

            @Override
            public void onResponse(Call<List<EventDTO>> call, Response<List<EventDTO>> response) {
                if (response.isSuccessful() && response.body() != null &&
                    !response.body().isEmpty()) {
                    recommendedTours.clear();
                    recommendedTours.addAll(response.body());
                    showRecommended();
                }
            }

            @Override
            public void onFailure(Call<List<EventDTO>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.Recommendedtoursnotavailable, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
