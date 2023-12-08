package com.example.aestheticaevent.ActivityFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.Activity_SignIn;
import com.example.aestheticaevent.Utils.Tools;

public class ThirdActivity extends AppCompatActivity {
TextView tvNext,tvSkip3;
Tools tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        tools=new Tools(this);
        tvNext=findViewById(R.id.tvNext);
        tvSkip3=findViewById(R.id.tvSkip3);
        tools.ScreenshotBlock(getWindow());
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