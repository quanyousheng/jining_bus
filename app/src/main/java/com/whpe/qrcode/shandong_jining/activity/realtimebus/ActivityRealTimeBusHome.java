package com.whpe.qrcode.shandong_jining.activity.realtimebus;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.bigtools.BDLocation.BDLocationUtils;
import com.whpe.qrcode.shandong_jining.bigtools.BDLocation.Const;
import com.whpe.qrcode.shandong_jining.bigtools.GlobalConfig;
import com.whpe.qrcode.shandong_jining.bigtools.ToastUtils;
import com.whpe.qrcode.shandong_jining.net.JsonComomUtils;
import com.whpe.qrcode.shandong_jining.net.action.NearbyStatInfoAction;
import com.whpe.qrcode.shandong_jining.net.action.QueryByStationIDAction;
import com.whpe.qrcode.shandong_jining.net.getbean.StationInfoList;
import com.whpe.qrcode.shandong_jining.net.getbean.StationRealTimeInfoList;
import com.whpe.qrcode.shandong_jining.parent.BackgroundTitleActivity;
import com.whpe.qrcode.shandong_jining.view.adapter.MyExtandableListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityRealTimeBusHome extends BackgroundTitleActivity implements View.OnClickListener, NearbyStatInfoAction.Inter_NearbyStatInfo, QueryByStationIDAction.Inter_QueryByStationID {
    public static String TAG = "LocationUtil";
    private TextView tv_refresh;
    private Button btn_tosearch;
    private ExpandableListView expand_list;
    private MyExtandableListViewAdapter myAdapter;
    private List<StationInfoList.StationInfo> parentList = new ArrayList<>();
    private Map<Integer, List<StationRealTimeInfoList.StationRealTimeInfo>> childMap = new HashMap<>();
    private NearbyStatInfoAction nearbyStatInfoAction;
    private Activity activity;
    private int parentPos;//记录点击的第几个父条目
    private String nearSta;//最近站点
    private double longitude, latitude;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获取经纬度
                BDLocationUtils bdLocationUtils = new BDLocationUtils(this);
                bdLocationUtils.doLocation();//开启定位
                bdLocationUtils.mLocationClient.start();//开始定位
                longitude = Const.LONGITUDE;
                latitude = Const.LATITUDE;
                Log.e(TAG, "百度定位：" + Const.LONGITUDE + "\n" + Const.LATITUDE);
            } else {
                Log.e(TAG, "权限没获取！！！" + "\n");
            }
        }
    }

    @Override
    protected void afterLayout() {
        super.afterLayout();
        showProgress();
        nearbyStatInfoAction = new NearbyStatInfoAction(this, this);
        nearbyStatInfoAction.sendAction(longitude, latitude);
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        if (Build.VERSION.SDK_INT >= 23) {
            //如果用户并没有同意该权限
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            } else {
                //获取经纬度
                BDLocationUtils bdLocationUtils = new BDLocationUtils(this);
                bdLocationUtils.doLocation();//开启定位
                bdLocationUtils.mLocationClient.start();//开始定位
                longitude = Const.LONGITUDE;
                latitude = Const.LATITUDE;
                Log.e(TAG, "百度定位：" + Const.LONGITUDE + "\n" + Const.LATITUDE);
            }
        }
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_realtimebushome);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setMyTitleColor(R.color.transparency);
        setTitle(getString(R.string.realtimebushome_title));
        btn_tosearch.setOnClickListener(this);
        tv_refresh.setOnClickListener(this);
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        activity = this;
        btn_tosearch = findViewById(R.id.btn_tosearch);
        expand_list = findViewById(R.id.expand_list);
        tv_refresh = findViewById(R.id.tv_refresh);
        myAdapter = new MyExtandableListViewAdapter(this,parentList, childMap);
        expand_list.setAdapter(myAdapter);
        expand_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
               //如果分组被打开 直接关闭
                if (expand_list.isGroupExpanded(groupPosition)) {
                    expand_list.collapseGroup(groupPosition);
                } else {
                    showProgress();
                    parentPos = groupPosition;
                    QueryByStationIDAction queryByStationIDAction = new QueryByStationIDAction(activity, ActivityRealTimeBusHome.this);
                    queryByStationIDAction.sendAction(parentList.get(groupPosition).getStationID());
                }
                return true;
            }
        });
        expand_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("RouteID", childMap.get(groupPosition).get(childPosition).getRouteID());
                bundle.putString("StationID", parentList.get(groupPosition).getStationID());
                transAty(ActivityRealTimeBusShowBusInfo.class, bundle);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_refresh:
                showProgress();
                longitude = Const.LONGITUDE;
                latitude = Const.LATITUDE;
                nearbyStatInfoAction.sendAction(longitude, latitude);
                break;
            case R.id.btn_tosearch:
                Bundle bundle = new Bundle();
                bundle.putString("nearSta", nearSta);
                transAty(ActivityRealTimeBusSearch.class, bundle);
                break;
        }
    }

    @Override
    public void onQuerySuccess(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode = getinfo.get(0);
            if (rescode.equals(GlobalConfig.RESCODE_SUCCESS)) {
                StationInfoList stationInfoList = new StationInfoList();
                stationInfoList = (StationInfoList) JsonComomUtils.parseAllInfo(getinfo.get(2), stationInfoList);
               if (stationInfoList.getList().size()>0){
                   nearSta = stationInfoList.getList().get(0).getStationName();
                   parentList.addAll(stationInfoList.getList());
                   myAdapter.notifyDataSetChanged();
               }
            } else {
                checkAllUpadate(rescode, getinfo);
            }
        } catch (Exception e) {
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onQueryFaild(String resmsg) {
        dissmissProgress();
        ToastUtils.showToast(this, resmsg);
    }

    @Override
    public void onQueryByStationIDSuccess(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode = getinfo.get(0);
            if (rescode.equals(GlobalConfig.RESCODE_SUCCESS)) {
                StationRealTimeInfoList realTimeInfoList = new StationRealTimeInfoList();
                realTimeInfoList = (StationRealTimeInfoList) JsonComomUtils.parseAllInfo(getinfo.get(2), realTimeInfoList);
                childMap.put(parentPos, realTimeInfoList.getList());
                myAdapter.notifyDataSetChanged();
            } else {
                checkAllUpadate(rescode, getinfo);
            }
        } catch (Exception e) {
            showExceptionAlertDialog();
        }
        expand_list.expandGroup(parentPos, true);
    }

    @Override
    public void onQueryByStationIDFaild(String resmsg) {
        dissmissProgress();
        ToastUtils.showToast(this, resmsg);
    }
}
