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

import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.TourInformationPageActivity;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;

import java.util.List;

/**
 * @author Güneş
 * this adapter was created to show attended tours and upcoming tours which can be accessed by account page
 */
public class Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page extends RecyclerView.Adapter<Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page.MapViewHolder>{
    Context context;
    List<EventDTO> tourList;

    public Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page(Context context, List<EventDTO> tourList) {
        this.context = context;
        this.tourList = tourList;
    }

    @NonNull
    @Override
    public Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page.MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tour_page_recycler_view_row, parent, false);
        return new Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page.MapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tour_RecyclerViewAdapter_for_tours_accessed_from_account_page.MapViewHolder holder, int position) {
        EventDTO currentTour = tourList.get(position);
        int guideId = currentTour.ownerId;

        holder.tourName.setText(currentTour.name);
        //TODO : userlara fotoğraf eklenecek
        //holder.guidePhoto.setImageURI(guide.photo);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TourInformationPageActivity.class);
                intent.putExtra("tourId", currentTour.id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public class MapViewHolder extends RecyclerView.ViewHolder{
        ImageView guidePhoto;
        TextView tourName;
        CardView cardView;
        public MapViewHolder(@NonNull View itemView) {
            super(itemView);
            guidePhoto = itemView.findViewById(R.id.miniTourCardGuideImage);
            tourName = itemView.findViewById(R.id.miniTourCardTourName);
            cardView = itemView.findViewById(R.id.TourMiniCard);
        }
    }

    public void setFilteredList(List<EventDTO> filteredList) {
        this.tourList = filteredList;
        notifyDataSetChanged();
    }
}
