package com.example.a3droplets.beijingnewsdemo.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a3droplets.beijingnewsdemo.R;
import com.example.a3droplets.beijingnewsdemo.activity.MainActivity;
import com.example.a3droplets.beijingnewsdemo.base.BaseFragment;
import com.example.a3droplets.beijingnewsdemo.bean.HomeCenterBean;
import com.example.a3droplets.beijingnewsdemo.pager.HomePager;

import org.xutils.common.util.DensityUtil;

import java.util.List;

/**
 * Created by Mickey_Ma on 2018/12/29.
 * Description:左侧菜单
 */
public class LeftmenuFragment extends BaseFragment {
    private TextView textView;
    /**
     * 左侧菜单对应的数据
     */
    private List<HomeCenterBean.DataBean> data;

    /**
     * 点击的位置
     */
    private int prePosition = 0;
    private LeftmenuFragmentAdapter adapter;
    private ListView listView;

    @Override
    public View initView() {
        listView = new ListView(mContext);
        listView.setPadding(0,DensityUtil.dip2px(mContext,40),0,0);
        listView.setBackgroundColor(Color.BLACK);
        //设置监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录点击位置和刷新适配器
                prePosition = position;
                adapter.notifyDataSetChanged();//getCount-->getView

                //2.关闭侧滑菜单
                MainActivity mainActivity = (MainActivity)mContext;
                mainActivity.getSlidingMenu().toggle();//关<->开

                //3.切换到对应的详情页面
                switchPager (prePosition);
            }
        });

        return listView;
    }

    /**
     * 根据位置切换到不同的详情页
     * @param prePosition
     */
    private void switchPager(int prePosition) {
        MainActivity mainActivity = (MainActivity) mContext;
        ContentFragment contentFragment = mainActivity.getContentFragment();
        //得到新闻中心
        HomePager homeCenterPager = contentFragment.getNewsCenterPager();
        //调用新闻中心的切换详情页的方法
        homeCenterPager.switchPager(prePosition);
    }

    @Override
    public void initData() {
        super.initData();
    }

    /**
     * 接收数据
     * @param dataBeanList
     */
    public void setData(List<HomeCenterBean.DataBean> dataBeanList) {
        this.data = dataBeanList;

        //设置适配器
        adapter = new LeftmenuFragmentAdapter();
        listView.setAdapter(adapter);

        switchPager(prePosition);
    }

    private class LeftmenuFragmentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView)View.inflate(mContext, R.layout.item_leftmenu,null);

            //设置内容
            textView.setText(data.get(position).getTitle());
            if (prePosition == position){
                //把颜色设置高亮-红色
                textView.setEnabled(true);
            }else {
                //默认为白色
                textView.setEnabled(false);
            }

            return textView;
        }
    }
}
