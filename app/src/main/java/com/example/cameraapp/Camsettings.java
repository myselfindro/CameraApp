package com.example.cameraapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.hardware.Camera;
import android.hardware.camera2.params.RggbChannelVector;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager3;


public class Camsettings extends AppCompatActivity {

    private static final String TAG = "mytag";
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
    String AdditionalLightNumber;
    String light;
    int totallights;
    String phordslr = "";
    SeekBar seekBarZoom, seekBarTemp;
    Canvas canvas;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    private String filtercolor = "";

    @SuppressLint("SetTextI18n")
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
        safeToTakePicture = true;
        btnAutowbswitch.setChecked(true);


        PickerLayoutManager3 pickerLayoutManager3 = new PickerLayoutManager3(this, PickerLayoutManager3.HORIZONTAL, false);
        pickerLayoutManager3.setChangeAlpha(true);
        pickerLayoutManager3.setScaleDownBy(0.99f);
        pickerLayoutManager3.setScaleDownDistance(1.9f);
        adapter = new PickerAdapter2(this, gettempData(80), rv_temp);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv_temp);
        rv_temp.setLayoutManager(pickerLayoutManager3);
        rv_temp.setAdapter(adapter);

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
        adapter4 = new PickerAdapter3(this, getShutterspeed(0), rv_Shutter);
        rv_Shutter.setAdapter(adapter4);
        rv_Shutter.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .setMaxScale(1.1f)
                .build());

        rv_temp.scrollToPosition(40);
        rv_tint.scrollToPosition(14);
        btnTint.setText("Tint: 0");
        tvTemp.setText("Temperature: " + "5000" + "K");

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

                ll_bottom_view.setVisibility(View.VISIBLE);
                iconSetting.setVisibility(View.VISIBLE);
                iconSetting2.setVisibility(View.GONE);
            }
        });


        btnAutowbswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {


                    rv_temp.scrollToPosition(44);
                    rv_tint.scrollToPosition(14);
                    btnTint.setText("Tint: 0");
                    tvTemp.setText("Temperature: " + "5000" + "K");
                    Camera.Parameters param;
                    param = mCamera.getParameters();
                    param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
                    mCamera.setParameters(param);


                } else {
                    rv_temp.scrollToPosition(1);
                    rv_tint.scrollToPosition(14);
                    btnTint.setText("Tint: 0");
                    tvTemp.setText("Temperature: " + "1000" + "K");
                    Camera.Parameters param;
                    param = mCamera.getParameters();
                    param.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_INCANDESCENT);
                    mCamera.setParameters(param);


                }
            }
        });


        switch (totallights) {
            case 1:
                btnShutter.setText("Shutter Speed: 1/13");
                rv_Shutter.scrollToPosition(5);
                break;
            case 2:
                btnShutter.setText("Shutter Speed: 1/25");
                rv_Shutter.scrollToPosition(8);
                break;
            case 3:
                btnShutter.setText("Shutter Speed: 1/40");
                rv_Shutter.scrollToPosition(10);
                break;
            case 4:
                btnShutter.setText("Shutter Speed: 1/50");
                rv_Shutter.scrollToPosition(11);
                break;
            case 5:
                btnShutter.setText("Shutter Speed: 1/50");
                rv_Shutter.scrollToPosition(11);
                break;
            case 6:
                btnShutter.setText("Shutter Speed: 1/80");
                rv_Shutter.scrollToPosition(13);
                break;
            case 7:
                btnShutter.setText("Shutter Speed: 1/80");
                rv_Shutter.scrollToPosition(13);
                break;
            case 8:
                btnShutter.setText("Shutter Speed: 1/100");
                rv_Shutter.scrollToPosition(14);
                break;
            default:
                btnShutter.setText("Shutter Speed: 1/25");
                rv_Shutter.scrollToPosition(8);
                break;
        }


    }


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

//            if (val < -130) {
//                filtercolor = "#664EBB19";
//                mPreview.setBackgroundColor(Color.parseColor("#664EBB19"));
//            }
//            if (val >= -130 && val < -90) {
//                filtercolor = "#6619BB3C";
//                mPreview.setBackgroundColor(Color.parseColor("#6619BB3C"));
//            }
//            if (val >= -90 && val < -40) {
//                filtercolor = "#4D9BFF00";
//                mPreview.setBackgroundColor(Color.parseColor("#4D9BFF00"));
//            }
//
//            if (val >= -40 && val < 0) {
//                filtercolor = "#4DABFE2A";
//                mPreview.setBackgroundColor(Color.parseColor("#4DABFE2A"));
//            }
//
//            if (val >= 0 && val < 10) {
//                filtercolor = "#0040BF45";
//                mPreview.setBackgroundColor(Color.parseColor("#0040BF45"));
//            }
//            if (val >= 10 && val < 40) {
//                filtercolor = "#4DF34EFF";
//                mPreview.setBackgroundColor(Color.parseColor("#4DF34EFF"));
//            }
//            if (val >= 40 && val < 90) {
//                filtercolor = "#4DFF25DA";
//                mPreview.setBackgroundColor(Color.parseColor("#4DFF25DA"));
//            }
//            if (val >= 90 && val < 130) {
//                filtercolor = "#4DFD1FCB";
//                mPreview.setBackgroundColor(Color.parseColor("#4DFD1FCB"));
//            }
//            if (val >= 130 && val < 150) {
//                filtercolor = "#4DCF03CE";
//                mPreview.setBackgroundColor(Color.parseColor("#4DCF03CE"));
//            }


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


//        switch (s) {
//            case "1/4":
//            case "1/5":
//            case "1/6":
//            case "1/8":
//            case "1/10":
//                param.setExposureCompensation(20);
//                break;
//            case "1/13":
//            case "1/15":
//            case "1/20":
//            case "1/30":
//                param.setExposureCompensation(15);
//                break;
//            case "1/40":
//            case "1/50":
//            case "1/60":
//            case "1/80":
//            case "1/100":
//            case "1/125":
//            case "1/160":
//                param.setExposureCompensation(5);
//                break;
//            case "1/200":
//            case "1/250":
//            case "1/320":
//            case "1/400":
//            case "1/500":
//                param.setExposureCompensation(0);
//                break;
//            case "1/640":
//            case "1/800":
//            case "1/1000":
//            case "1/1250":
//            case "1/1600":
//                param.setExposureCompensation(-5);
//                break;
//            case "1/2000":
//            case "1/2500":
//            case "1/3200":
//                param.setExposureCompensation(-15);
//                break;
//            case "1/4000":
//            case "1/5000":
//            case "1/6400":
//            case "1/8000":
//                param.setExposureCompensation(-20);
//                break;
//            default:
//                param.setExposureCompensation(0);
//                break;
//        }


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
                        rv_Shutter.scrollToPosition(4);
                        break;
                    case 2:
                        rv_Shutter.scrollToPosition(8);
                        break;
                    case 3:
                        rv_Shutter.scrollToPosition(10);
                        break;
                    case 4:
                    case 5:
                        rv_Shutter.scrollToPosition(11);
                        break;
                    case 6:
                    case 7:
                        rv_Shutter.scrollToPosition(13);
                        break;
                    case 8:
                        rv_Shutter.scrollToPosition(14);
                        break;
                    default:
                        rv_Shutter.scrollToPosition(8);
                        break;
                }

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
        List<String> data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("1000");
        data.add("1100");
        data.add("1200");
        data.add("1300");
        data.add("1400");
        data.add("1500");
        data.add("1600");
        data.add("1700");
        data.add("1800");
        data.add("1900");
        data.add("2000");
        data.add("2100");
        data.add("2200");
        data.add("2300");
        data.add("2400");
        data.add("2500");
        data.add("2600");
        data.add("2700");
        data.add("2800");
        data.add("2900");
        data.add("3000");
        data.add("3100");
        data.add("3200");
        data.add("3300");
        data.add("3400");
        data.add("3500");
        data.add("3600");
        data.add("3700");
        data.add("3800");
        data.add("3900");
        data.add("4000");
        data.add("4100");
        data.add("4200");
        data.add("4300");
        data.add("4400");
        data.add("4500");
        data.add("4600");
        data.add("4700");
        data.add("4800");
        data.add("4900");
        data.add("5000");
        data.add("5100");
        data.add("5200");
        data.add("5300");
        data.add("5400");
        data.add("5500");
        data.add("5600");
        data.add("5700");
        data.add("5800");
        data.add("5900");
        data.add("6000");
        data.add("6100");
        data.add("6200");
        data.add("6300");
        data.add("6400");
        data.add("6500");
        data.add("6600");
        data.add("6700");
        data.add("6800");
        data.add("6900");
        data.add("7000");
        data.add("7100");
        data.add("7200");
        data.add("7300");
        data.add("7400");
        data.add("7500");
        data.add("7600");
        data.add("7700");
        data.add("7800");
        data.add("7900");
        data.add("8000");
        data.add("");
        data.add("");
        return data;
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
        List<String> data = new ArrayList<>();

        data.add("25");
        data.add("50");
        data.add("100");
        data.add("200");
        data.add("400");
        data.add("800");
        data.add("1600");


        return data;

    }


    public List<String> getShutterspeed(int count) {

        List<String> data = new ArrayList<>();
        data.add("1/4");
        data.add("1/5");
        data.add("1/6");
        data.add("1/8");
        data.add("1/10");
        data.add("1/13");
        data.add("1/15");
        data.add("1/20");
        data.add("1/25");
        data.add("1/30");
        data.add("1/40");
        data.add("1/50");
        data.add("1/60");
        data.add("1/80");
        data.add("1/100");
        data.add("1/125");
        data.add("1/160");
        data.add("1/200");
        data.add("1/250");
        data.add("1/320");
        data.add("1/400");
        data.add("1/500");
        data.add("1/640");
        data.add("1/800");
        data.add("1/1000");
        data.add("1/1250");
        data.add("1/1600");
        data.add("1/2000");
        data.add("1/2500");
        data.add("1/3200");
        data.add("1/4000");
        data.add("1/5000");
        data.add("1/6400");
        data.add("1/8000");

        return data;

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