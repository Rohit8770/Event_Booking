package com.example.aestheticaevent.ActivityFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.aestheticaevent.BuyTicketSplash;
import com.example.aestheticaevent.MoreSettings.Ticket.ActivityTicket;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.Activity_SignIn;

public class ActivityDetails extends AppCompatActivity {

    CardView btnPreviou,btnNext,btnSkip;
    ImageView imgSteps;
    private int pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        btnNext=findViewById(R.id.btnNext);
        btnPreviou=findViewById(R.id.btnPreviou);
        btnSkip=findViewById(R.id.btnSkip);
        imgSteps=findViewById(R.id.imgSteps);


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivityDetails.this, Activity_SignIn.class);
                startActivity(i);
            }
        });
        btnPreviou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos>0){
                    pos--;
                    updateImage();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos++;
                updateImage();
                }
        });


    }
    void updateImage() {
        if (pos == 0) {
            imgSteps.setImageResource(R.drawable.imgfirst);
        } else if (pos == 1) {
            imgSteps.setImageResource(R.drawable.imgsecond);
        } else if (pos == 2) {
            imgSteps.setImageResource(R.drawable.imgthird);
        } else if (pos == 3) {
            Intent i=new Intent(ActivityDetails.this, Activity_SignIn.class);
            startActivity(i);
        }
        // Add more conditions if needed for other values of pos
    }




}