package com.example.cameraapp.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.example.cameraapp.AdditionalLight;
import com.example.cameraapp.Camerafilter;
import com.example.cameraapp.MainActivity;
import com.example.cameraapp.R;
import com.example.cameraapp.adapter.PickerAdapter;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Rsettingstest extends AppCompatActivity {

    private static final String TAG = "myapp";
    LinearLayout btnGotocamera, ll_Phone, ll_DSLR, ll_Phonedetails, ll_DSLRdetails, ll_phoneselect, ll_dslrselect, ll_Phone1, ll_DSLR1;
    ImageView btn_back;
    ImageView iconCam, iconPhone, btnIconphone, btnIconDSLR, iconPhone1, iconCam1, ll_bg;
    TextView tvPhone, tvDSLR, tvPhoneiso, tvPhone1, tvDSLR1;
    LinearLayout btnSetting, btnRestart, btnRestoreSetting, btnRestart2;
    DiscreteScrollView rv, rv_aperture, rv_shutterspeed;
    PickerAdapter adapter;
    PickerAdapter adapter1;
    PickerAdapter adapter2;
    TextView tvISO, tvAp, tvShutterspeed;
    String backgroundimageresponse, DSLRExposureSettingsresponse;
    String Lightnumber, AdditionalLightNumber, light;
    int totallights;
    String phordslr = "";
    private List<String> data;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsettings1);
        Intent intent = getIntent();
        AdditionalLightNumber = intent.getStringExtra("lightadditional");
        Lightnumber = intent.getStringExtra("lightnumber");
        light = intent.getStringExtra("light");
        phordslr = intent.getStringExtra("phordslr");
        btnGotocamera = findViewById(R.id.btnGotocamera);
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
        tvISO = findViewById(R.id.tvISO);
        tvAp = findViewById(R.id.tvAp);
        tvShutterspeed = findViewById(R.id.tvShutterspeed);
        rv = findViewById(R.id.rv);
        rv_aperture = findViewById(R.id.rv_aperture);
        rv_shutterspeed = findViewById(R.id.rv_shutterspeed);
        btnIconDSLR = findViewById(R.id.btnIconDSLR);
        btnIconphone = findViewById(R.id.btnIconphone);
        btnRestart = findViewById(R.id.btnRestart);
        tvPhoneiso = findViewById(R.id.tvPhoneiso);
        btnRestart2 = findViewById(R.id.btnRestart2);
        btnRestoreSetting = findViewById(R.id.btnRestoreSetting);
        ll_dslrselect = findViewById(R.id.ll_dslrselect);
        ll_phoneselect = findViewById(R.id.ll_phoneselect);
        ll_Phone1 = findViewById(R.id.ll_Phone1);
        ll_DSLR1 = findViewById(R.id.ll_DSLR1);
        iconPhone1 = findViewById(R.id.iconPhone1);
        iconCam1 = findViewById(R.id.iconCam1);
        tvPhone1 = findViewById(R.id.tvPhone1);
        tvDSLR1 = findViewById(R.id.tvDSLR1);
        ll_bg = findViewById(R.id.ll_bg);
        sharedPreferences = getApplicationContext().getSharedPreferences("allresponse", MODE_PRIVATE);
        backgroundimageresponse = sharedPreferences.getString("backgroundimageresponse", "");
        try {

            JSONObject result = new JSONObject(backgroundimageresponse);
            boolean success = result.getBoolean("status");
            if (success) {

                JSONObject response_data = result.getJSONObject("data");
                String image = response_data.getString("android_background_image");
                Glide.with(Rsettingstest.this)
                        .load(image)
                        .placeholder(R.drawable.bg)
                        .into(ll_bg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (phordslr.equals("phone")) {


            ll_phoneselect.setVisibility(View.VISIBLE);
            ll_dslrselect.setVisibility(View.GONE);
            ll_Phonedetails.setVisibility(View.VISIBLE);
            ll_DSLRdetails.setVisibility(View.GONE);


        } else if (phordslr.equals("dslr")) {

            ll_phoneselect.setVisibility(View.GONE);
            ll_dslrselect.setVisibility(View.VISIBLE);
            ll_Phonedetails.setVisibility(View.GONE);
            ll_DSLRdetails.setVisibility(View.VISIBLE);

        }


        if (Lightnumber == null) {

            Lightnumber = "0";
        }

        if (AdditionalLightNumber == null) {

            AdditionalLightNumber = "0";
        }

        totallights = Integer.parseInt(Lightnumber) + Integer.parseInt(AdditionalLightNumber);

        Log.d(TAG, "totallights-->" + totallights);
        switch (totallights) {
            case 1:
                tvPhoneiso.setText("1/13");
                break;
            case 2:
                tvPhoneiso.setText("1/25");
                break;
            case 3:
                tvPhoneiso.setText("1/40");
                break;
            case 4:
                tvPhoneiso.setText("1/50");
                break;
            case 5:
                tvPhoneiso.setText("1/50");
                break;
            case 6:
                tvPhoneiso.setText("1/80");
                break;
            case 7:
                tvPhoneiso.setText("1/80");
                break;
            case 8:
                tvPhoneiso.setText("1/100");
                break;
            default:
                tvPhoneiso.setText("1/25");
                break;
        }

//        dslrsettingresponse = "{\"status\":true,\"data\":{\"1\":{\"ISO 100\":{\"aperture\":[\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\"],\"shutter\":[\"\",\"\",\"\",\"2 sec\",\"0.4\",\"1/13\",\"1/60\",\"1 sec\",\"1/5\",\"1/25\",\"1/125\",\"1/2\",\"1/10\",\"1/50\",\"1.3 sec\",\"1/4\",\"1/20\",\"1/100\",\"0.6\",\"1/8\",\"1/40\",\"1.6 sec\",\"0.3\",\"1/15\",\"1/80\",\"0.8\",\"1/6\",\"1/30\",\"\",\"\",\"\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/125\"}},\"ISO 200\":{\"aperture\":[\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\"],\"shutter\":[\"\",\"\",\"\",\"1/60\",\"1 sec\",\"1/5\",\"1/25\",\"1/125\",\"1/2\",\"1/10\",\"1/50\",\"1/250\",\"1/4\",\"1/20\",\"1/100\",\"0.6\",\"1/8\",\"1/40\",\"1/200\",\"0.3\",\"1/15\",\"1/80\",\"0.8\",\"1/6\",\"1/30\",\"1/160\",\"0.4\",\"1/13\",\"\",\"\",\"\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/250\"}},\"ISO 400\":{\"aperture\":[\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\"],\"shutter\":[\"\",\"\",\"\",\"1/25\",\"1/125\",\"1/2\",\"1/10\",\"1/50\",\"1/250\",\"1/4\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"0.3\",\"1/15\",\"1/80\",\"1/400\",\"1/6\",\"1/30\",\"1/160\",\"0.4\",\"1/13\",\"1/60\",\"1/320\",\"1/5\",\"\",\"\",\"\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/500\"}},\"ISO 800\":{\"aperture\":[\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\"],\"shutter\":[\"\",\"\",\"\",\"1/10\",\"1/50\",\"1/250\",\"1/4\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/6\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/5\",\"1/25\",\"1/125\",\"1/640\",\"\",\"\",\"\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/1000\"}},\"ISO 1600\":{\"aperture\":[\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\"],\"shutter\":[\"\",\"\",\"\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/2000\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\",\"1/125\",\"1/640\",\"1/10\",\"1/50\",\"1/250\",\"1/1250\",\"\",\"\",\"\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/2000\"}}},\"2\":{\"ISO 100\":{\"aperture\":[\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\"],\"shutter\":[\"1/80\",\"0.8\",\"1/6\",\"1/30\",\"2 sec\",\"0.4\",\"1/13\",\"1/60\",\"1 sec\",\"1/5\",\"1/25\",\"1/125\",\"1/2\",\"1/10\",\"1/50\",\"1.3 sec\",\"1/4\",\"1/20\",\"1/100\",\"0.6\",\"1/8\",\"1/40\",\"1.6 sec\",\"0.3\",\"1/15\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/125\"}},\"ISO 200\":{\"aperture\":[\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\"],\"shutter\":[\"1/30\",\"1/160\",\"0.4\",\"1/13\",\"1/60\",\"1 sec\",\"1/5\",\"1/25\",\"1/125\",\"1/2\",\"1/10\",\"1/50\",\"1/250\",\"1/4\",\"1/20\",\"1/100\",\"0.6\",\"1/8\",\"1/40\",\"1/200\",\"0.3\",\"1/15\",\"1/80\",\"0.8\",\"1/6\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/250\"}},\"ISO 400\":{\"aperture\":[\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\"],\"shutter\":[\"1/25\",\"1/125\",\"1/640\",\"1/10\",\"1/50\",\"1/250\",\"1/4\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/6\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/5\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/1000\"}},\"ISO 800\":{\"aperture\":[\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\"],\"shutter\":[\"1/10\",\"1/50\",\"1/250\",\"1/1250\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/2000\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\",\"1/125\",\"1/640\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/2000\"}},\"ISO 1600\":{\"aperture\":[\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\"],\"shutter\":[\"1/20\",\"1/100\",\"1/500\",\"1/2500\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/2000\",\"1/30\",\"1/160\",\"1/800\",\"1/4000\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\",\"1/125\",\"1/640\",\"1/3200\",\"1/50\",\"1/250\",\"1/1250\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/4000\"}}},\"3\":{\"ISO 100\":{\"aperture\":[\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\"],\"shutter\":[\"1/125\",\"1/2\",\"1/10\",\"1/50\",\"1/250\",\"1/4\",\"1/20\",\"1/100\",\"0.6\",\"1/8\",\"1/40\",\"1/200\",\"1/3\",\"1/15\",\"1/80\",\"1/400\",\"1/6\",\"1/30\",\"1/160\",\"0.4\",\"1/13\",\"1/60\",\"1/320\",\"1/5\",\"1/25\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/400\"}},\"ISO 200\":{\"aperture\":[\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\"],\"shutter\":[\"1/50\",\"1/250\",\"1/4\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"1/3\",\"1/15\",\"1/80\",\"1/400\",\"1/6\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/5\",\"1/25\",\"1/125\",\"1/640\",\"1/10\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/800\"}},\"ISO 400\":{\"aperture\":[\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\"],\"shutter\":[\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/6\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\",\"1/125\",\"1/640\",\"1/10\",\"1/50\",\"1/250\",\"1/1250\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/1600\"}},\"ISO 800\":{\"aperture\":[\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\"],\"shutter\":[\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/2000\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\",\"1/125\",\"1/640\",\"1/3200\",\"1/50\",\"1/250\",\"1/1250\",\"1/20\",\"1/100\",\"1/500\",\"1/2500\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/3200\"}},\"ISO 1600\":{\"aperture\":[\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\"],\"shutter\":[\"1/5000\",\"1/80\",\"1/400\",\"1/2000\",\"1/30\",\"1/160\",\"1/800\",\"1/4000\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\",\"1/125\",\"1/640\",\"1/3200\",\"1/50\",\"1/250\",\"1/1250\",\"1/6400\",\"1/100\",\"1/500\",\"1/2500\",\"1/40\",\"1/200\",\"1/1000\"]}},\"4-5\":{\"ISO 100\":{\"aperture\":[\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\"],\"shutter\":[\"1/80\",\"1/400\",\"1/6\",\"1/30\",\"1/160\",\"0.4\",\"1/13\",\"1/60\",\"1/320\",\"1/5\",\"1/25\",\"1/125\",\"1/2\",\"1/10\",\"1/50\",\"1/250\",\"1/4\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"0.3\",\"1/15\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/500\"}},\"ISO 200\":{\"aperture\":[\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\"],\"shutter\":[\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/5\",\"1/25\",\"1/125\",\"1/640\",\"1/10\",\"1/50\",\"1/250\",\"1/4\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/6\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/1000\"}},\"ISO 400\":{\"aperture\":[\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"2.2\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\"],\"shutter\":[\"1/13\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\",\"1/125\",\"1/640\",\"1/10\",\"1/50\",\"1/250\",\"1/1250\",\"1/20\",\"1/100\",\"1/500\",\"1/10\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/2000\",\"1/30\",\"1/160\",\"1/800\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/2000\"}},\"ISO 800\":{\"aperture\":[\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\"],\"shutter\":[\"1/25\",\"1/125\",\"1/640\",\"1/3200\",\"1/50\",\"1/250\",\"1/1250\",\"1/20\",\"1/100\",\"1/500\",\"1/2500\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/2000\",\"1/30\",\"1/160\",\"1/800\",\"1/4000\",\"1/60\",\"1/320\",\"1/1600\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/4000\"}}},\"6-7\":{\"ISO 100\":{\"aperture\":[\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\"],\"shutter\":[\"1/320\",\"1/5\",\"1/25\",\"1/125\",\"1/640\",\"1/10\",\"1/50\",\"1/250\",\"1/4\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"1/3\",\"1/15\",\"1/80\",\"1/400\",\"1/6\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/800\"}},\"ISO 200\":{\"aperture\":[\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\"],\"shutter\":[\"1/125\",\"1/640\",\"1/10\",\"1/50\",\"1/250\",\"1/1250\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/6\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/1600\"}},\"ISO 400\":{\"aperture\":[\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\"],\"shutter\":[\"1/50\",\"1/250\",\"1/1250\",\"1/20\",\"1/100\",\"1/500\",\"1/2500\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/2000\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\",\"1/125\",\"1/640\",\"1/3200\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/3200\"}},\"ISO 800\":{\"aperture\":[\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\"],\"shutter\":[\"1/100\",\"1/500\",\"1/2500\",\"1/40\",\"1/200\",\"1/1000\",\"1/5000\",\"1/80\",\"1/400\",\"1/2000\",\"1/30\",\"1/160\",\"1/800\",\"1/4000\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\",\"1/125\",\"1/640\",\"1/3200\",\"1/50\",\"1/250\",\"1/1250\",\"1/6400\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/6400\"}},\"ISO 1600\":{\"aperture\":[\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\",\"3.2\"],\"shutter\":[\"1/8000\",\"1/200\",\"1/1000\",\"1/5000\",\"1/80\",\"1/400\",\"1/2000\",\"1/8000\",\"1/160\",\"1/800\",\"1/4000\",\"1/60\",\"1/320\",\"1/1600\",\"1/8000\",\"1/125\",\"1/640\",\"1/3200\",\"1/50\",\"1/250\",\"1/1250\",\"1/6400\",\"1/100\",\"1/500\",\"1/2500\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/8000\"}}},\"8\":{\"ISO 100\":{\"aperture\":[\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\",\"7.1\"],\"shutter\":[\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/6\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/5\",\"1/25\",\"1/125\",\"1/640\",\"1/10\",\"1/50\",\"1/250\",\"1/4\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/1000\"}},\"ISO 200\":{\"aperture\":[\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\",\"16\"],\"shutter\":[\"1/80\",\"1/400\",\"1/2000\",\"1/30\",\"1/160\",\"1/800\",\"1/13\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\",\"1/125\",\"1/640\",\"1/10\",\"1/50\",\"1/250\",\"1/1250\",\"1/20\",\"1/100\",\"1/500\",\"1/8\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/2000\"}},\"ISO 400\":{\"aperture\":[\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\"],\"shutter\":[\"1/30\",\"1/160\",\"1/800\",\"1/4000\",\"1/60\",\"1/320\",\"1/1600\",\"1/25\",\"1/125\",\"1/640\",\"1/3200\",\"1/50\",\"1/250\",\"1/1250\",\"1/20\",\"1/100\",\"1/500\",\"1/2500\",\"1/40\",\"1/200\",\"1/1000\",\"1/15\",\"1/80\",\"1/400\",\"1/2000\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/4000\"}},\"ISO 800\":{\"aperture\":[\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\",\"2\"],\"shutter\":[\"1/60\",\"1/320\",\"1/1600\",\"1/8000\",\"1/125\",\"1/640\",\"1/3200\",\"1/50\",\"1/250\",\"1/1250\",\"1/6400\",\"1/100\",\"1/500\",\"1/2500\",\"1/40\",\"1/200\",\"1/1000\",\"1/5000\",\"1/80\",\"1/400\",\"1/2000\",\"1/30\",\"1/160\",\"1/800\",\"1/4000\"],\"default\":{\"aperture\":\"1.4\",\"shutter\":\"1/8000\"}},\"ISO 1600\":{\"aperture\":[\"2\",\"16\",\"7.1\",\"3.2\",\"1.4\",\"11\",\"5\",\"2.2\",\"18\",\"8\",\"3.5\",\"1.6\",\"13\",\"5.6\",\"2.5\",\"20\",\"9\",\"4\",\"1.8\",\"14\",\"6.3\",\"2.8\",\"22\",\"10\",\"4.5\"],\"shutter\":[\"1/8000\",\"1/125\",\"1/640\",\"1/3200\",\"1/8000\",\"1/250\",\"1/1250\",\"1/6400\",\"1/100\",\"1/500\",\"1/2500\",\"1/8000\",\"1/200\",\"1/1000\",\"1/5000\",\"1/80\",\"1/400\",\"1/2000\",\"1/8000\",\"1/160\",\"1/800\",\"1/4000\",\"1/60\",\"1/320\",\"1/1600\"],\"default\":{\"aperture\":\"11\",\"shutter\":\"1/250\"}}}}}";
        onclick();


    }

    public void onclick() {

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRestart.setBackgroundResource(R.drawable.bg1);
                btnGotocamera.setBackgroundResource(R.drawable.bg2);
                Intent intent = new Intent(Rsettingstest.this, MainActivity.class);
                intent.putExtra("phordslr", phordslr);
                startActivity(intent);

            }
        });

        btnRestart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Rsettingstest.this, MainActivity.class);
                intent.putExtra("phordslr", phordslr);
                startActivity(intent);

            }
        });


        btnIconphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });

        btnIconDSLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogDSLR();
            }
        });

        ll_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_Phonedetails.setVisibility(View.VISIBLE);
                ll_DSLRdetails.setVisibility(View.GONE);
                iconPhone.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvPhone.setTextColor(ContextCompat.getColor(Rsettingstest.this, R.color.white));
                ll_Phone.setBackgroundResource(R.drawable.bg1);
                ll_DSLR.setBackgroundResource(R.drawable.bg5);
                iconCam.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvDSLR.setTextColor(ContextCompat.getColor(Rsettingstest.this, R.color.black));

            }
        });


        ll_Phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll_Phonedetails.setVisibility(View.VISIBLE);
//                ll_DSLRdetails.setVisibility(View.GONE);
                ll_Phonedetails.setVisibility(View.VISIBLE);
                ll_DSLRdetails.setVisibility(View.GONE);
                iconPhone1.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvPhone1.setTextColor(ContextCompat.getColor(Rsettingstest.this, R.color.white));
                ll_Phone1.setBackgroundResource(R.drawable.bg1);
                ll_DSLR1.setBackgroundResource(R.drawable.bg5);
                iconCam1.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvDSLR1.setTextColor(ContextCompat.getColor(Rsettingstest.this, R.color.black));
                phordslr = "phone";

            }
        });


        ll_DSLR1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_Phonedetails.setVisibility(View.GONE);
                ll_DSLRdetails.setVisibility(View.VISIBLE);
                iconPhone1.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvPhone1.setTextColor(ContextCompat.getColor(Rsettingstest.this, R.color.black));
                ll_Phone1.setBackgroundResource(R.drawable.bg5);
                ll_DSLR1.setBackgroundResource(R.drawable.bg1);
                iconCam1.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvDSLR1.setTextColor(ContextCompat.getColor(Rsettingstest.this, R.color.white));
                phordslr = "dslr";

            }
        });


        ll_DSLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_Phonedetails.setVisibility(View.GONE);
                ll_DSLRdetails.setVisibility(View.VISIBLE);
                iconPhone.setColorFilter(getApplication().getResources().getColor(R.color.black));
                tvPhone.setTextColor(ContextCompat.getColor(Rsettingstest.this, R.color.black));
                ll_Phone.setBackgroundResource(R.drawable.bg5);
                ll_DSLR.setBackgroundResource(R.drawable.bg1);
                iconCam.setColorFilter(getApplication().getResources().getColor(R.color.white));
                tvDSLR.setTextColor(ContextCompat.getColor(Rsettingstest.this, R.color.white));

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        btnGotocamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnRestart.setBackgroundResource(R.drawable.bg2);
                btnGotocamera.setBackgroundResource(R.drawable.bg1);

                Intent intent = new Intent(Rsettingstest.this, Camerafilter.class);
                intent.putExtra("light", light);
                intent.putExtra("lightadditional", AdditionalLightNumber);
                intent.putExtra("lightnumber", Lightnumber);
                intent.putExtra("phordslr", phordslr);
                intent.putExtra("totallight", totallights);
                startActivity(intent);


            }
        });


//        PickerLayoutManager pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);
//        pickerLayoutManager.setChangeAlpha(true);
//        pickerLayoutManager.setScaleDownBy(0.99f);
//        pickerLayoutManager.setScaleDownDistance(1.9f);

//        PickerLayoutManager1 pickerLayoutManager1 = new PickerLayoutManager1(this, PickerLayoutManager1.HORIZONTAL, false);
//        pickerLayoutManager1.setChangeAlpha(true);
//        pickerLayoutManager1.setScaleDownBy(0.99f);
//        pickerLayoutManager1.setScaleDownDistance(1.9f);
//
//        PickerLayoutManager2 pickerLayoutManager2 = new PickerLayoutManager2(this, PickerLayoutManager2.HORIZONTAL, false);
//        pickerLayoutManager2.setChangeAlpha(true);
//        pickerLayoutManager2.setScaleDownBy(0.99f);
//        pickerLayoutManager2.setScaleDownDistance(1.9f);


        SnapHelper snapHelper = new LinearSnapHelper();
//        adapter = new PickerAdapter(this, getData(100), rv);
//        snapHelper.attachToRecyclerView(rv);
//        rv.setOnFlingListener(snapHelper);
//        rv.setLayoutManager(pickerLayoutManager);
//        rv.setAdapter(adapter);
        rv.setOrientation(DSVOrientation.HORIZONTAL);
        adapter = new PickerAdapter(this, getData(), rv);
        rv.setAdapter(adapter);
        rv.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .setMaxScale(1.1f)
                .build());


//        SnapHelper snapHelper1 = new LinearSnapHelper();
//        adapter1 = new PickerAdapter(this, getapertureData(100), rv_aperture);
//        snapHelper1.attachToRecyclerView(rv_aperture);
//        rv_aperture.setOnFlingListener(snapHelper1);
//        rv_aperture.setLayoutManager(pickerLayoutManager1);
//        rv_aperture.setAdapter(adapter1);
        rv_aperture.setOrientation(DSVOrientation.HORIZONTAL);
        adapter1 = new PickerAdapter(this, getapertureData(100), rv_aperture);
        rv_aperture.setAdapter(adapter1);
        rv_aperture.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .setMaxScale(1.1f)
                .build());

        rv_shutterspeed.setOrientation(DSVOrientation.HORIZONTAL);
        adapter2 = new PickerAdapter(this, getshutterspeedData(0), rv_shutterspeed);
        rv_shutterspeed.setAdapter(adapter2);
        rv_shutterspeed.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .setMaxScale(1.1f)
                .build());


        switch (totallights) {
            case 1:
                adapter2 = new PickerAdapter(this, getshutterspeedData(0), rv_shutterspeed);
                snapHelper.attachToRecyclerView(rv_shutterspeed);
                rv_shutterspeed.setAdapter(adapter2);
                rv_shutterspeed.scrollToPosition(18);
                rv_aperture.scrollToPosition(18);
                tvShutterspeed.setText("Shutter Speed: 1/2 sec");
                tvAp.setText("Aperture(F-Stop): 11");

                break;
            case 2:

                adapter2 = new PickerAdapter(this, getshutterspeedData2(0), rv_shutterspeed);
                snapHelper.attachToRecyclerView(rv_shutterspeed);
                rv_shutterspeed.setAdapter(adapter2);
                rv_shutterspeed.scrollToPosition(18);
                rv_aperture.scrollToPosition(18);
                tvShutterspeed.setText("Shutter Speed: 1/4 sec");
                tvAp.setText("Aperture(F-Stop): 11");

                break;
            case 3:

                adapter2 = new PickerAdapter(this, getshutterspeedData3(0), rv_shutterspeed);
                snapHelper.attachToRecyclerView(rv_shutterspeed);
                rv_shutterspeed.setAdapter(adapter2);
                rv_shutterspeed.scrollToPosition(18);
                rv_aperture.scrollToPosition(18);
                tvShutterspeed.setText("Shutter Speed: 1/6 sec");
                tvAp.setText("Aperture(F-Stop): 11");

                break;
            case 4:
            case 5:

                adapter2 = new PickerAdapter(this, getshutterspeedData4and5(0), rv_shutterspeed);
                snapHelper.attachToRecyclerView(rv_shutterspeed);
                rv_shutterspeed.setAdapter(adapter2);
                rv_shutterspeed.scrollToPosition(18);
                rv_aperture.scrollToPosition(18);
                tvShutterspeed.setText("Shutter Speed: 1/8 sec");
                tvAp.setText("Aperture(F-Stop): 11");

                break;

            case 6:
            case 7:

                adapter2 = new PickerAdapter(this, getshutterspeedData6and7(0), rv_shutterspeed);
                snapHelper.attachToRecyclerView(rv_shutterspeed);
                rv_shutterspeed.setAdapter(adapter2);
                rv_shutterspeed.scrollToPosition(18);
                rv_aperture.scrollToPosition(18);
                tvShutterspeed.setText("Shutter Speed: 1/13 sec");
                tvAp.setText("Aperture(F-Stop): 11");

                break;

            case 8:

                adapter2 = new PickerAdapter(this, getshutterspeedData8(0), rv_shutterspeed);
                snapHelper.attachToRecyclerView(rv_shutterspeed);
                rv_shutterspeed.setAdapter(adapter2);
                rv_shutterspeed.scrollToPosition(18);
                rv_aperture.scrollToPosition(18);
                tvShutterspeed.setText("Shutter Speed: 1/15 sec");
                tvAp.setText("Aperture(F-Stop): 11");

                break;
            default:

                adapter2 = new PickerAdapter(this, getshutterspeedData(0), rv_shutterspeed);
                snapHelper.attachToRecyclerView(rv_shutterspeed);
                rv_shutterspeed.setAdapter(adapter2);
                break;
        }

       /* pickerLayoutManager.setOnScrollStopListener(new PickerLayoutManager.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
                isorange = ((TextView) view).getText().toString();
                tvISO.setText("ISO:" + isorange);
                Log.v("position", String.valueOf((int) view.getTag()));
                updateSutterValues((int) view.getTag());

            }
        });*/

        rv.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                onItemChanged(adapter.dataList.get(adapterPosition));
                updateSutterValues(adapterPosition);
            }
        });

//        pickerLayoutManager1.setOnScrollStopListener(new PickerLayoutManager1.onScrollStopListener() {
//            @Override
//            public void selectedView(View view) {
//                String aperturerange = ((TextView) view).getText().toString();
//                tvAp.setText("Aperture(F-Stop):" + aperturerange);
//                Log.v("position", String.valueOf((int) view.getTag()));
//                int val = (int) view.getTag();
//
//                tvShutterspeed.setText("Shutter Speed: " + adapter2.dataList.get(pickerLayoutManager1.getPosition(view)));
//                // adapter2.notifyDataSetChanged();
//                new Handler().postDelayed(() -> {
//
//                    rv_shutterspeed.scrollToPosition(pickerLayoutManager1.getPosition(view));
//
//                }, 200);
//
//            }
//        });

        rv_aperture.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCurrentItemChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

                onItemChanged2(adapter1.dataList.get(adapterPosition));
                //  String aperturerange = ((TextView) view).getText().toString();
                //   tvAp.setText("Aperture(F-Stop):" + aperturerange);
//                Log.v("position", String.valueOf((int) view.getTag()));
//                int val = (int) view.getTag();

                tvShutterspeed.setText("Shutter Speed: " + adapter2.dataList.get(adapterPosition));
                // adapter2.notifyDataSetChanged();
                //  new Handler().postDelayed(() -> {

                rv_shutterspeed.scrollToPosition(adapterPosition);

                //}, 200);

            }
        });

        rv_shutterspeed.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                onItemChanged3(adapter2.dataList.get(adapterPosition));
                //  String shutterspeedrange = ((TextView) view).getText().toString();
                // tvShutterspeed.setText("Shutter Speed:" + shutterspeedrange);
//                Log.v("position", String.valueOf((int) view.getTag()));
//                int val = (int) view.getTag();
                tvAp.setText("Aperture(F-Stop):" + adapter1.dataList.get(adapterPosition));
                // adapter1.notifyDataSetChanged();
                //  new Handler().postDelayed(() -> {
                rv_aperture.scrollToPosition(adapterPosition);
                //   }, 200);
            }
        });



       /* pickerLayoutManager2.setOnScrollStopListener(new PickerLayoutManager2.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
                String shutterspeedrange = ((TextView) view).getText().toString();
                tvShutterspeed.setText("Shutter Speed:" + shutterspeedrange);
                Log.v("position", String.valueOf((int) view.getTag()));
                int val = (int) view.getTag();
                tvAp.setText("Aperture(F-Stop):" + adapter1.dataList.get(pickerLayoutManager2.getPosition(view)));
                // adapter1.notifyDataSetChanged();
                new Handler().postDelayed(() -> {
                    rv_aperture.scrollToPosition(pickerLayoutManager2.getPosition(view) - 2);
                }, 200);

            }
        });*/


        btnRestoreSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rv.smoothScrollToPosition(0);
                rv_aperture.smoothScrollToPosition(18);
                rv_shutterspeed.smoothScrollToPosition(18);


            }
        });


    }

    private void onItemChanged(String s) {
        tvISO.setText("ISO:" + s);
    }

    private void onItemChanged2(String s) {

        tvAp.setText("Aperture(F-Stop):" + s);
    }

    private void onItemChanged3(String s) {

        tvShutterspeed.setText("Shutter Speed: " + s);
    }


    private void updateSutterValues(int pos) {
        List<String> list;
        switch (totallights) {
            case 1:
                list = getshutterspeedData2(pos);
                break;
            case 2:
                list = getshutterspeedData2(pos);
                break;
            case 3:
                list = getshutterspeedData3(pos);
                break;
            case 4:
            case 5:
                list = getshutterspeedData4and5(pos);
                break;
            case 6:
            case 7:
                list = getshutterspeedData6and7(pos);
                break;
            case 8:
                list = getshutterspeedData8(pos);
                break;
            default:
                list = getshutterspeedData(pos);
                break;
        }
        adapter2.swapData(list);
        adapter2.notifyDataSetChanged();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        rv_aperture.smoothScrollToPosition(18);
        rv_shutterspeed.smoothScrollToPosition(18);
//            }
//        }, 200);
        tvAp.setText("Aperture(F-Stop): " + adapter1.dataList.get(18));
        tvShutterspeed.setText("Shutter Speed: " + adapter2.dataList.get(18));


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

                dialog.dismiss();

            }
        });
    }


    public void showDialogDSLR() {
        LinearLayout btnOk;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.my_dialog3);
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

    private void setupData() {
        DSLRExposureSettingsresponse = sharedPreferences.getString("DSLRExposureSettingsresponse", "");


    }

    public List<String> getData() {
        data = new ArrayList<String>();
        data.add("100");
        data.add("200");
        data.add("400");
        data.add("800");
        data.add("1600");
        return data;

    }

    public List<String> getapertureData(int count) {
        List<String> data1 = new ArrayList<>();
        data1.add("1.4");
        data1.add("1.6");
        data1.add("1.8");
        data1.add("2.0");
        data1.add("2.2");
        data1.add("2.5");
        data1.add("2.8");
        data1.add("3.2");
        data1.add("3.5");
        data1.add("4.0");
        data1.add("4.5");
        data1.add("5.0");
        data1.add("5.3");
        data1.add("6.3");
        data1.add("7.1");
        data1.add("8");
        data1.add("9");
        data1.add("10");
        data1.add("11");
        data1.add("13");
        data1.add("14");
        data1.add("16");
        data1.add("18");
        data1.add("20");
        data1.add("22");
        return data1;

    }


    public List<String> getshutterspeedData(int count) {

        List<String> data1 = new ArrayList<>();
        switch (count) {
            case 0:
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                data1.add("0.3");
                data1.add("0.4");
                data1.add("1/2");
                data1.add("0.6");
                data1.add("0.8");
                data1.add("1 sec");
                data1.add("1.3 sec");
                data1.add("1.6 sec");
                data1.add("2 sec");
                break;

            case 1:
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                data1.add("0.3");
                data1.add("0.4");
                data1.add("1/2");
                data1.add("0.6");
                data1.add("0.8");
                data1.add("1 sec");
                break;
            case 2:
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                data1.add("0.3");
                data1.add("0.4");
                data1.add("1/2");
                break;
            case 3:
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                break;
            case 4:
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                break;
        }

        return data1;

    }


    public List<String> getshutterspeedData2(int count) {
        List<String> data1 = new ArrayList<>();
        switch (count) {
            case 0:

                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                data1.add("0.3");
                data1.add("0.4");
                data1.add("1/2");
                data1.add("0.6");
                data1.add("0.8");
                data1.add("1 sec");
                break;

            case 1:
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                data1.add("0.3");
                data1.add("0.4");
                data1.add("1/2");
                break;
            case 2:

                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                break;
            case 3:
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                break;
            case 4:
                data1.add("1/4000");
                data1.add("1/3200");
                data1.add("1/2500");
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                break;
        }

        return data1;
    }

    public List<String> getshutterspeedData3(int count) {
        List<String> data1 = new ArrayList<>();
        switch (count) {
            case 0:
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                data1.add("1/3");
                data1.add("0.4");
                data1.add("1/2");
                data1.add("0.6");
                break;

            case 1:
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                data1.add("1/3");
                data1.add("1/2");
                break;
            case 2:
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");

                break;
            case 3:

                data1.add("1/3200");
                data1.add("1/2500");
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                break;
            case 4:

                data1.add("1/6400");
                data1.add("1/5000");
                data1.add("1/4000");
                data1.add("1/3200");
                data1.add("1/2500");
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                break;
        }

        return data1;
    }

    public List<String> getshutterspeedData8(int count) {
        List<String> data1 = new ArrayList<>();
        switch (count) {
            case 0:
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                break;

            case 1:
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                break;
            case 2:

                data1.add("1/4000");
                data1.add("1/3200");
                data1.add("1/2500");
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");

                break;
            case 3:

                data1.add("1/8000");
                data1.add("1/6400");
                data1.add("1/5000");
                data1.add("1/4000");
                data1.add("1/3200");
                data1.add("1/2500");
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");

                break;
            case 4:
                data1.add("");
                data1.add("");
                data1.add("");
                data1.add("1/8000");
                data1.add("1/6400");
                data1.add("1/5000");
                data1.add("1/4000");
                data1.add("1/3200");
                data1.add("1/2500");
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");

                break;
        }

        return data1;
    }


    public List<String> getshutterspeedData4and5(int count) {
        List<String> data1 = new ArrayList<>();
        switch (count) {
            case 0:

                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                data1.add("0.3");
                data1.add("0.4");
                data1.add("1/2");
                break;

            case 1:
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                break;
            case 2:
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                break;
            case 3:

                data1.add("1/4000");
                data1.add("1/3200");
                data1.add("1/2500");
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");

                break;
            case 4:

                data1.add("1/8000");
                data1.add("1/6400");
                data1.add("1/5000");
                data1.add("1/4000");
                data1.add("1/3200");
                data1.add("1/2500");
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");

                break;
        }

        return data1;
    }


    public List<String> getshutterspeedData6and7(int count) {
        List<String> data1 = new ArrayList<>();
        switch (count) {
            case 0:

                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");
                data1.add("1/5");
                data1.add("1/4");
                data1.add("1/3");

                break;

            case 1:

                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");
                data1.add("1/10");
                data1.add("1/8");
                data1.add("1/6");

                break;
            case 2:

                data1.add("1/3200");
                data1.add("1/2500");
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");
                data1.add("1/20");
                data1.add("1/15");
                data1.add("1/13");


                break;
            case 3:


                data1.add("1/6400");
                data1.add("1/5000");
                data1.add("1/4000");
                data1.add("1/3200");
                data1.add("1/2500");
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");
                data1.add("1/40");
                data1.add("1/30");
                data1.add("1/25");


                break;
            case 4:


                data1.add("1/8000");
                data1.add("1/6400");
                data1.add("1/5000");
                data1.add("1/4000");
                data1.add("1/3200");
                data1.add("1/2500");
                data1.add("1/2000");
                data1.add("1/1600");
                data1.add("1/1250");
                data1.add("1/1000");
                data1.add("1/800");
                data1.add("1/640");
                data1.add("1/500");
                data1.add("1/400");
                data1.add("1/320");
                data1.add("1/250");
                data1.add("1/200");
                data1.add("1/160");
                data1.add("1/125");
                data1.add("1/100");
                data1.add("1/80");
                data1.add("1/60");
                data1.add("1/50");


                break;
        }

        return data1;
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Rsettingstest.this, AdditionalLight.class);
        intent.putExtra("lightnumber", Lightnumber);
        intent.putExtra("light", light);
        intent.putExtra("phordslr", phordslr);
        startActivity(intent);
        finish();
    }

}