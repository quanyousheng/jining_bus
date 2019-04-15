package com.whpe.qrcode.shandong_jining.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.bigtools.MyDrawableUtils;
import com.whpe.qrcode.shandong_jining.toolbean.TestRealTimeBusBean;

import java.util.ArrayList;

/**
 * Created by yang on 2019/3/26.
 */

public class TestRealTimeBusinfoSiteLvAdapter extends BaseAdapter {
    private ArrayList<TestRealTimeBusBean> realTimeBusBeans;
    //private ArrayList<TestRealTimeBusBean> haveSiteRealTimeBusBeans=new ArrayList<TestRealTimeBusBean>();
    private Context content;

    public TestRealTimeBusinfoSiteLvAdapter(Context content,ArrayList<TestRealTimeBusBean> realTimeBusBeanArrayList) {
        this.content = content;
        this.realTimeBusBeans = realTimeBusBeanArrayList;
    }

    public void setBus(){
        refresh();
    }

    private void refresh() {
        /*for(int i=0;i<realTimeBusBeans.size();i++){
            if(realTimeBusBeans.get(i).isHavesite()){
                haveSiteRealTimeBusBeans.add(realTimeBusBeans.get(i));
            }
        }*/
    }

    @Override
    public int getCount() {
        if(realTimeBusBeans==null){
            return 0;
        }else {
            return realTimeBusBeans.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(content).inflate(R.layout.item_realtimebusshowbusinfo_site,parent,false);
        ImageView iv_sitecircle=(ImageView)view.findViewById(R.id.iv_sitecircle);
        ImageView iv_bus=(ImageView)view.findViewById(R.id.iv_bus);
        TextView tv_site=(TextView)view.findViewById(R.id.tv_site);
        View v_topline=view.findViewById(R.id.v_topline);
        View v_bottomline=view.findViewById(R.id.v_bottomline);
        tv_site.setText(realTimeBusBeans.get(position).getSite());
        if(realTimeBusBeans.get(position).isHavebus()){
            iv_sitecircle.setBackground(MyDrawableUtils.getDrawble(content,R.drawable.aty_realtimebusshowbusinfo_itemsiteget));
            iv_bus.setVisibility(View.VISIBLE);
        }else {
            iv_sitecircle.setBackground(MyDrawableUtils.getDrawble(content,R.drawable.aty_realtimebusshowbusinfo_itemsitenotget));
            iv_bus.setVisibility(View.GONE);
        }
        if(position==0){
            v_topline.setVisibility(View.INVISIBLE);
        }
        if(position==realTimeBusBeans.size()-1){
            v_bottomline.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}
