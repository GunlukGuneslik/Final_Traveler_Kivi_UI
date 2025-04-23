package com.example.actualtravellerkiviprojectui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Zeynep
 */
public class WelcomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_page);

        Button buttonWelcomeSignIn = findViewById(R.id.button2);

        //I will update some minor things according to new activities.
        buttonWelcomeSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< Updated upstream
                Intent intent = new Intent(WelcomePageActivity.this, LogInActivity.class);//open sign in activity
=======
                Intent intent = new Intent(WelcomePageActivity.this, CreateAccountActivity.class);//sign in activity
>>>>>>> Stashed changes
                startActivity(intent);
            }
        });

        Button buttonWelcomeCreateAccount = findViewById(R.id.button3);
        buttonWelcomeCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< Updated upstream
                Intent intent = new Intent(WelcomePageActivity.this, LogInActivity.class);//open create account activity
=======
                Intent intent = new Intent(WelcomePageActivity.this, CreateAccountActivity.class);//create account activity
>>>>>>> Stashed changes
                startActivity(intent);
            }
        });
    }
}