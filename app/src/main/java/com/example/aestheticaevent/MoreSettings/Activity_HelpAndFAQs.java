package com.example.aestheticaevent.MoreSettings;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.Tools;

public class Activity_HelpAndFAQs extends AppCompatActivity {

    ImageView ivHelpAndFAQsBack;
    Tools tools;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_faqs);

        tools=new Tools(this);
        tools.ScreenshotBlock(getWindow());
        ivHelpAndFAQsBack =findViewById(R.id.ivHelpAndFAQsBack);

        ivHelpAndFAQsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
    }
}