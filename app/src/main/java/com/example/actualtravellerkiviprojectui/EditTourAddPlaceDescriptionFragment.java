package com.example.actualtravellerkiviprojectui;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.CreateTourPlaceDescriptionRecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationCreateDTO;

import java.util.ArrayList;

public class EditTourAddPlaceDescriptionFragment extends Fragment {
    EditTourActivity activity;
    RecyclerView recyclerView;
    CreateTourPlaceDescriptionRecyclerViewAdapter adapter;
    ArrayList<EventLocationCreateDTO> placeModels;
}
