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
import com.example.actualtravellerkiviprojectui.model.Tour;

import java.util.ArrayList;
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

    private List<Tour> allTours = new ArrayList<>();
    private List<Tour> filteredTours = new ArrayList<>();

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

        // Spinner ayarları
        spinnerFilter.setAdapter(new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item,
                new String[]{"All", "Ankara", "Istanbul", "Cappadocia"}));


        // Normal tur listesi (dikey)
        rvTours.setLayoutManager(new LinearLayoutManager(requireContext()));
        mainAdapter = new TourAdapter(filteredTours);
        rvTours.setAdapter(mainAdapter);

        // Önerilen turlar listesi (dikey)
        rvRecommended.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        recommendedAdapter = new TourAdapter(new ArrayList<>());
        rvRecommended.setAdapter(recommendedAdapter);

        btnSearch.setOnClickListener(v -> applySearchFilterSort());

        loadTours();              // Normal tur listesi
        loadRecommendedTours();   // Önerilen tur listesi
    }

    private void applySearchFilterSort() {
        String query = etSearch.getText().toString().trim().toLowerCase();
        String filter = spinnerFilter.getSelectedItem().toString();
        String sortBy = spinnerSort.getSelectedItem().toString();

        List<Tour> result = allTours.stream()
                .filter(t -> t.getTourName().toLowerCase().contains(query))
                .filter(t -> filter.equals("All") || t.getTourName().equals(filter))
                .collect(Collectors.toList());

        if (sortBy.equals("Date")) {
            result.sort((t1, t2) -> t1.getDate().compareTo(t2.getDate()));
        } else if (sortBy.equals("Popularity")) {
            result.sort((t1, t2) -> Integer.compare(t2.getPopularity(), t1.getPopularity()));
        }

        filteredTours.clear();
        filteredTours.addAll(result);
        mainAdapter.notifyDataSetChanged();
    }

    private void loadTours() {
        // Şimdilik test datası
        allTours = new ArrayList<>();
        filteredTours.clear();
        filteredTours.addAll(allTours);
        mainAdapter.notifyDataSetChanged();
    }

    private void loadRecommendedTours() {
        eventService.getRecommendedTours().enqueue(new Callback<List<EventDTO>>() {
            @Override
            public void onResponse(Call<List<EventDTO>> call, Response<List<EventDTO>> response) {
                if (response.isSuccessful() && response.body() != null &&
                    !response.body().isEmpty()) {
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
