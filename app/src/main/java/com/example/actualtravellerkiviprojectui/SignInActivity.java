package com.example.actualtravellerkiviprojectui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.state.UserState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        goToSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
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

            userService.getUserByEmail(email).enqueue(new Callback<UserDTO>() {
  @Override
  public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
      UserDTO user = response.body();
      if (!response.isSuccessful() && user == null) {
          Toast.makeText(SignInActivity.this, R.string.signin_user_not_found, Toast.LENGTH_SHORT).show();
      }
      userService.checkPassword(user.id, password).enqueue(new retrofit2.Callback<Boolean>() {
          @Override
          public void onResponse(retrofit2.Call<Boolean> call, retrofit2.Response<Boolean> response) {
              if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                  UserState.setUserId(user.id);
                  startActivity(new Intent(SignInActivity.this, ApplicationPagesActivity.class));
                  finish();
              } else {
                  Toast.makeText(SignInActivity.this, R.string.signin_wrong_password, Toast.LENGTH_SHORT).show();
              }
          }

          @Override
          public void onFailure(retrofit2.Call<Boolean> call, Throwable t) {
              Toast.makeText(SignInActivity.this, R.string.signin_server_error, Toast.LENGTH_SHORT).show();
          }
      });
  }

  @Override
  public void onFailure(Call<UserDTO> call, Throwable throwable) {
      Toast.makeText(SignInActivity.this, R.string.signin_server_error, Toast.LENGTH_SHORT).show();

  }
}

            );


            if (emailEt.getText().toString().isEmpty() ||
                passwordEt.getText().toString().isEmpty()) {
                Toast.makeText(this, R.string.signin_fill_all_fields, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.signin_demo_success, Toast.LENGTH_SHORT).show();
            }

        });

        // Forgot Password
        forgotLink.setOnClickListener(v -> startActivity(new Intent(this, RecoveryPasswordActivity.class)));
    }
}
