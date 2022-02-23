package com.example.cameraapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cameraapp.adapter.StudiolightAdapter2;
import com.example.cameraapp.model.StudiolightModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdditionalLight extends AppCompatActivity {

    LinearLayout btnSetting1, ll_Phone, ll_DSLR, ll_Phonedetails, ll_DSLRdetails, btnRestart, ll_phoneselect, ll_dslrselect, ll_Phone1, ll_DSLR1;
    ImageView btn_back;
    ImageView iconCam, iconPhone, iconPhone1, iconCam1, ll_bg;
    TextView tvPhone, tvDSLR, tvPhone1, tvDSLR1, tvSelectLight;
    LinearLayout btnSetting;
    RecyclerView rv_addlight;
    private StudiolightAdapter2 studiolightAdapter2;
    ArrayList<StudiolightModel> studiolightModelArrayList;
    ArrayList<StudiolightModel> lightnumberModelArrayList;
    private static final String TAG = "TAG";
    String light, lightresponse2, Lightnumber;
    String AdditionalLightNumber = "";
    JSONArray numberlistArray;
    String phordslr = "";
    String backgroundimageresponse, AppContentresponse;
    SharedPreferences sharedPreferences;
    String lightname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_light);
        Intent intent = getIntent();
        light = intent.getStringExtra("light");
        Lightnumber = intent.getStringExtra("lightnumber");
        phordslr = intent.getStringExtra("phordslr");
        btnSetting1 = findViewById(R.id.btnSetting1);
        btn_back = findViewById(R.id.btn_back);
        ll_Phone = findViewById(R.id.ll_Phone);
        ll_DSLR = findViewById(R.id.ll_DSLR);
        ll_Phonedetails = findViewById(R.id.ll_Phonedetails);
        ll_DSLRdetails = findViewById(R.id.ll_DSLRdetails);
        iconCam = findViewById(R.id.iconCam);
        iconPhone = findViewById(R.id.iconPhone);
        tvPhone = findViewById(R.id.tvPhone);
        tvDSLR = findViewById(R.id.tvDSLR);
        btnSetting = findViewById(R.id.btnSetting);
        btnRestart = findViewById(R.id.btnRestart);
        rv_addlight = findViewById(R.id.rv_addlight);
        ll_dslrselect = findViewById(R.id.ll_dslrselect);
        ll_phoneselect = findViewById(R.id.ll_phoneselect);
        ll_Phone1 = findViewById(R.id.ll_Phone1);
        ll_DSLR1 = findViewById(R.id.ll_DSLR1);
        iconPhone1 = findViewById(R.id.iconPhone1);
        iconCam1 = findViewById(R.id.iconCam1);
        tvPhone1 = findViewById(R.id.tvPhone1);
        tvDSLR1 = findViewById(R.id.tvDSLR1);
        ll_bg = findViewById(R.id.ll_bg);
        tvSelectLight = findViewById(R.id.tvSelectLight);

        sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
        backgroundimageresponse = sharedPreferences.getString("backgroundimageresponse", "");
        try {

            JSONObject result = new JSONObject(backgroundimageresponse);
            boolean success = result.getBoolean("status");
            if (success) {

                JSONObject response_data = result.getJSONObject("data");
                String image = response_data.getString("android_background_image");
                Glide.with(AdditionalLight.this)
                        .load(image)
                        .placeholder(R.drawable.bg)
                        .into(ll_bg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
        AppContentresponse = sharedPreferences.getString("AppContentresponse", "");
        try {

            JSONObject result = new JSONObject(AppContentresponse);
            boolean success = result.getBoolean("status");
            if (success) {
                JSONArray dataarray = result.getJSONArray("data");
                for (int i = 0; i < dataarray.length(); i++) {
                    JSONObject dataobj = dataarray.getJSONObject(4);
                    String title = dataobj.getString("content");
                    tvSelectLight.setText(title);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (phordslr.equals("phone")) {


            ll_phoneselect.setVisibility(View.VISIBLE);
            ll_dslrselect.setVisibility(View.GONE);


        } else if (phordslr.equals("dslr")) {

            ll_phoneselect.setVisibility(View.GONE);
            ll_dslrselect.setVisibility(View.VISIBLE);

        }

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();

            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRestart.setBackgroundResource(R.drawable.bg1);
                btnSetting1.setBackgroundResource(R.drawable.bg2);

                Intent intent = new Intent(AdditionalLight.this, MainActivity.class);
                startActivity(intent);
            }
        });


        ll_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.VISIBLE);
//                ll_DSLRdetails.setVisibility(View.GONE);
                iconPhone.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvPhone.setTextColor(ContextCompat.getColor(AdditionalLight.this, R.color.white));
                ll_Phone.setBackgroundResource(R.drawable.bg1);
                ll_DSLR.setBackgroundResource(R.drawable.bg5);
                iconCam.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvDSLR.setTextColor(ContextCompat.getColor(AdditionalLight.this, R.color.black));
                phordslr = "phone";


            }
        });


        ll_DSLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.GONE);
//                ll_DSLRdetails.setVisibility(View.VISIBLE);
                iconPhone.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvPhone.setTextColor(ContextCompat.getColor(AdditionalLight.this, R.color.black));
                ll_Phone.setBackgroundResource(R.drawable.bg5);
                ll_DSLR.setBackgroundResource(R.drawable.bg1);
                iconCam.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvDSLR.setTextColor(ContextCompat.getColor(AdditionalLight.this, R.color.white));
                phordslr = "dslr";


            }
        });


        ll_Phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.VISIBLE);
//                ll_DSLRdetails.setVisibility(View.GONE);
                iconPhone1.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvPhone1.setTextColor(ContextCompat.getColor(AdditionalLight.this, R.color.white));
                ll_Phone1.setBackgroundResource(R.drawable.bg1);
                ll_DSLR1.setBackgroundResource(R.drawable.bg5);
                iconCam1.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvDSLR1.setTextColor(ContextCompat.getColor(AdditionalLight.this, R.color.black));
                phordslr = "phone";

            }
        });


        ll_DSLR1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.GONE);
//                ll_DSLRdetails.setVisibility(View.VISIBLE);
                iconPhone1.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvPhone1.setTextColor(ContextCompat.getColor(AdditionalLight.this, R.color.black));
                ll_Phone1.setBackgroundResource(R.drawable.bg5);
                ll_DSLR1.setBackgroundResource(R.drawable.bg1);
                iconCam1.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvDSLR1.setTextColor(ContextCompat.getColor(AdditionalLight.this, R.color.white));
                phordslr = "dslr";

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        btnSetting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (light.equals("fluorescnent")){
                    if (lightname.equals("AL1-LED Accent Light")){
                        Toast.makeText(getApplicationContext(),"Not Applicable",Toast.LENGTH_SHORT).show();
                    }else if (lightname.equals("AL2-LED Accent Light")){
                        Toast.makeText(getApplicationContext(),"Not Applicable",Toast.LENGTH_SHORT).show();
                    }else {
                        if (AdditionalLightNumber.length() > 0) {
                            btnRestart.setBackgroundResource(R.drawable.bg2);
                            btnSetting1.setBackgroundResource(R.drawable.bg1);
                            Intent intent = new Intent(AdditionalLight.this, Rsettings1.class);
                            intent.putExtra("lightadditional", AdditionalLightNumber);
                            intent.putExtra("lightnumber", Lightnumber);
                            intent.putExtra("light", light);
                            intent.putExtra("phordslr", phordslr);
                            startActivity(intent);

                        } else {

                            Toast toast = Toast.makeText(getApplicationContext(), "Please Select Light", Toast.LENGTH_LONG);
                            view = toast.getView();
                            view.setBackgroundResource(R.drawable.border3);
                            TextView text = (TextView) view.findViewById(android.R.id.message);
                            /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                            text.setTextColor(Color.parseColor("#000000"));
                            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }

                    }

                }else if (light.equals("Led")){

                    if (lightname.equals("AL1 Accent Light")){
                        Toast.makeText(getApplicationContext(),"Not Applicable",Toast.LENGTH_SHORT).show();
                    }else if (lightname.equals("AL2 Accent Light")){
                        Toast.makeText(getApplicationContext(),"Not Applicable",Toast.LENGTH_SHORT).show();
                    }else {
                        if (AdditionalLightNumber.length() > 0) {
                            btnRestart.setBackgroundResource(R.drawable.bg2);
                            btnSetting1.setBackgroundResource(R.drawable.bg1);
                            Intent intent = new Intent(AdditionalLight.this, Rsettings1.class);
                            intent.putExtra("lightadditional", AdditionalLightNumber);
                            intent.putExtra("lightnumber", Lightnumber);
                            intent.putExtra("light", light);
                            intent.putExtra("phordslr", phordslr);
                            startActivity(intent);

                        } else {

                            Toast toast = Toast.makeText(getApplicationContext(), "Please Select Light", Toast.LENGTH_LONG);
                            view = toast.getView();
                            view.setBackgroundResource(R.drawable.border3);
                            TextView text = (TextView) view.findViewById(android.R.id.message);
                            /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                            text.setTextColor(Color.parseColor("#000000"));
                            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }
                }
            }
        });

        studioAdditionalLigh();
    }


    public void studioAdditionalLigh() {

        showProgressDialog();
        lightresponse2 = sharedPreferences.getString("studiolightresponse", "");
        String response = lightresponse2;

        try {
            JSONObject result = new JSONObject(response);
            boolean status = result.getBoolean("status");
            if (status) {
                studiolightModelArrayList = new ArrayList<>();
                lightnumberModelArrayList = new ArrayList<>();
                JSONObject data = result.getJSONObject("data");
                JSONObject additional_lights = data.getJSONObject("additional_lights");
                JSONArray namelistArray = additional_lights.getJSONArray("name");
                if (light.equals("Led")) {
                    numberlistArray = additional_lights.getJSONArray("led_light_number");
                } else {
                    numberlistArray = additional_lights.getJSONArray("florecent_light_number");
                }

                for (int i = 0; i < namelistArray.length(); i++) {

                    StudiolightModel studiolightModel = new StudiolightModel();
                    studiolightModel.setLightboxname((String) namelistArray.get(i));
                    studiolightModelArrayList.add(studiolightModel);

                }

                for (int j = 0; j < numberlistArray.length(); j++) {

                    StudiolightModel studiolightModel = new StudiolightModel();
                    studiolightModel.setAdditionalLight((String) numberlistArray.get(j));
                    lightnumberModelArrayList.add(studiolightModel);

                }

                setupRecycler();

            } else {

                Log.d(TAG, "unsuccessfull - " + "Error");
                Toast.makeText(AdditionalLight.this, "invalid", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        hideProgressDialog();


    }

    private void setupRecycler() {

        studiolightAdapter2 = new StudiolightAdapter2(this, studiolightModelArrayList, lightnumberModelArrayList, light);
        rv_addlight.setAdapter(studiolightAdapter2);
        rv_addlight.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }


    public void AdditionalLightNumber(StudiolightModel studiolightModel, String name) {

        AdditionalLightNumber = studiolightModel.getAdditionalLight();
        lightname = name;
        Log.d(TAG, "AdditionalLightNumber-->" + AdditionalLightNumber);
    }


    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    public void showDialog() {
        LinearLayout btnOk;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.my_dialog);
        dialog.show();


        btnOk = dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            ;

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdditionalLight.this, Camerafilter.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(AdditionalLight.this, SelectstudioLight2.class);
        intent.putExtra("light", light);
        intent.putExtra("phordslr", phordslr);
        intent.putExtra("Lightnumber", Lightnumber);
        startActivity(intent);
        finish();
    }
}