package com.example.actualtravellerkiviprojectui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

/**
 * @author zeynep
 */

public class TourInformationPageActivity extends AppCompatActivity {

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