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
import android.widget.EditText;
import android.widget.ImageView;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * @author zeynep
 */
public class EditTourActivity extends AppCompatActivity {
    private static final String TAG = "EditTourActivity";
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    //tourImageView.setImageURI(selectedImageUri);
                    isImageChanged = true;
                }
            }
    );
    private EventDTO currentTour;

    private LocalDateTime date;
    private EventCreateDTO updatedTour;
    private String tourDescription;
    private String tourName;

    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
    }

    public EventDTO getCurrentTour() {
        return currentTour;
    }

    public void setCurrentTour(EventDTO currentTour) {
        this.currentTour = currentTour;
    }

    public EventCreateDTO getUpdatedTour() {
        return updatedTour;
    }

    public void setUpdatedTour(EventCreateDTO updatedTour) {
        this.updatedTour = updatedTour;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public Uri getSelectedImageUri() {
        return selectedImageUri;
    }

    public void setSelectedImageUri(Uri selectedImageUri) {
        this.selectedImageUri = selectedImageUri;
    }

    public boolean isNameChanged() {
        return isNameChanged;
    }

    public void setNameChanged(boolean nameChanged) {
        isNameChanged = nameChanged;
    }

    public boolean isDateChanged() {
        return isDateChanged;
    }

    public void setDateChanged(boolean dateChanged) {
        isDateChanged = dateChanged;
    }

    public boolean isTimeChanged() {
        return isTimeChanged;
    }

    public void setTimeChanged(boolean timeChanged) {
        isTimeChanged = timeChanged;
    }

    public boolean isImageChanged() {
        return isImageChanged;
    }

    public void setImageChanged(boolean imageChanged) {
        isImageChanged = imageChanged;
    }

    public boolean isDescriptionChanged() {
        return isDescriptionChanged;
    }

    public void setDescriptionChanged(boolean descriptionChanged) {
        isDescriptionChanged = descriptionChanged;
    }

    public boolean isPlacesChanged() {
        return isPlacesChanged;
    }

    public void setPlacesChanged(boolean placesChanged) {
        isPlacesChanged = placesChanged;
    }

    public EditText getTourNameEditText() {
        return tourNameEditText;
    }

    public void setTourNameEditText(EditText tourNameEditText) {
        this.tourNameEditText = tourNameEditText;
    }

    public EditText getTourDescriptionText() {
        return tourDescriptionText;
    }

    public void setTourDescriptionText(EditText tourDescriptionText) {
        this.tourDescriptionText = tourDescriptionText;
    }

    public EditText getTourNotesText() {
        return tourNotesText;
    }

    public void setTourNotesText(EditText tourNotesText) {
        this.tourNotesText = tourNotesText;
    }

    public Button getReturnButton() {
        return returnButton;
    }

    public void setReturnButton(Button returnButton) {
        this.returnButton = returnButton;
    }

    public Button getSelectDateButton() {
        return selectDateButton;
    }

    public void setSelectDateButton(Button selectDateButton) {
        this.selectDateButton = selectDateButton;
    }

    public Button getSelectTimeButton() {
        return selectTimeButton;
    }

    public void setSelectTimeButton(Button selectTimeButton) {
        this.selectTimeButton = selectTimeButton;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public void setNextButton(Button nextButton) {
        this.nextButton = nextButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void setBackButton(Button backButton) {
        this.backButton = backButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public ImageView getTourImageView() {
        return tourImageView;
    }

    public void setTourImageView(ImageView tourImageView) {
        this.tourImageView = tourImageView;
    }

    public int getCurrentFragmentIndex() {
        return currentFragmentIndex;
    }

    public void setCurrentFragmentIndex(int currentFragmentIndex) {
        this.currentFragmentIndex = currentFragmentIndex;
    }

    public Fragment[] getFragments() {
        return fragments;
    }

    public void setFragments(Fragment[] fragments) {
        this.fragments = fragments;
    }

    public ArrayList<EventLocationDTO> getLocationList() {
        return locationList;
    }

    public void setLocationList(ArrayList<EventLocationDTO> locationList) {
        this.locationList = locationList;
    }
    private Uri selectedImageUri;
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
    private Button nextButton, backButton, saveButton;
    private ImageView tourImageView;
    private int currentFragmentIndex = 0;
    private Fragment[] fragments;

    ArrayList<EventLocationDTO> locationList;

    public ActivityResultLauncher<Intent> getImagePickerLauncher() {
        return imagePickerLauncher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tour);

        // Initialize UI components first
        initializeUIComponents();

        int tourId = -1;
        // Get tourId from intent
        try {
            tourId = getIntent().getIntExtra("tourId", -1);
        } catch (Exception e) {
            tourId = getIntent().getIntExtra("placeId", -1);
        }

        if (tourId == -1) {
            Toast.makeText(this, R.string.toast_tour_load_error, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load tour data
        loadTourData(tourId);
    }

    private void initializeUIComponents() {
        returnButton = findViewById(R.id.EditTourPageReturnButton);
        nextButton = findViewById(R.id.EditTourPageNextButton);
        backButton = findViewById(R.id.EditTourPageTurnButton);
        saveButton = findViewById(R.id.EditTourPageSaveButton);
        tourImageView = findViewById(R.id.EditTourImageView);
        tourNameEditText = findViewById(R.id.EditTourEnterTourNameTextView);
        selectDateButton = findViewById(R.id.EditTourPageSelectDateButton);
        selectTimeButton = findViewById(R.id.EditTourPageSelectTimeButton);

        // Set initial visibility
        saveButton.setVisibility(INVISIBLE);
        backButton.setVisibility(View.GONE);

        // Initialize fragments
        fragments = new Fragment[]{
                new EditTourAddPlaceFragment(),
                new EditTourAddPlaceDescriptionFragment(),
                new EditTourNoteFragment()
        };

        // Setup click listeners
        returnButton.setOnClickListener(v -> finish());

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

        selectDateButton.setOnClickListener(v -> {
            openDialog();
            isDateChanged = true;
        });

        selectTimeButton.setOnClickListener(v -> {
            openTimeDialog();
            isTimeChanged = true;
        });

        tourImageView.setOnClickListener(v -> selectTourImage());
    }

    private void loadTourData(int tourId) {
        CompletableFuture<EventDTO> tourFuture = NetworkModule.toCompletableFuture(eventService.getEvent(tourId));

        tourFuture.thenAccept(eventDTO -> {
            runOnUiThread(() -> {
                if (eventDTO == null) {
                    Toast.makeText(this, R.string.toast_tour_load_error, Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                // Store fetched tour
                currentTour = eventDTO;

                // Initialize updatedTour
                updatedTour = new EventCreateDTO();
                updatedTour.name = currentTour.name;
                updatedTour.details = currentTour.details;
                updatedTour.locations = new ArrayList<>(currentTour.locations);
                updatedTour.startDate = currentTour.startDate;

                // Initialize locationList
                locationList = new ArrayList<>(currentTour.locations);

                // Set UI data
                tourNameEditText.setText(currentTour.name);
                tourName = currentTour.name;
                tourDescription = currentTour.details;
                date = currentTour.startDate;

                // Set date button text
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                selectDateButton.setText("Change: " + date.format(formatter));

                // Set time button text
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                selectTimeButton.setText("Change: " + date.format(timeFormatter));

                // Load image
                NetworkModule.setImageViewFromCall(tourImageView, eventService.getPhoto(currentTour.id), null);

                // Setup save button
                setupSaveButton();

                // Load first fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutForEditTourPage, fragments[currentFragmentIndex])
                        .commit();
            });
        }).exceptionally(throwable -> {
            Log.e(TAG, "Error loading tour", throwable);
            runOnUiThread(() -> {
                Toast.makeText(this, R.string.toast_tour_load_error, Toast.LENGTH_SHORT).show();
                finish();
            });
            return null;
        });
    }

    private void setupSaveButton() {
        saveButton.setOnClickListener(v -> {
            if (locationList == null || locationList.isEmpty()) {
                Toast.makeText(this, "R.string.toast_no_locations", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get updated values
            if (tourNameEditText != null) {
                String newName = tourNameEditText.getText().toString().trim();
                if (!newName.equals(currentTour.name)) {
                    isNameChanged = true;
                    updatedTour.name = newName;
                }
            }

            if (tourDescriptionText != null) {
                String newDesc = tourDescriptionText.getText().toString().trim();
                if (!newDesc.equals(currentTour.details)) {
                    isDescriptionChanged = true;
                    updatedTour.details = newDesc;
                }
            }

            // Update date/time if changed
            if (isDateChanged || isTimeChanged) {
                updatedTour.startDate = date;
            }

            // Update locations if changed
            if (isPlacesChanged) {
                updatedTour.locations = locationList;
            }

            // Update tour
            updateTour();
        });
    }

    private void updateTour() {
        CompletableFuture<EventDTO> updateFuture = NetworkModule.toCompletableFuture(
                eventService.updateEvent(currentTour.id, updatedTour));

        updateFuture.thenAccept(eventDTO -> {
            runOnUiThread(() -> {
                Toast.makeText(this, R.string.toast_tour_updated, Toast.LENGTH_SHORT).show();

                // If image changed, upload it
                if (isImageChanged && selectedImageUri != null) {
                    uploadTourImage(currentTour.id);
                } else {
                    finish();
                }
            });
        }).exceptionally(throwable -> {
            Log.e(TAG, "Error updating tour", throwable);
            runOnUiThread(() -> {
                Toast.makeText(this, "Error updating tour", Toast.LENGTH_SHORT).show();
            });
            return null;
        });
    }

    private void uploadTourImage(int tourId) {
        NetworkModule.uploadImage(
                this,
                selectedImageUri,
                filePart -> eventService.setPhoto(tourId, filePart),
                success -> {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "R.string.toast_image_updated", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                },
                error -> {
                    Log.e(TAG, "Error uploading image", error);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "R.string.toast_image_upload_error", Toast.LENGTH_SHORT).show();
                        finish();
                    });
            }
        );
    }

    private void selectTourImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private void switchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutForCreateNewTourPage, fragments[currentFragmentIndex])
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

    public void updateTourDescription(String description) {
        this.tourDescription = description;
        this.updatedTour.details = description;
        isDescriptionChanged = true;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public ArrayList<EventLocationDTO> getSelectedPlaces() {
        return locationList;
    }

    public void setSelectedPlaces(ArrayList<EventLocationDTO> places) {
        this.locationList = places;
        this.updatedTour.locations = places;
        isPlacesChanged = true;
    }

    private void openDialog() {
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                date = LocalDateTime.of(
                        selectedYear,
                        selectedMonth + 1,
                        selectedDayOfMonth,
                        date.getHour(),
                        date.getMinute()
                );
                    selectDateButton.setText(
                            selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear
                    );
                },
                date.getYear(),
                date.getMonthValue() - 1,
                date.getDayOfMonth()
        );
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

    private void openTimeDialog() {
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                (view, selectedHour, selectedMinute) -> {
                date = LocalDateTime.of(
                        date.getYear(),
                        date.getMonth(),
                        date.getDayOfMonth(),
                        selectedHour,
                        selectedMinute
                );
                selectTimeButton.setText(selectedHour + ":" + selectedMinute);
                },
                date.getHour(),
                date.getMinute(),
                true
        );
        dialog.show();
    }
}
