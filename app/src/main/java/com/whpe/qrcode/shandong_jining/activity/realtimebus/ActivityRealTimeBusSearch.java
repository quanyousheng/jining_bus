package com.whpe.qrcode.shandong_jining.activity.realtimebus;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class ActivityRealTimeBusSearch extends NormalTitleActivity implements View.OnClickListener, RouteStationInfoAction.Inter_RouteStationInfo {
    private Button btn_query;
    private TextView tv_deleteHistory, tv_cancel;
    private EditText et_input;
    private ListView lv_history;
    private SearchRouteAdapter adapter;
    private ArrayList<RouteStationInfoList.RouteStationInfo.SegmentListBean> segmentList = new ArrayList<>();
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
                boolean isChangeDir;
                if (i == 0) {
                    isChangeDir = false;
                } else {
                    isChangeDir = true;
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("isChangeDir", isChangeDir);
                bundle.putInt("RouteID", route);
                transAty(ActivityRealTimeBusShowBusInfo.class, bundle);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_query:
                if (TextUtils.isEmpty(et_input.getText().toString().trim())) {
                    ToastUtils.showToast(this, "请输入线路再查询");
                } else {
                    segmentList.clear();
                    route = Integer.parseInt(et_input.getText().toString().trim());
                    showProgress();
                    RouteStationInfoAction routeStationInfoAction = new RouteStationInfoAction(this, this);
                    routeStationInfoAction.sendAction(route);
                }
                break;
            case R.id.tv_cancel:
                et_input.setText("");
                break;
            case R.id.tv_deleteHistory:
                segmentList.clear();
                adapter.notifyDataSetChanged();
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
                if (routeStationInfoList.getDataList().size() == 0) {
                    ToastUtils.showToast(ActivityRealTimeBusSearch.this, "未查询到该线路信息");
                } else {
                    segmentList.addAll(routeStationInfoList.getDataList().get(0).getSegmentList());
                    adapter = new SearchRouteAdapter(segmentList, nearSta, route);
                    lv_history.setAdapter(adapter);
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

//    @Override
//    public void titleback(View v) {
//        if (segmentList.size() > 0) {
//            ACache.get(this).put("dataList",segmentList);
//        }
//    }
}
