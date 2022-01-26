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
import com.example.cameraapp.adapter.StudiolightAdapter;
import com.example.cameraapp.model.StudiolightModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectstudioLight extends AppCompatActivity {

    private static final String TAG = "TAG";
    ImageView btn_back;
    LinearLayout btnNext, ll_Phone, ll_DSLR, ll_Phonedetails, ll_DSLRdetails, ll_phoneselect, ll_dslrselect, ll_Phone1, ll_DSLR1;
    ImageView iconCam, iconPhone, iconPhone1, iconCam1;
    TextView tvPhone, tvDSLR, tvPhone1, tvDSLR1, tvSelectLight;
    LinearLayout btnSetting, btnRestart;
    RecyclerView rv_studiolight;
    private StudiolightAdapter studiolightAdapter;
    ArrayList<StudiolightModel> studiolightModelArrayList;
    ArrayList<StudiolightModel> lightnumberModelArrayList;
    String light, lightresponse;
    String Lightnumber = "";
    String phordslr = "";
    JSONArray numberlistArray;
    ImageView ll_bg;
    String backgroundimageresponse, AppContentresponse;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectstudio_light);
        Intent intent = getIntent();
        light = intent.getStringExtra("light");
        phordslr = intent.getStringExtra("phordslr");
        btn_back = findViewById(R.id.btn_back);
        ll_Phone = findViewById(R.id.ll_Phone);
        ll_DSLR = findViewById(R.id.ll_DSLR);
        ll_Phonedetails = findViewById(R.id.ll_Phonedetails);
        ll_DSLRdetails = findViewById(R.id.ll_DSLRdetails);
        iconCam = findViewById(R.id.iconCam);
        iconPhone = findViewById(R.id.iconPhone);
        tvPhone = findViewById(R.id.tvPhone);
        tvDSLR = findViewById(R.id.tvDSLR);
        btnNext = findViewById(R.id.btnNext);
        btnSetting = findViewById(R.id.btnSetting);
        btnRestart = findViewById(R.id.btnRestart);
        rv_studiolight = findViewById(R.id.rv_studiolight);
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
                Glide.with(SelectstudioLight.this)
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
                    JSONObject dataobj = dataarray.getJSONObject(2);
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
                btnNext.setBackgroundResource(R.drawable.bg2);
                btnRestart.setBackgroundResource(R.drawable.bg1);
                Intent intent = new Intent(SelectstudioLight.this, MainActivity.class);
                intent.putExtra("phordslr", phordslr);
                startActivity(intent);
            }
        });


        ll_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.VISIBLE);
//                ll_DSLRdetails.setVisibility(View.GONE);
                iconPhone.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvPhone.setTextColor(ContextCompat.getColor(SelectstudioLight.this, R.color.white));
                ll_Phone.setBackgroundResource(R.drawable.bg1);
                ll_DSLR.setBackgroundResource(R.drawable.bg5);
                iconCam.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvDSLR.setTextColor(ContextCompat.getColor(SelectstudioLight.this, R.color.black));
                phordslr = "phone";


            }
        });


        ll_DSLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.GONE);
//                ll_DSLRdetails.setVisibility(View.VISIBLE);
                iconPhone.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvPhone.setTextColor(ContextCompat.getColor(SelectstudioLight.this, R.color.black));
                ll_Phone.setBackgroundResource(R.drawable.bg5);
                ll_DSLR.setBackgroundResource(R.drawable.bg1);
                iconCam.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvDSLR.setTextColor(ContextCompat.getColor(SelectstudioLight.this, R.color.white));
                phordslr = "dslr";

            }
        });


        ll_Phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.VISIBLE);
//                ll_DSLRdetails.setVisibility(View.GONE);
                iconPhone1.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvPhone1.setTextColor(ContextCompat.getColor(SelectstudioLight.this, R.color.white));
                ll_Phone1.setBackgroundResource(R.drawable.bg1);
                ll_DSLR1.setBackgroundResource(R.drawable.bg5);
                iconCam1.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvDSLR1.setTextColor(ContextCompat.getColor(SelectstudioLight.this, R.color.black));
                phordslr = "phone";

            }
        });


        ll_DSLR1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.GONE);
//                ll_DSLRdetails.setVisibility(View.VISIBLE);
                iconPhone1.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvPhone1.setTextColor(ContextCompat.getColor(SelectstudioLight.this, R.color.black));
                ll_Phone1.setBackgroundResource(R.drawable.bg5);
                ll_DSLR1.setBackgroundResource(R.drawable.bg1);
                iconCam1.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvDSLR1.setTextColor(ContextCompat.getColor(SelectstudioLight.this, R.color.white));
                phordslr = "dslr";

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Lightnumber.length() > 0) {

                    btnNext.setBackgroundResource(R.drawable.bg1);
                    btnRestart.setBackgroundResource(R.drawable.bg2);
                    Intent intent = new Intent(SelectstudioLight.this, SelectstudioLight2.class);
                    intent.putExtra("light", light);
                    intent.putExtra("lightnumber", Lightnumber);
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
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        studioLightboxlist();
    }


    public void studioLightboxlist() {


        showProgressDialog();

        /*if (light.equals("Led")) {

            lightresponse = "{\n" +
                    "  \"status\": true,\n" +
                    "  \"data\": {\n" +
                    "    \"lightbox\": {\n" +
                    "      \"name\": [\n" +
                    "        \"PS5\",\n" +
                    "        \"MS20\",\n" +
                    "        \"MS20J\",\n" +
                    "        \"MS20PRO\",\n" +
                    "        \"MS32\",\n" +
                    "        \"US31LED\",\n" +
                    "        \"VS36LED\",\n" +
                    "        \"VS53LED\"\n" +
                    "      ],\n" +
                    "      \"led_light_number\": [\n" +
                    "        \"2\",\n" +
                    "        \"2\",\n" +
                    "        \"2\",\n" +
                    "        \"4\",\n" +
                    "        \"4\",\n" +
                    "        \"4\",\n" +
                    "        \"2\",\n" +
                    "        \"4\"\n" +
                    "      ],\n" +
                    "      \"florecent_light_number\": [\n" +
                    "        \"1\",\n" +
                    "        \"1\",\n" +
                    "        \"1\",\n" +
                    "        \"2\",\n" +
                    "        \"2\",\n" +
                    "        \"0\",\n" +
                    "        \"0\",\n" +
                    "        \"0\"\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    \"additional_lights\": {\n" +
                    "      \"name\": [\n" +
                    "        \"AL1 Accent Light\",\n" +
                    "        \"AL2 Accent Light\",\n" +
                    "        \"AL1-LED Accent Light\",\n" +
                    "        \"AL2-LED Accent Light\",\n" +
                    "        \"Extra light bar for PS5\",\n" +
                    "        \"Extra light bar for MS20\",\n" +
                    "        \"Extra light bar for MS32\"\n" +
                    "      ],\n" +
                    "      \"led_light_number\": [\n" +
                    "        \"0\",\n" +
                    "        \"0\",\n" +
                    "        \"2\",\n" +
                    "        \"4\",\n" +
                    "        \"2\",\n" +
                    "        \"2\",\n" +
                    "        \"4\"\n" +
                    "      ],\n" +
                    "      \"florecent_light_number\": [\n" +
                    "        \"1\",\n" +
                    "        \"2\",\n" +
                    "        \"0\",\n" +
                    "        \"0\",\n" +
                    "        \"1\",\n" +
                    "        \"1\",\n" +
                    "        \"2\"\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
        } else {


            lightresponse = "{\n" +
                    "  \"status\": true,\n" +
                    "  \"data\": {\n" +
                    "    \"lightbox\": {\n" +
                    "      \"name\": [\n" +
                    "        \"PS5\",\n" +
                    "        \"MS20\",\n" +
                    "        \"MS20J\",\n" +
                    "        \"MS20PRO\",\n" +
                    "        \"MS32\",\n" +
                    "        \"US31LED\",\n" +
                    "        \"VS36LED\",\n" +
                    "        \"VS53LED\"\n" +
                    "      ],\n" +
                    "      \"led_light_number\": [\n" +
                    "        \"2\",\n" +
                    "        \"2\",\n" +
                    "        \"2\",\n" +
                    "        \"4\",\n" +
                    "        \"4\",\n" +
                    "        \"4\",\n" +
                    "        \"2\",\n" +
                    "        \"4\"\n" +
                    "      ],\n" +
                    "      \"florecent_light_number\": [\n" +
                    "        \"1\",\n" +
                    "        \"1\",\n" +
                    "        \"1\",\n" +
                    "        \"2\",\n" +
                    "        \"2\",\n" +
                    "        \"0\",\n" +
                    "        \"0\",\n" +
                    "        \"0\"\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    \"additional_lights\": {\n" +
                    "      \"name\": [\n" +
                    "        \"AL1 Accent Light\",\n" +
                    "        \"AL2 Accent Light\",\n" +
                    "        \"AL1-LED Accent Light\",\n" +
                    "        \"AL2-LED Accent Light\",\n" +
                    "        \"Extra light bar for PS5\",\n" +
                    "        \"Extra light bar for MS20\",\n" +
                    "        \"Extra light bar for MS32\"\n" +
                    "      ],\n" +
                    "      \"led_light_number\": [\n" +
                    "        \"0\",\n" +
                    "        \"0\",\n" +
                    "        \"2\",\n" +
                    "        \"4\",\n" +
                    "        \"2\",\n" +
                    "        \"2\",\n" +
                    "        \"4\"\n" +
                    "      ],\n" +
                    "      \"florecent_light_number\": [\n" +
                    "        \"1\",\n" +
                    "        \"2\",\n" +
                    "        \"0\",\n" +
                    "        \"0\",\n" +
                    "        \"1\",\n" +
                    "        \"1\",\n" +
                    "        \"2\"\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
        }*/

        lightresponse = sharedPreferences.getString("studiolightresponse","");
        String response = lightresponse;

        try {
            JSONObject result = new JSONObject(response);
            boolean status = result.getBoolean("status");
            if (status) {
                studiolightModelArrayList = new ArrayList<>();
                lightnumberModelArrayList = new ArrayList<>();
                JSONObject data = result.getJSONObject("data");
                JSONObject lightbox = data.getJSONObject("lightbox");
                JSONArray namelistArray = lightbox.getJSONArray("name");
                if (light.equals("Led")) {
                    numberlistArray = lightbox.getJSONArray("led_light_number");
                } else {
                    numberlistArray = lightbox.getJSONArray("florecent_light_number");
                }

                for (int i = 0; i < namelistArray.length(); i++) {

                    StudiolightModel studiolightModel = new StudiolightModel();
                    studiolightModel.setLightboxname((String) namelistArray.get(i));
                    studiolightModelArrayList.add(studiolightModel);

                }

                for (int j = 0; j < numberlistArray.length(); j++) {

                    StudiolightModel studiolightModel = new StudiolightModel();
                    studiolightModel.setLedlightnumber((String) numberlistArray.get(j));
                    lightnumberModelArrayList.add(studiolightModel);

                }

                setupRecycler();

            } else {

                Log.d(TAG, "unsuccessfull - " + "Error");
                Toast.makeText(SelectstudioLight.this, "invalid", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        hideProgressDialog();


    }


    private void setupRecycler() {

        studiolightAdapter = new StudiolightAdapter(this, studiolightModelArrayList, lightnumberModelArrayList);
        rv_studiolight.setAdapter(studiolightAdapter);
        rv_studiolight.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    public void lightNumber(StudiolightModel studiolightModel) {

        Lightnumber = studiolightModel.getLedlightnumber();
        Log.d(TAG, "Studionumberlight-->" + Lightnumber);
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

                Intent intent = new Intent(SelectstudioLight.this, Camerafilter.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SelectstudioLight.this, MainActivity.class);
        startActivity(intent);
    }
}