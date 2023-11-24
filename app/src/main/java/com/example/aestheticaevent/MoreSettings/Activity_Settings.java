package com.example.aestheticaevent.MoreSettings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.SharedPreference;

import java.util.concurrent.Executor;

public class Activity_Settings extends AppCompatActivity {


    SwitchCompat SwitchLock;
    private SharedPreferences sharedPreferences;
    private static final String SWITCH_STATE_KEY = "biometricSwitchState";
    private boolean isBiometricEnabled = false;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    ImageView ivHelpAndFAQsBack;
    LinearLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SwitchLock=findViewById(R.id.SwitchLock);

        main_layout=findViewById(R.id.main_layout);
        ivHelpAndFAQsBack=findViewById(R.id.ivHelpAndFAQsBack);

        ivHelpAndFAQsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isBiometricEnabled = sharedPreferences.getBoolean(SWITCH_STATE_KEY, false);
        SwitchLock.setChecked(isBiometricEnabled);


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


        ivHelpAndFAQsBack.setOnClickListener(v -> finish());

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

}