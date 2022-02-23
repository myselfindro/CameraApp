package com.example.cameraapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cameraapp.AdditionalLight;
import com.example.cameraapp.R;
import com.example.cameraapp.SelectstudioLight;
import com.example.cameraapp.model.StudiolightModel;

import java.util.ArrayList;

public class StudiolightAdapter extends RecyclerView.Adapter<StudiolightAdapter.MyViewHolder> {

    private static final String TAG = "Myapp";
    private LayoutInflater inflater;
    public ArrayList<StudiolightModel> studiolightModelArrayList;
    ArrayList<StudiolightModel> lightnumberModelArrayList;

    Context ctx;
    int selectedPosition = -1;
    String lightvalue;


    public StudiolightAdapter(Context ctx, ArrayList<StudiolightModel> studiolightModelArrayList, ArrayList<StudiolightModel> lightnumberModelArrayList, String light) {
        inflater = LayoutInflater.from(ctx);
        this.studiolightModelArrayList = studiolightModelArrayList;
        this.lightnumberModelArrayList = lightnumberModelArrayList;
        this.ctx = ctx;
        lightvalue = light;

    }


    public StudiolightAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_studiolight, parent, false);
        StudiolightAdapter.MyViewHolder holder = new StudiolightAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(StudiolightAdapter.MyViewHolder holder, int position) {

        holder.tvLight.setText(studiolightModelArrayList.get(position).getLightboxname());
        Log.d(TAG, "lightvalue: " + lightvalue);

        if (selectedPosition == position) {

            if (lightvalue.equals("fluorescnent")) {
                switch (studiolightModelArrayList.get(position).getLightboxname()) {
                    case "US31LED":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    case "VS36LED":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    case "VS53LED":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    default:
                        holder.btnLight.setBackgroundResource(R.drawable.bg1);
                        break;
                }
            } else {
                holder.btnLight.setBackgroundResource(R.drawable.bg1);
            }

        } else {
            if (lightvalue.equals("fluorescnent")) {
                switch (studiolightModelArrayList.get(position).getLightboxname()) {
                    case "US31LED":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    case "VS36LED":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    case "VS53LED":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    default:
                        holder.btnLight.setBackgroundResource(R.drawable.border);
                        break;
                }
            } else {
                holder.btnLight.setBackgroundResource(R.drawable.border);
            }
        }
        holder.btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
//                String number = lightnumberModelArrayList.get(position).getLedlightnumber();
//                Log.d(TAG,"LEDnumber-->"+number);

                ((SelectstudioLight) ctx).lightNumber(lightnumberModelArrayList.get(position), studiolightModelArrayList.get(position).getLightboxname());


            }
        });

    }

    @Override
    public int getItemCount() {
        return studiolightModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvLight;
        LinearLayout btnLight;
        private View view;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvLight = itemView.findViewById(R.id.tvLight);
            btnLight = itemView.findViewById(R.id.btnLight);

        }
    }


}
