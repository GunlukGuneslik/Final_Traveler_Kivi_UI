package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import java.util.ArrayList;

public class CreateTourPlaceDescriptionRecyclerViewAdapter  extends RecyclerView.Adapter<CreateTourPlaceDescriptionRecyclerViewAdapter.MyViewHolder>{

    Context context;
    private ArrayList<EventLocationCreateDTO> placeModels;
    public CreateTourPlaceDescriptionRecyclerViewAdapter(Context context, ArrayList<EventLocationCreateDTO> placeModels) {
        this.context = context;
        this.placeModels = placeModels;
    }
    @NonNull
    @Override
    public CreateTourPlaceDescriptionRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.create_tour_page_place_recycler_view_row, parent,false);
        return new CreateTourPlaceDescriptionRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateTourPlaceDescriptionRecyclerViewAdapter.MyViewHolder holder, int position) {
        EventLocationCreateDTO currentPlace = placeModels.get(position);
        if (currentPlace.title != null) {
            holder.placeName.setText(currentPlace.title);
        }
        if (currentPlace.description != null) {
            holder.placeDescription.setText(currentPlace.description);
        }

        holder.placeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                currentPlace.title = s.toString(); // Kullanıcının girdiğini PlaceModel'a kaydet
            }
        });

        holder.placeDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                currentPlace.description = s.toString();
            }
        });

        holder.deletePlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeModels.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), placeModels.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        EditText placeName;
        EditText placeDescription;
        Button deletePlaceButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.editTextPlaceName);
            placeDescription = itemView.findViewById(R.id.editTextPlaceDescription);
            deletePlaceButton = itemView.findViewById(R.id.deletePlaceButton);
        }
    }
}
