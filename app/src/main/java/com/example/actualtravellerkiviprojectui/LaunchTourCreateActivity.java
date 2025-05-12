//@author:Eftelya
package com.example.actualtravellerkiviprojectui;

import static android.view.View.INVISIBLE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.dto.Event.EventCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventDTO;
import com.example.actualtravellerkiviprojectui.dto.Event.EventLocationCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.model.Tour;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LaunchTourCreateActivity extends AppCompatActivity {
    private static final UserService userService = ServiceLocator.getUserService();
    UserDTO currentUser;
    public ArrayList<EventLocationCreateDTO> placeModels = new ArrayList<>();
    private EditText tourNameEditText;
    private Button returnButton;
    private Button selectDateButton;
    private Button selectTimeButton;
    Calendar calendar = Calendar.getInstance();

    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH);
    private int day = calendar.get(Calendar.DAY_OF_MONTH); // these are going to be used for creating the tour object
    private int hour;
    private int minute;
    public String tourDescription = "";
    private Uri selectedImageUri;
    private Button nextButton, backButton, launchButton;
    private ImageView tourImageView;
    private int currentFragmentIndex = 0;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_tour_create);

        currentUser = UserState.getUser(userService);

        returnButton = findViewById(R.id.CreateNewTourPageReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextButton = findViewById(R.id.CreateNewTourPageNextButton);
        backButton = findViewById(R.id.CreateNewTourPageTurnButton);
        launchButton = findViewById(R.id.CreateNewTourPageLaunchButton);
        launchButton.setVisibility(INVISIBLE);
        tourImageView = findViewById(R.id.tourImageView);

        // Initial visibility
        backButton.setVisibility(View.GONE);
        launchButton.setVisibility(View.GONE);

        // Initialize fragments: Place -> Descriptions -> Notes
        fragments = new Fragment[]{
                new CreateTourAddPlaceFragment(),
                new CreateTourAddPlaceDescriptionFragment(),
                new CreateTourAddTourNoteFragment()  // Add Notes fragment
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

        selectDateButton = findViewById(R.id.CreateTourPageSelectDatePage);
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        selectTimeButton = findViewById(R.id.SelectTimeButton);
        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeDialog();
            }
        });


        tourNameEditText = findViewById(R.id.EnterTourNameTextView);

        launchButton.setOnClickListener(v -> {
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

                Toast.makeText(this, "Tur oluşturuldu: " + createdTour.name, Toast.LENGTH_SHORT).show();
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
