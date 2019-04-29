package com.whpe.qrcode.shandong_jining.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.bigtools.MyDrawableUtils;
import com.whpe.qrcode.shandong_jining.toolbean.RealTimeBusBean;

import java.util.ArrayList;

public class RouteStationAdapter extends BaseAdapter {
    private ArrayList<RealTimeBusBean> realTimeBusBeans;
    private String stationId;
    private Context context;

    public RouteStationAdapter(Context context,ArrayList<RealTimeBusBean> realTimeBusBeans, String stationId) {
        this.realTimeBusBeans = realTimeBusBeans;
        this.stationId = stationId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return realTimeBusBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return realTimeBusBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_realtimebusshowbusinfo_site, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.iv_sitecircle = convertView.findViewById(R.id.iv_sitecircle);
            viewHolder.iv_bus = convertView.findViewById(R.id.iv_bus);
            viewHolder.tv_site = convertView.findViewById(R.id.tv_site);
            viewHolder.v_topline = convertView.findViewById(R.id.v_topline);
            viewHolder.v_bottomline = convertView.findViewById(R.id.v_bottomline);
            viewHolder.rl_item = convertView.findViewById(R.id.rl_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        RealTimeBusBean busBean=realTimeBusBeans.get(position);
        String staName = busBean.getSite();
        if (staName.contains("(上行)") || staName.contains("(下行)")
                || staName.contains("（上行）") || staName.contains("（下行）")) {
            staName=staName.substring(0, staName.length() - 4);
        }
        viewHolder.tv_site.setText(staName);
        if (busBean.isHavebus()) {
            viewHolder.iv_sitecircle.setBackground(MyDrawableUtils.getDrawble(context, R.drawable.aty_realtimebusshowbusinfo_itemsiteget));
            viewHolder.iv_bus.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iv_sitecircle.setBackground(MyDrawableUtils.getDrawble(context, R.drawable.aty_realtimebusshowbusinfo_itemsitenotget));
            viewHolder.iv_bus.setVisibility(View.GONE);
        }
        if (position == 0) {
            viewHolder.v_topline.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.v_topline.setVisibility(View.VISIBLE);
        }
        if (position == realTimeBusBeans.size() - 1) {
            viewHolder.v_bottomline.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.v_bottomline.setVisibility(View.VISIBLE);
        }
        if (busBean.getStationId().equals(stationId)){
            viewHolder.rl_item.setBackgroundColor(context.getResources().getColor(R.color.common_background_gray));
        }else {
            viewHolder.rl_item.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }

    public static class MyViewHolder {
        RelativeLayout rl_item;
        TextView tv_site;
        ImageView iv_sitecircle;
        ImageView iv_bus;
        View v_topline;
        View v_bottomline;
    }
}
