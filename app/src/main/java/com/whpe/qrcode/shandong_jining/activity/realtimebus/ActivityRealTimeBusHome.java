package com.whpe.qrcode.shandong_jining.activity.realtimebus;

import android.view.View;
import android.widget.Button;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.parent.BackgroundTitleActivity;
import com.whpe.qrcode.shandong_jining.parent.CustomNormalTitleActivity;

/**
 * Created by yang on 2019/3/22.
 */

public class ActivityRealTimeBusHome extends BackgroundTitleActivity implements View.OnClickListener {
    private Button btn_tosearch;

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
        setContentView(R.layout.activity_realtimebushome);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setMyTitleColor(R.color.transparency);
        setTitle(getString(R.string.realtimebushome_title));
        btn_tosearch.setOnClickListener(this);
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        btn_tosearch=(Button)findViewById(R.id.btn_tosearch);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_tosearch){
            transAty(ActivityRealTimeBusSearch.class);
        }
    }
}
