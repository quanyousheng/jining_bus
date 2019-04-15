package com.whpe.qrcode.shandong_jining.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.toolbean.TestRealTimeBusBean;

import java.util.ArrayList;

/**
 * Created by yang on 2019/3/26.
 */

public class TestRealTimeBusinfoBusLvAdapter extends BaseAdapter {
    private ArrayList<TestRealTimeBusBean> realTimeBusBeans;
    private Context content;

    public TestRealTimeBusinfoBusLvAdapter(Context context) {
        content=context;
    }

    public void setSite(ArrayList<TestRealTimeBusBean> realTimeBusBeanArrayList){
        this.realTimeBusBeans = realTimeBusBeanArrayList;
    }



    @Override
    public int getCount() {
        if(realTimeBusBeans==null){
            return 0;
        }else {
            return realTimeBusBeans.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(content).inflate(R.layout.item_realtimebusshowbusinfo_bus,null,false);
        ImageView iv_bus=(ImageView)view.findViewById(R.id.iv_bus);
        if(realTimeBusBeans.get(position).isHavebus()){
            iv_bus.setVisibility(View.VISIBLE);
        }else {
            iv_bus.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}
