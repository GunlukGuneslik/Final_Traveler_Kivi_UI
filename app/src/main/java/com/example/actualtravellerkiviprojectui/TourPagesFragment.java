//package com.example.actualtravellerkiviprojectui;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.SearchView;
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.actualtravellerkiviprojectui.adapter.Attended_Tour_RecyclerViewAdapter_for_tour_frames;
//import com.example.actualtravellerkiviprojectui.adapter.Place_RecyclerViewAdapter;
//import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
//import com.example.actualtravellerkiviprojectui.model.Tour;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//
//import java.util.ArrayList;
//
///**
// * @author Güneş
// * Bunu sileceğim sadece bir süre daha duracak
// */
//public class AttendedTourPagesFragment extends Fragment {
//    private ArrayList<Tour> toursList = new ArrayList<Tour>();
//    private SearchView tourSearchBar;
//    private FrameLayout fragmentForToursPage;
//    private RecyclerView recyclerViewForAttendedToursWaitingForRating;
//    private RecyclerView recyclerViewForAttendedToursRated;
//
//    private Attended_Tour_RecyclerViewAdapter_for_tour_frames adapter;
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public AttendedTourPagesFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MapPageFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AttendedTourPagesFragment newInstance(String param1, String param2) {
//        AttendedTourPagesFragment fragment = new AttendedTourPagesFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.activity_attended_tours, container, false);
//        recyclerViewForAttendedToursWaitingForRating = view.findViewById(R.id.recyclerViewAttendedToursWaitingForRating);
//        recyclerViewForAttendedToursRated = view.findViewById(R.id.recyclerViewAttendedToursRated);
//
//        fillTheAttendedToursWaitingForRatingArrayList();
//        fillTheAttendedToursRatedArrayList();
//
//
//        tourSearchBar = view.findViewById(R.id.searchViewForAttendedTours);
//        tourSearchBar.clearFocus();
//        tourSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            // we dont use this one
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                flitterList(newText);
//                return true;
//            }
//        });
//
//        adapter = new Attended_Tour_RecyclerViewAdapter_for_tour_frames(getContext(), toursList, getContext());
//        recyclerView.setAdapter(mapAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        return view;
//    }
//
//    // TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage.
//    private void flitterList(String Text) {
//        ArrayList<PlaceModel> fliteredList = new ArrayList<>();
//
//        for (PlaceModel current: placeModels) {
//            if (current.getPlaceName().toLowerCase().contains(Text.toLowerCase())) {
//                fliteredList.add(current);
//            }
//        }
//
//        if (fliteredList.isEmpty()) {
//            Toast.makeText(getContext(), "There is no place is found", Toast.LENGTH_SHORT).show();
//        } else {
//            mapAdapter.setFlitiredList(fliteredList);
//        }
//    }
//
//    // TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage.
//    /**it would be nice if the methods checks weather the data changed or not.
//     *if data is not changed it might be cause delay.
//     * also prevent the duplication of items
//     */
//    private void fillThePlaceArrayList() {
//        PlaceModel testPlace1 = new PlaceModel("Ankara Kalesi",5,8,"f", "Ankara", "Altındağ", new LatLng(39.925533, 32.866287));
//        PlaceModel testPlace2 = new PlaceModel("f",5,8,"f\nk\nh", "Ankara", "Çankaya", new LatLng(41.0082, 28.9784));
//
//        placeModels.add(testPlace1);
//        placeModels.add(testPlace2);
//    }
//}
