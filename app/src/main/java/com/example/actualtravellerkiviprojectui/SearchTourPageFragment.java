package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
import com.example.actualtravellerkiviprojectui.model.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchTourPageFragment extends Fragment {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();

    @Override
    public View onCreateView(@NonNull LayoutInflater inf, ViewGroup ctr, Bundle bs) {
        return inf.inflate(R.layout.fragment_search_tour_page, ctr, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bs) {
        super.onViewCreated(view, bs);

        EditText etSearch      = view.findViewById(R.id.etSearch);
        Button  btnSearch      = view.findViewById(R.id.btnSearch);
        Spinner spinnerFilter  = view.findViewById(R.id.spinnerFilter);
        Spinner spinnerSort    = view.findViewById(R.id.spinnerSort);
        RecyclerView rvTours   = view.findViewById(R.id.rvTours);

        rvTours.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<Tour> allTours      = loadTours();
        List<Tour> filteredTours = new ArrayList<>(allTours);
        TourAdapter adapter      = new TourAdapter(filteredTours);
        rvTours.setAdapter(adapter);

        spinnerFilter.setAdapter(new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item,
                new String[]{"All","Location A","Location B"}));
        spinnerSort.setAdapter(new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item,
                new String[]{"Date","Popularity"}));

        btnSearch.setOnClickListener(v -> {
            String q      = etSearch.getText().toString().trim().toLowerCase();
            String filt   = spinnerFilter.getSelectedItem().toString();
            String sortBy = spinnerSort.getSelectedItem().toString();

            List<Tour> result = allTours.stream()
                    .filter(t -> t.getTourName().toLowerCase().contains(q))
                    .filter(t -> filt.equals("All") || t.getTourName().equals(filt))
                    .collect(Collectors.toList());

            if (sortBy.equals("Date")) {
                result.sort((t1,t2) -> t1.getDate().compareTo(t2.getDate()));
            } else {
                result.sort((t1,t2) ->
                        Integer.compare(t2.getPopularity(), t1.getPopularity()));
            }

            filteredTours.clear();
            filteredTours.addAll(result);
            adapter.notifyDataSetChanged();
        });
    }

    // TODO: Test verisi; yerine gerçek API/DB çağıracaksın
    private List<Tour> loadTours() {
        ArrayList<Tour> tours = new ArrayList<>();
        return tours;
    }
}
