package com.example.cameraapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cameraapp.R;

import java.util.List;

public class PickerAdapter2 extends RecyclerView.Adapter<PickerAdapter2.TextVH> {

    private Context context;
    public List<String> dataList2;
    private RecyclerView recyclerView;

    public PickerAdapter2(Context context, List<String> dataList, RecyclerView recyclerView) {
        this.context = context;
        this.dataList2 = dataList;
        this.recyclerView = recyclerView;
    }

    @Override
    public TextVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.picker_item_layout2, parent, false);
        return new TextVH(view);
    }

    @Override
    public void onBindViewHolder(TextVH holder, final int position) {
        TextVH textVH = holder;
        textVH.pickerTxt.setText(dataList2.get(position));
        textVH.pickerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView != null) {
                    recyclerView.smoothScrollToPosition(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList2.size();
    }

    public void swapData(List<String> newData) {
        dataList2 = newData;
        notifyDataSetChanged();
    }

    class TextVH extends RecyclerView.ViewHolder {
        TextView pickerTxt;

        public TextVH(View itemView) {
            super(itemView);
            pickerTxt = (TextView) itemView.findViewById(R.id.picker_item);
        }
    }
}
