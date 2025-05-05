
package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.ApplicationPagesActivity;
import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.TourInformationPageActivity;
import com.example.actualtravellerkiviprojectui.model.Tour;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {
    private final List<Tour> tours;

    public TourAdapter(List<Tour> tours) {
        this.tours = tours;
    }

    @NonNull @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tour, parent, false);
        return new TourViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int pos) {
        Tour tour = tours.get(pos);
        holder.name.setText(tour.getTourName());
        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                .format(tour.getDate());
        holder.date.setText(date);
        holder.guide.setText("Guide: " + tour.getGuideName());
        //test için cardview'a action listener ekliyorum *ben zeynep*
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TourInformationPageActivity.class);
            v.getContext().startActivity(intent);
        });
        //sonra değiştirebiliriz data aktarmak için
    }

    @Override
    public int getItemCount() { return tours.size(); }

    static class TourViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, guide;
        CardView cardView;
        TourViewHolder(@NonNull View iv) {
            super(iv);
            name  = iv.findViewById(R.id.tvName);
            date  = iv.findViewById(R.id.tvDate);
            guide = iv.findViewById(R.id.tvGuide);
            cardView = iv.findViewById(R.id.cardTourSearch);
        }
    }
}
