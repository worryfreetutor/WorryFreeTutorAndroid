package com.example.kaixuan.worryfreetutor.homepage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kaixuan.worryfreetutor.R;
import com.example.kaixuan.worryfreetutor.base.BaseFragment;

public class HomePagerFragment extends BaseFragment {
    private View mView = null;
    /**
     * 标志，记录View初始化是否完成。
     */
    private boolean isPrepared;
    /**
     * 避免多次请求数据
     */
    private boolean mHasLoadedOnce;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView == null){
            mView = inflater.inflate(R.layout.fragment_homepage,container,false);
            initView();
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
//public class HomePagerFragment extends Fragment {
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_homepage, container, false);
//        return rootView;
//    }
//}
