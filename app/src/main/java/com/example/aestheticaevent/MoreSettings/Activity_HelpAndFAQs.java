package com.example.aestheticaevent.MoreSettings;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.aestheticaevent.R;

public class Activity_HelpAndFAQs extends AppCompatActivity {

    ImageView ivHelpAndFAQsBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_faqs);

        ivHelpAndFAQsBack =findViewById(R.id.ivHelpAndFAQsBack);

        ivHelpAndFAQsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}