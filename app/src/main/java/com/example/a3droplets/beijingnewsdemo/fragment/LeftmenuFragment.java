package com.example.a3droplets.beijingnewsdemo.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.a3droplets.beijingnewsdemo.base.BaseFragment;

/**
 * Created by Mickey_Ma on 2018/12/29.
 * Description:左侧菜单
 */
public class LeftmenuFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextSize(23);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);

        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("左侧Fragment的页面");
    }
}
