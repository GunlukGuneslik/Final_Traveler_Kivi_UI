package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.SocialMediaFragment;
import com.example.actualtravellerkiviprojectui.model.SocialMediaPostModel;

import java.util.List;
import java.util.Locale;

/**
 * @author zeynep
 */
public class SocialMediaPost_RecyclerViewAdapter extends RecyclerView.Adapter<SocialMediaPost_RecyclerViewAdapter.SocialMediaViewHolder> {
    Context context;
    List<SocialMediaPostModel> socialMediaPostModels;
    SocialMediaFragment socialMediaFragment;

    public SocialMediaPost_RecyclerViewAdapter(Context context, List<SocialMediaPostModel> socialMediaPostModels, SocialMediaFragment fragment) {
        this.context = context;
        this.socialMediaPostModels = socialMediaPostModels;
        socialMediaFragment = fragment;
    }

    public void setFilteredList(List<SocialMediaPostModel> filteredList) {
        this.socialMediaPostModels = filteredList;
        notifyDataSetChanged();
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
        SocialMediaPostModel socialMediaPostModel = socialMediaPostModels.get(position);
        holder.textViewUserName.setText(socialMediaPostModel.getUserName());
        holder.textViewPhotoDescription.setText(socialMediaPostModel.getPhotoDescription());
        holder.textViewHashtag.setText(socialMediaPostModel.getHashtag());
        holder.textViewLikes.setText(socialMediaPostModel.getNumberOfLikes() + " likes");
        holder.profileImageView.setImageResource(socialMediaPostModel.getProfilePhotoId());
        holder.placeImageView.setImageResource(socialMediaPostModel.getSharedPhotoId());
        holder.heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.isClicked){
                    socialMediaPostModels.get(holder.getAdapterPosition()).setNumberOfLikes(socialMediaPostModels.get(holder.getAdapterPosition()).getNumberOfLikes()+1);
                    holder.heartButton.setImageResource(R.drawable.filledheart);
                    holder.textViewLikes.setText(String.format(Locale.ENGLISH, "%d likes", socialMediaPostModel.getNumberOfLikes()));
                    holder.isClicked = true;
                }
            }
        });
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
        ImageButton heartButton;
        boolean isClicked = false;

        public SocialMediaViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImageView = itemView.findViewById(R.id.imageView);
            placeImageView = itemView.findViewById(R.id.imageView3);
            textViewUserName = itemView.findViewById(R.id.textView6);
            textViewPhotoDescription = itemView.findViewById(R.id.textView7);
            textViewHashtag = itemView.findViewById(R.id.textView8);
            textViewLikes = itemView.findViewById(R.id.textView9);
            heartButton = itemView.findViewById(R.id.imageButton2);

        }
    }
}
