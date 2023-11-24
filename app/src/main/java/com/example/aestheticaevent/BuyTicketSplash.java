package com.example.aestheticaevent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class BuyTicketSplash extends AppCompatActivity {
    ImageView ivBackGroundGIF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket_splash);

        ivBackGroundGIF = findViewById(R.id.ivBackGroundGIF);

        Glide.with(this)
                .asGif()
                .load(R.drawable.background_gif)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivBackGroundGIF);
    }
}