package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.ChangeTourDetailsPage;
import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.model.Tour;

import java.util.ArrayList;

/**
 * @author Güneş
 * this adapter was created to show previously created tours which can be accessed by account page of a guide user
 */
public class guideUserTourCatalog_RecyclerViewAdapter extends RecyclerView.Adapter<guideUserTourCatalog_RecyclerViewAdapter.MapViewHolder> {
    Context context;
    ArrayList<Tour> tourList;

    public guideUserTourCatalog_RecyclerViewAdapter(Context context, ArrayList<Tour> tourList) {
        this.context = context;
        this.tourList = tourList;
    }

    @NonNull
    @Override
    public guideUserTourCatalog_RecyclerViewAdapter.MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tour_page_recycler_view_row, parent, false);
        return new guideUserTourCatalog_RecyclerViewAdapter.MapViewHolder(view);
    }

    public void onBindViewHolder(@NonNull guideUserTourCatalog_RecyclerViewAdapter.MapViewHolder holder, int position) {
        Tour currentTour = tourList.get(position);

        holder.tourName.setText(currentTour.getTourName());
        // TODO muhtemelen bu uri olarak değiştirilecek
        holder.tourPhoto.setImageResource(currentTour.getTourImage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: buna kaldığım yerden devam edeceğim
                Intent intent = new Intent(v.getContext(), ChangeTourDetailsPage.class);
                intent.putExtra("tour", currentTour);
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public class MapViewHolder extends RecyclerView.ViewHolder{
        ImageView tourPhoto;
        TextView tourName;
        CardView cardView;
        public MapViewHolder(@NonNull View itemView) {
            super(itemView);
            tourPhoto = itemView.findViewById(R.id.miniTourCardGuideImage);
            tourName = itemView.findViewById(R.id.miniTourCardTourName);
            cardView = itemView.findViewById(R.id.TourMiniCard);
        }
    }

    public void setFilteredList(ArrayList<Tour> filteredList){
        this.tourList = filteredList;
        notifyDataSetChanged();
    }
}
