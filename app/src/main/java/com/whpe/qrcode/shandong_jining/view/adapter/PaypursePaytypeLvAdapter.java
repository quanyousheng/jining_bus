package com.whpe.qrcode.shandong_jining.view.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.bigtools.GlobalConfig;
import com.whpe.qrcode.shandong_jining.bigtools.MyDrawableUtils;
import com.whpe.qrcode.shandong_jining.toolbean.PaytypePrepayBean;

import java.util.ArrayList;

/**
 * Created by yang on 2018/9/21.
 */

public class PaypursePaytypeLvAdapter extends BaseAdapter {
    private Context mycontext;
    //private String[] paytype_selects;
    //private int[] paytype_selects_img;
    private int int_paytype_select = 0;
    private TypedArray typedArray;
    private ArrayList<PaytypePrepayBean> paytypePrepayBeans;

    public PaypursePaytypeLvAdapter(Context context, ArrayList<PaytypePrepayBean> paytypePrepayBeans) {
        mycontext = context;
        //paytype_selects=mycontext.getResources().getStringArray(R.array.paypurse_paytype_select);
        //typedArray=mycontext.getResources().obtainTypedArray(R.array.paypurse_paytype_select_img);
        //paytype_selects_img=new int[typedArray.length()];
        /*for(int i=0;i<typedArray.length();i++){
            paytype_selects_img[i] = typedArray.getResourceId(i, 0);
        }*/
        this.paytypePrepayBeans = paytypePrepayBeans;
    }

    public void setPaytypePosition(int i) {
        int_paytype_select = i;
    }

    public String getPaytypeCode() {
        return paytypePrepayBeans.get(int_paytype_select).getPayWayCode();
    }

    @Override
    public int getCount() {
        return paytypePrepayBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mycontext).inflate(R.layout.item_paypurse_paytype, viewGroup, false);
        }
        PaytypePrepayBean paytypePrepayBean = paytypePrepayBeans.get(i);
        TextView tv_paytype = view.findViewById(R.id.tv_paytype);
        ImageView iv_paytype = view.findViewById(R.id.iv_paytype);
        LinearLayout ll_paytype = view.findViewById(R.id.ll_paytype);
        if (int_paytype_select == i) {
            ll_paytype.setBackground(mycontext.getResources().getDrawable(R.drawable.square_select));
        } else {
            ll_paytype.setBackground(mycontext.getResources().getDrawable(R.drawable.rect_radius_bg));
        }
        tv_paytype.setText(paytypePrepayBean.getPayWayName());
        if (paytypePrepayBean.getPayWayCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYWEICHAT)) {
            iv_paytype.setImageDrawable(MyDrawableUtils.getDrawble(mycontext, R.drawable.weixin));
        } else if (paytypePrepayBean.getPayWayCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYCCB)) {
            iv_paytype.setImageDrawable(MyDrawableUtils.getDrawble(mycontext, R.drawable.jiansheyihang));
        } else if (paytypePrepayBean.getPayWayCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYUNION)) {
            iv_paytype.setImageDrawable(MyDrawableUtils.getDrawble(mycontext, R.drawable.yinlian));
        } else if (paytypePrepayBean.getPayWayCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYALIPAY)) {
            iv_paytype.setImageDrawable(MyDrawableUtils.getDrawble(mycontext, R.drawable.zhifubao));
        }

        return view;
    }
}