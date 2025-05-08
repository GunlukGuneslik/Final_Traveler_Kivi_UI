package com.example.actualtravellerkiviprojectui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.Account_Page_Posts_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;
import com.example.actualtravellerkiviprojectui.model.SocialMediaPostModel;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountPageFragment extends Fragment {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();

    ArrayList<SocialMediaPostModel> posts = new ArrayList<>();
    private RecyclerView recyclerView;
    private Account_Page_Posts_RecyclerViewAdapter adapter;

    private UserDTO currentUser;
    private int userProfilePhoto;
    private String userName;
    private ImageView profilePhoto;
    private TextView userProfileName;
    private Button settingsButton;
    private Button attendedToursButton;
    private Button upcomingToursButton;
    ActivityResultLauncher<Intent> resultLauncher;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountPageFragment newInstance(String param1, String param2) {
        AccountPageFragment fragment = new AccountPageFragment();
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

        currentUser = getCurrentUser();
        // TODO: This Won't work and use proper callback.
        try {
            //userProfilePhoto = Integer.valueOf(userService.getAvatar(currentUser.id).execute().body());
            userProfilePhoto = 4;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        userName = currentUser.firstName;

        resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        // TODO: burası kullanıcının fotoğrafını değiştirmiyor aslında!
                        Uri imageUri = result.getData().getData();
                        profilePhoto.setImageURI(imageUri);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "No image sellected",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_page, container, false);

        profilePhoto = view.findViewById(R.id.userProfilePhoto);
        // profilePhoto.setImageResource(userProfilePhoto);

        userProfileName = view.findViewById(R.id.userProfileNameTextView);
        userProfileName.setText(userName);

        settingsButton = view.findViewById(R.id.AccountPageSettingsButton);
        settingsButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getContext(), settingsButton);
            popup.getMenuInflater().inflate(R.menu.account_settings_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.ChangeTheAccountPhoto:
                        pickImage();
                        return true;
                    case R.id.ChangeName:
                        Toast.makeText(getContext(), "Change Name",Toast.LENGTH_SHORT).show();
                        //TODO
                        return true;
                    case R.id.ChangeLanguages:
                        Toast.makeText(getContext(), "Change Languages",Toast.LENGTH_SHORT).show();
                        //TODO
                        return true;
                }
                return false;
            });
            popup.show();
        });

        attendedToursButton = view.findViewById(R.id.AttendedToursButton);
        attendedToursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttendedToursActivity.class);
                startActivity(intent);
            }
        });

        upcomingToursButton = view.findViewById(R.id.UpcomingToursButton);
        upcomingToursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpcomingToursActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.AccountPagePostRecyclerView);
        //posts = currentUser.getPosts();
        fillSocialMediaPosts();

        adapter = new Account_Page_Posts_RecyclerViewAdapter(getContext(), posts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private void pickImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    //TODO: Complete this method. Will add callback and fail methods
    private UserDTO getCurrentUser() {
        try {
            return userService.getUser(1).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //TODO: complete this method so that it fills the array with user's posts.
    //TODO: currently for prototyping. use proper callback methods.
    private void fillSocialMediaPosts(){
        // TODO: should add a way to refresh the feed which will also act as a retry method on failed feed request.
        // TODO: should also retry the failed posts but idk how
        try {
            postService.fetchFeed(0, 1, 100, "").execute().body().content.forEach(post -> {
                try {
                    Log.i("request", "Post request.");
                    posts.add(SocialMediaPostModel.fromPostDTO(post));
                } catch (IOException e) {
                    //
                    String text = "Error fetching post.";
                    Log.w("retrofit", text);
                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                }
            });
        } catch (IOException e) {
            String text = "Error fetching feed.";
            Log.w("retrofit", text);
            Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        }
    }
}