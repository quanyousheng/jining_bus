package com.whpe.qrcode.shandong_jining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.bigtools.GlobalConfig;
import com.whpe.qrcode.shandong_jining.bigtools.MyDrawableUtils;
import com.whpe.qrcode.shandong_jining.bigtools.ParamGSONUtils;
import com.whpe.qrcode.shandong_jining.fragment.FrgHome;
import com.whpe.qrcode.shandong_jining.fragment.FrgMyself;
import com.whpe.qrcode.shandong_jining.net.JsonComomUtils;
import com.whpe.qrcode.shandong_jining.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.shandong_jining.parent.BackgroundTitleActivity;
import com.whpe.qrcode.shandong_jining.parent.CustomNormalTitleActivity;
import com.whpe.qrcode.shandong_jining.parent.ParentActivity;


/**
 * Created by yang on 2018/9/30.
 */

public class ActivityMain extends CustomNormalTitleActivity implements View.OnClickListener {
    private FrgHome frgHome;
    private FrgMyself frgMy;
    private ImageView iv_qrcode, tv_home, tv_my;
    private LoadQrcodeParamBean loadQrcodeParamBean = new LoadQrcodeParamBean();

    @Override
    protected void afterLayout() {
        Log.e("YC", "PAEAM=" + ParamGSONUtils.beanToString(loadQrcodeParamBean));
        Log.e("YC", "param=" + sharePreferenceParam.getParamInfos());
    }

    @Override
    protected void beforeLayout() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(sharePreferenceParam.getParamInfos(), loadQrcodeParamBean);
    }

    @Override
    protected void setActivityLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onCreateInitView() {
        setMyTitleColor(R.color.comon_text_black_less);
        showHome();
        tv_home.setOnClickListener(this);
        tv_my.setOnClickListener(this);
        iv_qrcode.setOnClickListener(this);
    }

    @Override
    protected void onCreatebindView() {
        tv_home = findViewById(R.id.tv_home);
        tv_my = findViewById(R.id.tv_my);
        iv_qrcode = findViewById(R.id.iv_qrcode);
    }

    private void showHome() {
        tv_home.setImageResource(R.drawable.home_p);
        tv_my.setImageResource(R.drawable.help_n);
        /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgHome = new FrgHome();
        ft.replace(R.id.frame_content, frgHome);
        ft.commitAllowingStateLoss();*/

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (frgHome == null) {
            frgHome = new FrgHome();
            ft.add(R.id.frame_content, frgHome);
        }
        if (frgMy != null) {
            ft.hide(frgMy);
        }
        ft.show(frgHome);
        ft.commitAllowingStateLoss();

    }

    private void showMy() {
        tv_home.setImageResource(R.drawable.home_n);
        tv_my.setImageResource(R.drawable.help_p);

        /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgMy = new FrgMyself();
        ft.replace(R.id.frame_content, frgMy);
        ft.commitAllowingStateLoss();*/

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (frgMy == null) {
            frgMy = new FrgMyself();
            ft.add(R.id.frame_content, frgMy);
        }
        if (frgHome != null) {
            ft.hide(frgHome);
        }
        ft.show(frgMy);
        ft.commitAllowingStateLoss();

    }

    public void showQRCode() {
        if (sharePreferenceLogin.getLoginStatus()) {
            transAty(ActivityQrcode.class);
        } else {
            transAty(ActivityLogin.class);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_home) {
            showHome();
        } else if (id == R.id.tv_my) {
//            showMy();
            Bundle bundle = new Bundle();
            bundle.putString(GlobalConfig.TITLEWEBVIEW_WEBURL, GlobalConfig.USEHELP_URL);
            bundle.putString(GlobalConfig.TITLEWEBVIEW_WEBTITLE, getString(R.string.usehelp_title));
            transAty(ActivityTitleWeb.class, bundle);
        } else if (id == R.id.iv_qrcode) {
            showQRCode();
        }
    }
}
