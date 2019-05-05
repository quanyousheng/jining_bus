package com.whpe.qrcode.shandong_jining.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.net.getbean.RouteStationInfoList;

import java.util.List;

public class SearchRouteAdapter1 extends BaseAdapter {
    private List<RouteStationInfoList.RouteStationInfo> segmentList;
    private String nearSta;

    public SearchRouteAdapter1(List<RouteStationInfoList.RouteStationInfo> segmentList, String nearSta) {
        this.segmentList = segmentList;
        this.nearSta = nearSta;
    }

    @Override
    public int getCount() {
        return segmentList.size();
    }

    @Override
    public Object getItem(int position) {
        return segmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route_search, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.tv_route = convertView.findViewById(R.id.tv_route);
            viewHolder.tv_way = convertView.findViewById(R.id.tv_way);
            viewHolder.tv_nearSta = convertView.findViewById(R.id.tv_nearSta);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        RouteStationInfoList.RouteStationInfo busBean = segmentList.get(position);
        String routeName = busBean.getRouteName();
        viewHolder.tv_route.setText(routeName);
        List<RouteStationInfoList.RouteStationInfo.SegmentListBean.StationListBean> stationListBean = busBean.getSegmentList().get(0).getStationList();
        String staStartName = stationListBean.get(0).getStationName();
        String staEndName = stationListBean.get(stationListBean.size() - 1).getStationName();
        if (staStartName.contains("(上行)") || staStartName.contains("(下行)")
                || staStartName.contains("（上行）") || staStartName.contains("（下行）")) {
            staStartName = staStartName.substring(0, staStartName.length() - 4);
        }
        if (staStartName.contains("(上)") || staStartName.contains("(下)")
                || staStartName.contains("（上）") || staStartName.contains("（下）")) {
            staStartName = staStartName.substring(0, staStartName.length() - 3);
        }
        if (staEndName.contains("(上行)") || staEndName.contains("(下行)")
                || staEndName.contains("（上行）") || staEndName.contains("（下行）")
                || staEndName.contains("（上行 ）") || staEndName.contains("（下行 ）")) {
            staEndName = staEndName.substring(0, staEndName.length() - 3);
        }
        if (staEndName.contains("(上)") || staEndName.contains("(下)")
                || staEndName.contains("（上）") || staEndName.contains("（下）")) {
            staEndName = staEndName.substring(0, staEndName.length() - 3);
        }
        viewHolder.tv_way.setText(staStartName + " - " + staEndName);
        viewHolder.tv_nearSta.setText("最近站点：" + nearSta);

        return convertView;
    }

    public static class MyViewHolder {
        TextView tv_nearSta;
        TextView tv_route;
        TextView tv_way;
    }
}
