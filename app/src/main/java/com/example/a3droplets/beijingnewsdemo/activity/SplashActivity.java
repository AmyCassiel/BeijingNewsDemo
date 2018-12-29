package com.example.a3droplets.beijingnewsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;
import com.example.a3droplets.beijingnewsdemo.base.CacheUtils;
import com.example.a3droplets.beijingnewsdemo.R;

/**
 * Created by Mickey_Ma on 2018/12/28.
 * Description:开启动画类
 */
public class SplashActivity extends Activity {
    private RelativeLayout rl_splash_root;
    public static final String START_MAIN = "start_main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏
        setContentView(R.layout.activity_splash);

        rl_splash_root = findViewById(R.id.rl_splash_root);

        //设置渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(false);//如果每个动画都应该使用自己的插值器，则传递false
        animationSet.addAnimation(alphaAnimation);//加载动画
        alphaAnimation.setDuration(300);
        rl_splash_root.startAnimation(animationSet);
        //设置动画监听
        animationSet.setAnimationListener(new SplashActivity.MyAnimationListener());
    }

    private class MyAnimationListener implements Animation.AnimationListener {

        /**
         * 当动画开始播放时回调这个方法
         * @param animation
         */
        @Override
        public void onAnimationStart(Animation animation) {

        }

        /**
         * 当动画播放结束时回调这个方法
         * @param animation
         */
        @Override
        public void onAnimationEnd(Animation animation) {

            //判断是否进入过主界面
            boolean isStartMain = CacheUtils.getBoolean(SplashActivity.this,START_MAIN);
            Intent intent;
            if (isStartMain){//判断是否进入过主界面
                //进入主界面
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }else {
                //进入导航界面
                intent = new Intent(SplashActivity.this, GuideActivity.class);
            }
            startActivity(intent);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
