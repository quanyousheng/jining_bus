package com.whpe.qrcode.shandong_jining;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.whpe.qrcode.shandong_jining.bigtools.BDLocation.LocationService;

import java.util.LinkedList;

/**
 * Created by yang on 2018/10/3.
 */

public class GYDZApplication extends Application {
    private LinkedList<Activity> mListAty = new LinkedList<>();
    private static GYDZApplication mIntanse;
    public LocationService locationService;

    @Override
    public void onCreate() {
        super.onCreate();
        mIntanse = this;
        locationService = new LocationService(getApplicationContext());
    }


    public static GYDZApplication getInstance(){
        return mIntanse;
    }

    public void addAty(Activity aty){
        mListAty.add(aty);
    }

    public void clearAllAty(){
        try {
            for(Activity aty : mListAty){
                if(null != aty){
                    aty.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
