package com.example.kaixuan.worryfreetutor.discover;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Toast;
import com.example.kaixuan.worryfreetutor.R;
import com.example.kaixuan.worryfreetutor.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends BaseFragment {

    private View mView = null;
    /**
     * 标志，记录View初始化是否完成。
     */
    private boolean isPrepared;
    /**
     * 避免多次请求数据
     */
    private boolean mHasLoadedOnce;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView == null){
            mView = inflater.inflate(R.layout.fragment_discover,container,false);
            initView();
            init();
            isPrepared = true;
            lazyLoad();
        }
        /**
         * 判断mView是否拥有parent，如果有，得删除，否则会有mView已有parent的错误

         ViewGroup parent = (ViewGroup)mView.getParent();
         if(parent != null){
         parent.removeView(mView);
         }
         */
        return mView;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mToolbar =  mView.findViewById(R.id.toolbar);
        mToolbar.inflateMenu((R.menu.menu_toolbar));
        mTabLayout = mView.findViewById(R.id.tabLayout);
        viewPager = mView.findViewById(R.id.viewpager);
    }


    private void init() {
        mFragments.add(new StudentFragment());
        mFragments.add(new TeacherFragment());

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_favorite:
                        Toast.makeText(mView.getContext(),"收藏夹",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_search:
                        Toast.makeText(mView.getContext(),"搜索",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_settings:
                        Toast.makeText(mView.getContext(),"筛选",Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
                return false;
            }
        });

       // mTabLayout.addTab(mTabLayout.newTab().setText("教师"));
       // mTabLayout.addTab(mTabLayout.newTab().setText("学生"));
        mTitle.add("学生");
        mTitle.add("教师");
//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(mView.getContext(),tab.getText(),Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }

            @Override
            public Parcelable saveState(){
                return null;
            }
        });

        mTabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onStart(){
        super.onStart();
    }



    @Override
    public void lazyLoad() {
        if(!isPrepared || !isVisible() || mHasLoadedOnce){
            return ;
        }
        /**
         * 在这里填充数据请求的操作
         */
        mHasLoadedOnce = true;
    }
}

//public class DiscoverFragment extends Fragment {
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
//        return rootView;
//    }
//}
