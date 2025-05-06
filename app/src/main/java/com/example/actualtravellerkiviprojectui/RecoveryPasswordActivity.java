package com.example.actualtravellerkiviprojectui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * @author Eftelya
 */


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author:Eftelya
 */
public class RecoveryPasswordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);

        EditText emailEt = findViewById(R.id.resetEmailEt);
        Button   sendBtn = findViewById(R.id.sendResetBtn);

        sendBtn.setOnClickListener(v -> {
            String email = emailEt.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(this,"Email boş olamaz",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Demo: reset linki gidecek → " + email,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}

