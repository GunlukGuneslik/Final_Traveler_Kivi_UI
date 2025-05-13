
package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.TourInformationPageActivity;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {

    private final UserService userService = ServiceLocator.getUserService();
    private final PostService postService = ServiceLocator.getPostService();
    private final EventService eventService = ServiceLocator.getEventService();
    private Context context;
    private List<EventDTO> tourList;

    public TourAdapter(Context context, List<EventDTO> tourList) {
        this.context = context;
        this.tourList = tourList;
    }

    public void setTours(List<EventDTO> newList) {
        this.tourList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tour, parent, false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        EventDTO tour = tourList.get(position);
        holder.tourName.setText(tour.name);
        UserDTO owner = null;

        userService.getUser(tour.ownerId).enqueue(new retrofit2.Callback<UserDTO>() {
            @Override
            public void onResponse(retrofit2.Call<UserDTO> call, retrofit2.Response<UserDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserDTO owner = response.body();
                    holder.guideName.setText("with " + owner.firstName);
                } else {
                    // API cevap verdi ama başarısız oldu
                    holder.guideName.setText("Rehber bilgisi alınamadı");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UserDTO> call, Throwable t) {
                // Bir kez daha dene
                if (!call.isCanceled()) {
                    call.clone().enqueue(this); // Aynı callback ile yeniden dene
                } else {
                    // İkinci deneme de başarısız olursa
                    holder.guideName.setText("ERR");
                }
            }
        });

        NetworkModule.setImageViewFromCall(holder.tourImage, eventService.getPhoto(tour.id), null);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TourInformationPageActivity.class);
            intent.putExtra("tourId", tour.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        ImageView tourImage;
        TextView tourName, guideName;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            tourImage = itemView.findViewById(R.id.tourImage);
            tourName = itemView.findViewById(R.id.tourNameText);
            guideName = itemView.findViewById(R.id.guideNameText);
        }
    }
}
