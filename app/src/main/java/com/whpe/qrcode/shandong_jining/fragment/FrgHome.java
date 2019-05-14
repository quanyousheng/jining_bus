package com.whpe.qrcode.shandong_jining.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;
import com.tomyang.whpe.qrcode.bean.ack.QueryNewsListAckBody;
import com.tomyang.whpe.qrcode.bean.ack.QueryNewsListItem;
import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.activity.ActivityCloudRechargeCard;
import com.whpe.qrcode.shandong_jining.activity.ActivityLogin;
import com.whpe.qrcode.shandong_jining.activity.ActivityMain;
import com.whpe.qrcode.shandong_jining.activity.ActivityMypurse;
import com.whpe.qrcode.shandong_jining.activity.ActivityNewsAndAdvertiseWeb;
import com.whpe.qrcode.shandong_jining.activity.ActivityTitleWeb;
import com.whpe.qrcode.shandong_jining.activity.MeActivity;
import com.whpe.qrcode.shandong_jining.activity.realtimebus.ActivityRealTimeBusHome;
import com.whpe.qrcode.shandong_jining.bigtools.GlobalConfig;
import com.whpe.qrcode.shandong_jining.bigtools.ToastUtils;
import com.whpe.qrcode.shandong_jining.net.action.ShowNewsContentListAction;
import com.whpe.qrcode.shandong_jining.net.action.ShowTopCardContentListAction;
import com.whpe.qrcode.shandong_jining.parent.ParentActivity;
import com.whpe.qrcode.shandong_jining.toolbean.TrueNewsBean;
import com.whpe.qrcode.shandong_jining.view.adapter.FakeNewsRlAdapter;
import com.whpe.qrcode.shandong_jining.view.adapter.GridAdapter;
import com.whpe.qrcode.shandong_jining.view.adapter.HomeTopPagerAdapter;
import com.whpe.qrcode.shandong_jining.view.adapter.TrueNewsRlAdapter;
import com.whpe.qrcode.shandong_jining.view.adapter.holder.TrueNewsRlHolder;

import java.util.ArrayList;


/**
 * Created by yang on 2018/9/30.
 */

public class FrgHome extends Fragment implements View.OnClickListener, ShowNewsContentListAction.Inter_ShowNewsContentList {
    private View content;
    private Context context;
    private ParentActivity activity;
    private RecyclerView rl_news;
    private FakeNewsRlAdapter fakeNewsRlAdapter;
    private TrueNewsRlAdapter trueNewsRlAdapter;
    private ArrayList<TrueNewsBean> trueNewsBeans = new ArrayList<TrueNewsBean>();
    private SwipeRefreshLayout srl_refresh;
    private GridView gridView;
    private ImageView iv_qrcode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.frg_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content = view;
        context = getContext();
        activity = (ParentActivity) getActivity();
        bindView();
        initView();
        manageNewsAndTopCard();
    }

    private void manageNewsAndTopCard() {
        if (activity.isNetworkAvailable(activity)) {
            requestForNewsList();
        } else {
            ToastUtils.showToast(activity, getString(R.string.app_notnet));
        }
    }

    public void requestForNewsList() {
        ShowNewsContentListAction showNewsContentListAction = new ShowNewsContentListAction(activity, this);
        String phone = "";
        if (((ActivityMain) activity).sharePreferenceLogin.getLoginStatus()) {
            phone = ((ActivityMain) activity).sharePreferenceLogin.getLoginPhone();
        }
        showNewsContentListAction.sendAction(GlobalConfig.NEWSANDADVERLIST_PAGECHOOSE_HOMEPAGE, phone, GlobalConfig.NEWSANDADVERLIST_SPACEID_2);
    }

    private void initView() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(context, getString(R.string.app_function_notopen));
            }
        };
        gridView.setAdapter(new GridAdapter(getActivity()));
        initTitle();
        initNews();
        initRefresh();
    }

    private void initRefresh() {
        srl_refresh.setFocusable(true);
        srl_refresh.setFocusableInTouchMode(true);

        srl_refresh.requestFocus();
        srl_refresh.setColorSchemeResources(R.color.app_theme);
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl_refresh.setRefreshing(false);
                manageNewsAndTopCard();
            }
        });
    }

    private void initNews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        //设置布局管理器
        rl_news.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        rl_news.setNestedScrollingEnabled(false);
        //设置Adapter
        trueNewsRlAdapter = new TrueNewsRlAdapter(activity);
        trueNewsRlAdapter.setHasStableIds(true);
        trueNewsRlAdapter.setNewsList(trueNewsBeans);
        rl_news.setAdapter(trueNewsRlAdapter);
        trueNewsRlAdapter.setItemClickListener(new TrueNewsRlHolder.MyItemClickListener() {

            @Override
            public void onItemClick(View view, int postion) {
                Bundle bundle = new Bundle();
                bundle.putString(GlobalConfig.NEWSANDADVER_INTENT_CONTENTID, trueNewsBeans.get(postion).getContentid());
                bundle.putString(GlobalConfig.NEWSANDADVER_INTENT_TITLE, GlobalConfig.NEWSANDADVER_NEWS);
                activity.transAty(ActivityNewsAndAdvertiseWeb.class, bundle);
            }
        });

    }

    private void initTitle() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
    }

    private void bindView() {
        rl_news = (RecyclerView) content.findViewById(R.id.rl_news);
        gridView = content.findViewById(R.id.gridView);
        iv_qrcode = content.findViewById(R.id.iv_qrcode);
//        iv_topcard=(ImageView)content.findViewById(R.id.iv_topcard);
//        vp_top = (ViewPager)content.findViewById(R.id.vp_top);
        srl_refresh = (SwipeRefreshLayout) content.findViewById(R.id.srl_refresh);
//        indicator_line = (ViewPagerIndicator)content.findViewById(R.id.indicator_line);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        activity.transAty(ActivityRealTimeBusHome.class);
                        break;
                    case 1:
                        if (activity.sharePreferenceLogin.getLoginStatus()) {
                            activity.transAty(ActivityMypurse.class);
                        } else {
                            activity.transAty(ActivityLogin.class);
                        }
                        break;
                    case 2:
                        if (activity.sharePreferenceLogin.getLoginStatus()) {
                            ((ParentActivity) getActivity()).transAty(ActivityCloudRechargeCard.class);
                        } else {
                            activity.transAty(ActivityLogin.class);
                        }
                        break;
                    case 3:
                        MeActivity.actionStart(getActivity());
                        break;
                    default:
                        break;
                }
            }
        });
        iv_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdded()) {
                    ((ActivityMain) getActivity()).showQRCode();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_qrcode) {

        }

//        if(id==R.id.iv_tabbusmap){
//
//        }else if(id==R.id.iv_tabrecharge){
//            if(activity.sharePreferenceLogin.getLoginStatus()){
//                activity.transAty(ActivityMypurse.class);
//            }else {
//                activity.transAty(ActivityLogin.class);
//            }
//        }else if(id==R.id.iv_tabusehelp){
//            Bundle bundle=new Bundle();
//            bundle.putString(GlobalConfig.TITLEWEBVIEW_WEBURL,GlobalConfig.USEHELP_URL);
//            bundle.putString(GlobalConfig.TITLEWEBVIEW_WEBTITLE,getString(R.string.usehelp_title));
//            ((ParentActivity)getActivity()).transAty(ActivityTitleWeb.class,bundle);
//        }else if(id==R.id.iv_tabcallsus){
//            Intent intent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.calls_phone)));
//            startActivity(intent);
//           //activity.transAty(ActivityRealTimeBusHome.class);
//        }else if(id==R.id.iv_tabsearchstudentcard){
//
//        }else if(id==R.id.iv_tabcloudrechargecard){
//            if(activity.sharePreferenceLogin.getLoginStatus()){
//                ((ParentActivity)getActivity()).transAty(ActivityCloudRechargeCard.class);
//            }else {
//                activity.transAty(ActivityLogin.class);
//            }
//            //ToastUtils.showToast(activity,getString(R.string.activity_qrcode_function_notopen));
//        }else if(id==R.id.iv_realTimeBus){
//            activity.transAty(ActivityRealTimeBusHome.class);
//        }
    }

    @Override
    public void onShowNewsContentListSucces(QueryNewsListAckBody getinfo) {
        trueNewsBeans.clear();
        ArrayList<QueryNewsListItem> queryNewsListItems = getinfo.getContentList();
        for (int i = 0; i < queryNewsListItems.size(); i++) {
            TrueNewsBean trueNewsBean = new TrueNewsBean();
            trueNewsBean.setContentid(queryNewsListItems.get(i).getContentId());
            trueNewsBean.setTitle(queryNewsListItems.get(i).getContentName());
            trueNewsBean.setInfo(queryNewsListItems.get(i).getContentDesc());
            trueNewsBean.setImg(queryNewsListItems.get(i).getContentImage());
            trueNewsBeans.add(trueNewsBean);
            //Log.e("YC","TITLE="+queryNewsListItems.get(i).getContentName()+"----img="+queryNewsListItems.get(i).getContentImage());
        }
        trueNewsRlAdapter.setNewsList(trueNewsBeans);
        trueNewsRlAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowNewsContentListFaild(String resmsg) {

    }

}
