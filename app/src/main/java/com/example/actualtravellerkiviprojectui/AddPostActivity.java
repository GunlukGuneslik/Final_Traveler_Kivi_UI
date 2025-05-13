package com.example.actualtravellerkiviprojectui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.util.ArrayList;
import java.util.List;

public class AddPostActivity extends AppCompatActivity {
    private PostService postService = ServiceLocator.getPostService();

    private Uri selectedImageUri;
    private ImageView ivPreview;
    private EditText etCaption;
    private EditText etHashtags;
    private Button btnShare;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);


        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        ivPreview = findViewById(R.id.ivPreview);
        etCaption = findViewById(R.id.etCaption);
        etHashtags = findViewById(R.id.etHashtags);
        btnShare = findViewById(R.id.btnShare);


        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    ivPreview.setImageURI(uri);
                    selectedImageUri = uri;
                }
            }
        });


        ivPreview.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });


        btnShare.setOnClickListener(v -> sharePost());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_share) {
            sharePost();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void sharePost() {
        String caption = etCaption.getText().toString().trim();
        String rawTags = etHashtags.getText().toString().trim();

        // Hashtagleri parse et (# işareti zorunlu değil, ekliyoruz)
        List<String> tags = new ArrayList<>();
        if (!rawTags.isEmpty()) {
            for (String t : rawTags.split("\\s+")) {
                if (!t.startsWith("#")) t = "#" + t;
                tags.add(t.toLowerCase());
            }
        }


        int userId = UserState.getUserId(); //

        // TODO: context?
        NetworkModule.uploadImage(App.getContext(), selectedImageUri, part -> {
            return postService.createPost(userId, caption, tags, part);
        }, postDTO -> {
            Toast.makeText(AddPostActivity.this, R.string.toast_post_shared, Toast.LENGTH_SHORT).show();
            finish();
        }, throwable -> {
            Toast.makeText(AddPostActivity.this,
                    R.string.toast_post_server_error + throwable.getMessage(), Toast.LENGTH_LONG).show();

        });

    }


}
