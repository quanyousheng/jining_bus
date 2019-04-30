package com.whpe.qrcode.shandong_jining.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.net.action.QueryByStationIDAction;
import com.whpe.qrcode.shandong_jining.net.getbean.StationInfoList;
import com.whpe.qrcode.shandong_jining.net.getbean.StationRealTimeInfoList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyExtandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    public List<StationInfoList.StationInfo> groupList;
    public Map<Integer, List<StationRealTimeInfoList.StationRealTimeInfo>> childMap;

    public MyExtandableListViewAdapter(Context context, List<StationInfoList.StationInfo> groupList, Map<Integer, List<StationRealTimeInfoList.StationRealTimeInfo>> childMap) {
        this.context = context;
        this.groupList = groupList;
        this.childMap = childMap;
    }

    @Override
    // 获取分组的个数
    public int getGroupCount() {
        return groupList.size();
    }

    //获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return childMap.get(groupPosition).size();
    }

    // 获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    //获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childMap.get(groupPosition).get(childPosition);
    }

    //获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded    该组是展开状态还是伸缩状态
     * @param convertView   重用已有的视图对象
     * @param parent        返回的视图对象始终依附于的视图组
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_realtimebushome_parent, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvSite = convertView.findViewById(R.id.tv_site);
            groupViewHolder.tvMile = convertView.findViewById(R.id.tv_miles);
            groupViewHolder.iv_arrow = convertView.findViewById(R.id.iv_arrow);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        String staName = groupList.get(groupPosition).getStationName();
//        if (staName.contains("(上行)") || staName.contains("(下行)")
//                || staName.contains("（上行）") || staName.contains("（下行）")) {
//            staName = staName.substring(0, staName.length() - 4);
//        }
        if (groupPosition == 0) {
            staName = staName + " （离我最近）";
        }
        groupViewHolder.tvSite.setText(staName);
        String distance = String.valueOf(groupList.get(groupPosition).getDistance());
        if (distance.contains(".")) {
            //      距离保留到小数点后两位
            groupViewHolder.tvMile.setText(distance.substring(0, distance.indexOf(".") + 2) + "米");
        } else {
            groupViewHolder.tvMile.setText(distance + "米");
        }
        if (isExpanded) {
            groupViewHolder.iv_arrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down));
        } else {
            groupViewHolder.iv_arrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_right));
        }
        return convertView;
    }

    /**
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild   子元素是否处于组中的最后一个
     * @param convertView   重用已有的视图(View)对象
     * @param parent        返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, View,
     * ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_realtimebushome_son, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_route = (TextView) convertView.findViewById(R.id.tv_route);
            childViewHolder.tv_next = (TextView) convertView.findViewById(R.id.tv_next);
            childViewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tv_route.setText(childMap.get(groupPosition).get(childPosition).getRouteName());
        List<StationRealTimeInfoList.StationRealTimeInfo.RealtimeInfoListBean> realTimeInfoList = childMap.get(groupPosition).get(childPosition).getRealtimeInfoList();
        if (realTimeInfoList != null && realTimeInfoList.size() > 0) {
            String staName = realTimeInfoList.get(0).getArriveStaName();
            if (staName.contains("(上行)") || staName.contains("(下行)")
                    || staName.contains("（上行）") || staName.contains("（下行）")) {
                childViewHolder.tv_next.setText("下一站：" + realTimeInfoList.get(0).getArriveStaName().substring(0, staName.length() - 4));
            } else {
                childViewHolder.tv_next.setText("下一站：" + realTimeInfoList.get(0).getArriveStaName());
            }
            childViewHolder.tv_time.setText(realTimeInfoList.get(0).getRunTime() + "分钟后");
        } else {
            childViewHolder.tv_next.setText("");
            childViewHolder.tv_time.setText("");
        }
        return convertView;
    }

    //指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class GroupViewHolder {
        TextView tvSite;
        TextView tvMile;
        ImageView iv_arrow;
    }

    public static class ChildViewHolder {
        TextView tv_time, tv_next, tv_route;
    }
}
