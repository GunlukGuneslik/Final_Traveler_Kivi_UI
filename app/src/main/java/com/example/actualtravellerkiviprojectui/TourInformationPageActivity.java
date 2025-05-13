package com.example.actualtravellerkiviprojectui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author zeynep
 */

public class TourInformationPageActivity extends AppCompatActivity {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();

    private TextView tourNameTextView;
    private TextView tourLanguage;
    private TextView tourDate;
    private TextView tourRate;
    private ImageView tourImage;
    private TextView guideName;
    private ImageView guideImage;

    Button buttonTourPlan, buttonMaps, buttonChat, buttonComments;

    Button addToMyToursButton, removeFromMyToursButton, editButton, editAndLaunchButton;

    private Fragment mapsFragment;
    private Fragment tourPlanFragment;
    private Fragment chatFragment;
    private Fragment commentsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tour_information_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.TourInformationPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button returnToTourSearchPageButton = findViewById(R.id.returnButtonTourInfo);

        returnToTourSearchPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TourInformationPageActivity.this, ApplicationPagesActivity.class);
                startActivity(intent);
            }
        });
        //Tour name
        tourNameTextView = findViewById(R.id.tourNameTextViewTourInformationPage);
        //Tour image
        tourImage = findViewById(R.id.TourImageTourInformationPage);
        // Tour language
        tourLanguage = findViewById(R.id.tourLanguageTextViewTourInfoPage);
        // date
        tourDate = findViewById(R.id.TourDateTourInformationPage);
        //tour rate
        tourRate = findViewById(R.id.tourRateTourInformationPage);
        Toast t3 = Toast.makeText(this, "Error: Tour information not available.", Toast.LENGTH_SHORT);
        eventService.getEvent(getIntent().getIntExtra("tourId", -1)).enqueue(new Callback<EventDTO>() {
            @Override
            public void onResponse(Call<EventDTO> call, Response<EventDTO> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }
                EventDTO currentTour = response.body();
                tourNameTextView.setText(currentTour.name);
                tourLanguage.setText("Language: " + currentTour.language);
                tourDate.setText(currentTour.startDate.toString());
                tourRate.setText("Rate: " + currentTour.rating);
                if (UserState.getUserId().equals(currentTour.ownerId)) {
                    if (currentTour.startDate.isBefore(LocalDateTime.now())) {
                        editAndLaunchButton.setVisibility(View.VISIBLE);
                    } else {
                        editButton.setVisibility(View.VISIBLE);
                    }
                } else {
                    eventService.getAttendedEvents(UserState.getUserId()).enqueue(new Callback<List<EventDTO>>() {
                        @Override
                        public void onResponse(Call<List<EventDTO>> call, Response<List<EventDTO>> response) {
                            if (!response.isSuccessful()) {
                                return;
                            }
                            var body = response.body();
                            if (body.contains(currentTour) &&
                                currentTour.startDate.compareTo(LocalDateTime.now()) >= 0) {
                                removeFromMyToursButton.setVisibility(View.VISIBLE);
                            } else {
                                if (!body.contains(currentTour) &&
                                    currentTour.startDate.compareTo(LocalDateTime.now()) >= 0) {
                                    addToMyToursButton.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<EventDTO>> call, Throwable throwable) {

                        }
                    });

                }
                NetworkModule.setImageViewFromCall(tourImage, eventService.getPhoto(currentTour.id), null);

            }

            @Override
            public void onFailure(Call<EventDTO> call, Throwable throwable) {
                t3.show();
                finish(); // Eğer tour yoksa sayfayı kapatabilirsin.
            }
        });


        // guide image
        guideImage = findViewById(R.id.guideImageTourInformationPage);
        // TODO: no images right now
        // guide name
        guideName = findViewById(R.id.guideNameTextViewTourInformationPage);

        //guideName.setText(guide.firstName);


        buttonTourPlan = findViewById(R.id.button5);
        buttonMaps = findViewById(R.id.button6);
        buttonChat = findViewById(R.id.button7);
        buttonComments = findViewById(R.id.button8);

        Toast t = Toast.makeText(this, "Error getting the events", Toast.LENGTH_SHORT);
        Toast t2 = Toast.makeText(this, "You are not registered to the tour.", Toast.LENGTH_SHORT);
        UserDTO currentUser;

        eventService.getAttendedEvents(UserState.getUserId()).enqueue(new Callback<List<EventDTO>>() {
            @Override
            public void onResponse(Call<List<EventDTO>> call, Response<List<EventDTO>> response) {
                // TODO:
                if (response.body().size() == 0) {
                    buttonChat.setEnabled(false);
                    t2.show();
                    buttonChat.setVisibility(View.GONE);
                } else {
                    buttonChat.setEnabled(true);
                    buttonChat.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<List<EventDTO>> call, Throwable throwable) {
                t.show();
            }
        });


        addToMyToursButton = findViewById(R.id.button4);
        removeFromMyToursButton = findViewById(R.id.button10);
        editButton = findViewById(R.id.button9);
        editAndLaunchButton = findViewById(R.id.button11);

        LocalDateTime currentDate = LocalDateTime.now();


        //TODO
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourInformationPageActivity.this, EditTourActivity.class);
                startActivity(intent);
            }
        });

        editAndLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourInformationPageActivity.this, EditTourActivity.class);
                startActivity(intent);
            }
        });

        //eğer tarih geçmişsse button yok olmalı
        //if(currentTour.getDate() != null && tourDate.before(currentDate)) {
        //     addToMyToursButton.setVisibility(View.GONE);
        //}
        addToMyToursButton.setOnClickListener(v -> {
            boolean isAdded = addToMyToursButton.getText().equals("Remove from my tours");
            //current user turlarına eklenmeli burada
            if (isAdded) {
                addToMyToursButton.setText("Add to my tours");
                try {
                    eventService.registerEvent(getIntent().getIntExtra("tourId", -1), UserState.getUserId()).execute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_outline_24, 0, 0, 0);
            } else {
                addToMyToursButton.setText("Remove from my tours");
//                try {
//                    eventService.getAttendedEvents(UserState.getUserId()).execute().body().remove(currentTour);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
                addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_24, 0, 0, 0);
            }
        });

        removeFromMyToursButton.setOnClickListener(v -> {
            boolean isClicked = removeFromMyToursButton.getText().equals("Remove from my tours");
            //current user turlarına eklenmeli burada
            if (isClicked) {
                removeFromMyToursButton.setText("Add to my tours");
                try {
                    eventService.registerEvent(getIntent().getIntExtra("tourId", -1), UserState.getUserId()).execute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                removeFromMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_outline_24, 0, 0, 0);
            } else {
                removeFromMyToursButton.setText("Remove from my tours");
//                try {
//                    eventService.getAttendedEvents(UserState.getUserId()).execute().body().remove(currentTour);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
                removeFromMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_24, 0, 0, 0);
            }
        });

        tourPlanFragment = new TourInformationPageTourPlanFragment();
        chatFragment = new TourInformationPageChatFragment();
        commentsFragment = new TourInformationPageCommentsFragment();
        mapsFragment = new TourInformationPageMapsFragment();

        openFragment(tourPlanFragment);

        buttonTourPlan.setOnClickListener(v -> openFragment(tourPlanFragment));
        buttonMaps.setOnClickListener(v -> openFragment(mapsFragment));
        buttonChat.setOnClickListener(v -> openFragment(chatFragment));
        buttonComments.setOnClickListener(v -> openFragment(commentsFragment));
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_tour_information, fragment).commit();
    }


}
