package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.AttendedToursActivity;
import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.model.Tour;

import java.util.ArrayList;

/**
 * @author Güneş
 * this adapter was created to show attended tours and upcoming tours which can be accessed by account page
 */
public class Attended_Tour_RecyclerViewAdapter_for_tour_frames extends RecyclerView.Adapter<Attended_Tour_RecyclerViewAdapter_for_tour_frames.MapViewHolder>{
    Context context;
    ArrayList<Tour> tourList;
    AttendedToursActivity tourPagesActivity;

    public Attended_Tour_RecyclerViewAdapter_for_tour_frames(AttendedToursActivity tourPagesActivity, ArrayList<Tour> tourList, Context context) {
        this.tourPagesActivity = tourPagesActivity;
        this.tourList = tourList;
        this.context = context;
    }

    @NonNull
    @Override
    public Attended_Tour_RecyclerViewAdapter_for_tour_frames.MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_tour_pages, parent, false);
        return new Attended_Tour_RecyclerViewAdapter_for_tour_frames.MapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Attended_Tour_RecyclerViewAdapter_for_tour_frames.MapViewHolder holder, int position) {
        Tour currentTour = tourList.get(position);
        //holder.guidePhoto.setImageResource(currentTour.getGuide().getPhoto);
        holder.places.setText(currentTour.getDestination());
        //holder.tourName.setText(currentTour.getTourName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Open the tour page
                Toast.makeText(context, "You clicked: a tour", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public class MapViewHolder extends RecyclerView.ViewHolder{
        ImageView guidePhoto;
        TextView tourName, places;
        CardView cardView;
        public MapViewHolder(@NonNull View itemView) {
            super(itemView);
            guidePhoto = itemView.findViewById(R.id.miniTourCardGuideImage);
            tourName = itemView.findViewById(R.id.miniTourCardTourName);
            places = itemView.findViewById(R.id.miniTourCardDestinition);
            cardView = itemView.findViewById(R.id.TourMiniCard);
        }
    }

    public void setFlitiredList(ArrayList<Tour> flitiredList){
        this.tourList = flitiredList;
        notifyDataSetChanged();
    }
}
