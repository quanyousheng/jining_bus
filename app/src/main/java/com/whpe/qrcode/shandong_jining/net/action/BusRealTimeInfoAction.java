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

public class BusRealTimeInfoAction {
    public Inter_RealTimeInfo inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean = new LoadQrcodeParamBean();

    public BusRealTimeInfoAction(Activity context, Inter_RealTimeInfo inter) {
        this.inter = inter;
        activity = context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity) activity).sharePreferenceParam.getParamInfos(), loadQrcodeParamBean);
    }

    public void sendAction(int routeID,int segmentid) {
        final Head head = new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity) activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setCmdType("passThroughFree");
        head.setCityQrParamVersion(loadQrcodeParamBean.getCityQrParamConfig().getParamVersion());
        final TransparentRequestBody transparentRequestBody = new TransparentRequestBody();
        transparentRequestBody.setBusinessType("QueryDetail_ByRouteID");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("RouteID", routeID);
        jsonObject.addProperty("Segmentid", segmentid);
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
                        Log.e("YC", "按照线路ID查询线路上的运行情况=" + info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos = JsonComomUtils.parseJson(getinfo);
                                inter.onQueryRealTimeSuccess(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onQueryRealTimeFaild(e.getMessage());
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

    public interface Inter_RealTimeInfo {
        public void onQueryRealTimeSuccess(ArrayList<String> getinfo);

        public void onQueryRealTimeFaild(String resmsg);
    }
}
