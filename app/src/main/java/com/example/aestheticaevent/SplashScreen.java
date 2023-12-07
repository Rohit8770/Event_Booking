package com.example.aestheticaevent;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aestheticaevent.ActivityFragments.FirsttActivity;
import com.example.aestheticaevent.HomeScreen.Activity_HomeScreen;
import com.example.aestheticaevent.User.Activity_SignIn;
import com.example.aestheticaevent.Utils.SharedPreference;

public class SplashScreen extends AppCompatActivity {
    ImageView splashScreenLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashScreenLogo = findViewById(R.id.splashScreenLogo);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1500);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashScreen.this, FirsttActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        // Start the animation
        splashScreenLogo.startAnimation(fadeIn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreference sharedPreference = new SharedPreference(this);
        if (sharedPreference.getLoggedIn()){
            startActivity(new Intent(this, Activity_HomeScreen.class));
            finish();
        }
    }
}
