package com.example.actualtravellerkiviprojectui;

import static com.example.actualtravellerkiviprojectui.api.modules.NetworkModule.toCompletableFuture;

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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;

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
        switch (filter) {
            case "Guide":
                filterByOwnerName(query, tours -> {
                    showAllTours();
                }, t -> {
                    showRecommended();
                    Toast.makeText(getContext(), "Turlar alınamadı", Toast.LENGTH_SHORT).show();
                });
                break;
            case "City":
                filterByLocation(query, tours -> {
                    showAllTours();
                }, t -> {
                    showRecommended();
                    Toast.makeText(getContext(), "Turlar alınamadı", Toast.LENGTH_SHORT).show();
                });
                break;
        }
    }

    private void filterByLocation(String location, Consumer<List<EventDTO>> onSuccess, Consumer<Throwable> onError) {
        setFilteredToursFromCall(eventService.getEventsByLocation(location), onSuccess, onError);
    }

    private void filterByOwnerName(String ownerName, Consumer<List<EventDTO>> onSuccess, Consumer<Throwable> onError) {
        setFilteredToursFromCall(eventService.getEventsByOwner(ownerName), onSuccess, onError);
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

    private void setFilteredToursFromCall(Call<List<EventDTO>> call, Consumer<List<EventDTO>> onSuccess, Consumer<Throwable> onError) {
        toCompletableFuture(call)
                .thenAccept(results -> {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            filteredTours.clear();
                            filteredTours.addAll(results);
                            filteredAdapter.notifyDataSetChanged();
                            onSuccess.accept(results);
                        });
                    }
                })
                .exceptionally(t -> {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), R.string.Tourscouldnotbeaccessed, Toast.LENGTH_SHORT).show();
                        onError.accept(t);
                    });
                    }
                    return null;
                });
    }

    private void initializeRecommendedTours() {
        if (!recommendedTours.isEmpty()) return;
        toCompletableFuture(eventService.getRecommendedTours())
                .thenAccept(list -> {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            recommendedTours.clear();
                            recommendedTours.addAll(list);
                            showRecommended();
                        });
                    }
                })
                .exceptionally(t -> {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), R.string.Recommendedtoursnotavailable, Toast.LENGTH_SHORT).show()
                    );
                    }
                    return null;
                });
    }
}

