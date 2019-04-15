package com.whpe.qrcode.shandong_jining.activity.realtimebus;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.parent.CustomNormalTitleActivity;
import com.whpe.qrcode.shandong_jining.parent.NormalTitleActivity;
import com.whpe.qrcode.shandong_jining.toolbean.TestRealTimeBusBean;
import com.whpe.qrcode.shandong_jining.view.adapter.TestRealTimeBusinfoSiteLvAdapter;

import java.util.ArrayList;

/**
 * Created by yang on 2019/3/22.
 */

public class ActivityRealTimeBusShowBusInfo extends CustomNormalTitleActivity implements View.OnClickListener {
    private Button btn_query;
    private ListView lv_busandsiteinfo;
    private TestRealTimeBusinfoSiteLvAdapter testRealTimeBusinfoSiteLvAdapter;

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        setMyTitleColor(R.color.white);
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_realtimebusshowbusinfo);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle("5路");
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        lv_busandsiteinfo=(ListView)findViewById(R.id.lv_busandsiteinfo);
        ArrayList<TestRealTimeBusBean> testRealTimeBusBeans=new ArrayList<TestRealTimeBusBean>();
        for(int i=0;i<15;i++){
            TestRealTimeBusBean testRealTimeBusBean=new TestRealTimeBusBean();
            testRealTimeBusBean.setSite("公用电子"+i);
            if(i==4||i==8||i==12){
                testRealTimeBusBean.setHavebus(true);
            }else {
                testRealTimeBusBean.setHavebus(false);
            }
            testRealTimeBusBeans.add(testRealTimeBusBean);
        }
        testRealTimeBusinfoSiteLvAdapter = new TestRealTimeBusinfoSiteLvAdapter(this,testRealTimeBusBeans);
        lv_busandsiteinfo.setAdapter(testRealTimeBusinfoSiteLvAdapter);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
    }
}
