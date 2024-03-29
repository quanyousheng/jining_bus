package com.whpe.qrcode.shandong_jining.activity.realtimebus;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.bigtools.GlobalConfig;
import com.whpe.qrcode.shandong_jining.bigtools.ToastUtils;
import com.whpe.qrcode.shandong_jining.net.JsonComomUtils;
import com.whpe.qrcode.shandong_jining.net.action.BusRealTimeInfoAction;
import com.whpe.qrcode.shandong_jining.net.action.RouteStationInfoAction;
import com.whpe.qrcode.shandong_jining.net.getbean.BusRealTimeInfo;
import com.whpe.qrcode.shandong_jining.net.getbean.RouteStationInfoList;
import com.whpe.qrcode.shandong_jining.parent.CustomNormalTitleActivity;
import com.whpe.qrcode.shandong_jining.toolbean.RealTimeBusBean;
import com.whpe.qrcode.shandong_jining.view.adapter.RouteStationAdapter;

import java.util.ArrayList;
import java.util.List;


public class ActivityRealTimeBusShowBusInfo extends CustomNormalTitleActivity implements View.OnClickListener, RouteStationInfoAction.Inter_RouteStationInfo, BusRealTimeInfoAction.Inter_RealTimeInfo {
    private TextView tv_routeName, tv_way, tv_time, tv_price;
    private FrameLayout frame_refresh, frame_changeDirection;
    private ListView lv_busandsiteinfo;
    private RouteStationAdapter adapter;
    private int routeId, segmentId;//segmentId代表单程ID
    private String stationId;
    private ArrayList<RealTimeBusBean> stationList = new ArrayList<RealTimeBusBean>();
    private ArrayList<String> stationIdList = new ArrayList<>();
    private List<RouteStationInfoList.RouteStationInfo.SegmentListBean> segmentList;
    private boolean isChangeDir;//是否换方向
    private RouteStationInfoAction routeStationInfoAction;
    private BusRealTimeInfoAction realTimeInfoAction;
    private static final int TIMER = 999;
    private static boolean flag;
    private static boolean onlyOnce;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIMER:
                    showProgress();
                    realTimeInfoAction.sendAction(routeId, segmentId);
                    if (flag) {
                        mHandler.sendEmptyMessageDelayed(TIMER, 15000);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        flag = true;
        onlyOnce = true;
        showProgress();
        routeStationInfoAction.sendAction(routeId);
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        setMyTitleColor(R.color.comon_text_black_less);
        routeId = getIntent().getExtras().getInt("RouteID");
        stationId = getIntent().getExtras().getString("StationID");
        isChangeDir = getIntent().getExtras().getBoolean("isChangeDir", false);
        routeStationInfoAction = new RouteStationInfoAction(this, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        flag = false;
        mHandler.removeMessages(TIMER);
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_realtimebusshowbusinfo);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        lv_busandsiteinfo = findViewById(R.id.lv_busandsiteinfo);
        frame_changeDirection = findViewById(R.id.frame_changeDirection);
        frame_refresh = findViewById(R.id.frame_refresh);
        tv_routeName = findViewById(R.id.tv_routeName);
        tv_way = findViewById(R.id.tv_way);
        tv_time = findViewById(R.id.tv_time);
        tv_price = findViewById(R.id.tv_price);
        adapter = new RouteStationAdapter(this, stationList, stationId);
        lv_busandsiteinfo.setAdapter(adapter);
        frame_changeDirection.setOnClickListener(this);
        frame_refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.frame_changeDirection:
                if (segmentList.size() > 1) {
                    showProgress();
                    isChangeDir = !isChangeDir;
                    routeStationInfoAction.sendAction(routeId);
                } else {
                    ToastUtils.showToast(ActivityRealTimeBusShowBusInfo.this, "环线暂不能换向");
                }
                break;
            case R.id.frame_refresh:
                showProgress();
                realTimeInfoAction.sendAction(routeId, segmentId);
                break;
        }
    }

    @Override
    public void onQuerySuccess(ArrayList<String> getinfo) {
        try {
            String rescode = getinfo.get(0);
            if (rescode.equals(GlobalConfig.RESCODE_SUCCESS)) {
                RouteStationInfoList routeStationInfoList = new RouteStationInfoList();
                routeStationInfoList = (RouteStationInfoList) JsonComomUtils.parseAllInfo(getinfo.get(2), routeStationInfoList);
//              如果数据没有正常返回就重新请求接口
                if (routeStationInfoList != null && routeStationInfoList.getDataList() != null) {
                    setTitle(routeStationInfoList.getDataList().get(0).getRouteName());
                    tv_routeName.setText(routeStationInfoList.getDataList().get(0).getRouteName());
                    segmentList = routeStationInfoList.getDataList().get(0).getSegmentList();
                    if (segmentList != null && segmentList.size() > 1) {
                        if (!isChangeDir) {
                            segmentId = segmentList.get(0).getSegmentID();
                        } else {
                            segmentId = segmentList.get(1).getSegmentID();
                        }
                    } else {
                        segmentId = segmentList.get(0).getSegmentID();
                    }
                    realTimeInfoAction = new BusRealTimeInfoAction(this, this);
                    realTimeInfoAction.sendAction(routeId, segmentId);

                    if (onlyOnce) {
                        onlyOnce = false;
                        mHandler.sendEmptyMessageDelayed(TIMER, 15000);
                    }

                } else {
                    routeStationInfoAction.sendAction(routeId);
                }
            } else {
                checkAllUpadate(rescode, getinfo);
            }
        } catch (Exception e) {
            Log.e("EEE", e.toString());
//            showExceptionAlertDialog();
            showAlertDialog("网络请求异常,请点击重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgress();
                    routeStationInfoAction.sendAction(routeId);
                }
            });
        }
    }

    @Override
    public void onQueryFaild(String resmsg) {
        dissmissProgress();
        ToastUtils.showToast(this, resmsg);
    }

    @Override
    public void onQueryRealTimeSuccess(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode = getinfo.get(0);
            if (rescode.equals(GlobalConfig.RESCODE_SUCCESS)) {
                BusRealTimeInfo busRealTimeInfo = new BusRealTimeInfo();
                busRealTimeInfo = (BusRealTimeInfo) JsonComomUtils.parseAllInfo(getinfo.get(2), busRealTimeInfo);
                stationIdList.clear();
                stationList.clear();
                if (busRealTimeInfo.getBusPosList() != null) {
                    for (BusRealTimeInfo.BusPosListBean busPosListBean : busRealTimeInfo.getBusPosList()) {
                        stationIdList.add(busPosListBean.getStationID());
                    }
                }
                if (!isChangeDir) {
                    tv_time.setText("时间：" + segmentList.get(0).getFirtLastShiftInfo().substring(4));
                    tv_price.setText("票价：" + segmentList.get(0).getRoutePrice() + "元");
                    if (segmentList != null && segmentList.get(0).getStationList() != null)
                        for (RouteStationInfoList.RouteStationInfo.SegmentListBean.StationListBean stationListBean : segmentList.get(0).getStationList()) {
                            stationList.add(new RealTimeBusBean(stationListBean.getStationName(), stationListBean.getStationID(), stationIdList.contains(stationListBean.getStationID()) ? true : false));
                        }
                } else {
                    tv_time.setText("时间：" + segmentList.get(1).getFirtLastShiftInfo().substring(4));
                    tv_price.setText("票价：" + segmentList.get(1).getRoutePrice() + "元");
                    if (segmentList != null && segmentList.get(1).getStationList() != null)
                        for (RouteStationInfoList.RouteStationInfo.SegmentListBean.StationListBean stationListBean : segmentList.get(1).getStationList()) {
                            stationList.add(new RealTimeBusBean(stationListBean.getStationName(), stationListBean.getStationID(), stationIdList.contains(stationListBean.getStationID()) ? true : false));
                        }
                }
                String staStartName = stationList.get(0).getSite();
                String staEndName = stationList.get(stationList.size() - 1).getSite();
                if (staStartName.contains("上行")) {
                    staStartName = staStartName.replace("上行", "起点");
                } else if (staStartName.contains("下行")) {
                    staStartName = staStartName.replace("下行", "起点");
                }
                if (staStartName.contains("上")) {
                    staStartName = staStartName.replace("上", "起点");
                } else if (staStartName.contains("下")) {
                    staStartName = staStartName.replace("下", "起点");
                }

                if (staEndName.contains("上行")) {
                    staEndName = staEndName.replace("上行", "终点");
                } else if (staEndName.contains("下行")) {
                    staEndName = staEndName.replace("下行", "终点");
                }
                if (staEndName.contains("上")) {
                    staEndName = staEndName.replace("上", "终点");
                } else if (staEndName.contains("下")) {
                    staEndName = staEndName.replace("下", "终点");
                }
                tv_way.setText(staStartName + " — " + staEndName);
                adapter.notifyDataSetChanged();
            } else {
                checkAllUpadate(rescode, getinfo);
            }
        } catch (Exception e) {
            Log.e("EEE", e.toString());
//            showExceptionAlertDialog();
            showAlertDialog("网络请求异常,请点击重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgress();
                    routeStationInfoAction.sendAction(routeId);
                }
            });
        }
    }

    @Override
    public void onQueryRealTimeFaild(String resmsg) {
        dissmissProgress();
        ToastUtils.showToast(this, resmsg);
    }

    public void showMap(View view) {
//        transAty(BusRouteMapActivity.class);
        BusRouteMapActivity.actionStart(this, routeId);
    }
}
