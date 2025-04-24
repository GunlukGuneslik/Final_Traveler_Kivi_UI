package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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
        fillSocialMediaPosts();
        return view;
    }
    //for testing now
    private void fillSocialMediaPosts(){
        String[] userNames = getResources().getStringArray(R.array.userNames);
        String[] photoDescriptions = getResources().getStringArray(R.array.photoDescriptions);
        String[] hashtags = getResources().getStringArray(R.array.hashtags);

        for (int i = 0; i < userNames.length; i++) {
            socialMediaPostModels.add(new SocialMediaPostModel(userNames[i], photoDescriptions[i], hashtags[i], R.drawable.baseline_account_box_24, R.drawable.anitkabir, 5));
        }

        SocialMediaPost_RecyclerViewAdapter adapter = new SocialMediaPost_RecyclerViewAdapter(getContext(),socialMediaPostModels);
        recyclerView.setAdapter(adapter);
    }
}