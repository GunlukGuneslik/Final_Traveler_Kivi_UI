package com.example.actualtravellerkiviprojectui;

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
                Toast.makeText(this, "Email alanı boş olamaz", Toast.LENGTH_SHORT).show();
                return;
            }

            com.example.actualtravellerkiviprojectui.api.UserService userService =
                    com.example.actualtravellerkiviprojectui.api.ServiceLocator.getUserService();

            userService.getAllUsers().enqueue(new retrofit2.Callback<java.util.List<com.example.actualtravellerkiviprojectui.dto.User.UserDTO>>() {
                @Override
                public void onResponse(retrofit2.Call<java.util.List<com.example.actualtravellerkiviprojectui.dto.User.UserDTO>> call,
                                       retrofit2.Response<java.util.List<com.example.actualtravellerkiviprojectui.dto.User.UserDTO>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        boolean found = false;
                        for (com.example.actualtravellerkiviprojectui.dto.User.UserDTO user : response.body()) {
                            if (user.email.equals(email)) {
                                found = true;
                                Toast.makeText(RecoveryPasswordActivity.this, "Reset e-mail gönderildi (simülasyon).", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        if (!found) {
                            Toast.makeText(RecoveryPasswordActivity.this, "Bu e-posta ile eşleşen kullanıcı yok", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RecoveryPasswordActivity.this, "Sunucu hatası: kullanıcılar alınamadı", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<java.util.List<com.example.actualtravellerkiviprojectui.dto.User.UserDTO>> call, Throwable t) {
                    Toast.makeText(RecoveryPasswordActivity.this, "Sunucu hatası", Toast.LENGTH_SHORT).show();
                }
            });

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

