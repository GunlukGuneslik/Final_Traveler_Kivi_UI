package com.example.actualtravellerkiviprojectui;

import android.content.ClipData;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.actualtravellerkiviprojectui.adapter.Place_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.adapter.SocialMediaPost_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zeynep
 * A simple {@link Fragment} subclass.
 * Use the {@link SocialMediaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialMediaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private SocialMediaPost_RecyclerViewAdapter socialMediaAdapter;

    private ArrayList<SocialMediaPostModel> socialMediaPostModels = new ArrayList<>();

    public SocialMediaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SocialMediaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SocialMediaFragment newInstance(String param1, String param2) {
        SocialMediaFragment fragment = new SocialMediaFragment();
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
        View view = inflater.inflate(R.layout.fragment_social_media, container, false);
        recyclerView = view.findViewById(R.id.socialMediaRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        socialMediaAdapter = new SocialMediaPost_RecyclerViewAdapter(getContext(),socialMediaPostModels,this);
        fillSocialMediaPosts();
        searchView = view.findViewById(R.id.socialMediaSearchBar);
        searchView.clearFocus();
        searchView.setQueryHint("#...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        return view;
    }

    private void filterList(String newText) {
        ArrayList<SocialMediaPostModel> filteredList = new ArrayList<>();
        for (SocialMediaPostModel post: socialMediaPostModels) {
            if(post.getHashtag().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(post);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "There is no post with written hashtag", Toast.LENGTH_SHORT).show();
        } else {
            socialMediaAdapter.setFilteredList(filteredList);
        }

    }

    //for testing now
    private void fillSocialMediaPosts(){
        String[] userNames = getResources().getStringArray(R.array.userNames);
        String[] photoDescriptions = getResources().getStringArray(R.array.photoDescriptions);
        String[] hashtags = getResources().getStringArray(R.array.hashtags);
        // merhaba zeynep, ufak bir değişiklik yaptım güneş.
        Date anyDate = new Date(2025,4,25);
        for (int i = 0; i < userNames.length; i++) {
            socialMediaPostModels.add(new SocialMediaPostModel(userNames[i], photoDescriptions[i], hashtags[i], R.drawable.baseline_account_box_24, R.drawable.anitkabir, 5, anyDate));
        }
        recyclerView.setAdapter(socialMediaAdapter);
    }
}