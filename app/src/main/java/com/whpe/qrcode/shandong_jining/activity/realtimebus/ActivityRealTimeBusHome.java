package com.whpe.qrcode.shandong_jining.activity.realtimebus;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.whpe.qrcode.shandong_jining.GYDZApplication;
import com.whpe.qrcode.shandong_jining.R;
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
    // 定位相关
    LocationClient mLocClient;
    boolean isFirstLoc = true; // 是否首次定位
    public MyLocationListenner myListener = new MyLocationListenner();

    public static String TAG = "LocationUtil";
    private final int SDK_PERMISSION_REQUEST = 127;
    private final int GPS_REQUEST_CODE = 100;
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
    private boolean showLocationFailDialog = false;//显示定位失败弹出提示框
    private boolean showNoDataDialog = false;//显示不在济宁弹出提示框
    private QueryByStationIDAction queryByStationIDAction;
    private boolean hasLocatePermission;//是否拥有定位权限
    private ArrayList<String> permissions = new ArrayList<String>();

    /*****
     * 定位结果回调，重写onReceiveLocation方法
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                sucLongitude = location.getLongitude();
                sucLatitude = location.getLatitude();
//                ToastUtils.showToast(activity, "经度：" + sucLongitude + ",纬度：" + sucLatitude);
                Log.e(TAG, "经度：" + sucLongitude + ",纬度：" + sucLatitude);
                showProgress();
                nearbyStatInfoAction = new NearbyStatInfoAction(ActivityRealTimeBusHome.this, ActivityRealTimeBusHome.this);
                nearbyStatInfoAction.sendAction(sucLongitude, sucLatitude);
                if (expand_list.isGroupExpanded(parentPos)) {
                    queryByStationIDAction.sendAction(parentList.get(parentPos).getStationID());
                }
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

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
//      获取定位权限
        getPersimmions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(30000);
        mLocClient.setLocOption(option);
        if (hasLocatePermission)
            mLocClient.start();
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
        // 退出时销毁定位
        mLocClient.stop();
        super.onStop();
    }


    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
//         适配Android 9.0手机  必须有GPS位置权限才能定位
            if (Build.VERSION.SDK_INT >= 28 && !checkGPSIsOpen()) {
                showAlertDialog("请打开位置信息", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转GPS设置界面
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    }
                });
            } else {
                if (permissions.size() > 0) {
                    hasLocatePermission = false;
                    requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
                } else {
                    hasLocatePermission = true;
                }
            }
        } else {
            hasLocatePermission = true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SDK_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted，do sth；
                    mLocClient.start();// 定位SDK
                } else {
                    // permission denied,
                    ToastUtils.showToast(this, "请授予位置信息权限");
                }
                break;
        }
    }

    /**
     * 检测GPS是否打开
     *
     * @return
     */
    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    /**
     * 跳转GPS设置
     */
    @TargetApi(23)
    private void openGPSSettings() {
        if (checkGPSIsOpen()) {
            if (permissions.size() > 0) {
                hasLocatePermission = false;
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            } else {
                hasLocatePermission = true;
            }
        } else {
            showAlertDialog("请打开位置信息", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转GPS设置界面
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, GPS_REQUEST_CODE);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            openGPSSettings();
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
