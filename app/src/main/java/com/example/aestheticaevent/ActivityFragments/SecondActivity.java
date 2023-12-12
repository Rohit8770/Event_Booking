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

public class SecondActivity extends AppCompatActivity {
    TextView tvNext2,tvSkip2;
    Tools tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tools=new Tools(this);
        tvNext2=findViewById(R.id.tvNext2);
        tvSkip2=findViewById(R.id.tvSkip2);
        tools.ScreenshotBlock(getWindow());
        tvSkip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tools.vibrate();
                tools.playBeepSound();

                Intent i= new Intent(SecondActivity.this, Activity_SignIn.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        tvNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tools.vibrate();
                tools.playBeepSound();

                Intent i= new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}