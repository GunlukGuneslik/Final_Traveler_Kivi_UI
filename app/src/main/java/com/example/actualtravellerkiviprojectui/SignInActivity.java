package com.example.actualtravellerkiviprojectui;





import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author: Eftelya
 */
public class SignInActivity extends AppCompatActivity {

    private EditText emailEt, passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // View’lar
        emailEt    = findViewById(R.id.emailEditText);
        passwordEt = findViewById(R.id.passwordEditText);
        Button   signInBtn   = findViewById(R.id.signInBtn);
        Button   signUpBtn   = findViewById(R.id.goToSignUpBtn);
        TextView forgotLink  = findViewById(R.id.forgotPasswordTxt);

        // Sign-In

        signInBtn.setOnClickListener(v -> {
            String email = emailEt.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show();
                return;
            }

            com.example.actualtravellerkiviprojectui.api.UserService userService =
                    com.example.actualtravellerkiviprojectui.api.ServiceLocator.getUserService();

            userService.getAllUsers().enqueue(new retrofit2.Callback<java.util.List<com.example.actualtravellerkiviprojectui.dto.User.UserDTO>>() {
                @Override
                public void onResponse(retrofit2.Call<java.util.List<com.example.actualtravellerkiviprojectui.dto.User.UserDTO>> call,
                                       retrofit2.Response<java.util.List<com.example.actualtravellerkiviprojectui.dto.User.UserDTO>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        for (com.example.actualtravellerkiviprojectui.dto.User.UserDTO user : response.body()) {
                            if (user.email.equals(email)) {
                                userService.checkPassword(user.id, password).enqueue(new retrofit2.Callback<Boolean>() {
                                    @Override
                                    public void onResponse(retrofit2.Call<Boolean> call, retrofit2.Response<Boolean> response) {
                                        if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                                            startActivity(new Intent(SignInActivity.this, ApplicationPagesActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(SignInActivity.this, "Şifre yanlış", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(retrofit2.Call<Boolean> call, Throwable t) {
                                        Toast.makeText(SignInActivity.this, "Sunucu hatası", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                        }
                        Toast.makeText(SignInActivity.this, "Email bulunamadı", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignInActivity.this, "Kullanıcılar alınamadı", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<java.util.List<com.example.actualtravellerkiviprojectui.dto.User.UserDTO>> call, Throwable t) {
                    Toast.makeText(SignInActivity.this, "Sunucu hatası", Toast.LENGTH_SHORT).show();
                }
            });

            if (emailEt.getText().toString().isEmpty() ||
                    passwordEt.getText().toString().isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldur", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Demo: giriş başarılı!", Toast.LENGTH_SHORT).show();
            }
        });

        // Sign-Up (şimdilik sadece Toast)
        signUpBtn.setOnClickListener(v ->
                Toast.makeText(this,"Sign-Up ekranı henüz yok",Toast.LENGTH_SHORT).show());

        // Forgot Password
        forgotLink.setOnClickListener(v ->
                startActivity(new Intent(this, RecoveryPasswordActivity.class)));
    }
}
