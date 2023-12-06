package com.example.aestheticaevent.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aestheticaevent.R;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class ActivityPhoneForgetPassword extends AppCompatActivity {


    CountryCodePicker ccp;
    EditText t1;
    CardView b1;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mAuth = FirebaseAuth.getInstance();
        t1 = findViewById(R.id.t1);
        b1 = findViewById(R.id.b1);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(t1);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPhoneForgetPassword.this, ActivityOtpForget.class);
                intent.putExtra("mobile", ccp.getFullNumberWithPlus().replace("", ""));
                startActivity(intent);
            }
        });
    }
}