package com.example.cameraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cameraapp.adapter.PickerAdapter;

import java.util.ArrayList;
import java.util.List;

import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager;
import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager1;
import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager2;


public class Rsettings extends AppCompatActivity {

    private static final String TAG = "Myapp";
    ImageView btn_back;
    LinearLayout btnSetting;
    RecyclerView rv, rv_aperture, rv_shutterspeed;
    PickerAdapter adapter;
    TextView tvISO,tvAp, tvShutterspeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsettings);
        btn_back = findViewById(R.id.btn_back);
        btnSetting = findViewById(R.id.btnSetting);
        tvISO = findViewById(R.id.tvISO);
        tvAp = findViewById(R.id.tvAp);
        tvShutterspeed = findViewById(R.id.tvShutterspeed);
        rv = findViewById(R.id.rv);
        rv_aperture = findViewById(R.id.rv_aperture);
        rv_shutterspeed = findViewById(R.id.rv_shutterspeed);
        onClick();

    }

    public void onClick() {

        PickerLayoutManager pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);
        pickerLayoutManager.setChangeAlpha(true);
        pickerLayoutManager.setScaleDownBy(0.99f);
        pickerLayoutManager.setScaleDownDistance(1.4f);

        PickerLayoutManager1 pickerLayoutManager1 = new PickerLayoutManager1(this, PickerLayoutManager1.HORIZONTAL, false);
        pickerLayoutManager1.setChangeAlpha(true);
        pickerLayoutManager1.setScaleDownBy(0.99f);
        pickerLayoutManager1.setScaleDownDistance(1.4f);

        PickerLayoutManager2 pickerLayoutManager2 = new PickerLayoutManager2(this, PickerLayoutManager2.HORIZONTAL, false);
        pickerLayoutManager2.setChangeAlpha(true);
        pickerLayoutManager2.setScaleDownBy(0.99f);
        pickerLayoutManager2.setScaleDownDistance(1.4f);


        adapter = new PickerAdapter(this, getData(100), rv);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv);
        rv.setLayoutManager(pickerLayoutManager);
        rv.setAdapter(adapter);

        adapter = new PickerAdapter(this, getapertureData(100), rv_aperture);
        snapHelper.attachToRecyclerView(rv_aperture);
        rv_aperture.setLayoutManager(pickerLayoutManager1);
        rv_aperture.setAdapter(adapter);

        adapter = new PickerAdapter(this, getshutterspeedData(100), rv_shutterspeed);
        snapHelper.attachToRecyclerView(rv_shutterspeed);
        rv_shutterspeed.setLayoutManager(pickerLayoutManager2);
        rv_shutterspeed.setAdapter(adapter);


        pickerLayoutManager.setOnScrollStopListener(new PickerLayoutManager.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
                String value = ((TextView) view).getText().toString();
                tvISO.setText("ISO:"+value);
            }
        });


        pickerLayoutManager1.setOnScrollStopListener(new PickerLayoutManager1.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
                String value = ((TextView) view).getText().toString();
                tvAp.setText("Aperture(F-Stop):"+value);
            }
        });

        pickerLayoutManager2.setOnScrollStopListener(new PickerLayoutManager2.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
                String value = ((TextView) view).getText().toString();
                tvShutterspeed.setText("Shutter Speed:"+value);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();

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

                Intent intent = new Intent(Rsettings.this, Camerafilter.class);
                startActivity(intent);
            }
        });
    }

    public List<String> getData(int count) {
        List<String> data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("");
        data.add("20");
        data.add("50");
        data.add("100");
        data.add("200");
        data.add("400");
        data.add("800");
        data.add("1600");
        data.add("");
        data.add("");
        data.add("");

        return data;

    }

    public List<String> getapertureData(int count) {
        List<String> data1 = new ArrayList<>();
        data1.add("");
        data1.add("");
        data1.add("");
        data1.add("1.2");
        data1.add("1.4");
        data1.add("1.0");
        data1.add("1.8");
        data1.add("2.0");
        data1.add("2.2");
        data1.add("2.5");
        data1.add("");
        data1.add("");
        data1.add("");

        return data1;

    }

    public List<String> getshutterspeedData(int count) {
        List<String> data1 = new ArrayList<>();
        data1.add("");
        data1.add("");
        data1.add("");
        data1.add("2.0s");
        data1.add("1.6s");
        data1.add("1.3s");
        data1.add("1.0s");
        data1.add("0.8s");
        data1.add("0.6s");
        data1.add("1/2s");
        data1.add("");
        data1.add("");
        data1.add("");

        return data1;

    }

}