package com.example.cameraapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.exifinterface.media.ExifInterface;

import com.example.cameraapp.utils.CameraPreview;
import com.example.cameraapp.utils.DrawingView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.cache.DiskLruCache;

public class Camerafilter extends AppCompatActivity {
    private static final String TAG = "mytag";
    private static final int FOCUS_AREA_SIZE = 300;
    public static Bitmap bitmap;
    String AdditionalLightNumber;
    String Lightnumber;
    ImageView btnClick;
    ImageView btnIconCam;
    ImageView btnRestore;
    LinearLayoutCompat btnSettings;
    ImageView btn_back;
    LinearLayoutCompat btnswitchCamera;
    private boolean cameraFront = false;
    private LinearLayout cameraPreview;
    String fontcamera = "";
    ImageView iconFilter;
    String light;
    Switch btnAElock;
    /* access modifiers changed from: private */
    public Camera mCamera;
    private Camera.PictureCallback mPicture;
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            boolean unused = Camerafilter.this.safeToTakePicture = true;
            Camerafilter.bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Log.d(Camerafilter.TAG, "bitmap--?" + Camerafilter.bitmap);
            Intent intent = new Intent(Camerafilter.this, Captureimage.class);
            intent.putExtra("camvalue", Camerafilter.this.fontcamera);
            intent.putExtra("light", Camerafilter.this.light);
            intent.putExtra("lightadditional", Camerafilter.this.AdditionalLightNumber);
            intent.putExtra("lightnumber", Camerafilter.this.Lightnumber);
            intent.putExtra("phordslr", Camerafilter.this.phordslr);
            intent.putExtra("totallight", Camerafilter.this.totallight);
            Camerafilter.this.startActivity(intent);
        }
    };
    private CameraPreview mPreview;
    private Context myContext;
    String phordslr = "";
    /* access modifiers changed from: private */
    public boolean safeToTakePicture = false;
    String totallight;
    private DrawingView drawingView;
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_camerafilter);
        getWindow().setFormat(0);
        Intent intent = getIntent();
        this.AdditionalLightNumber = intent.getStringExtra("lightadditional");
        this.Lightnumber = intent.getStringExtra("lightnumber");
        this.light = intent.getStringExtra("light");
        this.phordslr = intent.getStringExtra("phordslr");
        this.totallight = intent.getStringExtra("totallight");
        this.btnSettings = (LinearLayoutCompat) findViewById(R.id.btnSettings);
        this.btn_back = (ImageView) findViewById(R.id.btn_back);
        this.btnClick = (ImageView) findViewById(R.id.btnClick);
        this.btnRestore = (ImageView) findViewById(R.id.btnRestore);
        this.btnswitchCamera = (LinearLayoutCompat) findViewById(R.id.btnswitchCamera);
        this.btnIconCam = (ImageView) findViewById(R.id.btnIconCam);
        drawingView = (DrawingView) findViewById(R.id.drawing_surface);
        btnAElock = findViewById(R.id.btnAElock);
        getWindow().addFlags(128);
        this.myContext = this;
        Camera open = Camera.open();
        this.mCamera = open;
        open.setDisplayOrientation(90);
        this.fontcamera = ExifInterface.GPS_MEASUREMENT_3D;
        Log.d(TAG, "value-->" + this.fontcamera);
        this.cameraPreview = (LinearLayout) findViewById(R.id.cPreview);
        CameraPreview cameraPreview2 = new CameraPreview(this.myContext, this.mCamera);
        this.mPreview = cameraPreview2;
        this.cameraPreview.addView(cameraPreview2, 0);
        this.mCamera.startPreview();
        mPreview.setDrawingView(drawingView);
        safeToTakePicture = true;
        mPreview.aflock=true;

//        this.mPreview.setZOrderOnTop(true);
//        this.mPreview.getHolder().setFormat(-3);
        onClick();
    }

    public void onClick() {
        this.btnRestore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Camerafilter.this.showDialog();
            }
        });
        this.btnIconCam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Camerafilter.this.showDialogCam();
            }
        });
        this.btnClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Camerafilter.this.safeToTakePicture) {
                    Camerafilter.this.mCamera.autoFocus(new Camera.AutoFocusCallback() {
                        public void onAutoFocus(boolean success, Camera camera) {
                            if (success) {
                                Camerafilter.this.mCamera.takePicture((Camera.ShutterCallback) null, (Camera.PictureCallback) null, Camerafilter.this.mPictureCallback);
                                boolean unused = Camerafilter.this.safeToTakePicture = false;
                            }
                        }
                    });
                }
            }
        });
        this.btnswitchCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Camera.getNumberOfCameras() > 1) {
                    Camerafilter.this.releaseCamera();
                    Camerafilter.this.chooseCamera();
                }
            }
        });
        this.btnSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Camerafilter.this, Camsettings.class);
                intent.putExtra("light", Camerafilter.this.light);
                intent.putExtra("lightadditional", Camerafilter.this.AdditionalLightNumber);
                intent.putExtra("lightnumber", Camerafilter.this.Lightnumber);
                intent.putExtra("phordslr", Camerafilter.this.phordslr);
                intent.putExtra("totallight", Camerafilter.this.totallight);
                Camerafilter.this.startActivity(intent);
            }
        });
        this.btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Camerafilter.this.onBackPressed();
            }
        });

        btnAElock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mPreview.aflock=false;

                } else {
                    mPreview.aflock=true;

                }

            }
        });

//        mPreview.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    focusOnTouch(event);
//                }
//                return true;
//            }
//        });
    }



    private int findFrontFacingCamera() {
        Toast.makeText(getApplicationContext(), "Front Camera", 0).show();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == 1) {
                int cameraId = i;
                this.cameraFront = true;
                return cameraId;
            }
        }
        return -1;
    }

    private int findBackFacingCamera() {
        Toast.makeText(getApplicationContext(), "Back Camera", 0).show();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == 0) {
                int cameraId = i;
                this.cameraFront = false;
                return cameraId;
            }
        }
        return -1;
    }

    public void onResume() {
        super.onResume();
        if (this.mCamera == null) {
            Camera open = Camera.open();
            this.mCamera = open;
            open.setDisplayOrientation(90);
            this.mPicture = this.mPictureCallback;
            this.mPreview.refreshCamera(this.mCamera);
            Log.d("nu", "null");
            return;
        }
        Log.d("nu", "no null");
    }

    public void chooseCamera() {
        if (this.cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                Camera open = Camera.open(cameraId);
                this.mCamera = open;
                open.setDisplayOrientation(90);
                this.fontcamera = DiskLruCache.VERSION_1;
                Log.d(TAG, "value-->" + this.fontcamera);
                this.mPicture = this.mPictureCallback;
                this.mPreview.refreshCamera(this.mCamera);
                return;
            }
            return;
        }
        int cameraId2 = findFrontFacingCamera();
        if (cameraId2 >= 0) {
            Camera open2 = Camera.open(cameraId2);
            this.mCamera = open2;
            open2.setDisplayOrientation(90);
            this.fontcamera = ExifInterface.GPS_MEASUREMENT_2D;
            Log.d(TAG, "value-->" + this.fontcamera);
            this.mPicture = this.mPictureCallback;
            this.mPreview.refreshCamera(this.mCamera);
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /* access modifiers changed from: private */
    public void releaseCamera() {
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.stopPreview();
            this.mCamera.setPreviewCallback((Camera.PreviewCallback) null);
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    public void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.my_dialog2);
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(R.id.btnRestore);
        ((TextView) dialog.findViewById(R.id.btnRestart)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Camerafilter.this.startActivity(new Intent(Camerafilter.this, MainActivity.class));
            }
        });
    }

    public void showDialogCam() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.my_dialog4);
        dialog.show();
        ((LinearLayout) dialog.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, Rsettings1.class);
        intent.putExtra("light", this.light);
        intent.putExtra("lightadditional", this.AdditionalLightNumber);
        intent.putExtra("lightnumber", this.Lightnumber);
        intent.putExtra("phordslr", this.phordslr);
        startActivity(intent);
        finish();
    }
}
