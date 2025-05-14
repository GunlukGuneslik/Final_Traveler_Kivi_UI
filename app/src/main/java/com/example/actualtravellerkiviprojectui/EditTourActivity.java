package com.example.actualtravellerkiviprojectui;

import static android.view.View.INVISIBLE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.model.Tour;
import com.example.actualtravellerkiviprojectui.state.UserState;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Response;

/**
 * @author zeynep
 */

public class EditTourActivity extends AppCompatActivity {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();

    public EventDTO currentTour;
    public EventCreateDTO currentTourr;

    private LocalDateTime date;
    public String tourDescription;
    public String tourName;
    private String newName;
    private LocalDate newDate;
    private int newHour, newMinute;
    private Uri selectedImageUri;
    private String newDescription;
    private boolean isNameChanged = false;
    private boolean isDateChanged = false;
    private boolean isTimeChanged = false;
    private boolean isImageChanged = false;
    private boolean isDescriptionChanged = false;
    private boolean isPlacesChanged = false;


    private EditText tourNameEditText;
    private EditText tourDescriptionText;
    private EditText tourNotesText;
    private Button returnButton;
    private Button selectDateButton;
    private Button selectTimeButton;
    private Uri TourImageUri;
    private Button nextButton, backButton, saveButton;
    private ImageView tourImageView;
    private int currentFragmentIndex = 0;
    private Fragment[] fragments;
    private String name ;

    ArrayList<EventLocationDTO> locationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tour);


        //current tour info pagede böyle almışız ondan böyle yazdım.
        try {
            currentTour = eventService.getEvent(getIntent().getIntExtra("tourId", -1)).execute().body();
            locationList = new ArrayList<>(currentTour.locations);
        } catch (Exception e) {
            Log.w("event", "");
        }

        //test ediyorum
        if (currentTour == null) {
            // Hata mesajı göster veya kullanıcıyı bir hata sayfasına yönlendir.
            Toast.makeText(this, R.string.toast_tour_load_error, Toast.LENGTH_SHORT).show();
            finish(); // Eğer tour yoksa sayfayı kapatabilirsin.
            return;
        }

        date = currentTour.startDate;

        returnButton = findViewById(R.id.EditTourPageReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextButton = findViewById(R.id.EditTourPageNextButton);
        backButton = findViewById(R.id.EditTourPageTurnButton);
        saveButton = findViewById(R.id.EditTourPageSaveButton);
        saveButton.setVisibility(INVISIBLE);

        tourImageView = findViewById(R.id.EditTourImageView);
        NetworkModule.setImageViewFromCall(tourImageView,eventService.getPhoto(currentTour.id),null);

        backButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);

        // Initialize fragments: Place -> Descriptions -> Notes
        fragments = new Fragment[]{
                new EditTourAddPlaceFragment(),
                new EditTourAddPlaceDescriptionFragment(),
                new EditTourNoteFragment()  // Add Notes fragment
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

        selectDateButton = findViewById(R.id.EditTourPageSelectDateButton);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        selectDateButton.setText("Change: " + date.format(formatter));
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                isDateChanged= true;
            }
        });

        //TODO: datei yaptığımız gibi saatide burada göstereceğiz
        selectTimeButton = findViewById(R.id.EditTourPageSelectTimeButton);
        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeDialog();
                isTimeChanged = true;
            }
        });

        tourNameEditText = findViewById(R.id.EditTourEnterTourNameTextView);

        saveButton.setOnClickListener(v -> {
            if (!locationList.isEmpty()) {
                if(tourNameEditText!= null)
                {
                    isNameChanged=true;
                   tourName = (tourNameEditText).getText().toString().trim();
                }
                if(tourDescriptionText!= null)
                {
                    isDescriptionChanged=true;
                    tourDescription = (tourNameEditText).getText().toString().trim();
                }
                int year   = date.getYear();
                int month  = date.getMonthValue();
                int day    = date.getDayOfMonth();
                int hour   = date.getHour();
                int minute = date.getMinute();
                if(isDateChanged)
                {
                    year  = date.getYear();
                    month = date.getMonthValue();
                    day   = date.getDayOfMonth();
                }
                if(isTimeChanged)
                {
                    hour   = date.getHour();
                    minute = date.getMinute();
                }


                try {
                    Response<EventDTO> resp = eventService
                            .updateEvent(currentTour.id, currentTourr)
                            .execute();
                    if (resp.isSuccessful()) {
                        Toast.makeText(this, R.string.toast_tour_updated, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.toast_recovery_failure + resp.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(this, R.string.NetworkError  + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                // ------------------------------------------------------

                // 6) Activity’yi kapat
                finish();
            }
        });

        tourImageView.setOnClickListener(v -> selectTourImage());
    }

    private void switchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutForEditTourPage, fragments[currentFragmentIndex])
                .commit();

        backButton.setVisibility(currentFragmentIndex > 0 ? View.VISIBLE : View.GONE);

        if (currentFragmentIndex == fragments.length - 1 && !locationList.isEmpty()) {
            nextButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.GONE);
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

    public ArrayList<EventLocationDTO> getSelectedPlaces() {
        return locationList;
    }

    private void openDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                date = LocalDateTime.of(
                        selectedYear,
                        selectedMonth + 1,
                        selectedDayOfMonth,
                        date.getHour(),
                        date.getMinute()
                );
                selectDateButton.setText(selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear);
            }
        }, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

    private void openTimeDialog() {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                date = LocalDateTime.of(
                        date.getYear(),
                        date.getMonth(),
                        date.getDayOfMonth(),
                        selectedHour,
                        selectedMinute
                );
                selectTimeButton.setText(selectedHour + ":" + selectedMinute);
            }
        }, 12, 00, true);
        dialog.show();
    }
}