package com.example.actualtravellerkiviprojectui;


import static com.example.actualtravellerkiviprojectui.api.modules.NetworkModule.toCompletableFuture;

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
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.time.LocalDateTime;

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
        int tourId = getIntent().getIntExtra("tourId", -1);

        toCompletableFuture(eventService.getEvent(tourId))
                .thenAccept(currentTour -> {
                    runOnUiThread(() -> {
                        tourNameTextView.setText(currentTour.name);
                        tourLanguage.setText("Language: " + currentTour.language);
                        tourDate.setText(currentTour.startDate.toString());
                        tourRate.setText("Rate: " + currentTour.rating);

                        toCompletableFuture(userService.getUser(currentTour.ownerId))
                                .thenAccept(user -> {
                                    runOnUiThread(() -> guideName.setText(user.username));
                                    NetworkModule.setImageViewFromCall(guideImage, userService.getAvatar(user.id), null);
                                })
                                .exceptionally(e -> null);

                        if (UserState.getUserId().equals(currentTour.ownerId)) {
                            if (currentTour.startDate.isBefore(LocalDateTime.now())) {
                                editAndLaunchButton.setVisibility(View.VISIBLE);
                            } else {
                                editButton.setVisibility(View.VISIBLE);
                            }
                        } else {
                            toCompletableFuture(eventService.getAttendedEvents(UserState.getUserId()))
                                    .thenAccept(list -> runOnUiThread(() -> {
                                        if (list.contains(currentTour) &&
                                            currentTour.startDate.compareTo(LocalDateTime.now()) >=
                                            0) {
                                            removeFromMyToursButton.setVisibility(View.VISIBLE);
                                        } else {
                                            if (!list.contains(currentTour) &&
                                                currentTour.startDate.compareTo(LocalDateTime.now()) >=
                                                0) {
                                                addToMyToursButton.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }))
                                    .exceptionally(e -> null);
                        }
                        NetworkModule.setImageViewFromCall(tourImage, eventService.getPhoto(currentTour.id), null);
                    });
                })
                .exceptionally(t -> {
                    runOnUiThread(() -> {
                        t3.show();
                        finish();
                    });
                    return null;
                });

        // guide image
        guideImage = findViewById(R.id.guideImageTourInformationPage);
        // TODO: no images right now
        // guide name
        guideName = findViewById(R.id.guideNameTextViewTourInformationPage);


        buttonTourPlan = findViewById(R.id.button5);
        buttonMaps = findViewById(R.id.button6);
        buttonChat = findViewById(R.id.button7);
        buttonComments = findViewById(R.id.button8);



        addToMyToursButton = findViewById(R.id.button4);
        editButton = findViewById(R.id.button9);
        editAndLaunchButton = findViewById(R.id.button11);

        LocalDateTime currentDate = LocalDateTime.now();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourInformationPageActivity.this, EditTourActivity.class);
                intent.putExtra("tourId", tourId);
                startActivity(intent);
            }
        });

        editAndLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourInformationPageActivity.this, EditTourActivity.class);
                intent.putExtra("tourId", tourId);
                startActivity(intent);
            }
        });

        // Check if attended
        NetworkModule.toCompletableFuture(eventService.getEvent(tourId)).thenAccept(event -> {
            boolean isAttended = event.userIds.contains(UserState.getUserId());
            if (isAttended) {
                addToMyToursButton.setText("Remove from my tours");
                addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_24, 0, 0, 0);
                addToMyToursButton.setEnabled(true);
            } else {
                addToMyToursButton.setText("Add to my tours");
                addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_outline_24, 0, 0, 0);
                addToMyToursButton.setEnabled(true);
            }
            if (!isAttended) {
                buttonChat.setEnabled(false);
                buttonChat.setVisibility(View.GONE);
            } else {
                buttonChat.setEnabled(true);
                buttonChat.setVisibility(View.VISIBLE);
            }
        });

        addToMyToursButton.setOnClickListener(v -> {
            // Disable button during operation
            addToMyToursButton.setEnabled(false);

            // Use CompletableFuture instead of executing on the main thread
            NetworkModule.toCompletableFuture(eventService.getEvent(tourId))
                    .thenAccept(event -> {
                        boolean isAttended = event.userIds.contains(UserState.getUserId());

                        if (!isAttended) {
                            // User is not registered, so register them
                            NetworkModule.toCompletableFuture(eventService.registerEvent(tourId, UserState.getUserId()))
                                    .thenAccept(eventDTO -> {
                                        runOnUiThread(() -> {
                                            // Show "Remove" after successfully registering
                                            addToMyToursButton.setText("Remove from my tours");
                                            addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_24, 0, 0, 0);
                                            addToMyToursButton.setEnabled(true);
                                            Toast.makeText(TourInformationPageActivity.this, "Successfully registered for the tour", Toast.LENGTH_SHORT).show();
                                        });
                                    })
                                    .exceptionally(e -> {
                                        runOnUiThread(() -> {
                                            addToMyToursButton.setEnabled(true);
                                            Toast.makeText(TourInformationPageActivity.this, "Failed to register for the tour", Toast.LENGTH_SHORT).show();
                                        });
                                        return null;
                                    });
                        } else {
                            // User is registered, so unregister them
                            NetworkModule.toCompletableFuture(eventService.unregisterEvent(tourId, UserState.getUserId()))
                                    .thenAccept(eventDTO -> {
                                        runOnUiThread(() -> {
                                            // Show "Add" after successfully unregistering
                                            addToMyToursButton.setText("Add to my tours");
                                            addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_outline_24, 0, 0, 0);
                                            addToMyToursButton.setEnabled(true);
                                            Toast.makeText(TourInformationPageActivity.this, "Successfully unregistered from the tour", Toast.LENGTH_SHORT).show();
                                        });
                                    })
                                    .exceptionally(e -> {
                                        runOnUiThread(() -> {
                                            addToMyToursButton.setEnabled(true);
                                            Toast.makeText(TourInformationPageActivity.this, "Failed to unregister from the tour", Toast.LENGTH_SHORT).show();
                                        });
                                        return null;
                                    });
                        }
                    })
                    .exceptionally(e -> {
                        runOnUiThread(() -> {
                            addToMyToursButton.setEnabled(true);
                            Toast.makeText(TourInformationPageActivity.this, "Failed to get tour information", Toast.LENGTH_SHORT).show();
                        });
                        return null;
                    });
        });


        tourPlanFragment = new TourInformationPageTourPlanFragment();
        chatFragment = TourInformationPageChatFragment.newInstance(tourId);
        commentsFragment = TourInformationPageCommentsFragment.newInstance(tourId);
        mapsFragment = TourInformationPageMapsFragment.newInstance(tourId);

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


