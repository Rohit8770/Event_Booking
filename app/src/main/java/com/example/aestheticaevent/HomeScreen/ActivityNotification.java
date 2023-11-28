package com.example.aestheticaevent.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.aestheticaevent.R;

public class ActivityNotification extends AppCompatActivity {

    ImageView ivNotificationBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ivNotificationBack=findViewById(R.id.ivNotificationBack);
        ivNotificationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityNotification.this, Activity_HomeScreen.class);
                startActivity(intent);
            }
        });
    }
}