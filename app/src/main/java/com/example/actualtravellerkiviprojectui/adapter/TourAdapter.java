
package com.example.actualtravellerkiviprojectui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.model.Tour;
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
        holder.dest.setText(tour.getDestination());
        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                .format(tour.getDate());
        holder.date.setText(date);
        holder.guide.setText("Guide: " + tour.getGuideName());
    }

    @Override
    public int getItemCount() { return tours.size(); }

    static class TourViewHolder extends RecyclerView.ViewHolder {
        TextView dest, date, guide;
        TourViewHolder(@NonNull View iv) {
            super(iv);
            dest  = iv.findViewById(R.id.tvDestination);
            date  = iv.findViewById(R.id.tvDate);
            guide = iv.findViewById(R.id.tvGuide);
        }
    }
}
