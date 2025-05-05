package com.example.actualtravellerkiviprojectui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.actualtravellerkiviprojectui.dto.UserDTO;
import com.example.actualtravellerkiviprojectui.model.Tour;

/**
 * @author zeynep
 */

public class TourInformationPageActivity extends AppCompatActivity {

    private Tour currentTour;
    private TextView tourNameTextView;
    private TextView tourLanguage;
    private TextView tourDate;
    private TextView toruRate;
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
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
        currentTour = (Tour) getIntent().getSerializableExtra("tour");
        //Tour name
        tourNameTextView.findViewById(R.id.tourNameTextViewTourInformationPage);
        tourNameTextView.setText(currentTour.getTourName());
        //Tour image
        tourImage.findViewById(R.id.TourImageTourInformationPage);
        tourImage.setImageResource(currentTour.getTourImage());
        // Tour language
        tourLanguage.findViewById(R.id.tourLanguageTextViewTourInfoPage);
        tourLanguage.setText(currentTour.getTourLanguage());
        // date
        tourDate.findViewById(R.id.TourDateTourInformationPage);
        //TODO: fix this
        tourDate.setText("" + currentTour.getDate());

        guide = (UserDTO) getIntent().getSerializableExtra("guide");
        // guide image
        guideImage.findViewById(R.id.guideImageTourInformationPage);
        guideImage.setImageResource(guide.getImage());
        // guide name
        guideName.findViewById(R.id.guideNameTextViewTourInformationPage);
        guideName.setText(guide.getUserName());


        buttonTourPlan = findViewById(R.id.button5);
        buttonMaps= findViewById(R.id.button6);
        buttonChat = findViewById(R.id.button7);
        buttonComments = findViewById(R.id.button8);

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

    private void openFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_tour_information, fragment)
                .commit();
    }
}