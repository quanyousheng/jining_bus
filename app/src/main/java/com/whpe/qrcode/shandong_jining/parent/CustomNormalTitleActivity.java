package com.whpe.qrcode.shandong_jining.parent;

import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.bigtools.MyDrawableUtils;


/**
 * Created by yang on 2018/9/29.
 */

public class CustomNormalTitleActivity extends ParentActivity {
    private RelativeLayout rl_title;


    @Override
    protected void afterLayout() {

    }

    @Override
    protected void beforeLayout() {
        /*//设置 paddingTop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(R.color.app_theme));
        }*/
    }
    

    @Override
    protected void setActivityLayout() {

    }

    @Override
    protected void onCreateInitView() {

    }

    @Override
    protected void onCreatebindView() {

    }

    protected void setMyTitleColor(int color){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(color));
        }
    }

}
