package com.example.a3droplets.beijingnewsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.a3droplets.beijingnewsdemo.R;
import com.example.a3droplets.beijingnewsdemo.base.CacheUtils;
import com.example.a3droplets.beijingnewsdemo.base.DensityUtils;

import java.util.ArrayList;

/**
 * Created by Mickey_Ma on 2018/12/28.
 * Description: 导航类
 */

public class GuideActivity extends Activity {

    private ViewPager viewpager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews;
    private View iv_red_point;
    private int leftmax; //两点的间距
    private int widthdpi;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏
        setContentView(R.layout.activity_guide);

        //初始化视图
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        btn_start_main = (Button)findViewById(R.id.btn_start_main);
        ll_point_group = (LinearLayout)findViewById(R.id.ll_group_point);
        iv_red_point = (ImageView)findViewById(R.id.iv_red_point);
        //准备数据
        int[] ids = new int[]{
                R.mipmap.guide_1,
                R.mipmap.guide_2,
                R.mipmap.guide_3
        };

        widthdpi = DensityUtils.dip2px(this,10);//设置图像大小

        imageViews = new ArrayList<ImageView>();
        for (int i=0; i<ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);//设置背景
            imageViews.add(imageView);//添加到图片集合中去

            //创建点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            /**
             * 单位是像素
             * 把单位当成dp转成对应的像数
             */
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthdpi,35);
            if (i != 0){
                //不包括第0个，所有的点距离左边有10个像数
                params.leftMargin = widthdpi;
            }
            point.setLayoutParams(params);
            //添加到线性布局里面
            ll_point_group.addView(point);
        }
        //设置ViewPager的适配器
        viewpager.setAdapter(new MyPagerAdapter());

        //根据View的生命周期，当视图执行到onLayout或者onDraw的时候，视图的宽和高、边距都有了
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        //得到屏幕滑动的百分比
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        btn_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.保存参数，记录已经进入过引导页面，下次就不进
                CacheUtils.putBoolean(GuideActivity.this,SplashActivity.START_MAIN,true);

                //2.进入主页面
                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                //3.关闭引导界面
                finish();
            }
        });
    }

    class MyPagerAdapter extends PagerAdapter {
        /**
         * 返回数据的总个数*/
        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         * 作用：getView
         * @param container ViewPager
         * @param position 要创建页面的位置
         * @return 返回和创建当前页面有关系的值
         * */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            //添加倒容器中去
            container.addView(imageView);
            //    return position;
            return imageView;
            //  return super.instantiateItem(container, position);
        }
        /**
         * 判断
         * @param view 当前创建的视图
         * @param o 上面instantiateItem返回的结果值
         * @return
         * */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            //  return view == imageViews.get(Integer.parseInt((String)o));
            return view == o;
        }
        /**
         * 销毁页面
         * @param container ViewPager
         * @param position 要销毁的页面位置
         * @param object 要销毁的页面
         * */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            //执行不止一次
            iv_red_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            //间距 = 第一个点距离左边的距离 — 第零个点距离左边的距离
            leftmax = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * @param position             当前滑动页面的位置
         * @param positionOffset       页面滑动的百分比
         * @param positionOffsetPixels 滑动的像数
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //两点间移动的距离 = 屏幕滑动百分比 * 间距
            //int leftmargin = (int) (positionOffset * leftmax);
            //Log.e();

            //两点间滑动距离对应的坐标 = 原来的起始位置 + 两点间移动的位置

            int leftmargin = (int) ((position * leftmax) + (positionOffset * leftmax));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
            params.leftMargin = leftmargin;
            iv_red_point.setLayoutParams(params);

        }

        /**
         * 当页面被选中的时候，回调这个方法
         *
         * @param position 被选中页面的对应位置
         */
        @Override
        public void onPageSelected(int position) {

            if (position == imageViews.size() - 1) {
                //最后一个页面
                btn_start_main.setVisibility(View.VISIBLE);
            } else {
                //隐藏按钮
                btn_start_main.setVisibility(View.GONE);
            }
        }

        /**
         * 当ViewPager页面滑动状态发生变化的时候
         *
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
