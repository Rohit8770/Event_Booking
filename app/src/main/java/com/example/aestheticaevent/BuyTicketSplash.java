package com.example.aestheticaevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aestheticaevent.MoreSettings.Ticket.ActivityTicket;
import com.example.aestheticaevent.Utils.Tools;

public class BuyTicketSplash extends AppCompatActivity {
    ImageView ivBackGroundGIF;
    LinearLayout layIdDone;
    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket_splash);

        tools=new Tools(this);
        ivBackGroundGIF = findViewById(R.id.ivBackGroundGIF);
        layIdDone = findViewById(R.id.layIdDone);

        layIdDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(BuyTicketSplash.this, ActivityTicket.class);
                startActivity(i);
                tools.vibrate();
                tools.playBeepSound();
                finish();
            }
        });

        Glide.with(this)
                .asGif()
                .load(R.drawable.backgroud_gif)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivBackGroundGIF);
    }
}