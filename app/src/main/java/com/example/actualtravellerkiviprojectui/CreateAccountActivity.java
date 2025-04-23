package com.example.actualtravellerkiviprojectui;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Zeynep
 */
public class LogInActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
    }

    public void SignInButtonClicked(View view){

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
            //TODO: according to the kind of the error program may direct the user to another page.
            //TODO:For example, if user has another account with same e-mail program may open the log in page
        } else {
            TextView eMailEditTextLabel = findViewById(R.id.EmailAddressEditTextLabel);
            eMailEditTextLabel.setTextColor(Color.BLACK);
            eMailEditTextLabel.setText(getString(R.string.EnterEmailLabelTag));
        }

        EditText userPasswordEditText = findViewById(R.id.editTextTextPassword);
        EditText userPasswordAgainEditText = findViewById(R.id.editTextTextPasswordAgain);
        String userPassword = userPasswordEditText.getText().toString();
        String userPasswordAgain = userPasswordAgainEditText.getText().toString();

        if (userPassword.equals(userPasswordAgain)) {
            TextView userPasswordAgainTextView = findViewById(R.id.PasswordAgainEditTextLabel);
            userPasswordAgainTextView.setTextColor(Color.BLACK);
            userPasswordAgainTextView.setText("Enter Your Password Again");

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
                userPasswordTextView.setText(getString(R.string.enterPasswordLabelTag));
            }
        } else {
            valid = false;
            // cleans the edit text view
            userPasswordAgainEditText.setText("");

            TextView userPasswordAgainTextView = findViewById(R.id.PasswordAgainEditTextLabel);
            userPasswordAgainTextView.setTextColor(Color.RED);
            userPasswordAgainTextView.setText("Please enter the same password");
        }

        if (valid) {
            signIn(userName);
            //TODO: switch the page
        }
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
    public static void signIn(String userName) {
        //TODO
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
