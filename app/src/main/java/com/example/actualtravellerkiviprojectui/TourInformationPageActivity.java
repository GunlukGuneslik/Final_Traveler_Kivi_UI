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

import java.text.SimpleDateFormat;
import java.util.Locale;

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
        currentTour = getIntent().getParcelableExtra("tour");

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
                addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_outline_24, 0, 0, 0);
            } else {
                addToMyToursButton.setText("Remove from my tours");
                addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_24, 0, 0, 0);
            }
        });

        //test ediyorum
        if (currentTour == null) {
            // Hata mesajı göster veya kullanıcıyı bir hata sayfasına yönlendir.
            Toast.makeText(this, "Error: Tour information not available.", Toast.LENGTH_SHORT).show();
            finish(); // Eğer tour yoksa sayfayı kapatabilirsin.
        }
        else {
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
            String formattedDate = currentTour.startDate.toString();
            tourDate.setText(formattedDate);
            //tour rate
            tourRate = findViewById(R.id.tourRateTourInformationPage);
            tourRate.setText("Rate: " + currentTour.rating);

            userService.getUser(currentTour.ownerId).enqueue(new Callback<UserDTO>() {
                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                    guide = response.body();
                }

                // TODO handle it
                @Override
                public void onFailure(Call<UserDTO> call, Throwable throwable) {
                    finish();
                }
            });

            // test ediyorum
            if (guide == null) {
                Toast.makeText(this, "Error: Guide null.", Toast.LENGTH_SHORT).show();
                finish(); // Eğer tour yoksa sayfayı kapatabilirsin.
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

            //chat butonu işlevsiz eğer kullanıcı kayıtlı değilse
            //if(!currentUser.getTours().contain(currentTour)){
            //    buttonChat.setEnabled(false);
            //}
            //else{
            //    buttonChat.setEnabled(true);
            //}

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
    }

    private void openFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_tour_information, fragment)
                .commit();
    }
}