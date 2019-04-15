package com.whpe.qrcode.shandong_jining.fragment.cloudrechargecard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.whpe.qrcode.shandong_jining.R;
import com.whpe.qrcode.shandong_jining.parent.ParentActivity;


/**
 * Created by yang on 2018/12/12.
 */

public class FrgCloudRechargeCardSuccess extends Fragment {
    private View content;
    private Context context;
    private ParentActivity activity;
    private Button btn_submit_success;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_cloudrechargecard_success,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content=view;
        context=getContext();
        activity= (ParentActivity) getActivity();
        bindView();
        initView();
    }

    private void bindView() {
        btn_submit_success = (Button) content.findViewById(R.id.btn_submit_success);
    }

    private void initView() {
        btn_submit_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }
}
