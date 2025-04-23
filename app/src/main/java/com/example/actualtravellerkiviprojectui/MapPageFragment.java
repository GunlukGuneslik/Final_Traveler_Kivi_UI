package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.actualtravellerkiviprojectui.dto.PlaceModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapPageFragment extends Fragment {

    ArrayList<PlaceModel> placeModels = new ArrayList<PlaceModel>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapPageFragment() {
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
    public static MapPageFragment newInstance(String param1, String param2) {
        MapPageFragment fragment = new MapPageFragment();
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
        return inflater.inflate(R.layout.fragment_map_page, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.mapPageRecyclerView);
        fillThePlaceArrayList();
        Place_RecyclerViewAdapter mapAdapter = new Place_RecyclerViewAdapter(getContext(), placeModels);
        recyclerView.setAdapter(mapAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    // TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage.
    /**it would be nice if the methods checks weather the data changed or not.
     *if data is not changed it might be cause delay.
     * also prevent the duplication of items
     */
    private void fillThePlaceArrayList() {
        PlaceModel testPlace1 = new PlaceModel("f",5,8,"f");
        PlaceModel testPlace2 = new PlaceModel("f",5,8,"f\nk\nh");
        PlaceModel testPlace3 = new PlaceModel("f",5,8,"f");
        PlaceModel testPlace4 = new PlaceModel("f",5,8,"f\nk\nh");
        PlaceModel testPlace5 = new PlaceModel("f",5,8,"f\nk\nh");
        placeModels.add(testPlace1);
        placeModels.add(testPlace2);
        placeModels.add(testPlace3);
        placeModels.add(testPlace4);
        placeModels.add(testPlace5);
    }
}