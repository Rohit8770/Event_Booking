package com.example.aestheticaevent.ActivityFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.Activity_SignIn;
import com.example.aestheticaevent.Utils.SharedPreference;

public class FirsttActivity extends AppCompatActivity {

    TextView tvNext1,tvSkip;
    SharedPreference sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marge_fragment);

        sharedPreference = new SharedPreference(this); // Initialize sharedPreference


        if (sharedPreference.isOnboardingCompleted(this)) {
            // Onboarding already completed, navigate to SignIn activity
            startActivity(new Intent(this, Activity_SignIn.class));
            finish(); // Finish the current activity to prevent going back to onboarding
            return;
        }

        setContentView(R.layout.activity_marge_fragment);


        tvNext1=findViewById(R.id.tvNext1);
        tvSkip=findViewById(R.id.tvSkip);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(FirsttActivity.this, Activity_SignIn.class);
                startActivity(i);
            }
        });
        tvNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(FirsttActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });
    }
}