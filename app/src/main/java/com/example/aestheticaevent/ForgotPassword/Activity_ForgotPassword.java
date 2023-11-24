package com.example.aestheticaevent.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.aestheticaevent.R;

public class Activity_ForgotPassword extends AppCompatActivity {

    ImageView ivForgotPasswordUpBack;
    CardView cvForgotPasswordButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ivForgotPasswordUpBack = findViewById(R.id.ivForgotPasswordUpBack);
        cvForgotPasswordButton = findViewById(R.id.cvForgotPasswordButton);

        ivForgotPasswordUpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cvForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_ForgotPassword.this, Activity_Verification.class);
                startActivity(intent);
            }
        });
    }
}