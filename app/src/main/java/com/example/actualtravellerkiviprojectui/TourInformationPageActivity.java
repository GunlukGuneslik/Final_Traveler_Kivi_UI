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

import com.example.actualtravellerkiviprojectui.dto.UserDTO;
import com.example.actualtravellerkiviprojectui.model.Tour;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * @author zeynep
 */

public class TourInformationPageActivity extends AppCompatActivity {

    private Tour currentTour;
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

        currentTour = getIntent().getParcelableExtra("tour");

        Button addToMyToursButton = findViewById(R.id.addToMyToursButton);
        LocalDate currentDate = LocalDate.now();
        Date today = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        //eğer tarih geçmişsse button yok olmalı
        if(currentTour.getDate() != null && currentTour.getDate().before(today)) {
             addToMyToursButton.setVisibility(View.GONE);
        }
        addToMyToursButton.setOnClickListener(v -> {
            boolean isAdded = addToMyToursButton.getText().equals("Remove from my tours");
            //current user turlarına eklenmeli burada
            if (isAdded) {
                addToMyToursButton.setText("Add to my tours");
                //currentUser.getTours().add(currentTour);
                addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_outline_24, 0, 0, 0);
            } else {
                addToMyToursButton.setText("Remove from my tours");
                //currentUser.getTours().remove(currentTour);
                addToMyToursButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_add_circle_24, 0, 0, 0);
            }
        });


        //test ediyorum
        if (currentTour == null) {
            // Hata mesajı göster veya kullanıcıyı bir hata sayfasına yönlendir.
            Toast.makeText(this, "Error: Tour information not available.", Toast.LENGTH_SHORT).show();
            finish(); // Eğer tour yoksa sayfayı kapatabilirsin.
        }

        //Tour name
        tourNameTextView = findViewById(R.id.tourNameTextViewTourInformationPage);
        tourNameTextView.setText(currentTour.getTourName());
        //Tour image
        tourImage = findViewById(R.id.TourImageTourInformationPage);
        tourImage.setImageResource(currentTour.getTourImage());
        // Tour language
        tourLanguage = findViewById(R.id.tourLanguageTextViewTourInfoPage);
        tourLanguage.setText("Language: " + currentTour.getTourLanguage());
        // date
        tourDate = findViewById(R.id.TourDateTourInformationPage);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedTourDate = dateFormat.format(currentTour.getDate());
        tourDate.setText(formattedTourDate);
        //tour rate
        tourRate = findViewById(R.id.tourRateTourInformationPage);
        tourRate.setText("Rate: " + currentTour.getRate());

        guide = currentTour.getGuide();

        // test ediyorum
        if (guide == null) {
            Toast.makeText(this, "Error: Guied null.", Toast.LENGTH_SHORT).show();
            finish(); // Eğer tour yoksa sayfayı kapatabilirsin.
        }

        // guide image
        guideImage = findViewById(R.id.guideImageTourInformationPage);
        // TODO: no images right now
        //guideImage.setImageResource(guide.());
        // guide name
        guideName = findViewById(R.id.guideNameTextViewTourInformationPage);
        guideName.setText(guide.firstName);


        buttonTourPlan = findViewById(R.id.button5);
        buttonMaps= findViewById(R.id.button6);
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

    private void openFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_tour_information, fragment)
                .commit();
    }
}