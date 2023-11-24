package com.example.aestheticaevent.User;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.aestheticaevent.ForgotPassword.Activity_ForgotPassword;
import com.example.aestheticaevent.HomeScreen.Activity_HomeScreen;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.UserResponse.LoginResponse;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class Activity_SignIn extends AppCompatActivity {
    EditText etSignInEmail, etSignInPassword;
    ImageView ivSignInCloseEye;
    TextView tvSignInSignUp, tvSignInForgotPassword;
    CardView cvSignInButton;
    Restcall restcall;
    Button btnBack;
    SharedPreference sharedPreference;
    boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        sharedPreference=new SharedPreference(this);
        etSignInEmail = findViewById(R.id.etSignInEmail);
        etSignInPassword = findViewById(R.id.etSignInPassword);
        ivSignInCloseEye = findViewById(R.id.ivSignInCloseEye);
        tvSignInSignUp = findViewById(R.id.tvSignInSignUp);
        tvSignInForgotPassword = findViewById(R.id.tvSignInForgotPassword);
        cvSignInButton = findViewById(R.id.cvSignInButton);

        restcall= RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);



        tvSignInForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_SignIn.this, Activity_ForgotPassword.class);
                startActivity(intent);
            }
        });


        if (sharedPreference.isLoggedIn()){
            openHomePage();
        }else{
            getContext();
        }

        cvSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etSignInEmail.getText().toString().trim();
                String password = etSignInPassword.getText().toString().trim();

                // Validate email format
                if (!isValidEmail(email)) {
                    etSignInEmail.setError("Enter a valid email address");
                    return;
                }

                // Validate password format
                if (!isValidPassword(password)) {
                    etSignInPassword.setError("Password must be Strong");
                    return;
                }
                LoginUser();
            }
        });

        ivSignInCloseEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                isPasswordVisible = !isPasswordVisible;

                // Update the EditText's input type based on visibility
                etSignInPassword.setInputType(isPasswordVisible ?
                        android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                        android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

                // Update the ImageView based on visibility
                ivSignInCloseEye.setImageResource(isPasswordVisible ?
                        R.drawable.open_eye :
                        R.drawable.close_eye_icon);
            }
        });

        tvSignInSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_SignIn.this, Activity_SignUp.class);
                startActivity(intent);
            }
        });
    }
    private void openHomePage(){
        startActivity(new Intent(Activity_SignIn.this, Activity_HomeScreen.class));
        finish();
    }

    // Email validation method
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    // Password validation method
    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private void LoginUser() {
        restcall.LoginUser("login", etSignInEmail.getText().toString().trim(),
                        etSignInPassword.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(Activity_SignIn.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (loginResponse.getStatus().equals(VariableBag.SUCCESS_CODE)) {
                                    etSignInEmail.setText("");
                                    etSignInPassword.setText("");
                                    sharedPreference.setLoggedIn(true);
                                    openHomePage();
                                    sharedPreference.setStringvalue("user_id", loginResponse.getUserId());
                                    sharedPreference.setStringvalue("userName", loginResponse.getUsername());
                                    sharedPreference.setStringvalue("email", loginResponse.getEmail());
                                }
                                Toast.makeText(Activity_SignIn.this, ""+loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


    }
}
