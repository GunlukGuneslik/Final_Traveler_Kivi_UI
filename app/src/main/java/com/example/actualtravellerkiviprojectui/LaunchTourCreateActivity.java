package com.example.actualtravellerkiviprojectui;

import static android.view.View.INVISIBLE;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;

public class LaunchTourCreateActivity extends AppCompatActivity {

    public ArrayList<PlaceModel> placeModels;
    private EditText tourNameEditText;
    private Button returnButton;
    private Button selectDateButton;

    private Button lastButton;
    private Button nextButton;
    private Button launchTourButton;
    private int year; private int month; private int day; // these are going to be used for creating the tour object

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_tour_create);

        returnButton = findViewById(R.id.CreateNewTourPageReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lastButton = findViewById(R.id.CreateNewTourPageTurnButton);
        lastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: EFTELYA
            }
        });

        nextButton = findViewById(R.id.CreateNewTourPageNextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: EFTELYA
            }
        });

        launchTourButton = findViewById(R.id.CreateNewTourPageLaunchButton);
        launchTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: EFTELYA
                //TODO: yeni bir tur olarak kaydedecek guide userın turlarına ve database sevisine
            }
        });
        launchTourButton.setVisibility(INVISIBLE);

        selectDateButton = findViewById(R.id.CreateTourPageSelectDatePage);
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        placeModels = new ArrayList<>();

        CreateTourAddPlaceFragment fragment = new CreateTourAddPlaceFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutForCreateNewTourPage, fragment)
                .commit();

        tourNameEditText = findViewById(R.id.EnterTourNameTextView);
    }

    private void openDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        }, year, month + 1, day);

        dialog.show();
    }
}
