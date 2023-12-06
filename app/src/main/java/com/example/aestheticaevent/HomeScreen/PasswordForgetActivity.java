package com.example.aestheticaevent.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aestheticaevent.HomeScreen.HomeResponse.ForgetPasswordListResponse;
import com.example.aestheticaevent.MoreSettings.ActivityChangePassword;
import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.ChangePasswordListResponse;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.Tools;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class PasswordForgetActivity extends AppCompatActivity {

    EditText etNewPassword,etConfirmPassword;
    CardView btnDone;
    Restcall restcall;
    Tools tools;
    SharedPreference sharedPreference;
    String userId,mobileId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget);

        etNewPassword=findViewById(R.id.etNewPassword);
        btnDone=findViewById(R.id.btnDone);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        sharedPreference=new SharedPreference(PasswordForgetActivity.this);
        restcall= RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools=new Tools(this);



            userId = sharedPreference.getStringvalue("user_id");
        mobileId = getIntent().getStringExtra("mobile");



        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddForgetNewPassword();
            }
        });
    }
    private void AddForgetNewPassword() {
        tools.showLoading();
        restcall.AddForgetNewPassword("AddNewPassword",sharedPreference.getStringvalue("user_id"),
                        etNewPassword.getText().toString().trim(),
                        etConfirmPassword.getText().toString().trim(),mobileId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ForgetPasswordListResponse>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(PasswordForgetActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(ForgetPasswordListResponse changePasswordListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (changePasswordListResponse.getStatus().equals(VariableBag.SUCCESS_CODE)) {
                                    etNewPassword.setText("");
                                    etConfirmPassword.setText("");
                                    Toast.makeText(PasswordForgetActivity.this, changePasswordListResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }

                });
    }
}