package com.example.cameraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

public class SelectstudioLight2 extends AppCompatActivity {

    LinearLayout btnYes, ll_Phone, ll_DSLR, ll_Phonedetails, ll_DSLRdetails, ll_phoneselect, ll_dslrselect, ll_Phone1, ll_DSLR1;
    ImageView btn_back;
    ImageView iconCam, iconPhone, iconPhone1, iconCam1, ll_bg;
    TextView tvPhone, tvDSLR, tvPhone1, tvDSLR1, tvSelectLight;
    LinearLayout btnSetting, btnRestart, btnNo;
    String light,Lightnumber ;
    String phordslr = "";
    String backgroundimageresponse, AppContentresponse;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectstudio_light2);
        Intent intent = getIntent();
        light = intent.getStringExtra("light");
        Lightnumber = intent.getStringExtra("lightnumber");
        phordslr = intent.getStringExtra("phordslr");
        btnYes = findViewById(R.id.btnYes);
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
        btnNo = findViewById(R.id.btnNo);
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
                Glide.with(SelectstudioLight2.this)
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
                    JSONObject dataobj = dataarray.getJSONObject(3);
                    String title = dataobj.getString("content");
                    tvSelectLight.setText(title);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (phordslr.equals("phone")){


            ll_phoneselect.setVisibility(View.VISIBLE);
            ll_dslrselect.setVisibility(View.GONE);


        }else if (phordslr.equals("dslr")){

            ll_phoneselect.setVisibility(View.GONE);
            ll_dslrselect.setVisibility(View.VISIBLE);

        }


        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnNo.setBackgroundResource(R.drawable.bg1);
                btnYes.setBackgroundResource(R.drawable.bg2);

                Intent intent = new Intent(SelectstudioLight2.this, Rsettings1.class);
                intent.putExtra("light", light);
                intent.putExtra("lightadditional", "0");
                intent.putExtra("lightnumber", Lightnumber);
                intent.putExtra("phordslr",phordslr);
                startActivity(intent);

            }
        });


        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectstudioLight2.this, MainActivity.class);
                intent.putExtra("phordslr",phordslr);
                startActivity(intent);
            }
        });

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
                tvPhone.setTextColor(ContextCompat.getColor(SelectstudioLight2.this, R.color.white));
                ll_Phone.setBackgroundResource(R.drawable.bg1);
                ll_DSLR.setBackgroundResource(R.drawable.bg5);
                iconCam.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvDSLR.setTextColor(ContextCompat.getColor(SelectstudioLight2.this, R.color.black));
                phordslr = "phone";

            }
        });


        ll_DSLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.GONE);
//                ll_DSLRdetails.setVisibility(View.VISIBLE);
                iconPhone.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvPhone.setTextColor(ContextCompat.getColor(SelectstudioLight2.this, R.color.black));
                ll_Phone.setBackgroundResource(R.drawable.bg5);
                ll_DSLR.setBackgroundResource(R.drawable.bg1);
                iconCam.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvDSLR.setTextColor(ContextCompat.getColor(SelectstudioLight2.this, R.color.white));
                phordslr = "dslr";


            }
        });


        ll_Phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.VISIBLE);
//                ll_DSLRdetails.setVisibility(View.GONE);
                iconPhone1.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvPhone1.setTextColor(ContextCompat.getColor(SelectstudioLight2.this, R.color.white));
                ll_Phone1.setBackgroundResource(R.drawable.bg1);
                ll_DSLR1.setBackgroundResource(R.drawable.bg5);
                iconCam1.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvDSLR1.setTextColor(ContextCompat.getColor(SelectstudioLight2.this, R.color.black));
                phordslr = "phone";

            }
        });


        ll_DSLR1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.GONE);
//                ll_DSLRdetails.setVisibility(View.VISIBLE);
                iconPhone1.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvPhone1.setTextColor(ContextCompat.getColor(SelectstudioLight2.this, R.color.black));
                ll_Phone1.setBackgroundResource(R.drawable.bg5);
                ll_DSLR1.setBackgroundResource(R.drawable.bg1);
                iconCam1.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvDSLR1.setTextColor(ContextCompat.getColor(SelectstudioLight2.this, R.color.white));
                phordslr = "dslr";

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                btnNo.setBackgroundResource(R.drawable.bg2);
                btnYes.setBackgroundResource(R.drawable.bg1);

                Intent intent = new Intent(SelectstudioLight2.this, AdditionalLight.class);
                intent.putExtra("light", light);
                intent.putExtra("lightnumber", Lightnumber);
                intent.putExtra("phordslr",phordslr);
                startActivity(intent);

            }
        });
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

                Intent intent = new Intent(SelectstudioLight2.this, Camerafilter.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SelectstudioLight2.this, SelectstudioLight.class);
        intent.putExtra("light", light);
        intent.putExtra("phordslr",phordslr);
        startActivity(intent);
        finish();
    }
}