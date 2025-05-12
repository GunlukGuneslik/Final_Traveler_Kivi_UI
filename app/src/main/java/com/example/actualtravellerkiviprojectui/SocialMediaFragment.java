package com.example.actualtravellerkiviprojectui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.SocialMediaPost_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.dto.Post.PostDTO;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author zeynep
 */
public class SocialMediaFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private SocialMediaPost_RecyclerViewAdapter socialMediaAdapter;

    private List<PostDTO> postDTOS = new ArrayList<>();

    public SocialMediaFragment() {
        // Required empty public constructor
    }

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
        socialMediaAdapter = new SocialMediaPost_RecyclerViewAdapter(getContext(), postDTOS, this);
        fillSocialMediaPosts();
        ImageButton addPostButton = view.findViewById(R.id.addPostImageButton);
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPostActivity.class);
                startActivity(intent);
            }
        });
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
        List<PostDTO> filteredList = new ArrayList<>();
        for (PostDTO post : postDTOS) {
            if (post.tags.stream().anyMatch(s -> s.toLowerCase().contains(newText.toLowerCase()))) {
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
        try {
            postDTOS.clear();
            ServiceLocator.getPostService().fetchAllPosts().execute().body().forEach(postDTOS::add);
        } catch (IOException e) {
            cleanUp(e);
            return;
        }
        recyclerView.setAdapter(socialMediaAdapter);
    }

    private void cleanUp(Throwable t) {
        Snackbar.make(getView(), "Error fetching the tour. Please try again.", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", v -> {
                    // Retry the network call
                    fillSocialMediaPosts();
                })
                .show();
    }
}