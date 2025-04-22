package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ApplicationPagesActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationpages);

        // our navigation bar to acces in the code
        BottomNavigationView bottomNavigationBar = findViewById(R.id.navigatorBar);

        // for first opening
        replaceFragment(new SocialMediaFragment());

        // handels the selection from the navigation bar
        bottomNavigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.map:
                    Toast.makeText(ApplicationPagesActivity.this, "map", Toast.LENGTH_SHORT).show();
                    replaceFragment(new MapPageFragment());
                    break;
                case R.id.socialMedia:
                    Toast.makeText(ApplicationPagesActivity.this, "Social media", Toast.LENGTH_SHORT).show();
                    replaceFragment(new SocialMediaFragment());
                    break;
                case R.id.searchTour:
                    replaceFragment(new SearchTourPageFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new AccountPageFragment());
                    break;
            }


            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
