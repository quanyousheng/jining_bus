package com.whpe.qrcode.shandong_jining.activity.realtimebus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.whpe.qrcode.shandong_jining.GYDZApplication;
import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.bigtools.BDLocation.LocationService;
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
    private static final double longitude = 116.5938885585, latitude = 35.4209607038;//默认市政府地址
    private static double sucLongitude, sucLatitude;//定位成功的经纬度
    private LocationService locationService;//百度定位服务
    private boolean showLocationFailDialog = false;//显示定位失败弹出提示框
    private boolean showNoDataDialog = false;//显示不在济宁弹出提示框
    private QueryByStationIDAction queryByStationIDAction;

    /*****
     * 定位结果回调，重写onReceiveLocation方法
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                sucLongitude = location.getLongitude();
                sucLatitude = location.getLatitude();
                showProgress();
                nearbyStatInfoAction = new NearbyStatInfoAction(ActivityRealTimeBusHome.this, ActivityRealTimeBusHome.this);
                nearbyStatInfoAction.sendAction(sucLongitude, sucLatitude);
                if (parentPos != 0 && expand_list.isGroupExpanded(parentPos)) {
                    queryByStationIDAction.sendAction(parentList.get(parentPos).getStationID());
                }

                StringBuffer sb = new StringBuffer(256);
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(latitude);
                sb.append("\nlontitude : ");// 经度
                sb.append(longitude);
                Log.e(TAG, sb.toString());
            } else {
                if (sucLatitude != 0 && sucLongitude != 0) {
//                    showProgress();
                    nearbyStatInfoAction.sendAction(sucLongitude, sucLatitude);
                } else {
                    //         对话框只显示一次
                    if (!showLocationFailDialog) {
                        showAlertDialog("定位失败,切换到默认位置济宁市政府", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showLocationFailDialog = true;
                                showProgress();
                                nearbyStatInfoAction.sendAction(longitude, latitude);
                            }
                        });
                    }
                }
            }
        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        // -----------location config ------------
        locationService = ((GYDZApplication) getApplication()).locationService;
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
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
        myAdapter = new MyExtandableListViewAdapter(this, parentList, childMap);
        expand_list.setAdapter(myAdapter);
        expand_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //如果分组被打开 直接关闭
                for (int i = 0; i < parentList.size(); i++) {
                    if (expand_list.isGroupExpanded(i)) {
                        expand_list.collapseGroup(i);
                    } else {
                        if (i == groupPosition) {
                            showProgress();
                            parentPos = groupPosition;
                            queryByStationIDAction = new QueryByStationIDAction(activity, ActivityRealTimeBusHome.this);
                            queryByStationIDAction.sendAction(parentList.get(groupPosition).getStationID());
                        }
                    }
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
                if (sucLatitude != 0 && sucLongitude != 0) {
                    showProgress();
                    nearbyStatInfoAction.sendAction(sucLongitude, sucLatitude);
                } else {
                    showProgress();
                    nearbyStatInfoAction.sendAction(longitude, latitude);
                }
                break;
            case R.id.btn_tosearch:
                Bundle bundle = new Bundle();
                bundle.putString("nearSta", nearSta);
                transAty(ActivityRealTimeBusSearch.class, bundle);
                break;
        }
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    public void onQuerySuccess(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode = getinfo.get(0);
            if (rescode.equals(GlobalConfig.RESCODE_SUCCESS)) {
                StationInfoList stationInfoList = new StationInfoList();
                stationInfoList = (StationInfoList) JsonComomUtils.parseAllInfo(getinfo.get(2), stationInfoList);
                List<StationInfoList.StationInfo> dataList = stationInfoList.getList();
                if (dataList != null && dataList.size() > 0) {
                    for (StationInfoList.StationInfo stationInfo : dataList) {
                        String name = stationInfo.getStationName();
                        if (name.contains("(上行)") || name.contains("(下行)")
                                || name.contains("（上行）") || name.contains("（下行）")) {
                            name = name.substring(0, name.length() - 4);
                        }
                        stationInfo.setStationName(name);
                    }
                    for (int i = 0; i < dataList.size(); i++) {
                        String name = dataList.get(i).getStationName();
                        for (int j = i + 1; j < dataList.size(); j++) {
                            if (name.equals(dataList.get(j).getStationName())) {
                                dataList.remove(dataList.get(j));
                            }
                        }
                    }
                    nearSta = dataList.get(0).getStationName();
                    parentList.clear();
                    parentList.addAll(dataList);
                    myAdapter.notifyDataSetChanged();
                } else {
                    //               对话框只显示一次
                    if (!showNoDataDialog && parentList.size() == 0) {
                        showAlertDialog("当前位置不在济宁,切换到默认位置济宁市政府", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showNoDataDialog = true;
                                showProgress();
                                nearbyStatInfoAction.sendAction(longitude, latitude);
                            }
                        });
                    }
                }
            } else {
                checkAllUpadate(rescode, getinfo);
            }
        } catch (Exception e) {
            Log.e("EEE", e.toString());
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
            Log.e("EEE", e.toString());
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
