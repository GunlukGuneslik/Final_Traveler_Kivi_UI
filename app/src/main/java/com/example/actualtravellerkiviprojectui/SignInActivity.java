package com.example.actualtravellerkiviprojectui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.io.IOException;

/**
 * @author: Eftelya
 */
public class SignInActivity extends AppCompatActivity {

    private EditText emailEt, passwordEt;
    private Button goToSignUpBtn;
    Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        goToSignUpBtn = findViewById(R.id.goToSignUpBtn);
        goToSignUpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });

        // View’lar
        emailEt = findViewById(R.id.emailEditText);
        passwordEt = findViewById(R.id.passwordEditText);
        signInBtn = findViewById(R.id.signInBtn);
        TextView forgotLink = findViewById(R.id.forgotPasswordTxt);

        // Sign-In

        signInBtn.setOnClickListener(v -> {
            String email = emailEt.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, R.string.signin_fill_all_info, Toast.LENGTH_SHORT).show();
                return;
            }

            UserService userService = ServiceLocator.getUserService();
            NetworkModule.toCompletableFuture(userService.getUserByEmail(email))
                    .thenCompose(user ->
                            NetworkModule.toCompletableFuture(userService.checkPassword(user.id, password))
                                    .thenApply(isValid -> new Pair<>(user, isValid))
                    )
                    .thenAccept(pair -> runOnUiThread(() -> {
                        if (Boolean.TRUE.equals(pair.second)) {
                            UserState.setUserId(pair.first.id);
                            startActivity(new Intent(SignInActivity.this, ApplicationPagesActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, R.string.signin_wrong_password, Toast.LENGTH_SHORT).show();
                        }
                    }))
                    .exceptionally(t -> {
                        runOnUiThread(() -> {
                            Throwable cause = t.getCause() != null ? t.getCause() : t;
                            if (cause instanceof IOException) {
                                Toast.makeText(SignInActivity.this, R.string.signin_user_not_found, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignInActivity.this, R.string.signin_server_error, Toast.LENGTH_SHORT).show();
                            }
                        });
                        return null;
                    });
        });

        // Forgot Password
        forgotLink.setOnClickListener(v -> startActivity(new Intent(this, RecoveryPasswordActivity.class)));
    }
}
