package com.example.a3droplets.beijingnewsdemo.detailpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3droplets.beijingnewsdemo.R;
import com.example.a3droplets.beijingnewsdemo.activity.MainActivity;
import com.example.a3droplets.beijingnewsdemo.base.MenuDetailBasePager;
import com.example.a3droplets.beijingnewsdemo.bean.HomeCenterBean;
import com.example.a3droplets.beijingnewsdemo.bean.HomeCenterPagerBean;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mickey_Ma on 2019/1/7.
 * Description:
 */
public class HomeMenuDetailPager extends MenuDetailBasePager {

    @ViewInject(R.id.indicator)
    private TabPageIndicator tabPageIndicator;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    /**
     * 新闻详情页面的数据集合
     */
    private List<HomeCenterBean.DataBean.ChildrenBean> children;
    /**
     * 页签页面的集合
     */
    private ArrayList<TabDetailPager> tabDetailPagers;

    public HomeMenuDetailPager(Context context, HomeCenterBean.DataBean detailPagerData) {
        super(context);
        this.children = detailPagerData.getChildren();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.homemenu_detail_pager, null);
        x.view().inject(HomeMenuDetailPager.this, view);
        return view;
    }


    @Override
    public void initData() {
        super.initData();

        //准备新闻详情页面的数据
        tabDetailPagers = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            tabDetailPagers.add(new TabDetailPager(mContext, children.get(i)));

        }
        //设置ViewPager的适配器
        viewPager.setAdapter(new MyHomeMenuDetailPagerAdapter());
        //ViewPager和TabPagerIndicator关联
        tabPageIndicator.setViewPager(viewPager);
        //监听页面的变化用TabPagerIndicator
        tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListenter());

    }


    private class MyHomeMenuDetailPagerAdapter extends PagerAdapter {

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TabDetailPager tabDetailPager = tabDetailPagers.get(position);
            View view = tabDetailPager.rootView;
            tabDetailPager.initData();
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    private class MyOnPageChangeListenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            MainActivity mainActivity = (MainActivity) mContext;
            if (position == 0){
                //可以滑动
                mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else {
                //其他页面不能滑动
                mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
