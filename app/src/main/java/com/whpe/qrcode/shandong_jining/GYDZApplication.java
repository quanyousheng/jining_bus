package com.whpe.qrcode.shandong_jining;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;

import java.util.LinkedList;

/**
 * Created by yang on 2018/10/3.
 */

public class GYDZApplication extends Application {
    private LinkedList<Activity> mListAty = new LinkedList<>();
    private static GYDZApplication mIntanse;

    @Override
    public void onCreate() {
        super.onCreate();
        mIntanse = this;
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }


    public static GYDZApplication getInstance() {
        return mIntanse;
    }

    public void addAty(Activity aty) {
        mListAty.add(aty);
    }

    public void clearAllAty() {
        try {
            for (Activity aty : mListAty) {
                if (null != aty) {
                    aty.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
