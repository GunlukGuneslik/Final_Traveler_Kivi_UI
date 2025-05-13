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

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.example.actualtravellerkiviprojectui.dto.Event.EventCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author zeynep
 */

public class EditTourActivity extends AppCompatActivity {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();

    UserDTO currentUser;
    public EventDTO currentTour;
    public String tourDescription;
    public String tourName;
    public ArrayList<EventLocationDTO> locationList;

    private EditText tourNameEditText;
    private Button returnButton;
    private Button selectDateButton;
    private Button selectTimeButton;
    private LocalDate date;
    private Uri TourImageUri;
    private Button nextButton, backButton, saveButton;
    private ImageView tourImageView;
    private int currentFragmentIndex = 0;
    private Fragment[] fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tour);

        currentUser = UserState.getUser(userService);

        //current tour info pagede böyle almışız ondan böyle yazdım.
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
            }
        });

        //TODO: datei yaptığımız gibi saatide burada göstereceğiz
        selectTimeButton = findViewById(R.id.SelectTimeButton);
        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeDialog();
            }
        });

        tourNameEditText = findViewById(R.id.EditTourEnterTourNameTextView);

        saveButton.setOnClickListener(v -> {
            if (!placeModels.isEmpty()) {
                String tourName = (tourNameEditText).getText().toString().trim();
                String desc = getTourDescription();
                ArrayList<EventLocationCreateDTO> places = getSelectedPlaces();
                LocalDate tourDate = LocalDate.of(year, month, day);
                //TODO: EFTELYA
                String language = "English";
                int popularity = 0;
                double rate = 0;
                UserDTO guide = new UserDTO(); // Test kullanıcısı
                ArrayList<String> comments = new ArrayList<>();

                EventCreateDTO createdTour = new EventCreateDTO(
                        currentUser.id,
                        tourName,
                        desc,
                        tourDate,
                        null,
                        popularity,
                        rate,
                        language,
                        places
                );

                Toast.makeText(this, "Tur değiştirildi: " + createdTour.name, Toast.LENGTH_SHORT).show();
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

        if (currentFragmentIndex == fragments.length - 1 && !placeModels.isEmpty()) {
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

    public ArrayList<EventLocationCreateDTO> getSelectedPlaces() {
        return placeModels;
    }

    private void openDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDayOfMonth;
                selectDateButton.setText(selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear);
            }
        }, year, month, day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

    private void openTimeDialog() {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int SelectedHour, int selectedMinute) {
                hour = SelectedHour;
                minute = selectedMinute;
            }
        }, 12, 00, true);
        dialog.show();
    }
}