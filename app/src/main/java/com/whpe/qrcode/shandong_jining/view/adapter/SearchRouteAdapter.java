package com.whpe.qrcode.shandong_jining.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.net.getbean.RouteStationInfoList;

import java.util.List;

public class SearchRouteAdapter extends BaseAdapter {
    private List<RouteStationInfoList.RouteStationInfo.SegmentListBean> segmentList;
    private String nearSta;
    private int route;

    public SearchRouteAdapter(List<RouteStationInfoList.RouteStationInfo.SegmentListBean> segmentList, String nearSta, int route) {
        this.segmentList = segmentList;
        this.nearSta = nearSta;
        this.route = route;
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
        RouteStationInfoList.RouteStationInfo.SegmentListBean busBean = segmentList.get(position);
        viewHolder.tv_route.setText(route+"路");
        List<RouteStationInfoList.RouteStationInfo.SegmentListBean.StationListBean> stationListBean = busBean.getStationList();
        viewHolder.tv_way.setText(stationListBean.get(0).getStationName() + " - " + stationListBean.get(stationListBean.size() - 1).getStationName());
        viewHolder.tv_nearSta.setText("最近站点：" + nearSta);

        return convertView;
    }

    public static class MyViewHolder {
        TextView tv_nearSta;
        TextView tv_route;
        TextView tv_way;
    }
}
