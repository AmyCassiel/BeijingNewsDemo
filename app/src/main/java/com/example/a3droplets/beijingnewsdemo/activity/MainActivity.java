package com.example.a3droplets.beijingnewsdemo.activity;

import android.os.Bundle;

import com.example.a3droplets.beijingnewsdemo.R;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by Mickey_Ma on 2018/12/28.
 * Description:
 */
public class MainActivity extends SlidingFragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.设置主页面
        setContentView(R.layout.activity_main);

    }
}
