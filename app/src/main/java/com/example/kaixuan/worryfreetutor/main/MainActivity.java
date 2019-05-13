package com.example.kaixuan.worryfreetutor.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.kaixuan.worryfreetutor.R;
import com.example.kaixuan.worryfreetutor.discover.DiscoverFragment;
import com.example.kaixuan.worryfreetutor.homepage.HomePagerFragment;
import com.example.kaixuan.worryfreetutor.me.MeFragment;
import com.example.kaixuan.worryfreetutor.message.MessageFragment;
import com.example.kaixuan.worryfreetutor.shop.ShopFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationBar.OnTabSelectedListener,ViewPager.OnPageChangeListener {

    private BottomNavigationBar bottomNavigationBar;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private ViewPager viewPager;
    private int lastSelectedPosition =0;
    private List<Fragment> fragments;


    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationBar = findViewById(R.id.bottomnavigationbar);
        initView();
    }

    private void initView() {
        initBottomNavigationBar();
        initViewPager();
    }

    private void initBottomNavigationBar() {

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.bottomnavigationbar_homepage,"首页")).setActiveColor(R.color.mediumblue)
                .addItem(new BottomNavigationItem(R.drawable.bottomnavigationbar_discover,"发现")).setActiveColor(R.color.mediumblue)
                .addItem(new BottomNavigationItem(R.drawable.bottomnavigationbar_shop,"商场")).setActiveColor(R.color.mediumblue)
                .addItem(new BottomNavigationItem(R.drawable.bottomnavigationbar_message,"消息")).setActiveColor(R.color.mediumblue)
                .addItem(new BottomNavigationItem(R.drawable.bottomnavigationbar_me,"我")).setActiveColor(R.color.mediumblue)
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
//        setDefaultFragement();
    }

    private void initViewPager(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        fragments = new ArrayList<Fragment>();
        fragments.add(new HomePagerFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new ShopFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MeFragment());

        viewPager.setAdapter(new MainViewPageAdapter(getSupportFragmentManager(),fragments));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
    }

//    private void setDefaultFragement() {
//        fragmentManager = getSupportFragmentManager();
//        transaction = fragmentManager.beginTransaction();
//
//        /**
//         * replace这里尚有一些错误，不能这样，后面再改
//         */
//
//        transaction.replace(R.id.viewpager,MainFragment.newInstance("首页"));
//        transaction.commit();
//    }




    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationBar.selectTab(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(int position) {
        viewPager.setCurrentItem(position);

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
