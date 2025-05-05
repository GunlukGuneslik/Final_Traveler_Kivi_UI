package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.AccountPageFragment;
import com.example.actualtravellerkiviprojectui.model.SocialMediaPostModel;

import java.util.ArrayList;

public class User_Post_RecyclerViewAdapter extends RecyclerView.Adapter<Place_RecyclerViewAdapter.MapViewHolder> {
    Context context;
    ArrayList<SocialMediaPostModel> posts;
    AccountPageFragment accountPageFragment;

    public User_Post_RecyclerViewAdapter(Context context, AccountPageFragment accountPageFragment, ArrayList<SocialMediaPostModel> posts) {
        this.context = context;
        this.accountPageFragment = accountPageFragment;
        this.posts = posts;
    }

    @NonNull
    @Override
    public Place_RecyclerViewAdapter.MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Place_RecyclerViewAdapter.MapViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
