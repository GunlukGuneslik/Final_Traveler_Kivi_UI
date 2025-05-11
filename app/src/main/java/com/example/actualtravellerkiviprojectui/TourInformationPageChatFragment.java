package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.actualtravellerkiviprojectui.adapter.Chat_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author zeynep
 * A simple {@link Fragment} subclass.
 * Use the {@link TourInformationPageChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TourInformationPageChatFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private EditText editTextMessage;
    private ImageButton buttonSendChat;
    private RecyclerView chatRecyclerView;

    private ArrayList<Message> messages = new ArrayList<>();
    private Chat_RecyclerViewAdapter adapter;
    private String currentUserId;

    public TourInformationPageChatFragment() {
        // Required empty public constructor
    }

    public static TourInformationPageChatFragment newInstance(String param1, String param2) {
        TourInformationPageChatFragment fragment = new TourInformationPageChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour_information_page_chat, container, false);

        editTextMessage = view.findViewById(R.id.editTextChat);
        buttonSendChat = view.findViewById(R.id.imageButtonSend);
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);

        messages = new ArrayList<>();
        adapter = new Chat_RecyclerViewAdapter(requireContext(),messages,this,currentUserId);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        chatRecyclerView.setAdapter(adapter);

        buttonSendChat.setOnClickListener(v -> {
            String text = editTextMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                //currentUserId daha yok
                //Message message = new Message(currentUserId, text, System.currentTimeMillis());
                //messages.add(message);

                //Collections.sort(messages, Comparator.comparingLong(Message::getTimestamp));

                //adapter.notifyItemInserted(messages.size() - 1);
                //chatRecyclerView.scrollToPosition(messages.size() - 1);
                editTextMessage.setText("");
            }
        });

        return view;
    }
}