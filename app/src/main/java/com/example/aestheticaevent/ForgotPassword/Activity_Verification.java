package com.example.aestheticaevent.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.aestheticaevent.R;

public class Activity_Verification extends AppCompatActivity {

    ImageView ivForgotPasswordUpBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        ivForgotPasswordUpBack = findViewById(R.id.ivForgotPasswordUpBack);

        ivForgotPasswordUpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}