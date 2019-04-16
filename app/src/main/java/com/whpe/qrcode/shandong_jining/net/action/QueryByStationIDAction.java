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

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class QueryByStationIDAction {
    public Inter_QueryByStationID inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();

    public QueryByStationIDAction(Activity context, Inter_QueryByStationID inter_query) {
        this.inter = inter_query;
        activity=context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity)activity).sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    public void sendAction(String stationID){
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setCmdType("passThroughFree");
        head.setCityQrParamVersion(loadQrcodeParamBean.getCityQrParamConfig().getParamVersion());
        final TransparentRequestBody transparentRequestBody=new TransparentRequestBody();
        transparentRequestBody.setBusinessType("Query_ByStationID");
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("RouteID",-1);
        jsonObject.addProperty("StationID",stationID);
        transparentRequestBody.setParam(jsonObject);
        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).transparentWithoutLogin(head,transparentRequestBody).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String info) {
                        final String getinfo=info;
                        Log.e("YC","附近站点="+info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos= JsonComomUtils.parseJson(getinfo);
                                inter.onQueryByStationIDSuccess(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onQueryByStationIDFaild(e.getMessage());
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

    public interface Inter_QueryByStationID{
        public void onQueryByStationIDSuccess(ArrayList<String> getinfo);
        public void onQueryByStationIDFaild(String resmsg);
    }
}
