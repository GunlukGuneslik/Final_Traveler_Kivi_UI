package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.model.Tour;

import java.util.ArrayList;

public class Tours_RecyclerViewAdapter extends RecyclerView.Adapter<Tours_RecyclerViewAdapter.MapViewHolder>{

    Context context;
    ArrayList<Tour> upcomingToursList;

    @NonNull
    @Override
    public Tours_RecyclerViewAdapter.MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Tours_RecyclerViewAdapter.MapViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
