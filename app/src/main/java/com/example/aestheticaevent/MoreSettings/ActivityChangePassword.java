package com.example.aestheticaevent.MoreSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.ChangePasswordListResponse;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.Activity_SignIn;
import com.example.aestheticaevent.User.UserResponse.LoginResponse;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.Tools;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ActivityChangePassword extends AppCompatActivity {

    EditText etOldPassword,etNewPassword,etConfirmPassword;
    CardView btnDone;
    Restcall restcall;
    ImageView ivBack;
    Tools tools;
    SharedPreference sharedPreference;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        sharedPreference=new SharedPreference(ActivityChangePassword.this);
        tools=new Tools(this);
        etOldPassword=findViewById(R.id.etOldPassword);
        ivBack=findViewById(R.id.ivBack);
        etNewPassword=findViewById(R.id.etNewPassword);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        btnDone=findViewById(R.id.btnDone);
        restcall= RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools.ScreenshotBlock(getWindow());

        userId = sharedPreference.getStringvalue("user_id");


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();;
            }
        });
    }

    private void ChangePassword() {
        tools.showLoading();
        restcall.ChangePassword("ChangePassword", userId,
                        etOldPassword.getText().toString().trim(),
                        etNewPassword.getText().toString().trim(),
                        etConfirmPassword.getText().toString().trim())

                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ChangePasswordListResponse>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(ActivityChangePassword.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(ChangePasswordListResponse changePasswordListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (changePasswordListResponse != null) {
                                    if (changePasswordListResponse.getStatus().equals(VariableBag.SUCCESS_CODE)) {
                                        etNewPassword.setText("");
                                        etConfirmPassword.setText("");
                                        Toast.makeText(ActivityChangePassword.this, changePasswordListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ActivityChangePassword.this, "Error: " + changePasswordListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(ActivityChangePassword.this, "Empty response or invalid JSON", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                });
    }


}