package com.example.aestheticaevent.User;

import static android.content.ContentValues.TAG;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.aestheticaevent.HomeScreen.ActivityPhoneForgetPassword;
import com.example.aestheticaevent.HomeScreen.Activity_HomeScreen;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.UserResponse.LoginResponse;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.Tools;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
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
    Tools tools;
    ImageView googleAuth, facebookAuth;
    FirebaseDatabase database;
    FirebaseAuth mAuth, auth;
    GoogleSignInClient googleSignInClient;
    CallbackManager callbackManager;
    int Sign_In = 20;
    private Arrays Array;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tools = new Tools(this);
        tools.ScreenshotBlock(getWindow());
        sharedPreference = new SharedPreference(this);
        etSignInPassword = findViewById(R.id.etSignInPassword);
        tvSignInForgotPassword = findViewById(R.id.tvSignInForgotPassword);
        etSignInEmail = findViewById(R.id.etSignInEmail);
        btnBack = findViewById(R.id.btnBack);
        ivSignInCloseEye = findViewById(R.id.ivSignInCloseEye);
        tvSignInSignUp = findViewById(R.id.tvSignInSignUp);
        cvSignInButton = findViewById(R.id.cvSignInButton);

        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        sharedPreference.setOnboardingCompleted(this, true);

        if (sharedPreference.isLoggedIn()) {
            openHomePage();
        } else {
            getContext();
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Activity_SignIn.this, Activity_HomeScreen.class);
                startActivity(i);
            }
        });

        tvSignInForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cvSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tools.vibrate();
                tools.playBeepSound();

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
                isPasswordVisible = !isPasswordVisible;
                etSignInPassword.setInputType(isPasswordVisible ?
                        android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                        android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        facebookAuth = findViewById(R.id.facebookAuth);
        googleAuth = findViewById(R.id.googleAuth);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        AppEventsLogger.activateApp(this.getApplication());
        facebookAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookSignIn();
            }
        });

        if (auth.getCurrentUser() != null) {
            FirebaseUser user = auth.getCurrentUser();
            Intent intent = new Intent(Activity_SignIn.this, Activity_HomeScreen.class);
            intent.putExtra("user_name", user.getDisplayName());
            intent.putExtra("user_email", user.getEmail());
            // intent.putExtra("user_photo", user.getPhotoUrl().toString());
            if (user.getPhotoUrl() != null) {
                intent.putExtra("user_photo", user.getPhotoUrl().toString());
            } else {
                intent.putExtra("user_photo", ""); // or any other default value or handling you prefer
            }
            startActivity(intent);
        } else {
            googleAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (auth.getCurrentUser() != null) {
                        auth.signOut();
                    }
                    clearGoogleAccountInfo();
                }
            });
        }
    }
    private void openHomePage() {
        startActivity(new Intent(Activity_SignIn.this, Activity_HomeScreen.class));
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        tools.showLoading();
        restcall.LoginUser("login", etSignInEmail.getText().toString().trim(),
                        etSignInPassword.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
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
                                tools.stopLoading();
                                if (loginResponse.getStatus().equals(VariableBag.SUCCESS_CODE)) {
                                   /* etSignInEmail.setText("");
                                    etSignInPassword.setText("");*/
                                    sharedPreference.setLoggedIn(true);
                                    openHomePage();
                                    sharedPreference.setStringvalue("user_id", loginResponse.getUserId());
                                    sharedPreference.setStringvalue("userName", loginResponse.getUsername());
                                    sharedPreference.setStringvalue("email", loginResponse.getEmail());
                                    sharedPreference.setStringvalue("photo", loginResponse.getUser_image());
                                    sharedPreference.setStringvalue("mobile", loginResponse.getMobile());
                                }
                                Toast.makeText(Activity_SignIn.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }


    private void facebookSignIn() {
        LoginManager.getInstance().logInWithReadPermissions(this, Array.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //  Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    private void clearGoogleAccountInfo() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            googleSignInClient.revokeAccess()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                            } else {
                            }
                            googleSignIn();
                        }
                    });
        } else {
            googleSignIn();
        }
    }

    private void googleSignIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, Sign_In);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Sign_In) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (Exception e) {
                Toast.makeText(this, "Google login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", user.getUid());
                            map.put("email", user.getEmail());
                            map.put("name", user.getDisplayName());
                            map.put("profile", user.getPhotoUrl().toString());
                            database.getReference().child("users").child(user.getUid()).setValue(map);
                            Intent intent = new Intent(Activity_SignIn.this, Activity_HomeScreen.class);
                            intent.putExtra("user_name", user.getDisplayName());
                            intent.putExtra("user_email", user.getEmail());
                            intent.putExtra("user_photo", user.getPhotoUrl().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Activity_SignIn.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        //  Log.d("TAG", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", user.getUid());
                            map.put("email", user.getEmail());
                            map.put("name", user.getDisplayName());
                            map.put("profile", user.getPhotoUrl().toString());
                            database.getReference().child("users").child(user.getUid()).setValue(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> databaseTask) {
                                            if (databaseTask.isSuccessful()) {
                                                Intent intent = new Intent(Activity_SignIn.this, Activity_HomeScreen.class);
                                                intent.putExtra("user_name", user.getDisplayName());
                                                intent.putExtra("user_email", user.getEmail());
                                                intent.putExtra("user_photo", user.getPhotoUrl().toString());
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                //   Log.w(TAG, "signInWithCredential:failure", task.getException());
                                                Toast.makeText(Activity_SignIn.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(Activity_SignIn.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

  /*  @Override
   public void onBackPressed() {
        //   super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit")
                .setMessage("Are you sure you want to exit the app?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
        alertDialog.show();
    }*/

}