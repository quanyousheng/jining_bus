package com.whpe.qrcode.shandong_jining.net.action;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonObject;
import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.tomyang.whpe.qrcode.bean.request.TransparentRequestBody;
import com.whpe.qrcode.shandong_jining.bigtools.GlobalConfig;
import com.whpe.qrcode.shandong_jining.net.JsonComomUtils;
import com.whpe.qrcode.shandong_jining.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.shandong_jining.parent.ParentActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NearbyStatInfoAction {
    public Inter_NearbyStatInfo inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean = new LoadQrcodeParamBean();

    public NearbyStatInfoAction(Activity context, Inter_NearbyStatInfo inter_near) {
        this.inter = inter_near;
        activity = context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity) activity).sharePreferenceParam.getParamInfos(), loadQrcodeParamBean);
    }

    public void sendAction(double longitude, double latitude) {
        final Head head = new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity) activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setCmdType("passThroughFree");
        head.setCityQrParamVersion(loadQrcodeParamBean.getCityQrParamConfig().getParamVersion());
        final TransparentRequestBody transparentRequestBody = new TransparentRequestBody();
        transparentRequestBody.setBusinessType("Query_NearbyStatInfo");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Longitude", longitude);
        jsonObject.addProperty("Latitude", latitude);
        jsonObject.addProperty("Range", 0);
        transparentRequestBody.setParam(jsonObject);
        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).transparentWithoutLogin(head, transparentRequestBody).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String info) {
                        final String getinfo = info;
                        Log.e("YC", "附近站点=" + info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos = JsonComomUtils.parseJson(getinfo);
                                inter.onQuerySuccess(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onQueryFaild(e.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        }).start();


    }

    public interface Inter_NearbyStatInfo {
        public void onQuerySuccess(ArrayList<String> getinfo);

        public void onQueryFaild(String resmsg);
    }
}
