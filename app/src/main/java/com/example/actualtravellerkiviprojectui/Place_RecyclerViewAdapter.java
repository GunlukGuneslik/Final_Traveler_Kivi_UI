package com.example.actualtravellerkiviprojectui;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.dto.PlaceModel;

import java.util.ArrayList;

public class Place_RecyclerViewAdapter extends RecyclerView.Adapter<Place_RecyclerViewAdapter.MapViewHolder> {
    Context context;
    ArrayList<PlaceModel> placeModels;
    public Place_RecyclerViewAdapter(Context context, ArrayList<PlaceModel> placeModels) {
        this.context = context;
        this.placeModels = placeModels;
    }

    @NonNull
    @Override
    public Place_RecyclerViewAdapter.MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.map_page_recycler_view_row, parent, false);
        return new Place_RecyclerViewAdapter.MapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Place_RecyclerViewAdapter.MapViewHolder holder, int position) {
        holder.placeImageView.setImageResource(placeModels.get(position).getImageOfPlace());
        holder.placeNameView.setText(placeModels.get(position).getPlaceName());
        holder.placeRateView.setText(placeModels.get(position).getRateOfPlace());
        holder.placeDistanceView.setText(placeModels.get(position).getDistanceInKM());
        holder.placeInfoView.setText(placeModels.get(position).getPlaceInformationText());
    }

    @Override
    public int getItemCount() {
        return placeModels.size();
    }

    public static class MapViewHolder extends RecyclerView.ViewHolder {

        ImageView placeImageView;
        TextView placeNameView, placeRateView, placeDistanceView, placeInfoView;
        public MapViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImageView = itemView.findViewById(R.id.placeImageView);
            placeNameView = itemView.findViewById(R.id.placeNameTextView);
            placeRateView = itemView.findViewById(R.id.ratingTextView);
            placeDistanceView = itemView.findViewById(R.id.distanceTextView);
            placeInfoView = itemView.findViewById(R.id.placeInformationTextView);
        }
    }
}
