package com.whpe.qrcode.shandong_jining.activity;


import android.content.Context;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.parent.NormalTitleActivity;
import com.whpe.qrcode.shandong_jining.view.ProgressWebView;

/**
 * Created by yang on 2018/10/3.
 */

public class ActivityUsehelp extends NormalTitleActivity {
    private ProgressWebView wv;

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
        setContentView(R.layout.activity_usehelp);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
//        setTitle(getString(R.string.usehelp_title));
        initWebview();

    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        wv = findViewById(R.id.wv);
    }

    private void initWebview() {
        WebSettings settings = wv.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");//设置网页默认编码
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);          //允许缩放
        settings.setBuiltInZoomControls(true);  //原网页基础上缩放
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);      //任意比例缩放


        settings.setAppCacheEnabled(true);
        String appCaceDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCaceDir);
        // 格式规定为:file:////android_asset/文件名.html
        wv.loadUrl("file:////android_asset/rule.html");
        wv.setWebViewClient(new WebViewClient() {
            //2017 10-19webview无法读取页面由于https因此修改
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }

        });
    }
}
