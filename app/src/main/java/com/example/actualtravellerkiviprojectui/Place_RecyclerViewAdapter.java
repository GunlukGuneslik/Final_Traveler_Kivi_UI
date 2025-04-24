package com.example.actualtravellerkiviprojectui;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import java.util.ArrayList;

public class Place_RecyclerViewAdapter extends RecyclerView.Adapter<Place_RecyclerViewAdapter.MapViewHolder> {
    Context context;
    ArrayList<PlaceModel> placeModels;
    MapPageFragment mapPageFragment;

    public Place_RecyclerViewAdapter(Context context, ArrayList<PlaceModel> placeModels, MapPageFragment fragment) {
        this.context = context;
        this.placeModels = placeModels;
        mapPageFragment = fragment;
    }

    public void setFlitiredList(ArrayList<PlaceModel> flitiredList){
        this.placeModels = flitiredList;
        notifyDataSetChanged();
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
        PlaceModel currentPlace = placeModels.get(position);
        holder.placeImageView.setImageResource(currentPlace.getImageOfPlace());
        holder.placeNameView.setText(currentPlace.getPlaceName());
        holder.placeRateView.setText(currentPlace.getRateOfPlace());
        holder.placeDistanceView.setText(currentPlace.getDistanceInKM());
        holder.placeInfoView.setText(currentPlace.getPlaceInformationText());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapPageFragment.showPlaceOnMap(currentPlace);
                Toast.makeText(context, "You clicked: " + currentPlace.getPlaceName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeModels.size();
    }

    public class MapViewHolder extends RecyclerView.ViewHolder {

        ImageView placeImageView;
        TextView placeNameView, placeRateView, placeDistanceView, placeInfoView;
        CardView cardView;
        public MapViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImageView = itemView.findViewById(R.id.placeImageView);
            placeNameView = itemView.findViewById(R.id.placeNameTextView);
            placeRateView = itemView.findViewById(R.id.ratingTextView);
            placeDistanceView = itemView.findViewById(R.id.distanceTextView);
            placeInfoView = itemView.findViewById(R.id.placeInformationTextView);
            cardView = itemView.findViewById(R.id.Card);
        }
    }
}
