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
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aestheticaevent.HomeScreen.Activity_HomeScreen;
import com.example.aestheticaevent.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_MyProfile extends AppCompatActivity {
    EditText etProfileEditEmail, etSignUpName;
    CardView cvProfileEditButton;
    ImageView ivProfileBack;
    CircleImageView civProfileUser, civProfileCamera;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    String currentPhotoPath = "";
    ActivityResultLauncher<Intent> cameraLauncher;
    File currecntPhotoFile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        cvProfileEditButton = findViewById(R.id.cvProfileEditButton);

        etProfileEditEmail = findViewById(R.id.etProfileEditEmail);
        etSignUpName = findViewById(R.id.etSignUpName);

        ivProfileBack = findViewById(R.id.ivProfileBack);

        civProfileCamera = findViewById(R.id.civProfileCamera);
        civProfileUser = findViewById(R.id.civProfileUser);

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
                // Camera capture was successful, handle the result.
                Glide
                        .with(Activity_MyProfile.this)
                        .load(currentPhotoPath)
                        .placeholder(R.drawable.person_image)
                        .into(civProfileUser);
            } else {
                Toast.makeText(this, "Not", Toast.LENGTH_SHORT).show();
            }
        });
        cvProfileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}