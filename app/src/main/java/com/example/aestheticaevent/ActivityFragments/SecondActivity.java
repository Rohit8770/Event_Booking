package com.example.aestheticaevent.ActivityFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.Activity_SignIn;

public class SecondActivity extends AppCompatActivity {
    TextView tvNext2,tvSkip2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvNext2=findViewById(R.id.tvNext2);
        tvSkip2=findViewById(R.id.tvSkip2);

        tvSkip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SecondActivity.this, Activity_SignIn.class);
                startActivity(i);
            }
        });
        tvNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(i);
            }
        });
    }
}