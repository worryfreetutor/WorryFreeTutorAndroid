package com.example.kaixuan.worryfreetutor.base;

import android.support.v4.app.Fragment;


public abstract class BaseFragment extends Fragment {

    private boolean isVisible;

    /**
     * 此方法会在onCreateView之前被调用
     * 实现数据的懒加载
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()){
             isVisible = true;
             onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    private void onVisible(){
        lazyLoad();
    }

    private void onInvisible(){

    }

    public abstract void lazyLoad();
}