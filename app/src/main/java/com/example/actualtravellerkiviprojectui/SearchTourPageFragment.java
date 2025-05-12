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

    private RecyclerView rvTours, rvRecommended;
    private TextView recommendedTitle;
    private TourAdapter mainAdapter, recommendedAdapter;

    private List<EventDTO> allTours = new ArrayList<>();
    private List<EventDTO> filteredTours = new ArrayList<>();

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

        // View binding
        etSearch = view.findViewById(R.id.etSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        spinnerFilter = view.findViewById(R.id.spinnerFilter);
        spinnerSort = view.findViewById(R.id.spinnerSort);
        rvTours = view.findViewById(R.id.rvTours);
        rvRecommended = view.findViewById(R.id.rvRecommendedTours);
        recommendedTitle = view.findViewById(R.id.recommendedTitle);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_item_background_brown,
                new String[]{"All", "City", "Guide"}
        );
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(filterAdapter);

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_item_background_brown, // Seçili öğe
                new String[]{"Date", "Popularity"}
        );
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Açılır liste (normal)
        spinnerSort.setAdapter(sortAdapter);


        // Normal tur listesi (dikey)
        rvTours.setLayoutManager(new LinearLayoutManager(requireContext()));
        mainAdapter = new TourAdapter(getContext(), filteredTours);
        rvTours.setAdapter(mainAdapter);

        // Önerilen turlar listesi (dikey)
        rvRecommended.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        recommendedAdapter = new TourAdapter(getContext(), new ArrayList<>());
        rvRecommended.setAdapter(recommendedAdapter);

        btnSearch.setOnClickListener(v -> applySearchFilterSort());

        //loadTours();              // Normal tur listesi
        loadRecommendedTours();   // Önerilen tur listesi
    }

    private void applySearchFilterSort() {
        String query = etSearch.getText().toString().trim().toLowerCase();
        String filter = spinnerFilter.getSelectedItem().toString();
        String sortBy = spinnerSort.getSelectedItem().toString();
        // TODO: Use async
        List<EventDTO> result =
        allTours.stream()
                .filter(t -> {
                    if (query.isEmpty()) return true;

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
                            return (guide.username != null && guide.username.toLowerCase().contains(query)) || (guide.firstName != null && guide.firstName.toLowerCase().contains(query)) || (guide.lastName != null && guide.lastName.toLowerCase().contains(query));


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
        mainAdapter.notifyDataSetChanged();
    }


    private void loadTours() {
        eventService.getAllEvents().enqueue(new Callback<List<EventDTO>>() {
            @Override
            public void onResponse(Call<List<EventDTO>> call, Response<List<EventDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allTours.clear();
                    allTours.addAll(response.body());

                    // İlk görünümde hepsini göster
                    filteredTours.clear();
                    filteredTours.addAll(allTours);
                    mainAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<EventDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Turlar yüklenemedi", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadRecommendedTours() {
        eventService.getRecommendedTours().enqueue(new Callback<List<EventDTO>>() {
            @Override
            public void onResponse(Call<List<EventDTO>> call, Response<List<EventDTO>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<EventDTO> recommended = response.body();
                    recommendedAdapter = new TourAdapter(requireContext(), recommended);

                    rvRecommended.setAdapter(recommendedAdapter);
                    recommendedTitle.setVisibility(View.VISIBLE);
                    rvRecommended.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<EventDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Önerilen turlar alınamadı", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
