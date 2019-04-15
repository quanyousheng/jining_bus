package com.whpe.qrcode.shandong_jining.activity.realtimebus;

import android.view.View;
import android.widget.Button;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.parent.BackgroundTitleActivity;
import com.whpe.qrcode.shandong_jining.parent.NormalTitleActivity;

/**
 * Created by yang on 2019/3/22.
 */

public class ActivityRealTimeBusSearch extends NormalTitleActivity implements View.OnClickListener {
    private Button btn_query;

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
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
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        btn_query=(Button)findViewById(R.id.btn_query);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_query){
            transAty(ActivityRealTimeBusShowBusInfo.class);
        }
    }
}
