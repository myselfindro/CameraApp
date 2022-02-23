package com.example.cameraapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cameraapp.AdditionalLight;
import com.example.cameraapp.R;
import com.example.cameraapp.SelectstudioLight;
import com.example.cameraapp.model.StudiolightModel;

import java.util.ArrayList;

public class StudiolightAdapter2 extends RecyclerView.Adapter<StudiolightAdapter2.MyViewHolder> {

    private static final String TAG = "Myapp";
    private LayoutInflater inflater;
    private ArrayList<StudiolightModel> studiolightModelArrayList;
    ArrayList<StudiolightModel> lightnumberModelArrayList;
    String lightvalue;

    Context ctx;
    int selectedPosition = -1;


    public StudiolightAdapter2(Context ctx, ArrayList<StudiolightModel> studiolightModelArrayList, ArrayList<StudiolightModel> lightnumberModelArrayList, String light) {
        inflater = LayoutInflater.from(ctx);
        this.studiolightModelArrayList = studiolightModelArrayList;
        this.lightnumberModelArrayList = lightnumberModelArrayList;
        this.ctx = ctx;
        lightvalue = light;

    }


    public StudiolightAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_studiolight, parent, false);
        StudiolightAdapter2.MyViewHolder holder = new StudiolightAdapter2.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(StudiolightAdapter2.MyViewHolder holder, int position) {

        holder.tvLight.setText(studiolightModelArrayList.get(position).getLightboxname());
        if (selectedPosition == position) {

            if (lightvalue.equals("fluorescnent")) {
                switch (studiolightModelArrayList.get(position).getLightboxname()) {
                    case "AL1-LED Accent Light":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    case "AL2-LED Accent Light":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    default:
                        holder.btnLight.setBackgroundResource(R.drawable.bg1);
                        break;
                }
            } else if (lightvalue.equals("Led")){
                switch (studiolightModelArrayList.get(position).getLightboxname()) {
                    case "AL1 Accent Light":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    case "AL2 Accent Light":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    default:
                        holder.btnLight.setBackgroundResource(R.drawable.bg1);
                        break;
                }
            }

        } else {

            if (lightvalue.equals("fluorescnent")) {
                switch (studiolightModelArrayList.get(position).getLightboxname()) {
                    case "AL1-LED Accent Light":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    case "AL2-LED Accent Light":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    default:
                        holder.btnLight.setBackgroundResource(R.drawable.border);
                        break;
                }
            } else if (lightvalue.equals("Led")){
                switch (studiolightModelArrayList.get(position).getLightboxname()) {
                    case "AL1 Accent Light":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    case "AL2 Accent Light":
                        holder.btnLight.setBackgroundResource(R.drawable.bg3);
                        break;
                    default:
                        holder.btnLight.setBackgroundResource(R.drawable.border);
                        break;
                }
            }

        }
        holder.btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
//                String number = lightnumberModelArrayList.get(position).getLedlightnumber();
//                Log.d(TAG,"LEDnumber-->"+number);
                ((AdditionalLight) ctx).AdditionalLightNumber(lightnumberModelArrayList.get(position), studiolightModelArrayList.get(position).getLightboxname());


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
