package com.example.a3droplets.beijingnewsdemo.detailpager;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.example.a3droplets.beijingnewsdemo.activity.MainActivity;
import com.example.a3droplets.beijingnewsdemo.base.MenuDetailBasePager;
import com.example.a3droplets.beijingnewsdemo.bean.HomeCenterBean;

/**
 * Created by Mickey_Ma on 2019/1/7.
 * Description:组图详情页面
 */
public class PhotosMenuDetailPager extends MenuDetailBasePager {
    private final HomeCenterBean.DataBean dataBean;

    public PhotosMenuDetailPager(Context context, HomeCenterBean.DataBean dataBean) {
        super(context);
        this.dataBean = dataBean;
    }

    @Override
    public View initView() {
        return null;
    }
    @Override
    public void initData() {
        super.initData();
    }

    public void switchListGrid(ImageButton ib_swich_list_gird) {
    }
}
