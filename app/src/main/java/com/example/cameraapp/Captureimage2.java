package com.example.cameraapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class Captureimage2 extends AppCompatActivity {
    private static final String IMAGE_DIRECTORY = "/CustomImage";
    String AdditionalLightNumber;
    String Lightnumber;
    TextView btnDiscarbed;
    ImageView btnRestore;
    TextView btnSave;
    TextView btnShare;
    ImageView btn_back;
    Context context;
    String fontcamera;
    ImageView iconFilter;
    ImageView iconSetting;
    ImageView imgCaptured;
    String light;
    String phordslr = "";
    Bitmap rotatedBitmap;
    Bitmap scaledBitmap;
    String totallight;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_captureimage);
        Intent intent = getIntent();
        this.fontcamera = intent.getStringExtra("camvalue");
        this.AdditionalLightNumber = intent.getStringExtra("lightadditional");
        this.Lightnumber = intent.getStringExtra("lightnumber");
        this.light = intent.getStringExtra("light");
        this.phordslr = intent.getStringExtra("phordslr");
        this.totallight = intent.getStringExtra("totallight");
        this.btn_back = (ImageView) findViewById(R.id.btn_back);
        this.iconSetting = (ImageView) findViewById(R.id.iconSetting);
        this.imgCaptured = (ImageView) findViewById(R.id.imgCaptured);
        this.btnSave = (TextView) findViewById(R.id.btnSave);
        this.btnDiscarbed = (TextView) findViewById(R.id.btnDiscarbed);
        this.btnShare = (TextView) findViewById(R.id.btnShare);
        this.btnRestore = (ImageView) findViewById(R.id.btnRestore);
        if (this.fontcamera.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
            Matrix matrix = new Matrix();
            matrix.postRotate(270.0f);
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(Camsettings.bitmap, 4624, 3468, true);
            this.scaledBitmap = createScaledBitmap;
            Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap, 0, 0, createScaledBitmap.getWidth(), this.scaledBitmap.getHeight(), matrix, true);
            this.rotatedBitmap = createBitmap;
            this.imgCaptured.setImageBitmap(createBitmap);
        } else {
            Matrix matrix2 = new Matrix();
            matrix2.postRotate(90.0f);
            Bitmap createScaledBitmap2 = Bitmap.createScaledBitmap(Camsettings.bitmap, 4624, 3468, true);
            this.scaledBitmap = createScaledBitmap2;
            Bitmap createBitmap2 = Bitmap.createBitmap(createScaledBitmap2, 0, 0, createScaledBitmap2.getWidth(), this.scaledBitmap.getHeight(), matrix2, true);
            this.rotatedBitmap = createBitmap2;
            this.imgCaptured.setImageBitmap(createBitmap2);
        }
        this.btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Captureimage2.this.onBackPressed();
            }
        });
        this.btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Captureimage2 captureimage2 = Captureimage2.this;
                String unused = captureimage2.saveImage2(captureimage2.rotatedBitmap);
                Toast.makeText(Captureimage2.this.getApplicationContext(), "Image Save", 0).show();
                Intent intent = new Intent(Captureimage2.this, Camerafilter.class);
                intent.putExtra("light", Captureimage2.this.light);
                intent.putExtra("lightadditional", Captureimage2.this.AdditionalLightNumber);
                intent.putExtra("lightnumber", Captureimage2.this.Lightnumber);
                intent.putExtra("phordslr", Captureimage2.this.phordslr);
                intent.putExtra("totallight", Captureimage2.this.totallight);
                Captureimage2.this.startActivity(intent);
            }
        });
        this.btnDiscarbed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Captureimage2.this.getApplicationContext(), "Image Not Saved!", 0).show();
                Intent intent = new Intent(Captureimage2.this, Camerafilter.class);
                intent.putExtra("light", Captureimage2.this.light);
                intent.putExtra("lightadditional", Captureimage2.this.AdditionalLightNumber);
                intent.putExtra("lightnumber", Captureimage2.this.Lightnumber);
                intent.putExtra("phordslr", Captureimage2.this.phordslr);
                intent.putExtra("totallight", Captureimage2.this.totallight);
                Captureimage2.this.startActivity(intent);
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap icon = Captureimage2.this.rotatedBitmap;
                Intent share = new Intent("android.intent.action.SEND");
                share.setType("image/jpeg");
                ContentValues values = new ContentValues();
                values.put("title", "title");
                values.put("mime_type", "image/jpeg");
                Uri uri = Captureimage2.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                try {
                    OutputStream outstream = Captureimage2.this.getContentResolver().openOutputStream(uri);
                    icon.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                    outstream.close();
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
                share.putExtra("android.intent.extra.STREAM", uri);
                Captureimage2.this.startActivity(Intent.createChooser(share, "Share Image"));
            }
        });
        this.btnRestore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.iconSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Captureimage2.this, Camsettings.class);
                intent.putExtra("light", Captureimage2.this.light);
                intent.putExtra("lightadditional", Captureimage2.this.AdditionalLightNumber);
                intent.putExtra("lightnumber", Captureimage2.this.Lightnumber);
                intent.putExtra("phordslr", Captureimage2.this.phordslr);
                intent.putExtra("totallight", Captureimage2.this.totallight);
                Captureimage2.this.startActivity(intent);
            }
        });
    }

    /* access modifiers changed from: private */
    public String saveImage2(Bitmap image) {
        String savedImagePath = null;
        String imageFileName = "JPEG_" + Calendar.getInstance().getTimeInMillis() + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/MyStudio");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            galleryAddPic(savedImagePath);
        }
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        mediaScanIntent.setData(Uri.fromFile(new File(imagePath)));
        sendBroadcast(mediaScanIntent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, Camsettings.class);
        intent.putExtra("light", this.light);
        intent.putExtra("lightadditional", this.AdditionalLightNumber);
        intent.putExtra("lightnumber", this.Lightnumber);
        intent.putExtra("phordslr", this.phordslr);
        intent.putExtra("totallight", this.totallight);
        startActivity(intent);
    }
}
