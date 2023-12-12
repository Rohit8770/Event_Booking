package com.example.aestheticaevent.ActivityFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.Activity_SignIn;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.Tools;

public class FirsttActivity extends AppCompatActivity {

    TextView tvNext1,tvSkip;
    SharedPreference sharedPreference;
    Tools tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marge_fragment);

        sharedPreference = new SharedPreference(this); // Initialize sharedPreference
        tools=new Tools(this);
        tools.ScreenshotBlock(getWindow());
        if (sharedPreference.isOnboardingCompleted(this)) {
            startActivity(new Intent(this, Activity_SignIn.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_marge_fragment);


        tvNext1=findViewById(R.id.tvNext1);
        tvSkip=findViewById(R.id.tvSkip);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tools.vibrate();
                tools.playBeepSound();

                Intent i= new Intent(FirsttActivity.this, Activity_SignIn.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        tvNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tools.vibrate();
                tools.playBeepSound();

                Intent i= new Intent(FirsttActivity.this, SecondActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}