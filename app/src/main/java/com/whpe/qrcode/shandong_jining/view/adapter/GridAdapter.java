package com.whpe.qrcode.shandong_jining.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.net.getbean.Item;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private Integer[] data = {R.drawable.ssgj, R.drawable.mscz, R.drawable.kpyc, R.drawable.grzx};

    public GridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_grid_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv);
        imageView.setImageResource(data[position]);
        return convertView;
    }
}
