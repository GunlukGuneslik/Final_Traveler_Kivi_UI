package com.example.actualtravellerkiviprojectui;

import static android.view.View.GONE;

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
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.model.SocialMediaPostModel;
import com.example.actualtravellerkiviprojectui.state.UserState;

import java.io.IOException;
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

    ArrayList<PostDTO> posts = new ArrayList<>();
    private RecyclerView recyclerView;
    private Account_Page_Posts_RecyclerViewAdapter adapter;

    private UserDTO currentUser;
    private int userProfilePhoto;
    private String userName;
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


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    public static AccountPageFragment newInstance(String param1, String param2) {
        AccountPageFragment fragment = new AccountPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = requireContext()
                .getSharedPreferences("app_prefs", Context.MODE_PRIVATE);

        // 2) API’dan currentUser’ı çekiyor
        currentUser = UserState.getUser(userService);

        // 3) userName’i hem pref’ten hem de API’den gelen default’la tek satırda atıyor
        userName = prefs.getString("username", currentUser.firstName);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            try {
                // TODO: burası kullanıcının fotoğrafını değiştirmiyor aslında!
                Uri imageUri = result.getData().getData();
                int curId = UserState.getUserId();
                NetworkModule.uploadImage(getContext(), imageUri, filePart -> userService.setAvatar(curId, filePart),
                        userDetail -> {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_page, container, false);
        chooseLanguageButton = view.findViewById(R.id.ChooseLanguageButton );
        chooseLanguageButton .setOnClickListener(v -> {
            String[] languages = {"English", "Türkçe", "Deutsch"};
            new AlertDialog.Builder(requireContext())
                    .setTitle("Select Language")
                    .setSingleChoiceItems(languages, -1, null)
                    .setPositiveButton("OK", (dialog, which) -> {
                        int selected = ((AlertDialog) dialog).getListView()
                                .getCheckedItemPosition();
                        String lang = languages[selected];
                        // Dil seçim bilgisini kaydet
                        PreferenceManager.getDefaultSharedPreferences(requireContext())
                                .edit()
                                .putString("app_language", lang)
                                .apply();
                        Toast.makeText(getContext(),
                                "Language set to " + lang,
                                Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
        launchTourWindowForGuideUsers = view.findViewById(R.id.LaunchTourWindowForGuideUsers);
        if (currentUser.userType != UserDTO.UserType.GUIDE_USER) {
            //TODO: burası test edilecek eğer düzgün durmuyorsa telefonda gone yerine invisible yapılacak!
            launchTourWindowForGuideUsers.setVisibility(GONE);
        } else {
            catalogButton = view.findViewById(R.id.launchTourWindowCatalogButton);
            catalogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LaunchTourCatalogeActivity.class);
                    intent.putExtra("User", currentUser);
                    startActivity(intent);
                }
            });

            createButton = view.findViewById(R.id.launchTourWindowCreateButton);
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LaunchTourCreateActivity.class);
                    intent.putExtra("User", currentUser);
                    startActivity(intent);
                }
            });
            chooseLanguageButton = view.findViewById(R.id.ChooseLanguageButton);
            chooseLanguageButton.setOnClickListener(v -> showChangeLanguageDialog());
        }

        profilePhoto = view.findViewById(R.id.userProfilePhoto);
        // profilePhoto.setImageResource(userProfilePhoto);

        userProfileName = view.findViewById(R.id.userProfileNameTextView);

        String displayName = prefs.getString("username", userName);// to change the name @eftelya
        userProfileName.setText(displayName);


        settingsButton = view.findViewById(R.id.AccountPageSettingsButton);
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

        attendedToursButton = view.findViewById(R.id.AttendedToursButton);
        attendedToursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttendedToursActivity.class);
                startActivity(intent);
            }
        });

        upcomingToursButton = view.findViewById(R.id.UpcomingToursButton);
        upcomingToursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpcomingToursActivity.class);
                startActivity(intent);
            }
        });

        //TODO: Badge achievement system

        //badges
        postCreateBadge = view.findViewById(R.id.imageButtonPostCreate);
        eventJoinBadge = view.findViewById(R.id.imageButtonEventJoint);
        eventCreateBadge = view.findViewById(R.id.imageButtonEventCreate);
        commentWriteBadge = view.findViewById(R.id.ImageButtonCommentWriteBadge);
        imageUploadBadge = view.findViewById(R.id.ImageButtonImageUploadBadge);
        likeReceiveBadge = view.findViewById(R.id.ImageButtonLikeBadge);

        postCreateBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Post Create Badge", Toast.LENGTH_SHORT).show();
            }
        });

        eventJoinBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Event Join Badge", Toast.LENGTH_SHORT).show();
            }
        });

        eventCreateBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Event Create Badge", Toast.LENGTH_SHORT).show();
            }
        });

        commentWriteBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Comment Write Badge", Toast.LENGTH_SHORT).show();
            }
        });
        imageUploadBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Image Upload Badge", Toast.LENGTH_SHORT).show();
            }
        });
        likeReceiveBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Like Receive Badge", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = view.findViewById(R.id.AccountPagePostRecyclerView);
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
        resultLauncher.launch(intent);
    }

    //TODO: Complete this method. Will add callback and fail methods
    private UserDTO getCurrentUser() throws IOException {
        return userService.getUser(UserState.getUserId()).execute().body();
    }


    //TODO: is this a good place for this method? ofcouse not! iğ changed it but i don not understand it. Just copy paste from chatgpt.
    private void fillSocialMediaPosts() {
        new Thread(() -> {
            try {
                List<PostDTO> fetchedPosts = postService.fetchFeed(0, 1, 100, "").execute().body().content;
                requireActivity().runOnUiThread(() -> {
                    posts.clear();
                    posts.addAll(fetchedPosts);
                    adapter.notifyDataSetChanged(); // RecyclerView güncellensin
                });
            } catch (IOException e) {
                Log.e("retrofit", "Error fetching feed", e);
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error fetching feed", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    // --- Change Name Dialog ---@author:Eftelya
    private void showChangeNameDialog() {
        final EditText input = new EditText(requireContext());
        input.setHint("Yeni isminiz");

        new AlertDialog.Builder(requireContext())
                .setTitle("Change Name")
                .setView(input)
                .setPositiveButton("Kaydet", (dlg, which) -> {
                    String yeniIsim = input.getText().toString().trim();
                    prefs.edit()
                            .putString("username", yeniIsim)
                            .apply();
                    userProfileName.setText(yeniIsim);
                    Toast.makeText(getContext(), "İsim güncellendi", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("İptal", null)
                .show();
    }

    // --- Change Language Dialog ---@author:Eftelya
    // TODO : backendle bağlanması gerek.
    private void showChangeLanguageDialog() {
        String[] labels = {"English", "Türkçe", "Deutsch"};
        String[] codes = {"en", "tr", "de"};
        int checkedItem = getSavedLangIndex();

        new AlertDialog.Builder(requireContext())
                .setTitle("Select Language")
                .setSingleChoiceItems(labels, checkedItem, (dlg, which) -> {

                    prefs.edit()
                            .putString("lang", codes[which])
                            .apply();
                })
                .setPositiveButton("Uygula", (dlg, which) -> {
                    String lang = prefs.getString("lang", "en");
                    applyLocale(lang);
                })
                .setNegativeButton("İptal", null)
                .show();
    }
    private void showChangePasswordDialog() {

//TODO: backendle bağlancak.
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_change_password, null);
        EditText etOld = dialogView.findViewById(R.id.etOldPassword);
        EditText etNew = dialogView.findViewById(R.id.etNewPassword);
        EditText etNew2 = dialogView.findViewById(R.id.etConfirmNewPassword);

        new AlertDialog.Builder(requireContext())
                .setTitle("Change Password")
                .setView(dialogView)
                .setPositiveButton("Kaydet", (dlg, which) -> {
                    String oldPw = etOld.getText().toString();
                    String newPw = etNew.getText().toString();
                    String newPw2 = etNew2.getText().toString();
                    if (!newPw.equals(newPw2)) {
                        Toast.makeText(getContext(), "Şifreler eşleşmiyor", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // TODO: userService.changePassword(userId, oldPw, newPw) çağır
                })
                .setNegativeButton("İptal", null)
                .show();
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
