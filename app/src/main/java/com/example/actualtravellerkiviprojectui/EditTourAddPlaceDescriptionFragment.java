package com.example.actualtravellerkiviprojectui;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.actualtravellerkiviprojectui.adapter.CreateTourPlaceDescriptionRecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;
import java.util.ArrayList;

public class EditTourAddPlaceDescriptionFragment extends Fragment {
    EditTourActivity activity;
    RecyclerView recyclerView;
    CreateTourPlaceDescriptionRecyclerViewAdapter adapter;
    ArrayList<EventLocationDTO> locationsList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditTourAddPlaceDescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditTourAddPlaceDescriptionFragment newInstance(String param1, String param2) {
        EditTourAddPlaceDescriptionFragment fragment = new EditTourAddPlaceDescriptionFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_tour_page_add_descriptions_to_places, container, false);
        recyclerView = view.findViewById(R.id.AddDescriptionsRecyclerView);

        activity = (EditTourActivity) getActivity();
        locationsList = activity.locationList;

        adapter = new CreateTourPlaceDescriptionRecyclerViewAdapter(getContext(), locationsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

}
