package com.example.actualtravellerkiviprojectui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.adapter.Account_Page_Posts_RecyclerViewAdapter;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;
import com.example.actualtravellerkiviprojectui.dto.Post.PostDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserCreateUpdateDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountPageFragment extends Fragment {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();
    private SharedPreferences prefs;

    List<PostDTO> posts = new ArrayList<>();
    private RecyclerView recyclerView;
    private Account_Page_Posts_RecyclerViewAdapter adapter;

    private ImageView profilePhoto;
    private TextView userProfileName;
    private Button settingsButton;
    private Button attendedToursButton;
    private Button upcomingToursButton;
    private Button chooseLanguageButton;

    private ImageButton postCreateBadge, eventJoinBadge, eventCreateBadge, commentWriteBadge, imageUploadBadge, likeReceiveBadge;

    private LinearLayout launchTourWindowForGuideUsers;

    private Button catalogButton;
    private Button createButton;
    ActivityResultLauncher<Intent> resultLauncher;

    public AccountPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountPageFragment newInstance() {
        AccountPageFragment fragment = new AccountPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);

        Log.e("ERROR", UserState.getUserId().toString());

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            try {
                Uri imageUri = result.getData().getData();
                int curId = UserState.getUserId();
                NetworkModule.uploadImage(getContext(), imageUri, filePart -> userService.setAvatar(curId, filePart), userDetail -> {
                    // Handle success - update UI with the returned UserDetail
                    profilePhoto.setImageURI(imageUri);
                    Toast.makeText(getContext(), "Profile picture updated!", Toast.LENGTH_LONG).show();
                }, error -> {
                    // Handle error
                    Log.e("Avatar", "Upload failed", error);
                    Toast.makeText(getContext(), "Failed to upload profile picture!", Toast.LENGTH_LONG).show();
                });
            } catch (Exception e) {
                Log.e("Avatar", "No image selected", e);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_page, container, false);
        chooseLanguageButton = view.findViewById(R.id.ChooseLanguageButton);
        //badges
        postCreateBadge = view.findViewById(R.id.imageButtonPostCreate);
        eventJoinBadge = view.findViewById(R.id.imageButtonEventJoint);
        eventCreateBadge = view.findViewById(R.id.imageButtonEventCreate);
        commentWriteBadge = view.findViewById(R.id.ImageButtonCommentWriteBadge);
        imageUploadBadge = view.findViewById(R.id.ImageButtonImageUploadBadge);
        likeReceiveBadge = view.findViewById(R.id.ImageButtonLikeBadge);
        userProfileName = view.findViewById(R.id.userProfileNameTextView);
        settingsButton = view.findViewById(R.id.AccountPageSettingsButton);
        profilePhoto = view.findViewById(R.id.userProfilePhoto);
        attendedToursButton = view.findViewById(R.id.AttendedToursButton);
        upcomingToursButton = view.findViewById(R.id.UpcomingToursButton);
        recyclerView = view.findViewById(R.id.AccountPagePostRecyclerView);
        launchTourWindowForGuideUsers = view.findViewById(R.id.LaunchTourWindowForGuideUsers);
        createButton = view.findViewById(R.id.launchTourWindowCreateButton);
        catalogButton = view.findViewById(R.id.launchTourWindowCatalogButton);

        chooseLanguageButton.setOnClickListener(v -> {
            String[] languages = {"English", "Türkçe", "Deutsch"};
            new AlertDialog.Builder(requireContext()).setTitle("Select Language").setSingleChoiceItems(languages, -1, null).setPositiveButton("OK", (dialog, which) -> {
                int selected = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                String lang = languages[selected];
                // Dil seçim bilgisini kaydet
                PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().putString("app_language", lang).apply();
                Toast.makeText(getContext(), "Language set to " + lang, Toast.LENGTH_SHORT).show();
            }).setNegativeButton("Cancel", null).show();
        });

        NetworkModule.toCompletableFuture(userService.getUser(UserState.getUserId())).thenAccept(user -> {
            if (user != null && user.userType != UserDTO.UserType.GUIDE_USER) {
                launchTourWindowForGuideUsers.setVisibility(GONE);
            } else {
                launchTourWindowForGuideUsers.setVisibility(VISIBLE);


            }
            userProfileName.setText(user.username);
        }).exceptionally(e -> {
            Log.e("AccountPage", "Error fetching user details", e);
            return null;
        });

        NetworkModule.setImageViewFromCall(profilePhoto, userService.getAvatar(UserState.getUserId()), null);


        settingsButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getContext(), settingsButton);
            popup.getMenuInflater().inflate(R.menu.account_settings_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.ChangeTheAccountPhoto:
                        //@author Güneş
                        pickImage();
                        return true;
                    case R.id.ChangeName:
                        //@author Eftelya
                        showChangeNameDialog();
                        return true;

                    case R.id.ChangeLanguages:
                        //@author Eftelya
                        showChangeLanguageDialog();
                        return true;
                    case R.id.ChangePassword:
                        showChangePasswordDialog();
                        return true;

                }
                return false;
            });
            popup.show();
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LaunchTourCreateActivity.class);
                startActivity(intent);
            }
        });
        catalogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LaunchTourCatalogeActivity.class);
                startActivity(intent);
            }
        });
        chooseLanguageButton = view.findViewById(R.id.ChooseLanguageButton);
        chooseLanguageButton.setOnClickListener(v -> showChangeLanguageDialog());


        attendedToursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttendedToursActivity.class);
                startActivity(intent);
            }
        });

        upcomingToursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpcomingToursActivity.class);
                startActivity(intent);
            }
        });

        //TODO: Badge achievement system


        postCreateBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.PostCreateBadge, Toast.LENGTH_SHORT).show();
            }
        });

        eventJoinBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.EventJoinBadge, Toast.LENGTH_SHORT).show();
            }
        });

        eventCreateBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.PostCreateBadge, Toast.LENGTH_SHORT).show();
            }
        });

        commentWriteBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.CommentWriteBadge, Toast.LENGTH_SHORT).show();
            }
        });
        imageUploadBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.ImageUploadBadge, Toast.LENGTH_SHORT).show();
            }
        });
        likeReceiveBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.LikeReceiveBadge, Toast.LENGTH_SHORT).show();
            }
        });

        //posts = currentUser.getPosts();
        //TODO
        fillSocialMediaPosts();

        adapter = new Account_Page_Posts_RecyclerViewAdapter(getContext(), posts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private void pickImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        // Restrict to images
        intent.setType("image/*");
        resultLauncher.launch(intent);
    }

    private void fillSocialMediaPosts() {
        NetworkModule.toCompletableFuture(postService.fetchUserPosts(UserState.getUserId())).thenAccept(postList -> {
            if (postList != null) {
                posts.clear();
                posts.addAll(postList);
                adapter.notifyDataSetChanged(); // RecyclerView güncellensin
            }
        }).exceptionally(e -> {
            Log.e("retrofit", "Error fetching feed", e);
            if (getContext() != null) {
                Toast.makeText(getContext(), "Error fetching feed", Toast.LENGTH_SHORT).show();
            }
            return null;
        });
    }

    // --- Change Name Dialog ---@author:Eftelya
    private void showChangeNameDialog() {
        final EditText input = new EditText(requireContext());
        input.setHint(R.string.NewName);

        new AlertDialog.Builder(requireContext()).setTitle(R.string.ChangeName).setView(input).setPositiveButton(R.string.Save, (dlg, which) -> {
            String yeniIsim = input.getText().toString().trim();
            UserCreateUpdateDTO updateDTO = new UserCreateUpdateDTO();
            updateDTO.username = yeniIsim;
            NetworkModule.toCompletableFuture(userService.updateUser(UserState.getUserId(), updateDTO)).thenAccept(userDTO -> {
                userProfileName.setText(userDTO.username);
                Toast.makeText(getContext(), R.string.NameUpdated, Toast.LENGTH_SHORT).show();

            }).exceptionally(throwable -> {
                        Toast.makeText(getContext(), "Failed to update the name!", Toast.LENGTH_SHORT).show();
                        return null;
                    }

            );
        }).setNegativeButton(R.string.Cancel, null).show();
    }

    // --- Change Language Dialog ---
    // TODO : backendle bağlanması gerek.
    private void showChangeLanguageDialog() {
        String[] labels = {"English", "Türkçe", "Deutsch"};
        String[] codes = {"en", "tr", "de"};
        int checkedItem = getSavedLangIndex();

        new AlertDialog.Builder(requireContext()).setTitle(R.string.SelectLanguage).setSingleChoiceItems(labels, checkedItem, (dlg, which) -> {

            prefs.edit().putString("lang", codes[which]).apply();
        }).setPositiveButton(R.string.Apply, (dlg, which) -> {
            String lang = prefs.getString("lang", "en");
            applyLocale(lang);
        }).setNegativeButton(R.string.Cancel, null).show();
    }

    private void showChangePasswordDialog() {

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_change_password, null);
        EditText etOld = dialogView.findViewById(R.id.etOldPassword);
        EditText etNew = dialogView.findViewById(R.id.etNewPassword);
        EditText etNew2 = dialogView.findViewById(R.id.etConfirmNewPassword);

        new AlertDialog.Builder(requireContext()).setTitle(R.string.ChangePassword).setView(dialogView).setPositiveButton(R.string.Save, (dlg, which) -> {
            String oldPw = etOld.getText().toString();
            String newPw = etNew.getText().toString();
            String newPw2 = etNew2.getText().toString();
            if (!newPw.equals(newPw2)) {
                Toast.makeText(getContext(), R.string.PasswordsDontMatch, Toast.LENGTH_SHORT).show();
                return;
            }
            UserCreateUpdateDTO updateDTO = new UserCreateUpdateDTO();
            updateDTO.password = newPw;
            NetworkModule.toCompletableFuture(userService.updateUser(UserState.getUserId(), updateDTO)).thenAccept(userDTO -> {
                Toast.makeText(getContext(), "Successfully changed the password", Toast.LENGTH_SHORT).show();

            }).exceptionally(throwable -> {
                        Toast.makeText(getContext(), "Failed to change the password", Toast.LENGTH_SHORT).show();
                        return null;
                    }

            );
        }).setNegativeButton(R.string.Cancel, null).show();
    }


    // TODO: backendle bağlanması gerek. profile settingste change language e göre çıkan turlar otomatik filtrelenecek.
    private int getSavedLangIndex() {
        String kod = prefs.getString("lang", "en");
        switch (kod) {
            case "tr":
                return 1;
            case "de":
                return 2;
            default:
                return 0;
        }
    }

    private void applyLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources res = requireContext().getResources();
        Configuration cfg = res.getConfiguration();
        cfg.setLocale(locale);
        res.updateConfiguration(cfg, res.getDisplayMetrics());
        requireActivity().recreate();
    }

}
