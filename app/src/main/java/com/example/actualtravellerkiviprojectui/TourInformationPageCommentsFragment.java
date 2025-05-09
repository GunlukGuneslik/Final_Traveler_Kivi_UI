package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.actualtravellerkiviprojectui.adapter.Comments_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.adapter.TourPlan_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.example.actualtravellerkiviprojectui.model.Tour;

import java.util.ArrayList;

/**
 * @author zeynep
 */
public class TourInformationPageCommentsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Tour currentTour;
    private RecyclerView recyclerView;
    private Comments_RecyclerViewAdapter adapter;
    private ArrayList<String> comments = new ArrayList<>();

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
    public static TourInformationPageCommentsFragment newInstance(String param1, String param2) {
        TourInformationPageCommentsFragment fragment = new TourInformationPageCommentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour_information_page_comments, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        currentTour = getActivity().getIntent().getParcelableExtra("tour");
        comments = currentTour.getComments();
        adapter = new Comments_RecyclerViewAdapter(getContext(),comments,this);
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
}