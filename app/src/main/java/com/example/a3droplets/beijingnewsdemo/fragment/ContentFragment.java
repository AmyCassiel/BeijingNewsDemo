package com.example.a3droplets.beijingnewsdemo.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.a3droplets.beijingnewsdemo.R;
import com.example.a3droplets.beijingnewsdemo.activity.MainActivity;
import com.example.a3droplets.beijingnewsdemo.base.BaseFragment;
import com.example.a3droplets.beijingnewsdemo.base.BasePager;
import com.example.a3droplets.beijingnewsdemo.pager.HomePager;
import com.example.a3droplets.beijingnewsdemo.pager.SettingPager;
import com.example.a3droplets.beijingnewsdemo.pager.ShoppingsCenterPager;
import com.example.a3droplets.beijingnewsdemo.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Mickey_Ma on 2018/12/29.
 * Description:主界面菜单
 */
public class ContentFragment extends BaseFragment {

    //2.初始化控件
    @ViewInject(R.id.viewpager)
    public NoScrollViewPager viewPager;
    @ViewInject(R.id.rg_main)
    public RadioGroup radioGroup;

    public ArrayList<BasePager> basePagers; //三个页面的集合

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.content_fragment,null);

        //1.把视图注入到框架中，让ContentFragment.this和View关联起来
        x.view().inject(ContentFragment.this,view);

        //把View注入到ButterKnife
//        ButterKnife.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //初始化3个界面
        initPager();

        //设置适配器
        setAdapter();

        //设置RadioGroup状态选中的监听
        initListener();
    }

    private void initListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //先默认设置不可以滑动
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

                switch (checkedId){
                    case R.id.rb_home:
                        viewPager.setCurrentItem(0,false);
                        //切换到新闻的时候，修改成可以滑动
                        mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    case R.id.rb_shopping:
                        //ViewPager,切换到不同页面的办法
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.rb_setting:
                        viewPager.setCurrentItem(2,false);
                        break;
                }
            }
        });
        //设置默认选中首页
        radioGroup.check(R.id.rb_home);

        //监听页面的选中
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        basePagers.get(1).initData();

    }

    private void setAdapter() {
        viewPager.setAdapter(new MyPagerAdapter());
    }

    private void initPager() {
        //初始化三个界面
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(mContext));//主界面
        basePagers.add(new ShoppingsCenterPager(mContext));//购物界面
        basePagers.add(new SettingPager(mContext));//设置界面
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return basePagers.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            BasePager basePager = basePagers.get(position); //HomePager、NewsCenterPager、SettingPager
            View rootView = basePager.rootView; //代表不同页面的实例

            //调用initData
            //basePager.initData();  //孩子的视图和父类的FragmentLayout结合,初始化数据

            container.addView(rootView);

            return rootView;
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

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            //调用initData
            basePagers.get(i).initData(); //孩子的视图和父类的FragmentLayout结合

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    /**
     * 得到新闻中心
     * @return
     */
    public HomePager getNewsCenterPAger(){
        return (HomePager)basePagers.get(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
