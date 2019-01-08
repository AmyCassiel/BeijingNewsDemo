package com.example.a3droplets.beijingnewsdemo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import com.example.a3droplets.beijingnewsdemo.R;
import com.example.a3droplets.beijingnewsdemo.fragment.ContentFragment;
import com.example.a3droplets.beijingnewsdemo.fragment.LeftmenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by Mickey_Ma on 2018/12/28.
 * Description: 主界面
 */
public class MainActivity extends SlidingFragmentActivity {

    public static final String LEFTMENU_TAG = "leftmenu_tag";
    public static final String MAIN_CONTENT_TAG = "conent_tag";

    int screenWidth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics  out = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(out);
        screenWidth = out.widthPixels;

        initSlidingMenu();
        initFragment();
    }

    //初始化Fragment
    private void initFragment() {
        //1.得到事务
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //2.替换：左侧菜单和主菜单
        fragmentTransaction.replace(R.id.fl_leftmenu, new LeftmenuFragment(),LEFTMENU_TAG);
        fragmentTransaction.replace(R.id.fl_content,new ContentFragment(),MAIN_CONTENT_TAG);
        //3.提交
        fragmentTransaction.commit();

//        getSupportFragmentManager().beginTransaction().replace(R.id.fl_leftmenu,new LeftMenuFragment(), LEFTMENU_TAG).replace(R.id.fl_content,new ContentFragment(),  CONENT_TAG).commit();

    }

    private void initSlidingMenu() {
        //1.设置主页面
        setContentView(R.layout.activity_main);

        //2.设置左侧菜单
        setBehindContentView(R.layout.activity_leftmenu);

        //3.设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();

        //4.设置模式：左侧+主页；左侧+主页+右侧；主页+右侧
        slidingMenu.setMode(SlidingMenu.LEFT);

        //5.设置滑动模式：全屏，边缘，不可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //6.设置主页面占的宽度dip
        slidingMenu.setBehindOffset((int) (screenWidth*0.625));

    }

    /**
     * 得到正文fragment
     * @return
     */
    public ContentFragment getContentFragment(){
        return (ContentFragment)getSupportFragmentManager().findFragmentByTag(MAIN_CONTENT_TAG);
    }
    /**
     * 得到左侧菜单fragment
     * @return
     */
    public LeftmenuFragment getLeftMenuFragment() {
        //        FragmentManager fragmentManager = getSupportFragmentManager();
//        LeftmenuFragment  LF = (LeftmenuFragment) fragmentManager.findFragmentById(Integer.parseInt(LEFTMENU_TAG));


        return (LeftmenuFragment) getSupportFragmentManager().findFragmentById(Integer.parseInt(LEFTMENU_TAG));
    }
}
