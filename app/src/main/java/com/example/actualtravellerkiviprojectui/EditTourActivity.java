package com.example.actualtravellerkiviprojectui;

import static android.view.View.GONE;
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
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTourActivity extends AppCompatActivity {
    private static final UserService userService   = ServiceLocator.getUserService();
    private static final EventService eventService = ServiceLocator.getEventService();

    private UserDTO currentUser;
    private EventDTO currentTour;
    private LocalDateTime date;
    private String tourName;
     String tourDescription = "";
    private Uri selectedImageUri;
    private boolean isDateChanged  = false;
    private boolean isTimeChanged  = false;
    private boolean isImageChanged = false;

    ArrayList<EventLocationDTO> locationList;

    private EditText tourNameEditText;
    private Button returnButton;
    private Button selectDateButton;
    private Button selectTimeButton;
    private Button nextButton;
    private Button backButton;
    private Button saveButton;
    private ImageView tourImageView;

    private Fragment[] fragments;
    private int currentFragmentIndex = 0;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    tourImageView.setImageURI(selectedImageUri);
                    isImageChanged = true;
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tour);

        currentUser = UserState.getUser(userService);

        // 1) Load current tour
        int tourId = getIntent().getIntExtra("tourId", -1);
        try {
            currentTour = eventService.getEvent(tourId).execute().body();
        } catch (Exception e) {
            Log.e("EditTourActivity", "Tur yüklenemedi", e);
            Toast.makeText(this, "Tur bilgisi alınamadı", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (currentTour == null) {
            Toast.makeText(this, "Tur bulunamadı", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 2) Initialize data
        locationList = new ArrayList<>(currentTour.locations);
        date         = currentTour.startDate;

        // 3) Bind views
        returnButton     = findViewById(R.id.EditTourPageReturnButton);
        nextButton       = findViewById(R.id.EditTourPageNextButton);
        backButton       = findViewById(R.id.EditTourPageTurnButton);
        saveButton       = findViewById(R.id.EditTourPageSaveButton);
        selectDateButton = findViewById(R.id.EditTourPageSelectDateButton);
        selectTimeButton = findViewById(R.id.EditTourPageSelectTimeButton);
        tourNameEditText = findViewById(R.id.EditTourEnterTourNameTextView);
        tourImageView    = findViewById(R.id.EditTourImageView);

        // 4) Header actions
        returnButton.setOnClickListener(v -> finish());
        backButton.setOnClickListener(v -> {
            if (currentFragmentIndex > 0) {
                currentFragmentIndex--;
                switchFragment();
            }
        });
        nextButton.setOnClickListener(v -> {
            if (currentFragmentIndex < fragments.length - 1) {
                currentFragmentIndex++;
                switchFragment();
            }
        });

        // 5) Date & Time buttons
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        selectDateButton.setText(date.format(fmt));
        selectTimeButton.setText(
                String.format("%02d:%02d", date.getHour(), date.getMinute())
        );
        selectDateButton.setOnClickListener(v -> {
            openDatePicker();
            isDateChanged = true;
        });
        selectTimeButton.setOnClickListener(v -> {
            openTimePicker();
            isTimeChanged = true;
        });

        // 6) Image loader & picker
        NetworkModule.setImageViewFromCall(
                tourImageView,
                eventService.getPhoto(currentTour.id),
                null
        );
        tourImageView.setOnClickListener(v -> {
            Intent pick = new Intent(Intent.ACTION_PICK);
            pick.setType("image/*");
            imagePickerLauncher.launch(pick);
        });

        // 7) Fragments container
        fragments = new Fragment[] {
                new EditTourAddPlaceFragment(),
                new EditTourAddPlaceDescriptionFragment(),
                new EditTourNoteFragment()
        };
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutForEditTourPage, fragments[0])
                .commit();
        backButton.setVisibility(GONE);
        saveButton.setVisibility(INVISIBLE);

        // 8) Save button logic
        saveButton.setOnClickListener(v -> {
            tourName = tourNameEditText.getText().toString().trim();

            // Create DTO
            EventCreateDTO dto = new EventCreateDTO(
                    currentUser.id,
                    tourName,
                    tourDescription,
                    date,
                    /* photoUri= */ null,
                    /* popularity= */ 0,
                    /* rate= */ 0.0,
                    /* language= */ "English",
                    locationList
            );

            // Asynchronous update
            eventService.updateEvent(currentTour.id, dto)
                    .enqueue(new Callback<EventDTO>() {
                        @Override
                        public void onResponse(Call<EventDTO> call, Response<EventDTO> resp) {
                            if (resp.isSuccessful()) {
                                Toast.makeText(EditTourActivity.this,
                                        "Tur başarıyla güncellendi!",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EditTourActivity.this,
                                        "Sunucu hatası: " + resp.code(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<EventDTO> call, Throwable t) {
                            Toast.makeText(EditTourActivity.this,
                                    "Ağ hatası: " + t.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private void switchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutForEditTourPage, fragments[currentFragmentIndex])
                .commit();

        backButton.setVisibility(currentFragmentIndex > 0 ? View.VISIBLE : GONE);
        nextButton.setVisibility(
                currentFragmentIndex < fragments.length - 1 ? View.VISIBLE : GONE
        );
        saveButton.setVisibility(
                currentFragmentIndex == fragments.length - 1 ? View.VISIBLE : INVISIBLE
        );
    }

    private void openDatePicker() {
        new DatePickerDialog(
                this,
                (view, y, m, d) -> {
                    date = date.withYear(y).withMonth(m + 1).withDayOfMonth(d);
                    selectDateButton.setText(String.format("%02d/%02d/%04d", d, m + 1, y));
                },
                date.getYear(),
                date.getMonthValue() - 1,
                date.getDayOfMonth()
        ).show();
    }

    private void openTimePicker() {
        new TimePickerDialog(
                this,
                (view, h, min) -> {
                    date = date.withHour(h).withMinute(min);
                    selectTimeButton.setText(String.format("%02d:%02d", h, min));
                },
                date.getHour(),
                date.getMinute(),
                true
        ).show();
    }

    /** Called by fragments to pass back edited description */
    public void updateTourDescription(String desc) {
        this.tourDescription = desc;
    }
}
