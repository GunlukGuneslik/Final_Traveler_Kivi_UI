package com.example.actualtravellerkiviprojectui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.User.UserCreateUpdateDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.io.IOException;

/**
 * @author Güneş
 */
public class CreateAccountActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private UserDTO.UserType userType;
    private RadioButton guideUserButton;
    private RadioButton travelerUserButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_new_account);

        // Initialize RadioGroup and RadioButtons
        radioGroup = findViewById(R.id.userTypeRadioGroup);
        guideUserButton = findViewById(R.id.GuideRadioButton);
        travelerUserButton = findViewById(R.id.TravelerRadioButton);
    }

    public void SignInButtonClicked(View view){
        // Get and validate username
        EditText userNameEditText = findViewById(R.id.SignInPageNameEditText);
        String userName = userNameEditText.getText().toString();

        boolean valid = true;
        if (!userNameValidation(userName)) {
            valid = false;
            // cleans the edit text view
            userNameEditText.setText("");

            TextView UserNameEditTextLabel = findViewById(R.id.UserNameEditTextLabel);
            String messageForInvalidUserName = getMessageForInvalidUserName();
            UserNameEditTextLabel.setTextColor(Color.RED);
            UserNameEditTextLabel.setText(messageForInvalidUserName);
        } else {
            TextView UserNameEditTextLabel = findViewById(R.id.UserNameEditTextLabel);
            UserNameEditTextLabel.setTextColor(Color.BLACK);
            UserNameEditTextLabel.setText(getString(R.string.enterUserNameLabelTag));
        }

        // Get and validate email
        EditText userEMailAddressEditText = findViewById(R.id.editTextTextEmailAddress);
        String userEMailAddressString = userEMailAddressEditText.getText().toString();

        if (! userEMailValidation(userEMailAddressString)) {
            valid = false;
            // cleans the edit text view
            userEMailAddressEditText.setText("");

            TextView eMailEditTextLabel = findViewById(R.id.EmailAddressEditTextLabel);
            String messageForInvalidUserName = getMessageForInvalidEMailAddress(userEMailAddressString);
            eMailEditTextLabel.setTextColor(Color.RED);
            eMailEditTextLabel.setText(messageForInvalidUserName);
        } else {
            TextView eMailEditTextLabel = findViewById(R.id.EmailAddressEditTextLabel);
            eMailEditTextLabel.setTextColor(Color.BLACK);
            eMailEditTextLabel.setText(getString(R.string.EnterEmailLabelTag));
        }

        // Get and validate password
        EditText userPasswordEditText = findViewById(R.id.editTextTextPassword);
        EditText userPasswordAgainEditText = findViewById(R.id.editTextTextPasswordAgain);
        String userPassword = userPasswordEditText.getText().toString();
        String userPasswordAgain = userPasswordAgainEditText.getText().toString();

        if (userPassword.equals(userPasswordAgain)) {
            TextView userPasswordAgainTextView = findViewById(R.id.PasswordAgainEditTextLabel);
            userPasswordAgainTextView.setTextColor(Color.BLACK);
            userPasswordAgainTextView.setText(R.string.label_enter_password_again);

            if (! userPasswordValidation(userPassword)) {
                valid = false;
                // cleans the edit text view
                userPasswordEditText.setText("");
                userPasswordAgainEditText.setText("");

                TextView userPasswordTextView = findViewById(R.id.editTextTextPassword);
                String messageForInvalidPassword = getMessageForInvalidPassword(userPassword);
                userPasswordTextView.setTextColor(Color.RED);
                userPasswordTextView.setText(messageForInvalidPassword);
            } else {
                TextView userPasswordTextView = findViewById(R.id.editTextTextPassword);
                userPasswordTextView.setTextColor(Color.BLACK);
            }
        } else {
            valid = false;
            // cleans the edit text view
            userPasswordAgainEditText.setText("");

            TextView userPasswordAgainTextView = findViewById(R.id.PasswordAgainEditTextLabel);
            userPasswordAgainTextView.setTextColor(Color.RED);
            userPasswordAgainTextView.setText(R.string.error_passwords_do_not_match);
        }

        // Check the user type selection
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId < 0) {
            valid = false;
            Toast.makeText(this, R.string.toast_choose_account_type, Toast.LENGTH_SHORT).show();
        } else {
            // Fixed: Using isChecked() instead of isSelected()
            if (selectedId == R.id.GuideRadioButton) {
                userType = UserDTO.UserType.GUIDE_USER;
            } else {
                userType = UserDTO.UserType.REGULAR_USER;
            }
        }

        if (valid) {
            // Create account with validated data
            signIn(userName, userEMailAddressString, userPassword);
        }
    }

    /**
     * Creates a new user account with the provided information
     *
     * @param username The username for the new account
     * @param email    The email address for the new account
     * @param password The password for the new account
     */
    public void signIn(String username, String email, String password) {
        // Create UserCreateUpdateDTO object with user information
        UserCreateUpdateDTO userCreateDTO = new UserCreateUpdateDTO();
        userCreateDTO.username = username;
        userCreateDTO.email = email;
        userCreateDTO.password = password;
        userCreateDTO.userType = userType;

        // Get UserService instance
        UserService userService = ServiceLocator.getUserService();

        // Call create user API asynchronously
        NetworkModule.toCompletableFuture(userService.createUser(userCreateDTO))
                .thenAccept(user -> runOnUiThread(() -> {
                    // On successful registration
                    Toast.makeText(CreateAccountActivity.this,
                            "Account created successfully!", Toast.LENGTH_SHORT).show();

                    // Store user ID in UserState
                    UserState.setUserId(user.id);

                    // Navigate to sign-in activity
                    Intent intent = new Intent(CreateAccountActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish(); // Close current activity
                }))
                .exceptionally(throwable -> {
                    runOnUiThread(() -> {
                        Throwable cause =
                                throwable.getCause() != null ? throwable.getCause() : throwable;
                        String errorMessage = "Failed to create account";

                        if (cause instanceof IOException) {
                            errorMessage = "Network error. Please check your connection.";
                        }

                        Toast.makeText(CreateAccountActivity.this,
                                errorMessage, Toast.LENGTH_LONG).show();
                    });
                    return null;
                });
    }

    //TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage
    public static boolean userNameValidation(String userName){
        boolean result = true;
        for (int i = 0; i < userName.length() && result; i++) {
            char current = userName.charAt(i);
            result = Character.isAlphabetic(current);
        }
        return result;
    }

    //TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage
    public static String getMessageForInvalidUserName() {
        return  "User name cannot have special characters. ex: ? / ; .";
    }

    //TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage
    public static boolean userEMailValidation(String eMail) {
        return true;
    }

    //TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage
    public static String getMessageForInvalidEMailAddress(String eMail) {
        return "";
    }

    //TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage
    public static boolean userPasswordValidation(String password) {
        return true;
    }

    //TODO: this method just for testing. Please complete the method body in a meaningful way according to its usage
    public static String getMessageForInvalidPassword(String password) {
        return "";
    }
}
