package com.example.a3droplets.beijingnewsdemo.detailpager;

import android.content.Context;
import android.view.View;

import com.example.a3droplets.beijingnewsdemo.activity.MainActivity;
import com.example.a3droplets.beijingnewsdemo.base.MenuDetailBasePager;
import com.example.a3droplets.beijingnewsdemo.bean.HomeCenterBean;

/**
 * Created by Mickey_Ma on 2019/1/7.
 * Description:
 */
public class HomeMenuDetailPager extends MenuDetailBasePager {
    public HomeMenuDetailPager(Context context, HomeCenterBean.DataBean dataBean) {
        super(context);
    }

    public HomeMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        return null;
    }
}
