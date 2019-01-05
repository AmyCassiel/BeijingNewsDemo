package com.example.a3droplets.beijingnewsdemo.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a3droplets.beijingnewsdemo.R;
import com.example.a3droplets.beijingnewsdemo.activity.MainActivity;

/**
 * Created by Mickey_Ma on 2018/12/29.
 * Description:基类或者说公共类
 *  *      HomePager, NewsCenterPager,
 *  *      SmartServicePager, GovaffairPager
 *  *      SettingPager都继承该类
 *  *      在子类重新initData方法，实现子类的视图，并且视图在该方法中和基类的Fragmelayout布局结合在一起
 */
public class BasePager {
    /**
     * @param mmContext 上下文
     */
    public final Context mmContext; //MainActivity
    public ImageButton ib_menu; //点击侧滑
    public TextView tv_title;//显示标题
    public FrameLayout fl_main;//
    public ImageButton ib_swich_list_gird;
    public FrameLayout fl_content; //加载各个子页面
    public View rootView; //视图，代表各个页面的实例

    public BasePager(Context mmContext) {
        this.mmContext = mmContext;
        rootView = initView(); //构造方法——执行，视图就被初始化了
    }

    /**
     * 用于初始化公共部分视图，并初始化加载子视图的FragmentLayout
     * @return
     */
    private View initView() {
        View view = View.inflate(mmContext, R.layout.basepager,null);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        fl_main = (FrameLayout) view.findViewById(R.id.fl_main);
        ib_swich_list_gird = (ImageButton) view.findViewById(R.id.ib_swich_list_gird);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) mmContext;
                mainActivity.getSlidingMenu().toggle();//关<->开
            }
        });
        return view;
    }

    /**
     * 1.在子类重新initData方法，实现子类的视图，并且视图在该方法中和基类的Fragmelayout布局结合在一起
     * 2.绑定数据或者请求数据再绑定数据
     */
    public void initData() {
    }
}
