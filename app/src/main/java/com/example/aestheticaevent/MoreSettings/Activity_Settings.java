package com.example.aestheticaevent.MoreSettings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aestheticaevent.HomeScreen.ActivityEventinfo;
import com.example.aestheticaevent.HomeScreen.Activity_HomeScreen;
import com.example.aestheticaevent.HomeScreen.Adapters.SubCateAdapter;
import com.example.aestheticaevent.HomeScreen.HomeResponse.SubCategoryListResponse;
import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.DeleteListResponse;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.Activity_SignIn;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.Tools;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import java.util.concurrent.Executor;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class Activity_Settings extends AppCompatActivity {
    SwitchCompat SwitchLock;
    private SharedPreferences sharedPreferences;
    private static final String SWITCH_STATE_KEY = "biometricSwitchState";
    private boolean isBiometricEnabled = false;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    ImageView ivHelpAndFAQsBack;
    SharedPreference sharedPreference;
    LinearLayout main_layout, lyDeleteAccount,llChangePassword;
    Restcall restcall;
    String userId;
    Tools tools;
    SwitchCompat SoundId;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Activity_HomeScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SwitchLock = findViewById(R.id.SwitchLock);

        tools=new Tools(this);
        tools.ScreenshotBlock(getWindow());
        sharedPreference = new SharedPreference(this);
        main_layout = findViewById(R.id.main_layout);
        lyDeleteAccount = findViewById(R.id.lyDeleteAccount);
        ivHelpAndFAQsBack = findViewById(R.id.ivHelpAndFAQsBack);
      //  SoundId = findViewById(R.id.SoundId);
        llChangePassword = findViewById(R.id.llChangePassword);

        userId = sharedPreference.getStringvalue("user_id");

        llChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Activity_Settings.this, ActivityChangePassword.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        ivHelpAndFAQsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Activity_Settings.this, Activity_HomeScreen.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isBiometricEnabled = sharedPreferences.getBoolean(SWITCH_STATE_KEY, false);
        SwitchLock.setChecked(isBiometricEnabled);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);


        SwitchLock.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isBiometricEnabled = isChecked;
            sharedPreferences.edit().putBoolean(SWITCH_STATE_KEY, isBiometricEnabled).apply();

//            updateLockImage(isBiometricEnabled);  // Update the lock image

            if (isChecked) {
                enableBiometricAuthentication();
            } else {

                disableBiometricAuthentication();
            }
        });

        if (isBiometricEnabled) {
            main_layout.setVisibility(View.GONE);
            enableBiometricAuthentication();
        }


     //    ivHelpAndFAQsBack.setOnClickListener(v -> finish());
        lyDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Settings.this);
                builder.setTitle("Delete Account")
                        .setMessage("Are you sure you want to delete your account?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Deleteuser();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (biometricPrompt != null) {
            biometricPrompt.cancelAuthentication();
        }
    }


    private void enableBiometricAuthentication() {
        androidx.biometric.BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            Executor executor = ContextCompat.getMainExecutor(this);
            biometricPrompt = new BiometricPrompt(Activity_Settings.this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(Activity_Settings.this, "Biometric authentication enabled", Toast.LENGTH_SHORT).show();
                    main_layout.setVisibility(View.VISIBLE);
                }

                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                        finish();
                    }
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Insert fingerprint")
                    .setDescription("Use Fingerprint To Log In")
                    .setDeviceCredentialAllowed(true)
                    .build();
            biometricPrompt.authenticate(promptInfo);
        } else {
            Toast.makeText(this, "Device doesn't support biometric authentication", Toast.LENGTH_SHORT).show();
        }
    }

    private void disableBiometricAuthentication() {

        Toast.makeText(this, "Biometric authentication disabled", Toast.LENGTH_SHORT).show();
    }


    public void Deleteuser() {
        tools.showLoading();
        restcall.Deleteuser("deleteuser", userId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<DeleteListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Toast.makeText(Activity_Settings.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(DeleteListResponse deleteListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (deleteListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    sharedPreference.setLoggedIn(false);
                                    Intent i = new Intent(Activity_Settings.this, Activity_SignIn.class);
                                    startActivity(i);
                                    finish();

                                    Toast.makeText(Activity_Settings.this, deleteListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }
}