package com.example.aestheticaevent.MoreSettings;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aestheticaevent.HomeScreen.Activity_HomeScreen;
import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.DeleteListResponse;
import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.EditListResponse;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.User.Activity_SignIn;
import com.example.aestheticaevent.Utils.SharedPreference;
import com.example.aestheticaevent.Utils.Tools;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class Activity_MyProfile extends AppCompatActivity {
    EditText etProfileEditEmail, etSignUpName,etSignUpNumber;
    CardView cvProfileEditButton;
    ImageView ivProfileBack;
    Restcall restcall;
    CircleImageView imgUserProfile, civProfileCamera;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    String currentPhotoPath = "";
    ActivityResultLauncher<Intent> cameraLauncher;
    File currecntPhotoFile;
    String userId,userName,email,mobile;
    Tools tools;
    SharedPreference sharedPreference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        tools=new Tools(this);
        sharedPreference=new SharedPreference(Activity_MyProfile.this);
        cvProfileEditButton = findViewById(R.id.cvProfileEditButton);
        etProfileEditEmail = findViewById(R.id.etProfileEditEmail);
        etSignUpName = findViewById(R.id.etSignUpName);
        ivProfileBack = findViewById(R.id.ivProfileBack);
        etSignUpNumber = findViewById(R.id.etSignUpNumber);
        civProfileCamera = findViewById(R.id.civProfileCamera);
        imgUserProfile = findViewById(R.id.imgUserProfile);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);





        Intent i = getIntent();
        if (i != null) {
            userId = sharedPreference.getStringvalue("user_id");
            userName = sharedPreference.getStringvalue("userName");
            email = sharedPreference.getStringvalue("email");
            mobile = sharedPreference.getStringvalue("mobile");

            etSignUpName.setText(userName);
            etProfileEditEmail.setText(email);
            etSignUpNumber.setText(mobile);
        }



       /* etSignUpName.setText(sharedPreference.getStringvalue("userName"));
        etProfileEditEmail.setText(sharedPreference.getStringvalue("email"));*/
        String photoPath = sharedPreference.getStringvalue("photo");
        if (!TextUtils.isEmpty(photoPath)) {
            // Using Glide
            Glide.with(this)
                    .load(Uri.parse(photoPath))
                    .placeholder(R.drawable.person_image)
                    .into(imgUserProfile);
        }


        ivProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        civProfileCamera.setOnClickListener(new View.OnClickListener() {
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

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Glide
                        .with(Activity_MyProfile.this)
                        .load(currentPhotoPath)
                        .placeholder(R.drawable.person_image)
                        .into(imgUserProfile);
            } else {
                Toast.makeText(this, "Not", Toast.LENGTH_SHORT).show();
            }
        });
        cvProfileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editprofile();
                String email = etProfileEditEmail.getText().toString().trim();

                if (!isValidEmail(email)) {
                    etProfileEditEmail.setError("Enter a valid email address");
                    return;
                }
                Intent intent = new Intent(Activity_MyProfile.this, Activity_HomeScreen.class);
                intent.putExtra("photoPath", currentPhotoPath);
                intent.putExtra("fullName",etSignUpName.getText().toString().trim() );
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
                Toast.makeText(Activity_MyProfile.this, "Edit Successfully", Toast.LENGTH_SHORT).show();

            }

            private boolean isValidEmail(String target) {

                return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());

            }
        });
    }

    private void openCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
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
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }



    public void Editprofile() {


        String UserName = etSignUpName.getText().toString().trim();
        String Email = etProfileEditEmail.getText().toString().trim();

        if (UserName.isEmpty()){
            etSignUpName.setError("UserName cannot be empty");
            etSignUpName.requestFocus();
            tools.stopLoading();
            return;
        }

        else if (Email.isEmpty()){
            etProfileEditEmail.setError("UserName cannot be empty");
            etProfileEditEmail.requestFocus();
            tools.stopLoading();
            return;
        } else if (currentPhotoPath == null || currentPhotoPath.isEmpty()) {
            Toast.makeText(this, "Please select a new photo ", Toast.LENGTH_SHORT).show();
            tools.stopLoading();
        }

        tools.showLoading();
        RequestBody tag = RequestBody.create(MediaType.parse("text/plain"), "editprofile");
        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody UserNameb = RequestBody.create(MediaType.parse("text/plain"), etSignUpName.getText().toString().trim());
        RequestBody Emailb = RequestBody.create(MediaType.parse("text/plain"), etProfileEditEmail.getText().toString().trim());
        MultipartBody.Part fileToUpload = null;
        RequestBody mobileb = RequestBody.create(MediaType.parse("text/plain"), etSignUpNumber.getText().toString().trim());


        if (currentPhotoPath!=null && currentPhotoPath!=null){
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



        restcall.Editprofile(tag ,UserId,UserNameb,Emailb,fileToUpload,mobileb)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<EditListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Toast.makeText(Activity_MyProfile.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(EditListResponse editListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (editListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    etSignUpName.setText("");
                                    etProfileEditEmail.setText("");
                                    sharedPreference.setStringvalue("userName", etSignUpName.getText().toString().trim());
                                    sharedPreference.setStringvalue("email",etProfileEditEmail.getText().toString().trim());
                                    sharedPreference.setStringvalue("photo", editListResponse.getUserImage());
                                    sharedPreference.setStringvalue("mobile", editListResponse.getMobile());

                                    Toast.makeText(Activity_MyProfile.this, editListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }
}