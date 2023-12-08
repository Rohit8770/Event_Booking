package com.example.aestheticaevent.User;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.UserResponse.RegisterResponse;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.Tools;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class Activity_SignUp extends AppCompatActivity {
    EditText etSignUpName, etSignUpEmail, etSignUpPassword, etSignUpCorrectPassword, etSignUpPhone;
    ImageView ivSignUpBack, ivSignUpPasswordCloseEye, ivSignUpCPCloseEye, ivSignUpGoogleLogin, ivSignUpFacebookLogin;
    TextView txEventLogin;
    CardView cvSignUpButton;
    Restcall restcall;
    boolean isPasswordVisible = false;
    Tools tools;
    CircleImageView cameraivProfileCamera, cameraivProfileUser;
    String currentPhotoPath = "" , tokenId;
    private File currentPhotoFile;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    ActivityResultLauncher<Intent> cameraLauncher = null;
    String imageId;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tools = new Tools(this);
        cameraivProfileCamera = findViewById(R.id.cameraivProfileCamera);
        cameraivProfileUser = findViewById(R.id.cameraivProfileUser);
        etSignUpPhone = findViewById(R.id.etSignUpPhone);
        sharedPreference  = new SharedPreference(Activity_SignUp.this);
        tools.ScreenshotBlock(getWindow());
        etSignUpName = findViewById(R.id.etSignUpName);
        etSignUpEmail = findViewById(R.id.etSignUpEmail);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
      //  etSignUpCorrectPassword = findViewById(R.id.etSignUpCorrectPassword);
        ivSignUpBack = findViewById(R.id.ivSignUpBack);
        ivSignUpPasswordCloseEye = findViewById(R.id.ivSignUpPasswordCloseEye);
       // ivSignUpCPCloseEye = findViewById(R.id.ivSignUpCPCloseEye);
        txEventLogin = findViewById(R.id.txEventLogin);
        cvSignUpButton = findViewById(R.id.cvSignUpButton);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tokenId=sharedPreference.getStringvalue(VariableBag.Key_Token);
        cameraivProfileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    currentPhotoPath = "";
                    openGallery();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void openGallery() {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                galleryLauncher.launch(galleryIntent);
            }

            ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Handle the selected image from the gallery
                    Uri selectedImageUri = result.getData().getData();
                    currentPhotoPath = Tools.getRealPathFromURI(Activity_SignUp.this, selectedImageUri);
                    Tools.displayImage(Activity_SignUp.this, cameraivProfileUser, currentPhotoPath);
                } else {
                    Toast.makeText(Activity_SignUp.this, "Gallery selection canceled", Toast.LENGTH_SHORT).show();
                }
            });
        });




        txEventLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Tools.displayImage(Activity_SignUp.this, cameraivProfileUser, currentPhotoPath);
            } else {
                Toast.makeText(this, "Not", Toast.LENGTH_SHORT).show();
            }
        });
        cameraivProfileCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    currentPhotoPath = "";
                    if (checkCameraPermission()) {
                        openCamera();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ivSignUpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivSignUpPasswordCloseEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(etSignUpPassword, ivSignUpPasswordCloseEye);
            }
        });

     /*   ivSignUpCPCloseEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(etSignUpCorrectPassword, ivSignUpCPCloseEye);
            }
        });*/

        cvSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   tools.showLoading();
                String name = etSignUpName.getText().toString().trim();
                String email = etSignUpEmail.getText().toString().trim();
                String password = etSignUpPassword.getText().toString().trim();
                String mobile = etSignUpPhone.getText().toString().trim();
          //      String confirmPassword = etSignUpCorrectPassword.getText().toString().trim();

                if (name.isEmpty()) {
                    etSignUpName.setError("Name is required");
                } else if (!isValidEmail(email)) {
                    etSignUpEmail.setError("Enter a valid email address");
                } else if (!isValidPassword(password)) {
                    etSignUpPassword.setError("Password must be strong");
                }  else if (currentPhotoPath.isEmpty()) {
                    Toast.makeText(Activity_SignUp.this, "Please select a profile picture", Toast.LENGTH_SHORT).show();
                } else if (mobile.isEmpty()) {
                    etSignUpPhone.setError("Please enter mobile number");
                } else {
                    CallRegisterUser();
                }
            }
        });
    }

    private void togglePasswordVisibility(EditText editText, ImageView imageView) {
        isPasswordVisible = !isPasswordVisible;
        int inputType = isPasswordVisible ?
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;

        editText.setInputType(inputType);
        imageView.setImageResource(isPasswordVisible ?
                R.drawable.open_eye :
                R.drawable.close_eye_icon);
    }

    // Email validation method
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.aestheticaevent",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraLauncher.launch(takePictureIntent);
            }
        }
    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoFile = image;
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void CallRegisterUser() {
        tools.showLoading();
        RequestBody tag = RequestBody.create(MediaType.parse("text/plain"), "addUser");
        RequestBody userName = RequestBody.create(MediaType.parse("text/plain"), etSignUpName.getText().toString().trim());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etSignUpEmail.getText().toString().trim());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), etSignUpPassword.getText().toString().trim());
        MultipartBody.Part fileToUpload = null;
        RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), etSignUpPhone.getText().toString().trim());
        RequestBody TokenId = RequestBody.create(MediaType.parse("text/plain"),tokenId);

        if (fileToUpload == null && currentPhotoPath != "") {
            try {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                File file = new File(currentPhotoPath);
                RequestBody rbPhoto = RequestBody.create(MediaType.parse("multipart/from-data"), file);
                fileToUpload = MultipartBody.Part.createFormData("user_image", file.getName(), rbPhoto);

            } catch (Exception e) {
                Toast.makeText(this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        MultipartBody.Part finalFileToUpload = fileToUpload;



                restcall.RegisterUser(tag, userName, email, password, finalFileToUpload, mobile, TokenId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<RegisterResponse>() {
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
                                        Toast.makeText(Activity_SignUp.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onNext(RegisterResponse registerResponse) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tools.stopLoading();
                                        if (registerResponse.getStatus().equals(VariableBag.SUCCESS_CODE)) {
                                            etSignUpName.setText("");
                                            etSignUpEmail.setText("");
                                            etSignUpPassword.setText("");
                                        //    etSignUpCorrectPassword.setText("");
                                            etSignUpPhone.setText("");
                                            finish();
                                        }
                                        Toast.makeText(Activity_SignUp.this, "" + registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
            }

}
