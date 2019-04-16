package com.whpe.qrcode.shandong_jining.activity.realtimebus;

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
    private FrameLayout frame_refresh,frame_changeDirection;
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

    @Override
    protected void afterLayout() {
        super.afterLayout();
        showProgress();
        routeStationInfoAction = new RouteStationInfoAction(this, this);
        routeStationInfoAction.sendAction(routeId);
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        setMyTitleColor(R.color.white);
        routeId = getIntent().getExtras().getInt("RouteID");
        stationId=getIntent().getExtras().getString("StationID");
        isChangeDir=getIntent().getExtras().getBoolean("isChangeDir",false);
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
        frame_refresh=findViewById(R.id.frame_refresh);
        tv_routeName = findViewById(R.id.tv_routeName);
        tv_way = findViewById(R.id.tv_way);
        tv_time = findViewById(R.id.tv_time);
        tv_price = findViewById(R.id.tv_price);
        adapter = new RouteStationAdapter(this,stationList,stationId);
        lv_busandsiteinfo.setAdapter(adapter);
        frame_changeDirection.setOnClickListener(this);
        frame_refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.frame_changeDirection:
                showProgress();
                isChangeDir = !isChangeDir;
                routeStationInfoAction.sendAction(routeId);
                break;
            case R.id.frame_refresh:
                showProgress();
                realTimeInfoAction.sendAction(routeId,segmentId);
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
                setTitle(routeStationInfoList.getDataList().get(0).getRouteName());
                tv_routeName.setText(routeStationInfoList.getDataList().get(0).getRouteName());
                segmentList = routeStationInfoList.getDataList().get(0).getSegmentList();
                if (!isChangeDir) {
                    segmentId = segmentList.get(0).getSegmentID();
                } else {
                    segmentId = segmentList.get(1).getSegmentID();
                }
                realTimeInfoAction = new BusRealTimeInfoAction(this, this);
                realTimeInfoAction.sendAction(routeId, segmentId);
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
    public void onQueryRealTimeSuccess(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode = getinfo.get(0);
            if (rescode.equals(GlobalConfig.RESCODE_SUCCESS)) {
                BusRealTimeInfo busRealTimeInfo = new BusRealTimeInfo();
                busRealTimeInfo = (BusRealTimeInfo) JsonComomUtils.parseAllInfo(getinfo.get(2), busRealTimeInfo);
                stationIdList.clear();
                stationList.clear();
                for (BusRealTimeInfo.BusPosListBean busPosListBean : busRealTimeInfo.getBusPosList()) {
                    stationIdList.add(busPosListBean.getStationID());
                }
                if (!isChangeDir) {
                    tv_time.setText("时间：" + segmentList.get(0).getFirtLastShiftInfo().substring(4));
                    tv_price.setText("票价：" + segmentList.get(0).getRoutePrice() + "元");
                    for (RouteStationInfoList.RouteStationInfo.SegmentListBean.StationListBean stationListBean : segmentList.get(0).getStationList()) {
                        stationList.add(new RealTimeBusBean(stationListBean.getStationName(),stationListBean.getStationID(), stationIdList.contains(stationListBean.getStationID()) ? true : false));
                    }
                } else {
                    tv_time.setText("时间：" + segmentList.get(1).getFirtLastShiftInfo().substring(4));
                    tv_price.setText("票价：" + segmentList.get(1).getRoutePrice() + "元");
                    for (RouteStationInfoList.RouteStationInfo.SegmentListBean.StationListBean stationListBean : segmentList.get(1).getStationList()) {
                        stationList.add(new RealTimeBusBean(stationListBean.getStationName(),stationListBean.getStationID(), stationIdList.contains(stationListBean.getStationID()) ? true : false));
                    }
                }
                tv_way.setText(stationList.get(0).getSite() + " — " + stationList.get(stationList.size() - 1).getSite());
                adapter.notifyDataSetChanged();
            } else {
                checkAllUpadate(rescode, getinfo);
            }
        } catch (Exception e) {
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onQueryRealTimeFaild(String resmsg) {
        dissmissProgress();
        ToastUtils.showToast(this, resmsg);
    }
}
