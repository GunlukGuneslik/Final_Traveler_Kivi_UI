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
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.model.SocialMediaPostModel;

import java.util.ArrayList;

/**
 * @author Güneş
 */
public class Account_Page_Posts_RecyclerViewAdapter extends RecyclerView.Adapter<Account_Page_Posts_RecyclerViewAdapter.postViewHolder> {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();

    Context context;
    ArrayList<SocialMediaPostModel> posts;

    public Account_Page_Posts_RecyclerViewAdapter(Context context, ArrayList<SocialMediaPostModel> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public Account_Page_Posts_RecyclerViewAdapter.postViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.account_page_posts_recycler_view_row, parent, false);
        return new Account_Page_Posts_RecyclerViewAdapter.postViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Account_Page_Posts_RecyclerViewAdapter.postViewHolder holder, int position) {
        holder.hastag.setText(String.join(", ", posts.get(position).getHashtags()));
        holder.sharedDate.setText(posts.get(position).getSharedDate());

        // ! Important example of setting ImageView objects
        NetworkModule.setImageViewFromCall(holder.imageView, postService.getPhoto(posts.get(position).postId), null);

        holder.description.setText(posts.get(position).getPhotoDescription());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class postViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView hastag;
        TextView description;
        TextView sharedDate;

        public postViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.postImageView);
            hastag = itemView.findViewById(R.id.postPlaceHastags);
            description = itemView.findViewById(R.id.postDescription);
            sharedDate = itemView.findViewById(R.id.PostSharedDate);
        }
    }
}
