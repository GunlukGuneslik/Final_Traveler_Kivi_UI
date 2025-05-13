package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.Comments_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCommentDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zeynep
 */
public class TourInformationPageCommentsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TOUR_ID = "tourId";
    private final List<EventCommentDTO> comments = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private Integer tourId;
    private RecyclerView recyclerView;
    private Comments_RecyclerViewAdapter adapter;
    private EventDTO currentTour;

    public TourInformationPageCommentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TourInformationPageCommentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TourInformationPageCommentsFragment newInstance(int tourId) {
        TourInformationPageCommentsFragment fragment = new TourInformationPageCommentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TOUR_ID, tourId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tourId = getArguments().getInt(ARG_TOUR_ID);
        }
        adapter = new Comments_RecyclerViewAdapter(getContext(), comments, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour_information_page_comments, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        try {
            comments.clear();
            comments.addAll(ServiceLocator.getEventService().getEventComment(tourId).execute().body());
        } catch (IOException e) {
            Log.w("Tour", "Error fetching comments", e);
        }
        // Inflate the layout for this fragment
        return view;
    }
}