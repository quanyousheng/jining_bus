package com.whpe.qrcode.shandong_jining.activity.realtimebus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.bigtools.ACache;
import com.whpe.qrcode.shandong_jining.bigtools.GlobalConfig;
import com.whpe.qrcode.shandong_jining.bigtools.ToastUtils;
import com.whpe.qrcode.shandong_jining.net.JsonComomUtils;
import com.whpe.qrcode.shandong_jining.net.action.RouteStationInfoAction;
import com.whpe.qrcode.shandong_jining.net.getbean.RouteStationInfoList;
import com.whpe.qrcode.shandong_jining.parent.NormalTitleActivity;
import com.whpe.qrcode.shandong_jining.view.adapter.SearchRouteAdapter;
import com.whpe.qrcode.shandong_jining.view.adapter.SearchRouteAdapter1;

import java.util.ArrayList;
import java.util.List;

public class ActivityRealTimeBusSearch extends NormalTitleActivity implements View.OnClickListener, RouteStationInfoAction.Inter_RouteStationInfo {
    private Button btn_query;
    private TextView tv_deleteHistory, tv_cancel;
    private EditText et_input;
    private ListView lv_history;
    private SearchRouteAdapter adapter;
    private SearchRouteAdapter1 adapter1;//环线使用的Adapter
    private RouteStationInfoAction routeStationInfoAction;
    private ArrayList<RouteStationInfoList.RouteStationInfo.SegmentListBean> segmentList = new ArrayList<>();
    private ArrayList<RouteStationInfoList.RouteStationInfo> segmentList1 = new ArrayList<>();
    private String nearSta;
    private int route;

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        nearSta = getIntent().getExtras().getString("nearSta", "暂无数据");
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_realtimebussearch);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.realtimebussearch_title));
        btn_query.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_deleteHistory.setOnClickListener(this);
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        btn_query = findViewById(R.id.btn_query);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_deleteHistory = findViewById(R.id.tv_deleteHistory);
        tv_cancel = findViewById(R.id.tv_cancel);
        et_input = findViewById(R.id.et_input);
        lv_history = findViewById(R.id.lv_history);

        lv_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                boolean isChangeDir;
                if (segmentList1 != null && segmentList1.size() > 0) {
                    route = segmentList1.get(i).getRouteID();
                } else {
                    if (i == 0) {
                        isChangeDir = false;
                    } else {
                        isChangeDir = true;
                    }
                    bundle.putBoolean("isChangeDir", isChangeDir);
                }
                bundle.putInt("RouteID", route);
                transAty(ActivityRealTimeBusShowBusInfo.class, bundle);
            }
        });
    }

    public void searchRoute() {
        if (TextUtils.isEmpty(et_input.getText().toString().trim())) {
            ToastUtils.showToast(this, "请输入线路再查询");
        } else {
            segmentList.clear();
            segmentList1.clear();
            route = Integer.parseInt(et_input.getText().toString().trim());
            showProgress();
            routeStationInfoAction = new RouteStationInfoAction(this, this);
            switch (route) {
                case 33:
                    route = 331;
                    break;
                case 66:
                    route = 661;
                    break;
                case 77:
                    route = 771;
                    break;
                case 401:
                    route = 4011;
                    break;
            }
            routeStationInfoAction.sendAction(route);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_query:
                searchRoute();
                break;
            case R.id.tv_cancel:
                et_input.setText("");
                break;
            case R.id.tv_deleteHistory:
                if (segmentList.size() > 0) {
                    segmentList.clear();
                    adapter.notifyDataSetChanged();
                }
                if (segmentList1.size() > 0) {
                    segmentList1.clear();
                    adapter1.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onQuerySuccess(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode = getinfo.get(0);
            if (rescode.equals(GlobalConfig.RESCODE_SUCCESS)) {
                RouteStationInfoList routeStationInfoList = new RouteStationInfoList();
                routeStationInfoList = (RouteStationInfoList) JsonComomUtils.parseAllInfo(getinfo.get(2), routeStationInfoList);
//              数据返回正常就显示，否则继续请求接口
                if (routeStationInfoList != null && routeStationInfoList.getDataList() != null) {
                    if (routeStationInfoList.getDataList().size() == 0) {
                        ToastUtils.showToast(ActivityRealTimeBusSearch.this, "未查询到该线路信息");
                    } else {
                        if (routeStationInfoList.getDataList().get(0).getSegmentList().size() > 1) {
                            segmentList.addAll(routeStationInfoList.getDataList().get(0).getSegmentList());
                            adapter = new SearchRouteAdapter(segmentList, nearSta, route);
                            lv_history.setAdapter(adapter);
                        } else {
                            if (segmentList1 != null && segmentList1.size() > 0) {
                                segmentList1.add(routeStationInfoList.getDataList().get(0));
                                adapter1.notifyDataSetChanged();
                            } else {
                                segmentList1.add(routeStationInfoList.getDataList().get(0));
                                adapter1 = new SearchRouteAdapter1(segmentList1, nearSta);
                                lv_history.setAdapter(adapter1);
                                switch (route) {
                                    case 331:
                                        route = 332;
                                        break;
                                    case 661:
                                        route = 662;
                                        break;
                                    case 771:
                                        route = 772;
                                        break;
                                    case 4011:
                                        route = 4012;
                                        break;
                                }
                                routeStationInfoAction.sendAction(route);
                            }
                        }
                    }
                } else {
                    searchRoute();
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
                    searchRoute();
                }
            });
        }
    }

    @Override
    public void onQueryFaild(String resmsg) {
        dissmissProgress();
        ToastUtils.showToast(this, resmsg);
    }

//    @Override
//    public void titleback(View v) {
//        if (segmentList.size() > 0) {
//            ACache.get(this).put("dataList",segmentList);
//        }
//    }
}
