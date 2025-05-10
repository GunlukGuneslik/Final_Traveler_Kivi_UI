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
import com.example.actualtravellerkiviprojectui.TourInformationPageChatFragment;
import com.example.actualtravellerkiviprojectui.model.Message;
import com.example.actualtravellerkiviprojectui.model.SocialMediaPostModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author zeynep
 */
public class Chat_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private ArrayList<Message> messages;
    private String currentUserId;
    private static final int RIGHT = 1;
    private static final int LEFT = 2;
    TourInformationPageChatFragment tourInformationPageChatFragment;

    public Chat_RecyclerViewAdapter(Context context, ArrayList<Message> messages, TourInformationPageChatFragment fragment, String id) {
        this.context = context;
        this.messages = messages;
        tourInformationPageChatFragment = fragment;
        this.currentUserId = id;
    }


    //I will update this part
    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        return 0;
        //return message.getSenderId().equals(currentUserId) ? TYPE_RIGHT : TYPE_LEFT;
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
        Message message = messages.get(position);
        //User user = message.getUserId();
        //String userName = user.getName();
        if (holder instanceof RightViewHolder) {
            //((RightViewHolder) holder).textUserName.setText(userName);
            ((RightViewHolder) holder).textMessage.setText(message.getText());
        } else {
            //((LeftViewHolder) holder).textUserName.setText(userName);
            ((LeftViewHolder) holder).textMessage.setText(message.getText());
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
            textMessage = itemView.findViewById(R.id.messageLeftUserName);
        }
    }


}

