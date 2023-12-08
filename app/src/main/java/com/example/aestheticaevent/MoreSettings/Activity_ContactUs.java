package com.example.aestheticaevent.MoreSettings;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.Tools;

public class Activity_ContactUs extends AppCompatActivity {
    private static final int YOUR_PERMISSION_REQUEST_CODE = 123;
    TextView call, email, sendYourMessage;
    ImageView ivContactBack, IvContactUsShare, IvContactUsWhatsApp, IvContactUsInstagram;
    Tools tools;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        ivContactBack = findViewById(R.id.ivContactBack);
        IvContactUsShare = findViewById(R.id.IvContactUsShare);
        IvContactUsWhatsApp = findViewById(R.id.IvContactUsWhatsApp);
        IvContactUsInstagram = findViewById(R.id.IvContactUsInstagram);
        tools=new Tools(this);
        tools.ScreenshotBlock(getWindow());
        call = findViewById(R.id.call);
        email = findViewById(R.id.email);
        sendYourMessage = findViewById(R.id.sendYourMessage);

        ivContactBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the CALL_PHONE permission is granted
                if (ContextCompat.checkSelfPermission(Activity_ContactUs.this, Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Permission is already granted, initiate the phone call
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+919081463454"));
                    startActivity(callIntent);
                } else {
                    // Permission is not granted, request it from the user
                    ActivityCompat.requestPermissions(Activity_ContactUs.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            YOUR_PERMISSION_REQUEST_CODE);
                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("dudharejiyaparas@gmail.com") + "?subject=" +
                        Uri.encode("your email id ") + "&body=" + Uri.encode("");

                Uri uri = Uri.parse(uriText);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        sendYourMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openWhatsApp("9081463454");

            }
        });

        IvContactUsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String share = "Look All Programming";
                String subject = "https://play.google.com/store/apps/details?id=in.seekmyvision.seekmyvision";
                intent.putExtra(Intent.EXTRA_SUBJECT, share);
                intent.putExtra(Intent.EXTRA_TEXT, subject);
                startActivity(Intent.createChooser(intent, "Seek my vision"));
            }
        });

        IvContactUsWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openWhatsApp("9081463454");

            }
        });

        IvContactUsInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.instagram.com/esthetica_event/");
            }

            private void gotoUrl(String s) {
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
    }

    private void openWhatsApp(String phoneNumber) {


        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == YOUR_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+919081463454"));
                startActivity(callIntent);
            } else {
            }
        }
    }
}
