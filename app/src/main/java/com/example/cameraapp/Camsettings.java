package com.example.cameraapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.hardware.Camera;
import android.hardware.camera2.params.RggbChannelVector;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.cameraapp.adapter.PickerAdapter2;
import com.example.cameraapp.adapter.PickerAdapter3;
import com.example.cameraapp.utils.CameraPreview;
import com.example.cameraapp.utils.DrawingView;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager3;


public class Camsettings extends AppCompatActivity {

    private static final String TAG = "mytag";
    private static final int FOCUS_AREA_SIZE = 300;
    ImageView btn_back, iconSetting, iconFilter, btnClick, btnRestore, btnClose, iconSetting2, btnIconCam;
    TextView btnWhite, btnExpose, btnZoom, tvTemp, tvISO, btnTint, tvCool, tvWarm, btnShutter, tvlowISO, tvmaxISO;
    View Ulwhite, Ulexposure, Ulzoom;
    LinearLayout ll_whitebalance, ll_exposure, ll_zoom, ll_tint;
    DiscreteScrollView rv_ISO, rv_tint, rv_Shutter;
    RecyclerView rv_temp;
    PickerAdapter2 adapter;
    PickerAdapter2 adapter2;
    PickerAdapter2 adapter3;
    PickerAdapter3 adapter4;

    private Camera mCamera;
    private CameraPreview mPreview;
    private Context myContext;
    private LinearLayout cameraPreview;
    private boolean cameraFront = false;
    public static Bitmap bitmap;
    LinearLayoutCompat btnswitchCamera;
    private boolean safeToTakePicture = false;
    String fontcamera = "";
    LinearLayout ll_bottom_view;
    Switch btnAutowbswitch;
    String Lightnumber;
    String AdditionalLightNumber, CameraExposureSettingsIncrementalValuesresponse;
    String light;
    int totallights;
    String phordslr = "";
    SeekBar seekBarZoom, seekBarTemp;
    Canvas canvas;
    Switch btnAElock;
    String aelock = "1";
    SharedPreferences sharedPreferences;
    JSONArray Isoarry;
    SurfaceHolder holder;
    private List<String> shutterdata;
    private List<String> data;
    private List<String> whitebalancedata;

    private DrawingView drawingView;


    static {
        System.loadLibrary("NativeImageProcessor");
    }

    private String filtercolor = "";

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camsettings);
        Intent intent = getIntent();
        AdditionalLightNumber = intent.getStringExtra("lightadditional");
        Lightnumber = intent.getStringExtra("lightnumber");
        light = intent.getStringExtra("light");
        phordslr = intent.getStringExtra("phordslr");
        if (Lightnumber == null && AdditionalLightNumber == null) {
            totallights = 0;
        } else {
            totallights = Integer.parseInt(Lightnumber) + Integer.parseInt(AdditionalLightNumber);
        }
        sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
        CameraExposureSettingsIncrementalValuesresponse = sharedPreferences.getString("CameraExposureSettingsIncrementalValuesresponse", "");
        Log.d(TAG, "Responsedata-->"+CameraExposureSettingsIncrementalValuesresponse);
        drawingView = (DrawingView) findViewById(R.id.drawing_surface);
        btn_back = findViewById(R.id.btn_back);
        iconSetting = findViewById(R.id.iconSetting);
        btnWhite = findViewById(R.id.btnWhite);
        btnExpose = findViewById(R.id.btnExpose);
        btnZoom = findViewById(R.id.btnZoom);
        Ulwhite = findViewById(R.id.Ulwhite);
        Ulexposure = findViewById(R.id.Ulexposure);
        Ulzoom = findViewById(R.id.Ulzoom);
        ll_whitebalance = findViewById(R.id.ll_whitebalance);
        ll_exposure = findViewById(R.id.ll_exposure);
        ll_zoom = findViewById(R.id.ll_zoom);
        btnClick = findViewById(R.id.btnClick);
        btnRestore = findViewById(R.id.btnRestore);
        rv_temp = findViewById(R.id.rv_temp);
        tvTemp = findViewById(R.id.tvTemp);
        rv_ISO = findViewById(R.id.rv_ISO);
        tvISO = findViewById(R.id.tvISO);
        btnTint = findViewById(R.id.btnTint);
        tvCool = findViewById(R.id.tvCool);
        tvWarm = findViewById(R.id.tvWarm);
        rv_tint = findViewById(R.id.rv_tint);
        btnShutter = findViewById(R.id.btnShutter);
        tvlowISO = findViewById(R.id.tvlowISO);
        tvmaxISO = findViewById(R.id.tvmaxISO);
        rv_Shutter = findViewById(R.id.rv_Shutter);
        ll_bottom_view = findViewById(R.id.ll_bottom_view);
        btnClose = findViewById(R.id.btnClose);
        iconSetting2 = findViewById(R.id.iconSetting2);
        btnAutowbswitch = findViewById(R.id.btnAutowbswitch);
        btnIconCam = findViewById(R.id.btnIconCam);
        seekBarZoom = findViewById(R.id.seekBarZoom);
        ll_tint = findViewById(R.id.ll_tint);
        seekBarZoom.setMax(10);
        btnClick = findViewById(R.id.btnClick);
        btnswitchCamera = findViewById(R.id.btnswitchCamera);
        btnAElock = findViewById(R.id.btnAElock);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        myContext = this;

        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
        fontcamera = "3";
        Log.d(TAG, "value-->" + fontcamera);

        cameraPreview = (LinearLayout) findViewById(R.id.cPreview);
        mPreview = new CameraPreview(myContext, mCamera);
        cameraPreview.addView(mPreview, 0);
        mCamera.startPreview();
        mPreview.setDrawingView(drawingView);
        safeToTakePicture = true;
        btnAutowbswitch.setChecked(false);
        mPreview.aflock=true;


        PickerLayoutManager3 pickerLayoutManager3 = new PickerLayoutManager3(this, PickerLayoutManager3.HORIZONTAL, false);
        pickerLayoutManager3.setChangeAlpha(true);
        pickerLayoutManager3.setScaleDownBy(0.99f);
        pickerLayoutManager3.setScaleDownDistance(1.9f);
        adapter = new PickerAdapter2(this, gettempData(0), rv_temp);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv_temp);
        rv_temp.setLayoutManager(pickerLayoutManager3);
        rv_temp.setAdapter(adapter);

//        rv_temp.setOrientation(DSVOrientation.HORIZONTAL);
//        adapter = new PickerAdapter2(this, gettempData(0), rv_temp);
//        rv_temp.setAdapter(adapter);
//        rv_temp.setItemTransformer(new ScaleTransformer.Builder()
//        .setMinScale(0.8f)
//        .setMaxScale(1.1f)
//        .build());

        rv_ISO.setOrientation(DSVOrientation.HORIZONTAL);
        adapter2 = new PickerAdapter2(this, getisoData(0), rv_ISO);
        rv_ISO.setAdapter(adapter2);
        rv_ISO.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .setMaxScale(1.1f)
                .build());

        rv_tint.setOrientation(DSVOrientation.HORIZONTAL);
        adapter3 = new PickerAdapter2(this, gettintData(0), rv_tint);
        rv_tint.setAdapter(adapter3);
        rv_tint.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .setMaxScale(1.1f)
                .build());


        rv_Shutter.setOrientation(DSVOrientation.HORIZONTAL);
        adapter4 = new PickerAdapter3(this, getShutterspeed(""), rv_Shutter);
        rv_Shutter.setAdapter(adapter4);
        rv_Shutter.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .setMaxScale(1.1f)
                .build());



        if (whitebalancedata.contains("5000")){
            rv_temp.scrollToPosition(whitebalancedata.indexOf("5000"));
            tvTemp.setText("Temperature: " + "5000" + "K");
            Camera.Parameters param;
            param = mCamera.getParameters();
            param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
            mCamera.setParameters(param);

        }else {
            rv_temp.scrollToPosition(0);
            tvTemp.setText("Temperature: " + "1000" + "K");
            Camera.Parameters param;
            param = mCamera.getParameters();
            param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_INCANDESCENT);
            mCamera.setParameters(param);

        }
        rv_tint.scrollToPosition(14);
        btnTint.setText("Tint: 0");
        tvTemp.setText("Temperature: " + "5000" + "K");
        switch (totallights) {
            case 1:
                btnShutter.setText("Shutter Speed: 1/13");
                rv_Shutter.scrollToPosition(shutterdata.indexOf("1/13"));
                break;
            case 2:
                btnShutter.setText("Shutter Speed: 1/25");
                rv_Shutter.scrollToPosition(shutterdata.indexOf("1/25"));
                break;
            case 3:
                btnShutter.setText("Shutter Speed: 1/40");
                rv_Shutter.scrollToPosition(shutterdata.indexOf("1/40"));
                break;
            case 4:
                btnShutter.setText("Shutter Speed: 1/50");
                rv_Shutter.scrollToPosition(shutterdata.indexOf("1/50"));
                break;
            case 5:
                btnShutter.setText("Shutter Speed: 1/50");
                rv_Shutter.scrollToPosition(shutterdata.indexOf("1/50"));
                break;
            case 6:
                btnShutter.setText("Shutter Speed: 1/80");
                rv_Shutter.scrollToPosition(shutterdata.indexOf("1/80"));
                break;
            case 7:
                btnShutter.setText("Shutter Speed: 1/80");
                rv_Shutter.scrollToPosition(shutterdata.indexOf("1/80"));
                break;
            case 8:
                btnShutter.setText("Shutter Speed: 1/100");
                rv_Shutter.scrollToPosition(shutterdata.indexOf("1/100"));
                break;
            default:
                btnShutter.setText("Shutter Speed: 1/25");
                rv_Shutter.scrollToPosition(shutterdata.indexOf("1/25"));

                break;
        }

        btnAElock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    mPreview.aflock=false;
                    ll_bottom_view.setVisibility(View.GONE);
                    iconSetting2.setVisibility(View.VISIBLE);
                    iconSetting.setVisibility(View.GONE);

                } else {
                    mPreview.aflock=true;
                    ll_bottom_view.setVisibility(View.VISIBLE);
                    iconSetting.setVisibility(View.VISIBLE);
                    iconSetting2.setVisibility(View.GONE);

                }

            }
        });


       /* mPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (aelock.equals("1")) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        focusOnTouch(event);
                    }
                } else {

                }

                return true;
            }
        });*/


        seekBarZoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.d(TAG, "progress:" + progress);

                // YOur code here in set zoom for pinch zooming, sth like this
                if (mCamera.getParameters().isZoomSupported()) {
                    int zoomlevel = progress * 10;
                    if (zoomlevel == 100) {
                        zoomlevel = 99;
                    }
                    if (zoomlevel == 0) {
                        zoomlevel = 9;
                    }
                    Camera.Parameters params = mCamera.getParameters();

                    int maxZoom = params.getMaxZoom();
                    if (zoomlevel >= 0 && zoomlevel < maxZoom) {
                        params.setZoom(zoomlevel);
                        mCamera.setParameters(params);
                    } else {
                        // zoom parameter is incorrect
                    }


                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                Log.d(TAG, "onStartTrackingTouch");

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Log.d(TAG, "onStartTrackingTouch");

            }
        });


        pickerLayoutManager3.setOnScrollStopListener(new PickerLayoutManager3.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
                String value = ((TextView) view).getText().toString();
                Camera.Parameters param;
                param = mCamera.getParameters();
                String supportedValues = param.get("aperture");
                Log.d(TAG, "values-->" + supportedValues);

                try {
                    int val = Integer.parseInt(value);
                    if (val < 2000) {
                        param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_INCANDESCENT);
                    }
                    if (val >= 2000 && val < 4000) {
                        param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_FLUORESCENT);
                    }
                    if (val >= 4000 && val < 5000) {
                        param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
                    }
                    if (val >= 5000 && val < 6000) {
                        param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_DAYLIGHT);
                    }
                    if (val >= 6000 && val < 8000) {
                        param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_CLOUDY_DAYLIGHT);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                mCamera.setParameters(param);
                Log.i(" Progress ", "Supported White Balance Modes:" + param.getWhiteBalance().toString());
                tvTemp.setText("Temperature: " + value + "K");
                btnAutowbswitch.setChecked(false);


            }
        });

//        rv_temp.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>(){
//
//            @Override
//            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
//
//                onItemChangedTemp(adapter.dataList2.get(adapterPosition));
//
//
//            }
//        });


        rv_ISO.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                onItemChangedISO(adapter2.dataList2.get(adapterPosition));

            }
        });


        rv_tint.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                onItemChangedtint(adapter3.dataList2.get(adapterPosition));
            }
        });


        rv_Shutter.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                onItemChangedShutter(adapter4.dataList2.get(adapterPosition));
            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();

            }
        });

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (safeToTakePicture) {

                    mCamera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success, Camera camera) {

                            if (success) {

                                mCamera.takePicture(null, null, mPictureCallback);
                                safeToTakePicture = false;
                            }
                        }
                    });

                }
            }
        });


        btnswitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int camerasNumber = Camera.getNumberOfCameras();
                if (camerasNumber > 1) {

                    releaseCamera();
                    chooseCamera();
                }
            }
        });

        btnIconCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogCam();

            }
        });

        btnShutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvlowISO.setVisibility(View.GONE);
                tvmaxISO.setVisibility(View.GONE);
                rv_ISO.setVisibility(View.GONE);
                rv_Shutter.setVisibility(View.VISIBLE);
                tvISO.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.white));
                btnShutter.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.yellow));
            }
        });

        tvISO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tvlowISO.setVisibility(View.VISIBLE);
                tvmaxISO.setVisibility(View.VISIBLE);
                rv_ISO.setVisibility(View.VISIBLE);
                rv_Shutter.setVisibility(View.GONE);
                tvISO.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.yellow));
                btnShutter.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.white));


            }
        });

        tvTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvCool.setText("Cool");
                tvWarm.setText("Warm");
                tvCool.setVisibility(View.VISIBLE);
                tvWarm.setVisibility(View.VISIBLE);
                rv_temp.setVisibility(View.VISIBLE);
                rv_tint.setVisibility(View.GONE);
                btnTint.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.white));
                tvTemp.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.yellow));


            }
        });


        btnTint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvCool.setText("Green");
                tvWarm.setText("Magenta");
                rv_temp.setVisibility(View.GONE);
                rv_tint.setVisibility(View.VISIBLE);
                ll_tint.setVisibility(View.VISIBLE);
                btnTint.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.yellow));
                tvTemp.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.white));


            }
        });

        btnWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_whitebalance.setVisibility(View.VISIBLE);
                ll_exposure.setVisibility(View.GONE);
                ll_zoom.setVisibility(View.GONE);
                Ulwhite.setVisibility(View.VISIBLE);
                Ulexposure.setVisibility(View.INVISIBLE);
                Ulzoom.setVisibility(View.INVISIBLE);
                btnWhite.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.colorAccent));
                btnExpose.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.white));
                btnZoom.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.white));


            }
        });


        btnExpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_whitebalance.setVisibility(View.GONE);
                ll_exposure.setVisibility(View.VISIBLE);
                ll_zoom.setVisibility(View.GONE);
                Ulexposure.setVisibility(View.VISIBLE);
                Ulwhite.setVisibility(View.INVISIBLE);
                Ulzoom.setVisibility(View.INVISIBLE);
                btnExpose.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.colorAccent));
                btnWhite.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.white));
                btnZoom.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.white));

            }
        });


        btnZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_whitebalance.setVisibility(View.GONE);
                ll_exposure.setVisibility(View.GONE);
                ll_zoom.setVisibility(View.VISIBLE);
                Ulexposure.setVisibility(View.INVISIBLE);
                Ulwhite.setVisibility(View.INVISIBLE);
                Ulzoom.setVisibility(View.VISIBLE);
                btnExpose.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.white));
                btnWhite.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.white));
                btnZoom.setTextColor(ContextCompat.getColor(Camsettings.this, R.color.colorAccent));
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iconSetting.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
        }


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_bottom_view.setVisibility(View.GONE);
                iconSetting2.setVisibility(View.VISIBLE);
                iconSetting.setVisibility(View.GONE);
            }
        });


        iconSetting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (aelock.equals("")){
                    Toast.makeText(getApplicationContext(),"AE/AF Lock is ON! Please trun off to edit settings",Toast.LENGTH_SHORT).show();
                }else{
                    ll_bottom_view.setVisibility(View.VISIBLE);
                    iconSetting.setVisibility(View.VISIBLE);
                    iconSetting2.setVisibility(View.GONE);
                }

            }
        });


        btnAutowbswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {


                    if (whitebalancedata.contains("5000")){
                        rv_temp.smoothScrollToPosition(whitebalancedata.indexOf("5000"));
//                        tvTemp.setText("Temperature: " + "5000" + "K");
                    }else {
                        rv_temp.smoothScrollToPosition(2);
//                        tvTemp.setText("Temperature: " + "1000" + "K");

                    }
                    rv_tint.scrollToPosition(14);
                    btnTint.setText("Tint: 0");
//                    Camera.Parameters param;
//                    param = mCamera.getParameters();
//                    param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
//                    mCamera.setParameters(param);


                } else {
                    rv_temp.smoothScrollToPosition(1);
                    tvTemp.setText("Temperature: " + "1100" + "K");
                    rv_tint.scrollToPosition(14);
                    btnTint.setText("Tint: 0");
//                    Camera.Parameters param;
//                    param = mCamera.getParameters();
//                    param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_INCANDESCENT);
//                    mCamera.setParameters(param);


                }
            }
        });


    }


//    public void onItemChangedTemp(String s){
//        Camera.Parameters param;
//        param = mCamera.getParameters();
//        String supportedValues = param.get("aperture");
//        Log.d(TAG, "values-->" + supportedValues);
//        try {
//            int val = Integer.parseInt(s);
//            if (val < 2000) {
//                param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_INCANDESCENT);
//            }
//            if (val >= 2000 && val < 4000) {
//                param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_FLUORESCENT);
//            }
//            if (val >= 4000 && val < 5000) {
//                param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
//            }
//            if (val >= 5000 && val < 6000) {
//                param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_DAYLIGHT);
//            }
//            if (val >= 6000 && val < 8000) {
//                param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_CLOUDY_DAYLIGHT);
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        mCamera.setParameters(param);
//        Log.i(" Progress ", "Supported White Balance Modes:" + param.getWhiteBalance().toString());
//        tvTemp.setText("Temperature: " + s + "K");
//        btnAutowbswitch.setChecked(false);
//
//    }




    private void onItemChangedISO(String s) {

        tvISO.setText("ISO: " + s);
        Camera.Parameters param;
        param = mCamera.getParameters();
        int min = param.getMinExposureCompensation();
        int max = param.getMaxExposureCompensation();

        try {
            int val = Integer.parseInt(s);
            if (val < 50) {
                param.setExposureCompensation(-20);
            }
            if (val >= 50 && val < 100) {
                param.setExposureCompensation(-15);
            }
            if (val >= 100 && val < 200) {
                param.setExposureCompensation(-10);
            }

            if (val >= 200 && val < 400) {
                param.setExposureCompensation(0);
            }

            if (val >= 400 && val < 800) {
                param.setExposureCompensation(10);
            }
            if (val >= 800 && val < 1600) {
                param.setExposureCompensation(20);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        mCamera.setParameters(param);


    }

    private void onItemChangedtint(String s) {

        btnTint.setText("Tint: " + s);
        try {
            int val = Integer.parseInt(s);

            if (val < -130) {
                filtercolor = "#4D00B16A";
                mPreview.setBackgroundColor(Color.parseColor("#3400B16A"));
            }
            if (val >= -130 && val < -120) {
                filtercolor = "#4D00B141";
                mPreview.setBackgroundColor(Color.parseColor("#3200B141"));
            }
            if (val >= -120 && val < -110) {
                filtercolor = "#4D00B11D";
                mPreview.setBackgroundColor(Color.parseColor("#3200B11D"));
            }

            if (val >= -110 && val < -90) {
                filtercolor = "#4D06B100";
                mPreview.setBackgroundColor(Color.parseColor("#3406B100"));
            }

            if (val >= -90 && val < -60) {
                filtercolor = "#4D1BB100";
                mPreview.setBackgroundColor(Color.parseColor("#341BB100"));
            }

            if (val >= -60 && val < -30) {
                filtercolor = "#4D4AB100";
                mPreview.setBackgroundColor(Color.parseColor("#344AB100"));
            }

            if (val >= -30 && val < 0) {
                filtercolor = "#4D6CB100";
                mPreview.setBackgroundColor(Color.parseColor("#346CB100"));
            }

            if (val >= 0 && val < 10) {
                filtercolor = "#4DE500FF";
                mPreview.setBackgroundColor(Color.parseColor("#0040BF45"));
            }
            if (val >= 10 && val < 30) {
                filtercolor = "#34E500FF";
                mPreview.setBackgroundColor(Color.parseColor("#34E500FF"));
            }
            if (val >= 30 && val < 60) {
                filtercolor = "#4DFF00F2";
                mPreview.setBackgroundColor(Color.parseColor("#34FF00F2"));
            }
            if (val >= 60 && val < 90) {
                filtercolor = "#4DFF00B7";
                mPreview.setBackgroundColor(Color.parseColor("#34FF00B7"));
            }
            if (val >= 90 && val < 110) {
                filtercolor = "#4DFF0084";
                mPreview.setBackgroundColor(Color.parseColor("#34FF0084"));
            }
            if (val >= 110 && val < 130) {
                filtercolor = "#4DFF0066";
                mPreview.setBackgroundColor(Color.parseColor("#34FF0066"));
            }

            if (val >= 130 && val < 150) {
                filtercolor = "#4DFF0000";
                mPreview.setBackgroundColor(Color.parseColor("#34FF0000"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Bitmap setTIntColorToBitmap(Bitmap bmp) {
        int[] co = new int[]{Color.parseColor(filtercolor), Color.parseColor(filtercolor),
                Color.parseColor(filtercolor), Color.parseColor(filtercolor),
                Color.parseColor(filtercolor)};
        float[] coP = new float[]{0.2f, 0.4f, 0.6f, 0.8f, 1.0f};

        Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);

        /* Create your gradient. */
        LinearGradient grad = new LinearGradient(0, 0, 0, canvas.getHeight(), co, coP, Shader.TileMode.CLAMP);

        /* Draw your gradient to the top of your bitmap. */
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setAlpha(110);
        p.setShader(grad);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), p);

        return bitmap;
    }

    private void onItemChangedShutter(String s) {

        btnShutter.setText("Shutter: " + s + "s");
        Camera.Parameters param;
        param = mCamera.getParameters();
        int min = param.getMinExposureCompensation();
        int max = param.getMaxExposureCompensation();

        switch (s) {
            case "1/4":
                param.setExposureCompensation(20);
                break;
            case "1/5":
                param.setExposureCompensation(19);
                break;
            case "1/6":
                param.setExposureCompensation(18);
                break;
            case "1/8":
                param.setExposureCompensation(17);
                break;
            case "1/10":
                param.setExposureCompensation(16);
                break;
            case "1/13":
                param.setExposureCompensation(15);
                break;
            case "1/15":
                param.setExposureCompensation(14);
                break;
            case "1/20":
                param.setExposureCompensation(13);
                break;
            case "1/30":
                param.setExposureCompensation(12);
                break;
            case "1/40":
                param.setExposureCompensation(11);
                break;
            case "1/50":
                param.setExposureCompensation(10);
                break;
            case "1/60":
                param.setExposureCompensation(9);
                break;
            case "1/80":
                param.setExposureCompensation(8);
                break;
            case "1/100":
                param.setExposureCompensation(7);
                break;
            case "1/160":
                param.setExposureCompensation(6);
                break;
            case "1/200":
                param.setExposureCompensation(5);
                break;
            case "1/250":
                param.setExposureCompensation(4);
                break;
            case "1/320":
                param.setExposureCompensation(3);
                break;
            case "1/400":
                param.setExposureCompensation(2);
                break;
            case "1/500":
                param.setExposureCompensation(1);
                break;
            case "1/640":
                param.setExposureCompensation(0);
                break;
            case "1/800":
                param.setExposureCompensation(-1);
                break;
            case "1/1000":
                param.setExposureCompensation(-2);
                break;
            case "1/1250":
                param.setExposureCompensation(-4);
                break;
            case "1/1600":
                param.setExposureCompensation(-6);
                break;
            case "1/2000":
                param.setExposureCompensation(-8);
                break;
            case "1/2500":
                param.setExposureCompensation(-10);
                break;
            case "1/3200":
                param.setExposureCompensation(-12);
                break;
            case "1/4000":
                param.setExposureCompensation(-14);
                break;
            case "1/5000":
                param.setExposureCompensation(-16);
                break;
            case "1/6400":
                param.setExposureCompensation(-18);
                break;
            case "1/8000":
                param.setExposureCompensation(-20);
                break;
            default:
                param.setExposureCompensation(0);
                break;
        }

        mCamera.setParameters(param);


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static RggbChannelVector computeTemperature(final int factor) {
        return new RggbChannelVector(0.635f + (0.0208333f * factor), 1.0f, 1.0f, 3.7420394f + (-0.0287829f * factor));
    }


    private int findFrontFacingCamera() {

        Toast.makeText(getApplicationContext(), "Front Camera", Toast.LENGTH_SHORT).show();
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;

    }


    private int findBackFacingCamera() {
        Toast.makeText(getApplicationContext(), "Back Camera", Toast.LENGTH_SHORT).show();
        int cameraId = -1;

        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                break;

            }

        }
        return cameraId;
    }


    public void onResume() {

        super.onResume();
        if (mCamera == null) {
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
            mPreview.refreshCamera(mCamera);
            Log.d("nu", "null");
        } else {
            Log.d("nu", "no null");
        }

    }


    public void chooseCamera() {
        if (cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {

                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90);
                fontcamera = "1";
                Log.d(TAG, "value-->" + fontcamera);
                mPreview.refreshCamera(mCamera);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {

                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90);
                fontcamera = "2";
                Log.d(TAG, "value-->" + fontcamera);
                mPreview.refreshCamera(mCamera);
            }
        }
    }


    public void showDialogCam() {
        LinearLayout btnOk;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.my_dialog4);
        dialog.show();


        btnOk = dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            ;

            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }


    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            safeToTakePicture = true;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (filtercolor.length() > 0) {
                bitmap = setTIntColorToBitmap(bitmap);
            }
            Log.d(TAG, "bitmap--?" + bitmap);
            Intent intent = new Intent(Camsettings.this, Captureimage2.class);
            intent.putExtra("camvalue", fontcamera);
            intent.putExtra("light", light);
            intent.putExtra("lightadditional", AdditionalLightNumber);
            intent.putExtra("lightnumber", Lightnumber);
            intent.putExtra("phordslr", phordslr);
            intent.putExtra("totallight", totallights);
            startActivity(intent);
        }
    };

    public void showDialog() {
        TextView btnRestore, btnRestart;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.my_dialog2);
        dialog.show();

        btnRestore = dialog.findViewById(R.id.btnRestore);
        btnRestart = dialog.findViewById(R.id.btnRestart);

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rv_ISO.scrollToPosition(0);
                switch (totallights) {
                    case 1:
                        btnShutter.setText("Shutter Speed: 1/13");
                        rv_Shutter.scrollToPosition(shutterdata.indexOf("1/13"));
                        break;
                    case 2:
                        btnShutter.setText("Shutter Speed: 1/25");
                        rv_Shutter.scrollToPosition(shutterdata.indexOf("1/25"));
                        break;
                    case 3:
                        btnShutter.setText("Shutter Speed: 1/40");
                        rv_Shutter.scrollToPosition(shutterdata.indexOf("1/40"));
                        break;
                    case 4:
                        btnShutter.setText("Shutter Speed: 1/50");
                        rv_Shutter.scrollToPosition(shutterdata.indexOf("1/50"));
                        break;
                    case 5:
                        btnShutter.setText("Shutter Speed: 1/50");
                        rv_Shutter.scrollToPosition(shutterdata.indexOf("1/50"));
                        break;
                    case 6:
                        btnShutter.setText("Shutter Speed: 1/80");
                        rv_Shutter.scrollToPosition(shutterdata.indexOf("1/80"));
                        break;
                    case 7:
                        btnShutter.setText("Shutter Speed: 1/80");
                        rv_Shutter.scrollToPosition(shutterdata.indexOf("1/80"));
                        break;
                    case 8:
                        btnShutter.setText("Shutter Speed: 1/100");
                        rv_Shutter.scrollToPosition(shutterdata.indexOf("1/100"));
                        break;
                    default:
                        btnShutter.setText("Shutter Speed: 1/25");
                        rv_Shutter.scrollToPosition(shutterdata.indexOf("1/25"));
                        break;
                }
                rv_temp.smoothScrollToPosition(44);
                rv_tint.scrollToPosition(14);
                btnTint.setText("Tint: 0");
                dialog.dismiss();
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Camsettings.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public List<String> gettempData(int count) {
        whitebalancedata = new ArrayList<String>();
        try {
            JSONObject res = new JSONObject(CameraExposureSettingsIncrementalValuesresponse);
            Isoarry = res.getJSONObject("data").getJSONArray("white_balance");
            whitebalancedata.add("");
            whitebalancedata.add("");
            for (int i = 0; i < Isoarry.length(); i++) {
                whitebalancedata.add(String.valueOf(Isoarry.get(i)));
            }
            whitebalancedata.add("");
            whitebalancedata.add("");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return whitebalancedata;
    }


    public List<String> gettintData(int count) {
        List<String> data = new ArrayList<>();
        data.add("-150");
        data.add("-140");
        data.add("-130");
        data.add("-120");
        data.add("-100");
        data.add("-90");
        data.add("-80");
        data.add("-70");
        data.add("-60");
        data.add("-50");
        data.add("-40");
        data.add("-30");
        data.add("-20");
        data.add("-10");
        data.add("0");
        data.add("10");
        data.add("20");
        data.add("30");
        data.add("40");
        data.add("50");
        data.add("60");
        data.add("70");
        data.add("80");
        data.add("90");
        data.add("100");
        data.add("110");
        data.add("120");
        data.add("130");
        data.add("140");
        data.add("150");
        return data;
    }

    public List<String> getisoData(int count) {
        data = new ArrayList<String>();
        try {
            JSONObject res = new JSONObject(CameraExposureSettingsIncrementalValuesresponse);
            Isoarry = res.getJSONObject("data").getJSONArray("iso");
            for (int i = 0; i < Isoarry.length(); i++) {
                data.add(String.valueOf(Isoarry.get(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;

    }


    public List<String> getShutterspeed(String isoval) {

        shutterdata = new ArrayList<>();
        try {
            JSONObject res = new JSONObject(CameraExposureSettingsIncrementalValuesresponse);
            Isoarry = res.getJSONObject("data").getJSONArray("shutter");
            for (int i = 0; i < Isoarry.length(); i++) {
                shutterdata.add(String.valueOf(Isoarry.get(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return shutterdata;

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, Rsettings1.class);
        intent.putExtra("light", light);
        intent.putExtra("lightadditional", AdditionalLightNumber);
        intent.putExtra("lightnumber", Lightnumber);
        intent.putExtra("phordslr", phordslr);
        startActivity(intent);
        finish();
    }
}