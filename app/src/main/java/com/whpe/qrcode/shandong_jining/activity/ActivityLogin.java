package com.whpe.qrcode.shandong_jining.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.bigtools.GlobalConfig;
import com.whpe.qrcode.shandong_jining.bigtools.IDUtils;
import com.whpe.qrcode.shandong_jining.bigtools.ToastUtils;
import com.whpe.qrcode.shandong_jining.net.JsonComomUtils;
import com.whpe.qrcode.shandong_jining.net.action.GetSmsAction;
import com.whpe.qrcode.shandong_jining.net.action.LoginAction;
import com.whpe.qrcode.shandong_jining.net.getbean.LoginBean;
import com.whpe.qrcode.shandong_jining.parent.ParentActivity;
import com.whpe.qrcode.shandong_jining.view.CountDownTimerUtils;
import com.whpe.qrcode.shandong_jining.view.listener.LoginPhoneEditextChangeListner;

import java.util.ArrayList;

/**
 * Created by yang on 2018/10/4.
 */

public class ActivityLogin extends ParentActivity implements View.OnClickListener, GetSmsAction.Inter_querysms, LoginAction.Inter_login {

    private Button btn_send;
    private EditText et_phone;
    private EditText et_verify;
    private ImageView iv_delete_phone;
    private Button btn_login;
    private String st_phone;

    @Override
    protected void afterLayout() {

    }

    @Override
    protected void beforeLayout() {

    }

    @Override
    protected void setActivityLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onCreateInitView() {
        et_phone.addTextChangedListener(new LoginPhoneEditextChangeListner(this,btn_login,iv_delete_phone));
        iv_delete_phone.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    @Override
    protected void onCreatebindView() {
        btn_send = findViewById(R.id.btn_send);
        et_phone = (EditText)findViewById(R.id.et_phone);
        et_verify = (EditText)findViewById(R.id.et_verify);
        iv_delete_phone = (ImageView)findViewById(R.id.iv_delete_phone);
        btn_login = (Button)findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        st_phone = et_phone.getText().toString();
        String st_vertify= et_verify.getText().toString();
        if(id==R.id.iv_delete_phone){
            et_phone.setText("");
        }else if(id==R.id.btn_login){
            if(TextUtils.isEmpty(st_phone)||TextUtils.isEmpty(st_vertify)){
                ToastUtils.showToast(this,getString(R.string.login_promt_notinputinfo));
                return;
            }
            if(!IDUtils.isMobileNO(st_phone)){
                ToastUtils.showToast(this,getString(R.string.login_promt_phoneform_error));
                return;
            }
            if(st_vertify.length()!=6){
                ToastUtils.showToast(this,getString(R.string.login_promt_vertifyform_error));
                return;
            }
            showProgress();
            LoginAction loginAction=new LoginAction(this,this);
            loginAction.sendAction(st_phone,st_vertify);
        }else if(id==R.id.btn_send){
            if(TextUtils.isEmpty(st_phone)){
                ToastUtils.showToast(this,getString(R.string.login_promt_notinputphone));
                return;
            }
            if(!IDUtils.isMobileNO(st_phone)){
                ToastUtils.showToast(this,getString(R.string.login_promt_phoneform_error));
                return;
            }
            CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(btn_send, 60000, 1000);
            countDownTimerUtils.start();
            showProgress();
            GetSmsAction getSmsAction=new GetSmsAction(this,this);
            getSmsAction.sendAction(st_phone);
        }
    }

    @Override
    public void onQuerySmsSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            if(getinfo.get(0).equals(GlobalConfig.RESCODE_SUCCESS)){
                ToastUtils.showToast(this,getString(R.string.login_sendsms_true));
            }else {
                ToastUtils.showToast(this,getinfo.get(1));
                loginCheckAllUpadate(getinfo.get(0),getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onQuerySmsFaild(String resmsg) {
        dissmissProgress();
        ToastUtils.showToast(this,getString(R.string.login_sendsms_false));
    }

    @Override
    public void onLoginSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                LoginBean loginBean=new LoginBean();
                loginBean= (LoginBean) JsonComomUtils.parseAllInfo(getinfo.get(2),loginBean);
                sharePreferenceLogin.saveLoginStatus(true);
                sharePreferenceLogin.saveLoginPhone(st_phone);
                sharePreferenceLogin.saveUid(loginBean.getUid());
                sharePreferenceLogin.saveToken(loginBean.getToken());
                finish();
            }else {
                if(TextUtils.isEmpty(getinfo.get(1))){
                    ToastUtils.showToast(this,getString(R.string.login_login_false));
                }else {
                    ToastUtils.showToast(this,getinfo.get(1));
                    loginCheckAllUpadate(rescode,getinfo);
                }
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onLoginFaild(String resmsg) {
        dissmissProgress();
        ToastUtils.showToast(this,getString(R.string.login_login_false));
    }

}
