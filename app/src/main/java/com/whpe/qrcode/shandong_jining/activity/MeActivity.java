package com.whpe.qrcode.shandong_jining.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.parent.ParentActivity;

/**
 * 个人中心
 */

public class MeActivity extends ParentActivity {

    @Override
    protected void afterLayout() {

    }

    @Override
    protected void beforeLayout() {

    }

    @Override
    protected void setActivityLayout() {
        setContentView(R.layout.activity_me);
    }

    @Override
    protected void onCreateInitView() {

    }

    @Override
    protected void onCreatebindView() {

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MeActivity.class);
        context.startActivity(intent);
    }
}
