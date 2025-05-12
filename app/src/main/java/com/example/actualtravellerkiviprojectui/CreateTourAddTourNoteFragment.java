package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateTourAddTourNoteFragment extends Fragment {
    private EditText tourNoteEditText;
    private LaunchTourCreateActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // grab a reference to your host Activity so we can get/set the tourDescription
        activity = (LaunchTourCreateActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the XML you pasted (make sure its filename matches below)
        View view = inflater.inflate(
                R.layout.fragment_create_tour_page_add_notes_to_tour,  // ← adjust if your file is named differently
                container,
                false
        );

        // EDITTEXT: be sure your XML has:
        //   <EditText
        //       android:id="@+id/tourNoteEditText"
        //       … />
        tourNoteEditText = view.findViewById(R.id.tourNoteEditText);

        // Pre-populate with any existing note
        if (activity != null) {
            tourNoteEditText.setText(activity.getTourDescription());
        }

        // Sync every change back to the activity
        tourNoteEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (activity != null) {
                    activity.updateTourDescription(s.toString().trim());
                }
            }
        });

        return view;
    }
}
