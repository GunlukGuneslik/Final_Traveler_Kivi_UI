package com.example.actualtravellerkiviprojectui.adapter;

import static com.example.actualtravellerkiviprojectui.api.modules.NetworkModule.toCompletableFuture;

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
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.Post.PostDTO;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.util.List;
import java.util.Locale;

/**
 * @author zeynep
 */
public class SocialMediaPost_RecyclerViewAdapter extends RecyclerView.Adapter<SocialMediaPost_RecyclerViewAdapter.SocialMediaViewHolder> {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();

    Context context;
    List<PostDTO> socialMediaPostModels;
    SocialMediaFragment socialMediaFragment;

    public SocialMediaPost_RecyclerViewAdapter(Context context, List<PostDTO> socialMediaPostModels, SocialMediaFragment fragment) {
        this.context = context;
        this.socialMediaPostModels = socialMediaPostModels;
        socialMediaFragment = fragment;
    }

    public void setFilteredList(List<PostDTO> filteredList) {
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
        PostDTO post = socialMediaPostModels.get(position);
        toCompletableFuture(userService.getUser(post.userId))
                .thenCompose(owner -> toCompletableFuture(postService.likers(post.postId))
                        .thenAccept(likers -> runOnUiThread(() -> {
                            holder.textViewUserName.setText(owner.username);
                            holder.textViewPhotoDescription.setText(post.body);
                            holder.textViewHashtag.setText(post.tags.get(0));
                            holder.textViewLikes.setText(post.likeCount + " likes");
                            NetworkModule.setImageViewFromCall(holder.profileImageView, userService.getAvatar(owner.id), null);
                            NetworkModule.setImageViewFromCall(holder.placeImageView, postService.getPhoto(post.postId), null);
                            holder.filledHeartButton.setVisibility(
                                    likers.contains(UserState.getUserId())
                                    ? View.VISIBLE : View.GONE);
                            holder.heartButton.setVisibility(likers.contains(UserState.getUserId())
                                                             ? View.GONE : View.VISIBLE);
                        }))
                )
                .exceptionally(e -> null);

        holder.heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.heartButton.setImageResource(R.drawable.filledheart);
                if(!holder.isClicked){
                    socialMediaPostModels.get(holder.getAdapterPosition()).likeCount++;
                    holder.textViewLikes.setText(String.format(Locale.ENGLISH, "%d likes", post.likeCount));
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
        ImageButton filledHeartButton;
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
            filledHeartButton = itemView.findViewById(R.id.imageButton12);

        }
    }
}
