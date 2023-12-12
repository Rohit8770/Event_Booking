package com.example.aestheticaevent.HomeScreen;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;

import androidx.biometric.BiometricPrompt;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aestheticaevent.HomeScreen.HomeResponse.CategoryListResponse;
import com.example.aestheticaevent.MoreSettings.Ticket.ActivityTicket;
import com.example.aestheticaevent.User.Activity_SignIn;
import com.example.aestheticaevent.HomeScreen.Adapters.Adapter_EventList;
import com.example.aestheticaevent.MoreSettings.Activity_ContactUs;
import com.example.aestheticaevent.MoreSettings.Activity_HelpAndFAQs;
import com.example.aestheticaevent.MoreSettings.Activity_MyProfile;
import com.example.aestheticaevent.MoreSettings.Activity_Settings;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.Tools;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
import rx.schedulers.Schedulers;


public class Activity_HomeScreen extends AppCompatActivity {
    private RecyclerView rcvEvent;
    private Adapter_EventList adapterEventList;
    private EditText etEventSearch;
    private SwipeRefreshLayout swipeRefreshLayout;
    ImageView ivSetting, imgNotification,tvNoData;
    TextView tv, tvHomeMenuUserName, tvHomeMenuUserEmail, txtInvite,tvNoDataFound;
    CircleImageView civHomeMenuUserImage;
    View layout, layoutProfile, layoutContactUs, layoutHelpAndFAQs, layoutInviteAndShare, layoutLogOut, layoutSetting, layoutTicket;
    SharedPreference sharedPreference;
    private SharedPreferences sharedPreferences;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private RelativeLayout LayoutRelative;
    Restcall restcall;
    Tools tools;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        txtInvite = findViewById(R.id.txtInvite);
        tools = new Tools(this);
        tools.ScreenshotBlock(getWindow());
        sharedPreference = new SharedPreference(Activity_HomeScreen.this);
        rcvEvent = findViewById(R.id.rcvEvent);
        tvNoData = findViewById(R.id.tvNoData);
        etEventSearch = findViewById(R.id.etEventSearch);
        layout = findViewById(R.id.layout);
        tv = findViewById(R.id.tv);
     //   txNodata = findViewById(R.id.txNodata);
        LayoutRelative = findViewById(R.id.LayoutRelative);
        tvHomeMenuUserName = findViewById(R.id.tvHomeMenuUserName);
        tvHomeMenuUserEmail = findViewById(R.id.tvHomeMenuUserEmail);
        tvNoDataFound = findViewById(R.id.tvNoDataFound);
        civHomeMenuUserImage = findViewById(R.id.civHomeMenuUserImage);
        ivSetting = findViewById(R.id.ivSetting);
        layoutProfile = findViewById(R.id.layoutProfile);
        layoutContactUs = findViewById(R.id.layoutContactUs);
        layoutHelpAndFAQs = findViewById(R.id.layoutHelpAndFAQs);
        layoutInviteAndShare = findViewById(R.id.layoutInviteAndShare);
        layoutLogOut = findViewById(R.id.layoutLogOut);
        layoutSetting = findViewById(R.id.layoutSetting);
        layoutTicket = findViewById(R.id.layoutTicket);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        imgNotification = findViewById(R.id.imgNotification);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);


      //  txNodata.setVisibility(View.VISIBLE);
      //  getFCMToken();
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_HomeScreen.this, ActivityNotification.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });


        layout.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isBiometricEnabled = sharedPreferences.getBoolean("biometricSwitchState", false);

        if (isBiometricEnabled) {
            LayoutRelative.setVisibility(View.GONE);
            enableBiometricAuthentication();
        }

        tvHomeMenuUserName.setText(sharedPreference.getStringvalue("userName"));
        tvHomeMenuUserEmail.setText(sharedPreference.getStringvalue("email"));
        String photoPath1 = sharedPreference.getStringvalue("photo");
        if (!TextUtils.isEmpty(photoPath1)) {
            Glide.with(this)
                    .load(Uri.parse(photoPath1))
                    .placeholder(R.drawable.person_image)
                    .into(civHomeMenuUserImage);
        }

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String photoPath = intent.getStringExtra("photoPath");
            String fullName = intent.getStringExtra("fullName");
            String email = intent.getStringExtra("email");

            // Update UI elements with received data
            if (photoPath != null) {
                Glide.with(this)
                        .load(new File(photoPath))
                        .placeholder(R.drawable.person_image)
                        .into(civHomeMenuUserImage);
            }

            if (fullName != null) {
                tvHomeMenuUserName.setText(fullName);
            }

            if (email != null) {
                tvHomeMenuUserEmail.setText(email);
            }
        }

        etEventSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (adapterEventList != null) {
                    adapterEventList.Search(charSequence, rcvEvent);
                }

                boolean isSearchResultsEmpty = adapterEventList.isEmpty();
                if (isSearchResultsEmpty) {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.VISIBLE);
                } else {
                    tvNoDataFound.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
            }
        });

        layoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_HomeScreen.this, Activity_MyProfile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        layoutContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_HomeScreen.this, Activity_ContactUs.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        layoutHelpAndFAQs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_HomeScreen.this, Activity_HelpAndFAQs.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        layoutInviteAndShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareData();
            }
        });
        txtInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareData();
            }
        });

        layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_HomeScreen.this, Activity_Settings.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        layoutTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_HomeScreen.this, ActivityTicket.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }
        });
        layoutLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tools.vibrate();
                tools.playBeepSound();

                // Create the AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_HomeScreen.this);
                builder.setTitle("Log Out")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharedPreference.setLoggedIn(false);
                                sharedPreference.setUserId("");
                                Intent i = new Intent(Activity_HomeScreen.this, Activity_SignIn.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                });alertDialog.show();
            }
        });
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
            }
        });


        swipeRefreshLayout.setOnRefreshListener(() -> {
            GetCategoryCall();
            new Handler().postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
            }, 1000);
        });
    }

    protected void onResume() {
        super.onResume();
        GetCategoryCall();
    }

    public void GetCategoryCall() {
        tools.showLoading();
        restcall.getcategory("getcategory")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CategoryListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                tvNoData.setVisibility(View.VISIBLE);
                                tvNoDataFound.setVisibility(View.VISIBLE);
                                Toast.makeText(Activity_HomeScreen.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(CategoryListResponse categoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (categoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                        && categoryListResponse.getCategoryList() != null
                                        && categoryListResponse.getCategoryList().size() > 0) {

                                    rcvEvent.setVisibility(View.VISIBLE);
                                    tvNoData.setVisibility(View.GONE);
                                    tvNoDataFound.setVisibility(View.GONE);

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_HomeScreen.this, RecyclerView.HORIZONTAL, false);
                                    rcvEvent.setLayoutManager(layoutManager);
                                    adapterEventList = new Adapter_EventList(Activity_HomeScreen.this, categoryListResponse.getCategoryList());
                                    rcvEvent.setAdapter(adapterEventList);

                                    adapterEventList.setCategoryInterface(new Adapter_EventList.CategoryInterface() {
                                        @Override
                                        public void onCategoryClicked(String categoryId, String categoryName) {
                                            Intent i = new Intent(Activity_HomeScreen.this, ActivitySubEvent.class);
                                            i.putExtra("categoryId", categoryId);
                                            i.putExtra("categoryName", categoryName);
                                            startActivity(i);
                                        }
                                    });
                                }else {
                                    rcvEvent.setVisibility(View.GONE);
                                    tvNoData.setVisibility(View.VISIBLE);
                                    tvNoData.setVisibility(View.VISIBLE);
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });
    }

    private void enableBiometricAuthentication() {
        BiometricManager biometricManager = BiometricManager.from(this);
        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            Executor executor = ContextCompat.getMainExecutor(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                biometricPrompt = new BiometricPrompt(Activity_HomeScreen.this, executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(Activity_HomeScreen.this, "Biometric authentication enabled", Toast.LENGTH_SHORT).show();
                        LayoutRelative.setVisibility(View.VISIBLE);
                    }

                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        if (errorCode == androidx.biometric.BiometricPrompt.ERROR_USER_CANCELED) {
                            finish();
                        }
                    }
                });
            }
            promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Insert fingerprint")
                    .setDescription("Use Fingerprint To Log In")
                    .setDeviceCredentialAllowed(true)
                    .build();
            biometricPrompt.authenticate(promptInfo);
        } else {
            Toast.makeText(this, "Device doesn't support biometric authentication", Toast.LENGTH_SHORT).show();
        }
    }

    // @SuppressLint("MissingSuperCall")
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit")
                .setMessage("Are you sure you want to exit the app?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });

        alertDialog.show();
    }

    public void ShareData() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        String share = "look all Programmings";
        String subject = "https://play.google.com/store/apps/details?id=in.seekmyvision.seekmyvision";
        i.putExtra(Intent.EXTRA_SUBJECT, share);
        i.putExtra(Intent.EXTRA_TEXT, subject);
        startActivity(Intent.createChooser(i, "Seek my vision"));
    }
}

//Rohit