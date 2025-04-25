package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.actualtravellerkiviprojectui.dto.UserDTO;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountPageFragment extends Fragment {

    ArrayList<SocialMediaPostModel> posts = new ArrayList<>();
    private RecyclerView recyclerView;
    private Account_Page_Posts_RecyclerViewAdapter adapter;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountPageFragment newInstance(String param1, String param2) {
        AccountPageFragment fragment = new AccountPageFragment();
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
        View view = inflater.inflate(R.layout.fragment_account_page, container, false);

        recyclerView = view.findViewById(R.id.AccountPagePostRecyclerView);
        //posts = currentUser.getPosts();
        fillSocialMediaPosts();

        adapter = new Account_Page_Posts_RecyclerViewAdapter(getContext(), posts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    //TODO: complete this method so that it fills the array with user's posts
    private void fillSocialMediaPosts(){
        String[] photoDescriptions = getResources().getStringArray(R.array.photoDescriptions);
        String[] hashtags = getResources().getStringArray(R.array.hashtags);
        Date anyDate = new Date(2025,04,25);

        for (int i = 0; i < 4; i++) {
            posts.add(new SocialMediaPostModel("Helena", photoDescriptions[i], hashtags[i], R.drawable.baseline_account_box_24, R.drawable.anitkabir, 5, anyDate));
        }
    }
}