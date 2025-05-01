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
