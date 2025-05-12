package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.TourInformationPageTourPlanFragment;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;

import java.util.List;

/**
 * @author zeynep
 */
public class TourPlan_RecyclerViewAdapter extends RecyclerView.Adapter<TourPlan_RecyclerViewAdapter.TourPlanViewHolder> {
    Context context;
    List<EventLocationDTO> places;
    TourInformationPageTourPlanFragment tourInformationPageTourPlanFragment;

    public TourPlan_RecyclerViewAdapter(Context context, List<EventLocationDTO> places, TourInformationPageTourPlanFragment fragment) {
        this.context = context;
        this.places = places;
        tourInformationPageTourPlanFragment = fragment;
    }

    @NonNull
    @Override
    public TourPlan_RecyclerViewAdapter.TourPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tour_plan_recycler_view_row,parent,false);
        return new TourPlan_RecyclerViewAdapter.TourPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourPlan_RecyclerViewAdapter.TourPlanViewHolder holder, int position) {
        holder.placeName.setText(places.get(position).title);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class TourPlanViewHolder extends RecyclerView.ViewHolder{
        TextView placeName;

        public TourPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.TourPlanPlaceName);
        }
    }
}

