package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.TourInformationPageCommentsFragment;

import java.util.ArrayList;

/**
 * @author zeynep
 */

public class Comments_RecyclerViewAdapter extends RecyclerView.Adapter<Comments_RecyclerViewAdapter.CommentsViewHolder>{
    private ArrayList<String> comments;
    private Context context;
    private TourInformationPageCommentsFragment tourInformationPageCommentsFragment;

    public Comments_RecyclerViewAdapter(Context context, ArrayList<String> comments, TourInformationPageCommentsFragment fragment){
        this.comments = comments;
        this.context = context;
        tourInformationPageCommentsFragment = fragment;
    }
    @NonNull
    @Override
    public Comments_RecyclerViewAdapter.CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comments_recycler_view_row,parent,false);
        return new Comments_RecyclerViewAdapter.CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Comments_RecyclerViewAdapter.CommentsViewHolder holder, int position) {
        holder.textItem.setText(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        TextView textItem;

        public CommentsViewHolder(View itemView) {
            super(itemView);
            textItem = itemView.findViewById(R.id.Comment);
        }
    }
}
