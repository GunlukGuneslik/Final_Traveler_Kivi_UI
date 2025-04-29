package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.SocialMediaPostModel;

import java.util.ArrayList;

public class SocialMediaPost_RecyclerViewAdapter extends RecyclerView.Adapter<SocialMediaPost_RecyclerViewAdapter.SocialMediaViewHolder> {
    Context context;
    ArrayList<SocialMediaPostModel> socialMediaPostModels;

    public SocialMediaPost_RecyclerViewAdapter(Context context, ArrayList<SocialMediaPostModel> socialMediaPostModels) {
        this.context = context;
        this.socialMediaPostModels = socialMediaPostModels;
    }

    @NonNull
    @Override
    public SocialMediaPost_RecyclerViewAdapter.SocialMediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.social_media_page_recycler_view_row,parent,false);
        return new SocialMediaPost_RecyclerViewAdapter.SocialMediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SocialMediaPost_RecyclerViewAdapter.SocialMediaViewHolder holder, int position) {
        holder.textViewUserName.setText(socialMediaPostModels.get(position).getUserName());
        holder.textViewPhotoDescription.setText(socialMediaPostModels.get(position).getPhotoDescription());
        holder.textViewHashtag.setText(socialMediaPostModels.get(position).getHashtag());
        holder.textViewLikes.setText(socialMediaPostModels.get(position).getNumberOfLikes() + " likes");
        holder.profileImageView.setImageResource(socialMediaPostModels.get(position).getProfilePhotoId());
        holder.placeImageView.setImageResource(socialMediaPostModels.get(position).getSharedPhotoId());
    }

    @Override
    public int getItemCount() {
        return socialMediaPostModels.size();
    }

    public static class SocialMediaViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImageView;
        ImageView placeImageView;
        TextView textViewUserName;
        TextView textViewPhotoDescription;
        TextView textViewHashtag;
        TextView textViewLikes;


        public SocialMediaViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImageView = itemView.findViewById(R.id.imageView);
            placeImageView = itemView.findViewById(R.id.imageView3);
            textViewUserName = itemView.findViewById(R.id.textView6);
            textViewPhotoDescription = itemView.findViewById(R.id.textView7);
            textViewHashtag = itemView.findViewById(R.id.textView8);
            textViewLikes = itemView.findViewById(R.id.textView9);

        }
    }
}
