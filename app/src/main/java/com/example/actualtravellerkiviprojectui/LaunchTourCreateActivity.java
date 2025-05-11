//@author:Eftelya
package com.example.actualtravellerkiviprojectui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.model.Tour;

import java.util.ArrayList;
import java.util.Date;

public class LaunchTourCreateActivity extends AppCompatActivity {

    public ArrayList<PlaceModel> placeModels = new ArrayList<>();
    private String tourDescription = "";
    private Uri selectedImageUri;

    private Button nextButton, backButton, launchButton;
    private ImageView tourImageView;

    private int currentFragmentIndex = 0;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_tour_create);

        nextButton = findViewById(R.id.CreateNewTourPageNextButton);
        backButton = findViewById(R.id.CreateNewTourPageTurnButton);
        launchButton = findViewById(R.id.CreateNewTourPageLaunchButton);
        tourImageView = findViewById(R.id.imageView2);

        backButton.setVisibility(View.GONE);
        launchButton.setVisibility(View.GONE);

        fragments = new Fragment[]{
                new CreateTourAddPlaceFragment(),
                new CreateTourAddPlaceDescriptionFragment()
        };

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutForCreateNewTourPage, fragments[currentFragmentIndex])
                .commit();

        nextButton.setOnClickListener(v -> {
            if (currentFragmentIndex < fragments.length - 1) {
                currentFragmentIndex++;
                switchFragment();
            }
        });

        backButton.setOnClickListener(v -> {
            if (currentFragmentIndex > 0) {
                currentFragmentIndex--;
                switchFragment();
            }
        });

        launchButton.setOnClickListener(v -> {
            if (!placeModels.isEmpty()) {
                String tourName = ((EditText) findViewById(R.id.EnterTourNameTextView)).getText().toString().trim();
                String desc = getTourDescription();
                ArrayList<PlaceModel> places = getSelectedPlaces();
                Date tourDate = new Date();
                String language = "English";
                int popularity = 0;
                double rate = 5.0;
                UserDTO guide = new UserDTO(); // Test kullanıcısı
                ArrayList<String> comments = new ArrayList<>();

                Tour createdTour = new Tour(
                        tourName,
                        tourDate,
                        rate,
                        popularity,
                        language,
                        places,
                        guide,
                        selectedImageUri,
                        desc,
                        comments
                );

                Toast.makeText(this, "Tur oluşturuldu: " + createdTour.getTourName(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        tourImageView.setOnClickListener(v -> selectTourImage());
    }

    private void switchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutForCreateNewTourPage, fragments[currentFragmentIndex])
                .commit();

        backButton.setVisibility(currentFragmentIndex > 0 ? View.VISIBLE : View.GONE);

        if (currentFragmentIndex == fragments.length - 1 && !placeModels.isEmpty()) {
            nextButton.setVisibility(View.GONE);
            launchButton.setVisibility(View.VISIBLE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
            launchButton.setVisibility(View.GONE);
        }
    }

    private void selectTourImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    tourImageView.setImageURI(selectedImageUri);
                }
            });

    public void updateTourDescription(String description) {
        this.tourDescription = description;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public ArrayList<PlaceModel> getSelectedPlaces() {
        return placeModels;
    }
}
