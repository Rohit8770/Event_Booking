package com.example.aestheticaevent.ActivityFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.Activity_SignIn;

public class ThirdActivity extends AppCompatActivity {
TextView tvNext,tvSkip3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        tvNext=findViewById(R.id.tvNext);
        tvSkip3=findViewById(R.id.tvSkip3);

        tvSkip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ThirdActivity.this, Activity_SignIn.class);
                startActivity(i);
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ThirdActivity.this, Activity_SignIn.class);
                startActivity(i);
            }
        });
    }
}