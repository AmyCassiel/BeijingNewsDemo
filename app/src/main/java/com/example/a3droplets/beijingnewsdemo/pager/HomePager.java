package com.example.a3droplets.beijingnewsdemo.pager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.a3droplets.beijingnewsdemo.activity.MainActivity;
import com.example.a3droplets.beijingnewsdemo.base.BasePager;
import com.example.a3droplets.beijingnewsdemo.base.CacheUtils;
import com.example.a3droplets.beijingnewsdemo.base.Constants;
import com.example.a3droplets.beijingnewsdemo.base.MenuDetailBasePager;
import com.example.a3droplets.beijingnewsdemo.bean.HomeCenterBean;
import com.example.a3droplets.beijingnewsdemo.fragment.LeftmenuFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mickey_Ma on 2018/12/29.
 * Description:
 */
public class HomePager extends BasePager {

    private ArrayList<MenuDetailBasePager> menuDetailBasePagers;

    /**
     * 左侧菜单对应数据
     */
    private List<HomeCenterBean.DataBean> dataBeanList;
    public HomePager(Context mmContext) {
        super(mmContext);
    }

    @Override
    public void initData() {
        super.initData();
        //显示菜单按钮
        ib_menu.setVisibility(View.VISIBLE);
        //设置标题
        tv_title.setText("主页");

        String saveJson = CacheUtils.getString(mmContext, Constants.NEWSCENTER_PAGER_URL);
        if (!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

//        if(saveJson != null){
//            //能进来
//              processData(saveJson);
//        }

        getDataFromNet();
    }

    /**
     * 联网请求数据
     */
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(mmContext,Constants.NEWSCENTER_PAGER_URL,result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 解析数据
     * 绑定数据
     * @param saveJson
     */
    private void processData(String saveJson) {
        //1.解析数据：手动解析（用系统的Api解析）和第三方解析json的框架（Gson,fastjson）
//        Gson gson = new Gson();
//        NewsCenterBean centerBean = new Gson().fromJson(json, NewsCenterBean.class);
        HomeCenterBean centerBean = paraseJson(json);
        dataBeanList = centerBean.getData();

        Log.e("TAG", "新闻中心解析成功=" + dataBeanList.get(0).getChildren().get(0).getTitle());
        //把新闻中心的数据传递给左侧菜单
        MainActivity mainActivity = (MainActivity) mmContext;
        //得到左侧菜单
        LeftmenuFragment leftMunuFragment = mainActivity.getLeftMenuFragment();

        //2.绑定数据

        menuDetailBasePagers = new ArrayList<>();
        menuDetailBasePagers.add(new HomeMenuDetailPager(mainActivity,dataBeanList.get(0)));//新闻详情页面
        menuDetailBasePagers.add(new TopicMenuDetailPager(mainActivity,dataBeanList.get(0)));//专题详情页面
        menuDetailBasePagers.add(new PhotosMenuDetailPager(mainActivity,dataBeanList.get(2)));//组图详情页面
        menuDetailBasePagers.add(new InteractMenuDetailPager(mainActivity));//互动详情页面
        //调用LeftMunuFragment的setData
        leftMunuFragment.setData(dataBeanList);

//        switchPager(0);
    }
}
