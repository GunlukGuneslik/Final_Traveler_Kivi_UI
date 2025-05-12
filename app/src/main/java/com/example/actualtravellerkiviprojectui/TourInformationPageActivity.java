package com.example.actualtravellerkiviprojectui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

/**
 * @author zeynep
 */

public class TourInformationPageActivity extends AppCompatActivity {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();

    private EventDTO currentTour;
    private TextView tourNameTextView;
    private TextView tourLanguage;
    private TextView tourDate;
    private TextView tourRate;
    private ImageView tourImage;
    private UserDTO guide;
    private TextView guideName;
    private ImageView guideImage;

    Button buttonTourPlan, buttonMaps, buttonChat, buttonComments;

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

        //@author Güneş

        try {
            currentTour = eventService.getEvent(getIntent().getIntExtra("tourId", -1)).execute().body();
        } catch (Exception e) {
            Log.w("event", "");
        }

        //test ediyorum
        if (currentTour == null) {
            // Hata mesajı göster veya kullanıcıyı bir hata sayfasına yönlendir.
            Toast.makeText(this, "Error: Tour information not available.", Toast.LENGTH_SHORT).show();
            finish(); // Eğer tour yoksa sayfayı kapatabilirsin.
            return;
        }

        //Tour name
        tourNameTextView = findViewById(R.id.tourNameTextViewTourInformationPage);
        tourNameTextView.setText(currentTour.name);
        //Tour image
        tourImage = findViewById(R.id.TourImageTourInformationPage);
        NetworkModule.setImageViewFromCall(tourImage, eventService.getPhoto(currentTour.id), null);
        // Tour language
        tourLanguage = findViewById(R.id.tourLanguageTextViewTourInfoPage);
        tourLanguage.setText("Language: " + currentTour.language);
        // date
        tourDate = findViewById(R.id.TourDateTourInformationPage);
        tourDate.setText(currentTour.startDate.toString());
        //tour rate
        tourRate = findViewById(R.id.tourRateTourInformationPage);
        tourRate.setText("Rate: " + currentTour.rating);

        try {
            guide = userService.getUser(currentTour.ownerId).execute().body();
        } catch (IOException e) {
            Toast.makeText(this, "Error: Couldn't get the guide.", Toast.LENGTH_SHORT).show();
            finish(); // Eğer tour yoksa sayfayı kapatabilirsin.
            return;
        }


        // guide image
        guideImage = findViewById(R.id.guideImageTourInformationPage);
        // TODO: no images right now
        NetworkModule.setImageViewFromCall(guideImage, userService.getAvatar(guide.id), null);
        // guide name
        guideName = findViewById(R.id.guideNameTextViewTourInformationPage);
        guideName.setText(guide.firstName);


        buttonTourPlan = findViewById(R.id.button5);
        buttonMaps = findViewById(R.id.button6);
        buttonChat = findViewById(R.id.button7);
        buttonComments = findViewById(R.id.button8);
        UserDTO currentUser;
        try {
            currentUser = UserState.getUser(userService);
        } catch (Exception e) {
            finish();
            return;
        }
        //chat butonu işlevsiz eğer kullanıcı kayıtlı değilse
        try {
            if (!eventService.getAttendedEvents(currentUser.id).execute().body().contains(currentTour)) {
                buttonChat.setEnabled(false);
            } else {
                buttonChat.setEnabled(true);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error getting the events", Toast.LENGTH_SHORT).show();
        }

        Button addToMyToursButton = findViewById(R.id.button4);
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
                    eventService.getAttendedEvents(currentUser.id).execute().body().add(currentTour);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_outline_24, 0, 0, 0);
            } else {
                addToMyToursButton.setText("Remove from my tours");
                try {
                    eventService.getAttendedEvents(currentUser.id).execute().body().remove(currentTour);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_24, 0, 0, 0);
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
