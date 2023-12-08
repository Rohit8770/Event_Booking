package com.example.aestheticaevent.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aestheticaevent.HomeScreen.HomeResponse.MobileListResponse;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.Tools;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ActivityPhoneForgetPassword extends AppCompatActivity {


    CountryCodePicker ccp;
    EditText t1;
    CardView b1;
    FirebaseAuth mAuth;
    SharedPreference sharedPreference;
    Tools tools;
    Restcall restcall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        sharedPreference=new SharedPreference(this);
        tools=new Tools(this);
        restcall= RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        mAuth = FirebaseAuth.getInstance();
        t1 = findViewById(R.id.t1);
        b1 = findViewById(R.id.b1);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(t1);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPhoneForgetPassword.this, ActivityOtpForget.class);
                intent.putExtra("mobile", ccp.getFullNumberWithPlus().replace("", ""));
                GetAllDataCall();
                startActivity(intent);

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        GetAllDataCall();
    }
    public  void GetAllDataCall(){
        restcall.GetAllData("GetAllData")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<MobileListResponse>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ActivityPhoneForgetPassword.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(MobileListResponse mobileListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mobileListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                && mobileListResponse.getUsersList()!=null
                                && mobileListResponse.getUsersList().size()>0){
                                    t1.setText("");
                                    Toast.makeText(ActivityPhoneForgetPassword.this, mobileListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
        }
    }