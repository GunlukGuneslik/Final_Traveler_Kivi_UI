package com.example.actualtravellerkiviprojectui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.TourInformationPageChatFragment;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCommentDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.util.List;

/**
 * @author zeynep
 */
public class Chat_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();
    private List<EventCommentDTO> messages;
    private int currentUserId;
    private static final int RIGHT = 1;
    private static final int LEFT = 2;
    TourInformationPageChatFragment tourInformationPageChatFragment;

    public Chat_RecyclerViewAdapter(Context context, List<EventCommentDTO> messages, TourInformationPageChatFragment fragment) {
        this.context = context;
        this.messages = messages;
        tourInformationPageChatFragment = fragment;
        currentUserId = UserState.getUserId();
    }


    //I will update this part
    @Override
    public int getItemViewType(int position) {
        EventCommentDTO message = messages.get(position);
        if (message.ownerId.equals(currentUserId)) {
            return RIGHT;
        } else {
            return LEFT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == RIGHT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tour_information_page_chat_recycler_view_row_right, parent, false);
            return new RightViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tour_information_page_chat_recycler_view_row_left, parent, false);
            return new LeftViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EventCommentDTO message = messages.get(position);
        if (holder instanceof RightViewHolder) {
            NetworkModule.toCompletableFuture(userService.getUser(message.ownerId)).thenAccept(a->{
                ((RightViewHolder) holder).textUserName.setText(a.username);
            });
            ((RightViewHolder) holder).textMessage.setText(message.commentBody);
        } else {
            NetworkModule.toCompletableFuture(userService.getUser(message.ownerId)).thenAccept(a->{
                ((LeftViewHolder) holder).textUserName.setText(a.username);
            });
            ((LeftViewHolder) holder).textMessage.setText(message.commentBody);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }



    public class RightViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage;
        TextView textUserName;

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.right_chat_text_view);
            textUserName = itemView.findViewById(R.id.messageRightUserName);
        }
    }

    public class LeftViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage;
        TextView textUserName;

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.left_chat_text_view);
            textUserName = itemView.findViewById(R.id.messageLeftUserName);
        }
    }


}

