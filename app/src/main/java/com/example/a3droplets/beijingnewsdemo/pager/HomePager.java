package com.example.a3droplets.beijingnewsdemo.pager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.a3droplets.beijingnewsdemo.activity.MainActivity;
import com.example.a3droplets.beijingnewsdemo.base.BasePager;
import com.example.a3droplets.beijingnewsdemo.base.CacheUtils;
import com.example.a3droplets.beijingnewsdemo.base.Constants;
import com.example.a3droplets.beijingnewsdemo.base.MenuDetailBasePager;
import com.example.a3droplets.beijingnewsdemo.bean.HomeCenterBean;
import com.example.a3droplets.beijingnewsdemo.bean.HomeCenterPagerBean;
import com.example.a3droplets.beijingnewsdemo.detailpager.HomeMenuDetailPager;
import com.example.a3droplets.beijingnewsdemo.detailpager.InteractMenuDetailPager;
import com.example.a3droplets.beijingnewsdemo.detailpager.PhotosMenuDetailPager;
import com.example.a3droplets.beijingnewsdemo.detailpager.TopicMenuDetailPager;
import com.example.a3droplets.beijingnewsdemo.fragment.LeftmenuFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

        //得到缓存数据
        String saveJson = CacheUtils.getString(mmContext, Constants.NEWSCENTER_PAGER_URL);
        if (!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

//        if(saveJson != null){
//            //能进来
//              processData(saveJson);
//        }

//
        getDataFromNet();
    }

    /**
     * 使用xUtils3联网请求数据
     */
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //缓存数据
                CacheUtils.putString(mmContext,Constants.NEWSCENTER_PAGER_URL,result);
                //
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("请求失败",ex.getMessage());
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
     * 解析json数据
     * 绑定数据和显示数据
     * @param json
     */
    private void processData(String json) {
        //1.解析数据：手动解析（用系统的Api解析）和第三方解析json的框架（Gson,fastjson）
//        Gson gson = new Gson();
//       HomeCenterPagerBean bean = new Gson().fromJson(json, HomeCenterPagerBean.class);
        HomeCenterPagerBean bean = parsedJson2(json);
        HomeCenterBean centerBean = parsedJson(json);
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

    /**
     * 使用Android系统自带的API解析json数据
     * @param json
     * @return
     */
    private HomeCenterPagerBean parsedJson2(String json){
//        return new Gson().fromJson(json,HomeCenterPagerBean.class);
        HomeCenterPagerBean bean2 = new HomeCenterPagerBean();
        try{
            JSONObject jsonObject = new JSONObject(json);
            int retcode = jsonObject.optInt("retcode");
            bean2.setRetcode(retcode);
            JSONArray data = jsonObject.optJSONArray("data");

            List<HomeCenterPagerBean.DetailPagerData> detailPagerDatas = new ArrayList<>();
            //设置列表数据
            bean2.setData(detailPagerDatas);

            //for循环，解析每条数据
            for (int i=0; i<data.length();i++){
                JSONObject jsonObject1 = (JSONObject) data.get(i);

                HomeCenterPagerBean.DetailPagerData detailPagerData = new HomeCenterPagerBean.DetailPagerData();
                //添加到集合中
                detailPagerDatas.add(detailPagerData);

                int id = jsonObject1.optInt("id");
                int type = jsonObject1.optInt("type");
                String title = jsonObject1.optString("title");
                detailPagerData.setTitle(title);
                String url = jsonObject1.optString("url");
                detailPagerData.setUrl(url);
                String url1 = jsonObject1.optString("url1");
                detailPagerData.setDayurl(url1);
                String dayurl = jsonObject1.optString("dayurl");
                detailPagerData.setDayurl(dayurl);
                String excurl = jsonObject1.optString("excurl");
                detailPagerData.setExcurl(excurl);
                String weekurl = jsonObject1.optString("weekurl");
                detailPagerData.setWeekurl(weekurl);

                JSONArray children = jsonObject1.optJSONArray("children");
                if (children != null && children.length() >0){
                    List<HomeCenterPagerBean.DetailPagerData.ChildrenData> childrenDatas = new ArrayList<>();

                    //设置集合-ChildrenData
                    detailPagerData.setChildrenData(childrenDatas);

                    for (int j=0; j<data.length();j++){
                        JSONObject childrenitem = (JSONObject) children.get(j);

                        HomeCenterPagerBean.DetailPagerData.ChildrenData childrenData = new HomeCenterPagerBean.DetailPagerData.ChildrenData();
                        //添加到集合中
                        childrenDatas.add(childrenData);

                        int childId = childrenitem.optInt("id");
                        childrenData.setId(childId);
                        String childTitle = childrenitem.optString("title");
                        childrenData.setTitle(childTitle);
                        int childType = childrenitem.getInt("type");
                        childrenData.setType(childType);
                        String childUrl = childrenitem.optString("url");
                        childrenData.setUrl(childUrl);
                    }
                }
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return bean2;
    }

    /**
     * 解析json数据：1、使用系统的API解析json；2、使用第三方框架解析json数据，例如Gson，
     * @param json
     * @return
     */
    private HomeCenterBean parsedJson(String json){
        HomeCenterBean centerBean = new HomeCenterBean();
        try{
            JSONObject jsonObject = new JSONObject(json);
            int retcode = jsonObject.optInt("retcode");
            centerBean.setRetcode(retcode);
            JSONArray data = jsonObject.optJSONArray("data");

            //数据集合
            List<HomeCenterBean.DataBean> dataBeans = new ArrayList<>();
            centerBean.setData(dataBeans);
            for (int i=0; i<data.length();i++){
                JSONObject itemObject = (JSONObject) data.get(i);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return centerBean;
    }

    public void switchPager(int prePosition) {
        //设置标题
        tv_title.setText(dataBeanList.get(prePosition).getTitle());

        if (prePosition < menuDetailBasePagers.size()){
            final MenuDetailBasePager menuDetailBasePager = menuDetailBasePagers.get(prePosition);
            //调用
            menuDetailBasePager.initData();
            //视图
            View rootView = menuDetailBasePager.rootView;
            fl_main.removeAllViews(); //移除之前的所有视图
            fl_main.addView(rootView);

            if (prePosition == 2){
                //组图
                ib_swich_list_gird.setVisibility(View.VISIBLE);
                ib_swich_list_gird.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotosMenuDetailPager photosMenuDetailPager = (PhotosMenuDetailPager) menuDetailBasePagers.get(2);
                        photosMenuDetailPager.switchListGrid(ib_swich_list_gird);
                    }
                });
            }else {
                //其他
                ib_swich_list_gird.setVisibility(View.GONE);
            }
        }else {
            Toast.makeText(mmContext,"该页面暂时未实现",Toast.LENGTH_SHORT).show();
        }
    }
}
