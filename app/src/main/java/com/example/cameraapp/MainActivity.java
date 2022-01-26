package com.example.cameraapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.cameraapp.Allurl.Allurl;
import com.example.cameraapp.internet.CheckConnectivity;
import com.example.cameraapp.utils.PrefManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout btnNext, ll_LED, ll_fluorescnent, ll_Phone, ll_DSLR, ll_phoneselect, ll_dslrselect, ll_Phonedetails, ll_DSLRdetails;
    ImageView iconCam, iconPhone, btnInfo;
    TextView tvPhone, tvDSLR;
    LinearLayout btnSetting;
    String selectedlight = "";
    String phordslr = "";
    String studiolightresponse, numberoflightbyproductresponse, phoneaparture, DSLRExposureSettingsresponse, tokenresponse, VersionDetailsresponse,
            backgroundimageresponse, AppVersionresponse, AppContentresponse, CameraExposureSettingsIncrementalValuesresponse;
    private PrefManager prefManager;
    ImageView ll_bg;
    TextView tvSelectLight;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        processIntent(intent);
        phordslr = intent.getStringExtra("phordslr");
        if (phordslr == null) {
            phordslr = "phone";
        } else {
            phordslr = intent.getStringExtra("phordslr");
        }
        btnNext = findViewById(R.id.btnNext);
        ll_LED = findViewById(R.id.ll_LED);
        ll_fluorescnent = findViewById(R.id.ll_fluorescnent);
        ll_Phone = findViewById(R.id.ll_Phone);
        ll_DSLR = findViewById(R.id.ll_DSLR);
        ll_Phonedetails = findViewById(R.id.ll_Phonedetails);
        ll_DSLRdetails = findViewById(R.id.ll_DSLRdetails);
        iconCam = findViewById(R.id.iconCam);
        iconPhone = findViewById(R.id.iconPhone);
        tvPhone = findViewById(R.id.tvPhone);
        tvDSLR = findViewById(R.id.tvDSLR);
        btnSetting = findViewById(R.id.btnSetting);
        btnInfo = findViewById(R.id.btnInfo);
        ll_dslrselect = findViewById(R.id.ll_dslrselect);
        ll_phoneselect = findViewById(R.id.ll_phoneselect);
        tvSelectLight = findViewById(R.id.tvSelectLight);
        ll_bg = findViewById(R.id.ll_bg);

        requestAllPermission();


        if (phordslr.equals("phone")) {


            ll_dslrselect = findViewById(R.id.ll_dslrselect);
            ll_phoneselect = findViewById(R.id.ll_phoneselect);


        } else if (phordslr.equals("dslr")) {

            ll_phoneselect.setVisibility(View.GONE);
            ll_dslrselect.setVisibility(View.VISIBLE);

        }

        prefManager = new PrefManager(this);
//        prefManager.setFirstTimeLaunch(true);
        if (prefManager.isFirstTimeLaunch()) {
            allapi();
            prefManager.setFirstTimeLaunch(false);
        }else{

            offline();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    ;

    private void processIntent(Intent intent) {
        //get your extras
        /*if (intent != null && intent.getBooleanExtra("onTap", false)) {
            allapi();
        }*/
//        if (getIntent().getExtras()==null && getIntent().getExtras().keySet()==null){
//            Toast.makeText(MainActivity.this, "Old Data", Toast.LENGTH_LONG).show();
//        }else {
            allapi();
//        }

    }


    public void Onclick() {

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();

            }
        });

        ll_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.VISIBLE);
//                ll_DSLRdetails.setVisibility(View.GONE);
                iconPhone.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvPhone.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                ll_Phone.setBackgroundResource(R.drawable.bg1);
                ll_DSLR.setBackgroundResource(R.drawable.bg5);
                iconCam.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvDSLR.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                phordslr = "phone";

            }
        });


        ll_DSLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.GONE);
//                ll_DSLRdetails.setVisibility(View.VISIBLE);
                iconPhone.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvPhone.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                ll_Phone.setBackgroundResource(R.drawable.bg5);
                ll_DSLR.setBackgroundResource(R.drawable.bg1);
                iconCam.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvDSLR.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                phordslr = "dslr";

            }
        });


        ll_LED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_LED.setBackgroundResource(R.drawable.bg1);
                ll_fluorescnent.setBackgroundResource(R.drawable.border);
                selectedlight = "Led";

            }
        });


        ll_fluorescnent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_LED.setBackgroundResource(R.drawable.border);
                ll_fluorescnent.setBackgroundResource(R.drawable.bg1);
                selectedlight = "fluorescnent";

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedlight.length() == 0) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Please select which lighting type your MyStudio® unit has", Toast.LENGTH_LONG);
                    view = toast.getView();
                    view.setBackgroundResource(R.drawable.border3);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                    text.setTextColor(Color.parseColor("#000000"));
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();


                } else {

                    Intent intent = new Intent(MainActivity.this, SelectstudioLight.class);
                    intent.putExtra("light", selectedlight);
                    intent.putExtra("phordslr", phordslr);
                    startActivity(intent);
                }

            }
        });


        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();

            }
        });
    }

    public void offline(){

        //BackgroundImage
        sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
        backgroundimageresponse = sharedPreferences.getString("backgroundimageresponse", "");
        try {

            JSONObject result = new JSONObject(backgroundimageresponse);
            boolean success = result.getBoolean("status");
            if (success) {

                JSONObject response_data = result.getJSONObject("data");
                String image = response_data.getString("android_background_image");
                Glide.with(MainActivity.this)
                        .load(image)
                        .placeholder(R.drawable.bg)
                        .into(ll_bg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //AppContent
        sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
        AppContentresponse = sharedPreferences.getString("AppContentresponse", "");
        try {

            JSONObject result = new JSONObject(AppContentresponse);
            boolean success = result.getBoolean("status");
            if (success) {

                JSONArray dataarray = result.getJSONArray("data");
                for (int i = 0; i < dataarray.length(); i++) {
                    JSONObject dataobj = dataarray.getJSONObject(1);
                    String title = dataobj.getString("content");
                    tvSelectLight.setText(title);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void allapi() {

        //Studiolighturl

        if (CheckConnectivity.getInstance(getApplicationContext()).isOnline()) {

            showProgressDialog();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, Allurl.Studiolighturl,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {

                                Log.i("Response-->", String.valueOf(response));
                                studiolightresponse = String.valueOf(response);
                                //SharedPref
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("studiolightresponse", studiolightresponse);
                                editor.apply();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                            }

//                            hideProgressDialog();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgressDialog();
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("ert", error.toString());

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    9000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {

            Toast.makeText(getApplicationContext(), "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show();
        }

        //NumberOfLightsByProduct

        if (CheckConnectivity.getInstance(getApplicationContext()).isOnline()) {

            showProgressDialog();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, Allurl.NumberOfLightsByProduct,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {

                                Log.i("Response1-->", String.valueOf(response));
                                numberoflightbyproductresponse = String.valueOf(response);
                                //SharedPref
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("numberoflightbyproductresponse", numberoflightbyproductresponse);
                                editor.apply();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                            }

//                            hideProgressDialog();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgressDialog();
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("ert", error.toString());

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    9000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {

            Toast.makeText(getApplicationContext(), "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show();
        }


        //PhoneAperture

        if (CheckConnectivity.getInstance(getApplicationContext()).isOnline()) {

            showProgressDialog();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, Allurl.PhoneAperture,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {

                                Log.i("Response2-->", String.valueOf(response));
                                phoneaparture = String.valueOf(response);
                                //SharedPref
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("phoneaparture", phoneaparture);
                                editor.apply();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                            }

//                            hideProgressDialog();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgressDialog();
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("ert", error.toString());

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    9000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {

            Toast.makeText(getApplicationContext(), "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show();
        }

        //DSLRExposureSettings

        if (CheckConnectivity.getInstance(getApplicationContext()).isOnline()) {

            showProgressDialog();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, Allurl.DSLRExposureSettings,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {

                                Log.i("Response3-->", String.valueOf(response));
                                DSLRExposureSettingsresponse = String.valueOf(response);
                                //SharedPref
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("DSLRExposureSettingsresponse", DSLRExposureSettingsresponse);
                                editor.apply();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                            }

//                            hideProgressDialog();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgressDialog();
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("ert", error.toString());

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    9000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {

            Toast.makeText(getApplicationContext(), "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show();
        }


        //CameraExposureSettingsIncrementalValues
        if (CheckConnectivity.getInstance(getApplicationContext()).isOnline()) {

            showProgressDialog();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, Allurl.CameraExposureSettingsIncrementalValues,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {

                                Log.i("Response4-->", String.valueOf(response));
                                CameraExposureSettingsIncrementalValuesresponse = String.valueOf(response);
                                //SharedPref
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("CameraExposureSettingsIncrementalValuesresponse", CameraExposureSettingsIncrementalValuesresponse);
                                editor.apply();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                            }

//                            hideProgressDialog();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgressDialog();
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("ert", error.toString());

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    9000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {

            Toast.makeText(getApplicationContext(), "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show();
        }


        //token


//        if (CheckConnectivity.getInstance(getApplicationContext()).isOnline()) {
//
//            showProgressDialog();
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, Allurl.token,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//
//                            Log.i("Response3-->", String.valueOf(response));
//
//                            try {
//
//                                Log.i("Response5-->", String.valueOf(response));
//                                tokenresponse = String.valueOf(response);
//                                //SharedPref
//                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.putString("tokenresponse", tokenresponse);
//                                editor.apply();
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//
//                            }
//
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                            hideProgressDialog();
//                            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//            requestQueue.add(stringRequest);
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                    9000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        } else {
//
//            Toast.makeText(getApplicationContext(), "OOPS! No Internet Connection", Toast.LENGTH_SHORT).show();
//
//        }
//

        //VersionDetails

        if (CheckConnectivity.getInstance(getApplicationContext()).isOnline()) {

            showProgressDialog();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, Allurl.VersionDetails,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {

                                Log.i("Response6-->", String.valueOf(response));
                                VersionDetailsresponse = String.valueOf(response);
                                //SharedPref
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("VersionDetailsresponse", VersionDetailsresponse);
                                editor.apply();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                            }

//                            hideProgressDialog();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgressDialog();
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("ert", error.toString());

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    9000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {

            Toast.makeText(getApplicationContext(), "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show();
        }


        //backgroundimage

        if (CheckConnectivity.getInstance(getApplicationContext()).isOnline()) {

            showProgressDialog();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, Allurl.backgroundimage,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {

                                Log.i("Response7-->", String.valueOf(response));
                                backgroundimageresponse = String.valueOf(response);
                                try {

                                    JSONObject result = new JSONObject(backgroundimageresponse);
                                    boolean success = result.getBoolean("status");
                                    if (success) {

                                        JSONObject response_data = result.getJSONObject("data");
                                        String image = response_data.getString("android_background_image");

                                        Glide.with(MainActivity.this)
                                                .load(image)
                                                .placeholder(R.drawable.bg)
                                                .into(ll_bg);
                                        //SharedPref
                                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("backgroundimageresponse", backgroundimageresponse);
                                        editor.apply();
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                            }

//                            hideProgressDialog();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgressDialog();
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("ert", error.toString());

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    9000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {

            Toast.makeText(getApplicationContext(), "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show();
        }

        //AppVersion

        if (CheckConnectivity.getInstance(getApplicationContext()).isOnline()) {

            showProgressDialog();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, Allurl.AppVersion,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {

                                Log.i("Response8-->", String.valueOf(response));
                                AppVersionresponse = String.valueOf(response);
                                //SharedPref
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("AppVersionresponse", AppVersionresponse);
                                editor.apply();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                            }

//                            hideProgressDialog();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgressDialog();
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("ert", error.toString());

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    9000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {

            Toast.makeText(getApplicationContext(), "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show();
        }

        //AppContent

        if (CheckConnectivity.getInstance(getApplicationContext()).isOnline()) {

            showProgressDialog();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, Allurl.AppContent,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {

                                Log.i("Response9-->", String.valueOf(response));
                                AppContentresponse = String.valueOf(response);
                                try {

                                    JSONObject result = new JSONObject(AppContentresponse);
                                    boolean success = result.getBoolean("status");
                                    if (success) {

                                        JSONArray dataarray = result.getJSONArray("data");
                                        for (int i = 0; i < dataarray.length(); i++) {
                                            JSONObject dataobj = dataarray.getJSONObject(1);
                                            String title = dataobj.getString("content");
                                            tvSelectLight.setText(title);

                                        }

                                        //SharedPref
                                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("AppContentresponse", AppContentresponse);
                                        editor.apply();
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                            }

                            hideProgressDialog();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgressDialog();
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("ert", error.toString());

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    9000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {

            Toast.makeText(getApplicationContext(), "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show();
        }

    }


    private void requestAllPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            int secondsDelayed = 1;
                            new Handler().postDelayed(new Runnable() {
                                public void run() {


                                    Onclick();


                                }
                            }, secondsDelayed * 3000);
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings and reopen the application.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finishAffinity();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finishAffinity();
            }
        });
        builder.show();

    }


    public void showDialog() {
        LinearLayout btnOk;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.my_dialog1);
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

    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void onBackPressed() {

        finishAffinity();
    }
}