package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ApplicationPagesActivity extends AppCompatActivity {

    private Fragment mapFragment;
    private Fragment socialMediaFragment;
    private Fragment searchTourFragment;
    private Fragment profileFragment;
    private Fragment activeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationpages);

        // our navigation bar to acces in the code
        BottomNavigationView bottomNavigationBar = findViewById(R.id.navigatorBar);

        mapFragment = new MapPageFragment();
        socialMediaFragment = new SocialMediaFragment();
        searchTourFragment = new SearchTourPageFragment();
        profileFragment = new AccountPageFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, profileFragment, "ProfilePage").hide(profileFragment)
                .add(R.id.frameLayout, searchTourFragment, "searchTourPage").hide(searchTourFragment)
                .add(R.id.frameLayout, mapFragment, "mapPage").hide(mapFragment)
                .add(R.id.frameLayout, socialMediaFragment, "socialMediaPage") // This is the first to be shown
                .commit();
        // for first opening
        activeFragment = socialMediaFragment;


        // handels the selection from the navigation bar
        bottomNavigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.map:
                    //@ Güneş
                    changeFragment(mapFragment);
                    break;
                case R.id.socialMedia:
                    changeFragment(socialMediaFragment);
                    break;
                case R.id.searchTour:
                    changeFragment(searchTourFragment);
                    break;
                case R.id.profile:
                    // @ Güneş
                    changeFragment(profileFragment);
                    break;
            }
            return true;
        });
    }

    private void changeFragment(Fragment targetFragment) {
        if (activeFragment != targetFragment) {
            getSupportFragmentManager().beginTransaction()
                    .hide(activeFragment)
                    .show(targetFragment)
                    .commit();
            activeFragment = targetFragment;
        }
    }
}
