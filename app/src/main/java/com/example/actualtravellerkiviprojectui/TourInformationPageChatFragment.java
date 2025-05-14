package com.example.actualtravellerkiviprojectui;

import static com.example.actualtravellerkiviprojectui.api.modules.NetworkModule.toCompletableFuture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.Chat_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCommentCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCommentDTO;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.util.ArrayList;

/**
 * @author zeynep
 * A simple {@link Fragment} subclass.
 * Use the {@link TourInformationPageChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TourInformationPageChatFragment extends Fragment {
    private static final String ARG_TOUR_ID = "tourId";
    private final EventService eventService = ServiceLocator.getEventService();
    private final ArrayList<EventCommentDTO> messages = new ArrayList<>();

    private EditText editTextMessage;
    private ImageButton buttonSendChat;
    private RecyclerView chatRecyclerView;
    private Integer tourId;
    private Chat_RecyclerViewAdapter adapter;
    private String currentUserId;

    public TourInformationPageChatFragment() {
        // Required empty public constructor
    }

    public static TourInformationPageChatFragment newInstance(Integer tourId) {
        TourInformationPageChatFragment fragment = new TourInformationPageChatFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TOUR_ID, tourId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tourId = getArguments().getInt(ARG_TOUR_ID);
        }
        toCompletableFuture(eventService.getEventChatComments(tourId))
                .thenAccept(msgs -> requireActivity().runOnUiThread(() -> {
                    messages.clear();
                    messages.addAll(msgs);
                    adapter.notifyDataSetChanged();
                }))
                .exceptionally(e -> {
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Couldn't get chat messages.", Toast.LENGTH_SHORT).show()
                    );
                    return null;
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour_information_page_chat, container, false);

        editTextMessage = view.findViewById(R.id.editTextChat);
        buttonSendChat = view.findViewById(R.id.imageButtonSend);
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);

        adapter = new Chat_RecyclerViewAdapter(requireContext(), messages, this);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        chatRecyclerView.setAdapter(adapter);

        buttonSendChat.setOnClickListener(v -> {
            String text = editTextMessage.getText().toString().trim();
            if (text.isEmpty()) return;
            EventCommentCreateDTO dto = new EventCommentCreateDTO(UserState.getUserId(), text);
            toCompletableFuture(eventService.postEventChatComment(tourId, dto))
                    .thenAccept(c -> requireActivity().runOnUiThread(() -> {
                        messages.add(c);
                        adapter.notifyItemInserted(messages.size() - 1);
                        chatRecyclerView.scrollToPosition(messages.size() - 1);
                        editTextMessage.setText("");
                    }))
                    .exceptionally(e -> {
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Couldn't send message.", Toast.LENGTH_SHORT).show()
                        );
                        return null;
                });
        });

        return view;
    }
}
