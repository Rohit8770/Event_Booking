package com.example.aestheticaevent.ActivityFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aestheticaevent.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRActivity extends AppCompatActivity {

    ImageView qrcode_img;
    EditText qrcode_txt;
    Button qrcode_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qractivity);

        qrcode_img=findViewById(R.id.qrcode_img);
        qrcode_txt=findViewById(R.id.qrcode_txt);
        qrcode_btn=findViewById(R.id.qrcode_btn);

        qrcode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= qrcode_txt.getText().toString();

               try {
                   generateQRcode(text);
               } catch (WriterException e) {
                   e.printStackTrace();
                   Toast.makeText(QRActivity.this, "QR generate failed", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    public  void  generateQRcode(String text) throws WriterException {
        MultiFormatWriter writer=new MultiFormatWriter();

        BitMatrix bitMatrix=writer.encode(text, BarcodeFormat.QR_CODE,200,200);
        BarcodeEncoder encoder=new BarcodeEncoder();
        Bitmap bitmap=encoder.createBitmap(bitMatrix);
        qrcode_img.setImageBitmap(bitmap);

    }
}