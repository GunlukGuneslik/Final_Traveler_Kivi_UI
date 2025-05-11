package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;

import java.util.ArrayList;

public class CreateTourPlaceDescriptionRecyclerViewAdapter  extends RecyclerView.Adapter<CreateTourPlaceDescriptionRecyclerViewAdapter.MyViewHolder>{

    Context context;
    private ArrayList<PlaceModel> placeModels;
    public CreateTourPlaceDescriptionRecyclerViewAdapter(Context context, ArrayList<PlaceModel> placeModels) {
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
        PlaceModel placeModel = placeModels.get(position);
        if (placeModel.getPlaceName() != null) {
            holder.placeName.setText(placeModel.getPlaceName());
        }
        if (placeModel.getPlaceInformationText() != null) {
            holder.placeDescription.setText(placeModel.getPlaceInformationText());
        }

        holder.placeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                placeModel.setPlaceName(s.toString()); // Kullanıcının girdiğini PlaceModel'a kaydet
            }
        });

        holder.placeDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                placeModel.setPlaceInformationText(s.toString());
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.editTextPlaceName);
            placeDescription = itemView.findViewById(R.id.editTextPlaceDescription);
        }
    }
}
