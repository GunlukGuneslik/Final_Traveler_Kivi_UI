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

import com.example.actualtravellerkiviprojectui.MapPageFragment;
import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;

import java.util.List;

/**
 * @author Güneş
 */

public class Place_RecyclerViewAdapter extends RecyclerView.Adapter<Place_RecyclerViewAdapter.MapViewHolder> {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();

    Context context;
    List<EventLocationDTO> placeModels;
    MapPageFragment mapPageFragment;

    public Place_RecyclerViewAdapter(Context context, List<EventLocationDTO> placeModels, MapPageFragment fragment) {
        this.context = context;
        this.placeModels = placeModels;
        mapPageFragment = fragment;
    }

    public void setFilteredList(List<EventLocationDTO> filteredList) {
        this.placeModels = filteredList;
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
        EventLocationDTO place = placeModels.get(position);
        NetworkModule.setImageViewFromCall(holder.placeImageView, eventService.getLocationPhoto(place.id), null);
        holder.placeNameView.setText(place.title);
        holder.placeInfoView.setText(place.description);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapPageFragment.showPlaceOnMap(place);
                Toast.makeText(context, "You clicked: " + place.title, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeModels.size();
    }

    public class MapViewHolder extends RecyclerView.ViewHolder {

        ImageView placeImageView;
        TextView placeNameView, placeRateView, placeInfoView;
        CardView cardView;
        public MapViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImageView = itemView.findViewById(R.id.placeImageView);
            placeNameView = itemView.findViewById(R.id.placeNameTextView);
            placeRateView = itemView.findViewById(R.id.ratingTextView);
            placeInfoView = itemView.findViewById(R.id.placeInformationTextView);
            cardView = itemView.findViewById(R.id.Card);
        }
    }
}
